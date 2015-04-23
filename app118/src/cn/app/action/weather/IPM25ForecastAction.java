/**
 * @(#)PM25ForecastAction.java	01/05/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-01-05
 */
package cn.app.action.weather;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.app118.action.common.BaseAction;
import cn.app118.service.weather.IPM25ForecastService;

/**
 * 雾霾预报控制类
 * 
 * @author wRitchie
 *
 */
@Controller
@RequestMapping("iPM25ForecastAction")
public class IPM25ForecastAction extends BaseAction{
	
	@Resource
	private IPM25ForecastService pM25ForecastService;
	
	
	
	/**
	 * 供微信使用雾霾预报专用接口
	 * 
	 * @param map city:城市拼音 如beijing表示北京 
	 * @return
	 */
	@RequestMapping("/queryPM25Forecast")
	@ResponseBody
	public Map<String,Object> queryPM25Forecast(@RequestParam Map map){
		Map<String,Object> jsonMap=new HashMap<String, Object>();
		String city=map.get("city")+"";//城市拼音
		jsonMap=pM25ForecastService.getIKairPm(city);
		return jsonMap;
	}
}
