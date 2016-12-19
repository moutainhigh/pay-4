package com.pay.poss.appealmanager.controller;


import java.util.ArrayList;
import java.util.HashMap;
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
import com.pay.poss.appealmanager.common.Constants;
import com.pay.poss.appealmanager.dto.AppealTaskListDto;
import com.pay.poss.appealmanager.dto.AppealTaskSearchDto;
import com.pay.poss.appealmanager.dto.BaseDataDto;
import com.pay.poss.appealmanager.formbean.AppealTaskSearchFormBean;
import com.pay.poss.appealmanager.service.IAppealService;
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
public class AppealListForAdminSearchController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(AppealListForAdminSearchController.class);
	private IAppealService appealService ;
	private IDataService dataService;
	
    @Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("AppealListForAdminSearchController.referenceData() is running...");
    	List<BaseDataDto> appealStatusList = this.dataService.getAppealStatus();
    	Map<String,Object> dataMap = this.initData();    	
    	dataMap.put("appealStatusList", appealStatusList);
        return dataMap;     
	}

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {    
    	log.debug("AppealListForAdminSearchController.onSubmit() is running...");
    	
    	AppealTaskSearchFormBean appealTaskSearchFormBean = (AppealTaskSearchFormBean) command;
    	AppealTaskSearchDto appealTaskSearchDto = new AppealTaskSearchDto();
    	
    	BeanUtils.copyProperties(appealTaskSearchFormBean, appealTaskSearchDto);
    	Page<AppealTaskListDto> page = PageUtils.getPage(request);
    	appealTaskSearchDto.setPageEndRow((page.getPageNo()*page.getPageSize())+"");
		if((page.getPageNo()-1)==0){
			appealTaskSearchDto.setPageStartRow("0");
		}else{
			appealTaskSearchDto.setPageStartRow((page.getPageNo()-1)*page.getPageSize()+"");
		}
		List<AppealTaskListDto> appealList = appealService.getAppealListForSearch(appealTaskSearchDto);		
		Integer appealListCount = appealService.getAppealListForSearchCount(appealTaskSearchDto);
		
		page.setResult(appealList);
		page.setTotalCount(appealListCount);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<BaseDataDto> appealStatusList = dataService.getAppealStatus();
		dataMap.put("page", page);
		dataMap.put("appealStatusList", appealStatusList);
		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
		          
   }
    private Map<String,Object> initData(){    	
    	List<String> baseDataTypeList = new ArrayList<String>();
    	baseDataTypeList.add(Constants.TYPE_APPEALSOURCE);
    	baseDataTypeList.add(Constants.TYPE_BUSINESSTYPE);
    	baseDataTypeList.add(Constants.TYPE_PRODUCTBIGTYPE);
    	baseDataTypeList.add(Constants.TYPE_PRODUCTLITTLETYPE);
    	baseDataTypeList.add(Constants.TYPE_CUSTOMERREPLY);
    	baseDataTypeList.add(Constants.TYPE_APPEALTYPE);
    	baseDataTypeList.add(Constants.TYPE_APPEALLEVEL);
    	baseDataTypeList.add(Constants.DEPT);
    	baseDataTypeList.add(Constants.RELATION);
    	
    	return this.dataService.getBaseDataByType(baseDataTypeList);
    }
	public void setAppealService(IAppealService appealService) {
		this.appealService = appealService;
	}

	public void setDataService(IDataService dataService) {
		this.dataService = dataService;
	}
   
	
	
	
}
