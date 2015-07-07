<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<script type="text/javascript" charset="utf-8" src="/resource/ueditor/ueditor.parse.min.js"> </script>

<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="/resource/ueditor/lang/zh-cn/zh-cn.js"></script>
<!-- 百度ueditor结束-->
</head>
<body>
	<table border="1" width="98%">
		<tr>
			<td  >标题：</td>
			<td >
				${news.newsTitle}
			</td>
			<td  >关键字：</td>
			<td >
				${news.newsKeyword}
			</td>
		</tr>
		
		<tr>
			<td  >摘要：</td>
			<td  >
				${news.newsBrief}
			</td>
			<td  >
				缩略图：
			</td>
			<td  >
				
				<c:choose>
					<c:when test="${!empty  news.newsThumbnail}">
						<img src="/app118/newsAction/showImage?newsThumbnail=${news.newsThumbnail}" border="0"/>
					</c:when>
					<c:otherwise>
						
					</c:otherwise>
				</c:choose>
				
			</td>
		</tr>
		
		<tr>
			<td  >所属组织机构：</td>
			<td  >
				${orgName}
			</td>
			<td  >分类：</td>
			<td  >
				${codeName }
			</td>
		</tr>
		<tr>
			<td >来源：</td>
			<td >
				${news.newsSource}
			</td>
			<td >是否置顶：</td>
			<td nowrap="nowrap">
				<c:if test="${news.isStick eq '1'}">是</c:if>
				<c:if test="${news.isStick eq '0'}">否</c:if>
			</td>
		</tr>
		<tr>
			<td >
				开始时间：
			</td>
			<td >
				${beginTime}
			</td>
			<td >结束时间：</td>
			<td >
				${endTime}
			</td>
		</tr>
		<tr>
			<td style="padding-left: 122px;font-size:12px;  line-height:24px; color:#666666;height: 24px;">
				内容：
			</td>
			<td colspan="3">
				<div style="width: 98%;" class="content">
					${news.newsContent}
				</div>
			</td>
				
		</tr>
	</table>
</body>
<script language="javascript">
	$(function() {
	 	uParse(".content", {
	 	    rootPath: '/resource/ueditor/'
	 	})
	});
</script>
</html>