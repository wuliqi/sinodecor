<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信管理</title>
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
			jQuery1405912963467="23" onclick="sendSms();">
			<SPAN>&nbsp;发送短信</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-modify"></div>
		</div>
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-2"
			jQuery1405912963467="23" onclick="sendSmsByPhone();">
			<SPAN>&nbsp;直发手机</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-comment"></div>
		</div>
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon"
			toolbarid="item-1" jQuery1405912963467="22" onclick="exportExcelAll('all');">
			<SPAN>导出查询部分</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-ok"></div>
		</div>
	</div>
	<!-- 工具栏结束 -->
	<div id="maingrid" ></div>
	 
<script type="text/javascript">
	var dialog=null;//弹出对话框
     var url = "/app118/adminUserMangerAction/listAdminUserManger";
     $(function (){
     	init(url+"?orgId=${orgId}");
     	
     	/* $("#fromCreateTime").ligerDateEditor({
			showTime : true,
			labelWidth :200,
			labelAlign : 'left'
		});
     	$("#toCreateTime").ligerDateEditor({
			showTime : true,
			labelWidth : 100,
			labelAlign : 'left'
		}); */
     	
     });
     
     //重置条件
     function forClean(){
     	$("#loginName").val('');
     	$("#realName").val('');
     
     	//$("#fromCreateTime").val('');
     	//$("#toCreateTime").val('');
     }
   	 //查询
   	 function forSearch(){
   		var loginName=$("#loginName").val();
   		var realName=$("#realName").val();
   		
   		//var fromCreateTime= $("#fromCreateTime").val();
     	//var toCreateTime=$("#toCreateTime").val();
     	var orgId=$("#orgId").val();
   	    var params="?loginName="+loginName+"&realName="+encodeURI(realName)+"&orgId="+orgId;//+"&fromCreateTime="+fromCreateTime+"&toCreateTime="+toCreateTime
   	    init(url+params); 
   	 }
     

   	 //发送短信
   	 function sendSms(){
   		var params;
   		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行。");
			 return;
		}else{
			
			var userIds="";//用户标识
			var loginNames="";//登录名，即手机号码
			var len=rowid.length;
			for(var j=0;j<len;j++){
			   if(j!=len-1){
				   userIds+=rowid[j].userId+",";			
				   loginNames+=rowid[j].loginName+",";			
				}else{
					userIds+=rowid[j].userId;
					loginNames+=rowid[j].loginName;
				}
			 }
			params="?userIds="+userIds+"&loginNames="+loginNames;
			
		}
   		var url ="/app118/messageAction/toSendSms"+params;
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:540,
  				  isResize:true,
  				  width: 650, 
  				  title: '短信发送'
  		}); 
   	 }
   	
   	//发送手机短信  直发手机
   	function sendSmsByPhone(){
   		var url ="/app118/messageAction/toSendSmsByPhone";
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:540,
  				  isResize:true,
  				  width: 650, 
  				  title: '直发手机'
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
		  	          //{ display: '用户标识', name: 'userId', width: '5%'},
	  	              { display: '所属组织机构', name: 'orgName', width: '10%' },
	  	              { display: '姓名', name: 'realName', width: '10%' },
	  	              { display: '性别', name: 'sex', width: '5%' },
	  	              { display: '用户名', name: 'loginName', width: '10%'},
	  	              { display: '短信记录', name: '查看', width: '8%',render: function (row){
			             	var parms="\""+row.userId+"\""+","+"\""+row.realName+"\"";
			             	var html="<a href='javascript:viewSms("+parms+");'>查看</a>";
			             	
			             	return html;
			              }
	  	              }
  	              ], url:url,dataAction:"server" , pageSize:15 ,rownumbers:true,pageParmName:"curNo",pagesizeParmName:"curSize"
  	          });
  		
  	    $("#pageloading").hide();
  	}
  	
  	
  	//短信记录 
  	function viewSms(userId,realName){
	  	var url="/app118/messageAction/initMessage?receiverId="+userId+"&receiverName="+encodeURI(realName) ;
	  	if("undefined"==userId){
	  		$.ligerDialog.warn("没有短信记录 。");
	  		return;
	  	}else{
	  		dialog=$.ligerDialog.open({ 
				  url:url, 
				  height:540,
				  isResize:true,
				  width: 1024, 
				  title: '查看短信记录 '
			}); 
	  	}
  	}
  	
    //导出Excel
	function exportExcelAll() {
		var url = "/app118/adminUserMangerAction/listAdminUserMangerExportExcel";
		var loginName=$("#loginName").val();
   		var realName=$("#realName").val();
   		
   		//var fromCreateTime= $("#fromCreateTime").val();
     	//var toCreateTime=$("#toCreateTime").val();
     	var orgId=$("#orgId").val();
     	//"&fromCreateTime="+fromCreateTime+"&toCreateTime="+toCreateTime+
   	    var params="?loginName="+loginName+"&realName="+encodeURI(realName)+"&orgId="+orgId;
		$.ligerDialog.confirm('确认要导出查询到的所有信息吗？', function(yes) {
			if (yes) {
				window.location.href = url+params;
			} else {
				g.loadData();
			}
		});
		
	}
</script>
</body>
</html>