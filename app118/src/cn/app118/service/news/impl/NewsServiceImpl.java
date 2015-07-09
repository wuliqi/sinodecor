package cn.app118.service.news.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.app118.dao.news.NewsMapper;
import cn.app118.model.News;
import cn.app118.service.news.INewsService;

@Service("newsService")
public class NewsServiceImpl implements INewsService {

	@Resource
	private NewsMapper newsMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer newsId) {
		return newsMapper.deleteByPrimaryKey(newsId);
	}

	@Override
	public int insert(News record) {
		return newsMapper.insert(record);
	}

	@Override
	public int insertSelective(News record) {
		return newsMapper.insertSelective(record);
	}

	@Override
	public News selectByPrimaryKey(Integer newsId) {
		return newsMapper.selectByPrimaryKey(newsId);
	}
	
	@Override
	public News selectPreOrNextNews(Map map) {
		return newsMapper.selectPreOrNextNews(map);
	}
	
	@Override
	public int updateByPrimaryKeySelective(News record) {
		return newsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(News record) {
		return newsMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(News record) {
		return newsMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Map> selectByPager(Map map) {
		return newsMapper.selectByPager(map);
	}

	@Override
	public int selectByPagerCount(Map map) {
		return newsMapper.selectByPagerCount(map);
	}

	

}
