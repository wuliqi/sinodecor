/**
 * @(#)ContentAction.java	07/08/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-08
 */
package cn.app118.action.news;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.News;
import cn.app118.service.news.INewsService;

@Controller
@RequestMapping("contentAction")
public class ContentAction extends BaseAction {

	@Resource
	private INewsService newsService;//内容管理服务类
	
	
	/**
	 * 内容列表前台网站分页列表页面
	 * 
	 * @author 吴理琪
	 */
	@RequestMapping("listContentByPager")
	public ModelAndView listContentByPager(String curNo, String curSize,String sortname,String sortorder,
			String newsTitle,String newsSource,String orgId,String newsCategory,String newsCategoryCn, String fromCreateTime, String toCreateTime) {
		ModelAndView mv = new ModelAndView("/pages/web/listContent.jsp");
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
		if (!StringUtil.isEmpty(newsTitle)) {//标题
			map.put("newsTitle", newsTitle);
		}
		if (!StringUtil.isEmpty(newsSource)) {//来源
			map.put("newsSource", newsSource);
		}
		if (!StringUtil.isEmpty(orgId)) {//所属组织机构
			map.put("orgId", orgId);
		}
		if (!StringUtil.isEmpty(newsCategory)) {//分类
			map.put("newsCategory", newsCategory);
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
			if ("newsTitle".equals(sortname)) {
				orderbyStr = "order by news_title " + sortorder;
			} else if ("orgName".equals(sortname)) {
				orderbyStr = "order by orgName " + sortorder;
			} else if ("newsSource".equals(sortname)) {
				orderbyStr = "order by news_source " + sortorder;
			} else if ("createTime".equals(sortname)) {
				orderbyStr = "order by create_time " + sortorder;
			}else if ("newsCategory".equals(sortname)) {
				orderbyStr = "order by news_category " + sortorder;
			}
		} else {
			orderbyStr = " order by news_id desc ";
		}
		map.put("orderBy", orderbyStr);
		
		List<Map> list = new ArrayList<Map>();
		list = newsService.selectByPager(map);
		for (Map oneMap : list) {
			Date createTime = (Date) oneMap.get("createTime");
			if(createTime!=null){
				oneMap.put("createTime", DateUtil.getFormatDate(createTime, "yyyy-MM-dd"));// 创建时间
				
				Date currentTime=new Date();
				String hotImgStyle="style=\"visibility:hidden;\"";
				int result=DateUtil.compare(createTime, DateUtil.subtractDate(currentTime,SystemConstant.INACTIVE_INTERVAL));
				if(result>=0){//近3天的，显示热点图标
					hotImgStyle="style=\"visibility: visible; \"";
				}else{
					hotImgStyle="style=\"visibility:hidden;\"";
				}
				oneMap.put("hotImgStyle", hotImgStyle);
			}
		}
		int allSize = newsService.selectByPagerCount(map);

		mv.addObject("curNo", curNo);
		mv.addObject("curSize", curSize);
		mv.addObject("list", list);
		mv.addObject("total", allSize);
		int totalPages = (allSize + max -1) / max;
		mv.addObject("totalPages", totalPages);
		
		mv.addObject("newsCategory", newsCategory);
		mv.addObject("newsCategoryCn", newsCategoryCn);
		return mv;
	}
	
	/**
	 * 查看内容详情
	 * @param newsId
	 * @return
	 */
	@RequestMapping("viewContent")
	public ModelAndView viewContent(String newsId,String newsCategory,String newsCategoryCn){
		ModelAndView mv = new ModelAndView("/pages/web/viewContent.jsp");
		try {
			Integer newsIdInt = Integer.valueOf(newsId);
			News news=newsService.selectByPrimaryKey(newsIdInt);
			if(news!=null){
				Integer clicks=news.getClicks();
				News updNews=new News();
				updNews.setNewsId(newsIdInt);
				updNews.setClicks(clicks+1);
				newsService.updateByPrimaryKeySelective(updNews);
				mv.addObject("createTime",DateUtil.getFormatDate(news.getCreateTime(), ""));
				mv.addObject("news", news);
				
				Map preMap=new HashMap();
				preMap.put("newsId", newsIdInt);
				preMap.put("pre","pre");
				News preNews=newsService.selectPreOrNextNews(preMap);
				String pre="没有了";
				if(preNews!=null){
					pre="<a href='/app118/contentAction/viewContent?newsId="+preNews.getNewsId()+"' >"+preNews.getNewsTitle()+"</a>";
				}
				
				Map nextMap=new HashMap();
				nextMap.put("newsId", newsIdInt);
				nextMap.put("next","next");
				News nextNews=newsService.selectPreOrNextNews(nextMap);
				String next="没有了";
				if(nextNews!=null){
					next="<a href='/app118/contentAction/viewContent?newsId="+nextNews.getNewsId()+"' >"+nextNews.getNewsTitle()+"</a>";
				}
				mv.addObject("pre", pre);
				mv.addObject("next", next);
			}
		} catch (Exception e) {
			log.info("查看内容详情异常："+e);
		}
		mv.addObject("newsCategory", newsCategory);
		mv.addObject("newsCategoryCn", newsCategoryCn);
		return mv;
	}
	
	@RequestMapping("viewContentBody")
	public ModelAndView viewContentBody(String newsId,String newsCategory,String newsCategoryCn){
		ModelAndView mv = new ModelAndView("/pages/web/viewContentBody.jsp");
		try {
			Integer newsIdInt = Integer.valueOf(newsId);
			News news=newsService.selectByPrimaryKey(newsIdInt);
			if(news!=null){
				Integer clicks=news.getClicks();
				News updNews=new News();
				updNews.setNewsId(newsIdInt);
				updNews.setClicks(clicks+1);
				newsService.updateByPrimaryKeySelective(updNews);
				mv.addObject("news", news);
			}
			mv.addObject("newsCategory", newsCategory);
			mv.addObject("newsCategoryCn", newsCategoryCn);
		} catch (Exception e) {
			log.info("查看纯内容详情异常："+e);
		}
		return mv;
	}
}
