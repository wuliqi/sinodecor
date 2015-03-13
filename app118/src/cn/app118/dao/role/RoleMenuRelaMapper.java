/**
 * @(#)UserAction.java	09/04/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-04
 */
package cn.app118.dao.role;

import java.util.List;

import cn.app118.model.RoleMenuRela;

/**
 * 角色菜单关联关系dao
 * 
 * @author wRitchie
 *
 */
public interface RoleMenuRelaMapper {
    int deleteByPrimaryKey(Integer rmId);
    
    int deleteByRoleId(Integer roleId);

    int insert(RoleMenuRela record);

    int insertSelective(RoleMenuRela record);

    RoleMenuRela selectByPrimaryKey(Integer rmId);
    
    List<RoleMenuRela> selectRoleMenuRelaByRoleId(Integer roleId);

    int updateByPrimaryKeySelective(RoleMenuRela record);

    int updateByPrimaryKey(RoleMenuRela record);
}