/**
 *  File: GoActiveController.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-21   single_zhang     Create
 *
 */

package com.pay.app.controller.base.accountactive;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.facade.dto.MemberCriteria;

public class ToActiveController extends AbstractController{

	/**
	 * 补填信息页面
	 */
	private String activeMessage;
	private String activeReady;
	

	/**
	 * 此方法是连接到立即补全激活信息页面上去.
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginSession loginSession=SessionHelper.getLoginSession();
		if(loginSession!=null && loginSession.getActiveStatus().equals("1")){
			return new ModelAndView(activeReady);   
		}
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("criteria", new MemberCriteria());
		return new ModelAndView(activeMessage,paraMap);     
	}

	public void setActiveMessage(String activeMessage) {
		this.activeMessage = activeMessage;
	}
	public void setActiveReady(String activeReady) {
		this.activeReady = activeReady;
	}
}
