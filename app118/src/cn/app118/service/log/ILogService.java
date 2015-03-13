/**
 * @(#)UserAction.java	12/01/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-12-01
 */
package cn.app118.service.log;

import java.util.List;
import java.util.Map;

import cn.app118.model.Log;

/**
 * 日志服务接口
 * @author wRitchie
 *
 */
public interface ILogService {
	public int deleteByPrimaryKey(Integer logId);

	public int insert(Log record);

	public int insertSelective(Log record);

	public Log selectByPrimaryKey(Integer logId);

	public int updateByPrimaryKeySelective(Log record);

	public int updateByPrimaryKey(Log record);
	
	//日志分页列表
	public List<Map> selectByPager(Map map);
	
	//日志分页列表总记录数查询
	public int  selectByPagerCount(Map map);
}