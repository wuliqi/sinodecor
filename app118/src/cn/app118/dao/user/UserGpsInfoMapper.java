/**
 * @(#)UserAction.java	05/30/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-20
 */
package cn.app118.dao.user;

import cn.app118.model.UserGpsInfo;
/**
 * 
 * @author 吴理琪
 *
 */
public interface UserGpsInfoMapper {
    int deleteByPrimaryKey(Integer gpsId);

    int insert(UserGpsInfo record);

    int insertSelective(UserGpsInfo record);

    UserGpsInfo selectByPrimaryKey(Integer gpsId);

    int updateByPrimaryKeySelective(UserGpsInfo record);

    int updateByPrimaryKey(UserGpsInfo record);
}