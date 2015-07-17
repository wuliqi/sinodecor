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
				当前位置：<a href="/web/index.jsp" title="网站首页">网站首页</a> &gt; <a href="/app/wFeedbackAction/toAddFeedback?category=${category}">在线${contentTitle}</a>
			</div>
			<span>在线${contentTitle}</span>
		</h3>
		<div class="clear"></div>

		<!-- 公司简介内容开始 -->
        <div class="editor active" id="showtext">
		    <div>
		    	<form action="" method="post">
		    	<table>
					<tr>
						<td class="tdtitle">
							手机：
						</td>
						<td class="tdtext" nowrap="nowrap">
							<input type="hidden" name="category" id="category" value="${category}" style="width: 288px;height:20px "/>
							<input type="tel" name="phoneNumber" id="phoneNumber" style="width: 288px;height:20px "/>
						</td>
					</tr>
					<tr>
						<td class="tdtitle">
							${contentTitle}：
							<input type="hidden" name="contentTitle" id="contentTitle" value="${contentTitle}" style="width: 288px;height:20px "/>
						</td>
						<td >
							<textarea rows="8" cols="30" name="content" id="content"></textarea>
						</td>
					</tr>
					<tr align="center">
						<td colspan="2" > 
							<input type="button" value="保存" id="submitBtn" style="width: 60px;height: 26px;"/> 
						</td>
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
<script type="text/javascript">
$(function() {
	var url = "/app/wFeedbackAction/addFeedback";
	$("#submitBtn").click(function() {
		
		var phoneNumber=$("#phoneNumber").val();
		if(phoneNumber==null||trim(phoneNumber)==''){
			alert("手机号码不能为空。");
			$("#phoneNumber").focus();
			return;
		}else{
			if(!checkPhone(phoneNumber)){
				alert(phoneNumber+"，手机号码不正确。");
				$("#phoneNumber").focus();
				return;
			}
		}
		var content=$("#content").val();
		if(content==null||trim(content)==''){
			alert("${contentTitle}不能为空。");
			$("#content").focus();
			return;
		}else if(content.length>1024){
			alert("${contentTitle}内容不能超过512个字。");
			$("#content").focus();
			return;
		}
		
		document.forms[0].action = url;
 		document.forms[0].submit();	
	});

});

//提示信息
function tips(){
	var msg ="${message}";
	if(!(msg=='null'||msg=='')){
		if(msg == "success"){
			alert("${contentTitle}成功。");
		}else{
			alert("${contentTitle}失败。");
		}
	}
}
</script>

</body>
</html>