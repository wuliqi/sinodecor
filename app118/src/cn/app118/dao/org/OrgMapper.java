/**
 * @(#)UserAction.java	09/09/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-09
 */
package cn.app118.dao.org;

import java.util.List;
import java.util.Map;

import cn.app118.model.Org;

/**
 * 组织机构dao
 * 
 * @author wRitchie
 *
 */
public interface OrgMapper {
	
    int deleteByPrimaryKey(Integer orgId);

    int insert(Org record);

    int insertSelective(Org record);

    Org selectByPrimaryKey(Integer orgId);

    int updateByPrimaryKeySelective(Org record);

    int updateByPrimaryKey(Org record);
    
	// 分页查询组织机构列表
	public List<Map> selectByPager(Map map);

	// 分页查询组织机构列表总记录数
	public int selectByPagerCount(Map map);
	
	//动态查询
	public List<Org> selectBySelective(Org org);
}