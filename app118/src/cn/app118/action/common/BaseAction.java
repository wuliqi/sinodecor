/**
 * @(#)UserAction.java	05/19/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-19
 */
package cn.app118.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @title :action层基类
 * @author: 吴理琪
 * @data: 2015-5-19
 */
public class BaseAction {
	public Logger log = Logger.getLogger(this.getClass());//日志
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpSession session;
}
