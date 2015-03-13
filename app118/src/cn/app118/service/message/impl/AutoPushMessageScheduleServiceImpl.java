/**
 * @(#)UserAction.java	12/30/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-12-30
 */
package cn.app118.service.message.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.app118.constants.SystemConstant;
import cn.app118.dao.user.UserMapper;
import cn.app118.framework.util.DateUtil;
import cn.app118.model.User;
import cn.app118.service.message.IAutoPushMessageScheduleService;

@Component("autoPushMessageScheduleService")
public class AutoPushMessageScheduleServiceImpl implements IAutoPushMessageScheduleService {
	@Resource
	private UserMapper userMapper;
	
	
	
	//@Scheduled(cron="0/50 * * * * ?")   //每天中午十二点触发     0 50 14 * * ?
	//根据充值卡有效期到期推送消息
	@Override
	public void autoPushMessageByEndTime() {
		List<User> userList=userMapper.selectBySelective(new User());
		Date currentTime=new Date();//当前系统时间
		for(User user:userList){
			Date lastLoginTime=user.getLastLoginTime();
			if(null==lastLoginTime){//推送消息提示
				System.out.println("#######null:"+lastLoginTime);
				
			}else{
				int result=DateUtil.compare(lastLoginTime, DateUtil.subtractDate(currentTime,SystemConstant.INACTIVE_INTERVAL));
				if(result<0){//X天未登录过，推送消息提示
					System.out.println("#######"+SystemConstant.INACTIVE_INTERVAL+"天未登录:"+lastLoginTime);
				}else{
					System.out.println("#######正常登录:"+lastLoginTime);
				}
			}
		}
		
	}

}
