/**
 * @(#)UserAction.java	05/22/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-22
 */
package cn.app118.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;


/**
 * @title :http相关的功能处理工具类
 * @author: 吴理琪
 * @date: 2015-05-23
 */
public class HttpUtil {
 
    private static int connectTimeout = 3000;// 连接超时值 ,单位:毫秒
    
    /**
     * @title 以GET的方式请求一个http资源,并将结果以字符串的方式返回
     * @param url url链接字符串
     * @param encoding 请求编码设置 ，UTF-8 或GBK或其他
     * @return 结果字符串
     * @throws IOException
     * */
    public static String sendGet(String url,String charset) throws IOException{
        String result = "";
        
        URL u = new URL(url);
        URLConnection conn = u.openConnection();
        conn.connect();
        conn.setConnectTimeout(connectTimeout);// 设置链接超时时间
        
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),charset));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        in.close();
        return result;
    }
    
    /**
     * @title 以POST的方式请求一个http资源,并将结果以字符串的方式返回
     * @param url url链接字符串
     * @param param 请求参数
     * @param charset 请求编码设置 ，UTF-8 或GBK或其他
     * @param timeout 连接超时值
     * @return 结果字符串
     * @throws IOException
     * */
    public static String sendPost(String url, String param,final String charset, int timeout) throws IOException{
        StringBuilder result = new StringBuilder();
        URL httpurl = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection) httpurl.openConnection();
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        httpConn.setRequestProperty("Accept-Encoding", "gzip");//设置gzip请求支持
//        httpConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");//设置gzip请求支持
//        httpConn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");//设置gzip请求支持
//        httpConn.setRequestProperty("Pragma", "no-cache");// 不缓存
//        httpConn.setRequestProperty("Cache-Control", "no-cache");// 不缓存
        httpConn.setConnectTimeout(timeout);
        PrintWriter out = new PrintWriter(httpConn.getOutputStream());
        out.print(param);
        out.flush();
        out.close();
        BufferedReader in = null;
        String str = httpConn.getContentEncoding();
         
        if(!(str == null || str.indexOf("gzip") == -1)){
            GZIPInputStream gin = new GZIPInputStream(httpConn.getInputStream());
            in = new BufferedReader(new InputStreamReader(gin,charset));
        }else{
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),charset));
        }
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        in.close();
        httpConn.disconnect();
        return result.toString();
    }
    
    public static void main(String[] args) {
    	try {
    		//http://hl.my2my.cn/user/qamount.jsp?cpid=sailsors&pwd=mrwu123
    		String randomNum=GenerateIdUtil.getRandomNumber(6);
    		String phoneNumber="15600692202";
    		String msg="您的验证码为:"+randomNum+"。【北京塞尔瑟斯仪表科技股份有限公司】";
    		System.out.println("验证码："+randomNum);
			StringBuilder sb = new StringBuilder("http://hl.my2my.cn/sms/push_mt.jsp?cpid=sailsors&cppwd=mrwu123&phone="+phoneNumber+"&spnumber="+System.currentTimeMillis()+"&msgcont="+URLEncoder.encode(msg,"gbk"));
			System.out.println("请求参数："+sb.toString());
			String str=HttpUtil.sendGet(sb.toString(), "gbk");
			System.out.println("调用结果："+str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
