/**
 * @(#)UserAction.java	07/17/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-17
 */
package cn.app118.dao.menu;

import java.util.List;
import java.util.Map;

import cn.app118.model.Menu;

public interface MenuMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long menuId);
    
    List<Menu> selectMenuBySelective(Menu record);
    
    List<Menu> selectMenuByRoleId(Integer roleId);
    
    // 根据用户标识，查询用户所属角色，根据角色，查询用户授予的菜单资源
    List<Menu> selectBySelectiveByUserId(Map roleId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
}