/**
 * @(#)WWebAction.java	07/13/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-13
 */
package cn.app.action.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.StringUtil;
import cn.app118.service.web.IWebService;

/**
 * 友情链接控制类 网站
 * 
 * @author wRitchie
 *
 */
@Controller
@RequestMapping("wWebAction")
public class WWebAction {
	@Resource
	private IWebService webService;

	
	/**
	 * 友情链接网址分页列表页面
	 * 
	 * @author 吴理琪
	 */
	@RequestMapping("listWebByPager")
	public ModelAndView listWebByPager(String curNo, String curSize,String sortname,String sortorder,
			String webName,String webUrl,String fromCreateTime, String toCreateTime) {
		ModelAndView mv = new ModelAndView("/web/web/listWeb.jsp");
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
		if (!StringUtil.isEmpty(webName)) {//标题
			map.put("webName", webName);
		}
		if (!StringUtil.isEmpty(webUrl)) {//来源
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
			} else if ("createTime".equals(sortname)) {
				orderbyStr = "order by create_time " + sortorder;
			}
		} else {
			orderbyStr = " order by web_id desc ";
		}
		map.put("orderBy", orderbyStr);
		
		List<Map> list = new ArrayList<Map>();
		list = webService.selectByPager(map);
	/*	for (Map oneMap : list) {
			Date createTime = (Date) oneMap.get("createTime");
			if(createTime!=null){
				oneMap.put("createTime", DateUtil.getFormatDate(createTime, "yyyy-MM-dd"));// 创建时间
			}
		}*/
		int allSize = webService.selectByPagerCount(map);

		mv.addObject("curNo", curNo);
		mv.addObject("curSize", curSize);
		mv.addObject("list", list);
		mv.addObject("total", allSize);
		int totalPages = (allSize + max -1) / max;
		mv.addObject("totalPages", totalPages);
		mv.addObject("webName", webName);
		mv.addObject("webUrl", webUrl);
		return mv;
	}
	
	@RequestMapping("/initWeb")
	@ResponseBody
	public Map<String, Object> initWeb(String curNo, String curSize) {
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
		List<Map> list = new ArrayList<Map>();
		list = webService.selectByPager(map);
		/*for (Map oneMap : list) {
			Date createTime = (Date) oneMap.get("createTime");
			if(createTime!=null){
				oneMap.put("createTime", DateUtil.getFormatDate(createTime, "yyyy-MM-dd"));// 创建时间
			}
		}*/
		System.out.println("首页友情链接："+list.size());
		jsonMap.put("list", list);
		return jsonMap;
	}
}
