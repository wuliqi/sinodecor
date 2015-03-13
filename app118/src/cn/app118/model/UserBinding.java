/**
 * @(#)UserAction.java	09/02/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-02
 */
package cn.app118.model;

import java.util.Date;

/**
 * 用户绑定POJO对象
 * 
 * @author wRitchie
 *
 */
public class UserBinding {
	
    private Integer ubId;//绑定标识

    private Integer userId;//用户标识

    private String openId;//第三方标识

    //4/8:个推私家车（4:个推私家车appstore  8:个推私家车企业版 主要为IOS区分，android为4）
    private String bindingType;//绑定类型 1:QQ; 2:微博  3:个推->2:个推出租车   4:个推私家车
    
    private Date createTime;// 绑定时间
    
    private String osType;//操作系统列型  android:安卓  ios:苹果
    
    

    public Integer getUbId() {
        return ubId;
    }

    public void setUbId(Integer ubId) {
        this.ubId = ubId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getBindingType() {
        return bindingType;
    }

    public void setBindingType(String bindingType) {
        this.bindingType = bindingType == null ? null : bindingType.trim();
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}
    
    
}