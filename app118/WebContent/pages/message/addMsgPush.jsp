<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息推送</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerComboBox.js"></SCRIPT>
</head>
<body onload="tips();">
<form action="" method="post">
	<table border="0" align="center">
	
		<tr>
			<td  class="tdtitle">消息类型：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="hidden" name="loginNames" id="loginNames" value="${loginNames}" style="width: 252px;height: 20px;">
				<input type="hidden" name="receiverIds" id="receiverIds" value="${userIds}"  style="width: 252px;height: 20px;">
				<input type="hidden" name="realNames" id="realNames" value="${realNames}"  style="width: 252px;height: 20px;">
				
				<select name="msgType" id="msgType"  style="width: 252px;height: 20px;">
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${smsTypeMap}">
						<option value="${oneMap.key }" <c:if test="${oneMap.key eq '${msgType}'}">selected</c:if> >${oneMap.value}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">消息内容：</td>
			<td  nowrap="nowrap">
				&nbsp;<textarea rows="8" cols="29" name="msgContent" id="msgContent"></textarea><font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">发送方式：</td>
			<td  nowrap="nowrap">
				<input type="checkbox" name="sendTypeCheckbox"  value="push" checked="checked">推送
				<input type="checkbox" name="sendTypeCheckbox" value="sms">短信 
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
		var url = "/app118/messageAction/addMsgPush";
		$("#submitBtn").click(function() {
			var msgType=$("#msgType").val();
			if(msgType==null||trim(msgType)==''){
				$.ligerDialog.warn("请选择消息类型。");
				$("#msgType").focus();
				return;
			}
			var msgContent=$("#msgContent").val();
			if(msgContent==null||trim(msgContent)==''){
				$.ligerDialog.warn("消息内容不能为空。");
				$("#msgContent").focus();
				return;
			}else if(msgContent.length>512){
				$.ligerDialog.warn("消息内容不能超过512字符。");
				$("#msgContent").focus();
				return;
			}
			
			var chk_value =[];    
			$('input[name="sendTypeCheckbox"]:checked').each(function(){    
			 	chk_value.push($(this).val());    
			});   
			if(chk_value.length==0){
				$.ligerDialog.warn("请选择发送方式。");
				$("#sendTypeCheckbox").focus();
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
				$.ligerDialog.alert("发送成功。",function(){
					parent.closeDialog();	
				});
			}else{
				$.ligerDialog.alert("发送失败。",function(){
					parent.closeDialog();	
				});
			}
		}
	}		
	
</script>
</html>