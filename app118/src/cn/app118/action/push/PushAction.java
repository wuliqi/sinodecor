/**
 * @(#)UserAction.java	09/02/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-02
 */
package cn.app118.action.push;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 消息推送控制类
 * 
 * @author wRitchie
 *
 */
@Controller
@RequestMapping("pushAction")
public class PushAction {

	@RequestMapping("callbackPush")
	@ResponseBody
	/**
	 * 个推消息回调
	 * 
	 * @return
	 */
	public Map<String,Object> callbackPush(){//TODO
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		String CID = "c10f9efb1b0b55fd015c533ddea60c38";
		System.out.println("########个推消息回调");
//		IGtPush push = new IGtPush(SystemConstant.IGETUI_HOST, SystemConstant.IGETUI_APPKEY, SystemConstant.IGETUI_MASTER);
//	    IQueryResult abc = push.getClientIdStatus(SystemConstant.IGETUI_APPID, CID);
//	    System.out.println("###个推回调信息:"+abc.getResponse());
		return jsonMap;
		
	}

}
