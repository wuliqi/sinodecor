<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<title>${system.webName}</title>
<meta name="description" content="网站描述，一般显示在搜索引擎搜索结果中的描述文字，用于介绍网站，吸引浏览者点击。" />
<meta name="keywords" content="网站关键词" />
<link href="favicon.ico" rel="shortcut icon" />

<link rel='stylesheet' type='text/css' href='/pages/web/css/css.css'/>

<script src='/pages/web/js/jquery.bxSlider.min.js'></script>
<script type='text/javascript'>
	$(document).ready(function(){//图片轮播
		$('#slider6').bxSlider({
			mode:'vertical',autoHover:true,auto:true,pager: true,pause: 5000,controls:false
		});
	});
</script>
</head>
<body>
<header>
<!-- 导航菜单开始 -->
<%@include file="/pages/web/top.jsp"%>
<!-- 导航菜单结束 -->
</header>
<!-- 图片轮播开始 -->
<div class="inner met_flash">
<div class='flash flash6' style='width:980px; height:245px;'>
		<ul id='slider6' class='list-none'>
		<li><a href='#' target='_blank' ><img src='/pages/web/images/1342429839.jpg'width='980' height='245'></a></li>
		<li><a href='#' target='_blank' ><img src='/pages/web/images/1342430031.jpg'  width='980' height='245'></a></li>
		<li><a href='#' target='_blank' ><img src='/pages/web/images/1342430031.jpg'  width='980' height='245'></a></li>
		<li><a href='#' target='_blank' ><img src='/pages/web/images/1342430031.jpg'  width='980' height='245'></a></li>
		</ul>
	</div>
</div>
<!-- 图片轮播结束 -->

<div class="index inner">
	<!-- 公司简介开始 -->
	<div class="aboutus style-1">
		<h3 class="title">
			<span class='myCorner' data-corner='top 5px'>公司简介</span>
			<a href="/app118/contentAction/viewContentBody?newsId=12&newsCategory=7&newsCategoryCn=关于我们" class="more" title="链接关键词">更多>></a>
		</h3>
		<div class="active editor clear contour-1">
			<div>
				<img id="aboutImg" alt="" src="http://demo.metinfo.cn/upload/images/20120716_094159.jpg" style="margin: 8px; width: 196px; float: left; height: 209px; " />
			</div>
			<div id="aboutContent"></div>
			<div class="clear"></div>
		</div>
	</div>
	<!-- 公司简介结束 -->

	<!-- 案例开始 -->
	<div class="case style-2">
		  <h3 class='title myCorner' data-corner='top 5px'><a href="" title="链接关键词" class="more">更多>></a>客户案例 </h3>
		  <div class="active dl-jqrun contour-1">
				<dl class="ind">
					<dt>
						<a href="#" target='_self'>
							<img src="images/1342431883.jpg" alt="图片关键词" title="图片关键词" style="width:116px; height:80px;" />
						</a>
					</dt>
					<dd>
						<h4><a href="#" target='_self' title="示例案例六">示例案例六</a></h4>
						<p class="desc" title="相关描述文字，相关描述文字，相关描述文字，相关描述文字，相关描述文字。">
							相关描述文字，相关描述文字，相关描述文字，相关描述文字，相关..
						</p>
					</dd>
				</dl>
				
				<dl class="ind">
					<dt>
						<a href="#" target='_self'>
							<img src="images/1342428068.jpg" alt="图片关键词" title="图片关键词" style="width:116px; height:80px;" />
						</a>
					</dt>
					<dd>
						<h4><a href="#" target='_self' title="示例案例五">示例案例五</a></h4>
						<p class="desc" title="相关描述文字，相关描述文字，相关描述文字，相关描述文字，相关描述文字。">
							相关描述文字，相关描述文字，相关描述文字，相关描述文字，相关..
						</p>
					</dd>
				</dl>
				
				<div class="clear"></div>
		   </div>
	</div>
	<div class="clear"></div>
    <!-- 案例结束 -->
    
    <!-- 公司新闻开始 -->
	<div class="index-news style-1">
		<h3 class="title">
			<span class='myCorner' data-corner='top 5px'>公司新闻</span>
			<a href="/app118/contentAction/listContentByPager?curNo=1&newsCategory=3&newsCategoryCn=公司新闻" class="more" title="链接关键词">更多>></a>
		</h3>
		<div class="active clear listel contour-2">
		<ol class='list-none metlist' id="newsTitle">
			<!-- <li class='list top'><span class='time'>2012-07-17</span><a href='#' >如何选择网站关键词?</a></li>
			<li class='list '><span class='time'>2012-07-16</span><a href='#' >新手使用建站步骤</a></li>
			<li class='list '><span class='time'>2012-07-16</span><a href='#' >企业网站应该多长时间备份一次？</a></li>
			<li class='list '><span class='time'>2012-07-16</span><a href='#' >如何充分发挥的SEO功能</a></li>
			<li class='list '><span class='time'>2012-07-16</span><a href='#' >什么是伪静态？伪静态有何作用?</a></li>
			<li class='list '><span class='time'>2012-07-16</span><a href='#' >企业用网站进行网络宣传的优势</a></li>
			<li class='list '><span class='time'>2012-07-16</span><a href='#' >企业建站系统有何优势？</a></li>
			<li class='list '><span class='time'>2012-07-16</span><a href='#' >商业版和免费版在系统功能上有区别吗？</a></li>
			<li class='list '><span class='time'>2012-07-16</span><a href='#' >为什么企业要建多国语言网站？</a></li>
			<li class='list '><span class='time'>2012-07-16</span><a href='#' >如何获取网站管理系统商业授权？</a></li> -->
		</ol>
		</div>
	</div>
    <!-- 公司新闻结束 -->
    
    <!-- 行业资讯开始 -->
	<div class="index-news style-1">
		<h3 class="title">
			<span class='myCorner' data-corner='top 5px'>行业资讯</span>
			<a href="/app118/contentAction/listContentByPager?curNo=1&newsCategory=10&newsCategoryCn=行业资讯" class="more" title="链接关键词">更多>></a>
		</h3>
		<div class="active clear listel contour-2">
			<ol class='list-none metlist' id="professionTitle">
				<!----> <li class='list top'><span class='time'>2012-07-17</span><a href='#' >如何选择网站关键词?</a></li>
				<li class='list '><span class='time'>2012-07-16</span><a href='#' >新手使用建站步骤</a></li>
				<li class='list '><span class='time'>2012-07-16</span><a href='#' >企业网站应该多长时间备份一次？</a></li>
				<li class='list '><span class='time'>2012-07-16</span><a href='#' >如何充分发挥的SEO功能</a></li>
				<li class='list '><span class='time'>2012-07-16</span><a href='#' >什么是伪静态？伪静态有何作用?</a></li>
				<li class='list '><span class='time'>2012-07-16</span><a href='#' >企业用网站进行网络宣传的优势</a></li>
				<li class='list '><span class='time'>2012-07-16</span><a href='#'>企业建站系统有何优势？</a></li>
				<li class='list '><span class='time'>2012-07-16</span><a href='#'>商业版和免费版在系统功能上有区别吗？</a></li>
				<li class='list '><span class='time'>2012-07-16</span><a href='#' >为什么企业要建多国语言网站？</a></li>
				<li class='list '><span class='time'>2012-07-16</span><a href='#'>如何获取网站管理系统商业授权？</a></li> 
			</ol>
		</div>
	</div>
	 <!-- 行业资讯结束 -->
	
	<!-- 招聘开始 -->
	<div class="index-conts style-2">
		<h3 class='title myCorner' data-corner='top 5px'>
			<a href="/app118/contentAction/listContentByPager?curNo=1&newsCategory=6&newsCategoryCn=招贤纳士 " title="链接关键词" class="more">更多>></a>招贤纳士
		</h3>
		<div class="active clear listel contour-2">
			<ol class='list-none metlist' id="jobTitle">
				 <li class='list top'><span class='time'>2012-07-16</span><a href='#' >PHP技术支持</a></li>
				 <li class='list '><span class='time'>2012-07-16</span><a href='#' >网络销售</a></li>
				 <li class='list '><span class='time'>2012-07-16</span><a href='#' >网页UI设计师</a></li>
				 <li class='list '><span class='time'>2012-07-16</span><a href='#' >Web前端开发人员</a></li>
				 <li class='list '><span class='time'>2012-07-16</span><a href='#'>电子商务专员</a></li>
			</ol>
		</div>
	</div>
	<div class="clear p-line"></div>
	<!-- 招聘结束 -->
	
    <!-- 底部轮播图片开始 -->
	<div class="index-product style-2">
		<h3 class='title myCorner' data-corner='top 5px'>
			 <span></span>
			 <div class="flip">
				 <p id="trigger"></p> 
				 <a class="prev" id="car_prev" href="javascript:void(0);"></a> 
				 <a class="next" id="car_next" href="javascript:void(0);"></a>
			 </div>
			<a href=""  class="more">更多>></a>
		</h3>
		<div class="active clear">
			<div class="profld" id="indexcar" data-listnum="5">
				<ol class='list-none metlist'>
					 <li class='list'><a href='#'  class='img'><img src='images/1342405015.jpg'  width='160' height='130' /></a><h3 style='width:160px;'><a href='#' >示例产品八</a></h3></li>
					 <li class='list'><a href='#'  class='img'><img src='images/1342404422.jpg'  width='160' height='130' /></a><h3 style='width:160px;'><a href='#' >示例产品七</a></h3></li>
					 <li class='list'><a href='#'  class='img'><img src='images/1342404422.jpg'  width='160' height='130' /></a><h3 style='width:160px;'><a href='#' title='示例产品六' target='_self'>示例产品六</a></h3></li>
					 <li class='list'><a href='#'  class='img'><img src='images/1342404144.jpg'  width='160' height='130' /></a><h3 style='width:160px;'><a href='#' title='示例产品三' target='_self'>示例产品三</a></h3></li>
					 <li class='list'><a href='#'  class='img'><img src='images/1342360923.jpg'  width='160' height='130' /></a><h3 style='width:160px;'><a href='#' title='示例产品五' target='_self'>示例产品五</a></h3></li>
					 <li class='list'><a href='#'  class='img'><img src='images/1342405015.jpg'  width='160' height='130' /></a><h3 style='width:160px;'><a href='#' title='示例产品四' target='_self'>示例产品四</a></h3></li>
					 <li class='list'><a href='#'  class='img'><img src='images/1342404144.jpg'  width='160' height='130' /></a><h3 style='width:160px;'><a href='#' title='示例产品二' target='_self'>示例产品二</a></h3></li>
					 <li class='list'><a href='#'  class='img'><img src='images/1342360923.jpg'  width='160' height='130' /></a><h3 style='width:160px;'><a href='#'>示例产品一</a></h3></li>
				</ol>
			 </div>
		 </div>
	</div>
	<div class="clear"></div>
	<!-- 底部轮播图片结束 -->
	
	<!-- 友情链接开始-->
	<div class="index-links">
		<h3 class="title">
			<a href="" title="链接关键词" class="more">更多>></a>
		</h3>
		<div class="active clear">
			<div class="img">
				<ul class='list-none'></ul>
			</div>
			<div class="txt">
				<ul class='list-none'>
					<li><a href='#' target='_blank' title=''>sina</a></li>
					<li><a href='#' target='_blank' title=''>信息</a></li>
				</ul>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<!-- 友情链接结束 -->
</div>

<!-- 底部导航菜单开始 -->
<%@include file="/pages/web/footer.jsp"%>
<!-- 底部导航菜单结束 -->

<script src="/pages/web/js/fun.inc.js" type="text/javascript"></script>
<script type="text/javascript">
$(function (){
	initAbout();
	initNews("newsTitle",3,"新闻资讯");
	initNews("professionTitle",10,"行业资讯");
	initNews("jobTitle",6,"招贤纳士");
});

//公司简介
function initAbout(){	
	var url="/app118/contentAction/initAbout";
	$.ajax({
		type:'POST',
		url:url,
		data:{newsId:12},
		dataType:'json',
		success:function(news){
			var imgUrl="/app118/newsAction/showImage?newsThumbnail="+news.news.newsThumbnail;
			$("#aboutImg").attr("src",imgUrl);   
			$("#aboutContent").html(news.news.newsContent);
		},
		error:function(){
			$.ligerDialog.error("操作失败。");
		}
	});
}

//公司新闻  行业资讯
function initNews(htmlId,newsCategory,newsCategoryCn){	
	var url="/app118/contentAction/initNews";
	$.ajax({
		type:'POST',
		url:url,
		data:{"curNo":1,"newsCategory":newsCategory},
		dataType:'json',
		success:function(json){
			var jsonObj = eval(json.list);
			var html="";
			for(var i=0;i<jsonObj.length;i++){
				var news=jsonObj[i];
				if(i==0){
					html=html+"<li class='list top'><span class='time'>"+news.createTime+
					"</span><a href='/app118/contentAction/viewContent?newsId="+news.newsId+
					"&newsCategory="+news.newsCategory+"&newsCategoryCn='"+newsCategoryCn+" >"+news.newsTitle+"</a></li>";
				}else{
					html=html+"<li class='list'><span class='time'>"+news.createTime+
					"</span><a href='/app118/contentAction/viewContent?newsId="+news.newsId+
					"&newsCategory="+news.newsCategory+"&newsCategoryCn='"+newsCategoryCn+" >"+news.newsTitle+"</a></li>";
				}
			}
			$("#"+htmlId).html(html);
		},
		error:function(){
			$.ligerDialog.error("操作失败。");
		}
	});
}
</script>

</body>
</html>