<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户车辆绑定管理列表</title>
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
					汽车牌号：
				</td>
				<td class="tdtext">
					<input type="text" name="deviceName" value="${deviceName}" id="deviceName" style="width: 128px;height: 20px;"/>
				</td>
				<td class="tdtitle">姓名：</td>
				<td class="tdtext">
					<input type="text" name="realName" value="${realName}" id="realName" style="width: 128px;height: 20px;"/>
				</td>
			</tr>
			<tr>
				<td class="tdtitle">
					绑定时间从：
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
	<div id="toptoolbar" class="l-toolbar" ligeruiid="toptoolbar" style="width:99%;">
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-2"
			jQuery1405912963467="23" onclick="delDeviceUserRelaAndUpdAirDevice();">
			<SPAN>&nbsp;设备解绑回库</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-modify"></div>
		</div>
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-3"
			jQuery1405912963467="24" onclick="delDeviceUserRela();">
			<SPAN>&nbsp;车辆绑定删除</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-delete"></div>
		</div>
	</div>
	<!-- 工具栏结束 -->
	<div id="maingrid" ></div>
	 
<script type="text/javascript">
	var dialog=null;//弹出对话框
     var url = "/app118/userAction/listUserDeviceRel";
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
     	$("#deviceName").val('');
     	$("#realName").val('');
     	$("#fromCreateTime").val('');
     	$("#toCreateTime").val('');
     	//$("#orgId").val('');
     }
   	 //查询
   	 function forSearch(){
   		var deviceName=$("#deviceName").val();
   		var realName=$("#realName").val();
   		var fromCreateTime= $("#fromCreateTime").val();
     	var toCreateTime=$("#toCreateTime").val();
     	var orgId=$("#orgId").val();
   	    var params="?deviceName="+encodeURI(deviceName)+"&realName="+encodeURI(realName)+"&fromCreateTime="+fromCreateTime+"&toCreateTime="+toCreateTime+"&orgId="+orgId;
   	    init(url+params); 
   	 }
     
   	 

   	
   	 //TODO 设备解绑回库
   	 function delDeviceUserRelaAndUpdAirDevice(){
   		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行。");
			 return;
		}else{
		 var durIds="";
		 var deviceIds="";
		 var len=rowid.length;
		 for(var j=0;j<len;j++){
		   if(j!=len-1){
			   durIds+=rowid[j].durId+",";			
			   deviceIds+=rowid[j].deviceId+",";			
			}else{
				durIds+=rowid[j].durId;
				deviceIds+=rowid[j].deviceId;
			}
		 }
		 var url="/app118/userAction/delDeviceUserRelaAndUpdAirDevice";
		  $.ligerDialog.confirm('此操作将影响多个与其绑定的用户，不可恢复，请慎用。确认将设备与车辆解绑并使设备重回库存吗？',function(yes){
			  if(yes){
		      		$.ajax({
			      			type:'POST',
							url:url,
							data:{durIds:durIds,deviceIds:deviceIds},
							dataType:'json',
			      			success:function(msg){
			      				if(msg.flag=1){
			      					$.ligerDialog.success("设备与车辆解绑并设备重回库存成功。");
			      					g.loadData();
			      				}else{
			      					$.ligerDialog.error("设备与车辆解绑并设备重回库存失败。");
			      				}
			      			},
			      			error:function(){
			      				$.ligerDialog.error("操作失败。");
			      			}
			      		});
				}else{
				   g.loadData();
				}
			})
		} 
   	 }
   	 
   	 //车辆绑定删除
   	 function delDeviceUserRela(){
   		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行。");
			 return;
		}else{
		 var durIds="";
		 var len=rowid.length;
		 for(var j=0;j<len;j++){
		   if(j!=len-1){
			   durIds+=rowid[j].durId+",";			
			}else{
				durIds+=rowid[j].durId;
			}
		 }
		 var url="/app118/userAction/delUserDeviceRel";
		  $.ligerDialog.confirm('确认删除选择的绑定车辆吗？',function(yes){
			  if(yes){
		      		$.ajax({
			      			type:'POST',
							url:url,
							data:{durIds:durIds},
							dataType:'json',
			      			success:function(msg){
			      				if(msg.flag=1){
			      					$.ligerDialog.success("删除绑定车辆成功。");
			      					g.loadData();
			      				}else{
			      					$.ligerDialog.error("删除绑定车辆失败。");
			      				}
			      			},
			      			error:function(){
			      				$.ligerDialog.error("操作失败。");
			      			}
			      		});
				}else{
				   g.loadData();
				}
			})
		} 
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
		  	          { display: '绑定标识', name: 'durId', width: '6%'},
		  	          { display: '用户标识', name: 'userId', width: '6%'},
		  	          { display: '设备标识', name: 'deviceId', width: '6%'},
	  	              { display: '所属组织机构', name: 'orgName', width: '10%' },
	  	              { display: '姓名', name: 'realName', width: '10%' },
	  	              { display: '汽车牌号', name: 'deviceName', width: '12%'},
	  	              { display: '设备Mac', name: 'deviceMac', width: '16%'},
	  	              { display: '绑定时间', name: 'createTime', width: '16%',type: "date", format:"yyyy-mm-dd HH:mm:ss"},
	  	              { display: '查看', name: '查看', width: '10%',render: function (row){
			             	var parms="\""+row.userId+"\"";
			             	var html="<a href='javascript:viewUser("+parms+");'>查看</a>";
			             	
			             	return html;
			              }
	  	              }
  	              ], url:url,dataAction:"server" , pageSize:15 ,rownumbers:true,pageParmName:"curNo",pagesizeParmName:"curSize"
  	          });
  		
  	    $("#pageloading").hide();
  	}
</script>
</body>
</html>