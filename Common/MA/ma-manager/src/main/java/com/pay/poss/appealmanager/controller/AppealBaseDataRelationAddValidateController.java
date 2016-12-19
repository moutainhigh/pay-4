package com.pay.poss.appealmanager.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.poss.appealmanager.service.IDataService;
import com.pay.util.SpringControllerUtils;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealBaseDataRelationAddValidateController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-1-4		gungun_zhang			Create
 */

public class AppealBaseDataRelationAddValidateController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(AppealBaseDataRelationAddValidateController.class);
	private IDataService dataService;
	
	@Override
	 protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
			 Object command,BindException errors) throws Exception {  
    	log.debug("AppealBaseDataRelationAddValidateController.onSubmit() is running...");
    	
    	String fatherDataCode = request.getParameter("fatherDataCode");
    	String sonDataCode = request.getParameter("sonDataCode");
    	
    	String validateResult = dataService.isRelationExist(fatherDataCode,sonDataCode);
    	if(validateResult.equals("true")){
			SpringControllerUtils.renderText(response, "false");
		}else{
			SpringControllerUtils.renderText(response, "true");
		}
    	
        return null;
	}

	public void setDataService(IDataService dataService) {
		this.dataService = dataService;
	}
	
   
  
		          
  
   
   
	
	
}
