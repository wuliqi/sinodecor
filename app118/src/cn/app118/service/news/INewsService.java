package cn.app118.service.news;

import java.util.List;
import java.util.Map;

import cn.app118.model.News;

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