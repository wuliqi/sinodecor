<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="cn.app118.constants.SystemConstant"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>接口测试页</title>
</head>
<body>


<a href="/app/iUserAction/login?loginName=18810790739&password=1">登录接口</a><br/>
<a href="/app/iWeatherAction/getWeatherInfo?cid=39.93:116.40">天气预报接口</a><br/>
<a href="/app/iWeatherAction/queryWeather">新天气信息接口</a><br/>
<a href="/app/iPM25ForecastAction/queryPM25Forecast?city=beijing">雾霾预报</a><br/>
<a href="/app/stockAction/queryStockInfo?stockCode=s_sz399001,s_sh000001,sz000100">股票接口</a><br/>


<br/>
<!-- <a href="/app118/codeAction/insertCodeByBatch">批量导入行政区域</a><br/> -->
<a href="http://api.thinkpage.cn/weather/">心知官网</a><br/>
<a href="http://www.stateair.net/web/post/1/1.html">美使馆官网</a><br/>


</body>
</html>