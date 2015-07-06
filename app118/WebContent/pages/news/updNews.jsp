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
				<input type="text" name="newsTitle" id="newsTitle"  value="${news.newsTitle}" style="width: 220px;"/><font color="red">*</font>
				<input type="hidden" name="newsId" id="newsId" value="${news.newsId}"/>
			</td>
			<td  class="tdtitle">关键字：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="newsKeyword" id="newsKeyword" value="${news.newsKeyword}"  style="width: 220px;"/>
			</td>
		</tr>
		
		<tr>
			<td  class="tdtitle">摘要：</td>
			<td  nowrap="nowrap" class="tdtext">
				<input type="text" name="newsBrief" id="newsBrief" value="${news.newsBrief}"  style="width: 220px;"/>
			</td>
			<td  class="tdtitle"><a href="#" onclick="viewNewsThumb();">缩略图：</a></td>
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
						<option value="${oneMap.key }" <c:if test="${news.orgId==oneMap.key }">selected </c:if> >${oneMap.value}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
			<td  class="tdtitle">分类：</td>
			<td  class="tdtext" nowrap="nowrap">
				<select name="newsCategory" id="newsCategory"  style="width: 220px;height: 20px;">
					<option value="">---请选择---</option>
					<c:forEach var="oneMap" items="${codeList}">
						<option value="${oneMap.codeId }" <c:if test="${oneMap.codeId eq news.newsCategory}">selected</c:if> >${oneMap.codeName}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tdtitle">来源：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="newsSource" id="newsSource" value="${news.newsSource}" style="width: 220px;"/><font color="red">*</font>
			</td>
			<td class="tdtitle">是否置顶：</td>
			<td nowrap="nowrap">
				<input type="radio" name="isStick" id="isStick" value="1" <c:if test="${news.isStick eq '1'}">checked="checked"</c:if>/>是
				<input type="radio" name="isStick" id="isStick" value="0" <c:if test="${news.isStick eq '0'}">checked="checked"</c:if>/>否<font color="red">*</font>
				
			</td>
		</tr>
		
		
		<tr>
			<td class="tdtitle">
				开始时间：
			</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="beginTime"  id="beginTime" value="${beginTime}"/>
			</td>
			<td class="tdtitle">结束时间：</td>
			<td class="tdtext" nowrap="nowrap">
				<input type="text" name="endTime" id="endTime" value="${endTime}" />
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
	 	ue.setContent('${news.newsContent}');
		
		var url = "/app118/newsAction/updNews";
		$("#submitBtn").click(function() {
			var content=ue.getContent();
			$("#newsContent").val(content);
		 	var beginTime=$("#beginTime").val();
		 	
		 	/*var deviceName=$("#deviceName").val();
			var sex=$("#sex").val();
			var carCategory=$("#carCategory").val();
			var realName=$("#realName").val();
			var carBrand=$("#carBrand").val();
			var carSeries=$("#carSeries").val();
			var carType=$("#carType").val();
			var carStyle=$("#carStyle").val();
			var carYear=$("#carYear").val();
			var deviceMac=$("#deviceMac").val();
			var saleUserId=$("#saleUserId").val();
			var orgId=$("#orgId").val();
			if(loginName==null||trim(loginName)==''){
				$.ligerDialog.warn("手机号不能为空。");
				$("#loginName").focus();
				return;
			}else{
				if(!checkPhone(loginName)){
					$.ligerDialog.warn(loginName+"，手机号码不正确。");
					$("#loginName").focus();
					return;
				}
			}
			if(realName==null||trim(realName)==''){
				$.ligerDialog.warn("真实姓名不能为空。");
				$("#realName").focus();
				return;
			}
			if(sex==null||trim(sex)==''){
				$.ligerDialog.warn("性别不能为空，请选择。");
				$("#sex").focus();
				return;
			}
			
			if(orgId==null||trim(orgId)==''){
				$.ligerDialog.warn("所属门店不能为空，请选择。");
				$("#orgId").focus();
				return;
			}
			var val=$('input:radio[name="carCategory"]:checked').val();
			if(val==null){
				$.ligerDialog.warn("汽车种类不能为空，请选择。");
				$("#carCategory").focus();
				return;
			}
			
			if(carBrand==null||trim(carBrand)==''){
				$.ligerDialog.warn("汽车品牌不能为空，请选择。");
				$("#carBrand").focus();
				return;
			}
			if('---请选择---'==carSeries||carSeries==null||trim(carSeries)==''){
				$.ligerDialog.warn("汽车车系不能为空，请选择。");
				$("#carSeries").focus();
				return;
			}
			if('---请选择---'==carType||carType==null||trim(carType)==''){
				$.ligerDialog.warn("汽车型号不能为空，请选择。");
				$("#carType").focus();
				return;
			}
			if(carStyle==null||trim(carStyle)==''){
				$.ligerDialog.warn("汽车款式不能为空。");
				$("#carStyle").focus();
				return;
			}
			if(carYear==null||trim(carYear)==''){
				$.ligerDialog.warn("汽车年份不能为空，请选择。");
				$("#carYear").focus();
				return;
			}
			if(deviceName==null||trim(deviceName)==''){
				$.ligerDialog.warn("汽车牌号不能为空。");
				$("#deviceName").focus();
				return;
			}else if(!isCarNumber(deviceName)){
				$.ligerDialog.warn(deviceName+",汽车牌号不正确。");
				$("#deviceName").focus();
				return;
			}
			if(deviceMac==null||trim(deviceMac)==''){
				$.ligerDialog.warn("设备Mac不能为空。");
				$("#deviceMac").focus();
				return;
			}else if(!isMacAddress(deviceMac)){
				$.ligerDialog.warn('Mac地址错误，Mac地址格式为00:17:EA:92:DC:27');
				$("#deviceMac").focus();
				return;
			}
			if("---请选择---"==saleUserId||saleUserId==null||trim(saleUserId)==''){
				$.ligerDialog.warn("销售人员不能为空，请选择。");
				$("#saleUserId").focus();
				return;
			}
			
			$("#orgId").removeAttr("disabled"); */
			document.forms[0].action = url;
	 		document.forms[0].submit();	 

		});

	});
	
	//提示信息
	function tips(){
		var msg ="${message}";
		if(!(msg=='null'||msg=='')){
			if(msg == "success"){
				$.ligerDialog.alert("提示：修改内容成功。",function(){
					parent.closeDialog();	
				});
			}else if(msg=="beyondFileSize"){
				$.ligerDialog.alert("提示：修改内容失败,缩略图不能超过15M。",function(){
					parent.closeDialog();	
				});
			}else{
				$.ligerDialog.alert("提示：修改内容失败。",function(){
					parent.closeDialog();	
				});
			}
		}
	}
	
	function viewNewsThumb(){
		//'${news.newsThumbnail}'
		var url ="${path}"+"${news.newsThumbnail}";
		alert(url);
  		dialog=$.ligerDialog.open({ 
  				  url:url, 
  				  height:200,
  				  isResize:true,
  				  width: 300, 
  				  title: '查看缩略图'
  		}); 
	}
</script>
</html>