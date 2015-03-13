<%@ page language="java" contentType="text/html;charset=utf-8" %>

<%--
	说明：
	请再各页面的<head>区域<title>标签之后使用：
	<%@include file="/mobile/common/common.jsp"%>引用本页面
	
     viewport meta: 
     1、width : 控制viewport的大小，可以指定一个值，如600， 或者特殊的值，如device-width为设备的宽度（单位为缩放为100%的CSS的像素）
     2、height : 和width相对应，指定高度
     3、initial-scale : 初始缩放比例，页面第一次加载时的缩放比例
     4、maximum-scale : 允许用户缩放到的最大比例，范围从0到10.0
     5、minimum-scale : 允许用户缩放到的最小比例，范围从0到10.0
     6、user-scalable : 用户是否可以手动缩放，值可以是：①yes、 true允许用户缩放；②no、false不允许用户缩放
--%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- 页面清缓存 --%>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0" />

<script src="/mobile/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="/mobile/js/jquery.mobile-1.4.2/jquery.mobile-1.4.2.min.css" >
<script type="text/javascript" src="/mobile/js/jquery.mobile-1.4.2/jquery.mobile-1.4.2.js"></script>
<%--  全局禁用jQuery Mobile的Ajax提交功能 
<script type="text/javascript">
	$(document).bind("mobileinit", function() {
		$.mobile.defaultTransition = "slidedown";
		$.mobile.ajaxLinksEnabled = false; // 禁用超链接Ajax提交
		$.mobile.ajaxFormsEnabled = false; // 禁用表单Ajax提交
		$.mobile.ajaxEnabled = false; //禁用Ajax提交
	});
</script>--%>

<script src="/resource/js/app118.js"
	type="text/javascript"></script>