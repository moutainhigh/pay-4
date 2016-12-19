package com.pay.poss.enterprisemanager.controller;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.poss.enterprisemanager.dto.AccountTempDto;
import com.pay.poss.enterprisemanager.formbean.AccountTempFormBean;
import com.pay.poss.enterprisemanager.formbean.ProductJoinMenuFormBean;
import com.pay.poss.enterprisemanager.model.BaseData;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AccountTempJoinProductController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-1-20		gungun_zhang			Create
 */

public class AccountTempJoinProductController extends SimpleFormController {

	private Log log = LogFactory.getLog(AccountTempJoinProductController.class);
	private IEnterpriseService enterpriseService ;	
	

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("AccountTempJoinProductController.referenceData() is running...");
				
		String accountTempId = request.getParameter("accountTempId");
		AccountTempDto accountTemp = this.enterpriseService.getAccountTempById(accountTempId);
		String accountTempName = accountTemp.getAccountTempName();
				
		Map<String, Object> dataMap = this.initData(accountTempId);
		
		dataMap.put("accountTempId", accountTempId);
		dataMap.put("accountTempName", accountTempName);
		
		
		return dataMap;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		log.debug("AccountTempJoinProductController.onSubmit() is running...");
		AccountTempFormBean accountTempFormBean = (AccountTempFormBean)command;
		String productOfAccountTemp[] = accountTempFormBean.getFuncno();
		String accountTempId = accountTempFormBean.getAccountTempId();
		
		String accountTempName = accountTempFormBean.getAccountTempName();
		this.enterpriseService.productOfAccountTempEditTrans(productOfAccountTemp, accountTempId);
		Map<String,Object> dataMap = this.initData(accountTempId);
		dataMap.put("accountTempName",accountTempName);	
		dataMap.put("accountTempId",accountTempId);
		
		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
		
	}
	
	
	

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	private Map<String, Object> initData(String accountTempId){
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		List<BaseData> productOfAccountTempList = enterpriseService.getProductOfAccountTemp(accountTempId);
		List<BaseData> productAllList = enterpriseService.getAllProduct();
		List<BaseData> productNoList = new ArrayList<BaseData>();
		
		if(productOfAccountTempList!=null&&productAllList!=null){
			for(int i=0;i<productAllList.size();i++){
				BaseData bsAll = productAllList.get(i);
				Boolean sign = true;
				for(int j=0;j<productOfAccountTempList.size();j++){
					BaseData bsProduct = productOfAccountTempList.get(j);
					if(bsAll.getCode().equals(bsProduct.getCode())){
						sign = false;
					}
				}
				if(sign){
					productNoList.add(bsAll);
				}
			}
		}
		dataMap.put("productOfAccountTempList", productOfAccountTempList);
		dataMap.put("productNoList", productNoList);
		return dataMap;
	}
}
