/**
 * @(#)MessageAction.java	06/10/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-06-10
 */
package cn.app118.action.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.ButtonPermissionConstant;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.GetuiUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.Code;
import cn.app118.model.Message;
import cn.app118.model.MessageSend;
import cn.app118.model.Org;
import cn.app118.model.PushMessage;
import cn.app118.model.Role;
import cn.app118.model.User;
import cn.app118.model.UserBinding;
import cn.app118.service.code.ICodeService;
import cn.app118.service.message.IMessageService;
import cn.app118.service.message.ISmsService;
import cn.app118.service.org.IOrgService;
import cn.app118.service.user.IUserService;

import com.alibaba.fastjson.JSON;

/**
 * 消息控制类
 * 
 * @author 吴理琪
 * 
 */
@Controller
@RequestMapping("messageAction")
public class MessageAction extends BaseAction {

	@Resource
	private IMessageService messageService;//消息服务类
	
	@Resource
	private IUserService userService;//用户服务类
	
	@Resource
	private ISmsService smsService;// 短信服务类
	
	@Resource
	private IOrgService orgService;//组织机构服务类

	@Resource
	private ICodeService codeService;//代码服务类
	/**
	 * app查询用户消息列表接口
	 * 
	 * @param userId ：接收人的用户标识
	 * @param msgType:2：系统消息  4：活动消息  6：推送消息
	 * @return
	 */
	@RequestMapping("/listMessage")
	@ResponseBody
	public Map<String, Object> listMessage(Integer userId, String msgType) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if("2".equals(msgType)){
				msgType="1406";//系统消息
			}else if("4".equals(msgType)){
				msgType="1407";//活动消息
			}
			Map record = new HashMap();
			record.put("userId",userId);//对应sys_message_send 表的接收人标识，即receiver_id
			record.put("msgType",msgType);
			record.put("status", "0");//只查未查看的
			
			List<Map> list = messageService.listMessage(record);
			if (list.size() > 0) {
				jsonMap.put("message", SystemConstant.MSG_SUCCESS);// 1、表示异常
				jsonMap.put("tips", "恭喜您，查询消息成功。");
				jsonMap.put("list", list);
			} else {
				jsonMap.put("message", SystemConstant.MSG_NO_DATA);// 9、表示无数据
				jsonMap.put("tips", "对不起，查询消息暂无数据。");
			}
		} catch (Exception e) {
			jsonMap.put("message", SystemConstant.MSG_EXCEPTION);// 0、表示异常
			jsonMap.put("tips", "对不起，查询消息失败。");
			log.info(e);
		}
		return jsonMap;
	}
	
	

	
	/**
	 * app删除消息接口
	 * 
	 * @param msgSendId:消息发送标识
	 * 
	 */
	@RequestMapping("/delMessageSend")
	@ResponseBody
	public Map<String, Object> delMessageSend(String msgSendId) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		int result=0;
		try {
			if(!StringUtil.isEmpty(msgSendId)){
				MessageSend messageSend =new MessageSend();
				messageSend.setMsgSendId(Integer.valueOf(msgSendId));
				messageSend.setStatus("4");//
				result=messageService.updMessageSend(messageSend);
				
			}else{
				jsonMap.put("message", "4");// 4、表示消息发送标识为空
				jsonMap.put("tips", "消息发送标识为空，删除消息失败。");
			}
			if(result>0){
				jsonMap.put("message", SystemConstant.MSG_SUCCESS);// 1、表示成功
				jsonMap.put("tips", "删除消息成功。");
			}else{
				jsonMap.put("message", SystemConstant.MSG_NO_DATA);// 9、表示失败
				jsonMap.put("tips", "删除消息失败。");
			}
		} catch (Exception e) {
			jsonMap.put("message", SystemConstant.MSG_EXCEPTION);// 0、表示异常
			jsonMap.put("tips", "删除消息失败。");
			log.info("app删除消息，更新状态异常："+e);
		}
		return jsonMap;
	}
	
	

	// *****************************//
	/** TODO
	 * 进入消息列表页面
	 * 
	 * @author 吴理琪
	 */

	@RequestMapping("/initMessage")
	public ModelAndView initMessage(String receiverId,String receiverName) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/message/listMessage.jsp");
		
		//当前登录用户
		User loginUser=(User)session.getAttribute("user");
		Integer orgId = loginUser.getOrgId();
		mv.addObject("orgId", orgId);//当前登录人员的所属门店
		//组织机构下拉列表
		List<Org> orgList=orgService.selectBySelective(new Org());
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		mv.addObject("orgMap", orgMap);
		//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
		List<Role>  roleList=(List<Role>)session.getAttribute("roleList");//角色列表
		for (Role role : roleList) {
			if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId==ButtonPermissionConstant.ROOT_ORG_ID) {// 超级管理员
				break;
			} else {
				mv.addObject("disabled", "disabled=\"disabled\"");
			}
		}
		
		
		Map<String, String>  msgTypeMap=new HashMap<String,String>();//消息类型
		Map param=new HashMap();
		param.put("type", "50000");// 50000  消息类型
		List<Map> smsTypeList=codeService.selectBySelective(param);
		for(Map oneMap:smsTypeList){
			msgTypeMap.put(oneMap.get("codeId")+"", oneMap.get("codeName")+"");
		}
		
		Map<String, String>  msgStatusMap=SystemConstant.getMsgStatusMap();
		mv.addObject("msgTypeMap", msgTypeMap);
		mv.addObject("msgStatusMap", msgStatusMap);
		mv.addObject("receiverId", receiverId); 
		mv.addObject("receiverName", receiverName); 
		return mv;

	}

	/**
	 * 用户列表页面异步请求
	 * 
	 * @author writchie 
	 */

	@RequestMapping("listMessageByPager")
	@ResponseBody
	public Map<String, Object> listMessageByPager(String curNo, String curSize,String sortname,String sortorder,
			String msgType, String msgTitle,String status,String receiverId, String fromCreateTime,
			String toCreateTime,String orgId) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		/************* 分页处理 ****************/
		int skip;
		int max;
		if (curNo == null || "".equals(curNo))
			curNo = "0";
		if (curSize == null || "".equals(curSize))
			curSize = SystemConstant.PAGER_CURSIZE;
		skip = Integer.parseInt(curNo);
		max = Integer.parseInt(curSize);
		int start = (skip - 1) * max;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("len", max);
		/************* 分页处理 ****************/
		if (!StringUtil.isEmpty(msgType)) {
			map.put("msgType", msgType);
		}

		if (!StringUtil.isEmpty(msgTitle)) {
			map.put("msgTitle", msgTitle);
		}
		if (!StringUtil.isEmpty(receiverId)) {
			map.put("receiverId", receiverId);
		}
		if (!StringUtil.isEmpty(status)) {
			map.put("status", status);
		}

		if (!StringUtil.isEmpty(fromCreateTime)) {
			map.put("fromCreateTime", fromCreateTime);
		}
		
		if (!StringUtil.isEmpty(toCreateTime)) {
			map.put("toCreateTime", toCreateTime);
		}
		if (!StringUtil.isEmpty(orgId)) {
			map.put("orgId", orgId);
		}

		//排序
		String orderbyStr = null;
		if (!StringUtil.isEmpty(sortname)) {
			if ("orgName".equals(sortname)) {
				orderbyStr = "order by orgName " + sortorder;
			}else if ("msgTitle".equals(sortname)) {
				orderbyStr = "order by msgTitle " + sortorder;
			} else if ("msgType".equals(sortname)) {
				orderbyStr = "order by msgType " + sortorder;
			} else if ("receiverName".equals(sortname)) {
				orderbyStr = "order by receiverName " + sortorder;
			} else if ("status".equals(sortname)) {
				orderbyStr = "order by status " + sortorder;
			} else if ("msgCreateTime".equals(sortname)) {
				orderbyStr = "order by msgCreateTime " + sortorder;
			}
		} else {
			orderbyStr = " order by s.msg_send_id desc";
		}
		map.put("orderBy", orderbyStr);
		
		List<Map> list = new ArrayList<Map>();
		list = messageService.selectByPager(map);

		for (Map oneMap : list) {
			Date d = (Date)oneMap.get("msgCreateTime");//使用消息体创建时间
			if(d!=null){
				oneMap.put("msgCreateTime", DateUtil.getFormatDate(d, ""));
				
			}
		/*	String msgTypeDb = oneMap.get("msgType")+"";
			if(!StringUtil.isEmpty(msgTypeDb)){
				oneMap.put("msgType", SystemConstant.getMsgTypeMap().get(msgTypeDb));
			}*/
			String statusTmp = oneMap.get("status")+"";
			if(!StringUtil.isEmpty(statusTmp)){
				oneMap.put("status", SystemConstant.getMsgStatusMap().get(statusTmp));
			}
		}
		int allSize = messageService.selectByPagerCount(map);

		jsonMap.put("msgType", msgType);
		jsonMap.put("msgTitle", msgTitle);
		jsonMap.put("status", status);
		jsonMap.put("fromCreateTime", fromCreateTime);
		jsonMap.put("toCreateTime", toCreateTime);
		jsonMap.put("receiverId", receiverId);
		
		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;

	}

	/**
	 * 查看消息明细
	 * @param userId
	 * @return
	 */
	@RequestMapping("viewMessage")
	public ModelAndView viewMessage(String msgId,String msgSendId,String receiverName,String msgTypeCn){
		// 变量声明
	    ModelAndView mv = new ModelAndView("/pages/message/viewMessage.jsp");
	    Map<String,Object> msgMap=new HashMap<String,Object>();
	    Message  message=new Message();
	    MessageSend messageSend=new MessageSend();
	    if(!StringUtil.isEmpty(msgId)){
	    	message=messageService.findMessageById(Integer.valueOf(msgId));
	    	msgMap.put("msgTitle", message.getMsgTitle());
	  	    msgMap.put("msgType", msgTypeCn);
		    msgMap.put("msgContent",message.getMsgContent());
		    Date createTime = message.getMsgCreateTime();
	 	    if(createTime!=null){
	 	    	msgMap.put("createTime",DateUtil.getFormatDate(createTime, ""));
	 	    }
	    }
	    if(!StringUtil.isEmpty(msgSendId)){
	    	messageSend=messageService.findMessageSendById(Integer.valueOf(msgSendId));
	 	    msgMap.put("status",SystemConstant.getMsgStatusMap().get(messageSend.getStatus()));
	    }
	  
	    msgMap.put("receiverName",StringUtil.trimNull(receiverName));
	    mv.addObject("msgMap", msgMap);
	    return mv;
	}
	
	/**
	 * 初始进入推送消息页面
	 * @return
	 */
	@RequestMapping("/toAddMessage")
	public ModelAndView toAddMessage() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/message/addMessage.jsp");
		Map<String, String>  msgTypeMap=new HashMap<String,String>();
		
		Map param=new HashMap();
		param.put("type", "50000");// 50000  消息类型
		List<Map> smsTypeList=codeService.selectBySelective(param);
		for(Map oneMap:smsTypeList){
			msgTypeMap.put(oneMap.get("codeId")+"", oneMap.get("codeName")+"");
		}
		msgTypeMap.remove("1408");//屏蔽系统短信
		mv.addObject("msgTypeMap", msgTypeMap);
		return mv;
	}
	/**
	 * 推送消息及保存至数据库
	 * @param message :消息内容
	 * @param receiverIds:接收人的userId字符串，以逗号隔开
	 * 
	 * @author wRitchie
	 * @return
	 */
	@RequestMapping("/addMessage")
	public ModelAndView addMessage(Message message,String receiverIds){
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/message/addMessage.jsp");
		Map map=new HashMap();
		String flag="error";
		try {
			//1、增加一条消息内容
			User user=(User)session.getAttribute("user");
			Integer userId = user.getUserId();
			Integer orgId=user.getOrgId();
			Date createTime = new Date();
			message.setMsgCreateTime(createTime);//发送时间
			message.setUserId(userId);//当前操作人
			int result=messageService.insertMessage(message);
			Integer msgId= message.getMsgId();
			String[] receiverIdArray=receiverIds.split(",");
			if(result>0){
				PushMessage pushMessage=new PushMessage();//推送内容JSON串对象
				pushMessage.setMsgId(message.getMsgId());// 消息标识
				pushMessage.setMsgTitle(message.getMsgTitle());// 消息标题
				pushMessage.setMsgContent(message.getMsgContent());// 消息内容
				pushMessage.setCreateTime(createTime);//消息创建时间
				pushMessage.setMsgSendId(userId);// 消息发送标识
				String pushMessageJson="";
				
				for(String receiverId:receiverIdArray){
					System.out.println("#########推送给:"+receiverId);
					MessageSend messageSend=new MessageSend();
					if("0".equals(receiverId)){//0  表示发送给所有的人
						List<User> userList=new ArrayList<User>();
						userList=userService.findUser(new User());
						for(User u:userList){
							messageSend.setReceiverId(u.getUserId());//消息接收人标识
							messageSend.setMsgId(msgId);//消息标识
							messageSend.setSenderId(userId);//消息发送者标识
							messageSend.setCreateTime(createTime);//消息发送时间
							messageSend.setStatus("0");//消息状态  未查看
							messageSend.setOrgId(orgId);//所属组织机构
							messageSend.setRemark1("push");
							messageService.insertMessageSend(messageSend);
						}
						
						//调用消息推送方法推送 私家车
						GetuiUtil.initConfig("4");
						pushMessageJson=JSON.toJSONString(pushMessage);
						map.put("msgContentJson", pushMessageJson);
						map.put("msgContent", message.getMsgContent());
						GetuiUtil.pushTransmissionTemplateToApp(map);
						
						//调用消息推送方法推送 出租车
						GetuiUtil.initConfig("2");
						pushMessageJson=JSON.toJSONString(pushMessage);
						map.put("msgContentJson", pushMessageJson);
						map.put("msgContent", message.getMsgContent());
						GetuiUtil.pushTransmissionTemplateToApp(map);
						
						//调用消息推送方法推送 乘客端
						GetuiUtil.initConfig("6");
						pushMessageJson=JSON.toJSONString(pushMessage);
						map.put("msgContentJson", pushMessageJson);
						map.put("msgContent", message.getMsgContent());
						GetuiUtil.pushTransmissionTemplateToApp(map);
					}else{// 发送给指定的某个用户
						Integer receiverIdInt = Integer.valueOf(receiverId);
						messageSend.setReceiverId(receiverIdInt);//消息接收人标识
						messageSend.setMsgId(msgId);//消息标识
						messageSend.setSenderId(userId);//消息发送者标识
						messageSend.setCreateTime(createTime);//消息发送时间
						messageSend.setStatus("0");//消息状态  未查看
						messageSend.setOrgId(orgId);//所属组织机构
						messageSend.setRemark1("push");
						messageService.insertMessageSend(messageSend);
						
						//调用消息推送方法推送
						pushMessage.setReceiverId(receiverIdInt);//消息接收人标识
						pushMessage.setStatus(messageSend.getStatus());//消息状态  未查看
						
						//用户绑定关系表获获取个推所有的clientId,即第三方标识openId
						UserBinding userBinding=new UserBinding();
						userBinding.setUserId(receiverIdInt);
						List<UserBinding>  userBindingList=userService.listUserBinding(userBinding);
						for(UserBinding ub:userBindingList){
							pushMessageJson=JSON.toJSONString(pushMessage);
							System.out.println(receiverIdInt+"##########BindingType:"+ub.getBindingType());
							System.out.println("##########clientId:"+ub.getOpenId());
							
							GetuiUtil.initConfig(ub.getBindingType());//根据绑定种类，初始化个推配置信息
							map.put("msgContentJson", pushMessageJson);
							map.put("msgContent", message.getMsgTitle());
							map.put("clientId", ub.getOpenId());
							GetuiUtil.pushTransmissionTemplateMsg(map);
						}
					
					}
				
				}
				System.out.println("###推送消息串："+pushMessageJson);
			}
			flag="success";
		} catch (Exception e) {
			log.info("发送消息失败："+e);
		}
		mv.addObject("message", flag);
		return mv;
	}
	
	/**
	 * 进入短信发送管理列表页
	 * 
	 * @return
	 */
	@RequestMapping("/initSms")
	public ModelAndView initSms() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/message/listSms.jsp");
		
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		User loginUser=(User)session.getAttribute("user");
		Integer orgId = loginUser.getOrgId();
		mv.addObject("orgId", orgId);//当前登录人员的所属门店
		mv.addObject("orgMap", orgMap);
		
		//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
		List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
		for (Role role : roleList) {
			if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId==ButtonPermissionConstant.ROOT_ORG_ID) {//超级管理员
				break;
			}else{
				mv.addObject("disabled", "disabled=\"disabled\"");
			}
		}
		return mv;
	}

	/**
	 * 进入短信发送页面
	 * @return
	 */
	@RequestMapping("/toSendSms")
	public ModelAndView toSendSms(String userIds,String loginNames) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/message/addSms.jsp");
		Map<String, String>  smsTypeMap=new HashMap<String,String>();//消息类型
		Map param=new HashMap();
		param.put("type", "50000");// 50000  消息类型
		List<Map> smsTypeList=codeService.selectBySelective(param);
		for(Map oneMap:smsTypeList){
			smsTypeMap.put(oneMap.get("codeId")+"", oneMap.get("codeName")+"");
		}
		smsTypeMap.remove("1408");//屏蔽系统短信
		mv.addObject("smsTypeMap", smsTypeMap);
		mv.addObject("userIds", userIds);
		mv.addObject("loginNames", loginNames);
		
		return mv;
	}

	/**
	 * 
	 * @param message 消息内容体
	 * @param receiverIds 接收人用户标识
	 * @param loginNames 接收人手机号码
	 * @return
	 */
	@RequestMapping("/addSms")
	public ModelAndView addSms(Message message,String receiverIds,String loginNames){
		// 变量声明
		ModelAndView mv = new ModelAndView("/app118/messageAction/toSendSms");
		mv.addObject("userIds", receiverIds);
		mv.addObject("loginNames", loginNames);
		String msg=message.getMsgContent();
		String flag="error";
		try {
			//1、增加一条消息内容
			User user=(User)session.getAttribute("user");
			Integer userId = user.getUserId();
			Integer orgId=user.getOrgId();
			Date createTime = new Date();
			message.setMsgCreateTime(createTime);//发送时间
			message.setUserId(userId);//当前操作人
			String msgType=message.getMsgType();//短信类型
			Code code=codeService.selectByPrimaryKey(Integer.valueOf(msgType));
			message.setMsgTitle(code.getCodeName());//消息标题
	
			int result=messageService.insertMessage(message);//增加消息内容记录
			Integer msgId= message.getMsgId();
			String[] receiverIdArray=receiverIds.split(",");
			String receiverId=null;
			
			String[] loginNameArray=loginNames.split(",");
			String loginName=null;
			if(result>0){
				for(int i=0;i<receiverIdArray.length;i++){
					receiverId=receiverIdArray[i];
					loginName=loginNameArray[i];
					System.out.println("#########短信发送给用户标识:"+receiverId+"\t手机号码"+loginName);
					MessageSend messageSend=new MessageSend();
					//发送给指定的某个用户
					messageSend.setMsgId(msgId);//消息标识
					Integer receiverIdInt = Integer.valueOf(receiverId);
					messageSend.setReceiverId(receiverIdInt);//消息接收人标识
					messageSend.setSenderId(userId);//消息发送者标识
					messageSend.setCreateTime(createTime);//消息发送时间
					messageSend.setStatus("0");//消息状态  未查看
					messageSend.setRemark1("sms");//短信
					messageSend.setOrgId(orgId);//所属组织机构
					result=messageService.insertMessageSend(messageSend);
					if(result>0){
						//调用短信接口
						smsService.sendSms(loginName, msg,"3",userId);
						flag="success";
					}
				}
				
			}
			
		} catch (Exception e) {
			log.info("发送消息失败："+e);
		}
		mv.addObject("message", flag);
		return mv;
	}
	
	//直接发送
	/**
	 * 进入直接发送 短信发送页面
	 * @return
	 */
	@RequestMapping("/toSendSmsByPhone")
	public ModelAndView toSendSmsByPhone() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/message/addSmsByPhone.jsp");
		Map<String, String>  smsTypeMap=new HashMap<String,String>();
		Map param=new HashMap();
		param.put("type", "50000");// 50000  消息类型
		List<Map> smsTypeList=codeService.selectBySelective(param);
		for(Map oneMap:smsTypeList){
			smsTypeMap.put(oneMap.get("codeId")+"", oneMap.get("codeName")+"");
		}
		mv.addObject("smsTypeMap", smsTypeMap);
		return mv;
	}

	/**
	 * 直接发送
	 * 
	 * @param message 消息内容体
	 * @param receiverIds 接收人用户标识
	 * @param loginNames 接收人手机号码
	 * @return
	 */
	@RequestMapping("/addSmsByPhone")
	public ModelAndView addSmsByPhone(Message message,String phoneNumbers){
		// 变量声明
		ModelAndView mv = new ModelAndView("/app118/messageAction/toSendSmsByPhone");
		
		String msg=message.getMsgContent();
		String flag="error";
		try {
			//1、增加一条消息内容
			User user=(User)session.getAttribute("user");
			Integer userId = user.getUserId();
//			String msgType="9";//短信类型
			int result=0;
			if(!StringUtil.isEmpty(phoneNumbers)){
				String []loginNames=phoneNumbers.split(",");
				for(String phoneNumber:loginNames){
					System.out.println("#########往"+phoneNumber+"发送短信。。。");
//					Message oneMessage=new Message();
//					oneMessage.setMsgTitle(phoneNumber+"_营销短信");//标题
//					oneMessage.setMsgContent(msg);//内容
//					oneMessage.setMsgType(msgType);//短信类型
//					oneMessage.setMsgCreateTime(new Date());//发送时间
//					oneMessage.setUserId(userId);//当前操作人
//					result=messageService.insertMessage(oneMessage);//增加消息内容记录
//					if(result>0){
//					}
					smsService.sendSms(phoneNumber, msg,"3",userId);
					flag="success";
				}
				
			}
		} catch (Exception e) {
			flag="error";
			log.info("发送消息失败："+e);
		}
		mv.addObject("message", flag);
		return mv;
	}
	
	
	////////////////////////
	/**
	 * 进入消息推送初始页
	 * @return
	 */
	@RequestMapping("/initMsgPush")
	public ModelAndView initMsgPush() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/message/listMsgPush.jsp");
		Map param=new HashMap();
		param.put("type", 6);// 6:汽车品牌 
		List<Map> brandList=codeService.selectBySelective(param);
		mv.addObject("brandList", brandList);
		
		//派单员角色下所有的人员（即所有的销售人员）
		List<User> userList=userService.selectUserByRoleId(ButtonPermissionConstant.SALESUSERLIST_SALES);//13 派单员角色
		//当前登录用户
		User loginUser=(User)session.getAttribute("user");
		Integer orgId = loginUser.getOrgId();
		mv.addObject("orgId", orgId);//当前登录人员的所属门店
		//组织机构下拉列表
		List<Org> orgList=orgService.selectBySelective(new Org());
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		mv.addObject("orgMap", orgMap);
		mv.addObject("userList", userList);
		//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
		List<Role>  roleList=(List<Role>)session.getAttribute("roleList");//角色列表
		for (Role role : roleList) {
			if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId==ButtonPermissionConstant.ROOT_ORG_ID) {// 超级管理员
				break;
			} else {
				mv.addObject("disabled", "disabled=\"disabled\"");
			}
		}
		return mv;
	}

	/**
	 * 消息推送异步查询
	 * @param curNo
	 * @param curSize
	 * @param sortname
	 * @param sortorder
	 * @param orgId
	 * @param saleUserId
	 * @param rechargeEndTime
	 * @param hepaExpiryDate
	 * @param carBrand
	 * @param deviceName
	 * @param realName
	 * @param loginName
	 * @return
	 */
	@RequestMapping("listMsgPushByPager")
	@ResponseBody
	public Map<String,Object> listMsgPushByPager(String curNo,String curSize,String sortname,
			String sortorder,String orgId,String lastLoginTime,String realName,String loginName){
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		/************* 分页处理 ****************/
		int skip;
		int max;
		if (curNo == null || "".equals(curNo))
			curNo = "0";
		if (curSize == null || "".equals(curSize))
			curSize = SystemConstant.PAGER_CURSIZE;
		skip = Integer.parseInt(curNo);
		max = Integer.parseInt(curSize);
		int start=(skip-1)*max;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", start);
		map.put("len", max);
		/************* 分页处理 ****************/
		if(!StringUtil.isEmpty(orgId)){
			map.put("orgId", orgId);
		}
		
		if(!StringUtil.isEmpty(realName)){
			map.put("realName", realName);
		}
		if(!StringUtil.isEmpty(loginName)){
			map.put("loginName",loginName);
		}
		
		String orderbyStr = null;
		if (!StringUtil.isEmpty(sortname)) {
			if ("orgName".equals(sortname)) {//所属门店排序
				orderbyStr = "order by orgName " + sortorder;
			}else if ("realName".equals(sortname)) {//客户姓名 排序
				orderbyStr = "order by realName " + sortorder;
			}else if ("loginName".equals(sortname)) {//客户手机号 排序
				orderbyStr = "order by loginName " + sortorder;
			}else if ("lastLoginTime".equals(sortname)) {//客户最后访问时间排序
				orderbyStr = "order by lastLoginTime " + sortorder;
			}
		} else {
			orderbyStr = "order by lastLoginTime desc, u.user_id desc";
		}
		
		map.put("orderBy", orderbyStr);
		List<Map> list=new ArrayList<Map>();
		list=userService.selectUserAllByPager(map);
		for(Map oneMap:list){
			String color="#008000";
			Date currentTime=new Date();
			//客户最后访问时间
			Date d0=(Date)oneMap.get("lastLoginTime");
			if(d0!=null){
				oneMap.put("lastLoginTime",DateUtil.getFormatDate(d0, ""));//客户最后访问时间
				int result=DateUtil.compare(d0, DateUtil.subtractDate(currentTime,SystemConstant.INACTIVE_INTERVAL));
				if(result<0){//过有效期
					color="red";
				}else{
					color="#008000";
				}
			}else{
				oneMap.put("lastLoginTime","---");
				color="red";
			}
			oneMap.put("lastLoginTimeColor", color);
		}
		
		int allSize =userService.selectUserAllByPagerCount(map);
		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;
	}
	
	@RequestMapping("/toAddMsgPush")
	public ModelAndView toAddMsgPush(String userIds,String loginNames,String realNames) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/message/addMsgPush.jsp");
		Map<String, String>  smsTypeMap=new HashMap<String,String>();
		
		Map param=new HashMap();
		param.put("type", "50000");// 50000  消息类型
		List<Map> smsTypeList=codeService.selectBySelective(param);
		for(Map oneMap:smsTypeList){
			smsTypeMap.put(oneMap.get("codeId")+"", oneMap.get("codeName")+"");
		}
		smsTypeMap.remove("1408");//屏蔽系统短信
		mv.addObject("smsTypeMap", smsTypeMap);
		mv.addObject("userIds", userIds);
		mv.addObject("loginNames", loginNames);
		mv.addObject("realNames", realNames);
		return mv;
	}
	
	
	@RequestMapping("/addMsgPush")
	public ModelAndView addMsgPush(Message message,String receiverIds,String loginNames,String sendTypeCheckbox){
		// 变量声明
		ModelAndView mv = new ModelAndView("/app118/messageAction/toAddMsgPush");
		mv.addObject("userIds", receiverIds);
		mv.addObject("loginNames", loginNames);
		String msg=message.getMsgContent()+SystemConstant.COMPANY_SIGNATURE;//增加公司签名
		message.setMsgContent(msg);
		String flag="error";
		String []sendTypeArray=sendTypeCheckbox.split(",");
		
		//1、增加一条消息内容
		User user=(User)session.getAttribute("user");
		Integer userId = user.getUserId();
		Integer orgId=user.getOrgId();//所属组织机构
		Date createTime = new Date();
		message.setMsgCreateTime(createTime);//发送时间
		message.setUserId(userId);//当前操作人
		String msgType=message.getMsgType();//短信类型
		Code code=codeService.selectByPrimaryKey(Integer.valueOf(msgType));
		message.setMsgTitle(code.getCodeName());//消息标题
		int result=messageService.insertMessage(message);//增加消息内容记录
		Integer msgId= message.getMsgId();
		
		for(String sendType:sendTypeArray){
			System.out.println("####sendType："+sendType);
			if("push".equals(sendType)){//推送
				Map map=new HashMap();
				try {
					String[] receiverIdArray=receiverIds.split(",");
					if(result>0){
						PushMessage pushMessage=new PushMessage();//推送内容JSON串对象
						pushMessage.setMsgId(message.getMsgId());// 消息标识
						pushMessage.setMsgTitle(message.getMsgTitle());// 消息标题
						pushMessage.setMsgContent(message.getMsgContent());// 消息内容
						pushMessage.setCreateTime(createTime);//消息创建时间
						pushMessage.setMsgSendId(userId);// 消息发送标识
						String pushMessageJson="";
						
						for(String receiverId:receiverIdArray){
							log.info("#########推送给:"+receiverId);
							MessageSend messageSend=new MessageSend();
							// 发送给指定的某个用户
							Integer receiverIdInt = Integer.valueOf(receiverId);
							messageSend.setReceiverId(receiverIdInt);//消息接收人标识
							messageSend.setMsgId(msgId);//消息标识
							messageSend.setSenderId(userId);//消息发送者标识
							messageSend.setCreateTime(createTime);//消息发送时间
							messageSend.setStatus("0");//消息状态  未查看
							messageSend.setOrgId(orgId);//所属组织机构
							messageSend.setRemark1(sendType);
							messageService.insertMessageSend(messageSend);
							
							//调用消息推送方法推送
							pushMessage.setReceiverId(receiverIdInt);//消息接收人标识
							pushMessage.setStatus(messageSend.getStatus());//消息状态  未查看
							
							//用户绑定关系表获获取个推所有的clientId,即第三方标识openId
							UserBinding userBinding=new UserBinding();
							userBinding.setUserId(receiverIdInt);
							List<UserBinding>  userBindingList=userService.listUserBinding(userBinding);
							for(UserBinding ub:userBindingList){
								pushMessageJson=JSON.toJSONString(pushMessage);
								log.info(receiverIdInt+"##########BindingType:"+ub.getBindingType());
								log.info("##########clientId:"+ub.getOpenId());
								
								GetuiUtil.initConfig(ub.getBindingType());//根据绑定种类，初始化个推配置信息
								map.put("msgContentJson", pushMessageJson);
								map.put("msgContent", message.getMsgTitle());
								map.put("clientId", ub.getOpenId());
								GetuiUtil.pushTransmissionTemplateMsg(map);
							}
						}
						log.info("###推送消息串："+pushMessageJson);
					}
					flag="success";
				} catch (Exception e) {
					log.info("消息推送，推送消息失败："+e);
				}
			}else if("sms".equals(sendType)){//短信
				try {
					String[] receiverIdArray=receiverIds.split(",");
					String receiverId=null;
					String[] loginNameArray=loginNames.split(",");
					String loginName=null;
					if(result>0){
						for(int i=0;i<receiverIdArray.length;i++){
							receiverId=receiverIdArray[i];
							loginName=loginNameArray[i];
							log.info("#########短信发送给用户标识:"+receiverId+"\t手机号码："+loginName);
							MessageSend messageSend=new MessageSend();
							//发送给指定的某个用户
							messageSend.setMsgId(msgId);//消息标识
							Integer receiverIdInt = Integer.valueOf(receiverId);
							messageSend.setReceiverId(receiverIdInt);//消息接收人标识
							messageSend.setSenderId(userId);//消息发送者标识
							messageSend.setCreateTime(createTime);//消息发送时间
							messageSend.setStatus("0");//消息状态  未查看
							messageSend.setOrgId(orgId);//所属组织机构
							messageSend.setRemark1(sendType);
							result=messageService.insertMessageSend(messageSend);
							if(result>0){
								//调用短信接口
								smsService.sendSms(loginName, msg,"3",userId);
								flag="success";
							}
						}
						
					}
					
				} catch (Exception e) {
					log.info("消息推送，发送短信失败："+e);
				}
			}
		}
		mv.addObject("message", flag);
		return mv;
	}
	
}
