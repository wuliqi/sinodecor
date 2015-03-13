/**
 * @(#)UserAction.java	05/22/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-22
 */
package cn.app118.model;

import java.util.Date;

/**
 * @title：用户实体类
 * @description: 用户实体信息类。
 * @author： wRitchie
 * @date： 2015-05-22 16:10
 */
public class User {
	private Integer userId;// 用户标识

	private String loginName;// 登录名称

	private String password;// 密码

	private String nickname;// 昵称

	private Date createTime;// 创建时间

	private Date lastChangePwdTime;// 最后修改密码时间

	private Date lastLoginTime;// 最后登录时间

	private String realName;// 真实姓名

	private String signature;// 签名

	private String photoIconUrl;// 头像

	private Date birthday;// 生日

	private String sex;// 性别

	private String phoneNumber;// 手机号码

	private String carType;// 汽车型号

	private String country;// 所属国家

	private String city;// 所在省/市

	private String district;// 所在区/县
	
	private String carCategory;//用户种类  2:出租车   4：私家车

	private Integer orgId;//组织机构标识  2015-09-11
	
	private Date modifyTime;//最后修改时间
	
	private String remark;//备注
	
	private String userType;//用户类型  1：后台 3：app 5:商城
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName == null ? null : loginName.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastChangePwdTime() {
		return lastChangePwdTime;
	}

	public void setLastChangePwdTime(Date lastChangePwdTime) {
		this.lastChangePwdTime = lastChangePwdTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName == null ? null : realName.trim();
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature == null ? null : signature.trim();
	}

	public String getPhotoIconUrl() {
		return photoIconUrl;
	}

	public void setPhotoIconUrl(String photoIconUrl) {
		this.photoIconUrl = photoIconUrl == null ? null : photoIconUrl.trim();
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex == null ? null : sex.trim();
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType == null ? null : carType.trim();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district == null ? null : district.trim();
	}

	public String getCarCategory() {
		return carCategory;
	}

	public void setCarCategory(String carCategory) {
		this.carCategory = carCategory;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	
	
	
	
}