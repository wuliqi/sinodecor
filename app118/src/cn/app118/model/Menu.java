/**
 * @(#)UserAction.java	07/17/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-17
 */
package cn.app118.model;

import java.util.Date;
/**
 * 菜单实体对象
 * @author 吴理琪
 *
 */
public class Menu {
    private Integer menuId;//本实体记录的唯一标识  *

    private String menuCode;//菜单编码  *

    private String menuName;//菜单名称 *

    private String menuTitle;//菜单标题

    private Integer menuPid;//直接上级菜单的菜单标识。*

    private String menuFolderFlag;//是否菜单夹，是：菜单夹；否：菜单项。

    private String menuSort;//菜单对应的资源的类别。01 组件 02 HTML 03 报表

    private String menuRepresent;//记录菜单链接资源的名称。

    private Integer sortNo;//在同级中的排列顺序的序号，用自然数标识，如，1、2、3。

    private Integer menuLevel;//菜单级别  *

    private String menuPath;//菜单路径 *

    private String isactive;//是否可用标识 0：无效  1：有效

    private String userId;//菜单添加人员

    private Date createTime;//菜单添加时间

    private String remark;//菜单的备注

    private String menuIcon;//菜单图标
    
    private String remark1;//备用字段1

 

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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle == null ? null : menuTitle.trim();
    }



	public Integer getMenuPid() {
		return menuPid;
	}

	public void setMenuPid(Integer menuPid) {
		this.menuPid = menuPid;
	}

	public String getMenuFolderFlag() {
        return menuFolderFlag;
    }

    public void setMenuFolderFlag(String menuFolderFlag) {
        this.menuFolderFlag = menuFolderFlag == null ? null : menuFolderFlag.trim();
    }

    public String getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(String menuSort) {
        this.menuSort = menuSort == null ? null : menuSort.trim();
    }

    public String getMenuRepresent() {
        return menuRepresent;
    }

    public void setMenuRepresent(String menuRepresent) {
        this.menuRepresent = menuRepresent == null ? null : menuRepresent.trim();
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath == null ? null : menuPath.trim();
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive == null ? null : isactive.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
    }

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
    
    
}