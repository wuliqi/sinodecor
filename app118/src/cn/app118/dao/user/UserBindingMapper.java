/**
 * @(#)UserAction.java	09/02/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-02
 */
package cn.app118.dao.user;

import java.util.List;
import java.util.Map;

import cn.app118.model.UserBinding;

/**
 * 用户绑定Dao
 * 
 * @author wRitchie
 *
 */
public interface UserBindingMapper {
	
    int deleteByPrimaryKey(Integer ubId);

    int insert(UserBinding record);

    int insertSelective(UserBinding record);

    UserBinding selectByPrimaryKey(Integer ubId);
    
    List<UserBinding> selectBySelective(UserBinding record);
    
    //根据车牌号，获取绑定的用户绑定信息 
    List<UserBinding> selectUserBingingByDeviceName(Map map);

    int updateByPrimaryKeySelective(UserBinding record);

    int updateByPrimaryKey(UserBinding record);
}