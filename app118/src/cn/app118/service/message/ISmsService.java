/**
 * @(#)UserAction.java	05/23/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-23
 */
package cn.app118.service.message;
/**
 * 短信服务接口
 * 
 * @author 吴理琪
 * 
 */
public interface ISmsService {
	
	/**
	 * 发送短信
	 * @param phoneNumber
	 * @param msg
	 * @param type:1:表示行业信息  3:表示营销信息
	 * @return
	 */
	public String sendSms(String phoneNumber,String msg,String type,Integer userId);
	
	//获取剩余短信数
	public int getRemainSmsAmount();
	
}
