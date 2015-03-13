/**
 * @(#)UserAction.java	05/22/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-22
 */
package cn.app118.service.user.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.app118.dao.user.UserBindingMapper;
import cn.app118.dao.user.UserGpsInfoMapper;
import cn.app118.dao.user.UserMapper;
import cn.app118.dao.user.UserRoleRelaMapper;
import cn.app118.model.User;
import cn.app118.model.UserBinding;
import cn.app118.model.UserGpsInfo;
import cn.app118.service.user.IUserService;

/**
 * 用户管理服务层实现类
 * @author 吴理琪
 *
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private UserGpsInfoMapper userGpsInfoMapper;
	

	
	@Resource
	private UserBindingMapper userBindingMapper;//用户绑定关系Dao
	
	@Resource
	private UserRoleRelaMapper userRoleRelaMapper;// 用户角色关联关系dao
	

	
	
	
	public String login() {
		String userInfo = "登录成功";
		return userInfo;
	}
	
	
	public String addUser(User user) {
		int result=userMapper.insert(user);
		return result+"";
	}
	
	
	@Override
	public List<User> findUser(User user) {
		return userMapper.selectBySelective(user);
	}


	@Override
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}


	@Override
	public int updateByPrimaryKeySelective(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 增加用户GPS信息
	 * 
	 * 
	 */
	@Override
	public String addUserGpsInfo(UserGpsInfo userGpsInfo) {
		int result=userGpsInfoMapper.insert(userGpsInfo);
		return result+"";
	}
	
	/**
	 * 新增或修改用户绑定关系
	 * 
	 */
	@Override
	public int addOrUpdUserBinding(UserBinding userBinding) {
		UserBinding ub=new UserBinding();
		ub.setUserId(userBinding.getUserId());
		ub.setBindingType(userBinding.getBindingType());
		List<UserBinding> list=userBindingMapper.selectBySelective(ub);
		int result=0;
		if(list.size()>0){//存在，则修改
			ub=list.get(0);
			userBinding.setUbId(ub.getUbId());
			result=userBindingMapper.updateByPrimaryKeySelective(userBinding);
		}else{//不存在，则新增
			result=userBindingMapper.insert(userBinding);
		}
		return result;
	}
	
	/**
	 * 根据车牌号，获取绑定的用户绑定信息 
	 * 
	 */
	@Override
	public List<UserBinding> selectUserBingingByDeviceName(Map map) {
		return userBindingMapper.selectUserBingingByDeviceName(map);
	}
	
	/**
	 * 动态查询用户绑定关系
	 */
	@Override
	public List<UserBinding> listUserBinding(UserBinding userBinding) {
		return userBindingMapper.selectBySelective(userBinding);
	}
	
	/**
	 * 分页查询用户列表信息
	 */
	@Override
	public List<Map> selectByPager(Map map) {
		return userMapper.selectByPager(map);
	}

	/**
	 * 分页查询用户列表总记录数
	 */
	@Override
	public int selectByPagerCount(Map map) {
		return userMapper.selectByPagerCount(map);
	}


	/**
	 * 根据设备标识查找绑定的用户列表信息
	 * @param deviceId
	 * 
	 */
	@Override
	public List<User> listUserByDeviceId(String deviceId) {
		List<User>  list=new ArrayList<User>();
		list=userMapper.listUserByDeviceId(Integer.valueOf(deviceId));
 		return list;
	}

	/**
	 * 用户设备绑定关系列表分页查询
	 * 
	 */
	@Override
	public List<Map> selectUserDeviceRelByPager(Map map) {
		return userMapper.selectUserDeviceRelByPager(map);
	}

	/**
	 * 用户设备绑定关系列表总记录数查询
	 */
	@Override
	public int selectUserDeviceRelByPagerCount(Map map) {
		return userMapper.selectUserDeviceRelByPagerCount(map);
	}
	


	/**
	 * 删除后台用户及用户角色关联关系
	 * 
	 */
	@Override
	@Transactional
	public int delAccountAndUserRoleRela(String userIds) {
		int result=0;
		String []userIdArray=userIds.split(",");
		Integer userId=null;
		for(String oneUserId:userIdArray){
			userId=Integer.valueOf(oneUserId);
			//1、先删除用户角色关联关系
			result=userRoleRelaMapper.deleteByUserId(userId);
			//2、先删除用户信息
			result=userMapper.deleteByPrimaryKey(userId);
			
		}
		return result;
	}

	/**
	 * 根据角色标识查找所有的用户
	 */
	@Override
	public List<User> selectUserByRoleId(Integer roleId) {
		return userMapper.selectUserByRoleId(roleId);
	}

	/**
	 * 用户-设备-设备用户关联关系-充值-过滤器多表关联查询分页列表
	 */
	@Override
	public List<Map> selectUserAllByPager(Map map) {
		return userMapper.selectUserAllByPager(map);
	}

	/**
	 * 用户-设备-设备用户关联关系-充值-过滤器多表关联查询分页总记录数
	 * 
	 */
	@Override
	public int selectUserAllByPagerCount(Map map) {
		return userMapper.selectUserAllByPagerCount(map);
	}

	/**
	 * 用户-设备-设备用户关联关系-充值-过滤器多表关联查询列表导出Excel
	 */
	@Override
	public List<Map> selectUserAllByExportExcel(Map map) {
		return userMapper.selectUserAllByExportExcel(map);
	}


	@Override
	public int deleteByPrimaryKey(Integer userId) {
		return userMapper.deleteByPrimaryKey(userId);
	}

}
