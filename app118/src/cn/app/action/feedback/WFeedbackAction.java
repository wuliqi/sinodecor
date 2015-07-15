/**
 * @(#)WFeedbackAction.java	07/14/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-14
 */
package cn.app.action.feedback;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.model.Feedback;
import cn.app118.service.feedback.IFeedbackService;

/**
 * 反馈留言控制类 网站
 * 
 * @author wRitchie
 *
 */
@Controller
@RequestMapping("wFeedbackAction")
public class WFeedbackAction extends BaseAction {
	
	@Resource
	private IFeedbackService feedbackService;
	
	/**
	 * 进入反馈或留言初始页面
	 * @param category
	 * @return
	 */
	@RequestMapping("/toAddFeedback")
	public ModelAndView toAddFeedback(String category) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/web/feedback/addFeedback.jsp");
		String contentTitle="内容";
		if(category.equals("1")){
			contentTitle="反馈";
		}else if(category.equals("3")){
			contentTitle="留言";
		}
		mv.addObject("category", category);
		mv.addObject("contentTitle", contentTitle);
		return mv;
	}
	
	/**
	 * 保存反馈或留言
	 * @param feedback
	 * @return
	 */
	@RequestMapping("/addFeedback")
	public ModelAndView addFeedback(Feedback feedback,String contentTitle,String category) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/web/feedback/addFeedback.jsp");
		String flag = "error";
		try {
			feedback.setCreateTime(new Date());
			int result=feedbackService.insert(feedback);
			if(result>0){
				flag="success";
			}
		} catch (Exception e) {
			log.info("反馈或留言异常："+e);
		}
		mv.addObject("category", category);
		mv.addObject("contentTitle", contentTitle);
		mv.addObject("message", flag);
		return mv;
	}

}
