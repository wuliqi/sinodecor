<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志管理--列表</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<script src="/resource/lib/ligerUI/js/plugins/ligerGrid.js"
	type="text/javascript"></script>
<LINK rel=stylesheet type=text/css
	href="/resource/lib/ligerUI/skins/ligerui-icons.css">
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerToolBar.js"></SCRIPT>
<SCRIPT type=text/javascript src="/resource/lib/ligerUI/js/plugins/ligerMenu.js"></SCRIPT> 
<SCRIPT type=text/javascript src="/resource/lib/ligerUI/js/plugins/ligerMenuBar.js"></SCRIPT> 
<STYLE type=text/css>
    #menu1,.l-menu-shadow{top:30px; left:50px;} 
    #menu1{  width:200px;} 
</STYLE> 
</head>
<body>
	<div class="l-form">
		<form action="" id="form1" method="post">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td class="tdtitle">
					登录名：
				</td>
				<td class="tdtext">
					<input type="text" name="loginName" value="${loginName}" id="loginName" style="width: 128px;height: 20px;">
				</td>
				<td class="tdtitle">IP地址：</td>
				<td class="tdtext">
					<input type="text" name="ipAddress" value="${ipAddress}" id="ipAddress" style="width: 128px;height: 20px;"/>
				</td>
			</tr>
			<tr >
				<td class="tdtitle">
					操作时间从：
				</td>
				<td>
					<input type="text" name="fromCreateTime" value="${fromCreateTime}" id="fromCreateTime"/>
				</td>
				<td class="tdtitle">至：</td>
				<td>
					<input type="text" name="toCreateTime" value="${toCreateTime}" id="toCreateTime"/>
				</td>
			</tr>
			<tr>
				<td class="tdtitle">
					所属组织机构：
				</td>
				<td>
					<select name="orgId" id="orgId" style="width: 128px;height: 20px;" ${disabled}>
						<option value="">---请选择---</option>
						<c:forEach var="oneMap" items="${orgMap}">
							<option value="${oneMap.key }" <c:if test="${orgId==oneMap.key }">selected </c:if> >${oneMap.value}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td  align="right">
					<ul class="l-form-buttons" >
						<li onclick="forSearch()">
							<div class="l-button" style="width: 60px;"
								ligeruiid="Button1001">
								<div class="l-button-l"></div>
								<div class="l-button-r"></div>
								<span>查询</span>
							</div>
						</li>
						<li onclick="forClean();">
							<div class="l-button" style="width: 60px;"
								ligeruiid="Button1002">
								<div class="l-button-l"></div>
								<div class="l-button-r"></div>
								<span>重置</span>
							</div>
						</li>
					</ul>
					
				</td>
			</tr>
		</table>
		</form>
	</div>
	
	
	
	<div class="l-loading" style="display:block" id="pageloading"></div>
	<div class="l-clear"></div>
	
	<!-- 工具栏 -->
	
	<!-- 工具栏结束 -->
	<div id="maingrid" ></div>
	 
<script type="text/javascript">
	var dialog=null;//弹出对话框
     var url = "/app118/logAction/listLog?logType=${logType}";
     $(function (){
     	init(url+"&orgId=${orgId}");
     	
     	$("#fromCreateTime").ligerDateEditor({
			showTime : true,
			labelWidth :200,
			labelAlign : 'left'
		});
     	$("#toCreateTime").ligerDateEditor({
			showTime : true,
			labelWidth : 100,
			labelAlign : 'left'
		});
     	
     });
     
     //重置条件
     function forClean(){
     	$("#loginName").val('');
     	$("#ipAddress").val('');
     	$("#fromCreateTime").val('');
     	$("#toCreateTime").val('');
     }
   	 //查询
   	 function forSearch(){
   		var loginName=$("#loginName").val();
   		var ipAddress=$("#ipAddress").val();
   		var fromCreateTime= $("#fromCreateTime").val();
     	var toCreateTime=$("#toCreateTime").val();
     	var orgId=$("#orgId").val();
   	    var params="&loginName="+encodeURI(loginName)+"&ipAddress="+ipAddress+"&fromCreateTime="+fromCreateTime+"&toCreateTime="+toCreateTime+"&orgId="+orgId;
   	    init(url+params); 
   	 }
     
   	 
   	
   	 
    //关闭对话框
 	function closeDialog(){
 		dialog.hide();
 		g.loadData();
 	}
  
     
  	/**
  	*初始化
  	*/	
  	function init(url){
  		var data = {};
  		/**
  		* 加载Grid数据并显示
  		*/
  		window['g']= $("#maingrid").ligerGrid({
  	        	  width: '99%',
            	  height:'99%',
  	      	      rowHeight:26,
  	      	   	  checkbox:true,
  	              headerRowHeight:28,
  	              dateFormat: "yyyy-MM-dd hh:mm:ss",
  	              columns: [
		  	          //{ display: '日志标识', name: 'logId', width: '10%'},
	  	              { display: '所属组织机构', name: 'orgName', width: '13%'},
	  	              { display: '登录名', name: 'loginName', width: '8%'},
	  	              { display: '姓名', name: 'realName', width: '8%'},
	  	              { display: 'IP地址', name: 'ipAddress', width: '10%'},
	  	              { display: '操作内容', name: 'opContent', width: '40%'},
	  	              { display: '创建时间', name: 'opTime', width: '15%',type: "date", format:"yyyy-mm-dd HH:mm:ss"}
  	              ], url:url,dataAction:"server" , pageSize:15 ,rownumbers:true,pageParmName:"curNo",pagesizeParmName:"curSize"
  	          });
  		
  	    $("#pageloading").hide();
  	}
</script>
</body>
</html>