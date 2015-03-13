/**
 * @(#)UserAction.java	07/21/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-21
 */
package cn.app118.model;

import java.util.Date;

/**
 * 角色实体对象
 * @author 吴理琪
 *
 */
public class Role {
    private Integer roleId;//角色标识

    private String roleCode;//角色编码

    private String roleName;//角色名称

    private Integer userId;//用户标识：创建该角色的操作人员，即系统登录用户标识

    private Date createTime;//创建时间

    private String isactive;//描述该角色是否可用的一个状态标识   0：无效  1：有效

    private String roleDesc;//角色描述

    private String remark1;//备用字段

    private Integer sort;//排序

   
    public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
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

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive == null ? null : isactive.trim();
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}