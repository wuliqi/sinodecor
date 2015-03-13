<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<script src="/resource/lib/ligerUI/js/plugins/ligerGrid.js"
	type="text/javascript"></script>
<LINK rel=stylesheet type=text/css
	href="/resource/lib/ligerUI/skins/ligerui-icons.css">
<SCRIPT type=text/javascript
	src="/resource/lib/ligerUI/js/plugins/ligerToolBar.js"></SCRIPT>
</head>
<body>
	<div class="l-form">
		<form action="" id="form1" method="post">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td class="tdtitle">
					角色名：
				</td>
				<td class="tdtext">
					<input type="text" name="roleName" value="${roleName}" id="roleName" style="width: 220px;"/>
				</td>
				<td class="tdtitle">使用状态：</td>
				<td class="tdtext">
					<select style="width: 220px;" name="isactive" id="isactive">
						<option value="" >---请选择---</option>
						<option value="1">启用</option>
						<option value="0">废弃</option>
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
	
	<!-- style="float:right;" -->
	<div id="toptoolbar" class="l-toolbar" ligeruiid="toptoolbar" style="width:99%;">
		<div class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon"
			toolbarid="item-1" jQuery1405912963467="22" onclick="addRole();">
			<SPAN>增加角色</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-add"></div>
		</div>
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-2"
			jQuery1405912963467="23" onclick="toUpdRole();">
			<SPAN>&nbsp;修改角色</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-modify"></div>
		</div>
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-3"
			jQuery1405912963467="24" onclick="deleteRole();">
			<SPAN>&nbsp;删除角色</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-delete"></div>
		</div>
	</div>
	
	<div id="maingrid" ></div>
	 
<script type="text/javascript">
     var url = "/app118/roleAction/listRole";
     $(function (){
     	init(url);
     });
     
     //重置条件
     function forClean(){
     	$("#roleName").val('');
     	$("#isactive").val('');
     }
   	 //查询
   	 function forSearch(){
   		var roleName=$("#roleName").val();
   		var isactive=$("#isactive").val();
   	    var params="?roleName="+encodeURI(roleName)+"&isactive="+isactive;
   		init(url+params); 
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
  	              { display: '角色标识', name: 'roleId', width: '10%'},
  	              { display: '角色名', name: 'roleName', width: '15%'},
  	              { display: '使用状态', name: 'isactive', width: '15%' },
  	              { display: '用户数', name: 'userCount', width: '19%' },
  	              { display: '创建时间', name: 'createTime', width: '20%',type: "date", format:"yyyy-mm-dd HH:mm:ss"},
  	              { display: '排序', name: 'sort', width: '18%' }
  	              ], url:url,dataAction:"server" , pageSize:15 ,rownumbers:true,pageParmName:"curNo",pagesizeParmName:"curSize"
  	          });
  		
  	    $("#pageloading").hide();
  	}
  	
  	//增加角色
  	function addRole(){
  		var url ="/app118/roleAction/toAddRole";
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:550,
  				  isResize:true,
  				  width: 480, 
  				  title: '增加角色'
  		});
  	}


     //关闭对话框
  	function closeDialog(){
  		dialog.hide();
  		g.loadData();
  	}
  	
 	 //删除用户
  	 function deleteRole(){
  		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行");
			 return;
		}else{
		 var ids="";
		 var len=rowid.length;
		 for(var j=0;j<len;j++){
		   if(j!=len-1){
			   ids+=rowid[j].roleId+",";			
			}else{
				ids+=rowid[j].roleId;
			}
		 }
		 var url="/app118/roleAction/deleteRole";
		  $.ligerDialog.confirm('确认删除选择的角色及授予的菜单权限吗？',function(yes){
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
			})
		} 
  	 }
  	 
 	 //修改角色
   	 function toUpdRole(){
   		var params;
   		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行。");
			 return;
		}else{
		 var id="";
		 var len=rowid.length;
			 if(len>1){
			 	$.ligerDialog.warn("请只选择一行。");
				return;
			 }else{
				id+=rowid[0].roleId;
				
				params="?roleId="+id;

			 }
		}
   		var url ="/app118/roleAction/toUpdRole"+params;
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:550,
  				  isResize:true,
  				  width: 480, 
  				  title: '修改角色'
  		}); 
   	 }
</script>
</body>
</html>