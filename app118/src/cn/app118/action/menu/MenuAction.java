/**
 * @(#)RoleAction.java	08/29/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-08-29
 */
package cn.app118.action.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.model.Menu;
import cn.app118.service.menu.IMenuService;

/**
 * 菜单管理控制类
 * 
 * @author wRitchie
 *
 */
@Controller
@RequestMapping("menuAction")
public class MenuAction extends BaseAction {
	
	
	@Resource
	private IMenuService menuService;//菜单服务类
	/**
	 * 进入菜单列表初始页
	 * 
	 * @return
	 */
	@RequestMapping("initMenu")
	public ModelAndView initMenu(){
		ModelAndView mv=new ModelAndView("/pages/menu/listMenu.jsp");
		//List<Menu> menuList=menuService.selectBySelective(new Menu());
		//mv.addObject("menuList", menuList);
		return mv;
	}
	
	
	@RequestMapping("listMenu")
	@ResponseBody
	public Map<String,Object> listMenu(){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Menu> menuList=menuService.selectBySelective(new Menu());
		/*for(Menu m:menuList){
			System.out.println("id:"+m.getMenuId()+" pId:"+m.getMenuPid()+" menuName:"+m.getMenuName());
		}*/
		if(menuList.size()>0){
			jsonMap.put("flag", 1);
			jsonMap.put("menuList", menuList);
		}
		return jsonMap;
	}

}
