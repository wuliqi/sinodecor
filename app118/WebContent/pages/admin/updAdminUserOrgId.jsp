<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>店长修改页面--用户转移 修改销售人员或所属门店</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerComboBox.js"></SCRIPT>


</head>
<body onload="tips();">
<form action="" method="post">
	<table border="0">
		<tr>
			<td  class="tdtitle">所属组织机构：</td>
			<td  nowrap="nowrap" class="tdtext">
				<input type="hidden" name="userIds" id="userIds" value="${userIds }">
				<input type="hidden" name="deviceIds" id="deviceIds" value="${deviceIds }">
				<input type="hidden" name="deviceNames" id="deviceNames" value="${deviceNames}">
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
			<td  class="tdtitle">销售人员：</td>
			<td  nowrap="nowrap" class="tdtext">
				 <select  name="saleUserId" id="saleUserId"  style="width: 220px;height: 20px;" >
				 	    <option value="">---请选择---</option>
				 </select>
			</td>
		</tr>
		
		<tr align="center">
			<td colspan="2" > 
				<input type="button" value="保存" id="submitBtn" class="l-button l-button-submit"/> 
			</td>
		</tr>
	</table>
</form>
<script language="javascript">
	$(function() {
				
		var url = "/app118/adminUserMangerAction/updAdminUserOrgId";
		$("#submitBtn").click(function() {
			 $.ligerDialog.confirm('修改数据存在风险，请慎重，确认客户转移吗？',function(yes){
				  if(yes){
						var orgId=$("#orgId").val();
						if(orgId==null||trim(orgId)==''){
							$.ligerDialog.warn("所属门店不能为空，请选择。");
							$("#orgId").focus();
							return;
						}
						var saleUserId=$("#saleUserId").val();
						if("---请选择---"==saleUserId){
							saleUserId="";
						}
						$("#orgId").removeAttr("disabled"); 
						document.forms[0].action = url;
				 		document.forms[0].submit();	
					}
				});
			
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
	
	//所属门店选择后级联销售人员
	function getSalseChangeByOrgId(orgId){
		var saleUserIdSelect = document.getElementById("saleUserId");
		saleUserIdSelect.options.length=0; 

	 	var objOption =new Option(("","---请选择---")); 
	 	saleUserIdSelect.options[saleUserIdSelect.options.length] = objOption;
		<c:forEach items="${userList}" var="item" varStatus="index">
			if('${item.orgId}' == orgId){   
			   var objOption = new Option("${item.realName}","${item.userId}"); 
			   if("${item.userId}"=="${saleUserId}"){
				   objOption.selected = true; 
			   }
			   saleUserIdSelect.options[saleUserIdSelect.options.length] = objOption;   
			 }
	     </c:forEach>
	}
	getSalseChangeByOrgId("${orgId}");
</script>
</body>
</html>