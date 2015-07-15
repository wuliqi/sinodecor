/**
 * @(#)FeedbackMapper.java	07/14/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-14
 */
package cn.app118.dao.feedback;

import java.util.List;
import java.util.Map;

import cn.app118.model.Feedback;

/**
 * 反馈留言Dao
 * @author wRitchie
 *
 */
public interface FeedbackMapper {
    int deleteByPrimaryKey(Integer fdId);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    Feedback selectByPrimaryKey(Integer fdId);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKey(Feedback record);
    
	// 分页查询反馈留言列表
	public List<Map> selectByPager(Map map);

	// 分页查询反馈留言列表总记录数
	public int selectByPagerCount(Map map);
}