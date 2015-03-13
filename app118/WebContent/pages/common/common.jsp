<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
请再各页面的<head>区域<title>标签之后使用：
<%@include file="/pages/common/common.jsp"%>
引用本页面
--%>
<%--全局公共页面设置--%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />	

<%-- 页面清缓存 --%>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0" />

<%--全局公共页面变量--%>
<%
   request.setAttribute("base",request.getContextPath());// 公共路径
   request.setAttribute("title", "AirBest后台管理系统");
%>

<!-- CSS静态资源的引用 -->
<link href="/resource/css/app118.css" rel="stylesheet" type="text/css" />
<link href="/resource/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />


<!-- JS静态资源的引用 -->
<script src="/resource/js/app118.js" type="text/javascript"></script>

<script src="/resource/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="/resource/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="/resource/lib/ligerUI/js/plugins/ligerLayout.js" type="text/javascript"></script>   
<script src="/resource/lib/ligerUI/js/plugins/ligerAccordion.js" type="text/javascript"></script>
<script src="/resource/lib/ligerUI/js/plugins/ligerDialog.js"></script>
<script src="/resource/lib/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>

		

