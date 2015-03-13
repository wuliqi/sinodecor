/**
 * @(#)PropertiesUtil.java	05/15/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-15
 */
package cn.app118.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取properties配置文件
 * 
 * @author 吴理琪
 * 
 */
public class PropertiesUtil {

	public  String getValue(String key) {
		Properties p = new Properties();
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("mybatis.properties");
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String result = "";
		result = p.getProperty(key);
		System.out.println(result);
		return result;
	}
	
	public static void main(String[] args) {
		PropertiesUtil propertiesUtil=new PropertiesUtil();
		propertiesUtil.getValue("jdbc.driverClass");
		
	}
}
