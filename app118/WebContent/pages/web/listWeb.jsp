<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网址管理</title>
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
				<td class="tdtitle">名称：</td>
				<td class="tdtext">
					<input type="text" name="webName" value="${webName}" id="webName" style="width: 128px;height: 20px;"/>
				</td>
				<td class="tdtitle">URL：</td>
				<td class="tdtext">
					<input type="text" name="webUrl" value="${webUrl}" id="webUrl" style="width: 128px;height: 20px;"/>
				</td>
			</tr>
			
			<tr>	
				<td class="tdtitle">
					创建时间从：
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
		<div class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon"
			toolbarid="item-1" jQuery1405912963467="22" onclick="addWeb();">
			<SPAN>增加网址</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-add"></div>
		</div>
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-2"
			jQuery1405912963467="23" onclick="updWeb();">
			<SPAN>&nbsp;编辑网址</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-modify"></div>
		</div>
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-3"
			jQuery1405912963467="24" onclick="delWeb();">
			<SPAN>&nbsp;删除网址</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-delete"></div>
		</div>
	</div>
	<!-- 工具栏结束 -->
	<div id="maingrid" ></div>
	 
<script type="text/javascript">
	var dialog=null;//弹出对话框
     var url = "/app118/webAction/listWebByPager";
     $(function (){
     	init(url);
     	
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
     	$("#webName").val('');
     	$("#webUrl").val('');
     	$("#fromCreateTime").val('');
     	$("#toCreateTime").val('');
     }
     
   	 //查询网址
   	 function forSearch(){
   		var webName=$("#webName").val();
   		var webUrl=$("#webUrl").val();
   		var fromCreateTime= $("#fromCreateTime").val();
     	var toCreateTime=$("#toCreateTime").val();
     	
   	    var params="?webName="+encodeURI(webName)+"&webUrl="+encodeURI(webUrl)+"&fromCreateTime="+fromCreateTime+"&toCreateTime="+toCreateTime;
   	    init(url+params); 
   	 }
     
   	 //增加网址
   	 function addWeb(){
   		var url ="/app118/webAction/toAddWeb";
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:520,
  				  isResize:false,
  				  width: 650, 
  				  title: '增加网址'
  		}); 
   	 }
   	 
   	 //修改网址
   	 function updWeb(){
   		var params;
   		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行。");
			 return;
		}else{
		 var webId="";
		 var len=rowid.length;
			 if(len>1){
			 	$.ligerDialog.warn("请只选择一行。");
				return;
			 }else{
				webId+=rowid[0].webId;
				params="?webId="+webId;
			 }
		}
   		var url ="/app118/webAction/toUpdWeb"+params;
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:520,
  				  isResize:true,
  				  width: 650, 
  				  title: '修改网址'
  		}); 
   	 }
   	 
   	 //删除网址
   	 function delWeb(){
   		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行");
			 return;
		}else{
		 var ids="";
		 var len=rowid.length;
		 for(var j=0;j<len;j++){
		    if(j!=len-1){
			   ids+=rowid[j].webId+",";			
			}else{
				ids+=rowid[j].webId;
			}
		 }
		 var url="/app118/webAction/delWeb";
		  $.ligerDialog.confirm('确认删除选择的网址吗？',function(yes){
			  if(yes){
		      		$.ajax({
			      			type:'POST',
							url:url,
							data:{ids:ids},
							dataType:'json',
			      			success:function(msg){
			      				if(msg.flag==1){
			      					$.ligerDialog.success("删除网址成功。");
			      					g.loadData();
			      				}else{
			      					$.ligerDialog.error("删除网址失败。");
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
  	              columns: [
		  	          { display: '标题', name: 'webName', width: '30%'},
	  	              { display: '网址', name: 'webUrl', width: '55%' },
	  	              { display: '创建时间', name: 'createTime', width: '14%'}
  	              ], url:url,dataAction:"server" , pageSize:15 ,rownumbers:true,pageParmName:"curNo",pagesizeParmName:"curSize"
  	          });
  		
  	    $("#pageloading").hide();
  	}
</script>
</body>
</html>