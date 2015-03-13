/**
 * @(#)UserAction.java	09/11/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-11
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
import cn.app118.service.role.IRoleService;
import cn.app118.service.user.IUserRoleRelaService;
import cn.app118.service.user.IUserService;

/**
 * 后台用户管理控制类
 * 
 * @author wRitchie
 *
 */
@Controller
@RequestMapping("accountAction")
public class AccountAction extends BaseAction {

	@Resource
	private IUserService userService;// 用户服务类
	

	@Resource
	private ISmsService smsService;// 短信服务类
	
	@Resource
	private IOrgService orgService;//组织机构服务类
	
	@Resource
	private IRoleService roleService;//角色服务类
	
	@Resource
	private IUserRoleRelaService userRoleRelaService;//用户角色关联服务类
	/**
	 * 进入初始后台用户列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/initAccount")
	public ModelAndView initAccount() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/account/listAccount.jsp");
		
		//当前登录用户
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
				mv.addObject("updUserType", "1");//显示  转app用户按钮
				break;
			} else {
				mv.addObject("disabled", "disabled=\"disabled\"");
			}
		}
		
		return mv;
	}
	
	/**
	 * 后台用户列表页面异步请求
	 * 
	 * @return
	 */
	@RequestMapping("listAccount")
	@ResponseBody
	public Map<String,Object> listAccount(String curNo,String curSize,String sortname,String sortorder,String loginName,String realName,String fromCreateTime,String toCreateTime,String orgId){
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
		map.put("userType", "1");//只查用户类型为后台用户
		
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
		for(Map oneUser:list){
			Date d=(Date)oneUser.get("createTime");
			oneUser.put("createTime",DateUtil.getFormatDate(d, ""));
			oneUser.put("userTypeCn", SystemConstant.getLogTypeMap().get(oneUser.get("userType")+""));//用户类型
			
			
			Integer userId=(Integer)oneUser.get("userId");
			//根据用户标识查询所属用户角色start
			Map param=new HashMap();
			param.put("userId", userId);
			List<Role>  roleList=roleService.selectRoleByUserId(param);
			int roleSize = roleList.size();
			String roleNames="";
			String roleIds="";
			Role oneRole=null;
			for(int i=0;i<roleSize;i++){
				oneRole=roleList.get(i);
				if(i!=roleSize-1){
					roleNames+=oneRole.getRoleName()+",";
					roleIds+=oneRole.getRoleId()+",";
				}else{
					roleNames+=oneRole.getRoleName();
					roleIds+=oneRole.getRoleId();
				}
			}
			oneUser.put("roleNames", roleNames);
			oneUser.put("roleIds", roleIds);
			//根据用户标识查询所属用户角色end
		}
		
		int allSize =userService.selectByPagerCount(map);
		
		jsonMap.put("loginName", loginName);
		jsonMap.put("nickname", realName);
		jsonMap.put("fromCreateTime", fromCreateTime);
		jsonMap.put("toCreateTime", toCreateTime);
		
		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;
	}
	
	/**
	 * 初始进入新增后台用户页面
	 * @return
	 */
	@RequestMapping("/toInsertAccount")
	public ModelAndView toInsertAccount() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/account/insertAccount.jsp");
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		
		
		User loginUser=(User)session.getAttribute("user");
		Integer orgId = loginUser.getOrgId();
		mv.addObject("orgId", orgId);//当前登录人员的所属门店
		boolean isSuperAdmin=false;
		//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
		List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
		for (Role role : roleList) {
			if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId==ButtonPermissionConstant.ROOT_ORG_ID) {// 超级管理员
				isSuperAdmin=true;
				break;
			} else{
				
				mv.addObject("disabled", "disabled=\"disabled\"");
			}
		}
		
	
		
		//获取角色列表信息
		List<Role> allRoleList=roleService.selectBySeletive(new Role());
		List<Role> list=new ArrayList<Role>();
		if(!isSuperAdmin){//非超级管理员
			for(Role role:allRoleList){
				if(!(role.getRoleId()==9||role.getRoleId()==10)){//去掉超级管理员及库管理员
					list.add(role);
				}
			}
		}else{
			list=allRoleList;
		}
		mv.addObject("orgMap", orgMap);
		mv.addObject("list", list);
		return mv;
	}
	
	/**
	 * 添加后台用户
	 * 添加后台用户：实现用户注册及授予角色权限
	 * 
	 * @param loginName  手机号
	 * @param realName 姓名
	 * @param sex 性别
	 * @param carCategory 用户类别
	 * @param roleIds 分配的角色标识
	 * @param orgId 组织机构标识
	 * @return
	 */
	@RequestMapping("/addAccount")
	@Transactional
	public ModelAndView addAccount(User user,String roleIds) {
		Integer userId=null;//用户标识
		// 变量声明
	    ModelAndView mv = new ModelAndView("/app118/accountAction/toInsertAccount");
	    try {
			User opUser=(User)session.getAttribute("user");
			Integer opUserId=opUser.getUserId();
			
			//1、注册用户
			User u = new User();
			String loginName = user.getLoginName();
			u.setLoginName(loginName);
			List<User> exitUsers = userService.findUser(u);
			if (exitUsers.size() >= 1) {//1、判断该手机号登录名是否存在
				userId=exitUsers.get(0).getUserId();
				String realName=exitUsers.get(0).getRealName();
				log.info("用户已存在。");
				mv.addObject("message", "exist");
			}else{//1、不存的手机用户，则新建该手机号码的用户
				
				user.setPhoneNumber(loginName);//手机号
				user.setCreateTime(new Date());//创建时间
				user.setUserType("1");//用户类型  1：后台 3：app
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
				
				String result = userService.addUser(user);
				if ("1".equals(result)) {//表示注册成功
					userId=user.getUserId();
					//用户增加成功后，增加用户角色关联关系  TODO
					if(!StringUtil.isEmpty(roleIds)){
						String [] roleIdArray=roleIds.split(",");
						UserRoleRela userRoleRela=null;
						for(String oneRoleId:roleIdArray){
							userRoleRela=new UserRoleRela();
							userRoleRela.setUserId(userId);//用户标识
							userRoleRela.setRoleId(Integer.valueOf(oneRoleId));//角色标识
							userRoleRela.setOpUserId(opUserId);//操作人标识
							userRoleRela.setCreateTime(new Date());//关联时间
							userRoleRelaService.addUserRoleRela(userRoleRela);
						}
					}
					
					// 调用短信发送
					log.info("#######用户名："+loginName + "密码：" + pwd);
					smsService.sendSms(loginName, msg,"1",opUserId);
					
					log.info("用户注册成功。");
					mv.addObject("message", "success");
				} else {// 否则表示注册失败
					log.info("用户注册失败。");
					
				}
			}
		} catch (Exception e) {
			log.info("用户注册异常："+e);
		}
		return mv;
	}
	
	/**
	 * 删除后台用户
	 * 
	 */
	@RequestMapping("/delAccount")
	@ResponseBody
	public Map<String, Object> delAccount(String userIds) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int flag = 0;// 删除失败
		try {
			if (!StringUtil.isEmpty(userIds)){
				System.out.println(userIds);
				int result=userService.delAccountAndUserRoleRela(userIds);
				flag = result;// 1 表示删除成功
			}
		} catch (Exception e) {
			log.info("删除用户异常："+ e);
			flag = 0;
		}
		resultMap.put("flag", flag);
		return resultMap;
	}
	
	
	/**
	 * 进入修改用户页面
	 * @return
	 */
	@RequestMapping("/toUpdAccount")
	public ModelAndView toUpdAccount(String loginName,String realName,String sex,String userId,String orgId,String roleIds,String roleNames) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/account/updateAccount.jsp");
		mv.addObject("loginName", loginName);
		mv.addObject("realName",realName );
		mv.addObject("sex", sex);
		mv.addObject("userId", userId);
		mv.addObject("orgId", orgId);
		mv.addObject("roleIds", StringUtil.trimNull(roleIds));
		mv.addObject("roleNames", StringUtil.trimNull(roleNames));
		
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		
		List<Role> allRoleList=roleService.selectBySeletive(new Role());//角色列表信息
		List<Role> roleList= (List<Role>) session.getAttribute("roleList");//当前登录的角色列表
		List<Role> list=new ArrayList<Role>();
		User loginUser=(User)session.getAttribute("user");
		Integer orgId2 = loginUser.getOrgId();
		//判断当前用户是否为超级管理员
		boolean isSuperAdmin=false;
		for (Role role : roleList) {
			if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId2==ButtonPermissionConstant.ROOT_ORG_ID) {// 超级管理员
				isSuperAdmin=true;
				list=allRoleList;
				break;
			}
				
		}
		//非超级管理员去掉超级管理员及库管理员角色
		for(Role role:allRoleList){
			if(isSuperAdmin){
				list=allRoleList;
			}else{
				if(!(role.getRoleId()==9||role.getRoleId()==10)){//去掉超级管理员及库管理员
					list.add(role);
				}
			}
		}
		//初始化已经授予的角色
		for(Role role:list){
			String roleId=role.getRoleId()+"";
			String flag=isCheck(roleId, roleIds);
			role.setRemark1(flag);
		}
		mv.addObject("orgMap", orgMap);
		mv.addObject("list", list);
		return mv;
	}
	
	/**
	 * 判断角色是否勾选
	 * @param roleId
	 * @param roleIds
	 * 
	 * @return 0：不勾选   1：勾选
	 *  
	 */
	private String isCheck(String roleId,String roleIds){
		String flag="0";
		if(!(StringUtil.isEmpty(roleIds)||StringUtil.isEmpty(roleId))){
			String [] roleIdArray=roleIds.split(",");
			for(String oneRoleId:roleIdArray){
				if(roleId.equals(oneRoleId)){
					flag="1";
					return flag;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 修改用户
	 * 修改后台用户：实现后台用户及用户角色授权的修改
	 * 
	 * @param loginName  手机号
	 * @param realName 姓名
	 * @param sex 性别
	 * @param carCategory 用户类别
	 * @param roleIds 分配的角色标识
	 * @param orgId 组织机构标识
	 * @return
	 */
	@RequestMapping("/updAccount")
	@Transactional
	public ModelAndView updAccount(User user,String roleIds) {
		Integer userId=null;//用户标识
		// 变量声明
	    ModelAndView mv = new ModelAndView("/pages/account/updateAccount.jsp");
	    try {
			User opUser=(User)session.getAttribute("user");
			Integer opUserId=opUser.getUserId();
			//user.setUserId(opUserId);//修改人标识
			user.setLoginName(null);
			user.setModifyTime(new Date());//修改时间
			     //1、修改用户
			int result = userService.updateByPrimaryKeySelective(user);
			if (result>0) {//表示修改成功
				userId=user.getUserId();
				
				//2、删除以前授予的用户角色权限
				result=userRoleRelaService.deleteByUserId(userId);
				if(result>0){
					log.info("删除以前授予的用户角色权限成功。");
				}
				
				//3、删除用户角色权限后，增加用户角色关联关系 
				if(!StringUtil.isEmpty(roleIds)){
					String [] roleIdArray=roleIds.split(",");
					UserRoleRela userRoleRela=null;
					for(String oneRoleId:roleIdArray){
						userRoleRela=new UserRoleRela();
						userRoleRela.setUserId(userId);//用户标识
						userRoleRela.setRoleId(Integer.valueOf(oneRoleId));//角色标识
						userRoleRela.setOpUserId(opUserId);//操作人标识
						userRoleRela.setCreateTime(new Date());//关联时间
						userRoleRelaService.addUserRoleRela(userRoleRela);
					}
				}
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
	
	
	/**
	 * 查看后台用户明细
	 * @param userId
	 * @return
	 */
	@RequestMapping("viewAccount")
	public ModelAndView viewAccount(Integer userId,String orgName){
		// 变量声明
	    ModelAndView mv = new ModelAndView("/pages/account/viewAccount.jsp");
	    //1、根据userId查询用户信息
	    User user=userService.selectByPrimaryKey(userId);
	    Date createTime=user.getCreateTime();
	    mv.addObject("createTime",DateUtil.getFormatDate(createTime, ""));
	    Date modifyTime=user.getModifyTime();
	    if(null!=modifyTime){
	    	mv.addObject("modifyTime",DateUtil.getFormatDate(modifyTime, ""));
	    }
	    
	    //根据用户标识查询所属用户角色start
	    //2、根据userId查询所属角色
		Map param=new HashMap();
		param.put("userId", userId);
		List<Role>  roleList=roleService.selectRoleByUserId(param);
		int roleSize = roleList.size();
		String roleNames="";
		String roleIds="";
		Role oneRole=null;
		for(int i=0;i<roleSize;i++){
			oneRole=roleList.get(i);
			if(i!=roleSize-1){
				roleNames+=oneRole.getRoleName()+",";
				roleIds+=oneRole.getRoleId()+",";
			}else{
				roleNames+=oneRole.getRoleName();
				roleIds+=oneRole.getRoleId();
			}
		}
		//根据用户标识查询所属用户角色end
		mv.addObject("roleNames", roleNames);
		mv.addObject("orgName", orgName);
		mv.addObject("roleIds", roleIds);
	    mv.addObject("user", user);
	    return mv;
	}
	
	/**
	 * 修改后台用户密码
	 * @return
	 */
	@RequestMapping("/toUpdAcountPwd")
	@Transactional
	public ModelAndView toUpdAcountPwd() {
		 ModelAndView mv = new ModelAndView("/pages/account/updateAccountPwd.jsp");
		 return mv;
	}
	
	/**
	 * 修改后台用户密码
	 * 
	 * @param user
	 *    userId:用户标识   password:新密码
	 * @param curPwd 原密码
	 * @return
	 */
	@RequestMapping("/updAccountPwd")
	@Transactional
	public ModelAndView updAccountPwd(User user) {
		Integer userId=user.getUserId();//用户标识
		// 变量声明
	    ModelAndView mv = new ModelAndView("/pages/account/updateAccountPwd.jsp");
	    try {
			if(userId==null){
				mv.addObject("message", "none");
				return mv;
			}
//			System.out.println("###########userId:"+user.getUserId()+"pwd:"+user.getPassword());
			String pwdTmp=MD5Util.encode(user.getPassword());//模拟客户端app生成的md5密码，实现后台用户与app用户密码的一致性
			String sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY).substring(0, 16);
			String pwdEncrypt = AESUtil.encrypt(pwdTmp, sKey).substring(0,16);
			
			user.setPassword(pwdEncrypt);//新密码
			user.setModifyTime(new Date());//修改时间
		    //修改用户
			int result = userService.updateByPrimaryKeySelective(user);
			if (result>0) {//表示修改成功
				log.info("修改用户密码成功。");
				mv.addObject("message", "success");
			} else {// 否则表示修改用户失败
				log.info("修改用户密码失败。");
			}
		} catch (Exception e) {
			log.info("修改用户异常："+e);
		}
		return mv;
	}
}
