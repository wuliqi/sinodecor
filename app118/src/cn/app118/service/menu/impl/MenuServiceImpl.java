/**
 * @(#)UserAction.java	07/17/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-17
 */
package cn.app118.service.menu.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.app118.dao.menu.MenuMapper;
import cn.app118.model.Menu;
import cn.app118.service.menu.IMenuService;

/**
 * 菜单服务实现类
 * @author 吴理琪
 *
 */
@Service("menuService")
public class MenuServiceImpl implements IMenuService{
	
	@Resource
	private MenuMapper menuMapper;//菜单Dao

	/**
	 * 菜单列表
	 * 
	 */
	@Override
	public List<Menu> selectBySelective(Menu record) {
		return menuMapper.selectMenuBySelective(record);
	}

	/**
	 * 根据角色标识，查找所有的菜单信息
	 * 
	 */
	@Override
	public List<Menu> selectMenuByRoleId(Integer roleId) {
		return menuMapper.selectMenuByRoleId(roleId);
	}

	/**
	 * 根据用户标识，查询用户所属角色，根据角色，查询用户授予的菜单资源
	 * @param userId:用户标识
	 */
	@Override
	public List<Menu> selectBySelectiveByUserId(Map record) {
		return menuMapper.selectBySelectiveByUserId(record);
	}

}
