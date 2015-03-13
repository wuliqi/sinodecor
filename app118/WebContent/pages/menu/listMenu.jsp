<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单列表</title>
<!-- 引用本页面JS、CSS样式静态资源 -->
<%@include file="/pages/common/common.jsp"%>
<link href="/resource/lib/ligerUI/skins/Aqua/css/ligerui-tree.css" rel="stylesheet" type="text/css" />    
<script src="/resource/lib/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>

</head>
<body>


	<div id="target1" style="height:300px;position:relative;display:none;overflow:auto;">
				<div>
			     	<ul id="tree1" style="color:#000;background:#f2f2f2;solid #cacaca;"></ul>
		    	</div>
 	</div>	



	
<script type="text/javascript">
	var menuId = "";
	var menuName = "";
	var tree;
	var treeData = [];

   
	$.ajax({
		url:"/app118/menuAction/listMenu",
		//data:{sysType:"01", isactive:"1"},
		type:"POST",
		async:false, 
		dataType:"json",
		beforeSend:function(XMLHttpRequest){
		},
		success:function(result){
			var array=result.menuList;
			for(var i=0;i<array.length;i++){
				treeData.push({id:array[i].menuId,pid:array[i].pMenuCode,text:array[i].menuName,menuCode:array[i].menuCode, isexpand:true});
			}
			tree = $("#tree1").ligerTree({
				data: treeData,
				checkbox: false, 
				single: true,
				nodeWidth:150,
				onClick: treeOnClick,
			    idFieldName :'id',
			    parentIDFieldName :'pid'
			});
		},
		error:function(){
			$.ligerDialog.warn("温馨提示，系统加载异常！");
		}
	});
    
    
   function treeOnClick(node){
	   menuId = node.data.id;
	   menuName = node.data.text;
   }
</script>
</body>
</html>