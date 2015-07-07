<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<title></title>
<meta name="description" content="网站描述，一般显示在搜索引擎搜索结果中的描述文字，用于介绍网站，吸引浏览者点击。" />
<meta name="keywords" content="网站关键词" />
<meta name="generator" content="MetInfo 5.1.7" />
<link href="favicon.ico" rel="shortcut icon" />
<link rel="stylesheet" type="text/css" href="css/main.css" />
<script src="js/jQuery1.7.2.js" type="text/javascript"></script>
<script src="js/ch.js" type="text/javascript"></script>

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
	<%@include file="/pages/web/top.jsp"%>
	<!-- 导航菜单结束 -->
	</header>

	<div class="inner met_flash"><div class="flash">
<a href='#' target='_blank' title='企业网站管理系统'><img src='images/1342430358.jpg' width='980' alt='企业网站管理系统' height='90'></a>
</div></div>


<div class="sidebar inner">
 
	<!-- 左侧导航开始 -->
 	<%@include file="/pages/web/left.jsp"%>
 	<!-- 左侧导航结束 -->
 	
    <div class="sb_box">
	    <h3 class="title">
			<div class="position">
				当前位置：<a href="/pages/web/index.jsp" title="网站首页">网站首页</a> &gt; <a href="/pages/web/news.jsp">新闻资讯</a>
			</div>
			<span>新闻资讯</span>
		</h3>
		<div class="clear"></div>

        <div class="active" id="newslist">
			<ul class='list-none metlist'>
				<li class='list top'><span>[2012-07-17]</span><a href='shownews.jsp' title='如何选择网站关键词?' target='_self'>如何选择网站关键词?</a><img class='listhot' src='images/hot.gif' alt='图片关键词' /></li>
				<li class='list '><span>[2012-07-16]</span><a href='shownews.jsp' title='新手使用MetInfo建站步骤' target='_self'>新手使用MetInfo建站步骤</a><img class='listhot' src='images/hot.gif' alt='图片关键词' /></li>
				<li class='list '><span>[2012-07-16]</span><a href='shownews.jsp' title='企业网站应该多长时间备份一次？' target='_self'>企业网站应该多长时间备份一次？</a><img class='listhot' src='images/hot.gif' alt='图片关键词' /></li>
				<li class='list '><span>[2012-07-16]</span><a href='shownews.jsp' title='如何充分发挥MetInfo的SEO功能' target='_self'>如何充分发挥MetInfo的SEO功能</a><img class='listhot' src='images/hot.gif' alt='图片关键词' /></li>
				<li class='list '><span>[2012-07-16]</span><a href='shownews.jsp' title='什么是伪静态？伪静态有何作用?' target='_self'>什么是伪静态？伪静态有何作用?</a><img class='listhot' src='images/hot.gif' alt='图片关键词' /></li>
				<li class='list '><span>[2012-07-16]</span><a href='shownews.jsp' title='企业用网站进行网络宣传的优势' target='_self'>企业用网站进行网络宣传的优势</a><img class='listhot' src='images/hot.gif' alt='图片关键词' /></li>
				<li class='list '><span>[2012-07-16]</span><a href='shownews.jsp' title='MetInfo企业建站系统有何优势？' target='_self'>MetInfo企业建站系统有何优势？</a><img class='listhot' src='images/hot.gif' alt='图片关键词' /></li>
				<li class='list '><span>[2012-07-16]</span><a href='shownews.jsp' title='商业版和免费版在系统功能上有区别吗？' target='_self'>商业版和免费版在系统功能上有区别吗？</a><img class='listhot' src='images/hot.gif' alt='图片关键词' /></li>
				<li class='list '><span>[2012-07-16]</span><a href='shownews.jsp' title='为什么企业要建多国语言网站？' target='_self'>为什么企业要建多国语言网站？</a><img class='listhot' src='images/hot.gif' alt='图片关键词' /></li>
				<li class='list '><span>[2012-07-16]</span><a href='shownews.jsp' title='如何获取MetInfo网站管理系统商业授权？' target='_self'>如何获取MetInfo网站管理系统商业授权？</a><img class='listhot' src='images/hot.gif' alt='图片关键词' /></li>
			</ul>
			<div id="flip">
				<div class='digg4 metpager_8'>
					<span class='disabled disabledfy'><b>«</b></span>
					<span class='disabled disabledfy'>‹</span>
					<span class='current'>1</span>
					<span class='disabled'>2</span>
					<span class='disabled'>3</span>
					<span class='disabled disabledfy'>›</span>
					<span class='disabled disabledfy'><b>»</b></span>
				</div>
			</div>
		</div>
	</div>
    <div class="clear"></div>
</div>

<!-- 底部导航菜单开始 -->
<%@include file="/pages/web/footer.jsp"%>
<!-- 底部导航菜单结束 -->
<script src="js/fun.inc.js" type="text/javascript"></script>

</body>
</html>