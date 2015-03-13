/**
 * @(#)UserAction.java	12/01/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-12-01
 */
package cn.app118.model;

import java.util.Date;
/**
 * 系统日志POJO
 * 
 * @author wRitchie
 *
 */
public class Log {
    private Integer logId;//日志标识

    private Integer userId;//用户标识

    private String loginName;//登录名

    private String logType;//日志类型  1：后台 3：app

    private String ipAddress;//IP地址

    private String opContent;//操作内容

    private Date opTime;//操作时间

    private String terminalType;//终端型号

    private String localization;//地址定位（经纬度）

    private String remark1;//扩展字段1

    private String remark2;//扩展字段2
    
    private Integer orgId;//所属组织机构

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType == null ? null : logType.trim();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public String getOpContent() {
        return opContent;
    }

    public void setOpContent(String opContent) {
        this.opContent = opContent == null ? null : opContent.trim();
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType == null ? null : terminalType.trim();
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization == null ? null : localization.trim();
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