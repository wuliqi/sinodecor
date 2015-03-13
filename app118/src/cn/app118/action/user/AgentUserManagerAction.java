/**
 * @(#)AgentUserManagerAction.java	09/28/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-28
 */
package cn.app118.action.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.ButtonPermissionConstant;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.AESUtil;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.GenerateIdUtil;
import cn.app118.framework.util.MD5Util;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.Org;
import cn.app118.model.Role;
import cn.app118.model.User;
import cn.app118.model.UserRoleRela;
import cn.app118.service.message.ISmsService;
import cn.app118.service.org.IOrgService;
import cn.app118.service.user.IUserService;

/**
 * app用户管理
 * app用户管理仅对sys_user单表管理
 * 
 * @author wRitchie
 *
 */
@Controller
@RequestMapping("agentUserManagerAction")
public class AgentUserManagerAction extends BaseAction{

	@Resource
	private IUserService userService;// 用户服务类
	
	@Resource
	private IOrgService orgService;//组织机构服务类
	
	@Resource
	private ISmsService smsService;// 短信服务类
	
	/**
	 * app用户管理初始页面
	 * 
	 * @return
	 */
	@RequestMapping("/initAgentUser")
	public ModelAndView initAgentUser() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/agent/listAgentUser.jsp");
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
				mv.addObject("updUserType", "1");//显示  转后台用户按钮
				break;
			} else {
				mv.addObject("disabled", "disabled=\"disabled\"");
			}
		}
		return mv;
	}
	
	/**
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
	 * @param orgId
	 * @return
	 */
	@RequestMapping("listAgentUser")
	@ResponseBody
	public Map<String,Object> listAgentUser(String curNo,String curSize,String sortname,String sortorder,String loginName,String realName,String deviceName,String cardType,String fromCreateTime,String toCreateTime,String orgId){
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
		if(!StringUtil.isEmpty(fromCreateTime)){
			map.put("fromCreateTime", fromCreateTime);
		}
		if(!StringUtil.isEmpty(toCreateTime)){
			map.put("toCreateTime", toCreateTime);
		}
		if(!StringUtil.isEmpty(orgId)){
			map.put("orgId", orgId);
		}
		
		
		//排序
		String orderbyStr = null;
		if (!StringUtil.isEmpty(sortname)) {
			if ("createTime".equals(sortname)) {
				orderbyStr = "order by create_time " + sortorder;
			} else if ("userId".equals(sortname)) {
				orderbyStr = "order by user_id " + sortorder;
			}else if ("orgName".equals(sortname)) {
				orderbyStr = "order by org_id " + sortorder;
			} else if ("loginName".equals(sortname)) {
				orderbyStr = "order by login_name " + sortorder;
			} else if ("realName".equals(sortname)) {
				orderbyStr = "order by real_name " + sortorder;
			} else if ("sex".equals(sortname)) {
				orderbyStr = "order by sex " + sortorder;
			}
		} else {
			orderbyStr = "order by user_id desc";
		}
		map.put("orderBy", orderbyStr);
		
		List<Map> list=new ArrayList<Map>();
		list=userService.selectByPager(map);
		String userType="";
		for(Map oneUser:list){
			Date d=(Date)oneUser.get("createTime");
			oneUser.put("createTime",DateUtil.getFormatDate(d, ""));
			userType=oneUser.get("userType")+"";
			oneUser.put("userTypeCn", SystemConstant.getLogTypeMap().get(userType));
		}
		
		int allSize =userService.selectByPagerCount(map);
		
		jsonMap.put("loginName", loginName);
		jsonMap.put("realName", realName);
		jsonMap.put("fromCreateTime", fromCreateTime);
		jsonMap.put("toCreateTime", toCreateTime);
		
		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;
	}
	
	/**
	 * 初始进入注册用户
	 * @return
	 */
	@RequestMapping("/toInsertAgentUser")
	public ModelAndView toInsertAgentUser() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/agent/insertAgentUser.jsp");
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		mv.addObject("orgMap", orgMap);
		
		User loginUser=(User)session.getAttribute("user");
		Integer orgId = loginUser.getOrgId();
		mv.addObject("orgId", orgId);//当前登录人员的所属门店
		
		//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
		List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
		for (Role role : roleList) {
			if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId==ButtonPermissionConstant.ROOT_ORG_ID) {// 超级管理员
				break;
			} else{
				mv.addObject("disabled", "disabled=\"disabled\"");
			}
		}
		return mv;
	}
	
	@RequestMapping("/addAgentUser")
	@Transactional
	public ModelAndView addAgentUser(User user) {
		// 变量声明
	    ModelAndView mv = new ModelAndView("/app118/agentUserManagerAction/toInsertAgentUser");
	    try {
			//1、注册用户
			User u = new User();
			String loginName = user.getLoginName();
			u.setLoginName(loginName);
			List<User> exitUsers = userService.findUser(u);
			if (exitUsers.size() >= 1) {//1、判断该手机号登录名是否存在
				log.info("用户已存在。");
				mv.addObject("message", "exist");
			}else{//1、不存的手机用户，则新建该手机号码的用户
				user.setPhoneNumber(loginName);//手机号
				user.setCreateTime(new Date());//创建时间
				user.setUserType("3");//用户类型  1：后台 3：app
				user.setLastLoginTime(new Date());//最后登录时间初始化
				user.setLastChangePwdTime(new Date());//最后修改密码时间初始化
				String pwd = GenerateIdUtil.getRandomNumber(6);//随机密码
				//密码采用手机号码后4位
				int max=loginName.length();
				pwd=loginName.substring(max-4, max);
				//密码发送用户手机
				String msg = "注册成功，您的密码为：" + pwd+ "，请登录后及时修改，（请勿向任何人提供您收到的密码）"+SystemConstant.COMPANY_SIGNATURE;
				String pwdTmp=MD5Util.encode(pwd);//模拟客户端app生成的md5密码，实现后台用户与app用户密码的一致性
				String sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY).substring(0, 16);
				String pwdEncrypt = AESUtil.encrypt(pwdTmp, sKey).substring(0,16);
				user.setPassword(pwdEncrypt);
				user.setRemark("app注册用户。");
				String result = userService.addUser(user);
				if ("1".equals(result)) {//表示注册成功
					// 调用短信发送
					log.info("#######app用户管理,用户名："+loginName + "密码：" + pwd);
					User loginUser=(User)session.getAttribute("user");
					Integer userId=0;
					if(loginUser!=null){
						userId=loginUser.getUserId();
					}
					smsService.sendSms(loginName, msg,"1",userId);
					log.info("用户注册成功。");
					mv.addObject("message", "success");
				} else {// 否则表示注册失败
					log.info("用户注册失败。");
				}
			}
		} catch (Exception e) {
			log.info("用户注册异常："+e);
		}
	    mv.addObject("u", user);
		return mv;
	}
	
	@RequestMapping("/toUpdAgentUser")
	public ModelAndView toUpdAgentUser(String loginName,String realName,String sex,String userId,String orgId) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/agent/updAgentUser.jsp");
		mv.addObject("loginName", loginName);
		mv.addObject("realName",realName );
		mv.addObject("sex", sex);
		mv.addObject("userId", userId);
		mv.addObject("orgId", orgId);
	
		
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		
		List<Role> roleList= (List<Role>) session.getAttribute("roleList");//当前登录的角色列表
		User loginUser=(User)session.getAttribute("user");
		Integer orgId2 = loginUser.getOrgId();
		//判断当前用户是否为超级管理员
		for (Role role : roleList) {
			if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId2==ButtonPermissionConstant.ROOT_ORG_ID) {// 超级管理员
				break;
			}
		}
		mv.addObject("orgMap", orgMap);
		return mv;
	}
	
	@RequestMapping("/updAgentUser")
	@Transactional
	public ModelAndView updAgentUser(User user) {
		// 变量声明
	    ModelAndView mv = new ModelAndView("/app118/agentUserManagerAction/toUpdAgentUser");
	    try {
			User opUser=(User)session.getAttribute("user");
			Integer opUserId=opUser.getUserId();
			user.setLoginName(null);
			user.setModifyTime(new Date());//修改时间
			     //1、修改用户
			int result = userService.updateByPrimaryKeySelective(user);
			if (result>0) {//表示修改成功
				log.info("修改用户成功。");
				mv.addObject("message", "success");
			} else {// 否则表示修改用户失败
				log.info("修改用户失败。");
			}
		} catch (Exception e) {
			log.info("修改用户异常："+e);
		}
		return mv;
	}
	
	@RequestMapping("/delAgentUser")
	@ResponseBody
	public Map<String, Object> delAgentUser(String userIds) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int flag = 0;// 删除失败
		try {
			if (!StringUtil.isEmpty(userIds)){
				System.out.println("userIds:"+userIds);
				String []userIdArray=userIds.split(",");
				int result=0;
				for(String oneUserId:userIdArray){
					if(!StringUtil.isEmpty(oneUserId)){
						result=userService.deleteByPrimaryKey(Integer.valueOf(oneUserId));
					}
				}
				flag = result;// 1 表示删除成功
			}
		} catch (Exception e) {
			log.info("删除用户异常："+ e);
			flag = 0;
		}
		resultMap.put("flag", flag);
		return resultMap;
	}
	
}
