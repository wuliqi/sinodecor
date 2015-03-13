/**
 * @(#)PM25ForecastServiceImpl.java	01/05/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-01-05
 */
package cn.app118.service.weather.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.HttpUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.framework.util.WebScraping;
import cn.app118.service.weather.IPM25ForecastService;
/**
 * 抓取ikair网站雾霾预报信息
 * 
 * http://pm.ikair.com
 * @author wRitchie
 *
 */
@Service("pM25ForecastService")
public class PM25ForecastServiceImpl implements IPM25ForecastService{
	private Logger log = Logger.getLogger(this.getClass());// 日志
	/**
	 * 根据城市拼音获取iKair雾霾预报
	 * 
	 * @param city
	 * @return
	 */
	public Map<String, Object> getIKairPm(String city){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if(StringUtil.isEmpty(city)){
			city="beijing";
		}
		String url="http://pm.ikair.com/?method=fog&ename="+city;
		try {
			String contentResponse = HttpUtil.sendPost(url, "method=fog&ename="+city, "utf-8", 3000);
			//log.info("contentResponse:"+contentResponse);
			String[] data=contentResponse.split("\\$");
		
			String cityNameCn=data[0];
			String pm25Info=data[1];
			log.info("cityNameCn:"+cityNameCn);
			//log.info("pm25Info:"+pm25Info);
			
			String[] pm25InfoArray=pm25Info.split("<li class=\"dataWrap\">");
			
			List<Map<String,String>> pmList =new ArrayList<Map<String,String>>();
			String pm25value="";//pm2.5值
			String dateCn="";//日期  今天  明天
			String mmdd="";//日期  月-日
			String aqi="";//AQI指数
			String quality="";//等级
		
			for(String pm25:pm25InfoArray){
				Map<String,String> pmMap=new HashMap<String, String>();
				if(!StringUtil.isEmpty(pm25)){
					pm25value=WebScraping.outTag(WebScraping.getAreaContent(pm25.trim(),"<em class=\"font_size01\">", "</em>").trim());
					log.info("pm25value:"+pm25value.trim());
					pmMap.put("pm25value", pm25value.trim());
					
					dateCn=WebScraping.outTag(WebScraping.getAreaContent(pm25.trim(),"<span class=\"font_color03\">", "</span>").trim());
					log.info("dateCn:"+dateCn.trim());
					pmMap.put("dateCn", dateCn.trim());
					
					mmdd=WebScraping.outTag(WebScraping.getAreaContent(pm25.trim(),"<em class=\"font_color02\">", "</em>").trim());
					log.info("mmdd:"+mmdd.trim());
					pmMap.put("mmdd", mmdd.trim());
					
					aqi=WebScraping.outTag(WebScraping.getAreaContent(pm25.trim(),"<li class=\"zhishu font_size03\">", "</li>").trim());
					aqi=StringUtil.trim(aqi).substring(4);
					log.info("aqi:"+aqi);
					pmMap.put("aqi", aqi);
					
					quality=WebScraping.outTag(WebScraping.getAreaContent(pm25.trim(),"<li class=\"degree font_size04\">", "</li>").trim());
					log.info("quality:"+quality.trim());
					pmMap.put("quality", quality.trim());
					log.info("---------------------");
					pmList.add(pmMap);
				}
			}
		
			jsonMap.put("message", SystemConstant.MSG_SUCCESS);
			jsonMap.put("tips", "根据城市拼音获取iKair雾霾预报成功。");
			jsonMap.put("cityNameCn", cityNameCn);
			jsonMap.put("pmList", pmList);
			
		} catch (Exception e) {
			log.info("根据城市拼音获取iKair雾霾预报异常："+e);
			jsonMap.put("message", SystemConstant.MSG_EXCEPTION);
			jsonMap.put("tips", "根据城市拼音获取iKair雾霾预报异常。");
		}
		return jsonMap;
	}
	
	public static void main(String[] args) {
		
		PM25ForecastServiceImpl pM25ForecastServiceImpl=new PM25ForecastServiceImpl();
		pM25ForecastServiceImpl.getIKairPm("changsha");
		
	}
}
