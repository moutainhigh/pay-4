package com.pay.poss.merchantmanager.controller;



import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pay.poss.merchantmanager.service.IMerchantService;
import com.pay.util.SpringControllerUtils;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		ValidateController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-11-26		gungun_zhang			Create
 */


public class MerchantAddValidateController extends AbstractController {
	
	private Log log = LogFactory.getLog(MerchantAddValidateController.class);
	private IMerchantService merchantService;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("MerchantAddValidateController.handleRequestInternal is running...");
		String method = request.getParameter("method");
		
		if(StringUtils.isNotEmpty(method)){
			Map<String,String> paraMap = new HashMap<String,String>();
			if(method.equals("industry")){
				log.debug("MerchantAddValidateController.handleRequestInternal.validate.industry is running...");
				//验证mcc
				String industry = request.getParameter("industry");
				if(!Pattern.compile("^[0-9]*$").matcher(industry).matches()){
					SpringControllerUtils.renderText(response, "true");
					return null;
				}
				String isAllowResult = this.merchantService.isMccExist(industry);
				if(StringUtils.isEmpty(isAllowResult)){
					SpringControllerUtils.renderText(response, "false");
				}else{
					SpringControllerUtils.renderText(response, "true");
				}
			}else if(method.equals("bizLicenceCode")){
				//营业执照号码
				log.debug("MerchantAddValidateController.handleRequestInternal.validate.bizLicenceCode is running...");
				String bizLicenceCode = request.getParameter("bizLicenceCode");
				paraMap.put("bizLicenceCode",bizLicenceCode);
				String isAllowResult = this.merchantService.isAllowCreateOrUpdateMerchant(paraMap);
				if(StringUtils.isEmpty(isAllowResult)){
					SpringControllerUtils.renderText(response, "false");
				}else{
					SpringControllerUtils.renderText(response, "true");
				}
			}else if(method.equals("govCode")){
				//组织机构号码
				log.debug("MerchantAddValidateController.handleRequestInternal.validate.govCode is running...");
				String govCode = request.getParameter("govCode");
				paraMap.put("govCode",govCode);
				String isAllowResult = this.merchantService.isAllowCreateOrUpdateMerchant(paraMap);
				if(StringUtils.isEmpty(isAllowResult)){
					SpringControllerUtils.renderText(response, "false");
				}else{
					SpringControllerUtils.renderText(response, "true");
				}
			}else if(method.equals("taxCode")){
				//税务登记证号码
				log.debug("MerchantAddValidateController.handleRequestInternal.validate.taxCode is running...");
				String taxCode = request.getParameter("taxCode");
				paraMap.put("taxCode",taxCode);
				String isAllowResult = this.merchantService.isAllowCreateOrUpdateMerchant(paraMap);
				if(StringUtils.isEmpty(isAllowResult)){
					SpringControllerUtils.renderText(response, "false");
				}else{
					SpringControllerUtils.renderText(response, "true");
				}
			}else if(method.equals("email")){
				//登录名
				log.debug("MerchantAddValidateController.handleRequestInternal.validate.email is running...");
				String email = request.getParameter("email");
				paraMap.put("email",email);
				String isAllowResult = this.merchantService.isAllowCreateOrUpdateMerchant(paraMap);
				if(StringUtils.isEmpty(isAllowResult)){
					SpringControllerUtils.renderText(response, "false");
				}else{
					SpringControllerUtils.renderText(response, "true");
				}
			}else{
				log.debug("MerchantAddValidateController.handleRequestInternal.validate.method==null is running...");
				SpringControllerUtils.renderText(response, "false");
			}
		}
		return null;
	}

	public void setMerchantService(IMerchantService merchantService) {
		this.merchantService = merchantService;
	}

	
	
	

}
