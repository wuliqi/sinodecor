/**
 * @(#)UserAction.java	12/01/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-12-01
 */
package cn.app118.service.log.impl;




import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.app118.dao.log.LogMapper;
import cn.app118.model.Log;
import cn.app118.service.log.ILogService;

/**
 * 日志服务实现类
 * 
 * @author wRitchie
 *
 */
@Service("logService")
public class LogServiceImpl implements ILogService {

	@Resource
	private LogMapper logMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer logId) {
		return logMapper.deleteByPrimaryKey(logId);
	}

	@Override
	public int insert(Log record) {
		return logMapper.insert(record);
	}

	@Override
	public int insertSelective(Log record) {
		return logMapper.insertSelective(record);
	}

	@Override
	public Log selectByPrimaryKey(Integer logId) {
		return logMapper.selectByPrimaryKey(logId);
	}

	@Override
	public int updateByPrimaryKeySelective(Log record) {
		return logMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Log record) {
		return logMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Map> selectByPager(Map map) {
		return logMapper.selectByPager(map);
	}

	@Override
	public int selectByPagerCount(Map map) {
		return logMapper.selectByPagerCount(map);
	}

}
