/**
 * @(#)UserAction.java	07/21/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-21
 */
package cn.app118.service.role;

import java.util.List;
import java.util.Map;

import cn.app118.model.Role;
import cn.app118.model.RoleMenuRela;

/**
 * 角色服务接口
 * @author 吴理琪
 *
 */
public interface IRoleService {
	//查询角色列表分页查询
	public List<Map> selectByPager(Map map);
	
	//查询角色列表分页查询总记录数
	public int  selectByPagerCount(Map map);
	
	//增加角色
	public int addRole(Role role,String menuId);
	
	//修改角色
	public int updRole(Role role,String menuId);
	
	//动态查询角色列表
	List<Role> selectBySeletive(Role role);
	
	//删除角色及菜单关联关系
	public int delRoleAndRela(String ids);
	
	//根据角色标识查询角色信息
	public Role selectByPrimaryKey(Integer roleId);
	
	//根据角色标识查询角色及菜单关联关系信息
	List<RoleMenuRela> selectRoleMenuRelaByRoleId(Integer roleId);
	
	//根据用户标识查询所属用户角色
	List<Role> selectRoleByUserId(Map map);
	
 
}
