<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>加盟店客服务人员输入手机号页面</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerComboBox.js"></SCRIPT>
</head>
<body onload="tips();">
<form action="" method="post">
	<table border="0">
		<tr>
			<td  class="tdtitle">手机号：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="loginName" id="loginName"  style="width: 220px;"/><font color="red">*</font>手机号，即登录app的用户账号
			</td>
		</tr>
		<tr align="center">
			<td colspan="2" > 
				<input type="button" value="确定" id="submitBtn" class="l-button l-button-submit"/> 
			</td>
		</tr>
	</table>
</form>
</body>
<script language="javascript">
	$(function() {
		var url = "/app118/userAction/preBindUserAndAirDevie";
		$("#submitBtn").click(function() {
			var loginName=$("#loginName").val();
			
			if(loginName==null||trim(loginName)==''){
				$.ligerDialog.warn("手机号不能为空。");
				$("#loginName").focus();
				return;
			}else{
				if(!checkPhone(loginName)){
					$.ligerDialog.warn(loginName+"，手机号码不正确。");
					$("#loginName").focus();
					return;
				}
			}
			window.location.href=url+"?loginName="+loginName;

		});

	});
	
	//提示信息
	function tips(){
		var msg ="${message}";
		if(!(msg=='null'||msg=='')){
			if(msg == "success"){
				$.ligerDialog.alert("提示：${info}",function(){
					parent.closeDialog();	
				});
			}else{
				$.ligerDialog.alert("提示：${info}",function(){
					parent.closeDialog();	
				});
			}
		}
	}	
	
	//汽车品牌选择后级联汽车系列
	function doCarBrandChange(pCode){
		//清除车系
		var carSeriesSelect = document.getElementById("carSeries");
		carSeriesSelect.options.length=0; 
	 	var objOption =new Option(("","---请选择---")); 
	 	carSeriesSelect.options[carSeriesSelect.options.length] = objOption;
	 	
	 	
		//清除车型
		var carTypeSelect = document.getElementById("carType");
		carTypeSelect.options.length=0; 
		var objOption =new Option(("","---请选择---")); 
	 	carTypeSelect.options[carTypeSelect.options.length] = objOption;
	 	
	
		<c:forEach items="${carSeriesList}" var="item" varStatus="index">
			if('${item.pCode}' == pCode){   
			   var objOption = new Option("${item.codeName}","${item.codeId}");   
			   carSeriesSelect.options[carSeriesSelect.options.length] = objOption;   
			 }
	     </c:forEach>
	}
	//汽车系列选择后级联汽车型号
	function doCarSeriesChange(pCode){
		var carTypeSelect = document.getElementById("carType");
		carTypeSelect.options.length=0; 

	 	var objOption =new Option(("","---请选择---")); 
	 	carTypeSelect.options[carTypeSelect.options.length] = objOption;
		<c:forEach items="${carTypeList}" var="item" varStatus="index">
			if('${item.pCode}' == pCode){   
			   var objOption = new Option("${item.codeName}","${item.codeId}");   
			   carTypeSelect.options[carTypeSelect.options.length] = objOption;   
			 }
	     </c:forEach>
	}
	
	//所属门店选择后级联销售人员
	function getSalseChangeByOrgId(orgId){
		var saleUserIdSelect = document.getElementById("saleUserId");
		saleUserIdSelect.options.length=0; 

	 	var objOption =new Option(("","---请选择---")); 
	 	saleUserIdSelect.options[saleUserIdSelect.options.length] = objOption;
		<c:forEach items="${userList}" var="item" varStatus="index">
			if('${item.orgId}' == orgId){   
			   var objOption = new Option("${item.realName}","${item.userId}");   
			   saleUserIdSelect.options[saleUserIdSelect.options.length] = objOption;   
			 }
	     </c:forEach>
	}
	getSalseChangeByOrgId("${orgId}");
</script>
</html>