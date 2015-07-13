/**
 * @(#)WebServiceImpl.java	07/13/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-13
 */
package cn.app118.service.web.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.app118.dao.web.WebMapper;
import cn.app118.model.Web;
import cn.app118.service.web.IWebService;
@Service("webService")
public class WebServiceImpl implements IWebService {

	@Resource
	private WebMapper webMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer webId) {
		return webMapper.deleteByPrimaryKey(webId);
	}

	@Override
	public int insert(Web record) {
		return webMapper.insert(record);
	}

	@Override
	public int insertSelective(Web record) {
		return webMapper.insertSelective(record);
	}

	@Override
	public Web selectByPrimaryKey(Integer webId) {
		return webMapper.selectByPrimaryKey(webId);
	}

	@Override
	public int updateByPrimaryKeySelective(Web record) {
		return webMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Web record) {
		return webMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Map> selectByPager(Map map) {
		return webMapper.selectByPager(map);
	}

	@Override
	public int selectByPagerCount(Map map) {
		return webMapper.selectByPagerCount(map);
	}

}
