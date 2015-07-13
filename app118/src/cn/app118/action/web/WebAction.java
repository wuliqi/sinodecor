/**
 * @(#)WebAction.java	07/13/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-13
 */
package cn.app118.action.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.User;
import cn.app118.model.Web;
import cn.app118.service.web.IWebService;

/**
 * 友情链接控制类
 * 
 * @author wRitchie
 *
 */
@Controller
@RequestMapping("webAction")
public class WebAction extends BaseAction {
	
	@Resource
	private IWebService webService;

	/**
	 * 进入友情链接列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/initWeb")
	public ModelAndView initWeb() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/web/listWeb.jsp");
		return mv;
	}
	
	/**
	 * 友情链接列表页面异步请求
	 * 
	 * @author 吴理琪
	 */
	@RequestMapping("listWebByPager")
	@ResponseBody
	public Map<String, Object> listWebByPager(String curNo, String curSize,String sortname,String sortorder,
			String webName,String webUrl,String fromCreateTime, String toCreateTime) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		/************* 分页处理 ****************/
		int skip;
		int max;
		if (curNo == null || "".equals(curNo))
			curNo = "0";
		if (curSize == null || "".equals(curSize))
			curSize = SystemConstant.PAGER_CURSIZE;
		skip = Integer.parseInt(curNo);
		max = Integer.parseInt(curSize);
		int start = (skip - 1) * max;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("len", max);
		/************* 分页处理 ****************/
		if (!StringUtil.isEmpty(webName)) {//网址名称
			map.put("webName", webName);
		}
		if (!StringUtil.isEmpty(webUrl)) {//网址
			map.put("webUrl", webUrl);
		}
		
		if (!StringUtil.isEmpty(fromCreateTime)) {
			map.put("fromCreateTime", fromCreateTime);
		}
		if (!StringUtil.isEmpty(toCreateTime)) {
			map.put("toCreateTime", toCreateTime);
		}
		//排序
		String orderbyStr = null;
		if (!StringUtil.isEmpty(sortname)) {
			if ("webName".equals(sortname)) {
				orderbyStr = "order by webName " + sortorder;
			} else if ("webUrl".equals(sortname)) {
				orderbyStr = "order by webUrl " + sortorder;
			}else if ("createTime".equals(sortname)) {
				orderbyStr = "order by create_time " + sortorder;
			}
		} else {
			orderbyStr = " order by web_id desc ";
		}
		map.put("orderBy", orderbyStr);
		
		List<Map> list = new ArrayList<Map>();
		list = webService.selectByPager(map);
		for (Map oneMap : list) {
			Date createTime = (Date) oneMap.get("createTime");
			if(createTime!=null){
				oneMap.put("createTime", DateUtil.getFormatDate(createTime, ""));// 创建时间
			}
		}
		int allSize = webService.selectByPagerCount(map);

		jsonMap.put("fromCreateTime", fromCreateTime);
		jsonMap.put("toCreateTime", toCreateTime);
		jsonMap.put("webName", webName);
		jsonMap.put("webUrl", webUrl);

		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;
	}
	
	/**
	 * 增加友情链接网址
	 * @return
	 */
	@RequestMapping("/toAddWeb")
	public ModelAndView toAddWeb() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/web/addWeb.jsp");
		return mv;
	}
	
	/**
	 * 保存友情链接网址
	 * @param web
	 * @return
	 */
	@RequestMapping("/addWeb")
	public ModelAndView addWeb(Web web) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/web/addWeb.jsp");
		String flag = "error";
		try {
			User user=(User)session.getAttribute("user");
			Integer userId=user.getUserId();
			web.setUserId(userId);
			web.setCreateTime(new Date());
			int result=webService.insert(web);
			if(result>0){
				flag="success";
			}
		} catch (Exception e) {
			log.info("增加网址异常："+e);
		}
		mv.addObject("message", flag);
		return mv;
	}
	
	
	/**
	 * 删除友情链接网址
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delWeb")
	@ResponseBody
	public Map<String, Object> delWeb(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int flag = 0;// 删除失败
		try {
			if (!StringUtil.isEmpty(ids)) {
				String []idArray=ids.split(",");
				for(String webId:idArray){
					int result = webService.deleteByPrimaryKey(Integer.valueOf(webId));
					flag = result;// 1 表示删除成功
				}
			}
		} catch (Exception e) {
			log.info("删除网址异常：" + e);
			flag = 0;
		}
		resultMap.put("flag", flag);
		return resultMap;
	}
	
	/**
	 * 进入更新友情链接网址页面
	 * @param webId
	 * @return
	 */
	@RequestMapping("/toUpdWeb")
	public ModelAndView toUpdWeb(String webId) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/web/updWeb.jsp");
		Web web=webService.selectByPrimaryKey(Integer.valueOf(webId));
		mv.addObject("web", web);
		return mv;
	}
	
	/**
	 * 更新保存友情链接网址
	 * @param web
	 * @return
	 */
	@RequestMapping("/updWeb")
	public ModelAndView updWeb(Web web) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/web/updWeb.jsp");
		String flag = "error";
		try {
			int result=webService.updateByPrimaryKeySelective(web);
			if(result>0){
				flag="success";
			}
		} catch (Exception e) {
			log.info("修改网站异常："+e);
		}
		mv.addObject("message", flag);
		return mv;
	}
}
