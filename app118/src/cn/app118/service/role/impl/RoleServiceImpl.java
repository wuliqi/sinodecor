/**
 * @(#)UserAction.java	07/21/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-21
 */
package cn.app118.service.role.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.app118.dao.role.RoleMapper;
import cn.app118.dao.role.RoleMenuRelaMapper;
import cn.app118.model.Role;
import cn.app118.model.RoleMenuRela;
import cn.app118.service.role.IRoleService;
/**
 * 角色服务实现类
 * @author 吴理琪
 *
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService {
	@Resource
	private RoleMapper roleMapper;//角色Dao
	
	@Resource
	private RoleMenuRelaMapper roleMenuRelaMapper;//角色菜单关联关系表dao
	
	/**
	 * 查询角色列表分页查询
	 * 
	 */
	@Override
	public List<Map> selectByPager(Map map) {
		return roleMapper.selectByPager(map);
	}

	
	/**
	 * 查询角色列表分页查询总记录数
	 * 
	 */
	@Override
	public int selectByPagerCount(Map map) {
		return roleMapper.selectByPagerCount(map);
	}

	/**
	 * 增加角色
	 * 
	 */
	@Override
	@Transactional
	public int addRole(Role role,String menuId) {
		int result=0;
		//1、先往系统角色表中增加一条角色
		result=roleMapper.insert(role);
		RoleMenuRela roleMenuRel=null;
		if(result>0){
			//2、再往角色与菜单关联关系表中增加关联关系
			String []menuIds=menuId.split(",");
			Integer roleId = role.getRoleId();
			Integer userId = role.getUserId();
			for(String oneMenuId:menuIds){
				//System.out.println("新增角色菜单关联关系,roleId："+roleId+"\t menuId"+oneMenuId);
				roleMenuRel=new RoleMenuRela();
				roleMenuRel.setRoleId(roleId);//角色标识
				roleMenuRel.setMenuId(Integer.valueOf(oneMenuId));//菜单标识
				roleMenuRel.setUserId(userId);//操作用户标识
				roleMenuRel.setCreateTime(new Date());//创建时间
				result=roleMenuRelaMapper.insert(roleMenuRel);
			}
		}
		return result;
	}

	/**
	 * 动态查询角色列表
	 * 
	 */
	@Override
	public List<Role> selectBySeletive(Role role) {
		return roleMapper.selectBySeletive(role);
	}

	/**
	 * 删除角色及菜单关联关系
	 * 
	 */
	@Override
	@Transactional
	public int delRoleAndRela(String ids) {
		String [] idsArray=ids.split(",");
		int result=0;
		Integer roleId=null;
		for(String id:idsArray){
			//1、删除角色信息
			roleId = Integer.valueOf(id);
			System.out.println(roleId);
			result=roleMapper.deleteByPrimaryKey(roleId);
			if(result>0){
				//2、删除角色菜单关联关系
				result=roleMenuRelaMapper.deleteByRoleId(roleId);
			}
		}
		
		return result;
	}

	/**
	 * 根据角色标识查询角色信息
	 * 
	 */
	@Override
	public Role selectByPrimaryKey(Integer roleId) {
		return roleMapper.selectByPrimaryKey(roleId);
	}

	/**
	 * 根据角色标识查询角色及菜单关联关系信息
	 * 
	 */
	@Override
	public List<RoleMenuRela> selectRoleMenuRelaByRoleId(Integer roleId) {
		return roleMenuRelaMapper.selectRoleMenuRelaByRoleId(roleId);
	}


	/**
	 * 修改角色
	 * 
	 */
	@Override
	public int updRole(Role role, String menuId) {
		int result=0;
		//1、先往系统角色表中增加一条角色
		result=roleMapper.updateByPrimaryKeySelective(role);
		RoleMenuRela roleMenuRel=null;
		if(result>0){
			String []menuIds=menuId.split(",");
			Integer roleId = role.getRoleId();
			Integer userId = role.getUserId();
			//2、根据角色标识，先删除菜单关联关系
			result=roleMenuRelaMapper.deleteByRoleId(roleId);
			//3、再往角色与菜单关联关系表中增加关联关系
			
			for(String oneMenuId:menuIds){
				//System.out.println("新增角色菜单关联关系,roleId："+roleId+"\t menuId"+oneMenuId);
				roleMenuRel=new RoleMenuRela();
				roleMenuRel.setRoleId(roleId);//角色标识
				roleMenuRel.setMenuId(Integer.valueOf(oneMenuId));//菜单标识
				roleMenuRel.setUserId(userId);//操作用户标识
				roleMenuRel.setCreateTime(new Date());//创建时间
				result=roleMenuRelaMapper.insert(roleMenuRel);
			}
		}
		return result;
	}

	/**
	 * 根据用户标识查询所属用户角色
	 */
	@Override
	public List<Role> selectRoleByUserId(Map map) {
		return roleMapper.selectRoleByUserId(map);
	}

}
