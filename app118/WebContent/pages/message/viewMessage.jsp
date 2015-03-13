<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看消息明细</title>
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
				${msgMap.msgTitle}
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">消息类型：</td>
			<td class="tdtext" nowrap="nowrap">
				${msgMap.msgType}
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">接收姓名：</td>
			<td  nowrap="nowrap" class="tdtext">
				${msgMap.receiverName}
			</td>
		</tr>
		
		
		<tr>
		<td class="tdtitle">发送时间：</td>
		<td class="tdtext" nowrap="nowrap">
			${msgMap.createTime}
		</td>
		</tr>
		<tr>
			<td class="tdtitle">消息状态：</td>
			<td class="tdtext" nowrap="nowrap">
				${msgMap.status}
			</td>
		</tr>
		<tr>
			<td class="tdtitle">消息内容：</td>
			<td nowrap="nowrap">
				<textarea rows="6" cols="40" readonly="readonly">${msgMap.msgContent}</textarea>
				
			</td>
		</tr>
		<tr align="center">
			<td colspan="2" > 
				<input type="button" value="关闭" id="submitBtn" class="l-button l-button-submit"/> 
			</td>
		</tr>
	</table>
</form>
</body>
<script language="javascript">
	$(function() {
		var url = "/app118/messaageAction/addMessage";
		$("#submitBtn").click(function() {
			
			parent.closeDialog();	

		});

	});
	
	//提示信息
	function tips(){
		var msg ="${message}";
		if(!(msg=='null'||msg=='')){
			if(msg == "success"){
				//alert("操作成功！");
				$.ligerDialog.alert("操作成功！");
			}else{
				$.ligerDialog.alert("操作失败！");
				//alert("操作失败！");
			}
			parent.closeDialog();
		}
	}	
	
</script>
</html>