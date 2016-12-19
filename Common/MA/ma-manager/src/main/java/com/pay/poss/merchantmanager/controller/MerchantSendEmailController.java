package com.pay.poss.merchantmanager.controller;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.poss.merchantmanager.formbean.MerchantActivationFormBean;
import com.pay.poss.merchantmanager.service.IMerchantService;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		MerchantSendEmailController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-26		gungun_zhang			Create
 */


public class MerchantSendEmailController extends SimpleFormController {

	private Log log = LogFactory.getLog(MerchantSendEmailController.class);
	private IMerchantService merchantService ;

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("MerchantSendEmailController.referenceData() is running...");
		return new Hashtable<String, Object>();
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		log.debug("MerchantSendEmailController.onSubmit() is running...");
		MerchantActivationFormBean merchantActivationFormBean=(MerchantActivationFormBean)command; 
		String [] checkedArray = merchantActivationFormBean.getMerchantChecked();
		String sign = null;
		Boolean isSucess = true;
		if(checkedArray!=null&&checkedArray.length>0){			
			isSucess = this.merchantService.sendEmailForActivationTrans(checkedArray);
		}
		if(isSucess){
			sign = "发送成功!";
		}else{
			sign = "发送失败,请与管理员联系...";
		}
		Map<String,Object> dataMap = new Hashtable<String,Object>();
		dataMap.put("sign", sign);
		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
		
	}

	public void setMerchantService(IMerchantService merchantService) {
		this.merchantService = merchantService;
	}
	
	
		


}
