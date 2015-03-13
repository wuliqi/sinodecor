/**
 * @(#)UserAction.java	06/13/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-06-13
 */
package cn.app118.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.app118.constants.SystemConstant;

/**
 * @title: 从网络抓取资源工具类
 * @description: 第一步：列表抓取(列表网址,网页编码) 第二步：内容抓取
 * @author： 吴理琪
 * @date： 2015-06-16 09:10
 */
public class WebScraping {

	/**
	 * @title 获取采集链接列表
	 * @description:获取采集链接列表
	 * @param s
	 *            : 模拟浏览器获取， startFlag：开始标记，endFlag：结束标记
	 * @author: 吴理琪
	 * @date: 2015-06-16
	 * @return List<String> 采集链接列表
	 */
	public static void getLists(String s, String startFlag, String endFlag) {
		// 获取标题
		String title = getTitle(s);
		// 去掉指定后缀
		// title = title.replaceAll("_39健康保健_39健康网", "");
		// System.out.println(title);

		// 获取区域内容
		String area = getAreaContent(s, startFlag, endFlag);

		// 获取超链接
		List<String> links = getLink(area);
		for (String link : links) {
			// System.out.println(link);
		}
	}

	/**
	 * 获取采集标题及URL列表
	 * 
	 * @description:
	 * @param s
	 *            模拟浏览器获取
	 * @param startFlag
	 *            开始标记
	 * @param endFlag
	 *            结束标记
	 * @author: 吴理琪
	 * @date: 2015-06-16
	 * @return： List<Map> 采集标题、链接列表
	 */
	public static List<Map> getTitleAndUrlLists(String s, String startFlag,
			String endFlag) {
		// 获取区域内容
		String area = getAreaContent(s, startFlag, endFlag);
		// 获取超链接
		List<Map> links = getTitleAndUrlLink(area);
		return links;
	}

	/**
	 * 获取指定区域的内容
	 * 
	 * @description: 获取指定区域的内容 <div class=\"partbox\">(.*?)<div
	 *               class=\"con_right\">
	 * @param s
	 *            模拟浏览器获取
	 * @param startFlag
	 *            开始标记
	 * @param endFlag
	 *            结束标记
	 * @author: 吴理琪
	 * @date: 2015-06-16
	 * @return： List<Map> 标题、链接列表
	 */
	public static String getAreaContent(String s, String startFlag,
			String endFlag) {
		final Pattern pa = Pattern.compile(startFlag + "(.*?)" + endFlag,
				Pattern.DOTALL);
		final Matcher ma = pa.matcher(s);
		StringBuffer sb = new StringBuffer();
		while (ma.find()) {
			sb.append(ma.group());
		}
		return sb.toString();
	}

	/**
	 * 获得网页标题
	 * 
	 * @description: 获得网页标题
	 * @param s
	 *            模拟浏览器获取
	 * @author: 吴理琪
	 * @date: 2015-06-16
	 * @return： String> 网页标题
	 */
	public static String getTitle(final String s) {
		String regex;
		String title = "";
		final List<String> list = new ArrayList<String>();
		regex = "<title>.*?</title>";
		final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		final Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		for (int i = 0; i < list.size(); i++) {
			title = title + list.get(i);
		}
		return outTag(title);
	}

	/**
	 * 获得超链接
	 * 
	 * @description: 获得超链接
	 * @param s
	 *            模拟浏览器获取
	 * @author: 吴理琪
	 * @date: 2015-06-16
	 * @return： List<String> 超链接
	 */
	public static List<String> getLink(final String s) {
		String regex;
		final List<String> list = new ArrayList<String>();
		regex = "<a[^>]*href=(\"([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>(.*?)</a>";
		final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		final Matcher ma = pa.matcher(s);
		while (ma.find()) {
			String href = ma.group(2);
			if (href.matches("(http|https)://.*")) {
				list.add(href);
			}
			// System.out.println("(2)==>"+ma.group(2));
			// System.out.println("(5)==>"+ma.group(5));
		}
		return list;
	}

	/**
	 * 获取列表标题及url
	 * 
	 * @description: 获取列表标题及url
	 * @param s
	 *            模拟浏览器获取
	 * @author:吴理琪
	 * @return： List<Map> 标题、链接列表
	 */
	public static List<Map> getTitleAndUrlLink(final String s) {
		String regex;
		final List<Map> list = new ArrayList<Map>();
		regex = "<a[^>]*href=(\"([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>(.*?)</a>";
		final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		final Matcher ma = pa.matcher(s);
		while (ma.find()) {
			String href = ma.group(2);
			if (href.matches("(http|https)://.*")) {
				Map oneMap = new HashMap();
				oneMap.put("title", ma.group(5));
				oneMap.put("url", ma.group(2));
				list.add(oneMap);
			}
		}
		return list;
	}

	/**
	 * 去掉标记
	 * 
	 * @description: 去掉标记
	 * @param s
	 *            模拟浏览器获取
	 * @author:吴理琪
	 * @return： String 标题、链接列表
	 */
	public static String outTag(final String s) {
		return s.replaceAll("<.*?>", "");
	}

	/**
	 * 获得脚本代码
	 * 
	 * @description: 获得脚本代码
	 * @param s
	 *            模拟浏览器获取
	 * @author:吴理琪
	 * @return List<String> 获得脚本代码列表信息
	 */
	public static List<String> getScript(final String s) {
		String regex;
		final List<String> list = new ArrayList<String>();
		regex = "<script.*?</script>";
		final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		final Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}

	/**
	 * 获得CSS
	 * 
	 * @description:获得CSS
	 * @param s
	 *            模拟浏览器获取
	 * @author:吴理琪
	 * @return 获得CSS
	 */
	public List<String> getCSS(final String s) {
		String regex;
		final List<String> list = new ArrayList<String>();
		regex = "<style.*?</style>";
		final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		final Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}

	public static void main(String[] args) {
		try {
			// 第一步：获取采集列表内容
			String url = SystemConstant.THINKPAGE_WEATHER_URL;
			String charset = "UTF-8";
			String beginTag = "thinkpage_weather_res = {";
			String endTag = "thinkpage_weather_locationpath =";
			String contentResponse = HttpUtil.sendGet(url, charset);
			
			int begin=contentResponse.indexOf(beginTag);
			int end=contentResponse.indexOf(endTag);
			String json=contentResponse.substring(begin,end);
			json=json.replaceAll(";", "");
			String []josnArray=json.split("=");
			
			
			
			System.out.println(josnArray[0]);
			System.out.println(josnArray[1]);
			Map<String, Object> result=FastJsonUtil.toMap(josnArray[1]);
			
//			System.out.println("re:"+contentResponse);
//			String content = getAreaContent(contentResponse, startFlag,endFlag);
//			System.out.println("con:"+content);
//			content = outTag(content).replaceAll("\\s*", "");
//			String[] str = content.split("AQI");
			// content=content.replaceAll("(?i)(<iframe([^>]*)>([\\s\\S]*)</iframe>|<script([^>]*)>([\\s\\S]*)</script>|<!--([\\s\\S]*)-->)",
			// "");

			// System.out.println(content);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
