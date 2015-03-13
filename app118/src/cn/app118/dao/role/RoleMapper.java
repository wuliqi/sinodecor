/**
 * @(#)UserAction.java	09/11/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-11
 */
package cn.app118.dao.role;

import java.util.List;
import java.util.Map;

import cn.app118.model.Role;

/**
 * 角色dao
 * 
 * @author wRitchie
 *
 */
public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    //查询角色列表分页查询
    public List<Map> selectByPager(Map map);
   
    //查询角色列表分页查询总记录数
	public int  selectByPagerCount(Map map);
	
	List<Role> selectBySeletive(Role role);
	
	//根据用户标识查询所属用户角色
	List<Role> selectRoleByUserId(Map map);
}