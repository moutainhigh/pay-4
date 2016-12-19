package com.pay.poss.merchantmanager.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.pay.poss.security.model.SessionUserHolder;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file MerchantUtils.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-9-20 gungun_zhang Create
 */
public class MerchantUtils {

	public static SessionUserHolder getUserInfo(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		Object sessionObj = authentication.getPrincipal();
		SessionUserHolder sessionUserHolder = null;
		if (sessionObj instanceof SessionUserHolder) {
			sessionUserHolder = (SessionUserHolder) sessionObj;
		}
		return sessionUserHolder;
	}
}
