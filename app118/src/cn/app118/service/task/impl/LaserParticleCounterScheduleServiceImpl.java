/**
 * @(#)UserAction.java	09/24/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-24
 */
package cn.app118.service.task.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.app118.constants.PMConstant;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.FastJsonUtil;
import cn.app118.framework.util.HttpUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.framework.util.WebScraping;
import cn.app118.model.Weather;
import cn.app118.service.code.ICodeService;
import cn.app118.service.task.ILaserParticleCounterScheduleService;
import cn.app118.service.weather.IWeatherService;

/**
 * 定时任务--AirBest远程激光粒子计数器数据采集服务实现类
 * 
 * @author wRitchie
 *
 */
@Component("laserParticleCounterScheduleService")
public class LaserParticleCounterScheduleServiceImpl implements ILaserParticleCounterScheduleService {
	

	private Logger log = Logger.getLogger(this.getClass());//日志
	
	
	
	@Resource
	private IWeatherService weatherService;
	
	@Resource
	private ICodeService codeService;
	
	
	
	private String url = SystemConstant.MISSION_CHINA_WEATHER_URL;// 请求的URL
	private String charset = "UTF-8";// 页面编码
	private String startFlag = "<td class=\"aqi_5\">";// 开始标签
	private String endFlag = "</td>";// 结束标签
	private String contentResponse;// http请求返回的内容
	private String content;// 截取的内容块
	
	/**
	 * 定时采集心知天气时间
	 * 
	 * 
	 */
	//@Scheduled(cron=PMConstant.GRAB_WEATHER_CRON)  //TODO
	@Override
	public void grabWeatherInfo() {
		log.info("###开始自动采集天气信息："+DateUtil.getFormatDate(new Date(), ""));
		long startTime=System.currentTimeMillis();
		
		String aqi="";
		Date airbestUSEmbassyAQIPublishTime=null;
		String aqiConcentration="";
		try {
			url = SystemConstant.MISSION_CHINA_WEATHER_URL;
			log.info("*******网络抓取美使馆数据开始，USEmbassyAQI URL:"+url);
			contentResponse = HttpUtil.sendGet(url, charset);
			startFlag="<table>";
			endFlag="</table>";
			content = WebScraping.getAreaContent(contentResponse.trim(),startFlag, endFlag);
			String[] strArray = content.split("<tr>");
			String publishTimeContent=strArray[1].trim();
			String[] publishTimeArray=publishTimeContent.split("<span>");
			String publishTime=WebScraping.outTag(publishTimeArray[1]).trim();//美使馆发布时间
			airbestUSEmbassyAQIPublishTime=DateUtil.convertUsEmbassyAqiTime(publishTime,"");//美国大使馆天气预报AQI发布时间
			aqiConcentration=WebScraping.outTag(strArray[5]).replaceAll("\\s*", "");//美使馆pm2.5 µg/m³
			int start=aqiConcentration.indexOf(":");
			aqiConcentration=aqiConcentration.substring(start+1,aqiConcentration.length()-5);//Concentration: 24.0 µg/m³ 
			content = WebScraping.outTag(strArray[2]).replaceAll("\\s*", "");
			String[] aqiArray = content.split("AQI");
			aqi = aqiArray[0];
			log.info("*******网络抓取美使馆数据结束：airbestUSEmbassyAQI:"+aqi);
		} catch (Exception e) {
			log.info("获取美馆数据异常："+e);
		}
		
		Map code=new HashMap();
		code.put("type", "10000");//查找代码表中的行政区域城市
		code.put("status", "1");
		List<Map> codeList=codeService.selectBySelective(code);
		for(Map cityMap:codeList){
			String cid=cityMap.get("codeName")+"";
			log.info("开始采集："+cid);
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			Weather weather =new Weather();
			try {
				String thinkWeatherUrl = SystemConstant.THINKPAGE_WEATHER_URL;
				if(StringUtil.isEmpty(cid)){
					thinkWeatherUrl = SystemConstant.THINKPAGE_WEATHER_URL;
				}else{
					thinkWeatherUrl = SystemConstant.THINKPAGE_WEATHER_URL+"?cid="+URLEncoder.encode(cid,charset);
				}
				log.info("######网络抓取心知数据开始，thinkWeatherUrl："+thinkWeatherUrl);
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
				double speed = Double.valueOf(current.get("Speed") + "");
				int level = PMConstant.windSpeed2WindLevel(speed);
			
				Object weatherText = current.get("Text");
				Object temperature = current.get("Temperature");
				String humidity = current.get("Humidity") + "%";
				String windScale = current.get("Direction") + "风"+ level + "级";
				String codeThink = current.get("Code")+"";
				Date lastUpdate = new Date(lastBuildDate);
				log.info("######网络抓取心知数据结束，aqi:"+aqi);
				
				Map w=new HashMap();
				w.put("lastUpdate", lastUpdate);//发布时间
				w.put("remark2", cityName);//所在城市名称
				int count=weatherService.selectBySelectiveCount(w);
				log.info("###cid:"+cid+",count:"+count+",cityName:"+cityName+",lastUpdate:"+lastUpdate);
				if(count==0){
					log.info("------数据准备入库...");
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
					weather.setWeatherCode(codeThink);
					int flag=weatherService.addWeather(weather);
					if(flag>0){
						log.info("------抓取天气信息，城市cid参数："+cid+",于【"+DateUtil.getFormatDate(lastUpdate, "")+"】数据入库成功。");
					}else{
						log.info("------抓取天气信息，城市cid参数："+cid+",于【"+DateUtil.getFormatDate(lastUpdate, "")+"】数据入库失败。");
					}
				}else{
					log.info("------无需抓取天气信息,城市cid参数："+cid+",已经从网络抓取了天气信息。"+DateUtil.getFormatDate(lastUpdate, ""));
				}
				
				
			} catch (Exception e) {
				log.info("获取心知数据异常："+e);
			}
		}
		
		log.info("册除当前时间前5天的数据...");
		Date today=new Date();
		Date preTody=DateUtil.subtractDate(today, 5);
		int r=weatherService.deleteByLastUpdate(DateUtil.getFormatDate(preTody, ""));
		if(r>0){
			log.info("册除当前时间前5天的数据成功。");
		}else{
			log.info("暂无当前时间前5天的数据。");
		}
		
		long endTime=System.currentTimeMillis();
		log.info("###完成自动采集心知天气信息："+DateUtil.getFormatDate(new Date(), ""));
		log.info("###采集时间："+(endTime-startTime)/1000+"秒。");
	}
}
