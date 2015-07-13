/**
 * @(#)INewsService.java	07/06/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-06
 */
package cn.app118.service.news;

import java.util.List;
import java.util.Map;

import cn.app118.model.News;
/**
 * 内容服务接口
 * 
 * @author wRitchie
 *
 */
public interface INewsService {

	int deleteByPrimaryKey(Integer newsId);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Integer newsId);
    
    News selectPreOrNextNews(Map map);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKeyWithBLOBs(News record);

    int updateByPrimaryKey(News record);
    
    
	// 分页查询内容列表
	public List<Map> selectByPager(Map map);

	// 分页查询内容列表总记录数
	public int selectByPagerCount(Map map);
}
