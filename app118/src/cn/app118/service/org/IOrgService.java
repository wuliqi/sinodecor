/**
 * @(#)UserAction.java	09/09/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-09
 */
package cn.app118.service.org;

import java.util.List;
import java.util.Map;

import cn.app118.model.Org;

/**
 * 组织机构管理服务接口
 * 
 * @author wRitchie
 * 
 */
public interface IOrgService {

	// 分页查询组织机构列表
	public List<Map> selectByPager(Map map);

	// 分页查询组织机构列表总记录数
	public int selectByPagerCount(Map map);

	// 增加组织机构
	public int insertOrg(Org org);

	// 动态查询
	public List<Org> selectBySelective(Org org);
	
	//删除组织机构
	public int delOrg(String ids);
	
	//根据主键查找组织机构
	public Org findOrgById(Integer orgId);
	
	//修改组织机构
	public int updateOrg(Org org);

}
