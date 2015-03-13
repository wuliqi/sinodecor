<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>app用户管理--查看用户明细</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerComboBox.js"></SCRIPT>
</head>
<body onload="tips();">
	<form action="" method="post">
		<table border="0" align="center">
			<tr>
				<td class="tdtitle">手机号：</td>
				<td class="tdtext" nowrap="nowrap">${user.loginName}</td>
			</tr>
			<tr>
				<td class="tdtitle">真实姓名：</td>
				<td class="tdtext" nowrap="nowrap">${user.realName}</td>
			</tr>
			<tr>
				<td class="tdtitle">姓别：</td>
				<td nowrap="nowrap" class="tdtext">${user.sex}</td>
			</tr>
			<tr>
				<td class="tdtitle">------------</td>
				<td class="tdtext" style="color: gray">----------------------------</td>
			</tr>
			<c:forEach items="${list}" var="item" varStatus="index">
				<tr>
					<td class="tdtitle">汽车种类：</td>
					<td class="tdtext" nowrap="nowrap">${item.carCategory}</td>
				</tr>
				<tr>
					<td class="tdtitle">汽车品牌：</td>
					<td class="tdtext" nowrap="nowrap">${item.carBrand}</td>
				</tr>
				<tr>
					<td class="tdtitle">汽车系列：</td>
					<td class="tdtext" nowrap="nowrap">${item.carSeries}</td>
				</tr>
				<tr>
					<td class="tdtitle">汽车型号：</td>
					<td class="tdtext" nowrap="nowrap">${item.carType}</td>
				</tr>
				<tr>
					<td class="tdtitle">汽车款式：</td>
					<td class="tdtext" nowrap="nowrap">${item.carStyle}</td>
				</tr>
				<tr>
					<td class="tdtitle">汽车年份：</td>
					<td class="tdtext" nowrap="nowrap">${item.carYear}</td>
				</tr>
				<tr>
					<td class="tdtitle">汽车牌号：</td>
					<td class="tdtext" nowrap="nowrap">${item.deviceName}</td>
				</tr>
				<tr>
					<td class="tdtitle">设备Mac：</td>
					<td class="tdtext" nowrap="nowrap">${item.deviceMac}</td>
				</tr>
				<tr>
					<td class="tdtitle">充值种类：</td>
					<td nowrap="nowrap" class="tdtext">${item.cardType}</td>
				</tr>
				<tr>
					<td class="tdtitle">实付金额：</td>
					<td class="tdtext" nowrap="nowrap">${item.paidAmount}</td>
				</tr>
				<tr>
					<td class="tdtitle">------------</td>
					<td class="tdtext" style="color: gray">----------------------------</td>
				</tr>
			</c:forEach>


			<tr align="center">
				<td colspan="2"><input type="button" value="关闭" id="submitBtn"
					class="l-button l-button-submit" /></td>
			</tr>
		</table>
	</form>
</body>
<script language="javascript">
	$(function() {
		var url = "/app118/userAction/addUserAll";
		$("#submitBtn").click(function() {
			parent.closeDialog();

		});

	});
	
	//提示信息
	function tips(){
		var msg ="${message}";
		if(!(msg=='null'||msg=='')){
			if(msg == "success"){
				//alert("操作成功。");
				$.ligerDialog.alert("操作成功。");
			}else{
				$.ligerDialog.alert("操作失败。");
				//alert("操作失败。");
			}
			parent.closeDialog();
		}
	}	
	
</script>
</html>