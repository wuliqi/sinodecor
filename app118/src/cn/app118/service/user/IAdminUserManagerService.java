/**
 * @(#)UserAction.java	01/07/2016
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-01-07
 */
package cn.app118.service.user;

import java.util.Map;

/**
 * 客户转移服务接口
 * @author wRitchie
 *
 */
public interface IAdminUserManagerService {
	
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
	public Map<String, Object> updAdminUserOrgId(String userIds,String deviceIds,String deviceNames,String orgId,String saleUserId);

}
