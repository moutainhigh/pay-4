package com.pay.poss.enterprisemanager.controller;


import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.enterprisemanager.common.Constants;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchDto;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchListDto;
import com.pay.poss.enterprisemanager.formbean.EnterpriseSearchFormBean;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;
/**
 * 
 * @Description
 * @project poss-membermanager
 * @file MerchantListController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-8-11 gungun_zhang Create
 */

public class EnterpriseListForAccountController extends SimpleFormController {

	private Log log = LogFactory.getLog(EnterpriseListForAccountController.class);
	private IEnterpriseService enterpriseService;
	
	@Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("EnterpriseListForAccountController.referenceData is running...");
		return null;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		log.debug("EnterpriseListForAccountController.onSubmit is running...");
		EnterpriseSearchFormBean enterpriseSearchFormBean = (EnterpriseSearchFormBean) command;
		EnterpriseSearchDto enterpriseSearchDto = new EnterpriseSearchDto();
		
		BeanUtils.copyProperties(enterpriseSearchFormBean, enterpriseSearchDto);
		
		if(StringUtils.isNotEmpty(enterpriseSearchFormBean.getMerchantName())){
			if(Pattern.compile(Constants.SEARCHKEY).matcher(enterpriseSearchFormBean.getMerchantName()).matches()){
				enterpriseSearchDto.setEnterpriseSearchKey(enterpriseSearchFormBean.getMerchantName());
				enterpriseSearchDto.setMerchantName(null);
			}
		}
		Page<EnterpriseSearchListDto> page = PageUtils.getPage(request);
		enterpriseSearchDto.setPageEndRow((page.getPageNo()*page.getPageSize())+"");
		if((page.getPageNo()-1)==0){
			enterpriseSearchDto.setPageStartRow("0");
		}else{
			enterpriseSearchDto.setPageStartRow((page.getPageNo()-1)*page.getPageSize()+"");
		}
		List<EnterpriseSearchListDto> enterpriseList = enterpriseService.queryEnterprise(enterpriseSearchDto);
		
		Integer enterpriseListCount = enterpriseService.queryEnterpriseCount(enterpriseSearchDto);
		
		page.setResult(enterpriseList);
		page.setTotalCount(enterpriseListCount);
		return new ModelAndView(this.getSuccessView()).addObject("page", page);
	}

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	
}
