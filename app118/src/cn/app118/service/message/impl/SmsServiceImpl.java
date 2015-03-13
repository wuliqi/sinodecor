/**
 * @(#)UserAction.java	05/23/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-23 
 */

package cn.app118.service.message.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.app118.constants.SystemConstant;
import cn.app118.dao.message.MessageMapper;
import cn.app118.framework.util.GenerateIdUtil;
import cn.app118.framework.util.HttpUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.Message;
import cn.app118.service.message.ISmsService;

/**
 * @title：短信服务实现类
 * @description: 针对短信发送、查询短信数的管理控制类。
 * @author： wRitchie
 * @date： 2015-05-23 10:10
 */
@Service("smsService")
public class SmsServiceImpl implements ISmsService {
	private Logger log = Logger.getLogger(this.getClass());// 日志
	private String url = "";// 请求的URL
	private String result = "";// 请求返回的结果
	private int count = 0;// 剩余短信条数

	@Resource
	private MessageMapper messageMapper;//后台消息Dao
	/**
	 * 发送短信
	 * 
	 * @param phoneNumber
	 *            手机号
	 * @param msg
	 *            短信内容
	 * @param type  1:表示行业信息  3:表示营销信息
	 * 
	 */
	@Override
	public String sendSms(String phoneNumber, String msg,String type,Integer userId) {
		log.info("正往" + phoneNumber + ",发送短信...");
		log.info("发达的内容为：" + msg);
		try {
			if ("koonet.com".equals(SystemConstant.SMS_PROVIDER)) {//华录亿动
				String msgTmp = URLEncoder.encode(msg.replaceAll("<br/>", " "),
						"GBK");
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("cpid", SystemConstant.SMS_USER_ID);
				params.put("cppwd", SystemConstant.SMS_USER_PWD);
				params.put("spnumber", System.currentTimeMillis());
				params.put("phone", phoneNumber);
				params.put("msgcont", msgTmp);
				url = SystemConstant.SMS_SERVER_URL_SEND
						+ StringUtil.buildUrlParams(params);
				if (SystemConstant.SMS_SEND_FLAG) {
					try {
						result = HttpUtil.sendGet(url, "GBK");
						if("0".equals(result)){//成功
							addMessageLog(phoneNumber,msg,userId,type);
						}else{//失败
							
						}
					} catch (Exception e) {
						log.info("ERROR&发送短信失败:" + e);
						result = "ERROR&发送短信失败。";
					}
				} else {
					result = "ERROR&系统短信开关未打开，无法发送短信。";
				}
				log.info("短信发送结果：" + result);
			} else if ("esoftsms.com".equals(SystemConstant.SMS_PROVIDER)) {// 北京神州软科信息技术有限责任公司
				String tmp = URLEncoder.encode(msg.replaceAll("<br/>", " "),
						"UTF-8");
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("CorpID", SystemConstant.SMS_USER_ID);// 账号
				params.put("Pwd", SystemConstant.SMS_USER_PWD);// 密码
				params.put("Mobile", phoneNumber);// 发送手机号码
				params.put("Content", tmp);// 发送内容
				params.put("Cell", "");// 子号
				params.put("SendTime", "");// 定时发送时间
											// 固定14位长度字符串，比如：20060912152435代表2006年9月12日15时24分35秒，为空表示立即发送
				url = SystemConstant.SMS_SERVER_URL_SEND
						+ StringUtil.buildUrlParams(params);
				if (SystemConstant.SMS_SEND_FLAG) {
					try {
						result = HttpUtil.sendGet(url, "UTF-8");
						if ("0".equals(result)) {
							addMessageLog(phoneNumber,msg,userId,type);
						} else {
							result = result + "ERROR&发送短信失败。";
						}
					} catch (Exception e) {
						log.info("ERROR&发送短信失败:" + e);
						result = "ERROR&发送短信失败。";
					}
				} else {
					result = "ERROR&系统短信开关未打开，无法发送短信。";
				}
				log.info("短信发送结果：" + result);

			}else if("inolink.com".equals(SystemConstant.SMS_PROVIDER)){//北京同创凌凯信息技术有限公司
				String msgTmp = URLEncoder.encode(msg.replaceAll("<br/>", " "),"GBK");
				Map<String, Object> params = new HashMap<String, Object>();
				if("3".equals(type)){//营销信息账号
					params.put("CorpID", SystemConstant.SMS_USER_ID_MARKET);//营销信息账号
					params.put("Pwd", SystemConstant.SMS_USER_PWD_MARKET);//营销信息密码
				}else{
					params.put("CorpID", SystemConstant.SMS_USER_ID);//行业信息账号
					params.put("Pwd", SystemConstant.SMS_USER_PWD);//行业信息密码
				}
				params.put("Cell", "");//子号
				params.put("Mobile", phoneNumber);//发送手机号码
				params.put("Content", msgTmp);//发送内容
				url = SystemConstant.SMS_SERVER_URL_SEND
						+ StringUtil.buildUrlParams(params);
				if (SystemConstant.SMS_SEND_FLAG) {
					result = HttpUtil.sendGet(url, "GBK");
					Integer returnValue=Integer.parseInt(result.trim());
					if(returnValue>=0){//成功
						addMessageLog(phoneNumber,msg,userId,type);
					}else{//失败
						
					}
				} else {
					result = "ERROR&系统短信开关未打开，无法发送短信。";
				}
				log.info("短信发送结果：" + result);
				
			}
	
		} catch (Exception e) {
			log.info("sendSms方法异常："+e);
		}

		return result;
	}

	@Override
	public int getRemainSmsAmount() {
		try {
			if ("koonet.com".equals(SystemConstant.SMS_PROVIDER)) {// 华录亿动  
				Map<String, Object> params = new HashMap<String, Object>();
				String pwd = URLEncoder.encode(
						SystemConstant.SMS_USER_PWD.replaceAll("<br/>", " "),
						"GBK");
				params.put("cpid", SystemConstant.SMS_USER_ID);
				params.put("pwd", pwd);
				url = SystemConstant.SMS_SERVER_URL_AMOUNT
						+ StringUtil.buildUrlParams(params);
				log.info("总计剩余短信条数URL：" + url);
				if (SystemConstant.SMS_SEND_FLAG) {
					try {
						result = HttpUtil.sendGet(url, "GBK");
					} catch (Exception e) {
						log.info("查询总计剩余短信条数异常：" + e);
					}
				}
				log.info("总计剩余短信条数：" + result);
				count = Integer.parseInt(result);
			} else if ("esoftsms.com".equals(SystemConstant.SMS_PROVIDER)) {//北京神州软科信息技术有限责任公司
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("CorpID", SystemConstant.SMS_USER_ID);
				params.put("Pwd", SystemConstant.SMS_USER_PWD);
				url = SystemConstant.SMS_SERVER_URL_AMOUNT
						+ StringUtil.buildUrlParams(params);
				log.info("总计剩余短信条数URL：" + url);
				if (SystemConstant.SMS_SEND_FLAG) {
					try {
						result = HttpUtil.sendGet(url, "UTF-8");
					} catch (Exception e) {
						log.info("查询总计剩余短信条数异常：" + e);
					}
				} else {
					result = "0";
					log.info("ERROR&系统短信开关未打开，无法发送短信。");
				}
				log.info("总计剩余短信条数：" + result);
				count = Integer.parseInt(result);
			}else if ("inolink.com".equals(SystemConstant.SMS_PROVIDER)) {//北京同创凌凯信息技术有限公司
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("CorpID", SystemConstant.SMS_USER_ID);
				params.put("Pwd", SystemConstant.SMS_USER_PWD);
				url = SystemConstant.SMS_SERVER_URL_AMOUNT
						+ StringUtil.buildUrlParams(params);
				log.info("总计剩余短信条数URL：" + url);
				if (SystemConstant.SMS_SEND_FLAG) {
					try {
						result = HttpUtil.sendGet(url, "GBK");
					} catch (Exception e) {
						log.info("查询总计剩余短信条数异常：" + e);
					}
				} else {
					result = "0";
					log.info("ERROR&系统短信开关未打开，无法发送短信。");
				}
				log.info("总计剩余短信条数：" + result);
				count = Integer.parseInt(result);
			}
		} catch (Exception e) {
			log.info(e);
		}
		return count;
	}

	
	private void addMessageLog(String phoneNumber,String msg,Integer userId,String type){
		Message message=new Message();
		message.setMsgTitle(phoneNumber+"_"+SystemConstant.SMS_PROVIDER);
		message.setMsgContent(msg);
		message.setMsgType("1408");//短信 TODO
		message.setUserId(userId);
		message.setMsgCreateTime(new Date());
		message.setIsStick(type);//短信类型  1:表示行业信息  3:表示营销信息
		int result=messageMapper.insert(message);
		if(result>0){
			log.info("发送短信息记录日志成功。");
		}else{
			log.info("发送短信息失记录日志几败");
		}
	}
	public static void main(String[] args) {
		ISmsService smsService = new SmsServiceImpl();

		/*smsService.sendSms("18810790739",
				"您的验证码为：" + GenerateIdUtil.getRandomNumber(6)
						+ "，请输入验证码完成验证，（请勿向任何人提供您收到的短信验证码）【控杰科技】");*/
		smsService.getRemainSmsAmount();
	}
}
