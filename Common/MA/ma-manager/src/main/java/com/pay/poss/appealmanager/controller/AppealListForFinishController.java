package com.pay.poss.appealmanager.controller;


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
import com.pay.poss.appealmanager.dto.AppealTaskListDto;
import com.pay.poss.appealmanager.dto.AppealTaskSearchDto;
import com.pay.poss.appealmanager.dto.BaseDataDto;
import com.pay.poss.appealmanager.service.IAppealService;
import com.pay.poss.appealmanager.service.IDataService;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealListForDispenseController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-23		gungun_zhang			Create
 */

public class AppealListForFinishController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(AppealListForFinishController.class);
	private IAppealService appealService ;
	private IDataService dataService;
	
    @Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("AppealListForDispenseController.referenceData() is running...");
        return null;
	}

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {    
    	log.debug("AppealListForDispenseController.onSubmit() is running...");
    	
    	AppealTaskSearchDto appealTaskSearchDto = new AppealTaskSearchDto();		
    	Page<AppealTaskListDto> page = PageUtils.getPage(request);
    	appealTaskSearchDto.setPageEndRow((page.getPageNo()*page.getPageSize())+"");
		if((page.getPageNo()-1)==0){
			appealTaskSearchDto.setPageStartRow("0");
		}else{
			appealTaskSearchDto.setPageStartRow((page.getPageNo()-1)*page.getPageSize()+"");
		}
		List<AppealTaskListDto> appealList = appealService.getAppealListForFinish(appealTaskSearchDto);		
		Integer appealListCount = appealService.getAppealListForFinishCount(appealTaskSearchDto);
		
		page.setResult(appealList);
		page.setTotalCount(appealListCount);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<BaseDataDto> appealStatusList = dataService.getAppealStatus();
		dataMap.put("page", page);
		dataMap.put("appealStatusList", appealStatusList);
		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
		          
   }

	public void setAppealService(IAppealService appealService) {
		this.appealService = appealService;
	}

	public void setDataService(IDataService dataService) {
		this.dataService = dataService;
	}
   
	
	
	
}
