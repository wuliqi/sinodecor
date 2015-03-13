/**
 * @(#)UserAction.java	09/11/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-11
 */
package cn.app118.dao.user;

import java.util.List;

import cn.app118.model.UserRoleRela;
/**
 * 用户角色关联关系dao
 * 
 * @author wRitchie
 *
 */
public interface UserRoleRelaMapper {
    int deleteByPrimaryKey(Integer userRoleRefId);
    
    int deleteByUserId(Integer userId);

    int insert(UserRoleRela record);

    int insertSelective(UserRoleRela record);

    UserRoleRela selectByPrimaryKey(Integer userRoleRefId);

    int updateByPrimaryKeySelective(UserRoleRela record);

    int updateByPrimaryKey(UserRoleRela record);
    
    List<UserRoleRela>  listUserRoleRela(UserRoleRela record);
}