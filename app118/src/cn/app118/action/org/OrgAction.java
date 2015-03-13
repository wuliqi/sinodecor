/**
 * @(#)UserAction.java	09/09/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-09
 */
package cn.app118.action.org;

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
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.Org;
import cn.app118.model.User;
import cn.app118.service.code.ICodeService;
import cn.app118.service.org.IOrgService;

/**
 * 组织机构控制类
 * 
 * @author wRitchie
 * 
 */
@Controller
@RequestMapping("orgAction")
public class OrgAction extends BaseAction {

	@Resource
	private IOrgService orgService;
	
	@Resource
	private ICodeService codeService;// 代码管理服务类

	// *****************************//
	/**
	 * 进入消息列表页面
	 * 
	 * @author 吴理琪
	 */
	@RequestMapping("/initOrg")
	public ModelAndView initOrg() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/org/listOrg.jsp");
		return mv;
	}

	/**
	 * 用户列表页面异步请求
	 * 
	 * @author writchie
	 */
	@RequestMapping("listOrgByPager")
	@ResponseBody
	public Map<String, Object> listOrgByPager(String curNo, String curSize,String sortname,String sortorder,
			String orgName, String fromCreateTime, String toCreateTime) {
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
		if (!StringUtil.isEmpty(orgName)) {
			map.put("orgName", orgName);
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
			if ("orgName".equals(sortname)) {
				orderbyStr = "order by orgName " + sortorder;
			} else if ("orgNo".equals(sortname)) {
				orderbyStr = "order by orgNo " + sortorder;
			} else if ("remark".equals(sortname)) {
				orderbyStr = "order by remark " + sortorder;
			} else if ("createTime".equals(sortname)) {
				orderbyStr = "order by create_time " + sortorder;
			}else if ("orgTypeCn".equals(sortname)) {
				orderbyStr = "order by orgTypeCn " + sortorder;
			}else if ("mobile".equals(sortname)) {
				orderbyStr = "order by mobile " + sortorder;
			}else if ("abbr".equals(sortname)) {
				orderbyStr = "order by abbr " + sortorder;
			}
		} else {
			orderbyStr = " order by org_id desc ";
		}
		map.put("orderBy", orderbyStr);
		
		List<Map> list = new ArrayList<Map>();
		list = orgService.selectByPager(map);
		for (Map oneMap : list) {
			Date d = (Date) oneMap.get("createTime");
			oneMap.put("createTime", DateUtil.getFormatDate(d, ""));// 创建时间

		}
		int allSize = orgService.selectByPagerCount(map);

		jsonMap.put("fromCreateTime", fromCreateTime);
		jsonMap.put("toCreateTime", toCreateTime);
		jsonMap.put("orgName", orgName);

		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;

	}

	/**
	 * 初始进入增加组织机构面页
	 * 
	 * @return
	 */
	@RequestMapping("/toAddOrg")
	public ModelAndView toAddOrg() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/org/addOrg.jsp");
		Map<String, String> orgMap = new TreeMap<String, String>();
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		mv.addObject("orgMap", orgMap);
		Map param = new HashMap();
		param.put("type", "12");//商家品牌
		List<Map> codeList=codeService.selectBySelective(param);
		mv.addObject("codeList", codeList);
	
		return mv;
	}

	/**
	 * 增加组织机构
	 * 
	 * @return
	 */
	@RequestMapping("/addOrg")
	public ModelAndView addOrg(Org org) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/org/addOrg.jsp");
		String flag = "error";
		try {
			User user = (User) session.getAttribute("user");
			Integer userId = user.getUserId();
			Date createTime = new Date();
			org.setUserId(userId);
			org.setCreateTime(createTime);
			org.setSysType("01");
			org.setIsactive("1");
			org.setSortNo(1);
			int result = orgService.insertOrg(org);
			if (result > 0) {
				flag = "success";
			}
		} catch (Exception e) {
			log.info("增加组织机构失败：" + e);
		}
		mv.addObject("message", flag);
		return mv;
	}

	/**
	 * 删除用户
	 * 
	 */
	@RequestMapping("/delOrg")
	@ResponseBody
	public Map<String, Object> delOrg(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int flag = 0;// 删除失败
		try {
			if (!StringUtil.isEmpty(ids)) {
				int result = orgService.delOrg(ids);
				flag = result;// 1 表示删除成功
			}
		} catch (Exception e) {
			log.info("删除用户异常：" + e);
			flag = 0;
		}
		resultMap.put("flag", flag);
		return resultMap;
	}

	/**
	 * 初始进入修改组织机构面页
	 * 
	 * @return
	 */
	@RequestMapping("/toUpdOrg")
	public ModelAndView toUpdOrg(String id) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/org/updOrg.jsp");
		Map<String, String> orgMap = new TreeMap<String, String>();
		List<Org> orgList = orgService.selectBySelective(new Org());
		Org existOrg = orgService.findOrgById(Integer.valueOf(id));
		Integer orgId = existOrg.getOrgId();
		
		for (Org org : orgList) {
			if(orgId!=org.getOrgId()){
				orgMap.put(org.getOrgId() + "", org.getOrgName());
			}
		}
		mv.addObject("orgMap", orgMap);
		mv.addObject("org", existOrg);
		Map param = new HashMap();
		param.put("type", "12");//商家品牌
		List<Map> codeList=codeService.selectBySelective(param);
		mv.addObject("codeList", codeList);
		return mv;
	}

	/**
	 * 修改组织机构
	 * 
	 * @param org
	 * @return
	 */
	@RequestMapping("/updOrg")
	public ModelAndView updOrg(Org org) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/org/updOrg.jsp");
		String flag = "error";
		try {
			User user = (User) session.getAttribute("user");
			Integer userId = user.getUserId();
			Date createTime = new Date();
			org.setUserId(userId);
			org.setCreateTime(createTime);
			// org.setSysType("01");
			// org.setIsactive("1");
			// org.setSortNo(1);
			int result = orgService.updateOrg(org);
			if (result > 0) {
				flag = "success";
			}
		} catch (Exception e) {
			log.info("修改组织机构失败：" + e);
		}
		mv.addObject("message", flag);
		return mv;
	}
}
