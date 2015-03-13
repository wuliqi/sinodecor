/**
 * @(#)UserAction.java	05/22/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-22
 */
package cn.app118.service.user;

import java.util.List;
import java.util.Map;

import cn.app118.model.User;
import cn.app118.model.UserBinding;
import cn.app118.model.UserGpsInfo;

/**
 * 用户管理服务接口
 * 
 * @author 吴理琪
 * 
 */
public interface IUserService {

	public String login();

	public String addUser(User user);

	public List<User> findUser(User user);

	public User selectByPrimaryKey(Integer id);
	
	public int updateByPrimaryKeySelective(User record);
	
	//增加用户GPS信息
	public String addUserGpsInfo(UserGpsInfo userGpsInfo);
	
	//新增或修改用户绑定关系
	public int addOrUpdUserBinding(UserBinding userBinding);
	
	//根据车牌号，获取绑定的用户绑定信息 
    List<UserBinding> selectUserBingingByDeviceName(Map map);
	
	//动态查询用户绑定关系
	public List<UserBinding> listUserBinding(UserBinding userBinding);
	
	
	///////////////////
	//用户分页列表查询
	public List<Map> selectByPager(Map map);
	
	//用户分页列表总记录数查询
	public int  selectByPagerCount(Map map);
	
	//根据设备标识查找绑定的用户列表信息
	public List<User> listUserByDeviceId(String deviceId);

	//用户分页设备绑定关系列表查询
	public List<Map> selectUserDeviceRelByPager(Map map);
	
	//用户分页设备绑定关系列表总记录数查询
	public int  selectUserDeviceRelByPagerCount(Map map);
	
	//删除后台用户及用户角色关联关系
	public int delAccountAndUserRoleRela(String userIds);
	
	//根据角色标识查找所有的用户
    public List<User> selectUserByRoleId(Integer roleId);
    
    //用户-设备-设备用户关联关系-充值-过滤器多表关联查询分页列表
  	public List<Map> selectUserAllByPager(Map map);
  	
  	//用户-设备-设备用户关联关系-充值-过滤器多表关联查询分页列表总记录数
  	public int  selectUserAllByPagerCount(Map map);
  	
  	//用户-设备-设备用户关联关系-充值-过滤器多表关联查询分页列表导出Excel
  	public List<Map> selectUserAllByExportExcel(Map map);
  	
  	public int deleteByPrimaryKey(Integer userId);
  	
}
