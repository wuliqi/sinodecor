/**
 * @(#)NewsAction.java	07/02/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-02
 */
package cn.app118.action.news;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.News;
import cn.app118.model.Org;
import cn.app118.model.User;
import cn.app118.service.code.ICodeService;
import cn.app118.service.news.INewsService;
import cn.app118.service.org.IOrgService;

/**
 * 新闻信息控制类
 * 
 * @author 吴理琪
 * 
 */
@Controller
@RequestMapping("newsAction")
public class NewsAction extends BaseAction {
	
	@Resource
	private INewsService newsService;//内容管理服务类
	
	@Resource
	private IOrgService orgService;//组织机构管理服务类
	
	@Resource
	private ICodeService codeService;// 代码管理服务类
	
	/**
	 * 进入内容列表页面
	 * 
	 * @author 吴理琪
	 */
	@RequestMapping("/initNews")
	public ModelAndView initNews() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/news/listNews.jsp");
		return mv;
	}

	/**
	 * 内容列表页面异步请求
	 * 
	 * @author 吴理琪
	 */
	@RequestMapping("listNewsByPager")
	@ResponseBody
	public Map<String, Object> listNewsByPager(String curNo, String curSize,String sortname,String sortorder,
			String newsTitle, String fromCreateTime, String toCreateTime) {
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
		if (!StringUtil.isEmpty(newsTitle)) {
			map.put("newsTitle", newsTitle);
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
				orderbyStr = "order by newsTitle " + sortorder;
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
			orderbyStr = " order by news_id desc ";
		}
		map.put("orderBy", orderbyStr);
		
		List<Map> list = new ArrayList<Map>();
		list = newsService.selectByPager(map);
		for (Map oneMap : list) {
			Date d = (Date) oneMap.get("createTime");
			oneMap.put("createTime", DateUtil.getFormatDate(d, ""));// 创建时间

		}
		int allSize = newsService.selectByPagerCount(map);

		jsonMap.put("fromCreateTime", fromCreateTime);
		jsonMap.put("toCreateTime", toCreateTime);
		jsonMap.put("newsTitle", newsTitle);

		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;

	}
	
	
	@RequestMapping("/toAddNews")
	public ModelAndView toAddNews() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/news/addNews.jsp");
		
		Map<String, String> orgMap = new TreeMap<String, String>();
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		mv.addObject("orgMap", orgMap);
		String beginTime=DateUtil.getFormatDate(new Date(), "yyyy-MM-dd HH:mm");
		mv.addObject("beginTime", beginTime);
		Map param = new HashMap();
		param.put("type", "20000");//内容分类
		List<Map> codeList=codeService.selectBySelective(param);
		mv.addObject("codeList", codeList);
		return mv;
	}
	
	@RequestMapping("/addNews")
	public ModelAndView addNews(HttpServletRequest request,News news) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/news/addNews.jsp");
		String flag = "error";
		User user=(User)session.getAttribute("user");
		Integer userId=user.getUserId();
		
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获取上传的文件
			MultipartFile multiFile = multipartRequest.getFile("file");

			if (multiFile.isEmpty()) {
				flag="empty";
				mv.addObject("message", flag);
				return mv;
			}
			Integer fileSize = (int) multiFile.getSize() / 1024;
			/**
			 * 如果文件小于15M，则上传文件，否则提示用户不能超过15M
			 */
			if (fileSize > 1024 * 15) {
				flag="beyondFileSize";
				mv.addObject("message", flag);
				return mv;
			}
			// 获取文件后缀
			String[] suffixs = multiFile.getOriginalFilename().split("\\.");
			String suffix = "." + suffixs[suffixs.length - 1];
			// 获得文件全名
			String fname = userId + "_" + System.currentTimeMillis() + suffix;
			String path = request.getSession().getServletContext().getRealPath("upload");
			// 创建文件存放路径
			File folder = new File(path);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			File file = new File(path + File.separator + fname);
			log.info("######path:" + file.getAbsolutePath());
			// 如果文件不存在写文件到服务器
			multiFile.transferTo(file);
			news.setNewsThumbnail(fname);
			int result=newsService.insert(news);
			if(result>0){
				flag="success";
			}
		} catch (Exception e) {
			log.info("增加内容异常："+e);
		}
		
		mv.addObject("message", flag);
		return mv;
	}

}
