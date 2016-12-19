package com.pay.poss.base.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.pay.poss.security.model.SessionUserHolder;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		SessionUserUtils.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-20		gungun_zhang			Create
 */
public  class SessionUserUtils {
	public static SessionUserHolder getUserInfo(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null ) {
			Object sessionObj = authentication.getPrincipal() ;
			SessionUserHolder  sessionUserHolder = null;
			if(sessionObj instanceof SessionUserHolder){
				sessionUserHolder = (SessionUserHolder) sessionObj;
			}
			return sessionUserHolder;
		}
		return null;
	}
}
