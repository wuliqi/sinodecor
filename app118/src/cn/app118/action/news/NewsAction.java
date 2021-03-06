/**
 * @(#)NewsAction.java	07/02/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-02
 */
package cn.app118.action.news;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.ButtonPermissionConstant;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.Code;
import cn.app118.model.News;
import cn.app118.model.Org;
import cn.app118.model.Role;
import cn.app118.model.User;
import cn.app118.service.code.ICodeService;
import cn.app118.service.news.INewsService;
import cn.app118.service.org.IOrgService;

/**
 * 新闻信息控制类 后台
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
		
		User user = (User) session.getAttribute("user");
		Integer orgId=user.getOrgId();
		mv.addObject("orgId", orgId);
		
		Map<String, String> orgMap = new TreeMap<String, String>();
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		mv.addObject("orgMap", orgMap);
		Map param = new HashMap();
		param.put("type", "20000");//分类
		List<Map> codeList=codeService.selectBySelective(param);
		mv.addObject("codeList", codeList);
		
		//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
		List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
		for (Role role : roleList) {
			if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId==ButtonPermissionConstant.ROOT_ORG_ID) {// 超级管理员
				break;
			} else{
				
				mv.addObject("disabled", "disabled=\"disabled\"");
			}
		}
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
			String newsTitle,String newsSource,String orgId,String newsCategory, String fromCreateTime, String toCreateTime) {
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
				oneMap.put("createTime", DateUtil.getFormatDate(createTime, ""));// 创建时间
			}
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
		try {
			User user = (User) session.getAttribute("user");
			Integer orgId=user.getOrgId();
			mv.addObject("orgId", orgId);
			
			Map<String, String> orgMap = new TreeMap<String, String>();
			List<Org> orgList = orgService.selectBySelective(new Org());
			for (Org org : orgList) {
				orgMap.put(org.getOrgId() + "", org.getOrgName());
			}
			mv.addObject("orgMap", orgMap);
			Date today=new Date();
			String beginTime=DateUtil.getFormatDate(today, "yyyy-MM-dd");
			mv.addObject("beginTime", beginTime);
			Date endTime=DateUtil.addDate(today, 30);
			mv.addObject("endTime", DateUtil.getFormatDate(endTime, "yyyy-MM-dd"));
			
			Map param = new HashMap();
			param.put("type", "20000");//内容分类
			List<Map> codeList=codeService.selectBySelective(param);
			mv.addObject("codeList", codeList);
			
			//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
			List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
			for (Role role : roleList) {
				if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId==ButtonPermissionConstant.ROOT_ORG_ID) {// 超级管理员
					break;
				} else{
					
					mv.addObject("disabled", "disabled=\"disabled\"");
				}
			}
		} catch (Exception e) {
			log.info("初始化内容异常："+e);
		}
		return mv;
	}
	
	@RequestMapping("/addNews")
	public ModelAndView addNews(HttpServletRequest request,News news) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/news/addNews.jsp");
		String flag = "error";
		User user=(User)session.getAttribute("user");
		Integer userId=user.getUserId();
		String content=news.getNewsContent().replaceAll("\'", "\"");//单引号全部替换从双引号
		news.setNewsContent(content);
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获取上传的文件
			MultipartFile multiFile = multipartRequest.getFile("file");

			if (multiFile.isEmpty()) {//文件为空
				
			}else{
				Integer fileSize = (int) multiFile.getSize() / 1024;
				/**
				 * 如果文件小于15M，则上传文件，否则提示用户不能超过15M
				 */
				if (fileSize > 1024 * 15) {
					flag="beyondFileSize";
					mv.addObject("message", flag);
					return mv;
				}else{
					// 获取文件后缀
					String[] suffixs = multiFile.getOriginalFilename().split("\\.");
					String suffix = "." + suffixs[suffixs.length - 1];
					// 获得文件全名
					String fname = userId + "_" + System.currentTimeMillis() + suffix;
					String path = request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"news";
					System.out.println("******path:"+path);
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
				}
			}
			news.setCreateTime(new Date());
			news.setClicks(0);
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
	
	
	/**
	 * 删除新闻内容
	 * 
	 */
	@RequestMapping("/delNews")
	@ResponseBody
	public Map<String, Object> delNews(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int flag = 0;// 删除失败
		try {
			if (!StringUtil.isEmpty(ids)) {
				String []idArray=ids.split(",");
				for(String newsId:idArray){
					int result = newsService.deleteByPrimaryKey(Integer.valueOf(newsId));
					flag = result;// 1 表示删除成功
				}
			}
		} catch (Exception e) {
			log.info("删除新闻内容异常：" + e);
			flag = 0;
		}
		resultMap.put("flag", flag);
		return resultMap;
	}

	/**
	 * 初始进入修改内容面页
	 * 
	 * @return
	 */
	@RequestMapping("/toUpdNews")
	public ModelAndView toUpdNews(String newsId) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/news/updNews.jsp");
		try {
			Map<String, String> orgMap = new TreeMap<String, String>();
			List<Org> orgList = orgService.selectBySelective(new Org());
			for (Org org : orgList) {
				orgMap.put(org.getOrgId() + "", org.getOrgName());
			}
			mv.addObject("orgMap", orgMap);
			
			Map param = new HashMap();
			param.put("type", "20000");//内容分类
			List<Map> codeList=codeService.selectBySelective(param);
			mv.addObject("codeList", codeList);
			
			News news=newsService.selectByPrimaryKey(Integer.valueOf(newsId));
			String content=news.getNewsContent().replaceAll("\'", "\"");
			news.setNewsContent(content);
			
			mv.addObject("beginTime", DateUtil.getFormatDate(news.getBeginTime(), "yyyy-MM-dd"));
			mv.addObject("endTime", DateUtil.getFormatDate(news.getEndTime(), "yyyy-MM-dd"));
			mv.addObject("news", news);
			
			User user = (User) session.getAttribute("user");
			Integer orgId=user.getOrgId();
			//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
			List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
			for (Role role : roleList) {
				if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId==ButtonPermissionConstant.ROOT_ORG_ID) {// 超级管理员
					break;
				} else{
					
					mv.addObject("disabled", "disabled=\"disabled\"");
				}
			}
		} catch (Exception e) {
			log.info("进入修改内容页面异常："+e);
		}
		return mv;
	}
	
	@RequestMapping("/updNews")
	public ModelAndView updNews(HttpServletRequest request,News news) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/news/updNews.jsp");
		String flag = "error";
		User user=(User)session.getAttribute("user");
		Integer userId=user.getUserId();
		
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获取上传的文件
			MultipartFile multiFile = multipartRequest.getFile("file");

			if (multiFile.isEmpty()) {//文件为空
				log.info("上传文件为空。");
			}else{
				Integer fileSize = (int) multiFile.getSize() / 1024;
				/**
				 * 如果文件小于15M，则上传文件，否则提示用户不能超过15M
				 */
				if (fileSize > 1024 * 15) {
					flag="beyondFileSize";
					mv.addObject("message", flag);
					return mv;
				}else{
					// 获取文件后缀
					String[] suffixs = multiFile.getOriginalFilename().split("\\.");
					String suffix = "." + suffixs[suffixs.length - 1];
					// 获得文件全名
					String fname = userId + "_" + System.currentTimeMillis() + suffix;
					String path = request.getSession().getServletContext().getRealPath("/")+File.separator+"upload"+File.separator+"news";
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
				}
			}
			
			int result=newsService.updateByPrimaryKeySelective(news);
			if(result>0){
				flag="success";
			}
			
		} catch (Exception e) {
			log.info("修改内容异常："+e);
		}
		mv.addObject("message", flag);
		return mv;
	}
	
	/**
	 * 进入缩略图预览页面
	 * @param newsThumbnail
	 * @return
	 */
	@RequestMapping("/preViewImg")
	public ModelAndView preViewImg(String newsThumbnail) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/news/viewImg.jsp");
		mv.addObject("newsThumbnail", newsThumbnail);
		return mv;
	}
	
	
	@RequestMapping("/viewNews")
	public ModelAndView viewNews(String newsId) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/news/viewNews.jsp");
		News news=newsService.selectByPrimaryKey(Integer.valueOf(newsId));
		
		
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			if(org.getOrgId().equals(news.getOrgId())){
				mv.addObject("orgName", org.getOrgName());
				break;
			}
		}
		
		
		String newsCategory = news.getNewsCategory();
		if(!StringUtil.isEmpty(newsCategory)){
			Code code=codeService.selectByPrimaryKey(Integer.valueOf(newsCategory));
			mv.addObject("codeName", code.getCodeName());
		}
		
		
		mv.addObject("beginTime", DateUtil.getFormatDate(news.getBeginTime(), "yyyy-MM-dd"));
		mv.addObject("endTime", DateUtil.getFormatDate(news.getEndTime(), "yyyy-MM-dd"));
		mv.addObject("news", news);
		return mv;
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
