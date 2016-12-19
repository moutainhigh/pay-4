package com.pay.app.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.mvc.multiaction.ParameterMethodNameResolver;

import com.pay.app.controller.base.login.MemberController;


/**
 *  File: AjaxInterceptor.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date     2010-9-15     
 *  Author   wangzhi    
 *  Changes   
 *  Comment Ajax拦截器
 */
public class AjaxInterceptor  extends HandlerInterceptorAdapter {
	
	private static final Log logger = LogFactory.getLog(AjaxInterceptor.class);

	/**
	 * This implementation always returns <code>true</code>.
	 */
	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("进入Ajax拦截器。");
		}
		MultiActionController maControl = (MultiActionController) handler;   
		ParameterMethodNameResolver pmrResolver = (ParameterMethodNameResolver) maControl.getMethodNameResolver();   
        String methodName = pmrResolver.getHandlerMethodName(request);   
	

		String className = handler.getClass().getName();
		Class targetClass = Class.forName(className);  
        Method[] method = targetClass.getMethods();  
      
        
        if(StringUtils.equals(className, MemberController.class.getName())){
            if(logger.isDebugEnabled()){
                logger.debug("准备进入用户登录Controller");
            }
            
        }
        
      
         
        
        return true;
	}

	
	public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
	    System.out.println("postHandle .........");
    }

    /**
     * This implementation is empty.
     */
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("afterCompletion .........");
    }
}
