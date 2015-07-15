/**
 * @(#)Feedback.java	07/14/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-14
 */
package cn.app118.model;

import java.util.Date;

/**
 * 反馈留言 POJO
 * @author wRitchie
 *
 */
public class Feedback {
    private Integer fdId;//反馈留言标识

    private String phoneNumber;//手机号码

    private String category;//分类 1:反馈  3：留言

    private String content;//内容

    private Date createTime;//创建时间

    public Integer getFdId() {
        return fdId;
    }

    public void setFdId(Integer fdId) {
        this.fdId = fdId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}