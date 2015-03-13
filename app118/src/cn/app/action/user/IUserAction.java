package cn.app.action.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.AESUtil;
import cn.app118.framework.util.GenerateIdUtil;
import cn.app118.framework.util.GlobalUtil;
import cn.app118.framework.util.MD5Util;
import cn.app118.model.Log;
import cn.app118.model.User;
import cn.app118.service.log.ILogService;
import cn.app118.service.message.ISmsService;
import cn.app118.service.user.IUserService;

/**
 * 用户接口控制类
 * 
 * @author 吴理琪
 * 
 */

@Controller
@RequestMapping("iUserAction")
public class IUserAction extends BaseAction {
	@Resource
	private IUserService userService;// 用户服务类
	
	@Resource
	private ILogService logService;//日志服务类
	
	@Resource
	private ISmsService smsService;// 短信服务类
	
	
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> login(String loginName, String password) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			log.info(loginName);
			User u = new User();
			u.setLoginName(loginName);

			String pwdTmp = password;
			log.info("####密码：" + pwdTmp);
			String sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY).substring(0,
					16);
			String pwdEncrypt = AESUtil.encrypt(pwdTmp, sKey).substring(0, 16);
			log.info("####加密后密码：" + pwdEncrypt);

			List<User> exitUsers = userService.findUser(u);
			if (exitUsers.size() == 1) {
				User existUser=exitUsers.get(0);
				if (pwdEncrypt.equals(existUser.getPassword())) {
					jsonMap.put("message", "1");// 1、表示登录成功。
					jsonMap.put("tips", "恭喜您，登录成功。");
					jsonMap.put("user", exitUsers.get(0));
					
					Log opLog=new Log();//日志
					opLog.setUserId(existUser.getUserId());
					opLog.setLoginName(existUser.getLoginName());
					opLog.setLogType("3");// app登录
					opLog.setIpAddress(GlobalUtil.getIp(request));
					opLog.setOpContent("{\"message\":\"app登录成功\"}");
					opLog.setOpTime(new Date());
					opLog.setOrgId(existUser.getOrgId());
					logService.insert(opLog);

					
				} else {
					jsonMap.put("message", "2");// 2、表示用户密码错误。
					jsonMap.put("tips", "对不起，您的用户名或密码错误。");
				}
				return jsonMap;
			} else {
				jsonMap.put("message", "3");// 3、表示用户不存在。
				jsonMap.put("tips", "对不起，您的用户名或密码错误。");
			}
		} catch (Exception e) {
			jsonMap.put("message", "0");// 0、表示异常
			jsonMap.put("tips", "对不起，登录失败。");
			log.info(e);
		}
		return jsonMap;
	}

	/**
	 * 注册--获取手机验证码
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/getVerificationCode")
	@ResponseBody
	public Map<String, Object> getVerificationCode(String phoneNumber) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String verificationCode = GenerateIdUtil.getRandomNumber(6);
			String msg = "您的验证码为：" + verificationCode
					+ "，请输入验证码完成验证，（请勿向任何人提供您收到的验证码）【每日互联】";
			// 调用短信发送
			log.info(phoneNumber + "#######验证码：" + verificationCode);
			User loginUser=(User)session.getAttribute("user");
			Integer userId=0;
			if(loginUser!=null){
				userId=loginUser.getUserId();
			}
			smsService.sendSms(phoneNumber, msg,"1",userId);
			// session.setAttribute(phoneNumber, verificationCode);
			// log.info("#######session验证码"+session.getAttribute(phoneNumber));
			jsonMap.put("verificationCode", verificationCode);
			jsonMap.put("beginTime", System.currentTimeMillis());
			jsonMap.put("message", "1");// 1、表示发送验证码成功。
			jsonMap.put("tips", "手机验证码已发送到您的手机。");
		} catch (Exception e) {
			jsonMap.put("message", "0");// 0、表示异常
			jsonMap.put("tips", "手机验证码已发失败。");
			log.info(e);
		}
		return jsonMap;
	}
}
