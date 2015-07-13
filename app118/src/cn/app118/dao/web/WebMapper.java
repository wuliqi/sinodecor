/**
 * @(#)WebMapper.java	07/13/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-13
 */
package cn.app118.dao.web;

import java.util.List;
import java.util.Map;

import cn.app118.model.Web;

/**
 * 友情链接网址dao
 * 
 * @author wRitchie
 *
 */
public interface WebMapper {
	int deleteByPrimaryKey(Integer webId);

	int insert(Web record);

	int insertSelective(Web record);

	Web selectByPrimaryKey(Integer webId);

	int updateByPrimaryKeySelective(Web record);

	int updateByPrimaryKey(Web record);

	// 分页查询网址列表
	public List<Map> selectByPager(Map map);

	// 分页查询网址列表总记录数
	public int selectByPagerCount(Map map);
}