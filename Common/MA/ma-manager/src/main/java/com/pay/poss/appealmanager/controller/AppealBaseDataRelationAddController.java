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

import com.pay.poss.appealmanager.dto.BaseDataDto;
import com.pay.poss.appealmanager.dto.BaseDataRelationDto;
import com.pay.poss.appealmanager.formbean.AppealBaseDataRelationFormBean;
import com.pay.poss.appealmanager.service.IDataService;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealBaseDataRelationAddController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-1-4		gungun_zhang			Create
 */

public class AppealBaseDataRelationAddController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(AppealBaseDataRelationAddController.class);
	private IDataService dataService;
	
    @Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("AppealBaseDataRelationAddController.referenceData() is running...");    	
    	List<BaseDataDto> baseDataList = this.dataService.getAllBaseData();
    	
    	Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("baseDataList", baseDataList);		
		return dataMap;
	}

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {    
    	log.debug("AppealBaseDataRelationAddController.onSubmit() is running...");
    	
		AppealBaseDataRelationFormBean appealBaseDataRelationFormBean = (AppealBaseDataRelationFormBean)command;
	
		BaseDataRelationDto baseDataRelationDto = new BaseDataRelationDto();
		BeanUtils.copyProperties(appealBaseDataRelationFormBean,baseDataRelationDto);
		
		String sign = this.dataService.insertAppealBaseDataRelation(baseDataRelationDto);
		
		if(StringUtils.isEmpty(sign)){	
			sign = "基础数据添加失败!请与管理员联系..." ;
		}else{
			sign = "基础数据添加成功!";
			
	    }    
		List<BaseDataDto> baseDataList = this.dataService.getAllBaseData();
    	
    	Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("baseDataList", baseDataList);
		dataMap.put("sign", sign);
		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
		          
   }

	public void setDataService(IDataService dataService) {
		this.dataService = dataService;
	}
  


  
	
	
}
