/**
 * @(#)SystemConstant.java	05/15/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-15
 */
package cn.app118.constants;

import java.util.Map;
import java.util.TreeMap;

/**
 * 此常量类做为本项目的系统常量类
 * 
 * @author 吴理琪
 * 
 */
public class SystemConstant {
	public static String SYSTEM_NAME = "后台管理系统";//系统名称
	
	public static String COMPANY_SIGNATURE = " 【莎琪美妆】";//公司签名

	public static String WEB_SYSTEM_VERSION = "1.0.0";//后台系统版本号  2015-03-12
	
	public static String SYSTEM_SKEY = "app118";//系统密钥,系统上线有数据后，不可修改
	
	/***android 版本更新配置**/
	public static String ANDROID_SYSTEM_VERSION = "1.0.0";// app版本号 最多为3位，形如1.0.0 
	public static String ANDROID_DOWNLOAD_URL="http://app.app118.cn/upload/app118.apk";//升级url
		
	/***iOS 版本更新配置**/
	public static String IOS_SYSTEM_VERSION_APPSTORE = "1.0.0";//2015-02-09 IOS appStore 版本号 最多为3位，形如1.0.1  2015-09-05  
	public static String IOS_DOWN_FILE_NAME_APPSTORE = "http://itunes.apple.com/lookup?id=你程序的appId";//iOS itunes更新url

	public static String MISSION_CHINA_WEATHER_URL="http://www.stateair.net/web/post/1/1.html";//美国大使馆天气预报
	//http://api.thinkpage.cn/weather/?cid=CHBJ000000  
	public static String THINKPAGE_WEATHER_URL="http://api.thinkpage.cn/weather/";//心知天气预报
	
	public static String PM_IKAIR_URL="http://pm.ikair.com/";//北京艾克艾瑞科技有限公司雾霾预报
	
	//Jpush相关信息
	public static String JPUSH_URL="https://api.jpush.cn/v3/push";//极光推送
	public static String JPUSH_APPKEY ="6dd474d7faac9eb609fe6403";//极光推送APPKEY
	public static String JPUSH_MASTER_SECRET ="8abc0b5db3a71144a25a9371";//极光推送MASTER_SECRET
	
	
	//个推相关信息 私家车版
	public static String IGETUI_APPID_CAR = "LhRCeifIUj8ACNeXL8SRKA";//AppID
	public static String IGETUI_APPKEY_CAR = "7te123CIQz6UCbVmKnFki4";//AppKey
	public static String IGETUI_MASTER_SECRET_CAR = "FNVeP97uva9yGtKMpBtbrA";//MasterSecret
	public static String IGETUI_HOST_CAR = "http://sdk.open.api.igexin.com/apiex.htm";
	
	//个推相关信息 私家车版 iOS企业版发布特殊准备
	public static String IGETUI_APPID_CAR_ENTERPRISE = "1LcC7JzKi1A8UoRdCGynV2";//AppID
	public static String IGETUI_APPKEY_CAR_ENTERPRISE = "9kES4s1Ech5puAwcmbiyB";//AppKey
	public static String IGETUI_MASTER_SECRET_CAR_ENTERPRISE = "99IAAMxM0F73JyW1Chf5V1";//MasterSecret
	public static String IGETUI_HOST_CAR_ENTERPRISE = "http://sdk.open.api.igexin.com/apiex.htm";
	
	//个推相关信息 出租车版
	public static String IGETUI_APPID_TAXI = "7GCt1asJnU6wAWDgrJjlZ";
	public static String IGETUI_APPKEY_TAXI = "ZQY8pq3lsl6XhBIXW4kq7";
	public static String IGETUI_MASTER_SECRET_TAXI = "CVDmFkMBXi6q7er5oZTvC9";
	public static String IGETUI_HOST_TAXI = "http://sdk.open.api.igexin.com/apiex.htm";
	
	//个推相关信息乘客版
	public static String IGETUI_APPID_PASSENGER = "0PI76tfWtIAdEj1lgI3KX8";
	public static String IGETUI_APPKEY_PASSENGER = "JJRUuB3nP76q29118fniT8";
	public static String IGETUI_MASTER_SECRET_PASSENGER = "oHkZMXnnG88Iz8aRwqVou9";
	public static String IGETUI_HOST_PASSENGER = "http://sdk.open.api.igexin.com/apiex.htm";
	
	public static String SYSTEM_EMAIL = "app118@app118.cn";//系统邮箱
	
	public static String BASE_URL = "http://app.app118.cn";//系统URL
	
	public static boolean SYSTEM_SWITCH = false;//系统开关标记
	
	public static String FORCE_UPDATE_FLAG="1";//android 强制升级标记
	
	public static String FORCE_UPDATE_FLAG_IOS_APPSTORE="0";//IOS 企业版强制升级标记
	
	public static int SYSTEM_YEARS=20;//下拉年份间隔
	
	public static int INACTIVE_INTERVAL=3;//未激活间隔 即未请求天气时间间隔


	public static boolean SMS_SEND_FLAG = false;// 短信发送开关，true表示打开，false表示关闭，默认为true
	
	/**华录亿动 ****/
//	public static String SMS_PROVIDER = "koonet.com";// 短信供应商  华录亿动
//	public static String SMS_SERVER_URL_SEND = "http://hl.my2my.cn/sms/push_mt.jsp";//华录亿动 短信服务地址 发送短信
//	public static String SMS_SERVER_URL_AMOUNT = "http://hl.my2my.cn/user/qamount.jsp";//华录亿动 短信服务地址 查询短信条数
//	public static String SMS_USER_ID = "sailsors";// 短信服务用户名
//	public static String SMS_USER_PWD = "mrwu123";// 短信服务用户密码
	/**北京神州软科信息技术有限责任公司    ****/	
//	public static String SMS_PROVIDER = "esoftsms.com";// 短信供应商  北京神州软科信息技术有限责任公司    
//	public static String SMS_SERVER_URL_SEND = "http://42.96.149.47:1086/sdk/BatchSend.aspx";//北京神州软科信息技术有限责任公司  短信服务地址 发送短信
//	public static String SMS_SERVER_URL_AMOUNT = "http://42.96.149.47:1086/sdk/SelSum.aspx";//北京神州软科信息技术有限责任公司  短信服务地址 查询短信条数
//	public static String SMS_USER_ID = "TEST02056";// 短信服务用户名
//	public static String SMS_USER_PWD = "123456";// 短信服务用户密码
	
	public static String SMS_PROVIDER = "inolink.com";// 短信供应商   北京同创凌凯信息技术有限公司  
	public static String SMS_SERVER_URL_SEND = "http://202.85.215.211:82/WS/BatchSend.aspx";// 北京同创凌凯信息技术有限公司  短信服务地址 发送短信  http://inolink.com
	public static String SMS_SERVER_URL_AMOUNT = "http://202.85.215.211:82/WS/SelSum.aspx";// 北京同创凌凯信息技术有限公司  短信服务地址 查询短信条数
	public static String SMS_USER_ID = "tclkj03077";// 短信服务用户名  tclkj02959
	public static String SMS_USER_PWD = "bjsessgs";// 短信服务用户密码 223311
	public static String SMS_USER_ID_MARKET = "tclkj03076";// 短信服务用户名  tclkj02959
	public static String SMS_USER_PWD_MARKET = "bjsessgs";// 短信服务用户密码 223311
	public static String WEICHAT_URL="https://api.weixin.qq.com/cgi-bin/token";//获微信token的url
	public static String WEICHAT_GRANT_TYPE="client_credential";//微信开发者ID ​
	public static String WEICHAT_APPID="wx5911ffa990c24579";//微信开发者ID ​
	public static String WEICHAT_SECRET="62dd68fe73fc935aecff9ce85cd7cd39";//微信开发者ID ​
	public static long MAX_TIME=1000*60*5;//短信验证码发送间隔时间  5分钟
	public static String MSG_EXCEPTION = "0";// 异常
	public static String MSG_SUCCESS = "1";// 成功
	public static String MSG_FAIL = "2";// 失败
	public static String MSG_NO_DATA = "9";// 无数据
	public static String STATUS_VALID = "1";// 有效
	public static String STATUS_INVALID = "0";// 无效
	public static int CARD_TIMES = 30;// 次卡设置为30次
	public static Double TIMECARD_TO_YEARCARD_RATE=3.5;//次卡转年卡兑换比率系数  次卡1天兑换成3.5天的年卡
	public static String PAGER_CURSIZE="15";//分页每页条数
	public static String  VERIVYWORD="verifyWord";//验证码
	public static float PMC_PRICE=800.00f;//应收控制器价格
	public static float PMC_PAID_PRICE=0.00f;//实收控制器价格
	public static float INSTALL_PRICE=400.00f;//应收安装费用价格
	public static float INSTALL_PAID_PRICE=0.00f;//实收安装费用价格
	
	/**
	 * 消息状态
	 * 
	 * 消息状态： 0：未查看  1：已查看 4：已删除
	 * @return
	 */
	public static final Map<String,String> getMsgStatusMap(){
		Map<String, String> map = new TreeMap<String, String>();
		map.put("0", "未查看");
		map.put("1", "已查看");
		map.put("4", "已删除");
		return map;
	}
	
	/**日志类型
	 * 
	 * 日志类型：  1：后台  3：app
	 * @return
	 */
	public static final Map<String,String> getLogTypeMap(){
		Map<String, String> map = new TreeMap<String, String>();
		map.put("1", "后台");
		map.put("3", "app");
		return map;
	}
	
	/**
	 * 代码类别   状态
	 * 
	 * 代码状态  状态 0：废弃  1：启用
	 * 
	 */
	public static final Map<String,String> getCodeStatusMap(){
		Map<String, String> map = new TreeMap<String, String>();
		map.put("0", "废弃");
		map.put("1", "启用");
		map.put("4", "已删除");
		return map;
	}
	
	/**
	 * 代码所属种类
	 * 
	 * 代码所属种类    10:产品分类  12:商家品牌
	 * 
	 * @return
	 */
	public static final Map<String,String> getCodeTypeMap(){
		Map<String, String> map = new TreeMap<String, String>();
		//商城
		map.put("10", "产品分类");//产品分类
		map.put("12", "商家品牌");//商家品牌
		
		//行政区域 天气信息使用
		map.put("10000", "行政区域");//行政区域 
		map.put("50000", "消息类型");//消息类型 
		return map;
	}
}
