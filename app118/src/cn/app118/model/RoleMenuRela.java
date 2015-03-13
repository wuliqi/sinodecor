/**
 * @(#)UserAction.java	09/04/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-04
 */
package cn.app118.model;

import java.util.Date;

/**
 * 角色菜单关联关系
 * 
 * @author wRitchie
 *
 */
public class RoleMenuRela {
    private Integer rmId;//主键标识

    private Integer roleId;//角色标识

    private Integer menuId;//菜单标识

    private String menuCode;//菜单编码

    private Integer userId;//操作人员标识

    private Date createTime;//操作时间

    public Integer getRmId() {
        return rmId;
    }

    public void setRmId(Integer rmId) {
        this.rmId = rmId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}