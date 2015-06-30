<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="cn.app118.constants.SystemConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@include file="/pages/common/common.jsp"%>
<script src="/resource/lib/ligerUI/js/plugins/ligerTab.js"
	type="text/javascript"></script>
<script type="text/javascript">
	var tab = null;
	$(function() {
		$("#container").ligerLayout({
			leftWidth : 200
		}); //这一句可是关键啊
		
		$("#accordion1").ligerAccordion({
			height : 300
		});
		
		tab = $("#framecentertab").ligerGetTabManager();
		var height = $(".l-layout-center").height();
		$("#framecentertab").ligerTab({
			height: height,
			showSwitchInTab: true, 
            showSwitch: false, 
            dblClickToClose: true, 
            dragToMove: true, 
			contextmenu: true 
		});
	});
	
	/**
	 * 
     * 	添加面板
     * @param :tabid:选项卡标识，text：显示的选项卡文本，url：点击选项卡的链接
     */
     function addTab(tabid,text, url){
    	tab = liger.get("framecentertab");
    	if (!tab) return; 
 		tab.addTabItem({ tabid : tabid,text: text, url: url});
	 }
	   
  	
    
    function setHeader(title){
        tab = liger.get("framecentertab");
        tab.setHeader("home", title);
    }
    
   


	/* //弹出消息提示信息开始
	var tip;
	var num = 0;
	function popupMsg(msg) {
		if (!tip) {
			tip = $.ligerDialog.tip({
				title : '温馨提示',
				content : msg
			});
		} else {
			var visabled = tip.get('visible');
			if (!visabled)
				tip.show();
			tip.set('content', msg);
		}
	}
	//弹出消息提示信息结束 
	
	var flag=false;//true弹出消息，false不弹出
	if(flag){
		window.setInterval(loadingOrderPushLet, 60000); //循环执行，每隔1秒钟（1000）*N执行一次
	}
	*/
	/* function loadingOrderPushLet() {
		$.ajax({
			url : "/app118/loadingOrderAction/loadingOrderPushLet",
			type : "post",
			success : function(msg) {
				var obj = eval('(' + msg + ')');
				var retVal = obj.message;
				if (!(retVal == '' || retVal == null)) {
					//var info="您有"+retVal+"条新<a onclick=\"addTab('添加设备','添加设备','/app118/deviceAction/toAddAirDevice');\" >预约</a>信息。";
					popupMsg(retVal);
					
				}
			}
		});
	} */
	
	function addTabAndCloseTip(tabid,text, url){
    	tab = liger.get("framecentertab");
    	if (!tab) return; 
 		tab.addTabItem({ tabid : tabid,text: text, url: url});
 		closeTip();
	}
	
	function closeTip(){
		var visabled = tip.get('visible');
		if (visabled)
		    tip.hidden();
	}
</script>


<style type="text/css">
*{
	margin: 0px;
	padding: 0px;
}

body {
	padding: 0px;
	margin: 0;
	padding-bottom: 0px;
	padding-left: 0px;
	padding-right: 0px;
	padding-top: 0px;
}

#top {
	height: 102px;
	margin-bottom: 0px;
	/*background-color: #ffffff*/
}
</style>
</head>
<body >
	<!-- 顶部 -->
	<div id="top">
		<iframe id="topFrame" name="topFrame" width="100%" height="102" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" src="/pages/top.jsp"></iframe>
	</div>
	<div id="container" style="width: 99.8%">
		<!-- 左侧开始 -->
		<div position="left" title="功能导航">
			<div id="accordion1" class="liger-accordion" height="100%"
				style="width: 100%;">
				<c:forEach var="topMenu" items="${menuList}">
					<c:if test="${topMenu.menuPid == 0}">
						<div title="${topMenu.menuName}"><!-- 根菜单  -->
								<c:forEach var="subMenu" items="${menuList}">
									<c:if test="${subMenu.menuPid eq topMenu.menuCode}">
										<li style="cursor: pointer;">
											<a  onclick="addTab('${subMenu.menuName}','${subMenu.menuName}','${subMenu.menuPath}');" >${subMenu.menuName}</a>
										</li><!-- 二级菜单 -->
									</c:if>
								</c:forEach>
						</div>
					</c:if>
				</c:forEach>
				
				
				
				

				<!-- <div title="设备中心">
					<ul>
	                    <li><a onclick="addTab('添加设备','添加设备','/app118/deviceAction/toAddAirDevice');">添加设备</a></li>
	                    <li><a onclick="addTab('库存设备','库存设备','/app118/deviceAction/initAirDevice?deviceStatus=1');">库存设备</a></li>
	                    <li><a onclick="addTab('出库设备','出库设备','/app118/deviceAction/initAirDevice?deviceStatus=3');">出库设备</a></li>
	                    <li><a onclick="addTab('已绑定设备','已绑定设','/app118/deviceAction/initAirDevice?deviceStatus=5');">已绑定设备</a></li>
	                    <li><a onclick="addTab('已废弃设备','已废弃设备','/app118/deviceAction/initAirDevice?deviceStatus=7');">已废弃设备</a></li>
	                </ul>
				</div>
				<div title="充值管理">
					<ul>
	                    <li><a href="/app118/cardAction/toAddCard" target="home" onclick="addTab('添加充值卡');">添加充值卡</a></li>
	                    <li><a  onclick="addTab('VIP服务设备','VIP服务设备','/app118/cardAction/initDeviceRecharge?charge=1');">VIP服务设备</a></li>
	                    <li><a  onclick="addTab('免费服务设备','免费服务设备','/app118/cardAction/initDeviceRecharge?charge=0');">免费服务设备</a></li>
	                    <li><a  onclick="addTab('充值记录','充值记录','/app118/deviceRechargeHisAction/initDeviceRechargeHis');">充值记录</a></li>
	                    <li><a  onclick="addTab('批量添加充值卡','批量添加充值卡','/app118/cardAction/toAddBatchCard');">批量添加充值卡</a></li>
	                    <li><a  onclick="addTab('充值卡查询','充值卡查询','/app118/cardAction/initCard');">充值卡查询</a></li>
	                </ul>
				
				</div>
				
				<div title="用户管理">
					  <li><a  onclick="addTab('app用户管理','app用户管理','/app118/userAction/initUser');">app用户管理</a></li>
					  <li><a  onclick="addTab('用户设备绑定管理','用户设备绑定管理','/app118/userAction/initUserDeviceRel');">用户设备绑定管理</a></li>
					  <li><a  onclick="addTab('用户注册','用户注册','/app118/userAction/toAddUser');">用户注册</a></li>
				</div>
				
				<div title="消息管理">
					<li><a  onclick="addTab('消息管理','消息管理','/app118/messageAction/initMessage');">消息管理</a></li>
				
				</div>
				<div title="系统管理">
					<li><a  onclick="addTab('角色管理','角色管理','/app118/roleAction/initRole');">角色管理</a></li>
				
				</div> -->
			</div>
			<div id="leftInfo" height="100%" style="width: 100%;padding-left:18px;padding-right: 18px">
				<marquee direction="left"  scrollamount="2" behavior="scroll">${weather}</marquee>
				${userInfo}
				<br/>
				${orgInfo}
				<br/>
				------------------<br/>
				手机号码：18810790739<br/>
				地址：北京市海淀区志新村小区<br/>
				海泰大厦621室<br/>
				Version： ${vesion}<br/>
				Copyright © 2015<br/> 
				莎琪美妆　版权所有
			</div>
		</div>
		<!-- 左侧结束 -->
		<!-- 内容区域开始 -->
		<div position="center"  id="framecentertab" style="height: 100%;width: 100%;" >
			<div tabid="home" title="主页" style="height: 100%;width: 100%;"  >
				<iframe frameborder="0" name="home" id="home" height="100%" width="100%" scrolling="no"  src="/pages/welcome.jsp?weather=${weather}"/>
			</div>
		</div>
		
		<!-- 内容区域结束 -->
	</div>
</body>
</html>