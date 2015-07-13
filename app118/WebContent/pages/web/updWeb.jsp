<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改网址</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
</head>
<body onload="tips();">
<form action="" method="post">
	<table border="0" align="center">
		<tr>
			<td  class="tdtitle">名称：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="hidden" name="webId"  id="webId" value="${web.webId}" style="width: 252px;height: 20px;"/>
				<input type="text" name="webName"  id="webName" value="${web.webName}" style="width: 252px;height: 20px;"/>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">网址：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="webUrl" id="webUrl" value="${web.webUrl}" style="width: 252px;height: 20px;">
				<font color="red">*</font>
			</td>
		</tr>
	
		<tr align="center">
			<td colspan="2" > 
				<input type="button" value="保存" id="submitBtn" class="l-button l-button-submit"/> 
			</td>
		</tr>
	</table>
</form>
</body>
<script language="javascript">
	$(function() {
		var url = "/app118/webAction/updWeb";
		$("#submitBtn").click(function() {
			var webName=$("#webName").val();
			if(webName==null||trim(webName)==''){
				$.ligerDialog.warn("网站名称不能为空。");
				$("#webName").focus();
				return;
			}
			var webUrl=$("#webUrl").val();
			if(webUrl==null||trim(webUrl)==''){
				$.ligerDialog.warn("网站地址不能为空。");
				$("#webUrl").focus();
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
				$.ligerDialog.success("编辑网址成功。",function(){
					parent.closeDialog();	
				});
			}else{
				$.ligerDialog.error("编辑网址失败。",function(){
					parent.closeDialog();	
				});
			}
		}
	}		

</script>
</html>