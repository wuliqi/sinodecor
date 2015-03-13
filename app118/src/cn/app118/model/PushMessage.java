/**
 * @(#)UserAction.java	09/03/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-03
 */
package cn.app118.model;

import java.util.Date;
/**
 * 推送消息
 * 
 * @author wRitchie
 *
 */
public class PushMessage {
	
	private Integer msgId;// 消息标识

	private String msgTitle;// 消息标题

	private String msgContent;// 消息内容

	private String msgType;// 消息类型1:网页控车 2：系统消息 4：活动消息 6：推送消息   9:短信

	private Integer msgSendId;// 消息发送标识

	private Integer receiverId;// 消息接收者标识

	private Date createTime;// 消息创建时间

	private String status;// 消息状态 0：未查看 1：已查看

	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Integer getMsgSendId() {
		return msgSendId;
	}

	public void setMsgSendId(Integer msgSendId) {
		this.msgSendId = msgSendId;
	}

	public Integer getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
