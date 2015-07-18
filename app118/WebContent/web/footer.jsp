<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<footer data-module="10001" data-classnow="10001">
	<div class="inner">
		<div class="foot-nav">
			<a href='/app/wContentAction/listContentByPager?curNo=1&newsCategory=3&newsCategoryCn=公司新闻'  title='公司新闻'>公司新闻</a><span>|</span>
			<a href='javascript:addFeedback(3);'  title='在线留言'>在线留言</a><span>|</span>
			<a href='javascript:addFeedback(1);'  title='在线反馈'>在线反馈</a><span>|</span>
			<a href='/app/wWebAction/listWebByPager?curNo=1'  title='友情链接'>友情链接</a><span>|</span>
			<a href='/web/siteSearch.jsp'  title='站内搜索'>站内搜索</a><span>|</span>
			<!-- <a href='/web/sitemap.htm'  title='网站地图'>网站地图</a><span>|</span> -->
			<a href='/pages/login/login.jsp'  title='网站管理' target="_blank">网站管理</a>
		</div>
		<div class="foot-text">
			<p>${system.webName} 版权所有 ©2015-2025  ${system.icp}  <script src="http://s4.cnzz.com/z_stat.php?id=1254781847&web_id=1254781847" language="JavaScript"></script> version:${system.version}</p>
			<p>手机号码：18810790739   QQ:408873941  Email:wRitchie@sohu.com</p>
		</div>
	</div>
</footer>
<script type="text/javascript">
	 function addFeedback(category){
   		var url ="/app/wFeedbackAction/toAddFeedback?category="+category;
   		window.location.href=url;
   	 }
</script>