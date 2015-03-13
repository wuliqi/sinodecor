/**
 * @(#)UserAction.java	09/11/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-11
 */
package cn.app118.model;

import java.util.Date;
/**
 * 用户角色关联关系POJO对象
 * 
 * @author wRitchie
 *
 */
public class UserRoleRela {
    private Integer userRoleRefId;//用户角色关联关系标识

    private Integer userId;//用户标识

    private Integer roleId;//角色标识

    private Integer opUserId;//操作人员标识

    private Date createTime;//创建操作时间

    public Integer getUserRoleRefId() {
        return userRoleRefId;
    }

    public void setUserRoleRefId(Integer userRoleRefId) {
        this.userRoleRefId = userRoleRefId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}