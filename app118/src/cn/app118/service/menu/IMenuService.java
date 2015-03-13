/**
 * @(#)UserAction.java	07/17/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-17
 */
package cn.app118.service.menu;

import java.util.List;
import java.util.Map;

import cn.app118.model.Menu;
/**
 * 菜单列表服务接口
 * @author 吴理琪
 *
 */
public interface IMenuService {

	 /**
	  * 菜单列表
	  * @param record
	  * @return
	  */
	 public List<Menu> selectBySelective(Menu record);
	 
	 /**
	  * 根据用户标识，查询用户所属角色，根据角色，查询用户授予的菜单资源
	  * @param record  
	  *        userId:用户标识
	  * @return
	  */
	 public List<Menu> selectBySelectiveByUserId(Map record);
	 
	 /**
	  * 根据角色标识，查找所有的菜单信息
	  * 
	  * @param roleId
	  * @return
	  */
	 public List<Menu> selectMenuByRoleId(Integer roleId);
	 
	 
}
