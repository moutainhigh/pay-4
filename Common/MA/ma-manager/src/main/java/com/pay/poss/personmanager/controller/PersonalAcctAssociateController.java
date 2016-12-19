/**
 * 
 */
package com.pay.poss.personmanager.controller;

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
import com.pay.poss.personmanager.dto.PersonalAcctAssociateDto;
import com.pay.poss.personmanager.formbean.PersonSearchFormBean;
import com.pay.poss.personmanager.service.PersonMemberService;

/**
 * @Description 个人会员IP关联
 * @project 	poss-membermanager
 * @file 		PersonalAcctListSearchController.java 
 * @note		<br>
 * @develop		JDK1.6 + SpringSource 2.3.2
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-20		tianqing_wang			Create
 */
public class PersonalAcctAssociateController extends SimpleFormController {
	
	private Log log = LogFactory.getLog(PersonalAcctAssociateController.class);
	private PersonMemberService personMemberService;
	

	@SuppressWarnings("unchecked")
	@Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("PersonalAcctAssociateController.referenceData() is running...");
    	String loginIp = request.getParameter("loginIp");
    	Map paramMap = new HashMap();
    	paramMap.put("loginIp", loginIp);

		return paramMap;
	}

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
				BindException errors) throws Exception {  
		PersonSearchFormBean personSearchFormBean = (PersonSearchFormBean)command;
    	Page<PersonalAcctAssociateDto> page = PageUtils.getPage(request);
    	List<PersonalAcctAssociateDto> info  = personMemberService.queryPersonalAcctAssociatelist(personSearchFormBean,page);
    	page.setResult(info);
		return new ModelAndView(this.getSuccessView()).addObject("page", page);
		//return new ModelAndView(this.getSuccessView());
	   }

	public void setPersonMemberService(PersonMemberService personMemberService) {
		this.personMemberService = personMemberService;
	}
	
}
