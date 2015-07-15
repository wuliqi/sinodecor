<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
				<td class="tdtitle">手机号码：</td>
				<td class="tdtext">
					<input type="text" name="phoneNumber" value="${phoneNumber}" id="phoneNumber" style="width: 128px;height: 20px;"/>
				</td>
				<td class="tdtitle">分类：</td>
				<td class="tdtext">
					<select name="category" id="category"  style="width: 128px;height: 20px;">
					<option value="">---请选择---</option>
					<option value="1" <c:if test="${category eq '1'}">selected</c:if> >在线反馈</option>
					<option value="3" <c:if test="${category eq '3'}">selected</c:if>>在线留言</option>
				</select>
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
		<div class=l-bar-separator></div>
		<div class="l-toolbar-item l-panel-btn" toolbarid="item-3"
			jQuery1405912963467="24" onclick="delFeedback();">
			<SPAN>&nbsp;删除内容</SPAN>
			<div class=l-panel-btn-l></div>
			<div class=l-panel-btn-r></div>
			<div class="l-icon l-icon-delete"></div>
		</div>
	</div> 
	<!-- 工具栏结束 -->
	<div id="maingrid" ></div>
	 
<script type="text/javascript">
	var dialog=null;//弹出对话框
     var url = "/app118/feedbackAction/listFeedbackByPager";
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
     	$("#phoneNumber").val('');
     	$("#category").val('');
     	$("#fromCreateTime").val('');
     	$("#toCreateTime").val('');
     }
     
   	 //查询内容
   	 function forSearch(){
   		var phoneNumber=$("#phoneNumber").val();
   		var category=$("#category").val();
   		var fromCreateTime= $("#fromCreateTime").val();
     	var toCreateTime=$("#toCreateTime").val();
     	
   	    var params="?phoneNumber="+encodeURI(phoneNumber)+"&category="+encodeURI(category)+"&fromCreateTime="+fromCreateTime+"&toCreateTime="+toCreateTime;
   	    init(url+params); 
   	 }
    
   	 //删除
   	 function delFeedback(){
   		var rowid=g.getSelecteds();//获得选中行ID
		if(rowid.length==0){
			 $.ligerDialog.warn("请至少选择一行");
			 return;
		}else{
		 var ids="";
		 var len=rowid.length;
		 for(var j=0;j<len;j++){
		    if(j!=len-1){
			   ids+=rowid[j].fdId+",";			
			}else{
				ids+=rowid[j].fdId;
			}
		 }
		 var url="/app118/feedbackAction/delFeedback";
		  $.ligerDialog.confirm('确认删除选择的吗？',function(yes){
			  if(yes){
		      		$.ajax({
			      			type:'POST',
							url:url,
							data:{ids:ids},
							dataType:'json',
			      			success:function(msg){
			      				if(msg.flag==1){
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
		  	          { display: '手机号码', name: 'phoneNumber', width: '17%'},
	  	              { display: '分类', name: 'categoryCn', width: '9%'},
	  	              { display: '反馈/留言', name: 'content', width: '60%'},
	  	              { display: '创建时间', name: 'createTime', width: '13%'}
	  	            
  	              ], url:url,dataAction:"server" , pageSize:15 ,rownumbers:true,pageParmName:"curNo",pagesizeParmName:"curSize"
  	          });
  		
  	    $("#pageloading").hide();
  	}
</script>
</body>
</html>