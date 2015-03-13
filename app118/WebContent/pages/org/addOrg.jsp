<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加组织机构</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerComboBox.js"></SCRIPT>
</head>
<body onload="tips();">
<form action="" method="post">
	<table border="0" align="center">
		<tr>
			<td  class="tdtitle">组织机构名称：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="orgName"  id="orgName" style="width: 252px;height: 20px;"/>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">组织机构编号：</td>
			<td  nowrap="nowrap">
				<input type="text" name="orgNo" id="orgNo"  style="width: 252px;height: 20px;">
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">所属组织机构：</td>
			<td class="tdtext" nowrap="nowrap">
				<select name="orgPid" id="orgPid"  style="width: 252px;height: 20px;">
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${orgMap}">
						<option value="${oneMap.key }" <c:if test="${oneMap.key eq orgPid}">selected</c:if> >${oneMap.value}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		
		<tr>
			<td  class="tdtitle">所属品牌：</td>
			<td class="tdtext" nowrap="nowrap">
				<select name="orgType" id="orgType"  style="width: 252px;height: 20px;">
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${codeList}">
						<option value="${oneMap.codeId }" <c:if test="${oneMap.codeId eq orgType}">selected</c:if> >${oneMap.codeName}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">服务热线：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="mobile"  id="mobile" style="width: 252px;height: 20px;"/>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">地理位置：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="abbr"  id="abbr" style="width: 252px;height: 20px;"/>
				<br/>可通过<a href="http://lbs.amap.com/console/show/picker" target="_blank">高德坐标拾取工具</a>获得某点的精确经纬度。
			</td>
		</tr>
		
		<tr>
			<td class="tdtitle">备注：</td>
			<td nowrap="nowrap">
				<textarea rows="8" cols="30" name="remark" id="remark"></textarea>
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
		var url = "/app118/orgAction/addOrg";
		$("#submitBtn").click(function() {
			var orgName=$("#orgName").val();
			if(orgName==null||trim(orgName)==''){
				$.ligerDialog.warn("组织机构名称不能为空。");
				$("#orgName").focus();
				return;
			}
			var orgPid=$("#orgPid").val();
			if(orgPid==null||trim(orgPid)==''){
				$.ligerDialog.warn("请选择所属组织机构。");
				$("#orgPid").focus();
				return;
			}
			var orgNo=$("#orgNo").val();
			if(orgNo==null||trim(orgNo)==''){
				$.ligerDialog.warn("组织机构编号不能为空。");
				$("#orgNo").focus();
				return;
			}
			var remark=$("#remark").val();
			if(!(remark==null||trim(remark)=='')){
				if(remark.length>120){
					$.ligerDialog.warn("备注不能超过120字符。");
					$("#remark").focus();
					return;
				}
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
				$.ligerDialog.success("增加组织机构成功。",function(){
					parent.closeDialog();	
				});
			}else{
				$.ligerDialog.error("增加组织机构失败。",function(){
					parent.closeDialog();	
				});
			}
		}
	}		

</script>
</html>