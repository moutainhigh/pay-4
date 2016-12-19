package com.pay.poss.merchantmanager.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pay.poss.merchantmanager.service.IMerchantService;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		MerchantEditValidateController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-11-26		gungun_zhang			Create
 */


public class MerchantEditValidateController extends AbstractController {
	
	private Log log = LogFactory.getLog(MerchantEditValidateController.class);
	private IMerchantService merchantService;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		log.debug("MerchantAddValidateController.handleRequestInternal is running...");
//		String method = request.getParameter("method");
//		String memberCode = request.getParameter("memberCode");
//		MerchantDto merchantDtoOld = this.merchantService.getMerchantByMemberCode(memberCode);
//		
//		if(StringUtils.isNotEmpty(method)){
//			Map<String,String> paraMap = new HashMap<String,String>();
//			if(method.equals("industry")){
//				log.debug("MerchantAddValidateController.handleRequestInternal.validate.industry is running...");
//				//验证mcc
//				String industry = request.getParameter("industry");
//				if(!Pattern.compile("^[0-9]*$").matcher(industry).matches()){
//					SpringControllerUtils.renderText(response, "true");
//					return null;
//				}
//				String isAllowResult = this.merchantService.isMccExist(industry);
//				if(StringUtils.isEmpty(isAllowResult)){
//					SpringControllerUtils.renderText(response, "false");
//				}else{
//					SpringControllerUtils.renderText(response, "true");
//				}
//			}else if(method.equals("bizLicenceCode")){
//				//营业执照号码
//				log.debug("MerchantAddValidateController.handleRequestInternal.validate.bizLicenceCode is running...");
//				
//				String bizLicenceCode = request.getParameter("bizLicenceCode");
//				if(bizLicenceCode.equals(merchantDtoOld.getBizLicenceCode())){
//					SpringControllerUtils.renderText(response, "false");
//					return null;
//				}
//				paraMap.put("bizLicenceCode",bizLicenceCode);
//				String isAllowResult = this.merchantService.isAllowCreateOrUpdateMerchant(paraMap);
//				if(StringUtils.isEmpty(isAllowResult)){
//					SpringControllerUtils.renderText(response, "false");
//				}else{
//					SpringControllerUtils.renderText(response, "true");
//				}
//			}else if(method.equals("govCode")){
//				//组织机构号码
//				log.debug("MerchantAddValidateController.handleRequestInternal.validate.govCode is running...");
//				String govCode = request.getParameter("govCode");
//				if(govCode.equals(merchantDtoOld.getGovCode())){
//					SpringControllerUtils.renderText(response, "false");
//					return null;
//				}
//				paraMap.put("govCode",govCode);
//				String isAllowResult = this.merchantService.isAllowCreateOrUpdateMerchant(paraMap);
//				if(StringUtils.isEmpty(isAllowResult)){
//					SpringControllerUtils.renderText(response, "false");
//				}else{
//					SpringControllerUtils.renderText(response, "true");
//				}
//			}else if(method.equals("taxCode")){
//				//税务登记证号码
//				log.debug("MerchantAddValidateController.handleRequestInternal.validate.taxCode is running...");
//				String taxCode = request.getParameter("taxCode");
//				if(taxCode.equals(merchantDtoOld.getTaxCode())){
//					SpringControllerUtils.renderText(response, "false");
//					return null;
//				}
//				paraMap.put("taxCode",taxCode);
//				String isAllowResult = this.merchantService.isAllowCreateOrUpdateMerchant(paraMap);
//				if(StringUtils.isEmpty(isAllowResult)){
//					SpringControllerUtils.renderText(response, "false");
//				}else{
//					SpringControllerUtils.renderText(response, "true");
//				}
//			}else{
//				log.debug("MerchantAddValidateController.handleRequestInternal.validate.method==null is running...");
//				SpringControllerUtils.renderText(response, "false");
//			}
//		}
		return null;
	}

	public void setMerchantService(IMerchantService merchantService) {
		this.merchantService = merchantService;
	}

	
	
	

}
