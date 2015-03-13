<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册用户</title>
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
				<input type="text" name="loginName" id="loginName"  value="${u.loginName}"  style="width: 220px;"/><font color="red">*</font>手机号，即登录后台用户账号
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">真实姓名：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="realName" id="realName" value="${u.realName}" style="width: 220px;"/><font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">姓别：</td>
			<td  nowrap="nowrap" class="tdtext">
				<select id="sex" name="sex" style="width: 220px;height: 20px;">
					<option value="男">男</option>
					<option value="女">女</option>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">所属组织机构：</td>
			<td  nowrap="nowrap" class="tdtext">
				<select name="orgId" id="orgId" style="width: 220px;height: 20px;" ${disabled} >
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${orgMap}">
						<option value="${oneMap.key }"  <c:if test="${orgId==oneMap.key }">selected </c:if> >${oneMap.value}</option>
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


 
</body>
<script language="javascript">
	$(function() {
		var url = "/app118/agentUserManagerAction/addAgentUser";
		$("#submitBtn").click(function() {
			var loginName=$("#loginName").val();
			var realName=$("#realName").val();
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
			if(orgId==null||trim(orgId)==''){
				$.ligerDialog.warn("所属门店不能为空，请选择。");
				$("#orgId").focus();
				return;
			}
			
			
			$("#orgId").removeAttr("disabled"); 
			document.forms[0].action = url;
	 		document.forms[0].submit();	

		});

	});
	
	//提示信息
	function tips(){
		var msg ="${message}";
		if(!(msg=='null'||msg=='')){
			if(msg == "success"){
				$.ligerDialog.alert("注册用户成功。",function(){
					parent.closeDialog();	
				});
			}else if(msg=="exist"){
				$.ligerDialog.alert("用户已存在，不能重复注册。",function(){
					
				});
			}else{
				$.ligerDialog.alert("注册用户失败。",function(){
					parent.closeDialog();	
				});
			}
		}
	}	
</script>
</html>