<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息管理</title>
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
					接收人姓名：
				</td>
				<td class="tdtext">
					<input type="hidden" name="receiverId" value="${receiverId}" id="receiverId" style="width: 128px;height: 20px;"/>
					<input type="text"  name="receiverName" id="receiverName" value="${receiverName}" readonly="readonly"  style="width: 128px;height: 20px;"/>
			
				
				</td>
				<td class="tdtitle">消息主题：</td>
				<td class="tdtext">
					<input type="text" name="msgTitle" value="${msgTitle}" id="msgTitle" style="width: 128px;height: 20px;"/>
				</td>
			</tr>
			<tr>
				<td class="tdtitle">
					消息类型：
				</td>
				<td class="tdtext">
					<select name="msgType" id="msgType"  style="width: 128px;height: 20px;">
						<option value="">---请选择---</option>
						<c:forEach var="oneMap" items="${msgTypeMap}">
							<option value="${oneMap.key }" <c:if test="${oneMap.key eq '${msgType}'}">selected</c:if> >${oneMap.value}</option>
						</c:forEach>
					</select>
				</td>
				<td class="tdtitle">消息状态：</td>
				<td class="tdtext">
					<select name="status" id="status"  style="width: 128px;height: 20px;">
						<option value="">---请选择---</option>
						<c:forEach var="oneMap" items="${msgStatusMap}">
							<option value="${oneMap.key }" <c:if test="${oneMap.key eq '${msgType}'}">selected</c:if> >${oneMap.value}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td class="tdtitle">
					发送时间从：
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
		<div class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon"
			toolbarid="item-1" jQuery1405912963467="22" onclick="addMessage();">
			<SPAN>推送消息</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-add"></div>
		</div>
		<!--
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-3"
			jQuery1405912963467="24" onclick="delMessage();">
			<SPAN>&nbsp;删除</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-delete"></div>
		</div> -->
	</div>
	<!-- 工具栏结束 -->
	<div id="maingrid" ></div>
	 
<script type="text/javascript">
	var dialog=null;//弹出对话框
     var url = "/app118/messageAction/listMessageByPager";
     $(function (){
     	init(url+"?receiverId=${receiverId}&orgId=${orgId}");
     	
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
     	$("#receiverId").val('');
     	$("#receiverName").val('');
     	$("#msgTitle").val('');
     	$("#msgType").val('');
     	$("#status").val('');
     	$("#fromCreateTime").val('');
     	$("#toCreateTime").val('');
     }
     
   	 //查询消息
   	 function forSearch(){
   		var receiverId=$("#receiverId").val();
   		var msgTitle=$("#msgTitle").val();
   		var msgType=$("#msgType").val();
   		var status=$("#status").val();
   		var fromCreateTime= $("#fromCreateTime").val();
     	var toCreateTime=$("#toCreateTime").val();
     	var orgId=$("#orgId").val();
     	
   	    var params="?receiverId="+receiverId+"&msgTitle="+encodeURI(msgTitle)+"&fromCreateTime="+
   	    		fromCreateTime+"&toCreateTime="+toCreateTime+"&msgType="+msgType+"&status="+status+"&orgId="+orgId;
   	    init(url+params); 
   	 }
     
   	 //推送消息
   	 function addMessage(){
   		var url ="/app118/messageAction/toAddMessage";
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:520,
  				  isResize:false,
  				  width: 650, 
  				  title: '推送消息'
  		}); 
   	 }
   	 
 
   	
   	 //删除消息
//    	 function delMessage(){
//    		var rowid=g.getSelecteds();//获得选中行ID
// 		if(rowid.length==0){
// 			 $.ligerDialog.warn("请至少选择一行");
// 			 return;
// 		}else{
// 		 var userIds="";
// 		 var len=rowid.length;
// 		 for(var j=0;j<len;j++){
// 		   if(j!=len-1){
// 			   userIds+=rowid[j].userId+",";			
// 			}else{
// 				userIds+=rowid[j].userId;
// 			}
// 		 }
// 		 var url="/app118/userAction/delUser";
// 		  $.ligerDialog.confirm('确认删除选择的用户吗？',function(yes){
// 			  if(yes){
// 		      		$.ajax({
// 			      			type:'POST',
// 							url:url,
// 							data:{userIds:userIds},
// 							dataType:'json',
// 			      			success:function(msg){
// 			      				if(msg.flag=1){
// 			      					$.ligerDialog.success("删除成功！");
// 			      					g.loadData();
// 			      				}else{
// 			      					$.ligerDialog.error("删除失败！");
// 			      				}
// 			      			},
// 			      			error:function(){
// 			      				$.ligerDialog.error("操作失败！");
// 			      			}
// 			      		});
// 				}else{
// 				   g.loadData();
// 				}
// 			})
// 		} 
//    	 }
   	 
   	 //查看消息明细
   	 function viewMessage(msgId,msgSendId,receiverName,msgTypeCn){
   		var url ="/app118/messageAction/viewMessage?msgId="+msgId+"&msgSendId="+
   				msgSendId+"&receiverName="+encodeURI(receiverName)+"&msgTypeCn="+encodeURI(msgTypeCn);
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:520,
  				  isResize:true,
  				  width: 650, 
  				  title: '查看消息明细'
  		}); 
   	 }
   	 
    //关闭对话框
 	function closeDialog(){
 		dialog.hide();
 		g.loadData();
 	}
  
 	 //关闭对话框关设值
 	function closeDialogSelect(userId,realName){
	 	$("#receiverId").val(userId);
	 	$("#receiverName").val(realName);
 		dialog.hide();
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
  	              columns: [
		  	          { display: '所属组织机构', name: 'orgName', width: '10%'},
	  	              { display: '接收人姓名', name: 'receiverName', width: '7%' },
		  	          { display: '消息主题', name: 'msgTitle', width: '18%'},
		  	          { display: '消息内容', name: 'msgContent', width: '32%'},
	  	              { display: '消息类型', name: 'msgTypeCn', width: '8%'},
	  	              { display: '状态', name: 'status', width: '6%' },
	  	              { display: '发送时间', name: 'msgCreateTime', width: '13%'},
	  	              { display: '查看', name: '查看', width: '5%',render: function (row){
			             	var parms="\""+row.msgId+"\""+","+"\""+row.msgSendId+"\""+","+"\""+row.receiverName+"\""+","+"\""+row.msgTypeCn+"\"";  
			             	var html="<a href='javascript:viewMessage("+parms+");'>查看</a>";
			             	
			             	return html;
			              }
	  	              }
  	              ], url:url,dataAction:"server" , pageSize:15 ,rownumbers:true,pageParmName:"curNo",pagesizeParmName:"curSize"
  	          });
  		
  	    $("#pageloading").hide();
  	}
  	
  	//选择用户
  	$("#receiverName").click(function(){
		var url ="/app118/userAction/initSelectUserRadio";
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:420,
  				  isResize:true,
  				  width: 550, 
  				  title: '选择用户',
	  			  userId: $("#receiverIds").val()
  		});
		
	});
</script>
</body>
</html>