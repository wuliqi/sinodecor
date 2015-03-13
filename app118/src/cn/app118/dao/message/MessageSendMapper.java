/**
 * @(#)UserAction.java	08/13/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-08-13
 */
package cn.app118.dao.message;

import cn.app118.model.MessageSend;
/**
 * 消息发送Dao
 * @author wRitchie
 *
 */
public interface MessageSendMapper {
    int deleteByPrimaryKey(Integer msgSendId);

    int insert(MessageSend record);

    int insertSelective(MessageSend record);

    MessageSend selectByPrimaryKey(Integer msgSendId);

    int updateByPrimaryKeySelective(MessageSend record);

    int updateByPrimaryKey(MessageSend record);
}