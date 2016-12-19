package com.pay.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.app.common.helper.AppConf;
import com.pay.base.common.enums.OrginEnum;




/**
 *  File: AppFilter.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date     2010-9-2     
 *  Author   zengjin     
 *  Changes   
 *  Comment 会话过滤器
 */
public class AppFilter implements Filter{
	
	private static final Log logger = LogFactory.getLog(AppFilter.class);
	private static final String loginOutUrl="/outapp.htm";
	private static final String apploginOutUrl="/logout.htm";
	//private static 	final String checkUserUrl="/sso.htm";
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
	

		String forwadrUrl=loginOutUrl;
		
		String requestPath=request.getRequestURI();
		
		//String callBackUrl=AppConf.defaultCallBack;
	
		logger.info("appfilter lisenter  requestPath is :"+requestPath);

		
		/*****************判断用户是否登录而且为正常 用户********************/
		if(AppFilterCommon.isNormalUser(request)){
		    if(!AppFilterCommon.isSignature()){ //判断用户是否登录验签是否正确
	            RequestDispatcher rd=servletRequest.getRequestDispatcher(forwadrUrl);
	            logger.info("appfilter writer the log  [signature  is false will forward :"+apploginOutUrl+"]");
	            rd.forward(servletRequest, servletResponse);
	            return;
	        }
		    if(!AppFilterCommon.isNormalStatusUser(request)){//判断用户状态是否正常
            	RequestDispatcher rd=request.getRequestDispatcher("/error.htm?method=illegal");
            	logger.info("appfilter writer the log  [status  is error ]");
            	rd.forward(servletRequest, servletResponse);
            	return ;
            }
		    AppFilterCommon.makeMenuSelect(request);
			logger.info("appfilter writer the log  user login success  [memberCode:"+request.getSession().getAttribute("memberCode")+"]");
			//filterChain.doFilter(new XSSHttpServletRequestWrapper(request), servletResponse);
			filterChain.doFilter(new XSSHttpServletRequestWrapper(
					(HttpServletRequest) servletRequest), servletResponse);
			return;
		} 
		/*****************记录登录前请求的url********************/
		AppFilterCommon.setCallBackUri(request,AppConf.defaultCallBack,OrginEnum.INDIVIDUAL_LOCAL.getValue(),1);
		/*****************跳登录页********************/
		RequestDispatcher rd=servletRequest.getRequestDispatcher(forwadrUrl);
		logger.info("appfilter writer the log  [no session  will forward :"+forwadrUrl+"]");
        rd.forward(servletRequest, servletResponse);

        return;
  
		
	}



	@Override
	public void init(FilterConfig arg0) throws ServletException {
	
	}
}
