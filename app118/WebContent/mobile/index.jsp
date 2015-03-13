<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>大赢家</title>
<%@include file="/mobile/common/common.jsp"%>
<meta charset="UTF-8" />
<meta property="qc:admins" content="4440546002611225346367" />
<script type="text/javascript">
	function toLogin() {
		//以下为按钮点击事件的逻辑。注意这里要重新打开窗口
		//否则后面跳转到QQ登录，授权页面时会直接缩小当前浏览器的窗口，而不是打开新窗口
		window.location.href="http://dyj.app118.cn/app118/loginAction/login";
	}

	$(function() {
		var loginFlag = "${loginFlag}";
		if (loginFlag == "1") {//已登录按钮不可点击
			var info="${nickName},您好！<br/>";
			
			$("#loginFlag").html(info);
			$("#loginImg").attr("onclick",null); 
		} else {
			$("#loginFlag").html("未登录");
		}
	});
</script>

</head>
<body style="text-align: center">
	<span id="loginFlag">未登录</span>
	${openId}
	<img id="qqImg" src="${avatarURL}" border="0"/>
	<img id="loginImg" src="/mobile/images/bt_blue_76X24.png" border="0"
		onclick='toLogin()' />








	<%@include file="/mobile/share.jsp"%>

</body>
</html>