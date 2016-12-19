package com.pay.poss.appealmanager.controller;


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
import com.pay.poss.appealmanager.formbean.AppealBaseDataFormBean;
import com.pay.poss.appealmanager.service.IDataService;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealBaseDataAddController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-1-2		gungun_zhang			Create
 */

public class AppealBaseDataAddController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(AppealBaseDataAddController.class);
	private IDataService dataService;
	
    @Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("AppealBaseDataAddController.referenceData() is running...");    	
     	
		return null;
	}

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {    
    	log.debug("AppealBaseDataAddController.onSubmit() is running...");
    	
		AppealBaseDataFormBean appealBaseDataFormBean = (AppealBaseDataFormBean)command;
	
		BaseDataDto BaseDataDto = new BaseDataDto();
		BeanUtils.copyProperties(appealBaseDataFormBean,BaseDataDto);
		
		String sign = this.dataService.insertAppealBaseData(BaseDataDto);
		
		if(StringUtils.isEmpty(sign)){	
			sign = "基础数据添加失败!请与管理员联系..." ;
		}else{
			sign = "基础数据添加成功!";
			
	    }    
		
		return new ModelAndView(this.getSuccessView()).addObject("sign",sign);
		          
   }

	public void setDataService(IDataService dataService) {
		this.dataService = dataService;
	}
  


  
	
	
}
