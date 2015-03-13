/**
 * @(#)UserAction.java	09/09/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-09
 */
package cn.app118.model;

import java.util.Date;

/**
 * 组织机构
 * 
 * @author wRitchie
 *
 */
public class Org {
    private Integer orgId;//组织机构标识

    private String orgNo;//机构、单位等编码

    private Integer orgPid;//直属上级机构编码

    private String orgName;//机构单位详细的名称

    private String abbr;//机构名称简写,如北京简写bj。

    private String orgType;//机构类型：01：单位 02：部门：03：岗位

    private Integer userId;//负责人

    private String mobile;//联系电话

    private String fax;//传真

    private String sysType;//组织机构的一个系统来源方向：01 后台管理系统

    private Integer sortNo;//在同级中的排列顺序的序号，用自然数阿拉伯数字标识，如，1、2、3。

    private String isactive;//描述该机构是否有效的一个状态标识  0：无效  1：有效

    private String remark;//备注
    
    private Date createTime;//创建时间

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo == null ? null : orgNo.trim();
    }

    public Integer getOrgPid() {
        return orgPid;
    }

    public void setOrgPid(Integer orgPid) {
        this.orgPid = orgPid;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr == null ? null : abbr.trim();
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType == null ? null : orgType.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType == null ? null : sysType.trim();
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive == null ? null : isactive.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
    
}