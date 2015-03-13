<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台用户管理--查看用户明细</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerComboBox.js"></SCRIPT>
</head>
<body onload="tips();">
<form action="" method="post">
	<table border="0" align="center">
		<tr>
			<td  class="tdtitle">手机号：</td>
			<td class="tdtext" nowrap="nowrap">
				${user.loginName}
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">真实姓名：</td>
			<td class="tdtext" nowrap="nowrap">
				${user.realName}
			</td>
		</tr>
		<tr>
			<td  class="tdtitle">姓别：</td>
			<td  nowrap="nowrap" class="tdtext">
				${user.sex}
			</td>
		</tr>
		<tr>
			<td class="tdtitle">所属角色：</td>
			<td class="tdtext" nowrap="nowrap">
				${roleNames}
			</td>
		</tr>
		<tr>
			<td class="tdtitle">所属组织机构：</td>
			<td class="tdtext" nowrap="nowrap">
				${orgName}
			</td>
		</tr>
		<tr>
			<td class="tdtitle"> 创建时间：</td>
			<td class="tdtext" nowrap="nowrap">
				${createTime}
			</td>
		</tr>
		<tr>
			<td class="tdtitle"> 最后修改时间：</td>
			<td class="tdtext" nowrap="nowrap">
				${modifyTime}
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