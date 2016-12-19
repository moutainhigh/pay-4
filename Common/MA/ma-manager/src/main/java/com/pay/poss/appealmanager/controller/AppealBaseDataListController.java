package com.pay.poss.appealmanager.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.pay.poss.appealmanager.dto.BaseDataDto;
import com.pay.poss.appealmanager.formbean.AppealBaseDataSearchFormBean;
import com.pay.poss.appealmanager.service.IDataService;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealListForAdminSearchController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-31		gungun_zhang			Create
 */
public class AppealBaseDataListController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(AppealBaseDataListController.class);
	
	private IDataService dataService;
	
    @Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("AppealBaseDataListController.referenceData() is running...");
    	
        return null;     
	}

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {    
    	log.debug("AppealBaseDataListController.onSubmit() is running...");
    	String baseDataId = request.getParameter("baseDataId");    
    	String sign = null;
    	if(StringUtils.isNotEmpty(baseDataId)){
			sign = this.dataService.deleteAppealBaseData(baseDataId);
						
		}
    	AppealBaseDataSearchFormBean appealBaseDataSearchFormBean = (AppealBaseDataSearchFormBean) command;
    	BaseDataDto baseDataDto = new BaseDataDto();
    	
    	BeanUtils.copyProperties(appealBaseDataSearchFormBean, baseDataDto);
    	Page<BaseDataDto> page = PageUtils.getPage(request);
    	baseDataDto.setPageEndRow((page.getPageNo()*page.getPageSize())+"");
		if((page.getPageNo()-1)==0){
			baseDataDto.setPageStartRow("0");
		}else{
			baseDataDto.setPageStartRow((page.getPageNo()-1)*page.getPageSize()+"");
		}
		List<BaseDataDto> baseDataList = dataService.queryBaseData(baseDataDto);		
		Integer baseDataListCount = dataService.queryBaseDataCount(baseDataDto);
		
		page.setResult(baseDataList);
		page.setTotalCount(baseDataListCount);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("page", page);
		dataMap.put("sign", sign);
		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
		          
   }
  

	public void setDataService(IDataService dataService) {
		this.dataService = dataService;
	}
   
	
	
	
}
