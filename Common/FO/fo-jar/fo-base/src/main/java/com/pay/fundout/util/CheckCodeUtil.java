/**
 *  File: CheckCodeUtil.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-13   terry     Create
 *
 */
package com.pay.fundout.util;

import javax.servlet.http.HttpServletRequest;

import com.pay.util.StringUtil;

/**
 * 验证码校验
 */
public class CheckCodeUtil {

	/**
	 * 
	 * @param request
	 * @param checkCode
	 * @return
	 */
	public static boolean check(HttpServletRequest request,
			final String checkCode) {

		String rand = (String) request.getSession().getAttribute("rand");
		request.getSession().removeAttribute("rand");
		return !StringUtil.isEmpty(checkCode) && !StringUtil.isEmpty(rand)
				&& rand.equalsIgnoreCase(checkCode);
	}
}
