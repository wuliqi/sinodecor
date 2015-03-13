/**
 * @(#)UserAction.java	09/11/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-11
 */
package cn.app118.service.user;

import java.util.List;

import cn.app118.model.UserRoleRela;

/**
 * 用户角色关联关系服务接口
 * 
 * @author wRitchie
 *
 */
public interface IUserRoleRelaService {
	
	//增加用户角色关联关系
	public int addUserRoleRela(UserRoleRela record);

	//查询用户角色关联关系列表
	public List<UserRoleRela> listUserRoleRela(UserRoleRela record);
	
	//根据用户角色关联标识删除
	public int delUserRoleRela(Integer userRoleRefId);
	
	//根据用户标识删除用户角色关联关系
	public int deleteByUserId(Integer userId);
}
