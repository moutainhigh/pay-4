package com.pay.poss.externalmanager.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;




/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		TestExternalController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-11-8		gungun_zhang			Create
 */
public class TestExternalController extends SimpleFormController {

	private Log log = LogFactory.getLog(TestExternalController.class);
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("TestExternalController.referenceData() is running...");

		
		return new HashMap<String, Object>();
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		log.debug("TestExternalController.onSubmit() is running...");
		
		return new ModelAndView(this.getSuccessView());
	}



}
