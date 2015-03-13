<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>微信二维码--带设备标识</title>
<%@include file="/mobile/common/common.jsp"%>
<meta charset="UTF-8" />

<script type="text/javascript">

</script>

</head>
<body style="text-align: center">
<br/><br/><br/>
	<img alt="微信二维码" src="	${url}" width="132px;" height="132px;"><br/>
	<a href="${url}">下载</a>
</body>
</html>