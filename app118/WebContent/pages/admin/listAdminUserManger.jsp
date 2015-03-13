<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>店长修改页面--app用户列表管理</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<script src="/resource/lib/ligerUI/js/plugins/ligerGrid.js"
	type="text/javascript"></script>
<LINK rel=stylesheet type=text/css
	href="/resource/lib/ligerUI/skins/ligerui-icons.css">
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerToolBar.js"></SCRIPT>
<SCRIPT type=text/javascript src="/resource/lib/ligerUI/js/plugins/ligerMenu.js"></SCRIPT> 
<SCRIPT type=text/javascript src="/resource/lib/ligerUI/js/plugins/ligerMenuBar.js"></SCRIPT> 
<STYLE type=text/css>
    #menu1,.l-menu-shadow{top:30px; left:50px;} 
    #menu1{  width:200px;} 
</STYLE> 
</head>
<body>
	<div class="l-form">
		<form action="" id="form1" method="post">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td class="tdtitle">
					用户名：
				</td>
				<td class="tdtext">
					<input type="text" name="loginName" value="${loginName}" id="loginName" style="width: 128px;height: 20px;"/>
				</td>
				<td class="tdtitle">姓名：</td>
				<td class="tdtext">
					<input type="text" name="realName" value="${realName}" id="realName" style="width: 128px;height: 20px;"/>
				</td>
			</tr>
			<tr>
				<td class="tdtitle">汽车牌号：</td>
				<td class="tdtext">
					<input type="text" name="deviceName" value="${deviceName}" id="deviceName" style="width: 128px;height: 20px;"/>
				</td>
				<td class="tdtitle">服务类型：</td>
				<td class="tdtext">
					<select name="cardType" id="cardType"  style="width: 128px;height: 20px;">
						<option value="">---请选择---</option>
						<c:forEach var="oneMap" items="${cardTypeMap}">
							<option value="${oneMap.key }" <c:if test="${oneMap.key eq cardType}">selected</c:if> >${oneMap.value}服务</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td class="tdtitle">
					首安装时间从：
				</td>
				<td>
					<input type="text" name="fromCreateTime" value="${fromCreateTime}" id="fromCreateTime"/>
				</td>
				<td class="tdtitle">至：</td>
				<td>
					<input type="text" name="toCreateTime" value="${toCreateTime}" id="toCreateTime"/>
				</td>
			</tr>
			<tr>
				<td class="tdtitle">
					所属组织机构：
				</td>
				<td>
					<select name="orgId" id="orgId" style="width: 128px;height: 20px;" ${disabled}>
						<option value="">---请选择---</option>
						<c:forEach var="oneMap" items="${orgMap}">
							<option value="${oneMap.key }" <c:if test="${orgId==oneMap.key }">selected </c:if> >${oneMap.value}</option>
						</c:forEach>
					</select>
				</td>
				<td class="tdtitle">&nbsp;</td>
				<td class="tdtext">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td  align="right">
					<ul class="l-form-buttons" >
						<li onclick="forSearch()">
							<div class="l-button" style="width: 60px;"
								ligeruiid="Button1001">
								<div class="l-button-l"></div>
								<div class="l-button-r"></div>
								<span>查询</span>
							</div>
						</li>
						<li onclick="forClean();">
							<div class="l-button" style="width: 60px;"
								ligeruiid="Button1002">
								<div class="l-button-l"></div>
								<div class="l-button-r"></div>
								<span>重置</span>
							</div>
						</li>
					</ul>
					
				</td>
			</tr>
		</table>
		</form>
	</div>
	
	
	
	<div class="l-loading" style="display:block" id="pageloading"></div>
	<div class="l-clear"></div>
	
	<!-- 工具栏 -->
	<div id="toptoolbar" class="l-toolbar" ligeruiid="toptoolbar" style="width:99%;">
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-2"
			jQuery1405912963467="23" onclick="updUser();">
			<SPAN>&nbsp;修改客户信息</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-modify"></div>
		</div>
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-2"
			jQuery1405912963467="23" onclick="alterOrgId();">
			<SPAN>&nbsp;客户转移</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-communication"></div>
		</div>
	</div>
	<!-- 工具栏结束 -->
	<div id="maingrid" ></div>
	 
<script type="text/javascript">
	var dialog=null;//弹出对话框
     var url = "/app118/adminUserMangerAction/listAdminUserManger";
     $(function (){
     	init(url+"?orgId=${orgId}");
     	
     	$("#fromCreateTime").ligerDateEditor({
			showTime : true,
			labelWidth :200,
			labelAlign : 'left'
		});
     	$("#toCreateTime").ligerDateEditor({
			showTime : true,
			labelWidth : 100,
			labelAlign : 'left'
		});
     	
     });
     
     //重置条件
     function forClean(){
     	$("#loginName").val('');
     	$("#realName").val('');
     	$("#deviceName").val('');
     	$("#cardType").val('');
     	$("#fromCreateTime").val('');
     	$("#toCreateTime").val('');
     }
   	 //查询
   	 function forSearch(){
   		var loginName=$("#loginName").val();
   		var realName=$("#realName").val();
   		var deviceName=$("#deviceName").val();
   		var cardType=$("#cardType").val();
   		var fromCreateTime= $("#fromCreateTime").val();
     	var toCreateTime=$("#toCreateTime").val();
     	var orgId=$("#orgId").val();
   	    var params="?loginName="+loginName+"&realName="+encodeURI(realName)+"&deviceName="+encodeURI(deviceName)+"&cardType="+cardType+"&fromCreateTime="+fromCreateTime+"&toCreateTime="+toCreateTime+"&orgId="+orgId;
   	    init(url+params); 
   	 }
     

   	 //修改app用户信息
   	 function updUser(){
   		var params;
   		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行。");
			 return;
		}else{
		 
		 var len=rowid.length;
			 if(len>1){
			 	$.ligerDialog.warn("请只选择一行。");
				return;
			 }else{
				var userId=rowid[0].userId;
				var deviceId=rowid[0].deviceId;
				var rechargeId=rowid[0].rechargeId;
				var hepaId=rowid[0].hepaId;
				params="?userId="+userId+"&deviceId="+deviceId+"&rechargeId="+rechargeId+"&hepaId="+hepaId;

			 }
		}
   		var url ="/app118/adminUserMangerAction/toUpdUserAll"+params;
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:540,
  				  isResize:true,
  				  width: 650, 
  				  title: '修改客户信息'
  		}); 
   	 }
   	
   	 //用户转移--修改销售人员或所属门店
   	 function alterOrgId(){
   		var params;
   		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行。");
			 return;
		}else{
			 var len=rowid.length;
			 var userIds="";
			 var deviceIds="";
			 
			 var deviceNames="";
			 
			 for(var j=0;j<len;j++){
			   if(j!=len-1){
				   userIds+=rowid[j].userId+",";			
				   deviceIds+=rowid[j].deviceId+",";			
				   deviceNames+=rowid[j].deviceName+",";			
				}else{
					userIds+=rowid[j].userId;
					deviceIds+=rowid[j].deviceId;
					deviceNames+=rowid[j].deviceName;
				}
			 }
			params="?userIds="+userIds+"&deviceIds="+deviceIds+"&deviceNames="+encodeURI(deviceNames);
		}
   		var url ="/app118/adminUserMangerAction/toUpdAdminUserOrgId"+params;
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:340,
  				  isResize:true,
  				  width: 450, 
  				  title: '客户转移'
  		}); 
   	 }
   	
   
   
   	 
    //关闭对话框
 	function closeDialog(){
 		dialog.hide();
 		g.loadData();
 	}
  
     
  	/**
  	*初始化
  	*/	
  	function init(url){
  		var data = {};
  		/**
  		* 加载Grid数据并显示
  		*/
  		window['g']= $("#maingrid").ligerGrid({
  	        	  width: '99%',
            	  height:'99%',
  	      	      rowHeight:26,
  	      	   	  checkbox:true,
  	              headerRowHeight:28,
  	              dateFormat: "yyyy-MM-dd hh:mm:ss",
  	              columns: [
		  	          { display: '用户标识', name: 'userId', width: '5%'},
	  	              { display: '所属组织机构', name: 'orgName', width: '10%' },
	  	              { display: '姓名', name: 'realName', width: '10%' },
	  	              { display: '性别', name: 'sex', width: '5%' },
	  	              { display: '用户名', name: 'loginName', width: '10%'},
	  	              { display: '汽车品牌', name: 'carBrandCn', width: '10%'},
	  	              { display: '汽车系列', name: 'carSeriesCn', width: '10%'},
	  	              { display: '汽车型号', name: 'carTypeCn', width: '10%'},
	  	              { display: '汽车款式', name: 'carStyle', width: '10%'},
	  	              { display: '汽车年份', name: 'carYear', width: '10%'},
	  	              { display: '汽车牌号', name: 'deviceName', width: '10%'},
	  	              { display: '控制器MAC', name: 'deviceMac', width: '10%'},
	  	              { display: '首安装时间', name: 'installTime', width: '13%',type: "date", format:"yyyy-mm-dd HH:mm:ss"},
	  	              { display: '充值卡类型', name: 'cardTypeCn', width: '10%'},
	  	              { display: '实收金额(元)', name: 'paidAmount', width: '10%',type:'currency'},
	  	              { display: '过滤器方案', name: 'hepaStatusCn', width: '10%'},
	  	              { display: '实收金额(元)', name: 'realPrice', width: '10%',type:'currency'}
  	              ], url:url,dataAction:"server" , pageSize:15 ,rownumbers:true,pageParmName:"curNo",pagesizeParmName:"curSize"
  	          });
  		
  	    $("#pageloading").hide();
  	}
  	
  	/**
	* 价格格式化，小数点后留2位
	*
	* num 当前的值  column 列信息
	*/
	$.ligerDefaults.Grid.formatters['currency'] = function (num, column) { 
	  	if (!num) return "￥0.00"; 
	  	num = num.toString().replace(/\$|\,/g, ''); 
	  	if (isNaN(num)) num = "0.00"; 
	  	sign = (num == (num = Math.abs(num))); 
	  	num = Math.floor(num * 100 + 0.50000000001); 
	  	cents = num % 100; 
	  	num = Math.floor(num / 100).toString(); 
	  	if (cents < 10) cents = "0" + cents; 
	  	for(var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++) 
	  	num = num.substring(0, num.length - (4 * i + 3)) + ',' + 
	  	num.substring(num.length - (4 * i + 3)); 
	  	return "￥" + (((sign) ? '' : '-') + '' + num + '.' + cents); 
	}; 
</script>
</body>
</html>