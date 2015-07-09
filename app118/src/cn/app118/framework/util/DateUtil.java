/**
 * @(#)UserAction.java	05/22/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-22
 */
package cn.app118.framework.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.app118.constants.SystemConstant;

/**
 * @title :日期转换工具
 * @description :包含常用日期格式转换操作
 * @author: 吴理琪
 * @data: 2015-5-15
 */
public class DateUtil {
	/** 将日期及时间 格式为 yyyy-MM-dd HH:mm:ss或指定格式 * */
	public static String getFormatDate(Date date,String format) {
		if(format.isEmpty()){
			format="yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	/** 将毫秒数格式为 yyyy-MM-dd HH:mm:ss或指定格式 * */
	public static String getFormatDate(long millSec,String format) {
		if(format.isEmpty()){
			format="yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date=new Date(millSec);
		return sdf.format(date);
	}
	
	/** 得到系统当前日期及时间 格式为 yyyy-MM-dd HH:mm:ss * */
	public static String getDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/** 得到系统当前日期及时间 格式为 yyyy-MM-dd **/
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	/** 得到long型的日期值 * */
	public static Time getSqlTime() {
		return new Time(getTime());
	}

	/** 获取系统当前时间 * */
	public static long getTime() {
		java.util.Date dt = new java.util.Date();
		return dt.getTime();
	}

	/** 获取系统当前时间 * */
	public static Date getJavaDate() {
		return new java.util.Date();
	}

	/** 获取系统当前日期 得到的日期格式如：2004-10-09 * */
	public static java.sql.Date getSqlDate() {
		return new java.sql.Date(getTime());
	}

	/** 取得Timestamp类型时间 * */
	public static Timestamp getTimestamp() {
		return new Timestamp(getTime());
	}

	/** 得到Calendar对象 * */
	public static Calendar getCD() {
		Calendar mycd = Calendar.getInstance();
		return mycd;
	}

	/** 得到sStr格式日期 * */
	public static String getAll(String sStr) {
		Calendar mycd = Calendar.getInstance();
		return mycd.get(Calendar.YEAR) + sStr + (mycd.get(Calendar.MONTH) + 1)
				+ sStr + mycd.get(Calendar.DATE);
	}

	/** 得到日期,以-为分割符 * */
	public static String getAll() {
		return getAll("-");
	}

	/** 得到系统当前年 * */
	public static int getYear() {
		Calendar mycd = Calendar.getInstance();
		return mycd.get(Calendar.YEAR);
	}

	/** 得到系统当前月 * */
	public static int getMonth() {
		Calendar mycd = Calendar.getInstance();
		return mycd.get(Calendar.MONTH) + 1;
	}

	/** 得到系统当前日 * */
	public static int getDate() {
		Calendar mycd = Calendar.getInstance();
		return mycd.get(Calendar.DATE);
	}

	/** 得到系统年 * */
	public static int getAddYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/** 得到系统月 * */
	public static int getAddMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/** 得到系统日 * */
	public static int getAddDate() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	/** 得到日期格式为yyyy-mm-dd * */
	public static String getMiddle() {
		return getMiddle("-");
	}

	/** 得到日期格式为YYYY $sStr MM 其中sStr为分割字符 * */
	public static String getMiddleYM(String sStr) {
		Calendar mycd = Calendar.getInstance();
		int year = mycd.get(Calendar.YEAR);
		int month = mycd.get(Calendar.MONTH) + 1;
		String re = "" + String.valueOf(year);
		if (month < 10)
			re += sStr + "0" + String.valueOf(month);
		else
			re += sStr + String.valueOf(month);
		return re;
	}

	/** 得到日期格式为YYYY $sStr MM $sStr DD其中sStr为分割字符 * */
	public static String getMiddle(String sStr) {
		Calendar mycd = Calendar.getInstance();
		int year = mycd.get(Calendar.YEAR);
		int month = mycd.get(Calendar.MONTH) + 1;
		int date = mycd.get(Calendar.DATE);
		String re = "" + String.valueOf(year);
		if (month < 10)
			re += sStr + "0" + String.valueOf(month);
		else
			re += sStr + String.valueOf(month);
		if (date < 10)
			re += sStr + "0" + String.valueOf(date);
		else
			re += sStr + String.valueOf(date);
		return re;
	}

	/** 得到日期格式为 YYYY $sStr MM $sStr DD $sStr hh:mm:ss其中sStr为分割字符 * */
	public static String getTimeStr(String sStr) {

		Calendar mycd = Calendar.getInstance();
		String re = "" + mycd.get(Calendar.YEAR);
		if (mycd.get(Calendar.MONTH) + 1 < 10)
			re += sStr + "0" + String.valueOf(mycd.get(Calendar.MONTH) + 1);
		else
			re += sStr + String.valueOf(mycd.get(Calendar.MONTH) + 1);
		if (mycd.get(Calendar.DATE) < 10)
			re += sStr + "0" + String.valueOf(mycd.get(Calendar.DATE));
		else
			re += sStr + String.valueOf(mycd.get(Calendar.DATE));

		if (mycd.get(Calendar.HOUR) < 10)
			re += " " + "0" + String.valueOf(mycd.get(Calendar.HOUR));
		else
			re += " " + String.valueOf(mycd.get(Calendar.HOUR));
		if (mycd.get(Calendar.MINUTE) < 10)
			re += ":0" + String.valueOf(mycd.get(Calendar.MINUTE));
		else
			re += ":" + String.valueOf(mycd.get(Calendar.MINUTE));
		if (mycd.get(Calendar.SECOND) < 10)
			re += ":0" + String.valueOf(mycd.get(Calendar.SECOND));
		else
			re += ":" + String.valueOf(mycd.get(Calendar.SECOND));
		return re;
	}

	/** 取得两个日期的相隔天数 * */
	public static int getDays(Date sd, Date ed) {
		return (int) ((ed.getTime() - sd.getTime()) / (3600 * 24 * 1000));
	}

	/***************************************************************************
	 * 取得yyyymm,参数一：yyyymm,参数二：number 得到减去月份的日期
	 **************************************************************************/

	public static String getTime(String s) {
		if (s == null || s.equals(""))
			return "";
		String s1 = "";
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(s);
			s1 = simpledateformat.format(Calendar.getInstance().getTime());
		} catch (Exception exception) {
			System.out.println(Calendar.getInstance().toString()
					+ "cannot format time [function:getTime(String)]");
			exception.printStackTrace();
		}
		return s1;
	}

	/** 将String 转换操作，将sDt替换为Timestamp型的日期型 **/
	public static java.sql.Timestamp getDateTime(String sDt) {
		try {
			return java.sql.Timestamp.valueOf(sDt); // sDt
													// format:yyyy-mm-dd
													// hh:mm:ss.fffffffff
		} catch (IllegalArgumentException iae) {
			sDt = sDt + " 00:00:00";
			try {
				return java.sql.Timestamp.valueOf(sDt);
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 字符转java.sql.Date类型
	 * 
	 * @param sDate
	 *            :字符串形日期
	 * @param format
	 *            ：yyyy-MM-dd
	 * @return java.sql.Date 日期
	 * @author 吴理琪
	 */
	public static java.sql.Date getDate(String sDate, String format) {
		if (StringUtil.isEmpty(format))
			format = "yyyy-MM-dd";
		java.sql.Date date = null;
		if (!StringUtil.isEmpty(sDate)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			try {
				java.util.Date uDate = simpleDateFormat.parse(sDate);
				date = new java.sql.Date(uDate.getTime());
			} catch (ParseException e) {
				System.out.println("字符串型转java.sql.Date类型异常：" + e);
			}
		} else {
			System.out.println("转换的日期字符串：" + sDate + "为非法字符。");
		}
		return date;
	}

	/**
	 * java.sql.Date转字符串
	 * 
	 * @param date
	 *            :java.sql.Date
	 * @param format
	 *            : yyyy-MM-dd
	 * @return 日期字符串
	 * @author 吴理琪
	 */
	public static String getDateStr(java.sql.Date date, String format) {
		String sDate = "";
		if (null != date) {
			try {
				if (StringUtil.isEmpty(format))
					format = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
				sDate = simpleDateFormat.format(date);
			} catch (Exception e) {
				System.out.println("java.sql.Date类型转字符串型异常：" + e);
			}
		} else {
			System.out.println("转换的日期：" + sDate + "为非法日期。");
		}
		return sDate;
	}

	/**
	 * java.util.Date转换成java.sql.Date
	 * 
	 * @param uDate
	 *            :java.util.Date
	 * @return java.sql.Date 日期
	 * @author 吴理琪
	 */
	public static java.sql.Date utilDate2sqlDate(java.util.Date uDate) {
		java.sql.Date sDate = null;
		sDate = new java.sql.Date(uDate.getTime());
		return sDate;
	}

	/**
	 * java.sql.Date转换成java.util.Date
	 * 
	 * @param sDate
	 * @return java.util.Date 日期
	 * @author 吴理琪
	 */
	public static java.util.Date sqlDate2UtilDate(java.sql.Date sDate) {
		java.util.Date uDate = null;
		uDate = new java.util.Date(sDate.getTime());
		return uDate;
	}
	/**
	 * 在当前日期增加N天
	 * 
	 * @param d 日期
	 * @param day 天数
	 * @return
	 */
	public static Date addDate(Date d, long day) {
		long time = d.getTime();
		day = day * 24 * 60 * 60 * 1000;
		time += day;
		return new Date(time);
	}
	
	/**
	 * 在当前日期减去N天
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date subtractDate(Date d, long day) {
		long time = d.getTime();
		day = day * 24 * 60 * 60 * 1000;
		time -= day;
		return new Date(time);
	}

	/**
	 * 将英文格式的日期转换成标准日期
	 * 
	 * @param enDate
	 * @return
	 */
//	public static Date formatEnDate2Date(String enDate,String format){
//		SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy K a",Locale.ENGLISH); //MMM d, yyyy K:m:s a
//		if(StringUtil.isEmpty(format)){
//			sdf = new SimpleDateFormat("MMM d, yyyy K a",Locale.ENGLISH);
//		}else{
//			sdf = new SimpleDateFormat(format,Locale.ENGLISH);
//		}
//		Date d2 = null; 
//		try { 
//		d2 = sdf.parse(enDate); 
//		} catch (ParseException e) { 
//			e.printStackTrace(); 
//		} 
//		return d2;
//	}
	
	public static String phpDate2JavaDateStr(String phpDateStr,String format){
		if(StringUtil.isEmpty(format)){
			format="yyyy-MM-dd HH:mm:ss";
		}
		String dateTime="";
		phpDateStr=phpDateStr+"000";
		Long phpDateLong=Long.parseLong(phpDateStr);
		SimpleDateFormat df = new SimpleDateFormat(format); 
		dateTime = df.format(phpDateLong);		
		//System.out.println("php to java:"+dateTime);
		return dateTime;
	}
	
	
	/**
	 * 纠正美使馆时间
	 * @param publishTime
	 * @return
	 */
/*	public static String changerUsEmbassyAqiTime(String publishTime) {
		if(publishTime.endsWith("12 PM")){
			publishTime=publishTime.replaceAll("PM", "AM");
		}else if(publishTime.endsWith("12 AM")){
			publishTime=publishTime.replaceAll("AM", "PM");
		}
		return publishTime;
	}*/
	
	/**
	 * 将英文格式的日期转换成标准日期,并纠正美使馆时间
	 * 
	 * @date 2015/03/03
	 * 
	 */
	public static Date convertUsEmbassyAqiTime(String publishTime, String format) {
		Date usAqiPublishTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy K a",Locale.ENGLISH); // MMM d, yyyy K:m:s a
		try {
			if (StringUtil.isEmpty(format)) {
				sdf = new SimpleDateFormat("MMM d, yyyy K a", Locale.ENGLISH);
			} else {
				sdf = new SimpleDateFormat(format, Locale.ENGLISH);
			}

			if (publishTime.endsWith("12 PM")) {//中午12点特殊处理
				//publishTime = publishTime.replaceAll("PM", "AM");
				usAqiPublishTime = sdf.parse(publishTime);
				
				long time = usAqiPublishTime.getTime();
				long day =  24 * 60 * 60 * 1000/2;
				time += day;
				usAqiPublishTime=new Date(time);
				usAqiPublishTime = subtractDate(usAqiPublishTime, 1);
			} else if (publishTime.endsWith("12 AM")) {//00:00特殊处理
				//publishTime = publishTime.replaceAll("AM", "PM");
				usAqiPublishTime = sdf.parse(publishTime);
				
				long time = usAqiPublishTime.getTime();
				long day =  24 * 60 * 60 * 1000/2;
				time += day;
				usAqiPublishTime=new Date(time);
				usAqiPublishTime = subtractDate(usAqiPublishTime, 1);
			}else{
				usAqiPublishTime = sdf.parse(publishTime);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return usAqiPublishTime;
	}
	/**
	* 字符串转换成日期
	* @param str
	* @return date
	*/
	public static Date strToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	/**
	 * 时间比较
	 * @param d1
	 * @param d2
	 * @return >0:d1晚于d2   ==0： d1等于d2   <0:d1早于d2 
	 */
	public static int compare(Date d1, Date d2) {
		SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str1 = FORMAT.format(d1);
		//System.out.println("str1: " + str1);
		String str2 = FORMAT.format(d2);
		//System.out.println("str2: " + str2);

		int result = str1.compareTo(str2);
		/*if (result > 0) {
			System.out.println(str1 + " 晚于 " + str2);
		} else if (result == 0) {
			System.out.println(str1 + " 等于 " + str2);
		} else {
			System.out.println(str1 + " 早于 " + str2);
		}*/
		return result;
	}
	
	/**
	 * 获当前年减去20年做为下拉年份
	 * @return
	 */
	public static List<Integer> getSelectYears(){
		List<Integer> yearList=new ArrayList<Integer>();
		int curYear=DateUtil.getYear();//当前年减去20年做为下拉年份
		for(int i=curYear;i>=curYear-SystemConstant.SYSTEM_YEARS;i--){
			yearList.add(i);
		}
		return yearList;
	}
	public static void main(String[] args) {
		//System.out.println(DateUtil.getFormatDate(1425312000000L, ""));
		String times[]={"Mar 03, 2015 1 AM","Mar 03, 2015 2 AM","Mar 03, 2015 3 AM","Mar 03, 2015 4 AM",
				"Mar 03, 2015 5 AM","Mar 03, 2015 6 AM","Mar 03, 2015 7 AM","Mar 03, 2015 8 AM",
				"Mar 03, 2015 9 AM","Mar 03, 2015 10 AM","Mar 03, 2015 11 AM","Mar 03, 2015 12 PM","Mar 03, 2015 1 PM","Mar 03, 2015 2 PM","Mar 03, 2015 3 PM","Mar 03, 2015 4 PM",
				"Mar 03, 2015 5 PM","Mar 03, 2015 6 PM","Mar 03, 2015 7 PM","Mar 03, 2015 8 PM",
				"Mar 03, 2015 9 PM","Mar 03, 2015 10 PM","Mar 03, 2015 11 PM","Mar 04, 2015 12 AM"};
		String publishTime2="Mar 05, 2015 12 PM";
		System.out.println(publishTime2);
		Date userDate=DateUtil.convertUsEmbassyAqiTime(publishTime2,"");
		System.out.println(DateUtil.getFormatDate(userDate, ""));
		System.out.println("-------------");
		/*for(String publishTime:times){
			userDate=DateUtil.convertUsEmbassyAqiTime(publishTime,"");
			System.out.println(publishTime);
			System.out.println(DateUtil.getFormatDate(userDate, ""));
			System.out.println("-------------");
			
		}*/
		
		System.out.println("***:"+DateUtil.getFormatDate(1425528000000L, ""));
	}

}
