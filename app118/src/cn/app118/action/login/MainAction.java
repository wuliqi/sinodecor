/**
 * @(#)UserAction.java	06/10/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-06-10
 */
package cn.app118.action.login;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.AESUtil;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.GlobalUtil;
import cn.app118.framework.util.MD5Util;
import cn.app118.model.Log;
import cn.app118.model.Menu;
import cn.app118.model.Org;
import cn.app118.model.Role;
import cn.app118.model.User;
import cn.app118.model.Weather;
import cn.app118.service.log.ILogService;
import cn.app118.service.menu.IMenuService;
import cn.app118.service.org.IOrgService;
import cn.app118.service.role.IRoleService;
import cn.app118.service.user.IUserService;
import cn.app118.service.weather.IWeatherService;

/**
 * 后台主控制类
 * @author 吴理琪
 *
 */
@Controller
@RequestMapping("mainAction")
public class MainAction extends BaseAction{
	
	@Resource
	private IWeatherService weatherService;//天气服务
	
	@Resource
	private IUserService userService;// 用户服务类
	
	@Resource
	private IMenuService menuService;//菜单服务类
	
	@Resource
	private IRoleService roleService;//角色服务类
	
	@Resource
	private ILogService logService;//日志服务类
	
	@Resource
	private IOrgService orgService;//组织机构服务类
	/**
	 * 后台用户登录
	 * @param loginName
	 * @param password
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(String loginName,String password) {
		// 变量声明
		ModelAndView mv = new ModelAndView();
		String userInfo="游客，欢迎您！";
		mv.addObject("loginName",loginName);
		User existUser=new User();
		String remark=" 莎琪美妆<br/> 手机号码：18810790739<br/>客服中心：18810790739<br/>地址：北京市海淀区志新村小区<br/>海泰大厦621室<br/>";
		try {
			
			User u = new User();
			u.setLoginName(loginName);
			String pwdTmp =MD5Util.encode(password);
			//log.info("####登录名："+loginName+"\t密码：" + password);
			String sKey = MD5Util.md5(SystemConstant.SYSTEM_SKEY).substring(0,16);
			String pwdEncrypt = AESUtil.encrypt(pwdTmp, sKey).substring(0, 16);
			u.setUserType("1");//指定用户类型 ：1表示后台用户
			List<User> exitUsers = userService.findUser(u);
			
			if(exitUsers.size() == 1){//查询有一个合法用户
				existUser = exitUsers.get(0);
				Integer orgId=existUser.getOrgId();
				Org org=orgService.findOrgById(orgId);
				remark=org.getRemark();
				String tag="";
				if("男".equals(existUser.getSex())){
					tag="先生";
				}else if("女".equals(existUser.getSex())){
					tag="女士";
				}
				userInfo="尊敬的"+existUser.getRealName()+tag+"，欢迎您！";
				if (pwdEncrypt.equals(existUser.getPassword())) {// 若密码一致，表示登录成功
					session.setAttribute("user", existUser);
					//查询菜单
					Map param=new HashMap();
					param.put("userId", existUser.getUserId());
					List<Menu> menuList=menuService.selectBySelectiveByUserId(param);
					
					
					List<Role>  roleList=roleService.selectRoleByUserId(param);
					session.setAttribute("roleList", roleList);
					
					mv.addObject("menuList", menuList);
					
					Log opLog=new Log();//日志
					opLog.setUserId(existUser.getUserId());
					opLog.setLoginName(existUser.getLoginName());
					opLog.setLogType("1");//后台登录
					opLog.setIpAddress(GlobalUtil.getIp(request));
					opLog.setOpContent("{\"message\":\"后台登录成功\"}");
					opLog.setOpTime(new Date());
					opLog.setOrgId(existUser.getOrgId());
					logService.insert(opLog);
					
				} else {//表示用户或密码错误
					mv.addObject("msg",SystemConstant.MSG_FAIL);// 2 表示登录失败，原因密码错误。
					mv.setViewName("/pages/login/login.jsp");
					return mv;
				}
			} else {//表示用户登录失败。
				mv.addObject("msg",SystemConstant.MSG_NO_DATA);// 9 表示无数据，原因用户类型为后台用用的用户名不存在
				mv.setViewName("/pages/login/login.jsp");
				return mv;
			}
		
			
			
			//获取最新天气信息
			Map w=new HashMap();
			String clauseSql="group by last_update order by weather_id desc limit 1,2";
			w.put("remark2", "北京");;
			w.put("clauseSql", clauseSql);
			List<Weather> list=weatherService.selectWeather(w);
			StringBuilder weatherInfo=new StringBuilder();
			if(list.size()>0){
				Weather weather=list.get(0);
				String sp="  ";
//				weatherInfo.append(loginName);
//				weatherInfo.append(",欢迎您！    ");
				weatherInfo.append(weather.getRemark2());
				weatherInfo.append("  最新天气");
				weatherInfo.append(sp);
				weatherInfo.append("美国大使馆AQI");
				weatherInfo.append(sp);
				weatherInfo.append(weather.getUsEmbassyAqi());
				weatherInfo.append(sp);
				weatherInfo.append("环保局AQI");
				weatherInfo.append(sp);
				weatherInfo.append(weather.getAqi());
				weatherInfo.append(sp);
				weatherInfo.append("空气质量");
				weatherInfo.append(sp);
				weatherInfo.append(weather.getQuality());
				weatherInfo.append(sp);
				weatherInfo.append(weather.getTemperature());
				weatherInfo.append("°");
				weatherInfo.append(sp);
				weatherInfo.append(weather.getWeatherText());
				weatherInfo.append(sp);
				weatherInfo.append(weather.getWindScale());
				weatherInfo.append(sp);
				weatherInfo.append("湿度");
				weatherInfo.append(sp);
				weatherInfo.append(weather.getHumidity());
				weatherInfo.append(sp);
				/*weatherInfo.append("PM10");
				weatherInfo.append(sp);
				weatherInfo.append(weather.getPm10());
				weatherInfo.append(sp);*/
				weatherInfo.append("PM2.5");
				weatherInfo.append(sp);
				weatherInfo.append(weather.getPm25());
				weatherInfo.append("µg/m³");
				weatherInfo.append(sp);
			/*	weatherInfo.append("NO2");
				weatherInfo.append(sp);
				weatherInfo.append(weather.getNo2());
				weatherInfo.append(sp);
				weatherInfo.append("SO2");
				weatherInfo.append(sp);
				weatherInfo.append(weather.getSo2());
				weatherInfo.append(sp);
				weatherInfo.append("O3");
				weatherInfo.append(sp);
				weatherInfo.append(weather.getO3());
				weatherInfo.append(sp);
				weatherInfo.append("CO");
				weatherInfo.append(sp);
				weatherInfo.append(weather.getCo());
				weatherInfo.append(sp);*/
				weatherInfo.append(DateUtil.getFormatDate(weather.getLastUpdate(), "")+"");
			}
			
	
			mv.addObject("orgInfo",remark);
			mv.addObject("weather", weatherInfo.toString());
			mv.addObject("vesion", SystemConstant.WEB_SYSTEM_VERSION);
			mv.addObject("userInfo", userInfo);
			mv.setViewName("/pages/main.jsp");
		} catch (Exception e) {
			log.info("登录异常："+e);
			mv.addObject("msg",SystemConstant.MSG_EXCEPTION);//0表示异常
			mv.setViewName("/pages/login/login.jsp");
		}
		return mv;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout() {
		ModelAndView mv = new ModelAndView();
		session.removeAttribute("user");
		mv.setViewName("/pages/index.jsp");
		return mv;
	}
}
