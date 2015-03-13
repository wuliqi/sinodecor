/**
 * @(#)UserAction.java	05/15/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-15
 */
package cn.app118.action.user;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.ButtonPermissionConstant;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.AESUtil;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.GenerateIdUtil;
import cn.app118.framework.util.GlobalUtil;
import cn.app118.framework.util.MD5Util;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.Code;
import cn.app118.model.Log;
import cn.app118.model.Org;
import cn.app118.model.Role;
import cn.app118.model.User;
import cn.app118.model.UserBinding;
import cn.app118.model.UserGpsInfo;
import cn.app118.service.code.ICodeService;
import cn.app118.service.log.ILogService;
import cn.app118.service.message.ISmsService;
import cn.app118.service.org.IOrgService;
import cn.app118.service.user.IUserService;

import com.alibaba.fastjson.JSON;

/**
 * 用户控制类
 * 
 * @author 吴理琪
 * 
 */
@Deprecated
@Controller
@RequestMapping("userAction")
public class UserAction extends BaseAction {
	private Logger log = Logger.getLogger(this.getClass());// 日志
	@Resource
	private IUserService userService;// 用户服务类

	@Resource
	private ISmsService smsService;// 短信服务类
	
	
	@Resource
	private ICodeService codeService;//代码管理服务类
	
	@Resource
	private IOrgService orgService;//组织机构服务类
	
	@Resource
	private ILogService logService;//日志服务类

	/**
	 * 新注册接口，含手机号
	 * 
	 * @param map loginName:登录名   password:密码  phoneNumber:手机号码    验证码时间戳:beginTime
	 * 
	 * @return
	 */
	@RequestMapping("/registerWithPhone")
	@ResponseBody
	public Map<String, Object> registerWithPhone(@RequestParam Map map) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		User user = new User();
		String loginName=map.get("loginName")+"";
		String password=map.get("password")+"";
		String phoneNumber=map.get("phoneNumber")+"";
		String userType=map.get("userType")+"";
		long beginTime=0;
		String time=map.get("beginTime")+"";
		beginTime=Long.valueOf(time);
		try {
			
			if (StringUtil.isEmpty(loginName)) {
				jsonMap.put("message", "3");// 3、表示用户名为空。
				jsonMap.put("tips", "对不起，用户名不能为空。");
				return jsonMap;
			}
			if (StringUtil.isEmpty(password)) {
				jsonMap.put("message", "3");// 3、表示密码为空。
				jsonMap.put("tips", "对不起，密码不能为空。");
				return jsonMap;
			}
			if (StringUtil.isEmpty(phoneNumber)) {
				jsonMap.put("message", "3");// 3、表示手机号码为空。
				jsonMap.put("tips", "对不起，手机号码不能为空。");
				return jsonMap;
			}
			if (StringUtil.isEmpty(time)) {
				jsonMap.put("message", "3");// 3、表示验证码时间为空。
				jsonMap.put("tips", "对不起，验证码时间不能为空。");
				return jsonMap;
			}
		
			long currentTime = System.currentTimeMillis();
			if (currentTime - beginTime > SystemConstant.MAX_TIME) {// 验证码无效
				jsonMap.put("message", "2");// 2、表示注册失败。
				jsonMap.put("tips", "对不起，验证码已过期，注册失败。");
				return jsonMap;
			}
			
			User u = new User();
			u.setLoginName(loginName);
			List<User> exitUsers = userService.findUser(u);
			if (exitUsers.size() >= 1) {//判断登录名是否存在
				jsonMap.put("message", "2");// 2、表示用户已存在。
				jsonMap.put("tips", "对不起，用户已存在。");
				return jsonMap;
			} else {
				user.setLoginName(loginName);
				user.setPhoneNumber(phoneNumber);
				user.setCreateTime(new Date());
				user.setUserType(userType);//用户类型
				String pwdTmp = password;
				log.info("####密码：" + pwdTmp);
				String sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY).substring(0, 16);
				String pwdEncrypt = AESUtil.encrypt(pwdTmp, sKey).substring(0,16);
				log.info("####加密后密码：" + pwdEncrypt);
				user.setPassword(pwdEncrypt);
				String result = userService.addUser(user);
				if (result.equals("1")) {
					jsonMap.put("message", result);// 1、表示注册成功
					jsonMap.put("tips", "恭喜您，注册成功。");
					jsonMap.put("user", user);
				} else {
					jsonMap.put("message", result);// 1、表示注册成功
					jsonMap.put("tips", "对不起，注册失败。");
					jsonMap.put("user", user);
				}

			}
		} catch (Exception e) {
			jsonMap.put("message", "0");// 0、表示异常
			jsonMap.put("tips", "对不起，注册失败。");
			log.info(e);
		}
		return jsonMap;
	}
	
	
	/**
	 * 注册
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	@Deprecated
	public Map<String, Object> register(String loginName, String password) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		User user = new User();
		try {
			if (StringUtil.isEmpty(loginName)) {
				jsonMap.put("message", "3");// 3、表示用户名为空。
				jsonMap.put("tips", "对不起，用户名为空。");
				return jsonMap;
			}
			log.info(loginName);
			User u = new User();
			u.setLoginName(loginName);
			List<User> exitUsers = userService.findUser(u);
			if (exitUsers.size() >= 1) {
				jsonMap.put("message", "2");// 2、表示用户已存在。
				jsonMap.put("tips", "对不起，用户已存在。");
				return jsonMap;
			} else {
				user.setLoginName(loginName);
				user.setCreateTime(new Date());
				String pwdTmp = password;
				log.info("####密码：" + pwdTmp);
				String sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY)
						.substring(0, 16);
				String pwdEncrypt = AESUtil.encrypt(pwdTmp, sKey).substring(0,
						16);
				log.info("####加密后密码：" + pwdEncrypt);
				user.setPassword(pwdEncrypt);
				String result = userService.addUser(user);
				if (result.equals("1")) {
					jsonMap.put("message", result);// 1、表示注册成功
					jsonMap.put("tips", "恭喜您，注册成功。");
					jsonMap.put("user", user);
				} else {
					jsonMap.put("message", result);// 1、表示注册成功
					jsonMap.put("tips", "对不起，注册失败。");
					jsonMap.put("user", user);
				}
				
			}
		} catch (Exception e) {
			jsonMap.put("message", "0");// 0、表示异常
			jsonMap.put("tips", "对不起，注册失败。");
			log.info(e);
		}
		return jsonMap;
	}

	/**
	 * 登录
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> login(String loginName, String password) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			log.info(loginName);
			User u = new User();
			u.setLoginName(loginName);

			String pwdTmp = password;
			//log.info("####密码：" + pwdTmp);
			String sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY).substring(0,
					16);
			String pwdEncrypt = AESUtil.encrypt(pwdTmp, sKey).substring(0, 16);
			//log.info("####加密后密码：" + pwdEncrypt);

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

	/**
	 * 注册--绑定用户手机号
	 * 
	 * @param params
	 *            用户信息json串 ：userId 用户标识,phoneNuber：手机号 临时采用realName字段当作验证码字段
	 * @return
	 */
	@RequestMapping("/bindingPhone")
	@ResponseBody
	@Deprecated
	public Map<String, Object> bindingPhone(Integer userId, String phoneNumber,
			long beginTime) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			User user = new User();
			user.setUserId(userId);
			// log.info(phoneNumber+"####### bindingPhone  session验证码"+session.getAttribute(phoneNumber));
			long currentTime = System.currentTimeMillis();
			if (currentTime - beginTime < SystemConstant.MAX_TIME) {// 验证码有效
				user.setPhoneNumber(phoneNumber);
				userService.updateByPrimaryKeySelective(user);
				jsonMap.put("message", "1");// 1、表示修改成功。
				jsonMap.put("tips", "恭喜您，手机绑定成功。");
			} else {
				jsonMap.put("message", "2");// 2、表示绑定失败。
				jsonMap.put("tips", "对不起，验证码已过期，手机绑定失败。");
			}

		} catch (Exception e) {
			jsonMap.put("message", "0");// 0、表示异常
			jsonMap.put("tips", "对不起，手机绑定失败。");
			log.info(e);
		}
		return jsonMap;
	}

	/**
	 * 找回用户密码--获验证码
	 * 
	 * @param loginName
	 * @return
	 */
	@RequestMapping("/findPasswordVerificationCode")
	@ResponseBody
	public Map<String, Object> findPasswordVerificationCode(String loginName) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			User tmpUser = new User();
			tmpUser.setLoginName(loginName);
			List<User> exitUsers = userService.findUser(tmpUser);
			if (exitUsers.size() == 1) {
				User u = exitUsers.get(0);
				String phoneNumber = u.getPhoneNumber();
				if (!StringUtil.isEmpty(phoneNumber)) {
					String verificationCode = GenerateIdUtil.getRandomNumber(6);
					String msg = "您的验证码为：" + verificationCode
							+ "，请输入验证码完成验证，（请勿向任何人提供您收到的验证码）【每日互联】";
					// 调用短信发送
					User loginUser=(User)session.getAttribute("user");
					Integer userId=0;
					if(loginUser!=null){
						userId=loginUser.getUserId();
					}
					smsService.sendSms(phoneNumber, msg,"1",userId);
					// session.setAttribute(phoneNumber, verificationCode);
					jsonMap.put("user", u);// 用户信息返回
					jsonMap.put("beginTime", System.currentTimeMillis());// 当前时间戳返回
					jsonMap.put("verificationCode", verificationCode);// 将验证码返回
					jsonMap.put("message", "1");// 1、表示手机验证码已发送到您的手机。
					jsonMap.put("tips", "手机验证码已发送到您的手机。");
				} else {
					jsonMap.put("message", "3");// 3、用户名未绑定手机号，表示找回用户密码失败。
					jsonMap.put("tips", "对不起，用户名未绑定手机号，找回用户密码失败。");
				}
			} else {
				jsonMap.put("message", "2");// 2、表示用户名不存在,找回用户密码失败。
				jsonMap.put("tips", "对不起，用户名不存在或不唯一，找回用户密码失败。");

			}
		} catch (Exception e) {
			jsonMap.put("message", "0");// 0、表示异常
			jsonMap.put("tips", "对不起，找回用户密码失败。");
			log.info(e);
		}
		return jsonMap;
	}

	/**
	 * 找回用户密码--修改用户密码信息
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/updateUserPassword")
	@ResponseBody
	public Map<String, Object> updateUserPassword(Integer userId,
			long beginTime, String password) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		User user = new User();
		user.setUserId(userId);
		user.setLastChangePwdTime(new Date());
		try {
			long currentTime = System.currentTimeMillis();
			if (currentTime - beginTime < SystemConstant.MAX_TIME) {// 验证码有效
				String pwdTmp = "";
				String pwdEncrypt = "";
				if (!StringUtil.isEmpty(password)) {
					pwdTmp = password;
					//log.info("####密码：" + pwdTmp);
					String sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY)
							.substring(0, 16);
					pwdEncrypt = AESUtil.encrypt(pwdTmp, sKey).substring(0, 16);
					//log.info("####加密后密码：" + pwdEncrypt);
					user.setPassword(pwdEncrypt);
					userService.updateByPrimaryKeySelective(user);
					jsonMap.put("message", "1");// 1、表示修改成功。
					jsonMap.put("tips", "恭喜您，修改用户密码成功。");
				}
			} else {
				jsonMap.put("message", "2");// 2、表示修改用户密码失败。
				jsonMap.put("tips", "对不起，验证码已过期，修改用户密码失败。");
			}

		} catch (Exception e) {
			jsonMap.put("message", "0");// 0、表示异常
			jsonMap.put("tips", "对不起，修改用户密码失败。");
			log.info(e);
		}
		return jsonMap;
	}

	/**
	 * 绑定手机号-修改用户信息
	 * 
	 * @param userId
	 *            用户标识
	 * @param phoneNumber
	 *            手机号码
	 * @return
	 */
	@RequestMapping("/updateUserPhone")
	@ResponseBody
	public Map<String, Object> updateUserPhone(Integer userId,
			String phoneNumber, long beginTime) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			User user = new User();
			user.setUserId(userId);
			user.setPhoneNumber(phoneNumber);
			long currentTime = System.currentTimeMillis();
			if (currentTime - beginTime < SystemConstant.MAX_TIME) {// 验证码有效
				int result = userService.updateByPrimaryKeySelective(user);
				if (1 == result) {
					jsonMap.put("message", SystemConstant.MSG_SUCCESS);// 1、表示绑定手机号成功。
					jsonMap.put("tips", "恭喜您，修改绑定手机号成功。");
					jsonMap.put("phoneNumber", phoneNumber);
				} else {
					jsonMap.put("message", SystemConstant.MSG_FAIL);// 2、表示绑定手机号失败。
					jsonMap.put("tips", "修改对不起，绑定手机号失败。");
				}
			} else {
				jsonMap.put("message", "2");// 2、表示绑定失败。
				jsonMap.put("tips", "对不起，验证码已过期，修改绑定手机号失败。");
			}

		} catch (Exception e) {
			jsonMap.put("message", "0");// 0、表示异常
			jsonMap.put("tips", "对不起，绑定手机号失败。");
			log.info(e);
		}
		return jsonMap;
	}

	/**
	 * 修改用户信息,含照片
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/updateUserPhoto")
	@ResponseBody
	public Map<String, Object> updateUserPhoto(Integer userId,
			HttpServletRequest request) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		User user = new User();
		user.setUserId(userId);
		if (userId == null || userId == 0) {
			jsonMap.put("message", "4");// 4、表示userId为空。
			jsonMap.put("tips", "对不起，用户标识不存在。");
			return jsonMap;
		}
		// String loginName=request.getParameter("loginName");
		// String password=request.getParameter("password");
		// user.setPassword(password);
		// System.out.println(loginName+"\t"+password);
		try {
			String pwdTmp = "";
			String pwdEncrypt = "";
			if (!StringUtil.isEmpty(user.getPassword())) {
				pwdTmp = user.getPassword();
				log.info("####密码：" + pwdTmp);
				String sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY)
						.substring(0, 16);
				pwdEncrypt = AESUtil.encrypt(pwdTmp, sKey).substring(0, 16);
				log.info("####加密后密码：" + pwdEncrypt);
				user.setPassword(pwdEncrypt);
			}
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获取上传的文件
			MultipartFile multiFile = multipartRequest.getFile("file");

			if (multiFile.isEmpty()) {
				jsonMap.put("message", "3");// 3、表示上传文件为空。
				jsonMap.put("tips", "对不起，请先上传您的头像。");
				return jsonMap;
			}
			Integer fileSize = (int) multiFile.getSize() / 1024;
			/**
			 * 如果文件小于5M，则上传文件，否则提示用户不能超过5M
			 */
			if (fileSize > 1024 * 5) {
				jsonMap.put("message", "5");// 5、表示文件不能大于5M。
				jsonMap.put("tips", "对不起，文件不能大于5M。");
				return jsonMap;
			}
			// 获取文件后缀
			String[] suffixs = multiFile.getOriginalFilename().split("\\.");
			String suffix = "." + suffixs[suffixs.length - 1];
			// 获得文件全名
			String fname = userId + "_" + System.currentTimeMillis() + suffix;
			// String fname =userId+ "_"+multiFile.getOriginalFilename();

			String path = request.getSession().getServletContext()
					.getRealPath("upload");
			// 创建文件存放路径
			File folder = new File(path);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			File file = new File(path + File.separator + fname);
			log.info("######path:" + file.getAbsolutePath());
			// 如果文件不存在写文件到服务器
			multiFile.transferTo(file);
			user.setPhotoIconUrl(fname);
			int result = userService.updateByPrimaryKeySelective(user);
			if (result > 0) {
				jsonMap.put("message", "1");// 1、表示修改成功。
				jsonMap.put("photoName",fname);//返回头像名称
				jsonMap.put("tips", "恭喜您，修改用户信息成功。");
			} else {
				jsonMap.put("message", "2");// 2、表示未修改用户信息
				jsonMap.put("tips", "对不起，未修改用户信息。");
			}
		} catch (Exception e) {
			jsonMap.put("message", "0");// 0、表示异常
			jsonMap.put("tips", "对不起，修改用户信息失败。");
			log.info(e);
		}
		return jsonMap;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/updateUser")
	@ResponseBody
	public Map<String, Object> updateUser(String params) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		User user = JSON.parseObject(params, User.class);
		try {
			String pwdTmp = "";
			String pwdEncrypt = "";
			if (!StringUtil.isEmpty(user.getPassword())) {
				pwdTmp = user.getPassword();
				log.info("####密码：" + pwdTmp);
				String sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY)
						.substring(0, 16);
				pwdEncrypt = AESUtil.encrypt(pwdTmp, sKey).substring(0, 16);
				log.info("####加密后密码：" + pwdEncrypt);
				user.setPassword(pwdEncrypt);
			}
			int result = userService.updateByPrimaryKeySelective(user);
			if (result > 0) {
				jsonMap.put("message", "1");// 1、表示修改成功。
				jsonMap.put("tips", "恭喜您，修改用户信息成功。");
			} else {
				jsonMap.put("message", "2");// 1、表示修改成功。
				jsonMap.put("tips", "对不起，未修改用户信息。");
			}
		} catch (Exception e) {
			jsonMap.put("message", "0");// 0、表示异常
			jsonMap.put("tips", "对不起，修改用户信息失败。");
			log.info(e);
		}
		return jsonMap;
	}

	/**
	 * 个人中心-- 修改密码
	 * 
	 * @param userId 用户标识
	 * @param initPwd 原始密码
	 * @param newPwd 新密码
	 * @return //TODO
	 */
	@RequestMapping("/changeUserPassword")
	@ResponseBody
	public Map<String, Object> changeUserPassword(Integer userId,
			String initPwd, String newPwd) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			User user = new User();
			user.setUserId(userId);
			String pwdTmp = initPwd;
			//log.info("####原始密码：" + pwdTmp);
			String sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY).substring(0,16);
			String pwdEncrypt = AESUtil.encrypt(pwdTmp, sKey).substring(0, 16);
			//log.info("####原始加密后：" + pwdEncrypt);
			User initUser = userService.selectByPrimaryKey(userId);
			if (pwdEncrypt.equals(initUser.getPassword())) {// 原密码正确
				String newPwdEncrypt = "";
				if (!StringUtil.isEmpty(newPwd)) {// 新密码不空
					//log.info("####新密码：" + newPwd);
					sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY).substring(0,
							16);
					newPwdEncrypt = AESUtil.encrypt(newPwd, sKey).substring(0,
							16);
					//log.info("####新密码加密后：" + newPwdEncrypt);
					user.setPassword(newPwdEncrypt);
				} else {// 新密码为空
					jsonMap.put("message", "3");// 3、表示新密码为空。
					jsonMap.put("tips", "对不起，新密码为空，修改密码失败。");
				}
				int result = userService.updateByPrimaryKeySelective(user);
				if (result > 0) {
					jsonMap.put("message", "1");// 1、表示修改密码成功。
					jsonMap.put("tips", "恭喜您，修改密码成功。");
				} else {
					jsonMap.put("message", "2");// 2、表示未修改密码。
					jsonMap.put("tips", "对不起，未修改密码，修改密码失败。");
				}
			} else {// 原密码错误
				jsonMap.put("message", "4");// 4、表示用户原始密码错误。
				jsonMap.put("tips", "对不起，您的原始密码错误，修改密码失败。");
			}

		} catch (Exception e) {
			jsonMap.put("message", "0");// 0、表示异常
			jsonMap.put("tips", "对不起，修改密码失败。");
			log.info(e);
		}
		return jsonMap;
	}

	/**
	 * 新增用户GPS信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/addUserGpsInfo")
	@ResponseBody
	public Map<String, Object> addUserGpsInfo(Integer userId, String longitude,
			String latitude,String deviceId,String deviceName) {
		// 变量声明
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Date currentTime = new Date();
			UserGpsInfo userGpsInfo = new UserGpsInfo();
			userGpsInfo.setUserId(userId);
			userGpsInfo.setLongitude(longitude);
			userGpsInfo.setLatitude(latitude);
			userGpsInfo.setLocationTime(currentTime);
			String result = userService.addUserGpsInfo(userGpsInfo);
			jsonMap.put("message", result);// 1、表示增加用户GPS信息成功
			jsonMap.put("tips", "恭喜您，增加用户GPS信息成功。");
		} catch (Exception e) {
			jsonMap.put("message", "0");// 0、表示异常
			jsonMap.put("tips", "对不起，增加用户GPS信息失败。");
			log.info(e);
		}
		return jsonMap;
	}

	/**
	 * 根据用户标识查询用户信息
	 * 
	 * @param userId
	 * @return 用户实体对象
	 */
	@RequestMapping("/findUserById")
	@ResponseBody
	public Map<String, Object> findUserById(Integer userId) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			User user = new User();
			user = userService.selectByPrimaryKey(userId);
			if (user != null) {
				jsonMap.put("message", SystemConstant.MSG_SUCCESS);// 1表示成功
				jsonMap.put("tips", "查询用户信息成功。");
				user.setPassword(null);
				jsonMap.put("user", user);
			} else {
				jsonMap.put("message", SystemConstant.MSG_NO_DATA);// 9、暂无用户信息
				jsonMap.put("tips", "对不起，暂无用户信息。");
			}

		} catch (Exception e) {
			jsonMap.put("message", SystemConstant.MSG_EXCEPTION);// 0、表示异常
			jsonMap.put("tips", "对不起，查询用户信息失败。");
		}
		return jsonMap;
	}
	
	/**
	 * 新增或修改用户与第三方绑定关系
	 * 
	 * @return
	 */
	@RequestMapping("/addOrUpdUserBinding")
	@ResponseBody
	public Map<String, Object> addOrUpdUserBinding(@RequestParam Map map) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		UserBinding userBinding = new UserBinding();
		try {
			String userId = (String) map.get("userId");
			String openId = (String) map.get("openId");
			String bindingType = (String) map.get("bindingType");//绑定类型  2:个推出租  4:个推私家车
			String osType = (String) map.get("osType");
			
			if (!StringUtil.isEmpty(userId)&&!StringUtil.isEmpty(openId)) {
				userBinding.setUserId(Integer.valueOf(userId));// 用户标识
				userBinding.setOpenId(openId);// 第三方标识
				userBinding.setBindingType(bindingType);// 绑定类型
				userBinding.setCreateTime(new Date());// 绑定时间
				userBinding.setOsType(osType);//app端操作系统类型

				int result = userService.addOrUpdUserBinding(userBinding);
				if (result > 0) {
					jsonMap.put("message", SystemConstant.MSG_SUCCESS);// 1、表示新增或修改成功
					jsonMap.put("tips", "新增或修改成功。");
					jsonMap.put("userBinding", userBinding);
				} else {
					jsonMap.put("message", SystemConstant.MSG_FAIL);// 1、表示新增或修改成功
					jsonMap.put("tips", "未新增或修改数据。");
				}

			}
		} catch (Exception e) {
			jsonMap.put("message", SystemConstant.MSG_EXCEPTION);// 0、表示异常
			jsonMap.put("tips", "对不起，查询用户信息失败。");
		}
		return jsonMap;
	}

	// //////////////////////////////////
	/**
	 * 修改用户信息
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/updateUser2")
	@ResponseBody
	public Map<String, Object> updateUser2(@RequestBody User params) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String pwdTmp = "";
			String pwdEncrypt = "";
			if (!StringUtil.isEmpty(params.getPassword())) {
				pwdTmp = params.getPassword();
				//log.info("####密码：" + pwdTmp);
				String sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY)
						.substring(0, 16);
				pwdEncrypt = AESUtil.encrypt(pwdTmp, sKey);
				//log.info("####加密后密码：" + pwdEncrypt);
				params.setPassword(pwdEncrypt);
			}
			userService.updateByPrimaryKeySelective(params);
			jsonMap.put("message", "1");// 1、表示修改成功。
			jsonMap.put("tips", "恭喜您，修改用户信息成功。");
		} catch (Exception e) {
			jsonMap.put("message", "0");// 0、表示异常
			jsonMap.put("tips", "对不起，修改用户信息失败。");
			log.info(e);
		}
		return jsonMap;
	}

	@RequestMapping("/findUser")
	@ResponseBody
	public Map<String, Object> findUser(@RequestParam Map map) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			User user = new User();
			user.setLoginName(map.get("loginName") + "");
			List<User> list = userService.findUser(user);
			if (list.size() > 0) {
				jsonMap.put("message", SystemConstant.MSG_SUCCESS);// 1、表示查询成功
				jsonMap.put("tips", "查询用户信息成功。");
				jsonMap.put("list", list);
			} else {
				jsonMap.put("message", SystemConstant.MSG_NO_DATA);// 9、暂无用户信息
				jsonMap.put("tips", "对不起，暂无用户信息。");
			}
		} catch (Exception e) {
			jsonMap.put("message", SystemConstant.MSG_EXCEPTION);// 0、表示异常
			jsonMap.put("tips", "对不起，查询用户信息失败。");
		}
		return jsonMap;
	}
	
	@RequestMapping("/toAddUser")
	public ModelAndView toAddUser() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/user/addUser.jsp");
		return mv;
	}

	/**
	 * 后台增加用户，并且绑定设备
	 * @param loginName 登录的手机号码串，以英文逗号分隔，最多不超过3个手机号
	 * @param deviceName 车牌号码
	 * @param carType 汽车型号
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping("/addAndBindingUser")
	@Transactional
	public ModelAndView addAndBindingUser(String loginName) {
		Map<String, Object> jsonMap=new HashMap<String, Object>();
		StringBuilder exitPhoneNumber=new StringBuilder();//手机号登录名
		StringBuilder userIds=new StringBuilder();//userId
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/success.jsp");
		try {
			String  phones[]=loginName.split(",");
			for(int i=0;i<phones.length;i++){
				String oneLoginName=phones[i];//
				User u = new User();
				u.setLoginName(oneLoginName);
				List<User> exitUsers = userService.findUser(u);
				if (exitUsers.size() >= 1) {//1、判断该手机号登录名是否存在
					exitPhoneNumber.append(oneLoginName);
					exitPhoneNumber.append(",");
					userIds.append(exitUsers.get(0).getUserId());
					userIds.append(",");
				}else{//1、不存的手机用户，则新建该手机号码的用户
					User user=new User();
					user.setLoginName(oneLoginName);
					user.setPhoneNumber(oneLoginName);
					user.setCreateTime(new Date());
					String pwd = GenerateIdUtil.getRandomNumber(6);//随机密码
					//密码发送用户手机
					String msg = "您的账号已注册成功！您的密码为：" + pwd+ "，请登录后及时修改，（请勿向任何人提供您收到的密码），并请保存一下APP下载和更新地址：http://weixin.app118.cn/xiazai.php【每日互联】";
					// 调用短信发送
					log.info(oneLoginName + "#######密码：" + pwd);
					User loginUser=(User)session.getAttribute("user");
					Integer userId=0;
					if(loginUser!=null){
						userId=loginUser.getUserId();
					}
					smsService.sendSms(oneLoginName, msg,"1",userId);
					
					String pwdTmp=MD5Util.encode(pwd);//客户端app生成的md5密码
					//log.info(pwd+",客户端md5密码：" + pwdTmp);
					String sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY).substring(0, 16);
					String pwdEncrypt = AESUtil.encrypt(pwdTmp, sKey).substring(0,16);
					//log.info("####后台加密后密码：" + pwdEncrypt);
					user.setPassword(pwdEncrypt);
					String result = userService.addUser(user);
					if ("1".equals(result)) {// 1、表示注册成功
						userIds.append(user.getUserId());
						userIds.append(",");
					} else {// 否则表示注册失败
						log.info("注册绑定失败。");
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("error", e.getMessage());
			mv.setViewName("/pages/error.jsp");
		}
		//log.info("#######"+jsonMap);
		mv.addObject("message",jsonMap.get("tips"));
		return mv;
	}
	
	/**
	 * 进入用户列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/initUser")
	public ModelAndView initUser() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/user/listUser.jsp");
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
				mv.addObject("updUserType", "1");//显示  转后台用户按钮
				break;
			}else{
				mv.addObject("disabled", "disabled=\"disabled\"");
			}
		}
		
		return mv;
	}
	
	/**
	 * 用户列表页面异步请求
	 * 
	 * @return
	 */
	@RequestMapping("listUser")
	@ResponseBody
	public Map<String,Object> listUser(String curNo,String curSize,String sortname,String sortorder,String loginName,String realName,String fromCreateTime,String toCreateTime,String orgId){
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
		
		if(!StringUtil.isEmpty(loginName)){
			map.put("loginName",loginName);
		}
		if(!StringUtil.isEmpty(orgId)){
			map.put("orgId",orgId);
		}
		if(!StringUtil.isEmpty(realName)){
			map.put("realName", realName);
		}
		if(!StringUtil.isEmpty(fromCreateTime)){
			map.put("fromCreateTime", fromCreateTime);
		}
		if(!StringUtil.isEmpty(toCreateTime)){
			map.put("toCreateTime", toCreateTime);
		}
		String orderbyStr=null;
		if(!StringUtil.isEmpty(sortname)){
			if("createTime".equals(sortname)){
				orderbyStr="order by  create_time "+sortorder;
			}else if("userId".equals(sortname)){
				orderbyStr="order by user_id "+sortorder;
			}else if("loginName".equals(sortname)){
				orderbyStr="order by login_name "+sortorder;
			}else if("realName".equals(sortname)){
				orderbyStr="order by real_name "+sortorder;
			}else if("sex".equals(sortname)){
				orderbyStr="order by sex "+sortorder;
			}else if("orgName".equals(sortname)){
				orderbyStr="order by org_id "+sortorder;
			}
		}else{
			orderbyStr="order by user_id desc";
		}
		map.put("orderBy", orderbyStr);
		List<Map> list=new ArrayList<Map>();
		list=userService.selectByPager(map);
		for(Map oneUser:list){
			Date d=(Date)oneUser.get("createTime");
			oneUser.put("createTime",DateUtil.getFormatDate(d, ""));//创建时间
		}
		
		int allSize =userService.selectByPagerCount(map);
		
		jsonMap.put("loginName", loginName);
		jsonMap.put("nickname", realName);
		jsonMap.put("fromCreateTime", fromCreateTime);
		jsonMap.put("toCreateTime", toCreateTime);
		
		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;
	}
	
	/**
	 * 进入用户列表选择初始页面复选
	 * 
	 * @return
	 */
	@RequestMapping("/initSelectUser")
	public ModelAndView initSelectUser() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/user/listSelectUser.jsp");
		
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
	 * 进入用户列表选择初始页面单选
	 * @return
	 */
	@RequestMapping("/initSelectUserRadio")
	public ModelAndView initSelectUserRadio() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/user/listSelectUserRadio.jsp");
		
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
				
		
		return mv;
	}
	
	/**
	 * 用户列表页面异步请求
	 * 
	 * @return
	 */
	@RequestMapping("listSelectUser")
	@ResponseBody
	public Map<String,Object> listSelectUser(String curNo,String curSize,String loginName,String realName,String fromCreateTime,String toCreateTime){
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
		
		if(!StringUtil.isEmpty(loginName)){
			map.put("loginName",loginName);
		}
		if(!StringUtil.isEmpty(realName)){
			map.put("realName", realName);
		}
		if(!StringUtil.isEmpty(fromCreateTime)){
			map.put("fromCreateTime", fromCreateTime);
		}
		if(!StringUtil.isEmpty(toCreateTime)){
			map.put("toCreateTime", toCreateTime);
		}
		List<Map> list=new ArrayList<Map>();
		list=userService.selectByPager(map);
		for(Map oneUser:list){
			Date d=(Date)oneUser.get("createTime");
			oneUser.put("createTime",DateUtil.getFormatDate(d, ""));
		}
		
		int allSize =userService.selectByPagerCount(map);
		
		jsonMap.put("loginName", loginName);
		jsonMap.put("nickname", realName);
		jsonMap.put("fromCreateTime", fromCreateTime);
		jsonMap.put("toCreateTime", toCreateTime);
		
		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;
	}
	
	/**
	 * app用户管理--添加用户
	 * 
	 * 初始进入新增用户页面
	 * @return
	 */
	@RequestMapping("/toInsertUser")
	public ModelAndView toInsertUser() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/user/insertUser.jsp");
		Map param=new HashMap();
		param.put("type", "6");//汽车品牌
		List<Map> brandList=codeService.selectBySelective(param);
		
		param.put("type", "7");//车系
		List<Map> carSeriesList=codeService.selectBySelective(param);
		
		param.put("type", "8");//汽车型号
		List<Map> carTypeList=codeService.selectBySelective(param);
		mv.addObject("brandList", brandList);
		mv.addObject("carSeriesList", carSeriesList);
		mv.addObject("carTypeList", carTypeList);
		
		int curYear=DateUtil.getYear();//当前年减去20年做为下拉年份
		List<Integer> yearList=new ArrayList<Integer>();
		for(int i=curYear;i>=curYear-SystemConstant.SYSTEM_YEARS;i--){
			yearList.add(i);
		}
		mv.addObject("yearList", yearList);
		
		//销售员列表
		Integer roleId=ButtonPermissionConstant.SALESUSERLIST_SALES;//销售员角色id
		List<User> userList=userService.selectUserByRoleId(roleId);
		mv.addObject("userList", userList);
		
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		mv.addObject("orgMap", orgMap);
		User loginUser=(User)session.getAttribute("user");
		Integer orgId = loginUser.getOrgId();
		mv.addObject("orgId", orgId);//当前登录人员的所属门店
		//mv.addObject("userId", loginUser.getUserId());
		
		
//		List<User> ownOrgIdUserList=new ArrayList<User>();//所属门店的销售人员列表
//		for(User user:userList){
//			if(user.getOrgId()==orgId){
//				ownOrgIdUserList.add(user);
//			}
//		}
		
		//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
		List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
		for (Role role : roleList) {
			if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId==ButtonPermissionConstant.ROOT_ORG_ID) {// 超级管理员
				break;
			} else{
				
				mv.addObject("disabled", "disabled=\"disabled\"");
			}
		}
		
		return mv;
	}
	/**
	 * 添加用户
	 * 一站式添加用户：实现用户注册、设备入库、用户设备绑定、设备充值操作
	 * 
	 * @param loginName  手机号
	 * @param realName 姓名
	 * @param sex 性别
	 * @param carType 汽车型号
	 * @param deviceName 汽车牌号
	 * @param deviceMac 设备Mac
	 * @param cardType 充值种类
	 * @param paidAmount 实付金额
	 * @return
	 */
	@RequestMapping("/addUserAll")
	@Transactional
	public ModelAndView addUserAll(String loginName,String realName,String sex,String carBrand,String carSeries,String carYear,String carType,String carCategory,String deviceName,String deviceMac,String cardType,String paidAmount,String op,String saleUserId,String orgId,String installOrgId,String carStyle) {
		System.out.println("################orgId###############:"+orgId);
		// 变量声明
		ModelAndView mv = new ModelAndView("/app118/userAction/toInsertUser");
		Map<String, Object> jsonMap=new HashMap<String, Object>();
		Map<String, Object> paramMap=new HashMap<String, Object>();
		String userInfo="";
		Integer userId=null;//用户标识
		
		User loginUser=userService.selectByPrimaryKey(Integer.valueOf(saleUserId));
		paramMap.put("loginUserId", loginUser.getUserId());//当前登录的用户标识
		paramMap.put("loginOrgId", loginUser.getOrgId());//当前登录的用户所属组织机构标识
		paramMap.put("op", op);//op为upd时为修改，op为add时为新增
		
	    deviceName=deviceName.toUpperCase();
	    deviceMac=deviceMac.toUpperCase();
	    paramMap.put("loginName", loginName);//手机号
	    paramMap.put("realName", realName);//姓名
	    paramMap.put("sex", sex);//性别
	    paramMap.put("carCategory", carCategory);//汽车种类
	    paramMap.put("carBrand", carBrand);//汽车品牌
	    paramMap.put("carSeries", carSeries);//汽车系列
	    paramMap.put("carStyle", carStyle);//汽车款式
	    paramMap.put("carYear", carYear);//汽车年份
	    paramMap.put("carType", carType);//汽车型号
	    paramMap.put("deviceName", deviceName);//汽车牌号
	    paramMap.put("deviceMac", deviceMac);//设备Mac
	    paramMap.put("cardType", cardType);//充值种类
	    paramMap.put("paidAmount", paidAmount);//实付金额
	    paramMap.put("chargeType", "9");//充值方式 后台
	    paramMap.put("orgId", orgId);//所属组织机构
	    paramMap.put("installOrgId", installOrgId);//安装门店
	    

	    
	    //1、注册用户
	    User u = new User();
		u.setLoginName(loginName);
		List<User> exitUsers = userService.findUser(u);
		if (exitUsers.size() >= 1) {//1A、判断该手机号登录名是否存在
			userId=exitUsers.get(0).getUserId();
			realName=exitUsers.get(0).getRealName();
			paramMap.put("userId", userId);
			paramMap.put("realName", realName);
			//jsonMap.put("message", "用户已存在。");
			log.info("用户已存在。");
			userInfo="用户已存在。";
		
			mv.addObject("message", "success");
		}else{//1B、不存的手机用户，则新建该手机号码的用户
			paramMap.put("realName", realName);
			User user=new User();
			user.setLoginName(loginName);//用户名
			user.setPhoneNumber(loginName);//手要号
			user.setRealName(realName);//真实姓名
			user.setSex(sex);//性别
			user.setCreateTime(new Date());//创建时间
			user.setOrgId(Integer.valueOf(orgId));//所属组织机构
			user.setUserType("3");//用户类型 1：后台 3：app
			user.setLastLoginTime(new Date());//最后登录时间初始化
			user.setLastChangePwdTime(new Date());//最后修改密码时间初始化
			String pwd = GenerateIdUtil.getRandomNumber(6);//随机密码
			//密码采用手机号码后4位 TODO
			int max=loginName.length();
			pwd=loginName.substring(max-4, max);
			
			//密码发送用户手机
			String msg = "您的密码为：" + pwd+ "，请登录后及时修改，（请勿向任何人提供您收到的密码）【每日互联】";
			// 调用短信发送
			log.info("#######用户名："+loginName + "密码：" + pwd);
			User userLogined=(User)session.getAttribute("user");
			Integer uid=0;
			if(userLogined!=null){
				uid=userLogined.getUserId();
			}
			smsService.sendSms(loginName, msg,"1",uid);
			
			String pwdTmp=MD5Util.encode(pwd);//客户端app生成的md5密码
			String sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY).substring(0, 16);
			String pwdEncrypt = AESUtil.encrypt(pwdTmp, sKey).substring(0,16);
			user.setPassword(pwdEncrypt);
			user.setCarCategory(carCategory);//汽车种类
			String result = userService.addUser(user);
			if ("1".equals(result)) {//表示注册成功
				userId=user.getUserId();
				paramMap.put("userId", userId);
				userInfo="用户注册成功。";
				log.info("用户注册成功。");
				mv.addObject("message", "success");
			} else {// 否则表示注册失败
				log.info("注册绑定失败。");
				userInfo="用户注册失败。";
			}
		}
	    
		mv.addObject("info", userInfo+jsonMap.get("info"));
		System.out.println("all in one :"+userInfo+jsonMap.get("info"));
		return mv;
	}
	
	/**
	 * 初始绑定用户页面
	 * @return
	 */
	@RequestMapping("/toUpdUser")
	public ModelAndView toUpdUser(String loginName,String realName,String sex) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/user/updateUser.jsp");
		mv.addObject("loginName", loginName);
		mv.addObject("realName",realName );
		mv.addObject("sex", sex);	
		Map param=new HashMap();
		param.put("type", "6");//汽车品牌
		List<Map> brandList=codeService.selectBySelective(param);
		
		param.put("type", "7");//车系
		List<Map> carSeriesList=codeService.selectBySelective(param);
		
		param.put("type", "8");//汽车型号
		List<Map> carTypeList=codeService.selectBySelective(param);
		mv.addObject("brandList", brandList);
		mv.addObject("carSeriesList", carSeriesList);
		mv.addObject("carTypeList", carTypeList);
		
		int curYear=DateUtil.getYear();//当前年减去20年做为下拉年份
		List<Integer> yearList=new ArrayList<Integer>();
		for(int i=curYear;i>=curYear-SystemConstant.SYSTEM_YEARS;i--){
			yearList.add(i);
		}
		mv.addObject("yearList", yearList);
		
		//销售员列表
		Integer roleId=ButtonPermissionConstant.SALESUSERLIST_SALES;//销售员角色id
		List<User> userList=userService.selectUserByRoleId(roleId);
		mv.addObject("userList", userList);
		
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		mv.addObject("orgMap", orgMap);
		
		User loginUser=(User)session.getAttribute("user");
		Integer orgId = loginUser.getOrgId();
		mv.addObject("orgId", orgId);//当前登录人员的所属门店
		

		//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
		List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
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
	 * 查看用户数据
	 * @param userId
	 * @return
	 */
	@RequestMapping("viewUser")
	public ModelAndView viewUser(Integer userId){
		// 变量声明
	    ModelAndView mv = new ModelAndView("/pages/user/viewUser.jsp");
	    List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	      
	    //1、根据userId查询用户信息
	    User user=userService.selectByPrimaryKey(userId);
	 
	    mv.addObject("user", user);
	    return mv;
	}
	
	/**
	 * 进入用户设备绑定列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/initUserDeviceRel")
	public ModelAndView initUserDeviceRel() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/user/listUserDeviceRel.jsp");
		
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		mv.addObject("orgMap", orgMap);
		
		User loginUser=(User)session.getAttribute("user");
		Integer orgId = loginUser.getOrgId();
		mv.addObject("orgId", orgId);//当前登录人员的所属门店
		
		//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
		List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
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
	 * app用户车辆绑定异步请求
	 * 
	 * @return
	 */
	@RequestMapping("listUserDeviceRel")
	@ResponseBody
	public Map<String,Object> listUserDeviceRel(String curNo,String curSize,String sortname,String sortorder,String deviceName,String realName,String fromCreateTime,String toCreateTime,String orgId){
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
		
		if(!StringUtil.isEmpty(deviceName)){
			map.put("deviceName",deviceName);
		}
		if(!StringUtil.isEmpty(realName)){
			map.put("realName", realName);
		}
		if(!StringUtil.isEmpty(fromCreateTime)){
			map.put("fromCreateTime", fromCreateTime);
		}
		if(!StringUtil.isEmpty(toCreateTime)){
			map.put("toCreateTime", toCreateTime);
		}
		if(!StringUtil.isEmpty(orgId)){
			map.put("orgId", orgId);
		}
		List<Map> list=new ArrayList<Map>();
		
		String orderbyStr = null;
		if (!StringUtil.isEmpty(sortname)) {
			if ("createTime".equals(sortname)) {//绑定时间排序
				orderbyStr = "order by r.create_time " + sortorder;
			} else if ("userId".equals(sortname)) {//用户标识排序
				orderbyStr = "order by u.user_id " + sortorder;
			} else if ("deviceName".equals(sortname)) {//汽车牌号排序
				orderbyStr = "order by d.device_name " + sortorder;
			} else if ("realName".equals(sortname)) {//姓名排序
				orderbyStr = "order by u.real_name " + sortorder;
			} else if ("deviceMac".equals(sortname)) {
				orderbyStr = "order by d.device_mac " + sortorder;
			}else if ("orgName".equals(sortname)) {
				orderbyStr = "order by u.org_id " + sortorder;
			}
		} else {//默认排序
			orderbyStr = " order by r.create_time desc,u.real_name,r.dur_id desc";
		}
		//System.out.println("******sortname:"+sortname+"######orderbyStr:"+orderbyStr);
		map.put("orderBy", orderbyStr);
		
		list=userService.selectUserDeviceRelByPager(map);
		for(Map oneUser:list){
			Date d=(Date)oneUser.get("createTime");
			oneUser.put("createTime",DateUtil.getFormatDate(d, ""));//绑定时间
		}
		
		int allSize =userService.selectUserDeviceRelByPagerCount(map);
		
		jsonMap.put("deviceName", deviceName);
		jsonMap.put("realName", realName);
		jsonMap.put("fromCreateTime", fromCreateTime);
		jsonMap.put("toCreateTime", toCreateTime);
		
		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;
	}
	
	/**
	 * 设备解绑回库   TODO
	 * 
	 */
	@RequestMapping("/delDeviceUserRelaAndUpdAirDevice")
	@ResponseBody
	public Map<String, Object> delDeviceUserRelaAndUpdAirDevice(String durIds,String deviceIds) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int flag = 0;// 设备解绑失败
		try {
			if (!StringUtil.isEmpty(durIds)){
//				System.out.println("durIds:"+durIds);
//				System.out.println("deviceIds:"+deviceIds);
				String []durIdArray=durIds.split(",");
				String []deviceIdArray=deviceIds.split(",");
				for(int i=0;i<durIdArray.length;i++){
					String oneDurId=durIdArray[i];
					String oneDeviceId=deviceIdArray[i];
					Integer durId=Integer.valueOf(oneDurId);
					Integer deviceId=Integer.valueOf(oneDeviceId);
					
				}
				// 1 表示设备解绑成功
			}
		} catch (Exception e) {
			log.info("设备解绑异常："+ e);
			flag = 0;
		}
		resultMap.put("flag", flag);
		return resultMap;
	}
	

	
	//**********************//
	/**
	 * 进入装车app用户管理 列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/initUserByIntallOrgId")
	public ModelAndView initUserByIntallOrgId() {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/user/listUserByIntallOrgId.jsp");
		Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
		List<Org> orgList = orgService.selectBySelective(new Org());
		for (Org org : orgList) {
			orgMap.put(org.getOrgId() + "", org.getOrgName());
		}
		mv.addObject("orgMap", orgMap);
		
		User loginUser=(User)session.getAttribute("user");
		Integer orgId = loginUser.getOrgId();
		mv.addObject("orgId", orgId);//当前登录人员的所属门店
		
		//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
		List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
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
	 * 装车app用户管理 列表页面异步请求
	 * 
	 * @return
	 */
	@RequestMapping("listUserByIntallOrgId")
	@ResponseBody
	public Map<String,Object> listUserByIntallOrgId(String curNo,String curSize,String sortname,String sortorder,String deviceName,String realName,String fromCreateTime,String toCreateTime,String orgId){
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
		
		if(!StringUtil.isEmpty(deviceName)){
			map.put("deviceName",deviceName);
		}
		if(!StringUtil.isEmpty(realName)){
			map.put("realName", realName);
		}
		if(!StringUtil.isEmpty(fromCreateTime)){
			map.put("fromCreateTime", fromCreateTime);
		}
		if(!StringUtil.isEmpty(toCreateTime)){
			map.put("toCreateTime", toCreateTime);
		}
		if(!StringUtil.isEmpty(orgId)){
			map.put("installOrgId", orgId);
		}
		List<Map> list=new ArrayList<Map>();
		
		String orderbyStr = null;
		if (!StringUtil.isEmpty(sortname)) {
			if ("createTime".equals(sortname)) {//绑定时间排序
				orderbyStr = "order by r.create_time " + sortorder;
			} else if ("userId".equals(sortname)) {//用户标识排序
				orderbyStr = "order by u.user_id " + sortorder;
			} else if ("deviceName".equals(sortname)) {//汽车牌号排序
				orderbyStr = "order by d.device_name " + sortorder;
			} else if ("realName".equals(sortname)) {//姓名排序
				orderbyStr = "order by u.real_name " + sortorder;
			} else if ("deviceMac".equals(sortname)) {
				orderbyStr = "order by d.device_mac " + sortorder;
			}else if ("orgName".equals(sortname)) {
				orderbyStr = "order by u.org_id " + sortorder;
			}else if ("installOrgName".equals(sortname)) {
				orderbyStr = "order by installOrgName " + sortorder;
			}
		} else {//默认排序
			orderbyStr = " order by r.create_time desc,u.real_name,r.dur_id desc";
		}
		map.put("orderBy", orderbyStr);
		
		list=userService.selectUserDeviceRelByPager(map);
		for(Map oneUser:list){
			Date d=(Date)oneUser.get("createTime");
			oneUser.put("createTime",DateUtil.getFormatDate(d, ""));//绑定时间
		}
		
		int allSize =userService.selectUserDeviceRelByPagerCount(map);
		
		jsonMap.put("deviceName", deviceName);
		jsonMap.put("realName", realName);
		jsonMap.put("fromCreateTime", fromCreateTime);
		jsonMap.put("toCreateTime", toCreateTime);
		
		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;
	}
	
	/**
	 * 进入加盟店客服务人员输入手机号页面
	 * @return
	 */
	@RequestMapping("/toBindUserAndAirDevie")
	public ModelAndView toBindUserAndAirDevie() {
		ModelAndView mv = new ModelAndView("/pages/user/toBindUserAndAirDevice.jsp");
		return mv;
	}
	
	
	@RequestMapping("/preBindUserAndAirDevie")
	public ModelAndView preBindUserAndAirDevie(String loginName) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/user/addUserAndAirDevice.jsp");
		mv.addObject("carCategory","4");//默认为私家车    汽车种类:2:出租车  4：私家车
		//根据手机查询用户信息
		User u=new User();
		u.setLoginName(loginName);
		List<User> users=userService.findUser(u);
		
		int userSize = users.size();
		if(userSize==0){//分销商用户不存在
			mv.addObject("message","请核查用户"+loginName+",是否为分销商用户。");
			mv.addObject("info","请核查用户"+loginName+",是否为分销商用户。");
		}else if(userSize==1){//合法的分销商用户
			u=users.get(0);
			mv.addObject("u",u);
			int curYear=DateUtil.getYear();//当前年减去20年做为下拉年份
			List<Integer> yearList=new ArrayList<Integer>();
			for(int i=curYear;i>=curYear-SystemConstant.SYSTEM_YEARS;i--){
				yearList.add(i);
			}
			mv.addObject("yearList", yearList);
			
			//销售员列表
			Integer roleId=ButtonPermissionConstant.SALESUSERLIST_SALES;//销售员角色id
			List<User> userList=userService.selectUserByRoleId(roleId);
			mv.addObject("userList", userList);
			
			Map<String, String>  orgMap=new TreeMap<String, String>();//组织机构下拉列表
			List<Org> orgList = orgService.selectBySelective(new Org());
			for (Org org : orgList) {
				orgMap.put(org.getOrgId() + "", org.getOrgName());
			}
			mv.addObject("orgMap", orgMap);
			User loginUser=(User)session.getAttribute("user");
			Integer orgId = loginUser.getOrgId();
			mv.addObject("orgId", orgId);//当前登录人员的所属门店
		
			//组织机构权限控制，只有超级管理员可进行组织机构选择查询操作 
			List<Role> roleList = (List<Role>) session.getAttribute("roleList");// 角色列表
			for (Role role : roleList) {
				if (role.getRoleId() == ButtonPermissionConstant.ROLE_SUPERADMIN||orgId==ButtonPermissionConstant.ROOT_ORG_ID) {// 超级管理员
					break;
				} else{
					
					mv.addObject("disabled", "disabled=\"disabled\"");
				}
			}
		}else{
			mv.addObject("message","请核查"+loginName+",是否为分销商用户。");
			mv.addObject("info","请核查"+loginName+",是否为分销商用户。");
		}	
		return mv;
	}
	
	
	/**
	 * 转后台用户/转app用户
	 * @param userId
	 * @return
	 */
	@RequestMapping("/updUserType")
	@ResponseBody
	public Map<String, Object> updUserType(String userId,String userType) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int flag = 0;// 转后台用户失败
		try {
			if (!StringUtil.isEmpty(userId)){
				System.out.println(userId);
				User user=new User();
				user.setUserId(Integer.valueOf(userId));
				user.setUserType(userType);
				user.setRemark("转后台用户");
				user.setModifyTime(new Date());
				int result=userService.updateByPrimaryKeySelective(user);
				flag = result;// 1 表示删除成功
			}
		} catch (Exception e) {
			log.info("转后台用户用户异常："+ e);
			flag = 0;
		}
		resultMap.put("flag", flag);
		return resultMap;
	}
}
