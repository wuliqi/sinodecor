/**
 * @(#)UserAction.java	06/13/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-06-13
 */
package cn.app.action.weather;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.app118.action.common.BaseAction;
import cn.app118.framework.util.GlobalUtil;
import cn.app118.service.weather.IWeatherService;

/**
 * 天气预报数据服务控制
 * 
 * @author 吴理琪
 *
 */
@Controller
@RequestMapping("iWeatherAction")
public class IWeatherAction extends BaseAction{
	
	@Resource
	private IWeatherService weatherService;
	
	@Deprecated
	@RequestMapping("/getWeatherInfo")
	@ResponseBody
	public Map<String,Object> getWeatherInfo(@RequestParam Map map){
		//String cid
		String ip=GlobalUtil.getIp(request);
		map.put("ip", ip);
		Map<String,Object> jsonMap=weatherService.getWeatherInfo(map);
		return jsonMap;
	}
	
	
	@RequestMapping("/queryWeather")
	@ResponseBody
	public Map<String,Object> queryWeather(@RequestParam Map map){
		//String cid
		String ip=GlobalUtil.getIp(request);
		map.put("ip", ip);
		Map<String,Object> jsonMap=weatherService.queryWeather(map);
		return jsonMap;
	}

}
