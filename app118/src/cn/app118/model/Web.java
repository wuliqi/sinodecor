/**
 * @(#)Web.java	07/13/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-13
 */
package cn.app118.model;

import java.util.Date;
/**
 * 友情链接网址POJO
 * 
 * @author wRitchie
 *
 */
public class Web {
	
    private Integer webId;

    private String webName;

    private String webUrl;

    private Integer userId;

    private Date createTime;

    private Integer sortNo;

    private String webIcon;

    private String webCategory;

    public Integer getWebId() {
        return webId;
    }

    public void setWebId(Integer webId) {
        this.webId = webId;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName == null ? null : webName.trim();
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl == null ? null : webUrl.trim();
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

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getWebIcon() {
        return webIcon;
    }

    public void setWebIcon(String webIcon) {
        this.webIcon = webIcon == null ? null : webIcon.trim();
    }

    public String getWebCategory() {
        return webCategory;
    }

    public void setWebCategory(String webCategory) {
        this.webCategory = webCategory == null ? null : webCategory.trim();
    }
}