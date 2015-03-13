/**
 * @(#)RoleAction.java	07/21/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-21
 */
package cn.app118.action.role;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.Menu;
import cn.app118.model.Role;
import cn.app118.model.User;
import cn.app118.service.menu.IMenuService;
import cn.app118.service.role.IRoleService;
import cn.app118.service.user.IUserService;

/**
 * 后台角色控制类
 * @author 吴理琪
 *
 */
@Controller
@RequestMapping("roleAction")
public class RoleAction extends BaseAction{
	
	@Resource
	private IRoleService roleService;//角色服务类
	
	@Resource
	private IMenuService menuService;//菜单服务类
	
	@Resource
	private IUserService userService;//用户服务类
	/**
	 * 进入新增角色页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddRole")
	public ModelAndView toAddRole() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/role/addRole.jsp");
		return mv;
	}
	
	
	/**
	 * 进入角色列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/initRole")
	public ModelAndView initRole() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/role/listRole.jsp");
		return mv;
	}
	
	
	/**
	 * 角色列表页面异步请求
	 * 
	 * @return
	 */
	@RequestMapping("listRole")
	@ResponseBody
	public Map<String,Object> listRole(String curNo,String curSize,String sortname,String sortorder,String roleName,String isactive){
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
		if(!StringUtil.isEmpty(roleName)){
			map.put("roleName",roleName);
		}
		if(!StringUtil.isEmpty(isactive)){
			map.put("isactive", isactive);
		}
		
		String orderbyStr = null;
		if (!StringUtil.isEmpty(sortname)) {
			if ("roleId".equals(sortname)) {
				orderbyStr = "order by role_id " + sortorder;
			} else if ("roleName".equals(sortname)) {
				orderbyStr = "order by role_name " + sortorder;
			} else if ("isactive".equals(sortname)) {
				orderbyStr = "order by isactive " + sortorder;
			} else if ("userCount".equals(sortname)) {
				orderbyStr = "order by user_count " + sortorder;
			} else if ("createTime".equals(sortname)) {
				orderbyStr = "order by create_time " + sortorder;
			}
		} else {
			orderbyStr = " order by sort,role_id desc ";
		}
		map.put("orderBy", orderbyStr);
		
		List<Map> list=new ArrayList<Map>();
		list=roleService.selectByPager(map);
		for(Map r:list){
			isactive = r.get("isactive")+"";
			if("1".equals(isactive)){
				r.put("isactive", "启用");
			}else if("0".equals(isactive)){
				r.put("isactive", "废弃");
			}
			Date createTime=(Date)r.get("createTime");
			if(null!=createTime){
				r.put("createTime",DateUtil.getFormatDate(createTime, ""));
			}
			Integer roleId=(Integer)r.get("roleId");
			List<User> userList=userService.selectUserByRoleId(roleId);
			r.put("userCount", userList.size()+"");
		}
		int allSize =roleService.selectByPagerCount(map);
		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;
	}
	
	/**
	 * 增加角色
	 * 
	 * @return
	 */
	@RequestMapping("/addRole")
	public ModelAndView addRole(Role role,String menuId,String menuPid) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/role/addRole.jsp");
		User user=(User)session.getAttribute("user");
		
		try {
			role.setCreateTime(new Date());
			role.setIsactive("1");
			role.setUserId(user.getUserId());
			
			//将父结点的菜单权限追加授予
			TreeSet<String> menuSet=new TreeSet<String> ();

			String []menuPidArray=menuPid.split(",");
			for(String oneMenuPid:menuPidArray){
				if(!oneMenuPid.equals("0")){
					menuSet.add(oneMenuPid);
				}
			}
			
			String []menuIdArray=menuId.split(",");
			for(String oneMenuId:menuIdArray){
				menuSet.add(oneMenuId);
			}
		
			String menuIds="";
			for(String oneMenuId:menuSet){
				menuIds+=oneMenuId+",";
			}
			
			/*System.out.println("######################");
			System.out.println("menuId:"+menuId);
			System.out.println("menuPid:"+menuPid);
			System.out.println("合并后的menuId:"+menuIds);*/
			
			int result=roleService.addRole(role,menuIds);
			if(result>0){
				mv.addObject("message", "success");
				mv.addObject("info", "增加角色成功。");
			}else{
				mv.addObject("message", "error");
				mv.addObject("info", "增加角色失败。");
			}
		} catch (Exception e) {
			mv.addObject("message", "error");
			mv.addObject("info", "增加角色异常。");
			log.info("增加角色异常："+e);
		}
		
		return mv;
	}
	
	 /**
	  * 验证角色名称的唯一性
	  * @param roleName
	  * @return multi：不唯一，none：唯一
	  */
	 @RequestMapping("/checkRoleNameUnique")
	 @ResponseBody
	 public Map<String,Object> checkRoleNameUnique(String roleName){
		Map<String,Object> jsonMap=new HashMap<String, Object>();
		String  flag = "multi";//不唯一
		try{
			Role role=new Role();
			role.setRoleName(roleName);
			List<Role> list=roleService.selectBySeletive(role);
			int roleSize = list.size();
			if(roleSize==0){
				flag="none";//可以新增或修改
			}else if(roleSize==1){
				flag="modify";//可以修改
			}else{
				flag="multi";//不可以新增或修改
			}
		}catch(Exception e){
			log.info("验证角色名称的唯一性异常："+e);
			flag="fail";
		}
		jsonMap.put("message", flag);
		return jsonMap;
	 }
	 
	/**
	 * 删除角色及授予的菜单权限
	 * 
	 */
	@RequestMapping("/deleteRole")
	@ResponseBody
	public Map<String, Object> deleteRole(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int flag = 0;// 删除失败
		try {
			if (!StringUtil.isEmpty(ids)){
				int result=roleService.delRoleAndRela(ids);
				flag = result;// 1 表示删除成功
			}
		} catch (Exception e) {
			log.info("删除角色异常："+ e);
			flag = 0;
		}
		resultMap.put("flag", flag);
		return resultMap;
	}
	
	/**
	 * 初始进入修改角色
	 * 
	 * @return
	 */
	@RequestMapping("/toUpdRole")
	public ModelAndView toUpdRole(String roleId) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/role/updRole.jsp");
		Role role=new Role();
		//1、根据roleId查找角色信息
		Integer roleIdInt = Integer.valueOf(roleId);
		role=roleService.selectByPrimaryKey(roleIdInt);
		mv.addObject("role", role);
		
		//2、根据roldId查找角色及菜单关联关系
		List<Menu> list=menuService.selectMenuByRoleId(roleIdInt); 
		String menuId="";//初始化菜单标识
		String menuName="";//初始化菜单名称
		String menuPid="";//初始化菜单父标识
		for(int i=0;i<list.size();i++){
			Menu menu=list.get(i);
			if(i!=list.size()-1){
        		menuName += menu.getMenuName()+ "," ;
            	menuId +=menu.getMenuId() + ",";
            	menuPid +=menu.getMenuPid() + ",";
        	}else{
        		menuName +=  menu.getMenuName() ;
            	menuId +=menu.getMenuId();
            	menuPid +=menu.getMenuPid();
        	}
		}
		mv.addObject("menuId", menuId);
		mv.addObject("menuName", menuName);
		mv.addObject("menuPid", menuPid);
		return mv;
	}
	
	/**
	 * 修改角色
	 * 
	 * @return
	 */
	@RequestMapping("/updRole")
	public ModelAndView updRole(Role role,String menuId,String menuPid) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/role/updRole.jsp");
		User user=(User)session.getAttribute("user");
		try {
			//将父结点的菜单权限追加授予
			TreeSet<String> menuSet=new TreeSet<String> ();

			String []menuPidArray=menuPid.split(",");
			for(String oneMenuPid:menuPidArray){
				if(!oneMenuPid.equals("0")){
					menuSet.add(oneMenuPid);
				}
			}
			
			String []menuIdArray=menuId.split(",");
			for(String oneMenuId:menuIdArray){
				menuSet.add(oneMenuId);
			}
		
			String menuIds="";
			for(String oneMenuId:menuSet){
				menuIds+=oneMenuId+",";
			}
			
			/*System.out.println("######################");
			System.out.println("menuId:"+menuId);
			System.out.println("menuPid:"+menuPid);
			System.out.println("合并后的menuId:"+menuIds);*/
			
			role.setUserId(user.getUserId());
			int result=roleService.updRole(role,menuIds);
			if(result>0){
				mv.addObject("message", "success");
				mv.addObject("info", "修改角色成功。");
			}else{
				mv.addObject("message", "error");
				mv.addObject("info", "修改角色失败。");
			}
		} catch (Exception e) {
			mv.addObject("message", "error");
			mv.addObject("info", "修改角色异常。");
			log.info("修改角色异常："+e);
		}
		
		return mv;
	}

	
}
