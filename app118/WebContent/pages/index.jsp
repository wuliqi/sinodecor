<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.app118.constants.SystemConstant"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta property="qc:admins" content="4440546002611225346367" />
<meta property="wb:webmaster" content="492993831b5d0494" />

<title><%=SystemConstant.SYSTEM_NAME%></title>
<%@include file="/pages/common/common.jsp"%>
<script type="text/javascript">
	//当回车按下时,/=47,*=42,+=43
	$(document).bind("keypress", function(event){
		event = window.event || event;
		if(event.keyCode==13||event.keyCode==42){	//回车,*
			login();
		}
	});
	
	$(function() {
		var flag = "${msg}";
		if(flag!=''){
			if (flag == '2') {
				$.ligerDialog.error("用户名或密码错误。");
				return;
			}else if (flag == '9'){
				$.ligerDialog.error("用户名或密码错误，请确认。");
				return;
			}else if (flag == '0'){
				$.ligerDialog.error("用户名或密码错误，请与管理员联系。");
				return;
			}else if (flag == '4'){
				$.ligerDialog.error("权限限制不能访问，请登录。");
				return;
			}else{
				$.ligerDialog.error("用户名或密码错误，登录失败。");
				return;
			}
		}
		
	});

	// 更换验证码
	function changValCode(id) {
		$("#" + id).attr("src","/app118/verificationCode/getImgCode?" + (new Date()).getTime());
	}
	
	//登录验证
	function login() {
		var url="/app118/mainAction/login";
		var loginName=$("#loginName").val();
		if(loginName==null||trim(loginName)==''){
			$.ligerDialog.warn("登录名不能为空。");
			$("#loginName").focus();
			return;
		}
		var password=$("#password").val();
		if(password==null||trim(password)==''){
			$.ligerDialog.warn("密码不能为空。");
			$("#password").focus();
			return;
		}
		var code=$("#code").val();
		if(code==null||trim(code)==''){
			$.ligerDialog.warn("验证码不能为空。");
			$("#code").focus();
			return;
		}else{
			$.ajax({
				type : "POST",
				url : "/app118/verificationCode/checkVerifyWord",
				data : "verifyWord=" + $("#code").val(),
				success : function(msg) {
					if ("false"==msg) {
						$.ligerDialog.warn("验证码错误，请重新输入。");
						$("#code").select();
						changValCode("imgVcode");
					}else{
						document.forms[0].action = url;
				 		document.forms[0].submit();
					}
				},
				error:function(msg){
					$.ligerDialog.warn("操作失败。");
				}
			});
		}
		
		
	}
	
	function resetForm(){
		$("#loginName").val("");
		$("#password").val("");
		$("#code").val("");
	}
	
</script>
<style type="text/css">
body {
 padding:0;
 margin:0;
}
.main {
 width:912px;
 height:514px;
 margin:0 auto;
 /*border:1px solid #F00;*/
 position:absolute; 
 top:50%; 
 left:50%; 
 margin-left:-456px;
 margin-top:-257px;
 /*background-color:  #74bcec;*/
}
</style>
</head>
<body>
<form action="" method="post">
<div class="main">
<table  border="0" width="912px" height="514px" style="background-image: url('/resource/images/login_back.png');">
	<tr height="65px">
		<td colspan="3" style="width: 100%,height:35px">
			<img alt="<%=SystemConstant.SYSTEM_NAME%>" src="/resource/images/login_title.png" style="height: 35px;padding-left: 50px;padding-top: 40px;">
		</td>
	</tr>
	<tr height="379px">
		<td width="379px" height="256px" style="padding-bottom: 80px;">
			<img alt="<%=SystemConstant.SYSTEM_NAME%>" src="/resource/images/login_notebook.png" style="width: 379px;height: 256px;padding-left: 25px;padding-right: 25px;">
		</td>
		<td width="6px"  height="260px" valign="top" style="padding-top: 40px;">
			<img alt="" src="/resource/images/login_vertical_line.png" style="width:6px;height: 260px;">
		</td>
		<td>
			<table border="0" width="100%">
				<tr>
					<td align="right">
						<img alt="用户名" src="/resource/images/login_login_name.png" style="padding-right:16px; padding-right: 20px;">
					</td>
					<td>
						<input type="text" name="loginName" id="loginName" value="${loginName}" style="width: 250px;height: 33px;line-height: 33px;" /><font color="red">*</font>
					</td>
				</tr>
				<tr height="33px">
					<td align="right">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="right">
						<img alt="密码" src="/resource/images/login_password.png" style="padding-right: 20px;">
					</td>
					<td>
						<input type="password" name="password" id="password" style="width:  250px;height: 33px;line-height: 33px;" /><font color="red">*</font>
					</td>
				</tr>
				<tr height="33px">
					<td align="right">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="right">
						<img alt="验证码" src="/resource/images/login_validate_code.png" style="padding-right: 20px;">
					</td>
					<td>
						<input type="text" name="code" id="code" style="width:  250px;height: 33px;line-height: 33px;" /><font color="red">*</font> 
						<img src="/app118/verificationCode/getImgCode" align="middle" id='imgVcode' name='imgVcode' alt="验证码" onclick="javascript:changValCode('imgVcode');" title="看不清？换一张"/>
					</td>
				</tr>
				<tr height="33px">
					<td align="right">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<img alt="登录" src="/resource/images/login_login_btn.png" onclick="login();">
						<img alt="重置" src="/resource/images/login_reset.png" style="padding-left: 20px;" onclick="resetForm();">
					</td>
				</tr>
				<tr height="33px">
					<td align="right">
						&nbsp;
					</td>
					<td align="right">
						<a href="/index.jsp" style="text-decoration: none;"><font color="#ffffff">Version：<%=SystemConstant.WEB_SYSTEM_VERSION%></font>&nbsp;&nbsp;&nbsp;&nbsp;</a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>