/**
 * @(#)UserAction.java	06/13/2014
 * 
 * Copyright (c) 2014 SAILSORS INSTRUMENTS LTD.,BEIJING.All rights reserved.
 * Created by 2015-04-23
 */
package cn.app.action.stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.HttpUtil;
import cn.app118.framework.util.StringUtil;

/**
 * 新浪股票接口
 * @author wRitchie
 *
 */
@Controller
@RequestMapping("stockAction")
public class StockAction {
	
	String url="http://hq.sinajs.cn/list=";
	private String charset = "gb2312";// 页面编码
	
	@RequestMapping("queryStockInfo")
	@ResponseBody
	public Map<String,Object> queryStockInfo(String stockCode){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if(!StringUtil.isEmpty(stockCode)){
				String contentResponse = HttpUtil.sendGet(url+stockCode, charset);
				String []stockInfoArray=contentResponse.split(";");
				List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
				
				for(String stockInfo:stockInfoArray){
					Map<String, Object> oneStockMap=new HashMap<String, Object>();
					
					String[] tmpStockInfo=stockInfo.split("=");
					String stockCodeVar=tmpStockInfo[0];
					String stockValues=tmpStockInfo[1];
					int loc=stockCodeVar.lastIndexOf("_")+1;
					CharSequence oneStockCode=stockCodeVar.subSequence(loc,stockCodeVar.length());
					String [] stockValueArray=stockValues.split(",");
					oneStockMap.put("stockCode",oneStockCode);
					oneStockMap.put("stockValues", stockValueArray);
					list.add(oneStockMap);
				}
				jsonMap.put("list", list);
				jsonMap.put("message", SystemConstant.MSG_SUCCESS);
				jsonMap.put("size", list.size());
			}else{
				jsonMap.put("message", SystemConstant.MSG_NO_DATA);
				jsonMap.put("error","股票代码为空。");
			}
		} catch (IOException e) {
			System.out.println("查询股票异常："+e);
		}
		return jsonMap;
		
	}
	
	public static void main(String[] args) {
		StockAction stockUtil=new StockAction();
		Map<String, Object> jsonMap=stockUtil.queryStockInfo("sh601003,sh601001,sz000100,s_sz399001");
		System.out.println("****:"+jsonMap);
	}
}
