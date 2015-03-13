<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>代码管理--列表</title>
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
					所属种类：
				</td>
				<td class="tdtext">
					<select name="type" id="type" style="width: 120px;height: 20px;">
						<option value="">---请选择---</option>
						<c:forEach var="oneMap" items="${typeMap}">
							<option value="${oneMap.key }" >${oneMap.value}</option>
						</c:forEach>
					</select>
				</td>
				<td class="tdtitle">代码类别名称：</td>
				<td class="tdtext">
					<input type="text" name="codeName" value="${codeName}" id="codeName" style="width: 128px;height: 20px;"/>
				</td>
			</tr>
			<tr >
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
			toolbarid="item-1" jQuery1405912963467="22" onclick="toAddCode();">
			<SPAN>增加代码</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-add"></div>
		</div>
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-2"
			jQuery1405912963467="23" onclick="toUpdCode();">
			<SPAN>&nbsp;编辑代码</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-modify"></div>
		</div>
		<div class=l-bar-separator></div>
		<!-- <div class="l-toolbar-item l-panel-btn" toolbarid="item-3"
			jQuery1405912963467="24" onclick="delCode();">
			<SPAN>&nbsp;删除代码</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-delete"></div>
		</div> -->
		<!-- <div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-3"
			jQuery1405912963467="24" onclick="importCode();">
			<SPAN>&nbsp;Excel导入代码</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-setting"></div>
		</div> -->
	</div>
	<!-- 工具栏结束 -->
	<div id="maingrid" ></div>
	 
<script type="text/javascript">
	var dialog=null;//弹出对话框
     var url = "/app118/codeAction/listCode";
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
     	$("#codeName").val('');
     	$("#type").val('');
     	$("#fromCreateTime").val('');
     	$("#toCreateTime").val('');
     }
   	 //查询
   	 function forSearch(){
   		var codeName=$("#codeName").val();
   		var type=$("#type").val();
   		var fromCreateTime= $("#fromCreateTime").val();
     	var toCreateTime=$("#toCreateTime").val();
   	    var params="?codeName="+encodeURI(codeName)+"&type="+type+"&fromCreateTime="+fromCreateTime+"&toCreateTime="+toCreateTime;
   	    init(url+params); 
   	 }
     
   	 //增加
   	 function toAddCode(){
   		var type=$("#type").val();
   		var url ="/app118/codeAction/toAddCode?type="+type;
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:520,
  				  isResize:true,
  				  width: 650, 
  				  title: '增加代码'
  		}); 
   	 }
   	 
   	 //编辑代码
   	 function toUpdCode(){
    	var id="";
   		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行。");
			 return;
		}else{
		 	 var len=rowid.length;
			 if(len>1){
			 	$.ligerDialog.warn("请只选择一行。");
				return;
			 }else{
				id+=rowid[0].codeId;
			 }
		}
   		var url ="/app118/codeAction/toUpdCode?codeId="+id;
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:520,
  				  isResize:true,
  				  width: 650, 
  				  title: '编辑代码'
  		}); 
   	 }
   	
   	 //删除代码
   	 function delCode(){
   		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行");
			 return;
		}else{
		 var ids="";
		 var len=rowid.length;
		 for(var j=0;j<len;j++){
		   if(j!=len-1){
			   ids+=rowid[j].codeId+",";			
			}else{
				ids+=rowid[j].codeId;
			}
		 }
		 var url="/app118/codeAction/delCode";
		 
		  $.ligerDialog.confirm('确认删除选择的代码吗？',function(yes){
			  if(yes){
		      		$.ajax({
			      			type:'POST',
							url:url,
							data:{ids:ids},
							dataType:'json',
			      			success:function(msg){
			      				if(msg.flag=1){
			      					$.ligerDialog.success("删除成功。");
			      					g.loadData();
			      				}else{
			      					$.ligerDialog.error("删除失败。");
			      				}
			      			},
			      			error:function(){
			      				$.ligerDialog.error("操作失败。");
			      			}
			      		});
				}else{
				   g.loadData();
				}
			});
		} 
   	 }
   	 
   	 function importCode(){
   		 var url="/app118/codeAction/importCode";
   		$.ajax({
  			type:'POST',
			url:url,
			dataType:'json',
  			success:function(msg){
  				if(msg.flag=1){
  					$.ligerDialog.success("导入成功。");
  					g.loadData();
  				}else{
  					$.ligerDialog.error("导入失败。");
  				}
  			},
  			error:function(){
  				$.ligerDialog.error("操作失败。");
  			}
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
		  	          { display: '代码标识', name: 'codeId', width: '10%'},
	  	              { display: '所属种类', name: 'typeCn', width: '10%' },
	  	              { display: '代码类别名称', name: 'codeName', width: '10%'},
	  	              { display: '代码类别编码', name: 'codeValue', width: '10%'},
	  	              { display: '父代码类别名称', name: 'pCodeName', width: '15%'},
	  	              { display: '状态', name: 'status', width: '10%' },
	  	              { display: '创建时间', name: 'createTime', width: '15%',type: "date", format:"yyyy-mm-dd HH:mm:ss"},
	  	              { display: '备注', name: 'descr', width: '25%' }
  	              ], url:url,dataAction:"server" , pageSize:15 ,rownumbers:true,pageParmName:"curNo",pagesizeParmName:"curSize"
  	          });
  		
  	    $("#pageloading").hide();
  	}
</script>
</body>
</html>