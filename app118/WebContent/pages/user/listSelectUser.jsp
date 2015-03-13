<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表选择</title>
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
					用户名：
				</td>
				<td class="tdtext">
					<input type="text" name="loginName" value="${loginName}" id="loginName" style="width: 128px;height: 20px;"/>
				</td>
				<td class="tdtitle">姓名：</td>
				<td class="tdtext">
					<input type="text" name="realName" value="${realName}" id="realName" style="width: 128px;height: 20px;"/>
				</td>
			</tr>
			<tr>
				<td class="tdtitle">
					从：
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
				<td class="tdtitle">&nbsp;</td>
				<td class="tdtext">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="button" value="查询" id="submitBtn" class="l-button l-button-submit" onclick="forSearch()"/>
					<input type="button" value="重置" id="submitBtn" class="l-button l-button-submit" onclick="forClean();"/> 
				</td>
			</tr>
		</table>
		</form>
	</div>
	
	
	
	<div class="l-loading" style="display:block" id="pageloading"></div>
	<div class="l-clear"></div>
	<!-- 工具栏 -->
	<div id="toptoolbar" class="l-toolbar" ligeruiid="toptoolbar" style="width:99%;">
		<div class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon"
			toolbarid="item-1" jQuery1405912963467="22" onclick="select();">
			<SPAN>确认选择</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-ok"></div> 
		</div>
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-2"
			jQuery1405912963467="23" onclick="selectAll();">
			<SPAN>&nbsp;全部发送</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-customers"></div> 
		</div>
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-2"
			jQuery1405912963467="23" onclick="clearSelect();">
			<SPAN>&nbsp;清空选择</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-delete"></div> 
		</div>
	</div>
	<!-- 工具栏结束 -->
	
	
	<div id="maingrid" ></div>
	 
<script type="text/javascript">
	var dialog=null;//弹出对话框
     var url = "/app118/userAction/listUser";
     $(function (){
     	init(url+"?orgId=${orgId}");
     	
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
     	$("#realName").val('');
     	$("#fromCreateTime").val('');
     	$("#toCreateTime").val('');
     }
   	 //查询
   	 function forSearch(){
   		var loginName=$("#loginName").val();
   		var realName=$("#realName").val();
   		var fromCreateTime= $("#fromCreateTime").val();
     	var toCreateTime=$("#toCreateTime").val();
     	var orgId=$("#orgId").val();
   	    var params="?loginName="+loginName+"&realName="+encodeURI(realName)+"&fromCreateTime="+fromCreateTime+"&toCreateTime="+toCreateTime+"&orgId="+orgId;
   	    init(url+params); 
   	 }
     
   	
   	 //查看明细
   	 function viewUser(userId){
   		var url ="/app118/userAction/viewUser?userId="+userId;
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:520,
  				  isResize:true,
  				  width: 650, 
  				  title: '查看用户明细'
  		}); 
   	 }
   	 
   	 //确认选择
	 function select(){
	   		var rowid=g.getSelecteds();//获得选中行ID
			if(rowid.length==0){
				 $.ligerDialog.warn("请至少选择一行");
				 return;
			}else{
			 var userIds="";
			 var realNames="";
			 var len=rowid.length;
			 for(var j=0;j<len;j++){
			   if(j!=len-1){
				   userIds+=rowid[j].userId+",";			
				   realNames+=rowid[j].realName+",";			
				}else{
					userIds+=rowid[j].userId;	
					realNames+=rowid[j].realName;
				}
			 }
		
			}
			parent.closeDialog(userIds,realNames);
		}
	 
   	 //清空选择
   	 function clearSelect(){
   		parent.closeDialog("null","null"); 
   	 }
   	 
   	 //全部发送
   	 function selectAll(){
   		parent.closeDialog("sendAll","sendAll"); 
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
		  	          //{ display: '用户标识', name: 'userId', width: '10%',hidden:'true'},
	  	              { display: '姓名', name: 'realName', width: '25%' },
	  	              { display: '手机号码', name: 'loginName', width: '30%'},
	  	              { display: '创建时间', name: 'createTime', width: '30%',type: "date", format:"yyyy-mm-dd HH:mm:ss"}
  	              ], url:url,dataAction:"server" , pageSize:15 ,rownumbers:true,pageParmName:"curNo",pagesizeParmName:"curSize"
  	          });
  		
  	    $("#pageloading").hide();
  	}
</script>
</body>
</html>