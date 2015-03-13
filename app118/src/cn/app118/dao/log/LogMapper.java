/**
 * @(#)UserAction.java	12/01/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-12-01
 */
package cn.app118.dao.log;

import java.util.List;
import java.util.Map;

import cn.app118.model.Log;

/**
 * 日志DAO
 * @author wRitchie
 *
 */
public interface LogMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(Log record);
    
    int updateByUserIdSelective(Log record);
    
    int updateByPrimaryKey(Log record);
    
    //日志分页列表
  	public List<Map> selectByPager(Map map);
  	
  	//日志分页列表总记录数查询
  	public int  selectByPagerCount(Map map);
}