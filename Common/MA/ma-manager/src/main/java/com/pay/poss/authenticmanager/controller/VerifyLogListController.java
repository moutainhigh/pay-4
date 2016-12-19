package com.pay.poss.authenticmanager.controller;


import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.authenticmanager.dto.VerifySearchDto;
import com.pay.poss.authenticmanager.dto.VerifySearchListDto;
import com.pay.poss.authenticmanager.enums.PoliceMessageEnum;
import com.pay.poss.authenticmanager.enums.VerifyLogStatusEnum;
import com.pay.poss.authenticmanager.formbean.VerifySearchFormBean;
import com.pay.poss.authenticmanager.service.IAuthenticService;
/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		VerifyLogListController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-11-10		gungun_zhang			Create
 */

public class VerifyLogListController extends SimpleFormController {

	private Log log = LogFactory.getLog(VerifyLogListController.class);
	private IAuthenticService authenticService;
	
	@Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("VerifyLogListController.referenceData is running...");
		VerifyLogStatusEnum[] verifyLogStatusEnum =VerifyLogStatusEnum.values();
		Map<String,Object> dataMap = new Hashtable<String,Object>();
		dataMap.put("verifyLogStatusEnum",verifyLogStatusEnum);
		return dataMap;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		log.debug("VerifyLogListController.onSubmit is running...");
		
		VerifySearchFormBean verifySearchFormBean = (VerifySearchFormBean) command;
		VerifySearchDto verifySearchDto = new VerifySearchDto();
		BeanUtils.copyProperties(verifySearchFormBean, verifySearchDto);
		
		Page<VerifySearchListDto> page = PageUtils.getPage(request);
		verifySearchDto.setPageEndRow((page.getPageNo()*page.getPageSize())+"");
		if((page.getPageNo()-1)==0){
			verifySearchDto.setPageStartRow("0");
		}else{
			verifySearchDto.setPageStartRow((page.getPageNo()-1)*page.getPageSize()+"");
		}
		List<VerifySearchListDto> verifyLogList = authenticService.queryVerifyLog(verifySearchDto);
		
		Integer verifyLogListCount = authenticService.queryVerifyLogCount(verifySearchDto);
		
		page.setResult(verifyLogList);
		page.setTotalCount(verifyLogListCount);
		
		VerifyLogStatusEnum[] verifyLogStatusEnum =VerifyLogStatusEnum.values();
		Map<String,Object> dataMap = new Hashtable<String,Object>();
		PoliceMessageEnum[] policeMessageEnum = PoliceMessageEnum.values();
		dataMap.put("verifyLogStatusEnum",verifyLogStatusEnum);
		dataMap.put("page",page);
		dataMap.put("policeMessageEnum",policeMessageEnum);
		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
	}

	public void setAuthenticService(IAuthenticService authenticService) {
		this.authenticService = authenticService;
	}

	
	
}
