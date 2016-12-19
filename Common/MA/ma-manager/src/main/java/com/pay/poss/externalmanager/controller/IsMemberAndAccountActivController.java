package com.pay.poss.externalmanager.controller;



import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pay.poss.externalmanager.common.Constants;
import com.pay.poss.externalmanager.dto.IsMemberLegal;
import com.pay.poss.externalmanager.service.IExternalService;
import com.pay.util.JSonUtil;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		IsMemberAndAccountActivController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-5		gungun_zhang			Create
 */


public class IsMemberAndAccountActivController extends AbstractController {
	
	private Log log = LogFactory.getLog(IsMemberAndAccountActivController.class);
	private IExternalService externalService ;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("IsMemberAndAccountActivController.handleRequestInternal is running...");
		
		response.setCharacterEncoding("UTF-8");
		String memberEmail = request.getParameter("memberEmail");
		//String memberOrgCode = request.getParameter("memberOrgCode");
		IsMemberLegal isMemberLegal = new IsMemberLegal();
		
		//if(StringUtils.isEmpty(memberEmail)||StringUtils.isEmpty(memberOrgCode)){
		if(StringUtils.isEmpty(memberEmail)){
			isMemberLegal.setReturnBool(false);
			isMemberLegal.setMsg(Constants.ERROR_PARAM);
		}else{
			isMemberLegal = this.externalService.isMemberAndAccountActiv(memberEmail.trim(), "123");
		}
		String returnJson = JSonUtil.toJSonString(isMemberLegal);
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		out.close();
		return null;
	}

	public void setExternalService(IExternalService externalService) {
		this.externalService = externalService;
	}
	
	

}
