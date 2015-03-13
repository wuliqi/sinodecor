<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="cn.app118.constants.SystemConstant"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@include file="/pages/common/common.jsp"%>
</head>
<body style="margin: 0px;margin-bottom: 0px;margin-left: 0px;margin-right: 0px;margin-top: 0px;">
	<table border="0" width="100%" height="100px" style="background:url('/resource/images/background.png'); ">
		<tr>
			<td style="width: 70%;padding-left: 20px;"><img src="/resource/images/logo.png" alt="app118" /></td>
			<td style="width: 25%;"><img src="/resource/images/systemName.png" alt="<%=SystemConstant.SYSTEM_NAME%>" /></td> 
			<td><img src="/resource/images/verticalLine.png" alt="app118" /></td> 
			<td><img src="/resource/images/logout.png" alt="注销" onclick="logout()"/></td> 

		</tr>
	</table>
<script type="text/javascript"> 
function logout(){//退出
<%-- 	$.ligerDialog.confirm("您确认退出<%=SystemConstant.SYSTEM_NAME%>吗?", function (yes){
         if(yes){ --%>
        	 if (top.location != self.location)
     			top.location = self.location;
     		 window.top.location ="/app118/mainAction/logout" ;
       /*   }
	}); */

	
	//if(confirm("您确认退出<%=SystemConstant.SYSTEM_NAME%>吗?")) {
 	/* if (top.location != self.location)
			top.location = self.location;
		window.top.location ="/app118/mainAction/logout" ; */
	//}
}
</script>
</body>
</html>