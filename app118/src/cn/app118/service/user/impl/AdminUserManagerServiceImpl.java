/**
 * @(#)UserAction.java	01/07/2016
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-01-07
 */
package cn.app118.service.user.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.app118.constants.SystemConstant;
import cn.app118.dao.log.LogMapper;
import cn.app118.dao.user.UserMapper;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.Log;
import cn.app118.model.User;
import cn.app118.service.user.IAdminUserManagerService;

/**
 * 客户转移服务接口
 * @author wRitchie
 *
 */
@Service("adminUserManagerService")
public class AdminUserManagerServiceImpl implements IAdminUserManagerService {
	private Logger log = Logger.getLogger(this.getClass());// 日志
	
	@Resource
	private UserMapper userMapper;//用户dao
	
	@Resource
	private LogMapper logMapper;//日志dao
	

	/**
	 * 客户转移 批量修改销售人员或所属门店
	 * @param userIds
	 * @param deviceIds
	 * @param rechargeIds
	 * @param hepaIds
	 * @param deviceNames
	 * @param orgId
	 * @param saleUserId
	 * @return
	 */
	@Override
	@Transactional
	public Map<String, Object> updAdminUserOrgId(String userIds,
			String deviceIds, String deviceNames, String orgId, String saleUserId) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		StringBuilder info=new StringBuilder();
		try {
			String []userIdArray=userIds.split(",");

			if(!StringUtil.isEmpty(orgId)){//所属门店修改
				Integer orgIdInt=Integer.valueOf(orgId);
				for(int i=0;i<userIdArray.length;i++){
					String oneUserId=userIdArray[i];
				
					if(!StringUtil.isEmpty(oneUserId)){//userId不空
						System.out.println("###########修改用户标识："+oneUserId);
						//1、修改sys_user表的orgId
						User user=new User();
						user.setUserId(Integer.valueOf(oneUserId));
						user.setOrgId(orgIdInt);
						user.setModifyTime(new Date());
						user.setRemark("客户转移修改所属门店");
						userMapper.updateByPrimaryKeySelective(user);
						//2、修改sys_log 表的orgId
						Log log=new Log();
						log.setOrgId(orgIdInt);
						log.setUserId(Integer.valueOf(oneUserId));
						log.setRemark1("客户转移修改所属门店");
						logMapper.updateByUserIdSelective(log);
						
						
					}
				}
			}else{//所属门店未修改 TODO
				
			}
			info.append("客户转移成功。");
		} catch (Exception e) {
			log.info("客户转移异常："+e);
			jsonMap.put("message", SystemConstant.MSG_EXCEPTION);// 0、表示失败
			jsonMap.put("tips", "客户转移失败。");
			info.append("客户转移失败。");
		}
		jsonMap.put("info", info.toString());
		return jsonMap;
	}

}
