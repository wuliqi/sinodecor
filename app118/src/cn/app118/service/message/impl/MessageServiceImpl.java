/**
 * @(#)UserAction.java	06/10/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-06-10
 */
package cn.app118.service.message.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.app118.dao.message.MessageMapper;
import cn.app118.dao.message.MessageSendMapper;
import cn.app118.model.Message;
import cn.app118.model.MessageSend;
import cn.app118.service.message.IMessageService;
/**
 * 消息服务实现类
 * 
 * @author 吴理琪
 * 
 */
@Service("messageService")
public class MessageServiceImpl implements IMessageService {
	
	@Resource
	private MessageMapper messageMapper;//消息内容dao
	
	@Resource
	private MessageSendMapper messageSendMapper;//消息发送dao

	@Override
	public List<Map> listMessage(Map record) {
		return messageMapper.selectBySelective(record);
	}
	
	//******************************//
	/**
	 * 分页查询消息列表
	 * 
	 */
	@Override
	public List<Map> selectByPager(Map map) {
		return messageMapper.selectMessageByPager(map);
	}
	
	/**
	 * 分页查询消息列表总记录条数
	 * 
	 */
	@Override
	public int selectByPagerCount(Map map) {
		return messageMapper.selectMessageByPagerCount(map);
	}

	/**
	 * 根据主键查询消息内容
	 */
	@Override
	public Message findMessageById(Integer msgId) {
		return messageMapper.selectByPrimaryKey(msgId);
	}

	/**
	 * 根据主键查询消息发送信息
	 */
	@Override
	public MessageSend findMessageSendById(Integer msgSendId) {
		return messageSendMapper.selectByPrimaryKey(msgSendId);
	}
	
	/**
	 * 新增消息
	 */
	@Override
	public int insertMessage(Message message) {
		return messageMapper.insert(message);
	}

	/**
	 * 新增消息发送
	 */
	@Override
	public int insertMessageSend(MessageSend messageSend) {
		return messageSendMapper.insert(messageSend);
	}

	/**
	 * 
	 */
	@Override
	public int updMessageSend(MessageSend messageSend) {
		return messageSendMapper.updateByPrimaryKeySelective(messageSend);
	}
	
	

}
