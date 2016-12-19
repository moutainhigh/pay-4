package com.pay.poss.enterprisemanager.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.enterprisemanager.dto.AccountTempDto;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AccountTempListController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-1-20		gungun_zhang			Create
 */

public class AccountTempListController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(AccountTempListController.class);
	private IEnterpriseService enterpriseService ;
	
	
    @Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("AccountTempListController.referenceData() is running...");
        return null;
	}

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {    
    	log.debug("AccountTempListController.onSubmit() is running...");
    	
    	AccountTempDto accountTempDto = new AccountTempDto();		
    	Page<AccountTempDto> page = PageUtils.getPage(request);
    	accountTempDto.setPageEndRow((page.getPageNo()*page.getPageSize())+"");
		if((page.getPageNo()-1)==0){
			accountTempDto.setPageStartRow("0");
		}else{
			accountTempDto.setPageStartRow((page.getPageNo()-1)*page.getPageSize()+"");
		}
		List<AccountTempDto> accountTempList = enterpriseService.getAccountTempList(accountTempDto);		
		Integer accountTempListCount = enterpriseService.getAccountTempListCount(accountTempDto);
		
		page.setResult(accountTempList);
		page.setTotalCount(accountTempListCount);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		dataMap.put("page", page);
		
		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
		          
   }

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	
   
	
	
	
}
