/**
 * @(#)UserAction.java	06/10/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-06-10
 */
package cn.app118.service.message;

import java.util.List;
import java.util.Map;

import cn.app118.model.Message;
import cn.app118.model.MessageSend;
/**
 * 消息服务接口
 * 
 * @author 吴理琪
 * 
 */
public interface IMessageService {
	
	//查询消息列表
	public List<Map> listMessage(Map record);
	
	//分页查询消息列表
	public List<Map> selectByPager(Map map);
	
	//分页查询消息列表总记录数
	public int selectByPagerCount(Map map);
	
	//根据主键查询消息内容
	public Message findMessageById(Integer msgId);
	
	//根据主键查询消息发送信息
	public MessageSend findMessageSendById(Integer msgSendId);
	
	//新增消息
	public int insertMessage(Message message);
	
	//新增消息发送
	public int insertMessageSend(MessageSend messageSend);

	public int updMessageSend(MessageSend messageSend);
}
