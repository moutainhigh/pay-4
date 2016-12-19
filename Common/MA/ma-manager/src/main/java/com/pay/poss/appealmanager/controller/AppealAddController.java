package com.pay.poss.appealmanager.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StringMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.poss.appealmanager.common.Constants;
import com.pay.poss.appealmanager.dto.AppealDto;
import com.pay.poss.appealmanager.formbean.AppealFormBean;
import com.pay.poss.appealmanager.service.IAppealService;
import com.pay.poss.appealmanager.service.IDataService;
import com.pay.poss.base.common.SessionUserUtils;
import com.pay.poss.security.model.SessionUserHolder;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealAddController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-19		gungun_zhang			Create
 */

public class AppealAddController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(AppealAddController.class);
	private IAppealService appealService ;
	private IDataService dataService;
	
    @Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("AppealAddController.referenceData() is running...");
    	
    	
    	Map<String,Object> dataMap = this.initData();
     	
		return dataMap;
	}

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {    
    	log.debug("AppealAddController.onSubmit() is running...");
    	
		AppealFormBean appealFormBean = (AppealFormBean)command;
	
		AppealDto appealDto = new AppealDto();
		BeanUtils.copyProperties(appealFormBean,appealDto);
		SessionUserHolder user = SessionUserUtils.getUserInfo(request);
		appealDto.setOperatorId(user.getUserKy());
		appealDto.setOperatorDeptCode(user.getOrgCode());
		appealDto.setAppealStatusCode(Constants.STATUS_CREATED);
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
		
		MultipartFile imgFile1  =  multipartRequest.getFile("picture1"); 
		MultipartFile imgFile2  =  multipartRequest.getFile("picture2"); 
		MultipartFile imgFile3  =  multipartRequest.getFile("picture3"); 
		MultipartFile imgFile4  =  multipartRequest.getFile("picture4"); 
		MultipartFile imgFile5  =  multipartRequest.getFile("picture5"); 
		MultipartFile imgFile6  =  multipartRequest.getFile("picture6"); 
		Map<String,MultipartFile> multipartFileMap = new HashMap<String,MultipartFile>();
		multipartFileMap.put("picture1", imgFile1);
		multipartFileMap.put("picture2", imgFile2);
		multipartFileMap.put("picture3", imgFile3);
		multipartFileMap.put("picture4", imgFile4);
		multipartFileMap.put("picture5", imgFile5);
		multipartFileMap.put("picture6", imgFile6);
		String validateMessage = this.appealService.insertAppealPictureValidate(multipartFileMap);
		String sign = null;
		if(StringUtils.isNotEmpty(validateMessage)){
			sign = validateMessage;
		}else{
			String appealCode = this.appealService.insertAppealTrans(appealDto,multipartFileMap);
			
			if(StringUtils.isEmpty(appealCode)){	
				sign = "申诉添加失败!请与管理员联系..." ;
			}else{
				sign = "申诉添加成功!申诉号：" + appealCode ;
				
		           
			}
		}
		
		
    	Map<String,Object> dataMap = this.initData();	    	    
    	dataMap.put("sign", sign);
		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
		          
   }
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
    throws ServletException {
   
    binder.registerCustomEditor(String.class, new StringMultipartFileEditor());
   
    }



    private Map<String,Object> initData(){
    	
    	List<String> baseDataTypeList = new ArrayList<String>();
    	baseDataTypeList.add(Constants.TYPE_APPEALSOURCE);
    	
    	return this.dataService.getBaseDataByType(baseDataTypeList);
    }
    
	public void setAppealService(IAppealService appealService) {
		this.appealService = appealService;
	}

	public void setDataService(IDataService dataService) {
		this.dataService = dataService;
	}
	
	
	
}
