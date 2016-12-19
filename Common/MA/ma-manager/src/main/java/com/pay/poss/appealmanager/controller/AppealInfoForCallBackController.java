package com.pay.poss.appealmanager.controller;


import java.util.ArrayList;
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

import com.pay.poss.appealmanager.common.Constants;
import com.pay.poss.appealmanager.dto.AppealDto;
import com.pay.poss.appealmanager.dto.BaseDataDto;
import com.pay.poss.appealmanager.formbean.AppealFormBean;
import com.pay.poss.appealmanager.model.AppealHistory;
import com.pay.poss.appealmanager.service.IAppealService;
import com.pay.poss.appealmanager.service.IDataService;
import com.pay.poss.base.common.SessionUserUtils;
import com.pay.poss.security.model.SessionUserHolder;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealInfoForCallBackController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-28		gungun_zhang			Create
 */

public class AppealInfoForCallBackController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(AppealInfoForCallBackController.class);
	private IAppealService appealService ;
	private IDataService dataService;
	
    @Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("AppealInfoForCallBackController.referenceData() is running...");
    	String appealId = request.getParameter("appealId");
    	AppealDto appealDto = this.appealService.getAppealById(appealId);
    	List<AppealHistory> appealHistoryList = this.appealService.getAppealHistoryByAppealId(appealId); 
    	List<BaseDataDto> appealStatusList = this.dataService.getAppealStatus();
    	Map<String,Object> dataMap = this.initData();
    	dataMap.put("appealDto", appealDto);
    	dataMap.put("appealHistoryList", appealHistoryList);
    	dataMap.put("appealStatusList", appealStatusList);
        return dataMap;
	}

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {    
    	log.debug("AppealInfoForCallBackController.onSubmit() is running...");
    	AppealFormBean appealFormBean =  (AppealFormBean)command;
    	AppealDto appealDto = new AppealDto();
    	BeanUtils.copyProperties(appealFormBean,appealDto);
    	SessionUserHolder user = SessionUserUtils.getUserInfo(request);
		appealDto.setOperatorId(user.getUserKy());
		appealDto.setOperatorDeptCode(user.getOrgCode());		
    	this.appealService.updateAppealTrans(appealDto);
    	return new ModelAndView(this.getSuccessView());
		          
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
    	baseDataTypeList.add(Constants.OPTERATOR);
    	
    	return this.dataService.getBaseDataByType(baseDataTypeList);
    }
	public void setAppealService(IAppealService appealService) {
		this.appealService = appealService;
	}

	public void setDataService(IDataService dataService) {
		this.dataService = dataService;
	}
   
	
	
	
}
