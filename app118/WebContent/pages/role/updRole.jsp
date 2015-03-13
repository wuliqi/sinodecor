<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<SCRIPT type=text/javascript src="/resource/lib/ligerUI/js/plugins/ligerComboBox.js"></SCRIPT>
<link href="/resource/lib/ligerUI/skins/Aqua/css/ligerui-tree.css" rel="stylesheet" type="text/css" />    
<script src="/resource/lib/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>

</head>
<body onload="tips();">
<form action="" method="post">
	<table border="0">
		<tr>
			<td  class="tdtitle">角色名：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="hidden" name="roleId" id="roleId" value="${role.roleId}" style="width: 220px;"/>
				<input type="text" name="roleName" id="roleName" value="${role.roleName}"  style="width: 220px;"/>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">角色编码：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="roleCode" id="roleCode" value="${role.roleCode}"  style="width: 220px;"/><font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">角色状态：</td>
			<td class="tdtext" nowrap="nowrap">
				<select style="width: 220px;" name="isactive" id="isactive">
					<option value="1" <c:if test="${role.isactive eq '1'}">selected</c:if> >启用</option>
					<option value="0" <c:if test="${role.isactive eq '0'}">selected</c:if> >废弃</option>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr style="display: none">
			<td class="tdtitle">排序：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="sort" id="sort" value="${role.sort}"  style="width: 220px;"/><font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">分配权限：</td>
			<td  nowrap="nowrap">
				<textarea rows="15" cols="26" name="menuName"  id="menuName">${menuName}</textarea>
				<font color="red">*</font>
				<input type="button" value="选择"    class="l-button l-button-submit" onclick="selectMenu();">
				<input type="hidden"  name="menuId" id="menuId"  value="${menuId}"/>
				<input type="hidden"  name="menuPid" id="menuPid"  value="${menuPid}"/>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">备注：</td>
			<td  nowrap="nowrap">
				<textarea rows="5" cols="26" name="roleDesc" id="roleDesc">${role.roleDesc}</textarea>
			</td>
		</tr>
		<tr >
			<td colspan="2" > 
				&nbsp;
			</td>
		</tr>
		<tr align="center">
			<td colspan="2" > 
				<input type="button" value="保存" id="submitBtn" class="l-button l-button-submit"/> 
			</td>
		</tr>
	</table>
</form>

<div id="target1" style="height:300px;position:relative;display:none;overflow:auto;">
	<div>
     	<ul id="tree1" style="color:#000;background:#f2f2f2;solid #cacaca;"></ul>
   	</div>
 </div>
</body>
<script language="javascript">
	//菜单树变量定义
	
	var tree;
	var date = [];
	var manager = null;

	$(function() {
		manager = $("#tree1").ligerGetTreeManager();//菜单树
		$("#tree1").ligerTree({ checkbox: true }); 
		
		var url = "/app118/roleAction/updRole";
		$("#submitBtn").click(function() {
			var roleName=$("#roleName").val();
			var roleCode=$("#roleCode").val();
			var menuId=$("#menuId").val();
			
			if(roleName==null||trim(roleName)==''){
				$.ligerDialog.warn("角色名不能为空。");
				$("#roleName").focus();
				return;
			}
			if(roleCode==null||trim(roleCode)==''){
				$.ligerDialog.warn("角色编码不能为空。");
				$("#roleCode").focus();
				return;
			}
			
			if(menuId==null||trim(menuId)==''){
				$.ligerDialog.warn("分配权限不能为空，请授权。");
				$("#menuId").focus();
				return;
			}
			
			//验证角色名称的唯一性
			$.ajax({
				 type:"post",
				 url:"/app118/roleAction/checkRoleNameUnique",
				 data:{roleName:roleName},
				 dataType:"json",
				 success:function(msg1){
				 	if(msg1.message == "multi"){
				 		$.ligerDialog.warn('【'+roleName+'】已经存在，不能重复添加。');
						$("#roleName").focus();
						return;
				 	}else if(msg1.message == "none"||msg1.message == "modify"){
				 		document.forms[0].action = url;
				 		document.forms[0].submit();	
				 	}else if(msg1.message == "fail"){
				 		$.ligerDialog.warn("操作失败。");
				 	}
				},
				error:function(msg){
					$.ligerDialog.warn("操作失败。");
				}
			});
			

		});

	});
	

	
	//提示信息
	function tips(){
		var msg ="${message}";
		if(!(msg=='null'||msg=='')){
			if(msg == "success"){
				$.ligerDialog.alert("${info}",function(){
					parent.closeDialog();	
				});
			}else{
				$.ligerDialog.alert("${info}",function(){
					parent.closeDialog();	
				});
			}
		}
	}
	
	function selectMenu(){
		$.ligerDialog.open({
			title:'授予角色菜单权限',
			width:380, 
			target: $("#target1") ,
			buttons:[ 
			          { 
			        	  text: '确定', onclick: f_selectMenu 
			          }, 
			          { 
			        	  text:'取消', onclick: function (item, dialog) {
			        	 	 dialog.hide(); 
			        	  } 
			          }
			        ]
			});
	}
	
   
	$.ajax({
		url:"/app118/menuAction/listMenu",
		//data:{sysType:"01", isactive:"1"},
		type:"POST",
		async:false, 
		dataType:"json",
		beforeSend:function(XMLHttpRequest){
		},
		success:function(result){
			var array=result.menuList;
			for(var i=0;i<array.length;i++){
				//alert("id:"+array[i].menuId+"  pid:"+array[i].menuPid+"  text:"+array[i].menuName)
				date.push({id:array[i].menuId,pid:array[i].menuPid,text:array[i].menuName});
			}
			$("#menuId").val('${menuId}');
			$("#menuName").val('${menuName}');
			tree = $("#tree1").ligerTree({
			 	data: date,
				nodeWidth:250,
				idFieldName :'id',
		        parentIDFieldName :'pid'
			});
		},
		error:function(){
			$.ligerDialog.warn("提示，系统加载异常。");
		}
	});
   
	//确定菜单树选择
	function f_selectMenu(item,dialog){
        var notes = manager.getChecked();
        var menuIds = "";//菜单标识
    	var menuNames = "";//菜单名称
    	var menuPids="";//菜单父标识
    	if(notes.length<1){
    		$.ligerDialog.warn("请选择授予角色菜单的权限。");
    		return;
    	}
        for (var i = 0; i < notes.length; i++){
        	if(i!=notes.length-1){
        		menuNames += notes[i].data.text+ "," ;
            	menuIds +=notes[i].data.id + ",";
            	menuPids +=notes[i].data.pid + ",";
        	}else{
        		menuNames += notes[i].data.text ;
            	menuIds +=notes[i].data.id;
            	menuPids +=notes[i].data.pid;
        	}
        	
        }
        $("#menuName").val(menuNames);
    	$("#menuId").val(menuIds);
    	$("#menuPid").val(menuPids);
        dialog.hide();
	}
	
	f_selectNode("${menuId}");
	
	//初始化已选择的菜单项
	function f_selectNode(str) {
        str = "," + str + ",";
        var parm = function(data) {
            if (str.indexOf("," + data.id + ",") != -1){ 
            	return true; 
            }else{
            	return false; 
            }
        };
        tree.selectNode(parm);
    };

</script>
</html>