/**
 * @(#)UserAction.java	05/16/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-16
 */
package cn.app118.action.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cn.app118.constants.SystemConstant;

/**
 * @title :系统过滤器
 * @author: 吴理琪
 * @data: 2015-5-16
 */
public class App118Filter implements Filter {
	public Logger log = Logger.getLogger(this.getClass());
    private static String urls;
    private static boolean flag=false;//是否打印权限信息

    /**
     * @title:参数初始化
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        urls = filterConfig.getInitParameter("urls");
    }
    
    /**
     * @title: 系统初始化
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session=req.getSession();
        Map<String,Object> system=new HashMap<String,Object>();
        system.put("webName", SystemConstant.SYSTEM_WEB_NAME);
        system.put("icp", SystemConstant.SYSTEM_ICP);
        system.put("version", SystemConstant.WEB_SYSTEM_VERSION);
        session.setAttribute("system",system);
      
        String uri =  req.getRequestURI();
        req.setAttribute("path", req.getContextPath());
        //过滤器器中维护的特定url和模块中的jsp页面不进行拦截
        if(!uri.matches(urls) && uri.indexOf("/app/")!=0 && uri.indexOf("/client/")!=0){
        	if(flag)log.info("###非法请求："+uri);
            //如果没有登陆，或者请求session超时都返回重新登陆 
            Object so = req.getSession().getAttribute("user");
            if(so == null || so.equals("")){
                PrintWriter out = res.getWriter() ;
                out.print("<html>") ;
                out.print("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />") ;
                out.print("<script> ") ;   
                out.print("window.top.location='/index.jsp?msg=4';") ; 
                out.print("</script>") ; 
                out.print("</html>") ;
                return;
            }
         }
        if(flag)log.info("###正常请求："+uri);
        chain.doFilter(request, response);
    }
    
    /**
     * @title:系统关闭时的资源释放操作
     */
    public void destroy() {
        
    }
}
