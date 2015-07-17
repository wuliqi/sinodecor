<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<title></title>
<meta name="description" content="莎琪美妆--为您的美丽而服务" />
<meta name="keywords" content="莎琪美妆 Chanel 香奈儿   欧珀莱/AUPRES " />
<meta name="generator" content="MetInfo 5.1.7" />
<link href="favicon.ico" rel="shortcut icon" />

<link rel="stylesheet" type="text/css" href="/web/css/main.css" />
<script src="/web/js/jQuery1.7.2.js" type="text/javascript"></script>
<script src="/web/js/ch.js" type="text/javascript"></script>

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
				当前位置：<a href="/web/index.jsp" title="网站首页">网站首页</a> &gt; <a href="/app/wContentAction/listContentByPager?curNo=1&newsCategory=${newsCategory}&newsCategoryCn=${newsCategoryCn}" >${newsCategoryCn}</a> > 详情
			</div>
			<span>${newsCategoryCn}</span>
		</h3>
		<div class="clear"></div>

        <div class="active" id="shownews">
            <h1 class="title">${news.newsTitle}</h1>
            <!-- 详细内容开始 -->
            <div class="editor">
            	<div>
            		${news.newsContent}
				</div>
				<div class="clear"></div>
			</div>
			<!-- 详细内容结束 -->
			<div class="met_hits">
				<!--分享开始  -->
				<div class='metjiathis'>
				<!-- JiaThis Button BEGIN -->
				<div class="jiathis_style">
					<a class="jiathis_button_qzone"></a>
					<a class="jiathis_button_tsina"></a>
					<a class="jiathis_button_tqq"></a>
					<a class="jiathis_button_weixin"></a>
					<a class="jiathis_button_renren"></a>
					<a href="http://www.jiathis.com/share?uid=2045152" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
					<a class="jiathis_counter_style"></a>
				</div>
				<script type="text/javascript">
				var jiathis_config = {data_track_clickback:'true'};
				</script>
				<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=2045152" charset="utf-8"></script>
				<!-- JiaThis Button END -->
				</div>
				<!--分享结束  -->
				点击次数：${news.clicks}
				&nbsp;&nbsp;更新时间：${createTime}&nbsp;&nbsp;
				【<a href="javascript:window.print()">打印此页</a>】&nbsp;&nbsp;
				【<a href="javascript:self.close()">关闭</a>】
			</div>
            <div class="met_page">上一条：${pre}&nbsp;&nbsp;下一条：${next}</div>
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