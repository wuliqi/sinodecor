/**
 * @(#)UserAction.java	12/01/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-12-01
 */
package cn.app118.framework.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * 公共工具Util类
 * 
 * @author wRitchie
 *
 */
public class GlobalUtil {
	private static Logger log = Logger.getLogger(GlobalUtil.class);//日志
	
	/**
	 * 获取客户端浏览器IP地址
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = null;
		try {
			ip = request.getHeader("X-Real-IP");
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("x-forwarded-for");
			}
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("Proxy-Client-Ip");
			}
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("WL-Proxy-Client-Ip");
			}
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getRemoteAddr();
			}
			if ("0:0:0:0:0:0:0:1".equals(ip))
				ip = "127.0.0.1";
		} catch (Exception ex) {
			log.info(GlobalUtil.class+"getIp 获取Ip错误", ex);
		}
		return ip;
	}
}
