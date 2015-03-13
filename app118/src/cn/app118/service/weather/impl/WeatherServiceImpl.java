/**
 * @(#)UserAction.java	06/13/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-06-13
 */
package cn.app118.service.weather.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.app118.constants.PMConstant;
import cn.app118.constants.SystemConstant;
import cn.app118.dao.weather.WeatherMapper;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.FastJsonUtil;
import cn.app118.framework.util.HttpUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.framework.util.Trans2PinYinUtil;
import cn.app118.framework.util.WebScraping;
import cn.app118.model.Log;
import cn.app118.model.User;
import cn.app118.model.Weather;
import cn.app118.service.log.ILogService;
import cn.app118.service.user.IUserService;
import cn.app118.service.weather.IPM25ForecastService;
import cn.app118.service.weather.IWeatherService;

/**
 * 天气预报数据服务类
 * 
 * 
 * @author 吴理琪
 * 
 */
@Service("weatherService")
public class WeatherServiceImpl implements IWeatherService {
	private Logger log = Logger.getLogger(this.getClass());// 日志
	private String url = SystemConstant.MISSION_CHINA_WEATHER_URL;// 请求的URL
	private String charset = "UTF-8";// 页面编码
	private String startFlag = "<td class=\"aqi_5\">";// 开始标签
	private String endFlag = "</td>";// 结束标签
	private String contentResponse;// http请求返回的内容
	private String content;// 截取的内容块

	@Resource
	private WeatherMapper weatherMapper;//天气管理Dao
	
	@Resource
	private ILogService logService;//日志服务类
	
	@Resource
	private IUserService userService;// 用户服务类
	
	@Resource
	private IPM25ForecastService pM25ForecastService;
	
	/**
	 * 从网络上抓取天气预报数据
	 * 
	 * @author 吴理琪
	 * @param 无
	 * @return Map 
	 * airbestAQI：AQI 
	 * airbestPM25:PM2.5 
	 * airbestPM10:PM10
	 * airbestCO:CO 
	 * airbestNO2:NO2 
	 * airbestO3:O3 
	 * airbestSO2:SO2
	 * airbestQuality:空气质量 
	 * airbestWeatherText:天气情况文字
	 * airbestTemperature:当前实时温度 
	 * airbestHumidity：湿度。单位：百分比%
	 * airbestWindScale：风力等级。根据风速计算的风力等级 airbestLastUpdate：请求时系统时间
	 * airbestUSEmbassyAQI：美国大使馆天气预报AQI
	 * thinkPageWeather：心知天气JSON串
	 */
	@Override
	public Map<String, Object> getWeatherInfo(Map map) {
		String cid=map.get("cid")+"";//城市参数
		String userId=map.get("userId")+"";
		String ip=map.get("ip")+"";
		
		
		//log.info(userId+"城市cid参数："+cid);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Weather weather =new Weather();
		try {
			url = SystemConstant.MISSION_CHINA_WEATHER_URL;
			log.info("*******网络抓取美使馆数据开始，USEmbassyAQI URL:"+url);
			contentResponse = HttpUtil.sendGet(url, charset);
			startFlag="<table>";
			endFlag="</table>";
			content = WebScraping.getAreaContent(contentResponse.trim(),
					startFlag, endFlag);
			//System.out.println("######content:"+content);
			String[] strArray = content.split("<tr>");
			String publishTimeContent=strArray[1].trim();
			String[] publishTimeArray=publishTimeContent.split("<span>");
			String publishTime=WebScraping.outTag(publishTimeArray[1]).trim();
			//log.info("#############天气同步时美使馆发布时间："+publishTime+"###########"+publishTime.length());
			//publishTime=DateUtil.changerUsEmbassyAqiTime(publishTime);//纠正美使馆网站时间错误 TODO
			//log.info("#############天气同步时美使馆修改时间："+publishTime+"###########"+publishTime.length());
			Date airbestUSEmbassyAQIPublishTime=DateUtil.convertUsEmbassyAqiTime(publishTime,"");//美国大使馆天气预报AQI发布时间
			
			String aqiConcentration=WebScraping.outTag(strArray[5]).replaceAll("\\s*", "");
			int start=aqiConcentration.indexOf(":");
			aqiConcentration=aqiConcentration.substring(start+1,aqiConcentration.length()-5);
			
			Float aqiConcentrationFloat=Float.parseFloat(aqiConcentration);//Concentration: 24.0 µg/m³ 
			Float pm05Float=aqiConcentrationFloat*PMConstant.PM05/PMConstant.MULTIPLE;//当前是一分钟，除以3为21秒与手持表保持一致
			Float pm03Float=pm05Float*PMConstant.PM03;
			Float pm10Float=pm05Float/PMConstant.PM10;
			Float pm25Float=pm10Float/PMConstant.PM25;
			
			jsonMap.put("airbestParticlePm03", pm03Float.intValue());
			jsonMap.put("airbestParticlePm25", pm25Float.intValue());
			
			//System.out.println("===formatEnDate2Date:"+airbestUSEmbassyAQIPublishTime);
			content = WebScraping.outTag(strArray[2]).replaceAll("\\s*", "");
			//System.out.println("e:"+content);
			String[] aqiArray = content.split("AQI");
			String aqi = aqiArray[0];
			log.info("*******网络抓取美使馆数据结束：airbestUSEmbassyAQI:"+aqi);

			jsonMap.put("message", SystemConstant.MSG_SUCCESS);// 1、表示成功
			jsonMap.put("tips", "天气预报查询成功。");
			jsonMap.put("airbestUSEmbassyAQI", aqi);// 美国大使馆天气预报AQI
			jsonMap.put("airbestUSEmbassyAQIPublishTime", airbestUSEmbassyAQIPublishTime);// 美国大使馆天气预报AQI发布时间
			jsonMap.put("usePublishTimeInit", publishTime);// 美国大使馆天气预报AQI发布时间,未处理的时间
			jsonMap.put("airbestUSEmbassyAQIConcentration", aqiConcentration);// 美国大使馆天气预报AQI PM2.5一小时平均含量 µg/m³

			String thinkWeatherUrl = SystemConstant.THINKPAGE_WEATHER_URL;
			if(StringUtil.isEmpty(cid)){
				thinkWeatherUrl = SystemConstant.THINKPAGE_WEATHER_URL;
			}else{
				thinkWeatherUrl = SystemConstant.THINKPAGE_WEATHER_URL+"?cid="+URLEncoder.encode(cid,charset);
			}
			log.info("*******网络抓取心知数据开始，thinkWeatherUrl："+thinkWeatherUrl);
			String startTag = "thinkpage_weather_res = {";
			String endTag = "thinkpage_weather_locationpath =";
			contentResponse = HttpUtil.sendGet(thinkWeatherUrl, charset);
			int begin = contentResponse.indexOf(startTag);
			int end = contentResponse.indexOf(endTag);
			String json = contentResponse.substring(begin, end);
			json = json.replaceAll(";", "");
			String[] josnArray = json.split("=");
			String thinkPageWeather=josnArray[1];
			Map<String, Object> result = FastJsonUtil.toMap(thinkPageWeather);
			//jsonMap.put("thinkPageWeather", result);
			List<Map<String, Object>> weathers = (List<Map<String, Object>>) result.get("Weathers");
			Map<String, Object> current = (Map<String, Object>) weathers.get(0).get("Current");
			
			String cityName=weathers.get(0).get("CityName")+"";
			String lastBuildDate=weathers.get(0).get("LastBuildDate")+"";
			String cityId=weathers.get(0).get("CityId")+"";
			
			Map<String, Object> airQuality = (Map<String, Object>) weathers.get(0).get("AirQuality");
			if(airQuality!=null){
				Map<String, Object> cityInfo = (Map<String, Object>) airQuality.get("CityInfo");
				Object aqi2 = cityInfo.get("AQI");
				Object co = cityInfo.get("CO");
				Object pm25 = cityInfo.get("PM25");
				Object pm10 = cityInfo.get("PM10");
				Object no2 = cityInfo.get("NO2");
				Object o3 = cityInfo.get("O3");
				Object so2 = cityInfo.get("SO2");
				Object quality = cityInfo.get("Quality");
				
				jsonMap.put("airbestAQI", aqi2);
				jsonMap.put("airbestPM25", pm25);
				jsonMap.put("airbestPM10", pm10);
				jsonMap.put("airbestCO", co);
				jsonMap.put("airbestNO2", no2);
				jsonMap.put("airbestO3", o3);
				jsonMap.put("airbestSO2", so2);
				jsonMap.put("airbestQuality", quality);
				
				weather.setAqi(aqi2+"");
				weather.setCo(co+"");
				weather.setNo2(no2+"");
				weather.setO3(o3+"");
				weather.setSo2(so2+"");
				weather.setPm10(pm10+"");
				weather.setPm25(pm25+"");
				weather.setQuality(quality+"");
			}else{
				jsonMap.put("airbestAQI", "-");
				jsonMap.put("airbestPM25", "-");
				jsonMap.put("airbestPM10", "-");
				jsonMap.put("airbestCO", "-");
				jsonMap.put("airbestNO2", "-");
				jsonMap.put("airbestO3", "-");
				jsonMap.put("airbestSO2", "-");
				jsonMap.put("airbestQuality", "-");
				
				weather.setAqi("-");
				weather.setCo("-");
				weather.setNo2("-");
				weather.setO3("-");
				weather.setSo2("-");
				weather.setPm10("-");
				weather.setPm25("-");
				weather.setQuality("-");
			}
			
			//一周内天气预报
			List<Map<String,Object>> forecastList=(List<Map<String, Object>>) weathers.get(0).get("Forecast");
			/*List<Map<String,Object>> towDayforecastList=new ArrayList<Map<String,Object>>();
			if(forecastList.size()>0){
				towDayforecastList.add(forecastList.get(0));
				towDayforecastList.add(forecastList.get(1));
			}*/
			//log.info("心知天气预报URL："+thinkWeatherUrl+"最新发布时间："+lastBuildDate);
			double speed = Double.valueOf(current.get("Speed") + "");
			int level = PMConstant.windSpeed2WindLevel(speed);
		
			Object weatherText = current.get("Text");
			Object temperature = current.get("Temperature");
			String humidity = current.get("Humidity") + "%";
			String windScale = current.get("Direction") + "风"+ level + "级";
			String code = current.get("Code")+"";
			Date lastUpdate = new Date(lastBuildDate);
			
			jsonMap.put("airbestCityName", cityName);
			jsonMap.put("airbestWeatherText", weatherText);// 天气情况文字
			jsonMap.put("airbestTemperature", temperature);// 当前实时温度
			jsonMap.put("airbestHumidity", humidity);// 湿度。单位：百分比%
			jsonMap.put("airbestWindScale", windScale);// 风力等级。根据风速计算的风力等级
			jsonMap.put("airbestWindLevel", level);// 风力等级。根据风速计算的风力等级
			jsonMap.put("airbestLastUpdate", lastUpdate);//发布时间
			log.info("*******网络抓取心知数据结束。");
			Map w=new HashMap();
			w.put("lastUpdate", lastUpdate);
			w.put("remark2", cityName);
			int count=selectBySelectiveCount(w);
			if(count==0){
				log.info("*******数据准备入库...");
				weather.setLastUpdate(lastUpdate);
				weather.setUsEmbassyAqi(aqi);
				weather.setUsEmbassyAqiTime(airbestUSEmbassyAQIPublishTime);
				weather.setHumidity(humidity);
				weather.setTemperature(temperature+"");
				weather.setWeatherText(weatherText+"");
				weather.setWindScale(windScale);
				weather.setThinkPageWeather(thinkPageWeather);
				weather.setRemark1(cityId);//城市编号
				weather.setRemark2(cityName);//城市名称
				weather.setForecast(forecastList.toString());
				weather.setUsEmbassyAqiPm25(aqiConcentration);
				weather.setWeatherCode(code);//天气图标编码
				if(!StringUtil.isEmpty(userId)){
					weather.setUserId(Integer.valueOf(userId));//用户标识
				}
				int flag=addWeather(weather);
				if(flag>0){
					log.info("*******数据入库成功。");
				}else{
					log.info("*******数据入库失败。");
				}
				log.info("###:"+userId+",城市cid参数："+cid+",从网络抓取天气信息"+(flag==1?"成功。":"失败。")+lastUpdate);
			}else{
				log.info("###:"+userId+",城市cid参数："+cid+",已经从网络抓取了天气信息。"+DateUtil.getFormatDate(lastUpdate, ""));
			}

			//日志
			if(!StringUtil.isEmpty(userId)){
				User existUser=userService.selectByPrimaryKey(Integer.valueOf(userId));
				if(existUser!=null){
					Log opLog=new Log();//日志
					opLog.setUserId(existUser.getUserId());
					opLog.setLoginName(existUser.getLoginName());
					opLog.setLogType("3");// app
					opLog.setIpAddress(ip);
					opLog.setOpContent("{\"message\":\"请求天气信息成功。\"}");
					opLog.setOpTime(new Date());
					opLog.setLocalization(cid);
					opLog.setOrgId(existUser.getOrgId());
					logService.insert(opLog);
					
					//更新用户信息，修改最后登录时间,供系统消息推送使用
					User record=new User();
					record.setUserId(Integer.valueOf(userId));//用户标识
					record.setLastLoginTime(new Date());//最后访问时间
					record.setCity(cityName);//所在城市 
					userService.updateByPrimaryKeySelective(record);
				}
			}
		} catch (Exception e) {
			log.info("天气预报查询异常："+e);
			jsonMap.put("message", SystemConstant.MSG_EXCEPTION);// 0、表示失败
			jsonMap.put("tips", "天气预报查询失败。");
		}
		refresh(jsonMap);//调用粒子计数器
		return jsonMap;
	}

	
	
	public void refresh(Map<String, Object> jsonMap) {
	 	log.info("###app端于："+DateUtil.getDateTime()+"，更新天气，同时采集PM各种粒子数开始...");
		try {
			Map<String,Object> weatherMap=jsonMap;
			String airbestUSEmbassyAQIConcentration=weatherMap.get("airbestUSEmbassyAQIConcentration")+"";
			String airbestUSEmbassyAQI=weatherMap.get("airbestUSEmbassyAQI")+"";
			Date airbestUSEmbassyAQIPublishTime=(Date)weatherMap.get("airbestUSEmbassyAQIPublishTime");
			String usePublishTimeInit=weatherMap.get("usePublishTimeInit")+"";//TODO 测试后删除
			String airbestAQI=weatherMap.get("airbestAQI")+"";
			Date airbestLastUpdate=(Date)weatherMap.get("airbestLastUpdate");
			
			Float aqi=Float.parseFloat(airbestUSEmbassyAQIConcentration);//Concentration: 24.0 µg/m³ 
			Float pm05=aqi*PMConstant.PM05/PMConstant.MULTIPLE;//当前是一分钟，除以3为21秒与手持表保持一致
			Float pm03=pm05*PMConstant.PM03;
			Float pm10=pm05/PMConstant.PM10;
			Float pm25=pm10/PMConstant.PM25;
			
		
	
		} catch (Exception e) {
			log.info("更新天气时插入异常："+e);
		}
		log.info("更新天气时自动采集PM各种粒子数结束。");
	}
	
	

	/**
	 * 往数据库中增加网络抓取的天气信息
	 * 
	 * @author 吴理琪
	 */
	@Override
	public int addWeather(Weather weather) {
		return weatherMapper.insert(weather);
	}
	/**
	 * 读取数据库中天气信息
	 * 
	 * @author 吴理琪
	 */
	@Override
	public List<Weather> selectWeather(Map weather) {
		return weatherMapper.selectBySelective(weather);
	}

	@Override
	public int selectBySelectiveCount(Map record) {
		return weatherMapper.selectBySelectiveCount(record);
	}

	/**
	 * 新天气信息请求接口
	 * 新天气信息请求接口：含天气一天内过去24小时天气信息
	 * @param map  
	 * cid:所在地的经纬度：如39.93:116.40 
	 * userId：用户标识
	 * ip：请求的IP地址
	 * @return
	 */
	@Override
	public Map<String, Object> queryWeather(Map map) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String cid=map.get("cid")+"";//城市参数
		String userId=map.get("userId")+"";
		String ip=map.get("ip")+"";
		String time=map.get("time")+"";
		if(StringUtil.isEmpty(time)){
			time=DateUtil.getFormatDate(DateUtil.subtractDate(new Date(), 1), "");
		}else{
			time=DateUtil.getFormatDate(Long.valueOf(time), "");
		}
		if(StringUtil.isEmpty(cid)){//若无经纬，采用IP
			cid=ip;
		}
		String remark2="";
		try {
		
			remark2=convertLongitudeAndLatitude2CityName(cid);
			remark2=change2Beijing(remark2);
			String clauseSql="";
			Map w=new HashMap();
			if(remark2.equals("北京")){
				clauseSql="group by us_embassy_aqi_time order by weather_id desc ";
				w.put("usEmbassyAqiTime", time);
			}else{
				 clauseSql="group by us_embassy_aqi_time order by weather_id desc ";
					w.put("lastUpdate", time);
			}
			w.put("remark2", remark2);
			w.put("clauseSql", clauseSql);
			
			grabCityWeatherByOnce(cid, remark2);//每次请求天气前，先抓取一次 TODO
			
			List<Weather> list=selectWeather(w);
			List<Map> weatherList=new ArrayList<Map>();
			if(list.size()>0){
				for(Weather weather:list){
					String cityName=weather.getRemark2();
					
					Map oneMap=new HashMap();
					oneMap.put("airbestId", weather.getWeatherId());//
					oneMap.put("airbestAQI", weather.getAqi());
					oneMap.put("airbestUSEmbassyAQI", weather.getUsEmbassyAqi());
					oneMap.put("airbestCityName", cityName);
					oneMap.put("airbestHumidity", weather.getHumidity());
					oneMap.put("airbestPM25", weather.getPm25());
					oneMap.put("airbestUSEmbassyPM25", weather.getUsEmbassyAqiPm25());
					oneMap.put("airbestQuality", weather.getQuality());
					oneMap.put("airbestTemperature", weather.getTemperature());
					oneMap.put("airbestWeatherText", weather.getWeatherText());
					oneMap.put("airbestWindScale", weather.getWindScale());
					oneMap.put("airbestCode", weather.getWeatherCode());//天气图标编码
					//oneMap.put("airbestForecast", weather.getForecast());
					String forecast= weather.getForecast();
					 List<Map<String,Object>>  tmpMap=FastJsonUtil.toList(forecast);
					oneMap.put("airbestForecast", tmpMap);
					oneMap.put("airbestWindLevel", splitWindScale2Level(weather.getWindScale()));
					oneMap.put("airbestLastUpdate", weather.getLastUpdate());
					oneMap.put("airbestUSEmbassyAQIPublishTime", weather.getUsEmbassyAqiTime());
					if(!(StringUtil.isEmpty(cityName)||"-".equals(cityName))){
						if("北京".equals(cityName)){
							String usEmbassyAqiPm25=weather.getUsEmbassyAqiPm25();
							if(!(StringUtil.isEmpty(usEmbassyAqiPm25)||"-".equals(usEmbassyAqiPm25))){
								oneMap.put("airbestParticle", PMConstant.convertWeight2Particles(usEmbassyAqiPm25));
							}
							//System.out.println(cityName+"采用美使馆");
						}else{
							String pm25=weather.getPm25();
							if(!(StringUtil.isEmpty(pm25)||"-".equals(pm25))){
								oneMap.put("airbestParticle", PMConstant.convertWeight2Particles(pm25));
							}
							//System.out.println(cityName+"采用环保局");
						}
						
						
					}
					weatherList.add(oneMap);
				}
				
				jsonMap.put("message", SystemConstant.MSG_SUCCESS);// 1、表示成功
				jsonMap.put("tips", "天气信息查询成功。");
				jsonMap.put("size", weatherList.size());
				jsonMap.put("list", weatherList);
			}else{
				jsonMap.put("message", SystemConstant.MSG_NO_DATA);// 9、表示暂无数据
				jsonMap.put("tips", "天气信息暂无数据。");
				jsonMap.put("size", 0);
				//无该城市的天气信息，抓一次  TODO
				//grabCityWeatherByOnce(cid, remark2);
			}
			if(!StringUtil.isEmpty(userId)){
				Integer userIdInt=Integer.valueOf(userId);
				this.writeLog(userIdInt, ip, cid);//写日志
				
				//更新用户信息，修改最后登录时间,供系统消息推送使用
				User record=new User();
				record.setUserId(userIdInt);//用户标识
				record.setLastLoginTime(new Date());//最后访问时间
				record.setCity(remark2);//所在城市 
				userService.updateByPrimaryKeySelective(record);
			}
		} catch (Exception e) {
			jsonMap = new HashMap<String, Object>();
			jsonMap.put("message", SystemConstant.MSG_EXCEPTION);//0、表示异常
			jsonMap.put("size", "-1");
			jsonMap.put("tips", "天气信息异常。");
			log.info("天气信息异常"+e);
		}
		
		//雾霾预报 2015-02-10 增加
		String cityEn=Trans2PinYinUtil.trans2PinYin(remark2);
		//System.out.println("cityEn:"+cityEn);
		Map<String,Object> fogHazeForecast=new HashMap<String, Object>();
		fogHazeForecast=pM25ForecastService.getIKairPm(cityEn);
		jsonMap.put("fogHazeForecast", fogHazeForecast);
		
		
		return jsonMap;
	}


	/**
	 * 
	 * @param cid
	 * @param remark2
	 */
	private void grabCityWeatherByOnce(String cid, String remark2) {
		//若无数据，采集一次
		log.info(remark2+",采集一次天气信息："+DateUtil.getFormatDate(new Date(), ""));
		long startTime=System.currentTimeMillis();
		String aqi="";
		Date airbestUSEmbassyAQIPublishTime=null;
		String aqiConcentration="";
		try {
			url = SystemConstant.MISSION_CHINA_WEATHER_URL;
			log.info("*******采集一次网络抓取美使馆数据开始，USEmbassyAQI URL:"+url);
			contentResponse = HttpUtil.sendGet(url, charset);
			startFlag="<table>";
			endFlag="</table>";
			content = WebScraping.getAreaContent(contentResponse.trim(),startFlag, endFlag);
			String[] strArray = content.split("<tr>");
			String publishTimeContent=strArray[1].trim();
			String[] publishTimeArray=publishTimeContent.split("<span>");
			String publishTime=WebScraping.outTag(publishTimeArray[1]).trim();//美使馆发布时间
			//publishTime=DateUtil.changerUsEmbassyAqiTime(publishTime);//纠正美使馆网站时间错误 TODO
			airbestUSEmbassyAQIPublishTime=DateUtil.convertUsEmbassyAqiTime(publishTime,"");//美国大使馆天气预报AQI发布时间
			aqiConcentration=WebScraping.outTag(strArray[5]).replaceAll("\\s*", "");//美使馆pm2.5 µg/m³
			int start=aqiConcentration.indexOf(":");
			aqiConcentration=aqiConcentration.substring(start+1,aqiConcentration.length()-5);//Concentration: 24.0 µg/m³ 
			content = WebScraping.outTag(strArray[2]).replaceAll("\\s*", "");
			String[] aqiArray = content.split("AQI");
			aqi = aqiArray[0];
			log.info("*******采集一次网络抓取美使馆数据结束：airbestUSEmbassyAQI:"+aqi);
		} catch (Exception e) {
			log.info("采集一次获取美馆数据异常："+e);
		}
		
		Weather weather =new Weather();
		try {
			String thinkWeatherUrl = SystemConstant.THINKPAGE_WEATHER_URL;
			if(StringUtil.isEmpty(cid)){
				thinkWeatherUrl = SystemConstant.THINKPAGE_WEATHER_URL;
			}else{
				thinkWeatherUrl = SystemConstant.THINKPAGE_WEATHER_URL+"?cid="+URLEncoder.encode(cid,charset);
			}
			log.info("######采集一次网络抓取心知数据开始，thinkWeatherUrl："+thinkWeatherUrl);
			String startTag = "thinkpage_weather_res = {";
			String endTag = "thinkpage_weather_locationpath =";
			contentResponse = HttpUtil.sendGet(thinkWeatherUrl, charset);
			int begin = contentResponse.indexOf(startTag);
			int end = contentResponse.indexOf(endTag);
			String json = contentResponse.substring(begin, end);
			json = json.replaceAll(";", "");
			String[] josnArray = json.split("=");
			String thinkPageWeather=josnArray[1];
			Map<String, Object> result = FastJsonUtil.toMap(thinkPageWeather);
			List<Map<String, Object>> weathers = (List<Map<String, Object>>) result.get("Weathers");
			Map<String, Object> current = (Map<String, Object>) weathers.get(0).get("Current");
			Map<String, Object> airQuality = (Map<String, Object>) weathers.get(0).get("AirQuality");
			String cityId=weathers.get(0).get("CityId")+"";
			String cityName=weathers.get(0).get("CityName")+"";
			if(airQuality!=null){
				Map<String, Object> cityInfo = (Map<String, Object>) airQuality.get("CityInfo");
				Object aqi2 = cityInfo.get("AQI");
				Object co = cityInfo.get("CO");
				Object pm25 = cityInfo.get("PM25");
				Object pm10 = cityInfo.get("PM10");
				Object no2 = cityInfo.get("NO2");
				Object o3 = cityInfo.get("O3");
				Object so2 = cityInfo.get("SO2");
				Object quality = cityInfo.get("Quality");
				weather.setAqi(aqi2+"");
				weather.setCo(co+"");
				weather.setPm25(pm25+"");
				weather.setPm10(pm10+"");
				weather.setNo2(no2+"");
				weather.setO3(o3+"");
				weather.setSo2(so2+"");
				weather.setQuality(quality+"");
			}else{
				weather.setAqi("-");
				weather.setCo("-");
				weather.setPm25("-");
				weather.setPm10("-");
				weather.setNo2("-");
				weather.setO3("-");
				weather.setSo2("-");
				weather.setQuality("-");
			}
			String lastBuildDate=weathers.get(0).get("LastBuildDate")+"";
			//一周内天气预报
			List<Map<String,Object>> forecastList=(List<Map<String, Object>>) weathers.get(0).get("Forecast");
			/*List<Map<String,Object>> towDayforecastList=new ArrayList<Map<String,Object>>();
			if(forecastList.size()>0){
				towDayforecastList.add(forecastList.get(0));
				towDayforecastList.add(forecastList.get(1));
			}*/
			double speed = Double.valueOf(current.get("Speed") + "");
			int level = PMConstant.windSpeed2WindLevel(speed);
		
			Object weatherText = current.get("Text");
			Object temperature = current.get("Temperature");
			String humidity = current.get("Humidity") + "%";
			String windScale = current.get("Direction") + "风"+ level + "级";
			Date lastUpdate = new Date(lastBuildDate);
			String code = current.get("Code")+"";
			log.info("######采集一次网络抓取心知数据结束。aqi:"+aqi);
			
			Map w2=new HashMap();
			w2.put("lastUpdate", lastUpdate);//发布时间
			w2.put("remark2", cityName);//所在城市名称
			int count=this.selectBySelectiveCount(w2);
			log.info("######采集一次cid:"+cid+",count:"+count+",cityName:"+cityName+",lastUpdate:"+lastUpdate);
			if(count==0){
				log.info("------采集一次数据准备入库...");
				weather.setLastUpdate(lastUpdate);
				weather.setUsEmbassyAqi(aqi);
				weather.setUsEmbassyAqiTime(airbestUSEmbassyAQIPublishTime);
				weather.setHumidity(humidity);// 湿度。单位：百分比%
				weather.setTemperature(temperature+"");//当前实时温度
				weather.setWeatherText(weatherText+"");// 天气情况文字
				weather.setWindScale(windScale);// 风力等级。根据风速计算的风力等级
				weather.setThinkPageWeather(thinkPageWeather);
				weather.setRemark1(cityId);//城市编号
				weather.setRemark2(cityName);//城市名称
				weather.setForecast(forecastList.toString());
				weather.setUsEmbassyAqiPm25(aqiConcentration);
				weather.setWeatherCode(code);//天气图标编码
				
				int flag=this.addWeather(weather);
				if(flag>0){
					log.info("------采集一次抓取天气信息，城市cid参数："+cid+",于【"+DateUtil.getFormatDate(lastUpdate, "")+"】数据入库成功。");
				}else{
					log.info("------采集一次抓取天气信息，城市cid参数："+cid+",于【"+DateUtil.getFormatDate(lastUpdate, "")+"】数据入库失败。");
				}
			}else{
				log.info("------采集一次无需抓取天气信息,城市cid参数："+cid+",已经从网络抓取了天气信息。"+DateUtil.getFormatDate(lastUpdate, ""));
			}
		} catch (Exception e) {
			log.info("采集一次天气异常："+e);
		}
		long endTime=System.currentTimeMillis();
		log.info("###采集一次时间："+(endTime-startTime)/1000+"秒。");
	}

	/**
	 * 根据经纬度获取所在城市名称
	 * 
	 * @param cid
	 * @return
	 */
	private String convertLongitudeAndLatitude2CityName(String cid){
		String cityName="北京";
		try {
			if(!StringUtil.isEmpty(cid)){
				String thinkWeatherUrl= SystemConstant.THINKPAGE_WEATHER_URL+"?cid="+URLEncoder.encode(cid,charset);
				String startTag = "thinkpage_weather_res = {";
				String endTag = "thinkpage_weather_locationpath =";
				String contentResponse = HttpUtil.sendGet(thinkWeatherUrl, charset);
				int begin = contentResponse.indexOf(startTag);
				int end = contentResponse.indexOf(endTag);
				String json = contentResponse.substring(begin, end);
				json = json.replaceAll(";", "");
				String[] josnArray = json.split("=");
				String thinkPageWeather=josnArray[1];
				Map<String, Object> result = FastJsonUtil.toMap(thinkPageWeather);
				List<Map<String, Object>> weathers = (List<Map<String, Object>>) result.get("Weathers");
				cityName=weathers.get(0).get("CityName")+"";
			}
		} catch (Exception e) {
			log.info("根据经纬度获取所在城市名称异常："+e);
		}
		return cityName;
	}
	
	/**
	 * 将北京区县名称修改为北京
	 * 
	 * @param districtName
	 * @return
	 */
	private String change2Beijing(String districtName){
		//String []districtNameArray={"东城","西城","崇文","宣武","朝阳","丰台","石景山","海淀","门头沟","房山","通州","顺义","昌平","大兴","怀柔","平谷","密云","延庆"};
		List<String> districtList=new ArrayList<String>();
		districtList.add("东城");
		districtList.add("西城");
		districtList.add("崇文");
		districtList.add("宣武");
		districtList.add("朝阳");
		districtList.add("丰台");
		districtList.add("石景山");
		districtList.add("海淀");
		districtList.add("门头沟");
		districtList.add("房山");
		districtList.add("通州");
		districtList.add("顺义");
		districtList.add("昌平");
		districtList.add("大兴");
		districtList.add("怀柔");
		districtList.add("平谷");
		districtList.add("密云");
		districtList.add("延庆");
		if(districtList.contains(districtName)){
			return "北京";
		}else{
			return districtName;
		}
						
	}
	
	private String splitWindScale2Level(String windScale){
		String level="0";
		if(!StringUtil.isEmpty(windScale)){
			int start=windScale.indexOf("风");
			level=windScale.substring(start+1,windScale.length()-1);
		}
		return level;
	}
	
	public void writeLog(Integer userId,String ip,String cid){
		//日志
		User existUser=userService.selectByPrimaryKey(userId);
		if(existUser!=null){
			Log opLog=new Log();//日志
			opLog.setUserId(existUser.getUserId());
			opLog.setLoginName(existUser.getLoginName());
			opLog.setLogType("3");// app
			opLog.setIpAddress(ip);
			opLog.setOpContent("{\"message\":\"请求天气信息。\"}");
			opLog.setOpTime(new Date());
			opLog.setLocalization(cid);
			opLog.setOrgId(existUser.getOrgId());
			logService.insert(opLog);
		}
	
	}


	/**
	 * 根据心知天气更新时间删除
	 */
	@Override
	public int deleteByLastUpdate(String time) {
		return weatherMapper.deleteByLastUpdate(time);
	}
}
