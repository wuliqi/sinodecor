<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
</head>
<body>
<center>
	<h3>
		<font color="green">
			<c:if test="${message!='' }">
				${message}，系统3秒后自动跳转，手动请点击<a href="${url}">这里</a>。
			</c:if>
		</font>
	</h3>
</center>

<script language="javascript">
	window.setTimeout(goUrl,5000);//定时执行，3秒后执行show()  
	function goUrl(){
		var url="${url}";
		if(!(url==null || trim(url)=='')){
			window.location.href=url;
		}
	}
	

	
</script>
</body>
</html>
