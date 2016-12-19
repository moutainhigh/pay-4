package com.pay.poss.appealmanager.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.poss.appealmanager.service.IAppealService;
import com.pay.util.SpringControllerUtils;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		IsAppealStateValidateController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-31		gungun_zhang			Create
 */

public class IsAppealStateValidateController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(IsAppealStateValidateController.class);
	private IAppealService appealService ;
	
	@Override
	 protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
			 Object command,BindException errors) throws Exception {  
    	log.debug("IsAppealStateValidateController.onSubmit() is running...");
    	
    	String appealId = request.getParameter("appealId");
    	String appealState = request.getParameter("appealState");
    	String validateResult = appealService.isAppealStateValidate(appealId,appealState);
    	if(validateResult.equals("true")){
			SpringControllerUtils.renderText(response, "true");
		}else{
			SpringControllerUtils.renderText(response, "false");
		}
    	
        return null;
	}
	public void setAppealService(IAppealService appealService) {
		this.appealService = appealService;
	}
   
  
		          
  
   
   
	
	
}
