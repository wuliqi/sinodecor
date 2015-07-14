/**
 * @(#)ContentAction.java	07/08/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-08
 */
package cn.app.action.news;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.News;
import cn.app118.service.news.INewsService;
/**
 * 内容信息控制类 前台网站
 * @author wRitchie
 *
 */
@Controller
@RequestMapping("wContentAction")
public class WContentAction extends BaseAction {

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
		ModelAndView mv = new ModelAndView("/web/content/listContent.jsp");
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
		ModelAndView mv = new ModelAndView("/web/content/viewContent.jsp");
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
				String sameNewsCategory=news.getNewsCategory();
				Map preMap=new HashMap();
				preMap.put("newsId", newsIdInt);
				preMap.put("newsCategory", sameNewsCategory);
				preMap.put("pre","pre");
				News preNews=newsService.selectPreOrNextNews(preMap);
				String pre="没有了";
				if(preNews!=null){
					pre="<a href='/app/wContentAction/viewContent?newsId="+preNews.getNewsId()+"' >"+preNews.getNewsTitle()+"</a>";
				}
				
				Map nextMap=new HashMap();
				nextMap.put("newsId", newsIdInt);
				nextMap.put("newsCategory", sameNewsCategory);
				nextMap.put("next","next");
				News nextNews=newsService.selectPreOrNextNews(nextMap);
				String next="没有了";
				if(nextNews!=null){
					next="<a href='/app/wContentAction/viewContent?newsId="+nextNews.getNewsId()+"' >"+nextNews.getNewsTitle()+"</a>";
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
	
	/**
	 * 联系我们，关于我们的详细页面
	 * @param newsId
	 * @param newsCategory
	 * @param newsCategoryCn
	 * @return
	 */
	@RequestMapping("viewContentBody")
	public ModelAndView viewContentBody(String newsId,String newsCategory,String newsCategoryCn){
		ModelAndView mv = new ModelAndView("/web/content/viewContentBody.jsp");
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
	
	/**
	 * 首页关于我们初始化
	 * @param newsId
	 * @return
	 */
	@RequestMapping("/initAbout")
	@ResponseBody
	public Map<String, Object> initAbout(String newsId) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Integer newsIdInt = Integer.valueOf(newsId);
		News news=newsService.selectByPrimaryKey(newsIdInt);
		jsonMap.put("news", news);
		return jsonMap;
	}
	
	/**
	 * 通用内容查询
	 * @param curNo 当前页码
	 * @param curSize 页面大小 默认10条
	 * @param newsCategory 内容分类
	 * @param newsCategoryCn 内容分类名称
	 * @return
	 */
	@RequestMapping("/initNews")
	@ResponseBody
	public Map<String, Object> initNews(String curNo, String curSize,String newsCategory,String newsCategoryCn) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		/************* 分页处理 ****************/
		int skip;
		int max;
		if (curNo == null || "".equals(curNo))
			curNo = "0";
		if (curSize == null || "".equals(curSize))
			curSize = "10";
		skip = Integer.parseInt(curNo);
		max = Integer.parseInt(curSize);
		int start = (skip - 1) * max;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("len", max);
		/************* 分页处理 ****************/
		
		if (!StringUtil.isEmpty(newsCategory)) {//分类
			map.put("newsCategory", newsCategory);
		}
		
		//排序
		String orderbyStr = " order by news_id desc ";
		map.put("orderBy", orderbyStr);
		
		List<Map> list = new ArrayList<Map>();
		list = newsService.selectByPager(map);
		for (Map oneMap : list) {
			Date createTime = (Date) oneMap.get("createTime");
			if(createTime!=null){
				oneMap.put("createTime", DateUtil.getFormatDate(createTime, "yyyy-MM-dd"));// 创建时间
			}
		}
		//int allSize = newsService.selectByPagerCount(map);
		jsonMap.put("list", list);
		return jsonMap;
	}
	
	@RequestMapping("showImage")
	@ResponseBody
	/**
	 * 显示磁盘上的图片
	 * @param response
	 * @param newsThumbnail 缩略图名称
	 */
	public void showImage(HttpServletResponse response,String newsThumbnail) {
		response.setContentType("image/*");
		FileInputStream fis = null;
		OutputStream os = null;
		//服务器所在的路径
		String path = request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"news"+File.separator+newsThumbnail;
		System.out.println("****path:"+path);
		File file=new File(path);
		if(file.exists()){//存在
			try {
					fis = new FileInputStream(path);
					os = response.getOutputStream();
					int count = 0;
					byte[] buffer = new byte[1024 * 8];
					while ((count = fis.read(buffer)) != -1) {
						os.write(buffer, 0, count);
						os.flush();
					}
				
			} catch (Exception e) {
				log.info("显示图片异常："+e);
			} finally {
				try {
					fis.close();
					os.close();
				} catch (IOException e) {
					log.info("显示图片finally中异常："+e);
				}
			}
		}
	}
}
