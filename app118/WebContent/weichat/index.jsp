<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>微信控车</title>
<%@include file="/mobile/common/common.jsp"%>
<meta charset="UTF-8" />

<script type="text/javascript">
function save(){
	var url = "/app118/remoteControlAction/addDeviceCtrl";
	var deviceName=$("#deviceName").val();
	if(deviceName==null||trim(deviceName)==''){
		alert("汽车牌号不能为空。");
		return;
	}else if(!isCarNumber(deviceName)){
		alert(deviceName+",汽车牌号不正确。");
		return;
	}
	document.forms[0].action = url;
	document.forms[0].submit();	
}

function weixinAddContact(name){
	alert(name);
	WeixinJSBridge.invoke("addContact", {webtype: "1",username: name}, function(e) {
		WeixinJSBridge.log(e.err_msg);
		//e.err_msg:add_contact:added 已经添加
		//e.err_msg:add_contact:cancel 取消添加
		//e.err_msg:add_contact:ok 添加成功
		alert(e.err_msg);
		if(e.err_msg == 'add_contact:added' || e.err_msg == 'add_contact:ok'){
		    //关注成功，或者已经关注过
		    alert("关注成功。");
		}else{
			 alert("关注失败。");
		}
	})
}	
</script>

</head>
<body style="text-align: center">
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>

<form action="">
	<input type="text" name="deviceName" id="deviceName" value="${deviceName}">
	<input type="button" value="确认控车" onclick="save();">
	<!-- <a href="#" onclick="weixinAddContact('gh_89cee745c270')">关注我们:)</a>  -->
	
	 <div id="ctl00_ContentPlaceHolder1_i_fenxiang" style="float: right; margin: 40px 0 0 0;"><div class="bshare-custom"><a title="分享到QQ空间" class="bshare-qzone"></a><a title="分享到新浪微博" class="bshare-sinaminiblog"></a><a title="分享到人人网" class="bshare-renren"></a><a title="分享到腾讯微博" class="bshare-qqmb"></a><a title="分享到网易微博" class="bshare-neteasemb"></a><a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a><span class="BSHARE_COUNT bshare-share-count">0</span></div>
        <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=3&amp;lang=zh"></script>
        <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
     </div>
     <br style="clear: both;" />
</form>
</body>
</html>