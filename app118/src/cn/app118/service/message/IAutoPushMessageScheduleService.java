/**
 * @(#)UserAction.java	12/30/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-12-30
 */
package cn.app118.service.message;

/**
 * 自动推送信息服务接口
 * 
 * @author wRitchie
 *
 */
public interface IAutoPushMessageScheduleService {
	
	//根据充值卡有效期到期推送消息
	public  void autoPushMessageByEndTime();

}
