<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>app用户管理--添加用户</title>
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
		<tr>
			<td  class="tdtitle">真实姓名：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="realName" id="realName"  style="width: 220px;"/><font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">姓别：</td>
			<td  nowrap="nowrap" class="tdtext">
				<select id="sex" name="sex" style="width: 220px;height: 20px;">
					<option value="">---请选择---</option>
					<option value="男">男</option>
					<option value="女">女</option>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">所属组织机构：</td>
			<td  nowrap="nowrap" class="tdtext">
				<select name="orgId" id="orgId" style="width: 220px;height: 20px;" ${disabled} onchange="getSalseChangeByOrgId(this.value)">
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${orgMap}">
						<option value="${oneMap.key }" <c:if test="${orgId==oneMap.key }">selected </c:if> >${oneMap.value}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">汽车种类：</td>
			<td  nowrap="nowrap">
				<input type="radio" name="carCategory" id="carCategory" value="2" <c:if test="${carCategory eq '2'}">checked="checked"</c:if> />出租车
				<input type="radio" name="carCategory" id="carCategory" value="4" <c:if test="${carCategory eq '4'}">checked="checked"</c:if> />私家车<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">汽车品牌：</td>
			<td class="tdtext" nowrap="nowrap">
				 <select  name="carBrand" id="carBrand"  style="width: 220px;" onchange="doCarBrandChange(this.value)">
				 	 <option value="">---请选择---</option>
				 	 <c:forEach items="${brandList}" var="item" varStatus="index">
				 	 	<option value="${item.codeId}">${item.codeName}</option>
				     </c:forEach>
				 </select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">汽车车系：</td>
			<td class="tdtext" nowrap="nowrap">
				<select  name="carSeries" id="carSeries"  style="width: 220px;" onchange="doCarSeriesChange(this.value)">
				 	 <option value="">---请选择---</option>
				 	 
				 </select>
				
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">汽车型号：</td>
			<td class="tdtext" nowrap="nowrap">
				<select  name="carType" id="carType"  style="width: 220px;">
				 	 <option value="">---请选择---</option>
				 	 
				 </select>
				
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">汽车款式：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="carStyle" id="carStyle"  style="width: 220px;"/><font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">汽车年份：</td>
			<td class="tdtext">
				<select  name="carYear"  id="carYear"  style="width: 220px;">
					<option value="">---请选择---</option>
					<c:forEach items="${yearList}" var="year">
						<option value="${year}">${year}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">汽车牌号：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="deviceName" id="deviceName"  style="width: 220px;"/><font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">设备Mac：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="deviceMac" id="deviceMac"  style="width: 220px;"/><font color="red">*</font>例如 00:0E:0B:00:26:7B，以英文“：”分隔
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">销售人员：</td>
			<td  nowrap="nowrap" class="tdtext">
				 <select  name="saleUserId" id="saleUserId"  style="width: 220px;height: 20px;" >
				 	    <option value="">---请选择---</option>
				 </select>
				 <font color="red">*</font>
			</td>
		</tr>
	<%-- 	<tr>
			<td  class="tdtitle">充值种类：</td>
			<td  nowrap="nowrap" class="tdtext">
				<select name="cardType" id="cardType" style="width: 220px;height: 20px;" disabled="disabled">
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${cardTypeMap}">
						<option value="${oneMap.key }" <c:if test="${oneMap.key eq cardType}">selected</c:if>>${oneMap.value}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">实付金额：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="paidAmount" id="paidAmount" value="0"  style="width: 220px;" readonly="readonly"/>元
			</td>
		</tr>  --%>
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
		var url = "/app118/userAction/addUserAll?op=add";
		$("#submitBtn").click(function() {
			var loginName=$("#loginName").val();
			var deviceName=$("#deviceName").val();
			var sex=$("#sex").val();
			var carCategory=$("#carCategory").val();
			var realName=$("#realName").val();
			var carBrand=$("#carBrand").val();
			var carSeries=$("#carSeries").val();
			var carType=$("#carType").val();
			var carStyle=$("#carStyle").val();
			var carYear=$("#carYear").val();
			var deviceMac=$("#deviceMac").val();
			var saleUserId=$("#saleUserId").val();
			var orgId=$("#orgId").val();
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
			if(realName==null||trim(realName)==''){
				$.ligerDialog.warn("真实姓名不能为空。");
				$("#realName").focus();
				return;
			}
			if(sex==null||trim(sex)==''){
				$.ligerDialog.warn("性别不能为空，请选择。");
				$("#sex").focus();
				return;
			}
			
			if(orgId==null||trim(orgId)==''){
				$.ligerDialog.warn("所属门店不能为空，请选择。");
				$("#orgId").focus();
				return;
			}
			var val=$('input:radio[name="carCategory"]:checked').val();
			if(val==null){
				$.ligerDialog.warn("汽车种类不能为空，请选择。");
				$("#carCategory").focus();
				return;
			}
			
			if(carBrand==null||trim(carBrand)==''){
				$.ligerDialog.warn("汽车品牌不能为空，请选择。");
				$("#carBrand").focus();
				return;
			}
			if('---请选择---'==carSeries||carSeries==null||trim(carSeries)==''){
				$.ligerDialog.warn("汽车车系不能为空，请选择。");
				$("#carSeries").focus();
				return;
			}
			if('---请选择---'==carType||carType==null||trim(carType)==''){
				$.ligerDialog.warn("汽车型号不能为空，请选择。");
				$("#carType").focus();
				return;
			}
			if(carStyle==null||trim(carStyle)==''){
				$.ligerDialog.warn("汽车款式不能为空。");
				$("#carStyle").focus();
				return;
			}
			if(carYear==null||trim(carYear)==''){
				$.ligerDialog.warn("汽车年份不能为空，请选择。");
				$("#carYear").focus();
				return;
			}
			if(deviceName==null||trim(deviceName)==''){
				$.ligerDialog.warn("汽车牌号不能为空。");
				$("#deviceName").focus();
				return;
			}else if(!isCarNumber(deviceName)){
				$.ligerDialog.warn(deviceName+",汽车牌号不正确。");
				$("#deviceName").focus();
				return;
			}
			if(deviceMac==null||trim(deviceMac)==''){
				$.ligerDialog.warn("设备Mac不能为空。");
				$("#deviceMac").focus();
				return;
			}else if(!isMacAddress(deviceMac)){
				$.ligerDialog.warn('Mac地址错误，Mac地址格式为00:17:EA:92:DC:27');
				$("#deviceMac").focus();
				return;
			}
			if("---请选择---"==saleUserId||saleUserId==null||trim(saleUserId)==''){
				$.ligerDialog.warn("销售人员不能为空，请选择。");
				$("#saleUserId").focus();
				return;
			}
			
			$("#orgId").removeAttr("disabled"); 
			document.forms[0].action = url;
	 		document.forms[0].submit();	

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