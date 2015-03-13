/**
 * @(#)UserAction.java	09/11/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-11
 */
package cn.app118.service.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.app118.dao.user.UserRoleRelaMapper;
import cn.app118.model.UserRoleRela;
import cn.app118.service.user.IUserRoleRelaService;
/**
 * 用户角色关联关系服务实现类
 * 
 * @author wRitchie
 *
 */
@Service("userRoleRelaService")
public class UserRoleRelaServiceImpl implements IUserRoleRelaService {
	
	@Resource
	private UserRoleRelaMapper userRoleRelaMapper;
	
	/**
	 * 增加用户角色关联关系
	 * 
	 */
	@Override
	public int addUserRoleRela(UserRoleRela record) {
		return userRoleRelaMapper.insert(record);
	}

	/**
	 * 查询用户角色关联关系列表
	 * 
	 */
	@Override
	public List<UserRoleRela> listUserRoleRela(UserRoleRela record) {
		return userRoleRelaMapper.listUserRoleRela(record);
	}

	/**
	 * 根据用户角色关联标识删除
	 * 
	 */
	@Override
	public int delUserRoleRela(Integer userRoleRefId) {
		return userRoleRelaMapper.deleteByPrimaryKey(userRoleRefId);
	}

	/**
	 * 根据用户标识删除用户角色关联关系
	 */
	@Override
	public int deleteByUserId(Integer userId) {
		return userRoleRelaMapper.deleteByUserId(userId);
	}

}
