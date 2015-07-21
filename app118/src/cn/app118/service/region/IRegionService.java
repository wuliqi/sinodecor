/**
 * @(#)IRegionService.java	07/21/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-21
 */
package cn.app118.service.region;

import java.util.List;

import cn.app118.model.Region;
/**
 * 全国省市区服务接口
 * @author wRitchie
 *
 */
public interface IRegionService {
	int deleteByPrimaryKey(Double regionId);

    int insert(Region record);

    int insertSelective(Region record);

    Region selectByPrimaryKey(Double regionId);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);
    
    List<Region> selectByParentId(String parentId);
}
