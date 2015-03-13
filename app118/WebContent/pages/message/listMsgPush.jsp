<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发送消息</title>
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
					所属组织机构：
				</td>
				<td>
					<select name="orgId" id="orgId" style="width: 128px;height: 20px;" ${disabled} >
						<option value="">---请选择---</option>
						<c:forEach var="oneMap" items="${orgMap}">
							<option value="${oneMap.key }" <c:if test="${orgId==oneMap.key }">selected </c:if> >${oneMap.value}</option>
						</c:forEach>
					</select>
				</td>
				<td class="tdtitle">用户最后访问时间：</td>
				<td class="tdtext" nowrap="nowrap">
					<input type="text" name="lastLoginTime" value="${lastLoginTime}" id="lastLoginTime"/>
				</td>
			</tr>
			
			
			
			<tr>
				<td class="tdtitle">
					用户姓名：
				</td>
				<td class="tdtext">
					<input type="text"  name="realName" id="realName" value="${realName}"  style="width: 128px;height: 20px;"/>
				</td>
				<td class="tdtitle">用户电话：</td>
				<td class="tdtext">
					<input type="text" name="loginName" id="loginName" value="${loginName}"  style="width: 128px;height: 20px;"/>
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
		<div class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon"
			toolbarid="item-1" jQuery1405912963467="22" onclick="toAddMsgPush();">
			<SPAN>发送消息</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-add"></div>
		</div>
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-2"
			jQuery1405912963467="23" onclick="sendSmsByPhone();">
			<SPAN>&nbsp;直发手机</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-comment"></div>
		</div>
	</div>
	<!-- 工具栏结束 -->
	<div id="maingrid" ></div>
	 
<script type="text/javascript">
	var dialog=null;//弹出对话框
     var url = "/app118/messageAction/listMsgPushByPager";
     $(function (){
     	init(url+"?orgId=${orgId}");
     	$("#lastLoginTime").ligerDateEditor({
			showTime : true,
			labelWidth : 100,
			labelAlign : 'left'
		});
     	
     });
     
     //重置条件
     function forClean(){
    	$("#saleUserId").val('');
     	$("#lastLoginTime").val('');
     	$("#realName").val('');
     	$("#loginName").val('');
      	
     	
     }
     
   	 //查询消息
   	 function forSearch(){
   		var orgId=$("#orgId").val();
   		var lastLoginTime=$("#lastLoginTime").val();
     	var realName=$("#realName").val();
   		var loginName=$("#loginName").val();
   		if("---请选择---"==saleUserId){
   			saleUserId="";
   		}
     	
   	    var params="?orgId="+orgId+"&lastLoginTime="+encodeURI(lastLoginTime)+"&realName="+encodeURI(realName)+"&loginName="+loginName;
   	    init(url+params); 
   	 }
     
   	 //发送消息
   	 function toAddMsgPush(){
   		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行。");
			 return;
		}else{
			 var userIds="";
			 var realNames="";
			 var loginNames="";
			 
			 var len=rowid.length;
			 for(var j=0;j<len;j++){
			   if(j!=len-1){
				   userIds+=rowid[j].userId+",";			
				   realNames+=rowid[j].realName+",";			
				   loginNames+=rowid[j].loginName+",";			
				}else{
					userIds+=rowid[j].userId;
					realNames+=rowid[j].realName
					loginNames+=rowid[j].loginName
				}
			 }
		}
   		
   		var url ="/app118/messageAction/toAddMsgPush?userIds="+userIds+"&realNames="+encodeURI(realNames)+"&loginNames="+loginNames;
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:520,
  				  isResize:false,
  				  width: 650, 
  				  title: '发送消息'
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
		  	          { display: '所属组织机构', name: 'orgName'},
		  	          { display: '用户姓名', name: 'realName'},
	  	              { display: '用户手机号', name: 'loginName'},
	  	              { display: '用户最后访问时间', name: 'lastLoginTime',width:'14%',render:function(row){
	                	var html="<font color=\""+row.lastLoginTimeColor+"\">"+row.lastLoginTime+"</font>";
	                	return html;
                	    }
                	  },
	  	             
	  	          
	  	              /* ,
	  	              { display: '查看', name: '查看', render: function (row){
			             	var parms="\""+row.msgId+"\""+","+"\""+row.msgSendId+"\""+","+"\""+row.realName+"\"";  
			             	var html="<a href='javascript:viewMessage("+parms+");'>查看</a>";
			             	
			             	return html;
			              }
	  	              } */
  	              ], url:url,dataAction:"server" , pageSize:15 ,rownumbers:true,pageParmName:"curNo",pagesizeParmName:"curSize"
  	          });
  		
  	    $("#pageloading").hide();
  	}
  
</script>
</body>
</html>