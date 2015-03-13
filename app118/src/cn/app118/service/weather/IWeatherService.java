/**
 * @(#)UserAction.java	06/13/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-06-13
 */
package cn.app118.service.weather;

import java.util.List;
import java.util.Map;

import cn.app118.model.Weather;

/**
 * 天气预报数据服务接口
 * 
 * 
 * @author 吴理琪
 *
 */
public interface IWeatherService {
	//网络抓取天气信息
	/**
	 * 
	 * @param map
	 * cid:城市编码：如北京  或beijing
	 * @return
	 */
	@Deprecated
	public Map<String,Object> getWeatherInfo(Map map);
	
	/**
	 * 新天气信息请求接口
	 * 新天气信息请求接口：含天气一天内过去24小时天气信息
	 * @param map  
	 * cid:所在地的经纬度：如39.93:116.40 
	 * userId：用户标识
	 * ip：请求的IP地址
	 * @return
	 */
	public Map<String,Object> queryWeather(Map map);
	
	//网络抓取天气信息入库
	public int addWeather(Weather weather);
	
	//查询数据库中天气列表
	public List<Weather> selectWeather(Map weather);
	
	public int selectBySelectiveCount(Map record);
	
	//public int windSpeed2WindLevel(double speed);
	
	//根据心知天气更新时间删除
	public int deleteByLastUpdate(String  time);

}
