/**
 * @(#)AdminUserMangerAction.java	09/28/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-28
 */
package cn.app118.action.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.ButtonPermissionConstant;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.SmsExcelUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.Org;
import cn.app118.model.Role;
import cn.app118.model.User;
import cn.app118.service.code.ICodeService;
import cn.app118.service.org.IOrgService;
import cn.app118.service.user.IAdminUserManagerService;
import cn.app118.service.user.IUserService;

/**
 * 店长修改页面
 * 
 * @author wRitchie
 *
 */
@Controller
@RequestMapping("adminUserMangerAction")
public class AdminUserMangerAction extends BaseAction {

	@Resource
	private IUserService userService;// 用户服务类
	


	
	@Resource
	private ICodeService codeService;//代码管理服务类
	
	@Resource
	private IOrgService orgService;//组织机构服务类
	
	@Resource
	private IAdminUserManagerService adminUserManagerService;
	/**
	 * 进入店长修改页面列表页面
	 * 在一个页面管理 用户-设备-设备用户关联关系-充值-过滤器
	 * 
	 * @return
	 */
	@RequestMapping("/initAdminUserManger")
	public ModelAndView initAdminUserManger() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/admin/listAdminUserManger.jsp");
		User loginUser=(User)session.getAttribute("user");
		Integer orgId = loginUser.getOrgId();
		mv.addObject("orgId", orgId);//当前登录人员的所属门店
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		mv.addObject("orgMap", orgMap);
		
		//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
		List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
		for (Role role : roleList) {
			if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId==ButtonPermissionConstant.ROOT_ORG_ID) {// 超级管理员
				break;
			} else {
				mv.addObject("disabled", "disabled=\"disabled\"");
			}
		}
		return mv;
	}
	
	/**
	 * 店长修改页面列表页面AJAX请求
	 * 
	 * 在一个页面管理 用户-设备-设备用户关联关系-充值-过滤器
	 * @param curNo
	 * @param curSize
	 * @param sortname
	 * @param sortorder
	 * @param loginName
	 * @param realName
	 * @param deviceName
	 * @param cardType
	 * @param fromCreateTime
	 * @param toCreateTime
	 * @return
	 */
	@RequestMapping("listAdminUserManger")
	@ResponseBody
	public Map<String,Object> listAdminUserManger(String curNo,String curSize,String sortname,String sortorder,String loginName,String realName,String deviceName,String cardType,String fromCreateTime,String toCreateTime,String orgId){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		/************* 分页处理 ****************/
		int skip;
		int max;
		if (curNo == null || "".equals(curNo))
			curNo = "0";
		if (curSize == null || "".equals(curSize))
			curSize = SystemConstant.PAGER_CURSIZE;
		skip = Integer.parseInt(curNo);
		max = Integer.parseInt(curSize);
		int start=(skip-1)*max;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", start);
		map.put("len", max);
		/************* 分页处理 ****************/
		
		if(!StringUtil.isEmpty(loginName)){
			map.put("loginName",loginName);
		}
		if(!StringUtil.isEmpty(realName)){
			map.put("realName", realName);
		}
		if(!StringUtil.isEmpty(deviceName)){
			map.put("deviceName", deviceName);
		}
		if(!StringUtil.isEmpty(cardType)){
			if("0".equals(cardType)){
				map.put("cardTypeFree", cardType);
			}else{
				map.put("cardType", cardType);
			}
			
		}
		if(!StringUtil.isEmpty(fromCreateTime)){
			map.put("fromInstallTime", fromCreateTime);
		}
		if(!StringUtil.isEmpty(toCreateTime)){
			map.put("toInstallTime", toCreateTime);
		}
		if(!StringUtil.isEmpty(orgId)){
			map.put("orgId", orgId);
		}
		String orderbyStr = null;
		if (!StringUtil.isEmpty(sortname)) {
			if ("realName".equals(sortname)) {//姓名 排序
				orderbyStr = "order by u.real_name " + sortorder;
			} else if ("sex".equals(sortname)) {
				orderbyStr = "order by u.sex " + sortorder;//性别排序
			} else if ("loginName".equals(sortname)) {//用户名 排序
				orderbyStr = "order by u.login_name " + sortorder;
			} else if ("carBrandCn".equals(sortname)) {//汽车品牌排序
				orderbyStr = "order by d.car_brand " + sortorder;
			} else if ("carSeriesCn".equals(sortname)) {//汽车系列排序
				orderbyStr = "order by d.car_series " + sortorder;
			}else if ("carTypeCn".equals(sortname)) {//汽车型号排序
				orderbyStr = "order by d.car_type " + sortorder;
			}else if ("carYear".equals(sortname)) {//汽车年份排序
				orderbyStr = "order by d.car_year " + sortorder;
			}else if ("deviceName".equals(sortname)) {//汽车牌号排序
				orderbyStr = "order by d.device_name " + sortorder;
			}else if ("deviceMac".equals(sortname)) {//控制器MAC排序
				orderbyStr = "order by d.device_mac " + sortorder;
			}else if ("installTime".equals(sortname)) {//首安装时间排序
				orderbyStr = "order by d.install_time " + sortorder;
			}else if ("cardTypeCn".equals(sortname)) {//充值卡类型排序
				orderbyStr = "order by r.card_type " + sortorder;
			}else if ("total".equals(sortname)) {//剩余服务时间排序
				orderbyStr = "order by remainCount " + sortorder;
			}else if ("hepaInstallTime".equals(sortname)) {//过滤器安装时间排序
				orderbyStr = "order by hepaInstallTime " + sortorder;
			}else if ("orgName".equals(sortname)) {//所属门店排序
				orderbyStr = "order by orgName " + sortorder;
			}
		} else {
			orderbyStr = "order by endTime asc, u.user_id desc";
		}
		map.put("deviceStatus", "5");//限定为已绑定的设备
		map.put("orderBy", orderbyStr);
		List<Map> list=new ArrayList<Map>();
		list=userService.selectUserAllByPager(map);
	
		
		int allSize =userService.selectUserAllByPagerCount(map);
		
		jsonMap.put("loginName", loginName);
		jsonMap.put("realName", realName);
		jsonMap.put("deviceName", deviceName);
		jsonMap.put("cardType", cardType);
		jsonMap.put("fromCreateTime", fromCreateTime);
		jsonMap.put("toCreateTime", toCreateTime);
		
		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;
	}
	
	/**
	 * 导出Excel
	 * 
	 * @param curNo
	 * @param curSize
	 * @param sortname
	 * @param sortorder
	 * @param loginName
	 * @param realName
	 * @param deviceName
	 * @param cardType
	 * @param fromCreateTime
	 * @param toCreateTime
	 * @return
	 */
	@RequestMapping("listAdminUserMangerExportExcel")
	@ResponseBody
	public Map<String,Object> listAdminUserMangerExportExcel(String sortname,String sortorder,String loginName,String realName,String deviceName,String cardType,String fromCreateTime,String toCreateTime,String orgId,HttpServletResponse response){
		int flag = 0;// 导出到Excel失败
		OutputStream out = null;
		InputStream is = null;
		String path = request.getSession().getServletContext().getRealPath("download");
		String currentDate = DateUtil.getFormatDate(new Date(),"yyyyMMddHHmmss");
		String fileName = "用户车辆信息统计报表" + currentDate + ".xlsx";
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!StringUtil.isEmpty(loginName)) {
				map.put("loginName", loginName);
			}
			if (!StringUtil.isEmpty(realName)) {
				map.put("realName", realName);
			}
			if (!StringUtil.isEmpty(deviceName)) {
				map.put("deviceName", deviceName);
			}
			if (!StringUtil.isEmpty(cardType)) {
				if ("0".equals(cardType)) {
					map.put("cardTypeFree", cardType);
				} else {
					map.put("cardType", cardType);
				}

			}
			if (!StringUtil.isEmpty(fromCreateTime)) {
				map.put("fromInstallTime", fromCreateTime);
			}
			if (!StringUtil.isEmpty(toCreateTime)) {
				map.put("toInstallTime", toCreateTime);
			}
			if (!StringUtil.isEmpty(orgId)) {
				map.put("orgId", orgId);
			}
			String orderbyStr = null;
			if (!StringUtil.isEmpty(sortname)) {
				if ("realName".equals(sortname)) {// 姓名 排序
					orderbyStr = "order by u.real_name " + sortorder;
				}else if ("orgName".equals(sortname)) {
					orderbyStr = "order by d.org_Id " + sortorder;// 所属门店排序
				} else if ("sex".equals(sortname)) {
					orderbyStr = "order by u.sex " + sortorder;// 性别排序
				} else if ("loginName".equals(sortname)) {// 用户名 排序
					orderbyStr = "order by u.login_name " + sortorder;
				} else if ("carBrandCn".equals(sortname)) {// 汽车品牌排序
					orderbyStr = "order by d.car_brand " + sortorder;
				} else if ("carTypeCn".equals(sortname)) {// 汽车型号排序
					orderbyStr = "order by d.car_type " + sortorder;
				} else if ("deviceName".equals(sortname)) {// 汽车牌号排序
					orderbyStr = "order by d.device_name " + sortorder;
				} else if ("deviceMac".equals(sortname)) {// 控制器MAC排序
					orderbyStr = "order by d.device_mac " + sortorder;
				} else if ("installTime".equals(sortname)) {// 首安装时间排序
					orderbyStr = "order by d.install_time " + sortorder;
				} else if ("cardTypeCn".equals(sortname)) {// 充值卡类型排序
					orderbyStr = "order by r.card_type " + sortorder;
				} else if ("total".equals(sortname)) {// 剩余服务时间排序
					orderbyStr = "order by remainCount " + sortorder;
				} else if ("hepaInstallTime".equals(sortname)) {// 过滤器安装时间排序
					orderbyStr = "order by hepaInstallTime " + sortorder;
				}
			} else {
				orderbyStr = "order by endTime asc, u.user_id desc";
			}
			map.put("orderBy", orderbyStr);
			List<Map> list = new ArrayList<Map>();
			list = userService.selectUserAllByExportExcel(map);
			System.out.println("############列表大小："+list.size());
			List<String[]> titles = new ArrayList<String[]>();// Excel标识集合
			String[] title1 = {"所属组织机构", "姓名 ", "性别", "用户名", "汽车品牌", "汽车型号", "汽车牌号",
					"控制器MAC", "首安装时间", "充值卡类型", "剩余服务时间", "过滤器安装时间" };
			titles.add(title1);
			List<String[]> userDeviceData = new ArrayList<String[]>();// 数据项集合
			for (Map oneMap : list) {
				Date d = (Date) oneMap.get("installTime");
				if (d != null) {
					oneMap.put("installTime", DateUtil.getFormatDate(d, ""));// 设备首安装时间
				}
				Date d2 = (Date) oneMap.get("hepaInstallTime");
				if (d2 != null) {
					oneMap.put("hepaInstallTime",
							DateUtil.getFormatDate(d2, ""));// 过滤器安装时间
				}
				
				String[] userDeviceData1 = { // 数据项
						StringUtil.trimNull(oneMap.get("orgName") + ""),// 所属组织机构
						StringUtil.trimNull(oneMap.get("realName") + ""),// 姓名
						StringUtil.trimNull(oneMap.get("sex") + ""),// 性别
						StringUtil.trimNull(oneMap.get("loginName") + ""),// 用户名
						StringUtil.trimNull(oneMap.get("carBrandCn") + ""), // 汽车品牌
						StringUtil.trimNull(oneMap.get("carTypeCn") + ""), // 汽车型号
						StringUtil.trimNull(oneMap.get("deviceName") + ""),// 汽车牌号
						StringUtil.trimNull(oneMap.get("deviceMac") + ""),// 控制器MAC
						StringUtil.trimNull(oneMap.get("installTime") + ""),// 首安装时间
						StringUtil.trimNull(oneMap.get("cardTypeCn") + ""),// 充值卡类型
						StringUtil.trimNull(oneMap.get("total") + ""),// 剩余服务时间
						StringUtil.trimNull(oneMap.get("hepaInstallTime") + ""),// 过滤器安装时间
				};
				userDeviceData.add(userDeviceData1);

			}
			

			List<List<String[]>> data = new ArrayList<List<String[]>>();
			data.add(userDeviceData);
			String[] sheetName = { "用户车辆信息统计报表" }; //
			SmsExcelUtil.writeToFile(path + File.separator + fileName,
					sheetName, titles, data);
			System.out.println("####导出成功:" + path + File.separator + fileName);

			File file = new File(path + File.separator + fileName);
			log.info("开始下载,服务路径：" + path + File.separator + fileName);
			is = new FileInputStream(file);
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download"
					+ ";  charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ java.net.URLEncoder.encode(fileName, "UTF-8"));
			response.setHeader("Content-Length",
					(String.valueOf(file.length())));
			int i;
			while ((i = is.read()) != -1) {
				out = response.getOutputStream();
				out.write(i);
				out.flush();
			}

			flag = 1;// 1 表示导出到Excel成功

			jsonMap.put("loginName", loginName);
			jsonMap.put("realName", realName);
			jsonMap.put("deviceName", deviceName);
			jsonMap.put("cardType", cardType);
			jsonMap.put("fromCreateTime", fromCreateTime);
			jsonMap.put("toCreateTime", toCreateTime);
			
		} catch (Exception e) {
			log.info(fileName + " ,文件下载异常：" + e);
		} finally {
			try {
				is.close();
				out.close();
			} catch (IOException e) {
				log.info(fileName + " ,finally 文件下载处理关闭异常：" + e);
			}
		}
		jsonMap.put("flag", flag);
		return jsonMap;
	}
	
	/**
	 * 进入修改页面
	 * @param userId
	 * @param deviceId
	 * @param rechargeId
	 * @param hepaId
	 * @return
	 */
	@RequestMapping("/toUpdUserAll")
	public ModelAndView toUpdUserAll(String userId,String deviceId,String rechargeId,String hepaId,String msg) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/admin/updAdminUserManger.jsp");
		User user=new User();
		
		
		if(!StringUtil.isEmpty(userId)){
			user=userService.selectByPrimaryKey(Integer.valueOf(userId));
		}
		mv.addObject("user", user);
		int curYear=DateUtil.getYear();//当前年减去20年做为下拉年份
		List<Integer> yearList=new ArrayList<Integer>();
		for(int i=curYear;i>=curYear-SystemConstant.SYSTEM_YEARS;i--){
			yearList.add(i);
		}
		mv.addObject("yearList", yearList);
				
		//销售员列表
		Integer roleId=ButtonPermissionConstant.SALESUSERLIST_SALES;//销售员角色id
		List<User> userList=userService.selectUserByRoleId(roleId);
		mv.addObject("userList", userList);
		
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		User loginUser=(User)session.getAttribute("user");
		Integer orgId = loginUser.getOrgId();
		//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
		List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
		for (Role role : roleList) {
			if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId==ButtonPermissionConstant.ROOT_ORG_ID) {// 超级管理员
				break;
			} else{
				
				mv.addObject("disabled", "disabled=\"disabled\"");
			}
		}
		mv.addObject("orgMap", orgMap);
		mv.addObject("orgId", user.getOrgId());//被修改人员的所属门店
		mv.addObject("msg", msg);
		return mv;
	}
	

	/**
	 * 进入客户转移初始页面
	 * 
	 * @param userIds
	 * @param deviceIds
	 * @param rechargeIds
	 * @param hepaIds
	 * @param deviceNames
	 * @return
	 */
	@RequestMapping("toUpdAdminUserOrgId")
	public ModelAndView toUpdAdminUserOrgId(String userIds,String deviceIds,String rechargeIds,String hepaIds,String deviceNames ){
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/admin/updAdminUserOrgId.jsp");
		mv.addObject("userIds",userIds );
		mv.addObject("deviceIds", deviceIds);
		mv.addObject("rechargeIds", rechargeIds);
		mv.addObject("hepaIds",hepaIds );
		mv.addObject("deviceNames", deviceNames);
		
		//销售员列表
		Integer roleId=ButtonPermissionConstant.SALESUSERLIST_SALES;//销售员角色id
		List<User> userList=userService.selectUserByRoleId(roleId);
		mv.addObject("userList", userList);
		
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		User loginUser=(User)session.getAttribute("user");
		Integer orgId = loginUser.getOrgId();
		//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
		List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
		for (Role role : roleList) {
			if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId==ButtonPermissionConstant.ROOT_ORG_ID) {// 超级管理员
				break;
			} else{
				
				mv.addObject("disabled", "disabled=\"disabled\"");
			}
		}
		mv.addObject("orgMap", orgMap);
		mv.addObject("orgId", orgId);
		
		return mv;
	}
	
	/**
	 * 客户转移保存
	 * 
	 * @param userIds
	 * @param deviceIds
	 * @param rechargeIds
	 * @param hepaIds
	 * @param deviceNames
	 * @param orgId
	 * @param saleUserId
	 * @return
	 */
	@RequestMapping("updAdminUserOrgId")
	public ModelAndView updAdminUserOrgId(String userIds,String deviceIds,String deviceNames,String orgId,String saleUserId){
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/admin/updAdminUserOrgId.jsp");
		mv.addObject("userIds",userIds );
		mv.addObject("deviceIds", deviceIds);
	
		mv.addObject("deviceNames", deviceNames);
		Map<String, Object> jsonMap =adminUserManagerService.updAdminUserOrgId(userIds, deviceIds,deviceNames, orgId, saleUserId);
		String info=jsonMap.get("info")+"";
		mv.addObject("message", "success");
		mv.addObject("info", info);
		return mv;
	}
}
