/**
 * @(#)FeedbackAction.java	07/15/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-15
 */
package cn.app118.action.feedback;

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
import cn.app118.service.feedback.IFeedbackService;

/**
 * 反馈留言控制类 后台
 * 
 * @author wRitchie
 *
 */
@Controller
@RequestMapping("feedbackAction")
public class FeedbackAction extends BaseAction {
	
	@Resource
	private IFeedbackService feedbackService;
	
	/**
	 * 进入反馈留言列表页面
	 * 
	 * @author 吴理琪
	 */
	@RequestMapping("/initFeedback")
	public ModelAndView initFeedback() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/feedback/listFeedback.jsp");
		return mv;
	}

	/**
	 * 反馈留言列表页面异步请求
	 * 
	 * @author 吴理琪
	 */
	@RequestMapping("listFeedbackByPager")
	@ResponseBody
	public Map<String, Object> listFeedbackByPager(String curNo, String curSize,String sortname,String sortorder,
			String phoneNumber,String category,String fromCreateTime, String toCreateTime) {
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
		if (!StringUtil.isEmpty(phoneNumber)) {//手机号码
			map.put("phoneNumber", phoneNumber);
		}
		
		if (!StringUtil.isEmpty(category)) {//分类
			map.put("category", category);
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
			if ("phoneNumber".equals(sortname)) {
				orderbyStr = "order by phone_number " + sortorder;
			}  else if ("createTime".equals(sortname)) {
				orderbyStr = "order by create_time " + sortorder;
			}else if ("category".equals(sortname)) {
				orderbyStr = "order by category " + sortorder;
			}
		} else {
			orderbyStr = " order by fd_id desc ";
		}
		map.put("orderBy", orderbyStr);
		
		List<Map> list = new ArrayList<Map>();
		list = feedbackService.selectByPager(map);
		for (Map oneMap : list) {
			Date createTime = (Date) oneMap.get("createTime");
			if(createTime!=null){
				oneMap.put("createTime", DateUtil.getFormatDate(createTime, ""));// 创建时间
			}
			String categoryTmp=oneMap.get("category")+"";
			if("1".equals(categoryTmp)){
				oneMap.put("categoryCn","在线反馈");
			}else if("3".equals(categoryTmp)){
				oneMap.put("categoryCn","在线留言");
			}
			
		}
		int allSize = feedbackService.selectByPagerCount(map);

		jsonMap.put("fromCreateTime", fromCreateTime);
		jsonMap.put("toCreateTime", toCreateTime);
		jsonMap.put("phoneNumber", phoneNumber);
		jsonMap.put("category", category);

		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;
	}
	
	/**
	 * 删除反馈留言
	 * 
	 */
	@RequestMapping("/delFeedback")
	@ResponseBody
	public Map<String, Object> delFeedback(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int flag = 0;// 删除失败
		try {
			if (!StringUtil.isEmpty(ids)) {
				String []idArray=ids.split(",");
				for(String newsId:idArray){
					int result = feedbackService.deleteByPrimaryKey(Integer.valueOf(newsId));
					flag = result;// 1 表示删除成功
				}
			}
		} catch (Exception e) {
			log.info("删除反馈留言异常：" + e);
			flag = 0;
		}
		resultMap.put("flag", flag);
		return resultMap;
	}
}
