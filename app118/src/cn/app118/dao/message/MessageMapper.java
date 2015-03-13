/**
 * @(#)UserAction.java	06/10/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-06-10
 */
package cn.app118.dao.message;

import java.util.List;
import java.util.Map;

import cn.app118.model.Message;

/**
 * 消息Dao
 * 
 * @author 吴理琪
 * 
 */
public interface MessageMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer msgId);
    
    List<Map> selectBySelective(Map record);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
    
    

	public List<Map> selectMessageByPager(Map map);
	
	public int selectMessageByPagerCount(Map map);
}