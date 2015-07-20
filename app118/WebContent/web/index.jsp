<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<title>${system.webName}</title>
<meta name="description" content="莎琪美妆--为您的美丽而服务" />
<meta name="keywords" content="莎琪美妆 Chanel 香奈儿   欧珀莱/AUPRES " />
<link rel="icon" href="/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>

<link rel='stylesheet' type='text/css' href='/web/css/css.css'/>

<link rel="stylesheet" type="text/css" href="/web/css/main.css" />
<script src="/web/js/jQuery1.7.2.js" type="text/javascript"></script>
<script src="/web/js/ch.js" type="text/javascript"></script>

<script src='/web/js/jquery.bxSlider.min.js'></script>
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
<%@include file="/web/top.jsp"%>
<!-- 导航菜单结束 -->
</header>
<!-- 图片轮播开始 -->
<div class="inner met_flash">
<div class='flash flash6' style='width:980px; height:245px;'>
		<ul id='slider6' class='list-none'>
		<li><a href='http://app118.cn' target='_blank' ><img src='/web/images/1342429839.jpg'width='980' height='245'></a></li>
		<li><a href='http://app118.cn' target='_blank' ><img src='/web/images/1342430031.jpg'  width='980' height='245'></a></li>
		<li><a href='http://app118.cn' target='_blank' ><img src='/web/images/1342429839.jpg'  width='980' height='245'></a></li>
		<li><a href='http://app118.cn' target='_blank' ><img src='/web/images/1342430031.jpg'  width='980' height='245'></a></li>
		</ul>
	</div>
</div>
<!-- 图片轮播结束 -->

<div class="index inner">
	<!-- 公司简介开始 -->
	<div class="aboutus style-1">
		<h3 class="title">
			<span class='myCorner' data-corner='top 5px'>公司简介</span>
			<a href="/app/wContentAction/viewContentBody?newsId=12&newsCategory=7&newsCategoryCn=关于我们" class="more" title="链接关键词">更多>></a>
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
		  <h3 class='title myCorner' data-corner='top 5px'><a href="/app/wContentAction/listContentByPager?curNo=1&newsCategory=5&newsCategoryCn=客户案例" title="客户案例" class="more">更多>></a>客户案例 </h3>
		  <div class="active dl-jqrun contour-1" id="caseContent">
		  	<!--    <dl class="ind">
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
				
				<div class="clear"></div> -->
		   </div>
	</div>
	<div class="clear"></div>
    <!-- 案例结束 -->
    
    <!-- 公司新闻开始 -->
	<div class="index-news style-1">
		<h3 class="title">
			<span class='myCorner' data-corner='top 5px'>公司新闻</span>
			<a href="/app/wContentAction/listContentByPager?curNo=1&newsCategory=3&newsCategoryCn=公司新闻" class="more" title="链接关键词">更多>></a>
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
			<a href="/app/wContentAction/listContentByPager?curNo=1&newsCategory=10&newsCategoryCn=行业资讯" class="more" title="链接关键词">更多>></a>
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
			<a href="/app/wContentAction/listContentByPager?curNo=1&newsCategory=6&newsCategoryCn=招贤纳士 " title="链接关键词" class="more">更多>></a>招贤纳士
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
	<div class="index-product style-2"  >
		<h3 class='title myCorner' data-corner='top 5px'>
			 <span></span>
			 <div class="flip">
				 <p id="trigger"></p> 
				 <a class="prev" id="car_prev" href="javascript:void(0);"></a> 
				 <a class="next" id="car_next" href="javascript:void(0);"></a>
			 </div>
			<a href="/app/wContentAction/listContentByPager?curNo=1&newsCategory=5&newsCategoryCn=客户案例"  class="more">更多>></a>
		</h3>
		<div class="active clear" >
		
			<div class="profld" id="indexcar" data-listnum="5">
				<ol class='list-none metlist' id="viewpagerTitle">
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
		<h3 class="title" title="友情链接">
			 <a href="/app/wWebAction/listWebByPager?curNo=1" title="友情链接" class="more">更多>></a>
		</h3>
		<div class="active clear">
			<div class="img">
				<ul class='list-none'></ul>
			</div>
			<div class="txt">
				<ul class='list-none' id="friendLink">
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
<%@include file="/web/footer.jsp"%>
<!-- 底部导航菜单结束 -->

<script src="/web/js/fun.inc.js" type="text/javascript"></script>
<script type="text/javascript">
$(function (){
	initAbout();
	initNews("newsTitle",3,"新闻资讯");
	initNews("professionTitle",10,"行业资讯");
	initNews("jobTitle",6,"招贤纳士");
	initCase("caseContent",5,"客户案例");//5
	initViwepager("viewpagerTitle",5,"客户案例");//5
	initFriendLink("friendLink");
});

//公司简介
function initAbout(){	
	var url="/app/wContentAction/initAbout";
	$.ajax({
		type:'POST',
		url:url,
		data:{newsId:12},
		dataType:'json',
		success:function(news){
			var imgUrl="/upload/news/"+news.news.newsThumbnail;
			$("#aboutImg").attr("src",imgUrl);   
			$("#aboutContent").html(news.news.newsContent);
		},
		error:function(){
			$.ligerDialog.error("操作失败。");
		}
	});
}

//公司新闻  行业资讯  招贤纳士
function initNews(htmlId,newsCategory,newsCategoryCn){	
	var url="/app/wContentAction/initNews";
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
					"</span><a href='/app/wContentAction/viewContent?newsId="+news.newsId+
					"&newsCategory="+news.newsCategory+"&newsCategoryCn='"+newsCategoryCn+" >"+news.newsTitle+"</a></li>";
				}else{
					html=html+"<li class='list'><span class='time'>"+news.createTime+
					"</span><a href='/app/wContentAction/viewContent?newsId="+news.newsId+
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

//客户案例
function initCase(htmlId,newsCategory,newsCategoryCn){	
	var url="/app/wContentAction/initNews";
	$.ajax({
		type:'POST',
		url:url,
		data:{"curNo":1,"curSize":2,"newsCategory":newsCategory},
		dataType:'json',
		success:function(json){
			var jsonObj = eval(json.list);
			var html="";
			for(var i=0;i<jsonObj.length;i++){
				var news=jsonObj[i];
				
					html=html+"<dl class='ind'>"+
					"<dt style=\"height: 86px;\" >"+
					"<a href='/app/wContentAction/viewContent?newsId="+news.newsId+
					"&newsCategory="+news.newsCategory+"&newsCategoryCn="+newsCategoryCn+"' target='_self'>"+
						"<img src='/upload/news/"+news.newsThumbnail+"' alt='"+news.newsTitle+"' title='"+news.newsTitle+"' style='width:116px; height:80px;' />"+
					"</a>"+
					"</dt>"+
					"<dd style=\"width: 149px; height: 86px;\">"+
						"<h4><a href='/app/wContentAction/viewContent?newsId="+news.newsId+
						"&newsCategory="+news.newsCategory+"&newsCategoryCn="+newsCategoryCn+"' target='_self' title='"+news.newsTitle+"'>"+news.newsTitle.substring(0,5)+"</a></h4>"+
						"<p class='desc' title='"+news.newsBrief+"'>"+
							news.newsBrief.substring(0,28) +
						"</p>"+
					"</dd>"+
					"</dl>"; 
			}
			html=html+"<div class='clear'></div>";
			$("#"+htmlId).html(html);
		},
		error:function(){
			$.ligerDialog.error("操作失败。");
		}
	});
}

//底部图片轮播
function initViwepager(htmlId,newsCategory,newsCategoryCn){	
	var url="/app/wContentAction/initNews";
	$.ajax({
		type:'POST',
		url:url,
		async: false, 
		data:{"curNo":1,"curSize":20,"newsCategory":newsCategory},
		dataType:'json',
		success:function(json){
			var jsonObj = eval(json.list);
			var html="";
			for(var i=0;i<jsonObj.length;i++){
				var news=jsonObj[i];
				if(i==0){
					html=html+"<li class='list' style='height: 157px; margin: 0px 15px; position: relative; left: 0px;'><a href='/app/wContentAction/viewContent?newsId="+news.newsId+
					"&newsCategory="+news.newsCategory+"&newsCategoryCn="+newsCategoryCn+"'  class='img'>"+
					"<img src='/upload/news/"+news.newsThumbnail+"'  width='160' height='130' /></a>"+
					"<h3 style='width:160px;'><a href='/app/wContentAction/viewContent?newsId="+news.newsId+
					"&newsCategory="+news.newsCategory+"&newsCategoryCn="+newsCategoryCn+"' title='"+news.newsTitle+"' target='_self'>"+news.newsTitle+"</a></h3></li>";
				}else{
					html=html+"<li class='list' style='height: 157px; margin: 0px 15px; position: relative;'><a href='/app/wContentAction/viewContent?newsId="+news.newsId+
					"&newsCategory="+news.newsCategory+"&newsCategoryCn="+newsCategoryCn+"'  class='img'>"+
					"<img src='/upload/news/"+news.newsThumbnail+"'  width='160' height='130' /></a>"+
					"<h3 style='width:160px;'><a href='/app/wContentAction/viewContent?newsId="+news.newsId+
					"&newsCategory="+news.newsCategory+"&newsCategoryCn="+newsCategoryCn+"' title='"+news.newsTitle+"' target='_self'>"+news.newsTitle+"</a></h3></li>";
				}
			}
			$("#"+htmlId).html(html);
		},
		error:function(){
			$.ligerDialog.error("操作失败。");
		}
	});
}

//友情链接
function initFriendLink(htmlId){
	var url="/app/wWebAction/initWeb";
	$.ajax({
		type:'POST',
		url:url,
		async: false, 
		data:{"curNo":1,"curSize":100},
		dataType:'json',
		success:function(json){
			var jsonObj = eval(json.list);
			var html="";
			for(var i=0;i<jsonObj.length;i++){
				var web=jsonObj[i];
				html=html+"<li><a href='"+web.webUrl+"' target='_blank' title='"+web.webName+"'>"+web.webName+"</a></li>";
			}
			$("#"+htmlId).html(html);
		},
		error:function(){
			$.ligerDialog.error("操作失败。");
		}
	});
}


</script>


<link href="/web/qq/css/qqconsult.css" rel="stylesheet" type="text/css" />
<!-- 代码部分 begin -->
<div class="main-im">
	<div id="open_im" class="open-im">&nbsp;</div>  
	<div class="im_main" id="im_main">
		<div id="close_im" class="close-im"><a href="javascript:void(0);" title="点击关闭">&nbsp;</a></div>
		<a href="http://wpa.qq.com/msgrd?v=3&uin=408873941&site=qq&menu=yes" target="_blank" class="im-qq qq-a" title="在线QQ客服">
			<div class="qq-container"></div>
			<div class="qq-hover-c"><img class="img-qq" src="/web/qq/images/qq.png"></div>
			<span> QQ在线咨询</span>
		</a>
		<div class="im-tel">
			<div>售前咨询热线</div>
			<div class="tel-num">18801459612</div>
			<div>售后咨询热线</div>
			<div class="tel-num">18810790739</div>
		</div>
		<div class="im-footer" style="position:relative">
			<div class="weixing-container">
				<div class="weixing-show">
					<div class="weixing-txt">微信扫一扫<br>打开莎琪美妆</div>
					<img class="weixing-ma" src="/web/qq/images/weixing-ma.png">
					<div class="weixing-sanjiao"></div>
					<div class="weixing-sanjiao-big"></div>
				</div>
			</div>
			<div class="go-top"><a href="javascript:;" title="返回顶部"></a> </div>
			<div style="clear:both"></div>
		</div>
	</div>
</div>

<script>
$(function(){
	$('#close_im').bind('click',function(){
		$('#main-im').css("height","0");
		$('#im_main').hide();
		$('#open_im').show();
	});
	$('#open_im').bind('click',function(e){
		$('#main-im').css("height","272");
		$('#im_main').show();
		$(this).hide();
	});
	$('.go-top').bind('click',function(){
		$(window).scrollTop(0);
	});
	$(".weixing-container").bind('mouseenter',function(){
		$('.weixing-show').show();
	})
	$(".weixing-container").bind('mouseleave',function(){        
		$('.weixing-show').hide();
	});
});
</script>
<!-- 代码部分 end-->
</body>
</html>