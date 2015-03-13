<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台用户管理--修改用户</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerComboBox.js"></SCRIPT>
</head>
<body onload="tips();">
<form action="" method="post">
	<table border="0">
		<tr>
			<td  class="tdtitle">手机号：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="hidden" name="userId" id="userId" value="${userId}">
				<input type="text" name="loginName" id="loginName" value="${loginName }"  style="width: 220px;" readonly="readonly"/><font color="red">*</font>手机号，即登录后台用户账号
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">真实姓名：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="realName" id="realName" value="${realName }"  style="width: 220px;"/><font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">姓别：</td>
			<td  nowrap="nowrap" class="tdtext">
				<select id="sex" name="sex" style="width: 220px;height: 20px;">
					<option value="男" <c:if test="${sex eq '男'}">selected</c:if>>男</option>
					<option value="女" <c:if test="${sex eq '女'}">selected</c:if>>女</option>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">所属角色：</td>
			<td>
				<textarea rows="15" cols="26"  name="roleNames" id="roleNames"  readonly="readonly">${roleNames}</textarea>
				<input type="hidden" name="roleIds" id="roleIds" value="${roleIds}"/>
				<font color="red">*</font>
				<input type="button" value="选择"    class="l-button l-button-submit" onclick="selectRole();">
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">所属组织机构：</td>
			<td  nowrap="nowrap" class="tdtext">
				<select name="orgId" id="orgId" style="width: 220px;height: 20px;">
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${orgMap}">
						<option value="${oneMap.key }" <c:if test="${orgId eq oneMap.key}">selected</c:if> >${oneMap.value}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
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
	     <c:forEach items="${list}" var="role" varStatus="index">
	     	<input type="checkbox" name="role" id="role" value="${role.roleId }" <c:if test="${role.remark1 eq '1'}">checked</c:if> >${role.roleName}<br/>
	     </c:forEach>
   	</div>
</div>

</body>
<script language="javascript">
	$(function() {
		var url = "/app118/accountAction/updAccount";
		$("#submitBtn").click(function() {
			var loginName=$("#loginName").val();
			var realName=$("#realName").val();
			var roleIds=$("#roleIds").val();
			var orgId=$("#orgId").val();
			
			if(loginName==null||trim(loginName)==''){
				$.ligerDialog.warn("手机号不能为空。");
				$("#loginName").focus();
				return;
			}else{
				if(!checkPhone(loginName)){
					$.ligerDialog.warn(loginName+"，手机号码不正确。");
					$("#loginName").focus();
					return;
				}
			}
			if(realName==null||trim(realName)==''){
				$.ligerDialog.warn("真实姓名不能为空。");
				$("#realName").focus();
				return;
			}
			if(roleIds==null||trim(roleIds)==''){
				$.ligerDialog.warn("所属角色不能为空，请选择。");
				$("#roleIds").focus();
				return;
			}
			
			
			if(orgId==null||trim(orgId)==''){
				$.ligerDialog.warn("所属门店不能为空，请选择。");
				$("#orgId").focus();
				return;
			}
			document.forms[0].action = url;
	 		document.forms[0].submit();	

		});

	});
	
	//提示信息
	function tips(){
		var msg ="${message}";
		if(!(msg=='null'||msg=='')){
			if(msg == "success"){
				$.ligerDialog.alert("修改用户成功。",function(){
					parent.closeDialog();	
				});
			}else if(msg=="exist"){
				$.ligerDialog.alert("用户已存在，不能重复添加。",function(){
					parent.closeDialog();	
				});
			}else{
				$.ligerDialog.alert("修改用户失败。",function(){
					parent.closeDialog();	
				});
			}
		}
	}	
	
	function selectRole(){
		$.ligerDialog.open({
			title:'授予用户角色',
			width:380, 
			target: $("#target1") ,
			buttons:[ 
			          { 
			        	  text: '确定', onclick: f_selectRole 
			          }, 
			          { 
			        	  text:'取消', onclick: function (item, dialog) {
			        	 	 dialog.hide(); 
			        	  } 
			          }
			        ]
			});
	}
	
	//确定选择授予用户角色
	function f_selectRole(item,dialog){
		var r=document.getElementsByName("role");
		var roleIds="";
		var roleNames="";
		
	    for(var i=0;i<r.length;i++){
	   	  
	   		if(r[i].checked){
	   		  roleIds+= r[i].value+",";
	   		  roleNames+= r[i].nextSibling.nodeValue+",";
	   		}
	    } 
	    $("#roleIds").val(roleIds.substring(0, roleIds.length-1));
	    $("#roleNames").val(roleNames.substring(0, roleNames.length-1));

        dialog.hide();
	}
	
</script>
</html>