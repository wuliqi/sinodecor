<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>代码管理--修改</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerComboBox.js"></SCRIPT>
</head>
<body onload="tips();">
<form action="" method="post">
	<table border="0">
		<tr>
			<td  class="tdtitle">所属种类：</td>
			<td class="tdtext" nowrap="nowrap">
				<select name="type" id="type" style="width: 220px;height: 20px;">
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${typeMap}">
						<option value="${oneMap.key }" <c:if test="${oneMap.key eq  code.type}">selected</c:if> >${oneMap.value}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
				<input type="hidden" name="codeId" id="codeId" value="${code.codeId }">
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">代码类别名称：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="codeName" id="codeName" value="${code.codeName}" style="width: 220px;"/>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">状态：</td>
			<td  nowrap="nowrap" class="tdtext">
				<select name="status" id="status" style="width: 220px;height: 20px;">
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${statusMap}">
						<option value="${oneMap.key }" <c:if test="${oneMap.key eq  code.status}">selected</c:if> >${oneMap.value}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">代码类别编码：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="codeValue" id="codeValue" value="${code.codeValue}"  style="width: 220px;"/>
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">父代码类别名称：</td>
			<td  nowrap="nowrap" class="tdtext">
				<select name="pCode" id="pCode" style="width: 220px;height: 20px;">
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${codeMap}">
						<option value="${oneMap.key }" <c:if test="${oneMap.key eq  code.pCode}">selected</c:if> >${oneMap.value}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">描述：</td>
			<td >
				<textarea rows="8" cols="30" name="descr" id="descr" style="width: 220px;">${code.descr}</textarea>
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
		var url = "/app118/codeAction/updCode";
		$("#submitBtn").click(function() {
			var type=$("#type").val();
			var status=$("#status").val();
			var codeName=$("#codeName").val();
			var descr=$("#descr").val();
			
			if(type==null||trim(type)==''){
				$.ligerDialog.warn("代码所属种类不能为空，请选择。");
				$("#type").focus();
				return;
			}
			if(codeName==null||trim(codeName)==''){
				$.ligerDialog.warn("代码类别名称不能为空。");
				$("#codeName").focus();
				return;
			}
			if(status==null||trim(status)==''){
				$.ligerDialog.warn("状态不能为空,请选择。");
				$("#status").focus();
				return;
			}
			if(!(descr==null||trim(descr)=='')){
				if(descr.length>250){
					$.ligerDialog.warn("描述信息不能超过250字符。");
					$("#descr").focus();
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
				$.ligerDialog.alert("${info}",function(){
					parent.closeDialog();	
				});
			}else{
				$.ligerDialog.alert("${info}",function(){
					parent.closeDialog();	
				});
			}
		}
	}	
	
</script>
</html>