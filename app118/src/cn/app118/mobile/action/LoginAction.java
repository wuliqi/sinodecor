/**
 * @(#)UserAction.java	06/27/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-06-27
 */
package cn.app118.mobile.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.PageFans;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.PageFansBean;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.javabeans.weibo.Company;
import com.qq.connect.oauth.Oauth;

/**
 * 大赢家第三方登录Action
 * 
 * @author 吴理琪
 * 
 */
@Controller
@RequestMapping("loginAction")
public class LoginAction extends BaseAction {

	@RequestMapping("init")
	public ModelAndView init(String flag) {
		ModelAndView mv = new ModelAndView("/mobile/index.jsp");
		mv.addObject("loginFlag", flag);
		return mv;
	}

	@RequestMapping("login")
	@ResponseBody
	public void login(HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		try {
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
			String url = new Oauth().getAuthorizeURL(request);
			System.out.println("***URL:" + url);
		} catch (Exception e) {
			System.out.println("#######QQ登录异常：");
			e.printStackTrace();
		}
	}

	@RequestMapping("callback")
	public ModelAndView callback(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/mobile/index.jsp");
		try {
			PrintWriter out = response.getWriter();
			// 获取QQ
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

			String accessToken = null, openID = null;
			long tokenExpireIn = 0L;

			if (accessTokenObj.getAccessToken().equals("")) {
				// 我们的网站被CSRF攻击了或者用户取消了授权
				// 做一些数据统计工作
				out.print("没有获取到响应参数。\n");
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();

				session.setAttribute("accessToken",accessToken);
				session.setAttribute("tokenExpirein",String.valueOf(tokenExpireIn));

				// 利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();

				out.println("欢迎你，代号为 " + openID + " 的用户!");
				session.setAttribute("openId", openID);
				out.println("<a href=" + "/shuoshuoDemo.html"
						+ " target=\"_blank\">去看看发表说说的demo吧</a>");
				// 利用获取到的accessToken 去获取当前用户的openid --------- end

				out
						.println("<p> start -----------------------------------利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息 ---------------------------- start </p>");
				UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
				UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
				out.println("<br/>");
				if (userInfoBean.getRet() == 0) {
					out.println(userInfoBean.getNickname() + "<br/>");
					out.println( userInfoBean.getGender()+ "<br/>");
					out.println("黄钻等级： " + userInfoBean.getLevel()
							+ "<br/>");
					out
							.println("会员 : " + userInfoBean.isVip() + "<br/>");
					out.println("黄钻会员： "
							+ userInfoBean.isYellowYearVip() + "<br/>");
					out.println("<image src="
							+ userInfoBean.getAvatar().getAvatarURL30()
							+ "/><br/>");
					out.println("<image src="
							+ userInfoBean.getAvatar().getAvatarURL50()
							+ "/><br/>");
					out.println("<image src="
							+ userInfoBean.getAvatar().getAvatarURL100()
							+ "/><br/>");
					
					mv.addObject("nickName", userInfoBean.getNickname() );//昵称
					mv.addObject("gender", userInfoBean.getGender() );//性别
					mv.addObject("avatarURL", userInfoBean.getAvatar().getAvatarURL30() );//头像
				} else {
					out.println("很抱歉，我们没能正确获取到您的信息，原因是： "
							+ userInfoBean.getMsg());
				}
				out
						.println("<p> end -----------------------------------利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息 ---------------------------- end </p>");

				out
						.println("<p> start ----------------------------------- 验证当前用户是否为认证空间的粉丝------------------------------------------------ start <p>");
				PageFans pageFansObj = new PageFans(accessToken, openID);
				PageFansBean pageFansBean = pageFansObj
						.checkPageFans("97700000");
				if (pageFansBean.getRet() == 0) {
					out.println("<p>验证您"
							+ (pageFansBean.isFans() ? "是" : "不是")
							+ "QQ空间97700000官方认证空间的粉丝</p>");
				} else {
					out.println("很抱歉，我们没能正确获取到您的信息，原因是： "
							+ pageFansBean.getMsg());
				}
				out
						.println("<p> end ----------------------------------- 验证当前用户是否为认证空间的粉丝------------------------------------------------ end <p>");

				out
						.println("<p> start -----------------------------------利用获取到的accessToken,openid 去获取用户在微博的昵称等信息 ---------------------------- start </p>");
				com.qq.connect.api.weibo.UserInfo weiboUserInfo = new com.qq.connect.api.weibo.UserInfo(
						accessToken, openID);
				com.qq.connect.javabeans.weibo.UserInfoBean weiboUserInfoBean = weiboUserInfo
						.getUserInfo();
				if (weiboUserInfoBean.getRet() == 0) {
					// 获取用户的微博头像----------------------start
					out.println("<image src="
							+ weiboUserInfoBean.getAvatar().getAvatarURL30()
							+ "/><br/>");
					out.println("<image src="
							+ weiboUserInfoBean.getAvatar().getAvatarURL50()
							+ "/><br/>");
					out.println("<image src="
							+ weiboUserInfoBean.getAvatar().getAvatarURL100()
							+ "/><br/>");
					// 获取用户的微博头像 ---------------------end

					// 获取用户的生日信息 --------------------start
					out.println("<p>尊敬的用户，你的生日是： "
							+ weiboUserInfoBean.getBirthday().getYear() + "年"
							+ weiboUserInfoBean.getBirthday().getMonth() + "月"
							+ weiboUserInfoBean.getBirthday().getDay() + "日");
					// 获取用户的生日信息 --------------------end

					StringBuffer sb = new StringBuffer();
					sb.append("<p>所在地:" + weiboUserInfoBean.getCountryCode()
							+ "-" + weiboUserInfoBean.getProvinceCode() + "-"
							+ weiboUserInfoBean.getCityCode()
							+ weiboUserInfoBean.getLocation());

					// 获取用户的公司信息---------------------------start
					ArrayList<Company> companies = weiboUserInfoBean
							.getCompanies();
					if (companies.size() > 0) {
						// 有公司信息
						for (int i = 0, j = companies.size(); i < j; i++) {
							sb.append("<p>曾服役过的公司：公司ID-"
									+ companies.get(i).getID() + " 名称-"
									+ companies.get(i).getCompanyName()
									+ " 部门名称-"
									+ companies.get(i).getDepartmentName()
									+ " 开始工作年-"
									+ companies.get(i).getBeginYear()
									+ " 结束工作年-" + companies.get(i).getEndYear());
						}
					} else {
						// 没有公司信息
					}
					// 获取用户的公司信息---------------------------end
					out.println(sb.toString());

				} else {
					out.println("很抱歉，我们没能正确获取到您的信息，原因是： "
							+ weiboUserInfoBean.getMsg());
				}
				out
						.println("<p> end -----------------------------------利用获取到的accessToken,openid 去获取用户在微博的昵称等信息 ---------------------------- end </p>");

			}
			log.info("login QQ finished.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("loginFlag", 1);
		log.info("---------------qq callback  end--------------------");
		return mv;
	}
}
