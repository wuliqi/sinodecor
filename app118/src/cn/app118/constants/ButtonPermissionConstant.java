/**
 * @(#)UserAction.java	05/15/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-10-05
 */
package cn.app118.constants;

/**
 * 按钮权限控制常量类
 * 
 * @author writchie
 *
 */
public class ButtonPermissionConstant {
	/**
	 * 超级管理管员 按钮权限
	 * 
	 * 拥有所有的按钮权限
	 * 
	 */
	public static int  ROLE_SUPERADMIN = 9;
	
	/**
	 * 库管员 按钮权限  
	 * 
	 * 设备中心->过滤器管理->过滤器入库 
	 * 
	 */
	public static int  ADDHEPA_LIBMANAGER = 10;
	
	
	/**
	 * 销售员角色 按钮权限  
	 * 用户管理->app用户管理->添加用户--销售人员下拉列表选择
	 * 控制器销售报表->销售员下拉列表选择
	 */
	public static int  SALESUSERLIST_SALES = 13;//     

	/**
	 * 客服角色人员  按钮权限
	 * 
	 * 预约管理->安装预约->预约处理   
	 * 预约管理->过滤器预约->预约处理   
	 * 预约管理->防护套餐预约->预约处理   
	 * 预约管理->幸运大转盘->中奖处理 
	 * 充值管理->VIP服务设备->删除充值卡信息 
	 * 
	 */
	public static int  ROLE_CLIENTMANAGER = 15;
	
	/**
	 * 店长角色人员 按钮权限
	 * 
	 * 过滤器管理->过滤器预定
	 * 
	 */
	public static int  ROLE_SHOP_MANAGER = 16;
	
	/**
	 * 总公司
	 */
	public static int ROOT_ORG_ID=1;
}
