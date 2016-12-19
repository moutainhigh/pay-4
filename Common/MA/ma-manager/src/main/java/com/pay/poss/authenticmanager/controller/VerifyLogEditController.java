package com.pay.poss.authenticmanager.controller;


import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.poss.authenticmanager.common.Constants;
import com.pay.poss.authenticmanager.dto.VerifyLogDto;
import com.pay.poss.authenticmanager.enums.PoliceMessageEnum;
import com.pay.poss.authenticmanager.enums.VerifyLogStatusEnum;
import com.pay.poss.authenticmanager.service.IAuthenticService;
import com.pay.poss.base.common.SessionUserUtils;
import com.pay.poss.base.common.properties.CommonConfiguration;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.util.WebUtil;



/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		VerifyLogEditController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-11-11		gungun_zhang			Create
 */
 
 

public class VerifyLogEditController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(VerifyLogEditController.class);
	private IAuthenticService authenticService ;
	
    @Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("VerifyLogEditController.referenceData() is running...");
    	
    	String verifyId = request.getParameter("verifyId");
    	String type = request.getParameter("type");
    	String isView = "false";
    	VerifyLogDto verifyLogDto = this.authenticService.getVerifyLogById(verifyId);
    	if(!"3".equals(verifyLogDto.getVerifyStatus()) && !"4".equals(verifyLogDto.getVerifyStatus())){
    		type="view";
    		isView="true";
    	}
    	VerifyLogStatusEnum[] verifyLogStatusEnum = VerifyLogStatusEnum.values();
    	PoliceMessageEnum[] policeMessageEnum = PoliceMessageEnum.values();
    	
    	Map<String,Object> dataMap = new Hashtable<String,Object>();
    	
    	dataMap.put("verifyLogDto", verifyLogDto);
    	dataMap.put("type", type);
    	dataMap.put("isView", isView);
    	dataMap.put("verifyLogStatusEnum", verifyLogStatusEnum);
    	dataMap.put("policeMessageEnum", policeMessageEnum);
    	
		return dataMap;
	}

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {    
    		log.debug("VerifyLogEditController.onSubmit() is running...");
    		
    		String type =(String) request.getParameter("type");
    		String verifyId = request.getParameter("verifyId");
    		Map<String,Object> dataMap = new Hashtable<String,Object>();
    		String sign = null;
    		Boolean isSucess = false;
    		VerifyLogDto verifyLogDto = null;
    		PoliceMessageEnum[] policeMessageEnum = PoliceMessageEnum.values();
			if(StringUtils.isNotEmpty(type)&&StringUtils.isNotEmpty(verifyId)){
				SessionUserHolder opreate = SessionUserUtils.getUserInfo(request);
				if(opreate!=null){
									
					verifyLogDto = new VerifyLogDto();
					String ip = WebUtil.getIp(request);
					if(ip!=null){
						verifyLogDto.setUserIp(ip);	
					}else{
						verifyLogDto.setUserIp("未知");	
					}
					verifyLogDto.setOpLoginName(opreate.getUsername());	
					if(opreate.getLoginTime()!=null){
						verifyLogDto.setLoginTime(opreate.getLoginTime());	
					}else{
						verifyLogDto.setLoginTime(new Date());	
					}
					
					
					String policeMessage = request.getParameter("policeMessage");
					String memberCode = request.getParameter("memberCode");
					verifyLogDto.setVerifyId(verifyId);
					verifyLogDto.setMessage(policeMessage);
					verifyLogDto.setMemberCode(memberCode);
				}
				String value = CommonConfiguration.getStrProperties(Constants.Properties_ISFREE);
				if(value!=null){
					if(value.equals("true")){
						isSucess = authenticService.updateVerifyLogStatusFreeTrans(verifyLogDto);
					}else if(value.equals("false")){
						isSucess = authenticService.updateVerifyLogStatusTrans(verifyLogDto);
					}
				}
	    		
	    	} else{
	    			sign = "参数未知,操作失败!请与管理员联系...";
	    			verifyLogDto = this.authenticService.getVerifyLogById(verifyId);
	    	    	VerifyLogStatusEnum[] verifyLogStatusEnum = VerifyLogStatusEnum.values();
	    	    	
	    	    		    	    	
	    	    	dataMap.put("sign", sign);
	    	    	dataMap.put("verifyLogDto", verifyLogDto);
	    	    	dataMap.put("type", type);
	    	    	dataMap.put("verifyLogStatusEnum", verifyLogStatusEnum);
	    	    	dataMap.put("policeMessageEnum", policeMessageEnum);
	    			
	    	    	return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
	    	}
				
			if(isSucess){
				sign="操作成功!" ;
				dataMap.put("edited", "edited");
			}else{
				sign="操作失败!请与管理员联系..." ;
				dataMap.put("type", type);
				dataMap.put("policeMessageEnum", policeMessageEnum);
			}
			verifyLogDto = this.authenticService.getVerifyLogById(verifyId);
	    	VerifyLogStatusEnum[] verifyLogStatusEnum = VerifyLogStatusEnum.values();
	    		    
	    	dataMap.put("sign", sign);
	    	dataMap.put("verifyLogDto", verifyLogDto);	    	
	    	dataMap.put("verifyLogStatusEnum", verifyLogStatusEnum);
	    	dataMap.put("policeMessageEnum", policeMessageEnum);
			
	    	return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);	
			            
   }

	public void setAuthenticService(IAuthenticService authenticService) {
		this.authenticService = authenticService;
	}
    
}
