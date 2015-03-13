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
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerComboBox.js"></SCRIPT>
</head>
<body onload="tips();">
<form action="" method="post">
	<table border="0" align="center">
		<tr>
			<td  class="tdtitle">消息主题：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="msgTitle"  id="msgTitle" style="width: 252px;height: 20px;"/><font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">消息类型：</td>
			<td class="tdtext" nowrap="nowrap">
				<select name="msgType" id="msgType"  style="width: 252px;height: 20px;">
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${msgTypeMap}">
						<option value="${oneMap.key }" <c:if test="${oneMap.key eq '${msgType}'}">selected</c:if> >${oneMap.value}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">接收人：</td>
			<td  nowrap="nowrap">
				<textarea rows="6" cols="30" name="receiverName" id="receiverName" readonly="readonly"></textarea><font color="red">*</font>
				<input type="hidden" name="receiverIds" id="receiverIds">
				<input type="button" value="选择用户" id="selectUsers" class="l-button l-button-submit" style="vertical-align: center;"/> 
			</td>
		</tr>
		
		<tr>
			<td class="tdtitle">消息内容：</td>
			<td nowrap="nowrap">
				<textarea rows="8" cols="30" name="msgContent" id="msgContent"></textarea><font color="red">*</font>
			</td>
		</tr>
		<tr align="center">
			<td colspan="2" > 
				<input type="button" value="确认发送" id="submitBtn" class="l-button l-button-submit"/> 
			</td>
		</tr>
	</table>
</form>
</body>
<script language="javascript">
	$(function() {
		var url = "/app118/messageAction/addMessage";
		$("#submitBtn").click(function() {
			
			var msgTitle=$("#msgTitle").val();
			if(msgTitle==null||trim(msgTitle)==''){
				$.ligerDialog.warn("消息主题不能为空。");
				$("#msgTitle").focus();
				return;
			}
			var msgType=$("#msgType").val();
			if(msgType==null||trim(msgType)==''){
				$.ligerDialog.warn("请选择消息类型。");
				$("#msgType").focus();
				return;
			}
			var receiverIds=$("#receiverIds").val();
			if(receiverIds==null||trim(receiverIds)==''){
				$.ligerDialog.warn("请选择接收人。");
				$("#receiverName").focus();
				return;
			}
			var msgContent=$("#msgContent").val();
			if(msgContent==null||trim(msgContent)==''){
				$.ligerDialog.warn("消息内容不能为空。");
				$("#msgContent").focus();
				return;
			}
			
			document.forms[0].action = url;
	 		document.forms[0].submit();	
		});
		
		$("#selectUsers").click(function(){
			var url ="/app118/userAction/initSelectUser";
	  		dialog=$.ligerDialog.open({ 
	  				  url:url, 
	  				  height:420,
	  				  isResize:true,
	  				  width: 550, 
	  				  title: '选择用户',
		  			  userId: $("#receiverIds").val()
	  		});
			
		});

	});
	

	//提示信息
	function tips(){
		var msg ="${message}";
		if(!(msg=='null'||msg=='')){
			if(msg == "success"){
				$.ligerDialog.alert("发送消息成功。",function(){
					parent.closeDialog();	
				});
			}else{
				$.ligerDialog.alert("发送消息失败。",function(){
					parent.closeDialog();	
				});
			}
		}
	}		
	
	
	 //关闭对话框
 	function closeDialog(userIds,realNames){
 		var currentReceiverIds=$("#receiverIds").val();
 		var currentReceiverNames=$("#receiverName").val();
 		if("null"==userIds.replace(/^\s+|\s+$/g,"")){//去空格
 			$("#receiverIds").val("");
 			$("#receiverName").val("");
 		}else if("sendAll"==userIds.replace(/^\s+|\s+$/g,"")){
 			$("#receiverName").val("所有用户");
 			$("#receiverIds").val("0");
 		}else{
	 		if(currentReceiverIds.length<1){
	 			currentReceiverIds+=userIds;
		 		currentReceiverNames+=realNames;
	 		}else{
	 			currentReceiverIds=currentReceiverIds+","+userIds;
		 		currentReceiverNames=currentReceiverNames+","+realNames;
	 		}
	 		$("#receiverIds").val(filterRepeatStr(currentReceiverIds));
	 		$("#receiverName").val(filterRepeatStr(currentReceiverNames));
 		}
 		dialog.hide();
 	}
</script>
</html>