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
import com.pay.poss.appealmanager.dto.BaseDataRelationDto;
import com.pay.poss.appealmanager.formbean.AppealBaseDataRelationSearchFormBean;
import com.pay.poss.appealmanager.service.IDataService;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealBaseDataRelationListController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-1-3		gungun_zhang			Create
 */
public class AppealBaseDataRelationListController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(AppealBaseDataRelationListController.class);
	
	private IDataService dataService;
	
    @Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("AppealBaseDataRelationListController.referenceData() is running...");
    	List<BaseDataDto> baseDataList = this.dataService.getAllBaseData();
    	
    	Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("baseDataList", baseDataList);		
		return dataMap;
        
	}

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {    
    	log.debug("AppealBaseDataRelationListController.onSubmit() is running...");
    	String baseDataRelationId = request.getParameter("baseDataRelationId");    
    	String sign = null;
    	if(StringUtils.isNotEmpty(baseDataRelationId)){
			sign = this.dataService.deleteAppealBaseDataRelation(baseDataRelationId);
						
		}
    	AppealBaseDataRelationSearchFormBean appealBaseDataRelationSearchFormBean = (AppealBaseDataRelationSearchFormBean) command;
    	BaseDataRelationDto baseDataRelationDto = new BaseDataRelationDto();
    	
    	BeanUtils.copyProperties(appealBaseDataRelationSearchFormBean, baseDataRelationDto);
    	Page<BaseDataRelationDto> page = PageUtils.getPage(request);
    	baseDataRelationDto.setPageEndRow((page.getPageNo()*page.getPageSize())+"");
		if((page.getPageNo()-1)==0){
			baseDataRelationDto.setPageStartRow("0");
		}else{
			baseDataRelationDto.setPageStartRow((page.getPageNo()-1)*page.getPageSize()+"");
		}
		List<BaseDataRelationDto> baseDataRelationList = dataService.queryBaseDataRelation(baseDataRelationDto);		
		Integer baseDataRelationListCount = dataService.queryBaseDataRelationCount(baseDataRelationDto);
		
		page.setResult(baseDataRelationList);
		page.setTotalCount(baseDataRelationListCount);
    	List<BaseDataDto> baseDataList = this.dataService.getAllBaseData();
    	    	
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("page", page);
		dataMap.put("sign", sign);
		dataMap.put("baseDataList", baseDataList);
		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
		          
   }
  

	public void setDataService(IDataService dataService) {
		this.dataService = dataService;
	}
   
	
	
	
}
