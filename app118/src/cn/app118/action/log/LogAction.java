/**
 * @(#)UserAction.java	12/01/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-12-01
 */
package cn.app118.action.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.ButtonPermissionConstant;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.Org;
import cn.app118.model.Role;
import cn.app118.model.User;
import cn.app118.service.log.ILogService;
import cn.app118.service.org.IOrgService;

/**
 * 日志控制类
 * 
 * @author wRitchie
 *
 */
@Controller
@RequestMapping("logAction")
public class LogAction extends BaseAction{
	@Resource
	private ILogService logService;
	
	@Resource
	private IOrgService orgService;//组织机构服务类
	/**
	 * 日志列表初始页
	 * 
	 * @return
	 */
	@RequestMapping("initLog")
	public ModelAndView initLog(String logType){
		ModelAndView mv = new ModelAndView("/pages/log/listLog.jsp");
		mv.addObject("logType", logType);
		
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		User loginUser=(User)session.getAttribute("user");
		Integer orgId = loginUser.getOrgId();
		mv.addObject("orgId", orgId);//当前登录人员的所属门店
		mv.addObject("orgMap", orgMap);
		
		//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
		List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
		for (Role role : roleList) {
			if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId==ButtonPermissionConstant.ROOT_ORG_ID) {//超级管理员
				break;
			}else{
				mv.addObject("disabled", "disabled=\"disabled\"");
			}
		}
		return mv;
	}

	
	/**
	 * 日志列表页面异步请求
	 * 
	 * @return
	 */
	@RequestMapping("listLog")
	@ResponseBody
	public Map<String,Object> listLog(String curNo,String curSize,String sortname,String sortorder,String loginName,String ipAddress,String fromCreateTime,String toCreateTime,String orgId,String logType){
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
		if(!StringUtil.isEmpty(orgId)){
			map.put("orgId",orgId);
		}
		if(!StringUtil.isEmpty(ipAddress)){
			map.put("ipAddress", ipAddress);
		}
		if(!StringUtil.isEmpty(fromCreateTime)){
			map.put("fromCreateTime", fromCreateTime);
		}
		if(!StringUtil.isEmpty(toCreateTime)){
			map.put("toCreateTime", toCreateTime);
		}
		if(!StringUtil.isEmpty(logType)){
			map.put("logType", logType);
		}
		String orderbyStr=null;
		if(!StringUtil.isEmpty(sortname)){
			if("opTime".equals(sortname)){
				orderbyStr="order by  op_time "+sortorder;
			}else if("logId".equals(sortname)){
				orderbyStr="order by log_id "+sortorder;
			}else if("loginName".equals(sortname)){
				orderbyStr="order by login_name "+sortorder;
			}else if("ipAddress".equals(sortname)){
				orderbyStr="order by ip_address "+sortorder;
			}else if("opContent".equals(sortname)){
				orderbyStr="order by op_content "+sortorder;
			}else if("orgName".equals(sortname)){
				orderbyStr="order by org_id "+sortorder;
			}
		}else{
			orderbyStr="order by log_id desc";
		}
		map.put("orderBy", orderbyStr);
		List<Map> list=logService.selectByPager(map);
		for(Map oneLog:list){
			Date d=(Date)oneLog.get("opTime");
			if(null!=d){
				oneLog.put("opTime",DateUtil.getFormatDate(d, ""));//操作时间
			}
		}
		int allSize =logService.selectByPagerCount(map);
		
		jsonMap.put("loginName", loginName);
		jsonMap.put("ipAddress", ipAddress);
		jsonMap.put("fromCreateTime", fromCreateTime);
		jsonMap.put("toCreateTime", toCreateTime);
		/*jsonMap.put("curNo", curNo);
		jsonMap.put("curSize", curSize);*/
		
		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;
	}
}
