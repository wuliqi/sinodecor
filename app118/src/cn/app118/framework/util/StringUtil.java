/**
 * @(#)UserAction.java	05/15/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-15
 */
package cn.app118.framework.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 字符串工具类
 * 
 * @author 吴理琪
 * 
 */
public class StringUtil {

	/**
	 * 将null值转为""
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String trimNull(String srcStr) {
		String result = "";
		if (!(null == srcStr || srcStr.equalsIgnoreCase("null")||"undefined".equalsIgnoreCase(srcStr))) {
			result = srcStr;
		}
		return result;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return true:表示空，false：表示不空
	 */
	public static boolean isEmpty(String str) {
		boolean flag = false;
		if (null == str ||"null".equals(str)|| "".equals(str) || str.trim().length() < 1||"undefined".equalsIgnoreCase(str))
			flag = true;
		return flag;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(Object str) {
		return str != null;
	}

	/**
	 * 构建http请求参数组装方法
	 * 
	 * @param parameters
	 *            ：键值对参数
	 * @return 返回组装好的url请求参数字符串
	 */
	public static String buildUrlParams(Map<String, Object> parameters) {
		String urlString = "";
		if (parameters != null) {
			int i = 0;
			StringBuffer param = new StringBuffer();
			for (String key : parameters.keySet()) {
				if (i == 0) {
					param.append("?");
				} else {
					param.append("&");
				}
				param.append(key).append("=").append(parameters.get(key));
				i++;
			}
			urlString += param;
		}
		return urlString;
	}
	/**
	 * ISO8859-1转UTF-8
	 * @param iso88591Str
	 * @return
	 */
	public static String iso88591ToUtf8(String iso88591Str) {
		String utfStr = "";
		try {
			if (!isEmpty(iso88591Str)) {
				utfStr = new String(iso88591Str.getBytes("iso-8859-1"), "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("ISO8859-1转UTF-8异常："+e);
		}
		return utfStr;
	}
	
	/**
	 * 去掉所有的空格
	 * @param str
	 * @return
	 */
	public static String trim(String str){
		str=str.replaceAll(" +","");
		return str;
	}
}
