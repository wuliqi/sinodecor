/**
 * @(#)RegionServiceImpl.java	07/21/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-21
 */
package cn.app118.service.region.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.app118.dao.region.RegionMapper;
import cn.app118.model.Region;
import cn.app118.service.region.IRegionService;
/**
 * 全国省市区服务实现类
 * @author wRitchie
 *
 */
@Service("regionService")
public class RegionServiceImpl implements IRegionService {

	@Resource
	private RegionMapper regionMapper;
	
	@Override
	public int deleteByPrimaryKey(Double regionId) {
		return regionMapper.deleteByPrimaryKey(regionId);
	}

	@Override
	public int insert(Region record) {
		return regionMapper.insert(record);
	}

	@Override
	public int insertSelective(Region record) {
		return regionMapper.insertSelective(record);
	}

	@Override
	public Region selectByPrimaryKey(Double regionId) {
		return regionMapper.selectByPrimaryKey(regionId);
	}

	@Override
	public int updateByPrimaryKeySelective(Region record) {
		return regionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Region record) {
		return regionMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Region> selectByParentId(String parentId) {
		return regionMapper.selectByParentId(parentId);
	}

}
