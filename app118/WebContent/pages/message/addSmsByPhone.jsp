<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>直接发送手机短信</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerComboBox.js"></SCRIPT>
</head>
<body onload="tips();">
<form action="" method="post">
	<table border="0" align="center">
		<tr>
			<td width="20%">手机号码：</td>
			<td nowrap="nowrap" width="80%">
				<textarea rows="8" cols="29"  name="phoneNumbers" id="phoneNumbers" ></textarea><font color="red">*</font>
				<br/>多个手机号请用“,”分隔
			</td>
		</tr>
		 <tr style="display: none">
			<td   width="20%">短信种类：</td>
			<td nowrap="nowrap" width="80%">
				<select name="msgType" id="msgType"  style="width: 252px;height: 20px;" disabled="disabled">
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${smsTypeMap}">
						<option value="${oneMap.key }" <c:if test="${oneMap.key eq '1408'}">selected</c:if> >${oneMap.value}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
		</tr> 
		<tr>
			<td >短信内容：</td>
			<td  nowrap="nowrap">
				<textarea rows="8" cols="29" name="msgContent" id="msgContent"> 【每日互联】</textarea><font color="red">*</font>
				<br/>提交信息末尾加签名，如企业签名【每日互联】
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
		var url = "/app118/messageAction/addSmsByPhone";
		$("#submitBtn").click(function() {
			var phoneNumbers=$("#phoneNumbers").val();
			if(phoneNumbers==null||trim(phoneNumbers)==''){
				$.ligerDialog.warn("手机号不能为空。");
				$("#phoneNumbers").focus();
				return;
			}else{
				var loginName="";
				var loginNames=phoneNumbers.split(","); 
				for(var i=0;i<loginNames.length ;i++ ){
					loginName=loginNames[i];
					if(!checkPhone(loginName)){
						$.ligerDialog.warn(loginName+"，手机号码不正确。");
						$("#phoneNumbers").focus();
						return;
					}
				} 
			}
			
			
			/* var msgType=$("#msgType").val();
			if(msgType==null||trim(msgType)==''){
				$.ligerDialog.warn("请选择短信种类。");
				$("#msgType").focus();
				return;
			} */
			var msgContent=$("#msgContent").val();
			if(msgContent==null||trim(msgContent)==''){
				$.ligerDialog.warn("短信内容不能为空。");
				$("#msgContent").focus();
				return;
			}else if(msgContent.length>512){
				$.ligerDialog.warn("短信内容不能超过512字符。");
				$("#msgContent").focus();
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
				$.ligerDialog.alert("发送短信成功。",function(){
					parent.closeDialog();	
				});
			}else{
				$.ligerDialog.alert("发送短信失败。",function(){
					parent.closeDialog();	
				});
			}
		}
	}		
	
</script>
</html>