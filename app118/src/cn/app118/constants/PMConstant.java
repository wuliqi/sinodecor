/**
 * @(#)UserAction.java	09/17/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-17
 */
package cn.app118.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 由AQI换算pm0.3  pm0.5 pm1.0 pm2.5模拟数据
 * 
 * @author wRitchie
 *
 */
public class PMConstant {
	
	public static final String LASER_CRON = "0 0/30 * * * ?";//定时采集时间    每45分钟执行一次  
	
	public static final String GRAB_WEATHER_CRON = "0 0/15 * * * ?";//定时采集心知天气时间    每15分钟执行一次  
	
	public static float PM05=3111;//PM0.5 从重量到数量
	
	public static float PM03=4.1f;//PM0.3 从重量到数量 0.5X4.1
	
	public static float PM10=8.1f;//PM1.0 从重量到数量 0.5%8.1
	
	public static float PM25=10.1f;//PM0.3 从重量到数量 1.0%10.1
	
	public static int  MULTIPLE=3;//倍数
	
	/**
	 * 根据PM2.5重量转换成pm0.3\0.5\1.0\2.5粒子数
	 * 
	 * @param aqiConcentration
	 * @return Map<String,Float>
	 */
	public static Map<String,Integer> convertWeight2Particles(String aqiConcentration){ 
		Map<String,Integer> map=new HashMap<String,Integer>();
		Float aqiConcentrationFloat=Float.parseFloat(aqiConcentration);//Concentration: 24.0 µg/m³ 
		Float pm05Float=aqiConcentrationFloat*PMConstant.PM05/PMConstant.MULTIPLE;//当前是一分钟，除以3为21秒与手持表保持一致
		Float pm03Float=pm05Float*PMConstant.PM03;
		Float pm10Float=pm05Float/PMConstant.PM10;
		Float pm25Float=pm10Float/PMConstant.PM25;
		/*System.out.println("#############");
		System.out.println("微克每立方米："+aqiConcentration);
		System.out.println("pm03:"+pm03Float.intValue());
		System.out.println("pm05:"+pm05Float.intValue());
		System.out.println("pm10:"+pm10Float.intValue());
		System.out.println("pm25:"+pm25Float.intValue());
		System.out.println("#############");*/
		map.put("pm03", pm03Float.intValue());
		map.put("pm05", pm05Float.intValue());
		map.put("pm10", pm10Float.intValue());
		map.put("pm25", pm25Float.intValue());
		return map;
	}
	
	/**
	 * 风速换算成风力等级
	 * 
	 * @param speed
	 * @return
	 */
	public static int windSpeed2WindLevel(double speed) {
		int level = 0;
		if (speed < 1) {
			level = 0;
		} else if (speed >= 1 && speed <= 5) {
			level = 1;
		} else if (speed >= 6 && speed <= 11) {
			level = 2;
		} else if (speed >= 12 && speed <= 19) {
			level = 3;
		} else if (speed >= 20 && speed <= 28) {
			level = 4;
		} else if (speed >= 29 && speed <= 38) {
			level = 5;
		} else if (speed >= 39 && speed <= 49) {
			level = 6;
		} else if (speed >= 50 && speed <= 61) {
			level = 7;
		} else if (speed >= 62 && speed <= 74) {
			level = 8;
		} else if (speed >= 75 && speed <= 88) {
			level = 9;
		} else if (speed >= 89 && speed <= 102) {
			level = 10;
		} else if (speed >= 103 && speed <= 117) {
			level = 11;
		} else {
			level = 12;
		}
		return level;
	}


}
