/**
 * @(#)UserAction.java	09/02/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-02
 */
package cn.app118.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.app118.constants.SystemConstant;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

/**
 * 个推推送消息工具类
 * 
 * @author wRitchie
 * 
 */
public class GetuiUtil {
	private static Logger log = Logger.getLogger(GetuiUtil.class);

	static String host ;
	static String appKey;
	static String master;
	static String appId;
	
	public static void main(String[] args) throws Exception {
		GetuiUtil.initConfig("4");
		Map map=new HashMap();
		map.put("msgContent", "airbest有更新，请下载更新.");
		map.put("msgContentJson", "app118 test for iOS.");
		map.put("clientId", "a432ef8a1b3f6ac510fdea91f3be0fe1");//219
		GetuiUtil.pushTransmissionTemplateMsg(map);
	}
	
	/**
	 * 初始化个推信息
	 * 
	 * @param type 4:表示私家车（或8表私家车iOS企业版发布、4表示私家车android和iOS appstore发布）  2：表示出租车        6:表示乘客 
	 */
	public static void initConfig(String type){
		if("4".equals(type)){//私家车
			 host = SystemConstant.IGETUI_HOST_CAR;
			 appKey = SystemConstant.IGETUI_APPKEY_CAR;
			 master = SystemConstant.IGETUI_MASTER_SECRET_CAR;
			 appId = SystemConstant.IGETUI_APPID_CAR;
		}else if("2".equals(type)){//出租车
			 host = SystemConstant.IGETUI_HOST_TAXI;
			 appKey = SystemConstant.IGETUI_APPKEY_TAXI;
			 master = SystemConstant.IGETUI_MASTER_SECRET_TAXI;
			 appId = SystemConstant.IGETUI_APPID_TAXI;
		}else if("6".equals(type)){//乘客版
			 host = SystemConstant.IGETUI_HOST_PASSENGER;
			 appKey = SystemConstant.IGETUI_APPKEY_PASSENGER;
			 master = SystemConstant.IGETUI_MASTER_SECRET_PASSENGER;
			 appId = SystemConstant.IGETUI_APPID_PASSENGER;
		}else if("8".equals(type)){//私家车 iOS 企业版 特殊版 //TODO
			 host = SystemConstant.IGETUI_HOST_CAR_ENTERPRISE;
			 appKey = SystemConstant.IGETUI_APPKEY_CAR_ENTERPRISE;
			 master = SystemConstant.IGETUI_MASTER_SECRET_CAR_ENTERPRISE;
			 appId = SystemConstant.IGETUI_APPID_CAR_ENTERPRISE;
		}
		
		log.info("##########################");
		log.info("类型："+type);
		log.info("host："+host);
		log.info("appKey："+appKey);
		log.info("master："+master);
		log.info("appId："+appId);
		log.info("##########################");
	}

	/**
	 * 透传消息模板--推送消息
	 * 
	 * @param map
	 *   msgContentJson:消息json串
	 *   msgContent:iOS下拉通知内容
	 *   clientId:个推唯一标识
	 *   
	 * @return
	 */
	public static String pushTransmissionTemplateMsg(Map map){
		String msgContentJson=map.get("msgContentJson")+"";
		String msgContent=map.get("msgContent")+"";
		String clientId=map.get("clientId")+"";
		IPushResult ret = null ;
		try {
			// 配置返回每个用户返回用户状态
			System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
		
			IGtPush push = new IGtPush(host, appKey, master);
			// 建立连接，开始鉴权
			push.connect();
			// 通知透传模板
			TransmissionTemplate template = new TransmissionTemplate();
			
			template.setAppId(appId);
			template.setAppkey(appKey);
			template.setTransmissionType(2);//收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动
			template.setTransmissionContent(msgContentJson);
			template.setPushInfo("test", 1, msgContent, "test1.wav");//iOS推送需要对该字段进行设置具体参数详见iOS模板说明 
			
			SingleMessage message = new SingleMessage();
			message.setOffline(true);
			message.setOfflineExpireTime(24 * 3600 * 1000); //离线有效时间，单位为毫秒，可选
			message.setData(template);
  
			Target target = new Target();
			target.setAppId(appId);
			target.setClientId(clientId);
			ret = push.pushMessageToSingle(message, target);
			// 打印服务器返回信息
			log.info(clientId+"###透传消息模板--推送消息结果:" + ret.getResponse().toString());
		} catch (Exception e) {
			log.info("###透传消息模板--推送消息异常："+e);
		}
		return  ret.getResponse().toString();
	}
	
	
	/**
	 * 针对指定的目标条件(透传消息模板)--推送消息
	 * 
	 * @param map
	 * msgContentJson:内容JSON串
	 * msgContent：iOS下拉通知内容
	 *
	 * @return
	 */
	public static String pushTransmissionTemplateToApp(Map map){
		String msgContentJson=map.get("msgContentJson")+"";//内容JSON串
		String msgContent=map.get("msgContent")+"";//消息内容
		
		IPushResult ret=null;
		try {
			IGtPush push =  new IGtPush(host, appKey, master);
			//建立连接，开始鉴权
			push.connect();
			
			//透传模板
			TransmissionTemplate template = new TransmissionTemplate();
			template.setAppId(appId);
			template.setAppkey(appKey);
			template.setTransmissionType(2);
			template.setTransmissionContent(msgContentJson);
			template.setPushInfo("test", 1, msgContent, "test1.wav");//iOS推送需要对该字段进行设置具体参数详见iOS模板说明 
			
			AppMessage message = new AppMessage();
			message.setData(template);
			//设置消息离线，并设置离线时间
			message.setOffline(true);
			message.setOfflineExpireTime(24*1000*3600);//离线有效时间，单位为毫秒，可选
			//设置推送目标条件过滤
			List appIdList = new ArrayList();
			List phoneTypeList = new ArrayList();
			List provinceList = new ArrayList();
			List tagList = new ArrayList();
			appIdList.add(appId);
			//设置机型
			phoneTypeList.add("ANDROID");
			phoneTypeList.add("IOS");
			//provinceList.add("浙江");//设置省份
			//tagList.add("开心");//设置标签内容
			message.setAppIdList(appIdList);
			message.setPhoneTypeList(phoneTypeList);
			message.setProvinceList(provinceList);
			message.setTagList(tagList);
			ret = push.pushMessageToApp(message);
			log.info("###针对指定的目标条件(透传消息模板)--推送消息至app结果："+ret.getResponse().toString());
		} catch (Exception e) {
			log.info("###针对指定的目标条件(透传消息模板)--推送消息至app异常："+e);
		}
		return ret.getResponse().toString();
	}
	
	//********************************************//
	
	/**
	 * 点击通知启动应用--消息推送	
	 * 
	 * @param map
	 * @return
	 */
	@Deprecated
	public static String pushNotificationTemplateMsg(Map map){
		IPushResult ret=null;
		try {
			IGtPush push =  new IGtPush(host, appKey, master);
			push.connect();
			
			NotificationTemplate template = new NotificationTemplate();
		    // 设置APPID与APPKEY
			template.setAppId(appId);
			template.setAppkey(appKey);
		    // 设置通知栏标题与内容
		    template.setTitle("Airbest请输入通知栏标题");
		    template.setText("Airbest请输入通知栏内容");
		    // 配置通知栏图标
		    template.setLogo("icon.png");
		    // 配置通知栏网络图标
		    template.setLogoUrl("");
		    // 设置通知是否响铃，震动，或者可清除
		    template.setIsRing(true);
		    template.setIsVibrate(true);
		    template.setIsClearable(true);
		    
			SingleMessage message = new SingleMessage();
			message.setOffline(true);
			message.setOfflineExpireTime(24 * 3600 * 1000);//离线有效时间，单位为毫秒，可选
			message.setData(template);
  
			Target target = new Target();
			target.setAppId(appId);
			target.setClientId("");//CID
  
			ret = push.pushMessageToSingle(message, target);
			log.info("###点击通知启动应用--消息推送结果:"+ret.getResponse().toString());
		} catch (Exception e) {
			log.info("###点击通知启动应用--消息推送异常："+e);
		}
		return ret.getResponse().toString();
	}
	
	/**
	 * 针对指定的目标条件(点击通知启动应用)--推送消息
	 * 
	 * @param map
	 * @return
	 */
	@Deprecated
	public static String pushNotificationTemplateToApp(Map map){
		String msgTitle=map.get("msgTitle")+"";
		String msgContent=map.get("msgContent")+"";
		IPushResult ret=null;
		try {
			IGtPush push =  new IGtPush(host, appKey, master);
			//建立连接，开始鉴权
			push.connect();
			
			NotificationTemplate template = new NotificationTemplate();
		    // 设置APPID与APPKEY
			template.setAppId(appId);
			template.setAppkey(appKey);
		    // 设置通知栏标题与内容
		    template.setTitle(msgTitle);
		    template.setText(msgContent);
		    // 配置通知栏图标
		    template.setLogo("icon.png");
		    // 配置通知栏网络图标
		    template.setLogoUrl("");
		    // 设置通知是否响铃，震动，或者可清除
		    template.setIsRing(true);
		    template.setIsVibrate(true);
		    template.setIsClearable(true);
			
			
			AppMessage message = new AppMessage();
			message.setData(template);
			//设置消息离线，并设置离线时间
			message.setOffline(true);
			message.setOfflineExpireTime(24*1000*3600);//离线有效时间，单位为毫秒，可选
			//设置推送目标条件过滤
			List appIdList = new ArrayList();
			List phoneTypeList = new ArrayList();
			List provinceList = new ArrayList();
			List tagList = new ArrayList();
			appIdList.add(appId);
			//设置机型
			phoneTypeList.add("ANDROID");
			phoneTypeList.add("IOS");
			//provinceList.add("浙江");//设置省份
			//tagList.add("开心");//设置标签内容
			message.setAppIdList(appIdList);
			message.setPhoneTypeList(phoneTypeList);
			message.setProvinceList(provinceList);
			message.setTagList(tagList);
			ret = push.pushMessageToApp(message);
			log.info("###推送到app结果："+ret.getResponse().toString());
		} catch (Exception e) {
			log.info("推送到app异常："+e);	
		}
		return ret.getResponse().toString();
	}
}
