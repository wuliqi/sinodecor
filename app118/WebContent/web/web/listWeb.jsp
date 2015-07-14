<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<title></title>
<meta name="description" content="网站描述，一般显示在搜索引擎搜索结果中的描述文字，用于介绍网站，吸引浏览者点击。" />
<meta name="keywords" content="网站关键词" />
<link href="favicon.ico" rel="shortcut icon" />
<link rel="stylesheet" type="text/css" href="/web/css/main.css" />
<script src="/web/js/jQuery1.7.2.js" type="text/javascript"></script>
<script src="/web/js/ch.js" type="text/javascript"></script>

<style>
.digg4 {
	padding: 3px;
	margin: 3px;
	text-align: center;
	font-family: Tahoma, Arial, Helvetica, Sans-serif;
	font-size: 12px;
}

.digg4  a, .digg4 span.miy {
	border: 1px solid #ddd;
	padding: 2px 5px 2px 5px;
	margin: 2px;
	color: #aaa;
	text-decoration: none;
}

.digg4  a:hover {
	border: 1px solid #a0a0a0;
}

.digg4  a:hover {
	border: 1px solid #a0a0a0;
}

.digg4  span.current {
	border: 1px solid #e0e0e0;
	padding: 2px 5px 2px 5px;
	margin: 2px;
	color: #aaa;
	background-color: #f0f0f0;
	text-decoration: none;
}

.digg4  span.disabled {
	border: 1px solid #f3f3f3;
	padding: 2px 5px 2px 5px;
	margin: 2px;
	color: #ccc;
}

.digg4 .disabledfy {
	font-family: Tahoma, Verdana;
}
</style>

</head>
<body>
    <header>
	<!-- 导航菜单开始 -->
	<%@include file="/web/top.jsp"%>
	<!-- 导航菜单结束 -->
	</header>

	<div class="inner met_flash">
		<div class="flash">
			<a href='#' target='_blank' title='${system.webName}'>
				<img src='/web/images/1342430358.jpg' width='980' alt='${system.webName}' height='90'>
			</a>
		</div>
	</div>


<div class="sidebar inner">
 
	<!-- 左侧导航开始 -->
 	<%@include file="/web/left.jsp"%>
 	<!-- 左侧导航结束 -->
 	
    <div class="sb_box">
	    <h3 class="title">
			<div class="position">
				当前位置：<a href="/web/index.jsp" title="网站首页">网站首页</a> &gt; <a href="/app/wWebAction/listWebByPager?curNo=1">友情链接</a>
			</div>
			<span>友情链接</span>
		</h3>
		<div class="clear"></div>

        <div class="active" id="newslist">
			<!-- 列表开始 -->
			<ul class='list-none metlist'>
				<c:forEach items="${list}" var="web" varStatus="status">
					<li class='list '>
						<a href='${web.webUrl}' title='${web.webName}' target='_self'>${web.webName}</a>
					</li>
				</c:forEach>
			</ul>
			<!-- 列表结束-->
			
			<!-- 分页开始 -->
			<div id="flip">		
				<div class='digg4 metpager_8'>
				<c:if test="${totalPages>0}">	   			
					<table align="center">
							<tr>
						   		<td width="100%" colspan="9" align="right">
						   		            每页显示${curSize}条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						   			<a href='/app/wWebAction/listWebByPager?curNo=1'>«首页</a>
						   			<c:choose>
						   				<c:when test="${curNo-1 ge 1}">
						   					<a href='/app/wWebAction/listWebByPager?curNo=${curNo-1}'>‹上一页</a>
						   				</c:when>
						   				<c:otherwise>
						   					<a href='/app/wWebAction/listWebByPager?curNo=1'>‹上一页</a>
						   				</c:otherwise>
						   			</c:choose>
						   			<c:choose>
						   				<c:when test="${curNo+1 le totalPages}">
						   					<a href='/app/wWebAction/listWebByPager?curNo=${curNo+1}'>下一页›</a>
						   				</c:when>
						   				<c:otherwise>
						   					<a href='/app/wWebAction/listWebByPager?curNo=${totalPages}'>下一页›</a>
						   				</c:otherwise>
						   			</c:choose>			   			
						   			<a href='/app/wWebAction/listWebByPager?curNo=${totalPages}'>末页»</a>	
						   			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第${curNo}页/共${totalPages}页			   			
						   		</td>
						   	</tr> 
				   		</table>
			   		</c:if>	
			   		<c:if test="${totalPages<=0}">	
			   			<span ><font color="gray">暂无数据！</font></span>
			   		</c:if>	
				</div> 
			</div>
			<!-- 分页结束 -->
		</div>
	</div>
    <div class="clear"></div>
    
</div>

<!-- 底部导航菜单开始 -->
<%@include file="/web/footer.jsp"%>
<!-- 底部导航菜单结束 -->
<script src="/web/js/fun.inc.js" type="text/javascript"></script>
</body>
</html>