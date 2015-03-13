/**
 * @(#)iSoftwareUpdAction.java	06/09/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-06-09
 */
package cn.app.action.softupd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.SystemConstant;


/**
 * app端软件更新管理控制类
 * 
 * @author 吴理琪
 * 
 */
@Controller
@RequestMapping("iSoftwareUpdAction")
public class ISoftwareUpdAction extends BaseAction{
	
	
	/**
	 * 获取软件最新版本  私家车版
	 * @param type :操作系统   1：Android 2:iOS
	 * @return 用户实体对象
	 */
	@RequestMapping("/getLastestVersion")
	@ResponseBody
	public Map<String, Object> getLastestVersion(String type) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if("1".equals(type)){//Android
			jsonMap.put("message", SystemConstant.MSG_SUCCESS);// 1表示成功
			jsonMap.put("versionCode", SystemConstant.ANDROID_SYSTEM_VERSION);// 最新版本号
			jsonMap.put("path", SystemConstant.ANDROID_DOWNLOAD_URL);// 最新版本路径,存放于第三方库中
			jsonMap.put("tips", "最新版本为:"+SystemConstant.ANDROID_SYSTEM_VERSION);
			jsonMap.put("forceUpdateFlag", SystemConstant.FORCE_UPDATE_FLAG);
		}else if("2".equals(type)){//iOS  appStore
			jsonMap.put("message", SystemConstant.MSG_SUCCESS);// 1表示成功
			jsonMap.put("versionCode", SystemConstant.IOS_SYSTEM_VERSION_APPSTORE);// 1表示成功
			jsonMap.put("path", SystemConstant.IOS_DOWN_FILE_NAME_APPSTORE);// iOS itunes 更新url
			jsonMap.put("tips", "最新版本为:"+SystemConstant.IOS_SYSTEM_VERSION_APPSTORE);
			jsonMap.put("forceUpdateFlag", SystemConstant.FORCE_UPDATE_FLAG_IOS_APPSTORE);
		}
		return jsonMap;
	}
	
	/**
	 * 文件下载
	 * @param fileName
	 * @param response
	 */
	@RequestMapping("downloadFile2")
	public void downloadFile2(String fileName,HttpServletResponse response){
		try {
			String path =request.getSession().getServletContext().getRealPath("upload");  
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName="+ java.net.URLEncoder.encode(fileName, "UTF-8"));  
			log.info("下载开始:"+path+File.separator+fileName);
			File file=new File(path+File.separator+fileName);
			System.out.println(file.getAbsolutePath());
			InputStream inputStream=new FileInputStream(file);
			OutputStream os=response.getOutputStream();
			byte[] b=new byte[1024];
			int length;
			while((length=inputStream.read(b))>0){
				os.write(b,0,length);
			}
			inputStream.close();
			log.info("下载完成。");
		} catch (Exception e) {
			log.info(fileName+" ,文件下载异常："+e);
		}
	}
	
	/**
	 * 文件下载
	 * @param fileName
	 * @param response
	 */
	@RequestMapping("downloadFile")
	public void downloadFile(String fileName,HttpServletResponse response){
		//fileName="app118.apk";
		String filePath =request.getSession().getServletContext().getRealPath("upload"); 
		OutputStream out = null;
		InputStream is=null;
		try {
			File file =new File(filePath+File.separator+fileName);
			log.info("开始下载,服务路径："+filePath+File.separator+fileName);
			is = new FileInputStream(file);
			request.setCharacterEncoding("UTF-8");
	    	response.setContentType("application/x-download" + ";  charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode(fileName, "UTF-8"));
			response.setHeader("Content-Length", (String.valueOf(file.length())));
			int i;   
			while((i=is.read()) != -1){ 
				out=response.getOutputStream();
			    out.write(i);  
			    out.flush();
			}
		} catch (Exception e) {
			log.info(fileName+" ,文件下载异常："+e);
		}finally{
			try {
				is.close();
				out.close();  
			} catch (IOException e) {
				log.info(fileName+" ,finally 文件下载处理关闭异常："+e);
			} 
		}
	}
	
	
	
}
