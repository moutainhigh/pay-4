/**
 * 
 */
package com.pay.poss.personmanager.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.poss.base.common.PossIpUtil;
import com.pay.poss.base.common.SessionUserUtils;
import com.pay.poss.personmanager.service.PersonMemberService;
import com.pay.poss.security.model.SessionUserHolder;

/**
 * @Description 个人会员操作
 * @project 	poss-membermanager
 * @file 		PersonalAcctListSearchController.java 
 * @note		<br>
 * @develop		JDK1.6 + SpringSource 2.3.2
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-19		tianqing_wang			Create
 */
public class PersonalMemberOperateController extends SimpleFormController {
	
	private Log log = LogFactory.getLog(PersonalMemberOperateController.class);
	public static final  String OPERATE_FROZEN = "frozen";
	public static final  String OPERATE_UNFROZEN = "unFrozen";
	public static final  String OPERATE_ALLOWIN = "allowIn";   
	public static final  String OPERATE_ALLOWOUT = "allowOut";  
	public static final  String OPERATE_UNALLOWIN = "unAllowIn";
	public static final  String OPERATE_UNALLOWOUT = "unAllowOut";

	
	private PersonMemberService personMemberService;
	

	@SuppressWarnings("unchecked")
	@Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("PersonalMemberOperateController.referenceData() is running...");
    	SessionUserHolder user = SessionUserUtils.getUserInfo(request);
    	String acctCode = (String)request.getParameter("acctCode");
    	String operate = (String)request.getParameter("operate");
    	String ip = PossIpUtil.getIp(request);
		Map paramMap = new HashMap();
    	paramMap.put("acctCode", acctCode);
    	paramMap.put("operate", operate);
    	if(null!=user){
    		if(OPERATE_FROZEN.equals(operate) || OPERATE_ALLOWIN.equals(operate) 
        			|| OPERATE_ALLOWOUT.equals(operate) ||OPERATE_UNFROZEN.equals(operate)
        			|| OPERATE_UNALLOWIN.equals(operate)|| OPERATE_UNALLOWOUT.equals(operate)){
        		//personMemberService.updatePersonalMemberAcctOperate(paramMap);
    			paramMap.put("loginIp", ip);
    			paramMap.put("loginName", user.getUsername());
        		personMemberService.updateAcctStatusTrans(paramMap);
        	}
    	}
		return paramMap;
	}

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
				BindException errors) throws Exception {  
		return null;
		//return new ModelAndView(this.getSuccessView());
	   }

	public void setPersonMemberService(PersonMemberService personMemberService) {
		this.personMemberService = personMemberService;
	}
	
}
