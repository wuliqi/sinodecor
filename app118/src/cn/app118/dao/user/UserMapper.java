/**
 * @(#)UserAction.java	05/22/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-22
 */
package cn.app118.dao.user;

import java.util.List;
import java.util.Map;

import cn.app118.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);
    
    int deleteByUserIds(String userIds);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);
    
    List<User> selectBySelective(User record);
    
    //根据角色标识查找所有的用户
    List<User> selectUserByRoleId(Integer roleId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    public List<Map> selectByPager(Map map);
	
	public int  selectByPagerCount(Map map);
	
	//根据设备标识查找绑定的用户列表信息
	public List<User> listUserByDeviceId(Integer deviceId);
	
	public List<Map> selectUserDeviceRelByPager(Map map);
	
	public int  selectUserDeviceRelByPagerCount(Map map);
	
	//用户-设备-设备用户关联关系-充值-过滤器多表关联查询分页列表
	public List<Map> selectUserAllByPager(Map map);
	
	//用户-设备-设备用户关联关系-充值-过滤器多表关联查询分页列表总记录数
	public int  selectUserAllByPagerCount(Map map);
	
	//用户-设备-设备用户关联关系-充值-过滤器多表关联查询分页列表导出Excel
	public List<Map> selectUserAllByExportExcel(Map map);
		
}