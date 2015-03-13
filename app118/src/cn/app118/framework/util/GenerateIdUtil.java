/**
 * @(#)UserAction.java	05/15/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-15
 */
package cn.app118.framework.util;

import java.util.Random;


public class GenerateIdUtil {

	/**
	 * 自动生成16位字符串ID,可转为BigDecemal
	 * @return 16位数字的字符串
	 */
	public static String getSerialNo()
	{
		//String id=UUID.randomUUID().toString();
		Long currentTime=System.currentTimeMillis();
		String id=currentTime.toString()+getRandomNumber(3);
		return id;
	}

	  /**
	  * 生成指定位数的随机数字符串
	  * @param randomCount：要生成多少位的随机数
	  * @return 相应位数的随机数
	  * @author wRitchie
	  */
	 public static  String getRandomNumber(int randomCount) {
	  Random random = new Random();
	     StringBuilder sb = new StringBuilder(randomCount);
	     for(int i=0; i < randomCount; i++)
	         sb.append((char)('0' + random.nextInt(10)));
	     return sb.toString();
	 }
	 
	 /**
	 * 根据充值卡类型，自动生成16位字符串ID,可转为BigDecemal
	 * @return 16位数字的字符串
	 */
	public static String getSerialNoByCardType(String cardType)
	{
		//String id=UUID.randomUUID().toString();
		Long currentTime=System.currentTimeMillis();
		String id=cardType+currentTime.toString()+getRandomNumber(2);
		return id;
	}
	
	public static void main(String[] args) {
		for(int i=0;i<10;++i){
			i=i+1;
			System.out.println(GenerateIdUtil.getSerialNoByCardType(i+""));
		}
	}
}
