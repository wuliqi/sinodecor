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
<SCRIPT type=text/javascript  src="/resource/lib/ligerUI/js/plugins/ligerComboBox.js"></SCRIPT>

<!-- 百度ueditor开始 -->
<script type="text/javascript" charset="utf-8" src="/resource/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/resource/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="/resource/ueditor/lang/zh-cn/zh-cn.js"></script>
<!-- 百度ueditor结束-->


</head>
<body onload="tips();">
<form action="" method="post" enctype="multipart/form-data">
	<table border="0">
		<tr>
			<td  class="tdtitle">标题：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="newsTitle" id="newsTitle"  style="width: 220px;"/><font color="red">*</font>
			</td>
			<td  class="tdtitle">关键字：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="newsKeyword" id="newsKeyword"  style="width: 220px;"/>
			</td>
		</tr>
		
		<tr>
			<td  class="tdtitle">摘要：</td>
			<td  nowrap="nowrap" class="tdtext">
				<input type="text" name="newsBrief" id="newsBrief"  style="width: 220px;"/>
			</td>
			<td  class="tdtitle">缩略图：</td>
			<td  nowrap="nowrap" class="tdtext">
				<input type="file" name="file" id="file"  style="width: 220px;"/>
			</td>
		</tr>
		
		<tr>
			<td  class="tdtitle">所属组织机构：</td>
			<td  nowrap="nowrap" class="tdtext">
				<select name="orgId" id="orgId" style="width: 220px;height: 20px;" ${disabled}>
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${orgMap}">
						<option value="${oneMap.key }" <c:if test="${orgId==oneMap.key }">selected </c:if> >${oneMap.value}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
			<td  class="tdtitle">分类：</td>
			<td  class="tdtext" nowrap="nowrap">
				<select name="newsCategory" id="newsCategory"  style="width: 220px;height: 20px;">
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${codeList}">
						<option value="${oneMap.codeId }" <c:if test="${oneMap.codeId eq orgType}">selected</c:if> >${oneMap.codeName}</option>
					</c:forEach>
				</select>
				
				
				<font color="red">*</font>
			</td>
			
			
		</tr>
		<tr>
			<td class="tdtitle">来源：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="newsSource" id="newsSource"  style="width: 220px;"/><font color="red">*</font>
			</td>
			<td class="tdtitle">是否置顶：</td>
			<td nowrap="nowrap">
				<input type="radio" name="isStick" id="isStick" value="1" />是
				<input type="radio" name="isStick" id="isStick" value="0" checked="checked" />否<font color="red">*</font>
				
			</td>
		</tr>
		
		
		<tr>
			<td class="tdtitle">
				开始时间：
			</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="beginTime"  id="beginTime"/>
			</td>
			<td class="tdtitle">结束时间：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="endTime" value="${endTime}" id="endTime"/>
			</td>
		</tr>
		
	</table>
	<div>
		<div style="padding-left: 122px;font-size:12px;  line-height:24px; color:#666666;height: 24px;">
			内容：
		</div>
		<div align="center" style="width: 98%;">
			    <input type="hidden" name="newsContent" id="newsContent"  style="width: 220px;"/> 
				<script id="editor" type="text/plain" style="width:800px;height:250px;"></script>
		</div>	
	</div>
	<br style="display:inline; line-height:6px; "/>
	<table border="0" align="center" >
		<tr align="center">
			<td colspan="2" > 
				<input type="button" value="保存" id="submitBtn" class="l-button l-button-submit"/> 
			</td>
		</tr>
	</table>
</form>
</body>
<script language="javascript">
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	var ue = UE.getEditor('editor');
	$(function() {
		
		$("#beginTime").ligerDateEditor({
			showTime : true,
			labelWidth :200,
			labelAlign : 'left'
		});
	 	$("#endTime").ligerDateEditor({
			showTime : true,
			labelWidth : 100,
			labelAlign : 'left'
		});
	 	
	 	$("#beginTime").ligerGetDateEditorManager().setValue('${beginTime}');
	 	$("#endTime").ligerGetDateEditorManager().setValue('${endTime}');
		
		
		var url = "/app118/newsAction/addNews";
		$("#submitBtn").click(function() {
			var content=ue.getContent();
			$("#newsContent").val(content);
		 	
		 	var newsTitle=$("#newsTitle").val();
			var orgId=$("#orgId").val();
			var newsCategory=$("#newsCategory").val();
			var newsSource=$("#newsSource").val();
			var isStick=$("#isStick").val();
		 	var beginTime=$("#beginTime").val();
		 	var endTime=$("#endTime").val();
		 	
		
			if(newsTitle==null||trim(newsTitle)==''){
				$.ligerDialog.warn("标题不能为空。");
				$("#newsTitle").focus();
				return;
			}
			if(orgId==null||trim(orgId)==''){
				$.ligerDialog.warn("所属组织机构不能为空，请选择。");
				$("#orgId").focus();
				return;
			}
			if(newsCategory==null||trim(newsCategory)==''){
				$.ligerDialog.warn("分类不能为空。");
				$("#newsCategory").focus();
				return;
			}
			if(newsSource==null||trim(newsSource)==''){
				$.ligerDialog.warn("来源不能为空，请选择。");
				$("#newsSource").focus();
				return;
			}
			var val=$('input:radio[name="isStick"]:checked').val();
			if(val==null){
				$.ligerDialog.warn("是否置顶不能为空，请选择。");
				$("#isStick").focus();
				return;
			}
			if(beginTime==null||trim(beginTime)==''){
				$.ligerDialog.warn("开始时间不能为空。");
				$("#beginTime").focus();
				return;
			}
			if(endTime==null||trim(endTime)==''){
				$.ligerDialog.warn("结束时间不能为空。");
				$("#endTime").focus();
				return;
			}
			$("#orgId").removeAttr("disabled"); 
			document.forms[0].action = url;
	 		document.forms[0].submit();	 
		});
	});
	
	//提示信息
	function tips(){
		var msg ="${message}";
		if(!(msg=='null'||msg=='')){
			if(msg == "success"){
				$.ligerDialog.alert("提示：增加内容成功。",function(){
					parent.closeDialog();	
				});
			}else if(msg=="beyondFileSize"){
				$.ligerDialog.alert("提示：增加内容失败,缩略图不能超过15M。",function(){
					parent.closeDialog();	
				});
			}else{
				$.ligerDialog.alert("提示：增加内容失败。",function(){
					parent.closeDialog();	
				});
			}
		}
	}	
</script>
</html>