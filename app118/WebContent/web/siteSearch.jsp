<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<script src="/resource/js/app118.js" type="text/javascript"></script>
<link href="/resource/css/app118.css" rel="stylesheet" type="text/css" />

<SCRIPT language=javascript>
	function g(formname) {
		var url = "http://www.baidu.com/baidu";
		if (formname.s[1].checked) {
			formname.ct.value = "2097152";
		} else {
			formname.ct.value = "0";
		}
		formname.action = url;
		return true;
	}
</SCRIPT>
</head>
<body  onload="tips();">
    <header>
	<!-- 导航菜单开始 -->
	<%@include file="/web/top.jsp"%>
	<!-- 导航菜单结束 -->
	</header>

	<!-- 图片开始 -->
	<div class="inner met_flash">
		<div class="flash">
			<a href='#' target='_blank' title='企业网站管理系统'>
				<img src='/web/images/1342430358.jpg' width='980' alt='企业网站管理系统' height='90'>
			</a>
		</div>
	</div>
	<!-- 图片结束 -->

<div class="sidebar inner">
 	<!-- 左侧导航开始 -->
 	<%@include file="/web/left.jsp"%>
 	<!-- 左侧导航结束 -->
    
    <div class="sb_box">
	    <h3 class="title">
			<div class="position">
				当前位置：<a href="/web/index.jsp" title="网站首页">网站首页</a> &gt; <a href="/web/siteSearch.jsp">站内搜索</a>
			</div>
			<span>站内搜索</span>
		</h3>
		<div class="clear"></div>

		<!-- 公司简介内容开始 -->
        <div class="editor active" id="showtext">
		    <div>
		    	<form name="f1" onsubmit="return g(this)">
					<table bgcolor="#FFFFFF" style="font-size: 9pt;" border="0" width="100%">
						<tr height="60">
							<td valign="top">
								<img src="http://img.baidu.com/img/logo-137px.gif" border="0" alt="baidu">
							</td>
							<td>
								<input name=word size="30" maxlength="100" style="height: 20px;width: 288px;">
								<input type="submit" value="百度搜索" style="height: 20px;"><br>
								<input name=tn type=hidden value="bds">
								<input name=cl type=hidden value="3">
								<input name=ct type=hidden>
								<input name=si type=hidden value="www.app118.cn">
								<div class="mt10">
									<input name=s type=radio> 互联网
								    <input name=s type=radio checked> www.app118.cn
								</div></td>
						</tr>
					</table>
				</form>
			</div>
			<div class="clear"></div>
		</div>
		<!-- 公司简介内容结束-->
    </div>
    <div class="clear"></div>
    
</div>

<!-- 底部导航菜单开始 -->
<%@include file="/web/footer.jsp"%>
<!-- 底部导航菜单结束 -->
<script src="/web/js/fun.inc.js" type="text/javascript"></script>
</body>
</html>





