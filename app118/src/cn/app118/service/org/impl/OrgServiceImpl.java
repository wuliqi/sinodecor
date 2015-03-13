/**
 * @(#)UserAction.java	09/09/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-09
 */
package cn.app118.service.org.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.app118.dao.org.OrgMapper;
import cn.app118.model.Org;
import cn.app118.service.org.IOrgService;

/**
 * 组织机构管理服务实现类
 * 
 * @author wRitchie
 * 
 */
@Service("orgService")
public class OrgServiceImpl implements IOrgService {
	
	@Resource
	private OrgMapper orgMapper;
	
	/**
	 * 分页查询组织机构列表
	 * 
	 */
	@Override
	public List<Map> selectByPager(Map map) {
		return orgMapper.selectByPager(map);
	}

	/**
	 * 分页查询组织机构列表总记录数
	 */
	@Override
	public int selectByPagerCount(Map map) {
		return orgMapper.selectByPagerCount(map);
	}

	/**
	 * 增加组织机构
	 */
	@Override
	public int insertOrg(Org org) {
		return orgMapper.insert(org);
	}

	/**
	 * 动态查询组织机构
	 */
	@Override
	public List<Org> selectBySelective(Org org) {
		return orgMapper.selectBySelective(org);
	}

	/**
	 * 删除组织机构
	 * 
	 */
	@Override
	public int delOrg(String ids) {
		int result=0;
		String []idArray=ids.split(",");
		for(String oneId:idArray){
			if(!oneId.equals("1")){//根结点不能删除
				result=orgMapper.deleteByPrimaryKey(Integer.valueOf(oneId));
			}
		}
		return result;
	}

	/**
	 *根据主键查找组织机构
	 */
	@Override
	public Org findOrgById(Integer orgId) {
		return orgMapper.selectByPrimaryKey(orgId);
	}

	/**
	 * 修改组织机构
	 */
	@Override
	public int updateOrg(Org org) {
		return orgMapper.updateByPrimaryKeySelective(org);
	}

}
