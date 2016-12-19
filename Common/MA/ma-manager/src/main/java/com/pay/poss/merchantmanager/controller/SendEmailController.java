package com.pay.poss.merchantmanager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pay.poss.merchantmanager.dto.MerchantDto;
import com.pay.poss.merchantmanager.service.IMerchantService;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		SendEmailController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-2		gungun_zhang			Create
 */


public class SendEmailController extends AbstractController{

	private Log log = LogFactory.getLog(SendEmailController.class);
	private IMerchantService merchantService ;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("SendEmailController is running...");
		String memberCode = request.getParameter("memberCode");
		if(StringUtils.isNotEmpty(memberCode)){
			List<MerchantDto> list  = merchantService.getMerchantListByMemberCode(memberCode);
			if(!CollectionUtils.isEmpty(list)){
				this.merchantService.sendEmailTrans(list.get(0));
			}
		
		}
		
		return null;
	}
	
	public void setMerchantService(IMerchantService merchantService) {
		this.merchantService = merchantService;
	}
		


}
