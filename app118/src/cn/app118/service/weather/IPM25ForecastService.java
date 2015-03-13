/**
 * @(#)IPM25ForecastService.java	01/05/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-01-05
 */
package cn.app118.service.weather;

import java.util.Map;

public interface IPM25ForecastService {

	/**
	 * 根据城市拼音获取iKair雾霾预报
	 * 
	 * @param city
	 * @return
	 */
	public Map<String, Object> getIKairPm(String city);
}
