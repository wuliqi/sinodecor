/**
 * @(#)UserAction.java	06/19/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-06-19
 */
package cn.app118.model;

import java.util.Date;
/**
 * 天气信息表
 * @author 吴理琪
 *
 */
public class Weather {
	
    private Integer weatherId;//天气标识

    private String aqi;//aqi指数

    private String co;//一氧化碳值

    private String no2;//二氧化氮值

    private String o3;//臭氧值

    private String so2;//二氧化硫值

    private String pm10;//PM10值

    private String pm25;//PM2.5值

    private String quality;//空气质量

    private String humidity;//湿度

    private String temperature;//温度

    private String weatherText;//天气文本

    private String windScale;//风级

    private String usEmbassyAqi;//美大使馆aqi值

    private String thinkPageWeather;//心知天气JSON文本

    private Date lastUpdate;//最后更新时间

    private String remark1;//扩展字段1 城市编码

    private String remark2;//扩展字段2 城市名称
    
	private Integer userId;// 用户标识
	
	private Date usEmbassyAqiTime;//美大使馆aqi发布时间
	
	private String usEmbassyAqiPm25;//美使馆pm25 µg/m³  2015-01-19 增加
	
	private String forecast;//一周天气预报json
	
	private String weatherCode;//天气图标编码

    public Integer getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi == null ? null : aqi.trim();
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co == null ? null : co.trim();
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2 == null ? null : no2.trim();
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3 == null ? null : o3.trim();
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2 == null ? null : so2.trim();
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10 == null ? null : pm10.trim();
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25 == null ? null : pm25.trim();
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality == null ? null : quality.trim();
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity == null ? null : humidity.trim();
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature == null ? null : temperature.trim();
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText == null ? null : weatherText.trim();
    }

    public String getWindScale() {
        return windScale;
    }

    public void setWindScale(String windScale) {
        this.windScale = windScale == null ? null : windScale.trim();
    }

    public String getUsEmbassyAqi() {
        return usEmbassyAqi;
    }

    public void setUsEmbassyAqi(String usEmbassyAqi) {
        this.usEmbassyAqi = usEmbassyAqi == null ? null : usEmbassyAqi.trim();
    }

    public String getThinkPageWeather() {
        return thinkPageWeather;
    }

    public void setThinkPageWeather(String thinkPageWeather) {
        this.thinkPageWeather = thinkPageWeather == null ? null : thinkPageWeather.trim();
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getUsEmbassyAqiTime() {
		return usEmbassyAqiTime;
	}

	public void setUsEmbassyAqiTime(Date usEmbassyAqiTime) {
		this.usEmbassyAqiTime = usEmbassyAqiTime;
	}

	public String getUsEmbassyAqiPm25() {
		return usEmbassyAqiPm25;
	}

	public void setUsEmbassyAqiPm25(String usEmbassyAqiPm25) {
		this.usEmbassyAqiPm25 = usEmbassyAqiPm25;
	}

	public String getForecast() {
		return forecast;
	}

	public void setForecast(String forecast) {
		this.forecast = forecast;
	}

	public String getWeatherCode() {
		return weatherCode;
	}

	public void setWeatherCode(String weatherCode) {
		this.weatherCode = weatherCode;
	}


	
    
}