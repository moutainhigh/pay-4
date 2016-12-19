package com.pay.poss.enterprisemanager.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.poss.enterprisemanager.dto.EnterpriseSearchListDto;
import com.pay.poss.enterprisemanager.formbean.EnterpriseSearchFormBean;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;
import com.pay.poss.merchantmanager.service.IMerchantService;


/**
 * 
 * @Description
 * @project poss-membermanager
 * @file EnterpriseMessageChangeController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-12-27 tianqing_wang  Create
 */

public class EnterpriseMessageChangeController extends SimpleFormController {

	private Log log = LogFactory.getLog(EnterpriseMessageChangeController.class);

	private IEnterpriseService enterpriseService ;
	
	private IMerchantService merchantService;

	@SuppressWarnings("unchecked")
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("EnterpriseMessageChangeController.referenceData() is running...");
		Map dataMap = new HashMap();
		String memberCode = request.getParameter("memberCode");
		//Long memberCode = this.getLongValue(strMemberCode);
		EnterpriseSearchListDto  dto = enterpriseService.queryEnterpriseByMemberCode(memberCode);
		dataMap.put("loginName", dto.getLoginName());
		dataMap.put("memberCode", dto.getMemberCode());
		return dataMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		log.debug("EnterpriseMessageChangeController.onSubmit() is running...");
		EnterpriseSearchFormBean formBean = (EnterpriseSearchFormBean) command;
		
		Map<String,String> tmpMap = new HashMap<String,String>();
		tmpMap.put("email",formBean.getLoginName());
		String isAllowResult = this.merchantService.isAllowCreateOrUpdateMerchant(tmpMap);
		if(StringUtils.isNotEmpty(isAllowResult)){
			return new ModelAndView(this.getSuccessView())
			.addObject("success",false)
			.addObject("errorMsg", isAllowResult)
			.addObject("loginName",request.getParameter("oldLoginName"))
			.addObject("memberCode",formBean.getMemberCode());
		}
		
		Map paramMap = new HashMap();
		paramMap.put("loginName", formBean.getLoginName());
		paramMap.put("memberCode", formBean.getMemberCode());
		Boolean success = enterpriseService.updateLoginNameOfEnterpriseTrans(paramMap);
		EnterpriseSearchListDto  dto = enterpriseService.queryEnterpriseByMemberCode(formBean.getMemberCode());
		return new ModelAndView(this.getSuccessView())
				.addObject("success",success)
				.addObject("loginName",dto.getLoginName())
				.addObject("memberCode",dto.getMemberCode());
	}

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	
	public void setMerchantService(IMerchantService merchantService) {
		this.merchantService = merchantService;
	}

}
