/**
 * @(#)UserAction.java	08/13/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-08-13
 */
package cn.app118.model;

import java.util.Date;
/**
 * 消息发送实体
 * 
 * @author wRitchie
 *
 */
public class MessageSend {
    private Integer msgSendId;//消息发送标识

    private Integer msgId;//消息内容标识

    private Integer receiverId;//消息接收者标识

    private Integer senderId;//消息发送者标识

    private Date createTime;//消息创建时间

    private String status;//消息状态   0：未查看   1：已查看

    private String remark1;//备用字段1

    private String remark2;//备用字段2
    
    private Integer orgId;//所属组织机构

    public Integer getMsgSendId() {
        return msgSendId;
    }

    public void setMsgSendId(Integer msgSendId) {
        this.msgSendId = msgSendId;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
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
        this.status = status == null ? null : status.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
    
    
}