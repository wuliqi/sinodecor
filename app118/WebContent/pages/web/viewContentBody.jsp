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

</head>
<body>
    <header>
	<!-- 导航菜单开始 -->
	<%@include file="/pages/web/top.jsp"%>
	<!-- 导航菜单结束 -->
	</header>

	<!-- 图片开始 -->
	<div class="inner met_flash">
		<div class="flash">
			<a href='#' target='_blank' title='企业网站管理系统'>
				<img src='/pages/web/images/1342430358.jpg' width='980' alt='企业网站管理系统' height='90'>
			</a>
		</div>
	</div>
	<!-- 图片结束 -->

<div class="sidebar inner">
 	<!-- 左侧导航开始 -->
 	<%@include file="/pages/web/left.jsp"%>
 	<!-- 左侧导航结束 -->
    
    <div class="sb_box">
	    <h3 class="title">
			<div class="position">
				当前位置：<a href="/pages/web/index.jsp" title="网站首页">网站首页</a> &gt; <a href="/app118/contentAction/viewContentBody?newsId=${news.newsId}&newsCategory=${news.newsCategory}&newsCategoryCn=${newsCategoryCn}">${newsCategoryCn}</a>
			</div>
			<span>${newsCategoryCn}</span>
		</h3>
		<div class="clear"></div>

		<!-- 公司简介内容开始 -->
        <div class="editor active" id="showtext">
		    <div>
		    	${news.newsContent}
			</div>
			<div class="clear"></div>
		</div>
		<!-- 公司简介内容结束-->
    </div>
    <div class="clear"></div>
    
</div>

<!-- 底部导航菜单开始 -->
<%@include file="/pages/web/footer.jsp"%>
<!-- 底部导航菜单结束 -->
<script src="js/fun.inc.js" type="text/javascript"></script>
</body>
</html>