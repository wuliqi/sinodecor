/**
 * @(#)UserAction.java	07/11/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-11
 */
package cn.app118.model;

import java.util.Date;
/**
 * 消息内容POPO
 * @author wRitchie
 *
 */
public class Message {
    private Integer msgId;//消息标识

    private String msgTitle;//消息标题

    private String msgContent;//消息内容

    private String msgType;//消息类型  2：系统消息 4：活动消息  6：推送消息  9:短信

    private Date msgCreateTime;//消息创建时间

    private Date msgExpiryTime;//失效时间

    private String isStick;//是否置顶   1:置顶  0:不置顶-->  短信类型  1:表示行业信息  3:表示营销信息

    private Integer userId;//发布人标识  

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
        this.msgTitle = msgTitle == null ? null : msgTitle.trim();
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent == null ? null : msgContent.trim();
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }

    public Date getMsgCreateTime() {
        return msgCreateTime;
    }

    public void setMsgCreateTime(Date msgCreateTime) {
        this.msgCreateTime = msgCreateTime;
    }

    public Date getMsgExpiryTime() {
        return msgExpiryTime;
    }

    public void setMsgExpiryTime(Date msgExpiryTime) {
        this.msgExpiryTime = msgExpiryTime;
    }

    public String getIsStick() {
        return isStick;
    }

    public void setIsStick(String isStick) {
        this.isStick = isStick == null ? null : isStick.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}