<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.app118.constants.SystemConstant"%>
<!DOCTYPE html>
<html lang="en" class="no-js">

<head>

<meta charset="utf-8">
<title><%=SystemConstant.SYSTEM_NAME%></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- CSS -->

<link rel="stylesheet" href="css/supersized.css">
<link rel="stylesheet" href="css/login.css">
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	<script src="js/html5.js"></script>
<![endif]-->
<script src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/tooltips.js"></script>

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
						
						if (document.forms[0].isMember.checked){
							setCookie("loginName",loginName);
							setCookie("password",password);
						} else {
							delCookie("loginName");
							delCookie("password");
						}
						document.forms[0].action = url;
						document.forms[0].submit();
					}
				},
				error : function(msg) {
					$.ligerDialog.warn("操作失败。");
				}
			});
		}

	}

	function resetForm() {
		$("#loginName").val("");
		$("#password").val("");
		$("#code").val("");
	}
</script>

</head>

<body>
<%
	String loginName = ""; //用户名
	String password = ""; //密码
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("loginName")) {
				loginName = cookies[i].getValue().split("-")[0];
				request.setAttribute("loginName", loginName); //存用户名
				request.setAttribute("checkFlag", true);
			} else if (cookies[i].getName().equals("password")) {
				password = cookies[i].getValue().split("-")[0];
				request.setAttribute("password", password); //存密码
			}
		}
	}
%>


<div class="page-container">
	<div class="main_box">
		<div class="login_box">
			<div class="login_logo">
				<img src="/resource/images/login_title.png"  alt="<%=SystemConstant.SYSTEM_NAME%>" >
			</div>
		
			<div class="login_form">
				<form action="" method="post">
					<div class="form-group">
						<label for="j_username" class="t">用户名：</label> 
						<input type="text" id="loginName" name="loginName" value="${loginName}" class="form-control x319 in">
					</div>
					<div class="form-group">
						<label for="j_password" class="t">密　码：</label> 
						<input type="password" id="password"  name="password" value="${password}"  class="password form-control x319 in">
					</div>
					<div class="form-group">
						<label for="j_captcha" class="t">验证码：</label>
						<input  type="text" name="code" id="code"  class="form-control x164 in">
						<img src="/app118/verificationCode/getImgCode" align="middle" id='imgVcode' name='imgVcode' alt="验证码" onclick="javascript:changValCode('imgVcode');" title="看不清？换一张"/>
					</div>
					<div class="form-group">
						<label class="t"></label>
						<label for="j_remember" class="m">
						<input id="isMember" name="isMember" type="checkbox" value="true"  <c:if test="${checkFlag==true}">checked="checked"</c:if>>&nbsp;记住我</label>
					</div>
					<div class="form-group space">
						<label class="t"></label>　　　
						<button type="button" class="btn btn-primary btn-lg" onclick="login();">&nbsp;登&nbsp;录&nbsp </button>
						<input type="reset" value="&nbsp;重&nbsp;置&nbsp;" class="btn btn-default btn-lg" onclick="resetForm();">
					</div>
				</form>
			</div>
		</div>
		<div class="bottom" onclick="client();">Copyright &copy; 2015 - 2025 app118.cn,All Rights Reserved  Version <%=SystemConstant.WEB_SYSTEM_VERSION%></div>
	</div>
</div>

<!-- Javascript -->
<script src="js/supersized.3.2.7.min.js"></script>
<script src="js/supersized-init.js"></script>
<script src="js/scripts.js"></script>
<script type="text/javascript">
	function client(){
		window.location.href="/pages/web/index.jsp";
	}
	
</script>
<div style="text-align:center;">
<p></p>
</div>
</body>
</html>