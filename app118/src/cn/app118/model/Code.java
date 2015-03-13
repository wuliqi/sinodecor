/**
 * @(#)UserAction.java	08/21/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-08-21
 */
package cn.app118.model;

import java.util.Date;
/**
 * 代码管理POJO
 * 
 * @author wRitchie
 *
 */
public class Code {
	
    private Integer codeId;//代码类别标识

    private String codeName;//代码类别名称

    private Integer pCode;//代码类别父标识

    private String status;//状态   0：废弃  1：启用

    private String type;//所属种类  来自常量类

    private Integer sort;//排序

    private String descr;//描述

    private Date createTime;//创建时间

    private String remark1;//备用字段1

    private String remark2;//备用字段2
    
    private String codeValue;//代码编码

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName == null ? null : codeName.trim();
    }

    public Integer getpCode() {
        return pCode;
    }

    public void setpCode(Integer pCode) {
        this.pCode = pCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    

    public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
    
    
}