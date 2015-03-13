<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>app用户管理</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<script src="/resource/lib/ligerUI/js/plugins/ligerGrid.js"
	type="text/javascript"></script>
<LINK rel=stylesheet type=text/css
	href="/resource/lib/ligerUI/skins/ligerui-icons.css">
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerToolBar.js"></SCRIPT>
<SCRIPT type=text/javascript src="/resource/lib/ligerUI/js/plugins/ligerMenu.js"></SCRIPT> 
<SCRIPT type=text/javascript src="/resource/lib/ligerUI/js/plugins/ligerMenuBar.js"></SCRIPT> 
<STYLE type=text/css>
    #menu1,.l-menu-shadow{top:30px; left:50px;} 
    #menu1{  width:200px;} 
</STYLE> 
</head>
<body>
	<div class="l-form">
		<form action="" id="form1" method="post">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td class="tdtitle">
					用户名：
				</td>
				<td class="tdtext">
					<input type="text" name="loginName" value="${loginName}" id="loginName" style="width: 128px;height: 20px;"/>
				</td>
				<td class="tdtitle">姓名：</td>
				<td class="tdtext">
					<input type="text" name="realName" value="${realName}" id="realName" style="width: 128px;height: 20px;"/>
				</td>
			</tr>
			<tr>
				<td class="tdtitle">
					从：
				</td>
				<td>
					<input type="text" name="fromCreateTime" value="${fromCreateTime}" id="fromCreateTime"/>
				</td>
				<td class="tdtitle">至：</td>
				<td>
					<input type="text" name="toCreateTime" value="${toCreateTime}" id="toCreateTime"/>
				</td>
			</tr>
			<tr>
				<td class="tdtitle">
					所属组织机构：
				</td>
				<td>
					<select name="orgId" id="orgId" style="width: 128px;height: 20px;" ${disabled}>
						<option value="">---请选择---</option>
						<c:forEach var="oneMap" items="${orgMap}">
							<option value="${oneMap.key }" <c:if test="${orgId==oneMap.key }">selected </c:if> >${oneMap.value}</option>
						</c:forEach>
					</select>
				</td>
				<td class="tdtitle">&nbsp;</td>
				<td class="tdtext">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td  align="right">
					<ul class="l-form-buttons" >
						<li onclick="forSearch()">
							<div class="l-button" style="width: 60px;"
								ligeruiid="Button1001">
								<div class="l-button-l"></div>
								<div class="l-button-r"></div>
								<span>查询</span>
							</div>
						</li>
						<li onclick="forClean();">
							<div class="l-button" style="width: 60px;"
								ligeruiid="Button1002">
								<div class="l-button-l"></div>
								<div class="l-button-r"></div>
								<span>重置</span>
							</div>
						</li>
					</ul>
					
				</td>
			</tr>
		</table>
		</form>
	</div>
	
	
	
	<div class="l-loading" style="display:block" id="pageloading"></div>
	<div class="l-clear"></div>
	
	<!-- 工具栏 -->
	<div id="toptoolbar" class="l-toolbar" ligeruiid="toptoolbar" style="width:99%;">
		<div class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon"
			toolbarid="item-1" jQuery1405912963467="22" onclick="toInsertAgentUser();">
			<SPAN>注册用户</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-add"></div>
		</div>
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-2"
			jQuery1405912963467="23" onclick="toUpdAgentUser();">
			<SPAN>&nbsp;修改用户</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-modify"></div>
		</div>
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-3"
			jQuery1405912963467="24" onclick="delAgentUser();">
			<SPAN>&nbsp;删除用户</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-delete"></div>
		</div>
		<c:if test="${updUserType==1 }">
			<div class=l-bar-separator></div>
			<div class="l-toolbar-item l-panel-btn" toolbarid="item-3"
				jQuery1405912963467="24" onclick="updUserType();">
				<SPAN>&nbsp;转后台用户</SPAN>
				<div class=l-panel-btn-l></div>
				<div class=l-panel-btn-r></div>
				<div class="l-icon l-icon-back"></div>
			</div>
		</c:if>
	</div>
	<!-- 工具栏结束 -->
	<div id="maingrid" ></div>
	 
<script type="text/javascript">
	var dialog=null;//弹出对话框
     var url = "/app118/agentUserManagerAction/listAgentUser";
     $(function (){
     	$("#fromCreateTime").ligerDateEditor({
			showTime : true,
			labelWidth :200,
			labelAlign : 'left'
		});
     	$("#toCreateTime").ligerDateEditor({
			showTime : true,
			labelWidth : 100,
			labelAlign : 'left'
		});
     	
     	init(url+"?orgId=${orgId}");
     });
     
     //重置条件
     function forClean(){
     	$("#loginName").val('');
     	$("#realName").val('');
     	$("#fromCreateTime").val('');
     	$("#toCreateTime").val('');
     }
   	 //查询
   	 function forSearch(){
   		var loginName=$("#loginName").val();
   		var realName=$("#realName").val();
   		var fromCreateTime= $("#fromCreateTime").val();
     	var toCreateTime=$("#toCreateTime").val();
     	var orgId=$("#orgId").val();
   	    var params="?loginName="+loginName+"&realName="+encodeURI(realName)+"&fromCreateTime="+fromCreateTime+"&toCreateTime="+toCreateTime+"&orgId="+orgId;
   	    init(url+params); 
   	 }
     
   	 //增加用户
   	 function toInsertAgentUser(){
   		var url ="/app118/agentUserManagerAction/toInsertAgentUser";
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:520,
  				  isResize:true,
  				  width: 650, 
  				  title: '注册用户'
  		}); 
   	 }
   	 
   	 //修改用户
   	 function toUpdAgentUser(){
   		var params;
   		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行。");
			 return;
		}else{
			 var len=rowid.length;
			 if(len>1){
			 	$.ligerDialog.warn("请只选择一行。");
				return;
			 }else{
				var id=rowid[0].userId;
				var loginName=rowid[0].loginName;
				var realName=rowid[0].realName;
				var sex=rowid[0].sex;
				var orgId=rowid[0].orgId;
			
				params="?loginName="+loginName+"&realName="+encodeURI(realName)+"&sex="
					+encodeURI(sex)+"&userId="+id+"&orgId="+orgId;
	
			 }
		}
   		var url ="/app118/agentUserManagerAction/toUpdAgentUser"+params;
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:520,
  				  isResize:true,
  				  width: 650, 
  				  title: '修改用户'
  		}); 
   	 }
   	
   	 //删除用户
   	 function delAgentUser(){
   		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行");
			 return;
		}else{
		 var userIds="";
		 var len=rowid.length;
		 for(var j=0;j<len;j++){
		   if(j!=len-1){
			   userIds+=rowid[j].userId+",";			
			}else{
				userIds+=rowid[j].userId;
			}
		 }
		 var url="/app118/agentUserManagerAction/delAgentUser";
		  $.ligerDialog.confirm('确认删除选择的用户吗？',function(yes){
			  if(yes){
		      		$.ajax({
			      			type:'POST',
							url:url,
							data:{userIds:userIds},
							dataType:'json',
			      			success:function(msg){
			      				if(msg.flag=1){
			      					$.ligerDialog.success("删除成功。");
			      					g.loadData();
			      				}else{
			      					$.ligerDialog.error("删除失败。");
			      				}
			      			},
			      			error:function(){
			      				$.ligerDialog.error("操作失败。");
			      			}
			      		});
				}else{
				   g.loadData();
				}
			})
		} 
   	 }
   	 
     //转后台用户
     function updUserType(){
    		var params;
    		var rowid=g.getSelecteds();//获得选中行ID
  		if(rowid.length==0){
  			 $.ligerDialog.warn("请至少选择一行。");
  			 return;
  		}else{
  		 var id="";
  		 var len=rowid.length;
  			 if(len>1){
  			 	$.ligerDialog.warn("请只选择一行。");
  				return;
  			 }else{
  				id+=rowid[0].userId;
  				var userType=rowid[0].userType;
  				if(userType!="3"){
  					$.ligerDialog.warn("【"+rowid[0].realName+"】为后台用户，无需转换。");
  					return;
  				}
  			 }
  		}
    		var url ="/app118/userAction/updUserType";
    	    $.ligerDialog.confirm('确认将选择的用户转为后台用户吗？',function(yes){
  		  if(yes){
  	      		$.ajax({
  		      			type:'POST',
  						url:url,
  						data:{userId:id,userType:"1"},
  						dataType:'json',
  		      			success:function(msg){
  		      				if(msg.flag=1){
  		      					$.ligerDialog.success("转后台用户成功。");
  		      					g.loadData();
  		      				}else{
  		      					$.ligerDialog.error("转后台用户失败。");
  		      				}
  		      			},
  		      			error:function(){
  		      				$.ligerDialog.error("操作失败。");
  		      			}
  		      		});
  			}else{
  			   g.loadData();
  			}
  		 })
     }
     
     
    //关闭对话框
 	function closeDialog(){
 		dialog.hide();
 		g.loadData();
 	}
  
     
  	/**
  	*初始化
  	*/	
  	function init(url){
  		var data = {};
  		/**
  		* 加载Grid数据并显示
  		*/
  		window['g']= $("#maingrid").ligerGrid({
  	        	  width: '99%',
            	  height:'99%',
  	      	      rowHeight:26,
  	      	   	  checkbox:true,
  	              headerRowHeight:28,
  	              dateFormat: "yyyy-MM-dd hh:mm:ss",
  	              columns: [
		  	          { display: '用户标识', name: 'userId'},
	  	              { display: '所属组织机构', name: 'orgName'},
	  	              { display: '用户名', name: 'loginName'},
	  	              { display: '姓名', name: 'realName'},
	  	              { display: '性别', name: 'sex'},
	  	              { display: '用户类型', name: 'userTypeCn'},
	  	              { display: '创建时间', name: 'createTime'}
  	              ], url:url,dataAction:"server" , pageSize:15 ,rownumbers:true,pageParmName:"curNo",pagesizeParmName:"curSize"
  	          });
  		
  	    $("#pageloading").hide();
  	}
</script>
</body>
</html>