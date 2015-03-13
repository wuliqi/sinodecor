/**
 * @(#)UserAction.java	06/19/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-06-19
 */
package cn.app118.dao.weather;

import java.util.List;
import java.util.Map;

import cn.app118.model.Weather;
/**
 * 天气信息管理DAO
 * @author 吴理琪
 *
 */
public interface WeatherMapper {
    int deleteByPrimaryKey(Integer weatherId);
    
    int deleteByLastUpdate(String  time);

    int insert(Weather record);

    int insertSelective(Weather record);

    Weather selectByPrimaryKey(Integer weatherId);
    
    List<Weather> selectBySelective(Map record);
    
    int selectBySelectiveCount(Map record);

    int updateByPrimaryKeySelective(Weather record);

    int updateByPrimaryKey(Weather record);
}