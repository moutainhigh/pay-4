package com.pay.app.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 *  Description:验证码专用的方法
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2011-8-23   ddr     Create
 *
 */
public final  class ValidateCodeUtils {
	
	public final static String  DEFAULT_RAND_NAME  = "rand"; 
	/**
	 * 比对session中的参数和request是否一样
	 * @param request
	 * @param sessionParam
	 * @param requestParam
	 * @return
	 */
	public static boolean validateCode(HttpServletRequest request,String sessionParam,String requestParam){
		
		String sessionValue = (String) request.getSession().getAttribute(sessionParam);
		String requestValue = request.getParameter(requestParam);
		if(requestValue!=null&&requestValue.trim().length()>0 && requestValue.equalsIgnoreCase(sessionValue) ){
			return true;
		}
		return false;
	}
	
	/**
	 * 比对验证码是否一样
	 * @param request
	 * @param requestParam
	 * @return
	 */
	public static boolean validateCode(HttpServletRequest request,String requestParam){
		boolean ok =  validateCode(request, DEFAULT_RAND_NAME, requestParam);
		removeAttr(request, DEFAULT_RAND_NAME);
		return ok;
	}
	
	/**
	 * 清除session对象
	 * @param request
	 * @param sessionParam
	 */
	public static void removeAttr(HttpServletRequest request,String sessionParam){
		request.getSession().removeAttribute(sessionParam);
	}
	/**
	 * 清除验证码
	 * @param request
	 */
	public static void removeCode(HttpServletRequest request){
		removeAttr(request, DEFAULT_RAND_NAME);
	}
	
	
}
