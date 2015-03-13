<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台用户管理--修改用户密码</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerComboBox.js"></SCRIPT>
</head>
<body onload="tips();">
<form action="" method="post">
	<table border="0">
		<tr>
			<td  class="tdtitle">新密码：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="hidden" name="userId" id="userId" value="${user.userId}">
				<input type="password" name="curPwd" id="curPwd"   style="width: 220px;" /><font color="red">*</font>请输入6-16字符新密码（由数字、字母、符号组成）。
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">确认密码：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="password" name="password" id="password"   style="width: 220px;" /><font color="red">*</font>请输入6-16字符确认密码（由数字、字母、符号组成）
			</td>
		</tr>
	
		
		<tr align="center">
			<td colspan="2" > 
				<input type="button" value="修改密码" id="submitBtn" class="l-button l-button-submit"/> 
			</td>
		</tr>
	</table>
</form>



</body>
<script language="javascript">
	$(function() {
		var url = "/app118/accountAction/updAccountPwd";
		$("#submitBtn").click(function() {
			var curPwd=$("#curPwd").val();
			var password=$("#password").val();
			
			if(curPwd==null||trim(curPwd)==''){
				$.ligerDialog.warn("新密码不能为空。");
				$("#curPwd").focus();
				return;
			}
			if(password==null||trim(password)==''){
				$.ligerDialog.warn("确认密码不能为空。");
				$("#password").focus();
				return;
			}
			if(curPwd!=password){
				$.ligerDialog.warn("确认密码与新密码不一致。");
				$("#password").focus();
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
				$.ligerDialog.success("修改用户密码成功。",function(){
					if (top.location != self.location){
		     			top.location = self.location;
					}
		     		window.top.location ="/app118/mainAction/logout" ;
				});
			}else if(msg == "none"){
				$.ligerDialog.warn("修改用户密码失败，请重新登录再修改。");
			}else{
				$.ligerDialog.warn("修改用户密码失败。");
			}
		}
	}	
	
	
	
</script>
</html>