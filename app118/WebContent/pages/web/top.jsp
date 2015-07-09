<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="/pages/web/css/main.css" />
<script src="/pages/web/js/jQuery1.7.2.js" type="text/javascript"></script>
<script src="/pages/web/js/ch.js" type="text/javascript"></script>

<div class="inner">
	<div class="top-logo">
		<a href="/pages/web/index.jsp" title="${system.webName}" id="web_logo"><img src="/pages/web/images/logo.png" alt="${system.webName}" title="${system.webName}" style="margin:20px 0px 0px 0px;" /></a>
		
		<ul class="top-nav list-none">
		<li class="t">
			<a href='#' onclick='SetHome(this,window.location,"非IE浏览器不支持此功能，请手动设置！");' style='cursor:pointer;' title='设为首页'  >设为首页</a>
			<span>|</span>
			<a href='#' onclick='addFavorite("非IE浏览器不支持此功能，请手动设置！");' style='cursor:pointer;' title='收藏本站'  >收藏本站</a>
			<span>|</span>
			<a class="fontswitch" id="StranLink" href="javascript:StranBody()">繁体中文</a>
			<!--
			<span>|</span>
			<a href='#' title='WAP'>WAP</a><span>|</span>
			<a href='#' title='English'  >English</a><span>|</span>
			<a href='#' title='我的订单' class='shopweba'>我的订单</a> -->
		</li>
		<li class="b">
		</li>
		</ul>
	</div>
	<!-- 顶部菜单开始     class='navdown'-->
	<nav>
		<ul class="list-none">
			<li id="nav_10001" style='width:121px;'>
				<a href='/pages/web/index.jsp' class='nav'><span>网站首页</span></a>
			</li>
			
			<li class="line"></li>
			<li id='nav_1' style='width:121px;' >
				<a href='/app118/contentAction/viewContentBody?newsId=12&newsCategory=7&newsCategoryCn=关于我们' class='hover-none nav'><span>关于我们</span></a>
			</li>
			
			<li class="line"></li>
			<li id='nav_2' style='width:121px;' >
				<a href='/app118/contentAction/listContentByPager?curNo=1&newsCategory=3&newsCategoryCn=新闻资讯'  class='hover-none nav'><span>新闻资讯</span></a>
			</li>
			
			<li class="line"></li>
			<li id='nav_3' style='width:121px;' >
				<a href='/app118/contentAction/listContentByPager?curNo=1&newsCategory=4&newsCategoryCn=产品展示' class='hover-none nav'><span>产品展示</span></a>
			</li>
			
			<li class="line"></li>
			<li id='nav_32' style='width:121px;' >
				<a href='/app118/contentAction/listContentByPager?curNo=1&newsCategory=9&newsCategoryCn=下载中心'   class='hover-none nav'><span>下载中心</span></a>
			</li>
			
			<li class="line"></li>
			<li id='nav_33' style='width:121px;' >
				<a href='/app118/contentAction/listContentByPager?curNo=1&newsCategory=5&newsCategoryCn=客户案例'   class='hover-none nav'><span>客户案例</span></a>
			</li>
			
			<li class="line"></li>
			<li id='nav_36' style='width:120px;' >
				<a href='/app118/contentAction/listContentByPager?curNo=1&newsCategory=6&newsCategoryCn=招贤纳士'   class='hover-none nav'><span>招贤纳士</span></a>
			</li>
			
			<li class="line"></li>
			<li id='nav_22' style='width:120px;' >
				<a href='/app118/contentAction/viewContentBody?newsId=29&newsCategory=8&newsCategoryCn=联系我们'  class='hover-none nav'><span>联系我们</span></a>
			</li>
		</ul>
	</nav>
	<!-- 顶部菜单结束 -->
</div>