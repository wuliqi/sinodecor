/**
 * @(#)FeedbackServiceImpl.java	07/14/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-14
 */
package cn.app118.service.feedback.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.app118.dao.feedback.FeedbackMapper;
import cn.app118.model.Feedback;
import cn.app118.service.feedback.IFeedbackService;

/**
 * 反馈留言服务实现类
 * @author wRitchie
 *
 */
@Service("feedbackService")
public class FeedbackServiceImpl implements IFeedbackService{
	
	@Resource
	private FeedbackMapper feedbackMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer fdId) {
		return feedbackMapper.deleteByPrimaryKey(fdId);
	}

	@Override
	public int insert(Feedback record) {
		return feedbackMapper.insert(record);
	}

	@Override
	public int insertSelective(Feedback record) {
		return feedbackMapper.insertSelective(record);
	}

	@Override
	public Feedback selectByPrimaryKey(Integer fdId) {
		return feedbackMapper.selectByPrimaryKey(fdId);
	}

	@Override
	public int updateByPrimaryKeySelective(Feedback record) {
		return feedbackMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Feedback record) {
		return feedbackMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Map> selectByPager(Map map) {
		return feedbackMapper.selectByPager(map);
	}

	@Override
	public int selectByPagerCount(Map map) {
		return feedbackMapper.selectByPagerCount(map);
	}

}
