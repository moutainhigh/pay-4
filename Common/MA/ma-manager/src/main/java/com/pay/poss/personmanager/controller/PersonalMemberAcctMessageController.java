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
import com.pay.poss.personmanager.dto.PersonalAcctInfoSearchDto;
import com.pay.poss.personmanager.formbean.PersonSearchFormBean;
import com.pay.poss.personmanager.service.PersonMemberService;

/**
 * @Description 账户信息查询
 * @project 	poss-membermanager
 * @file 		PersonalMemberAcctMessageController.java 
 * @note		<br>
 * @develop		JDK1.6 + SpringSource 2.3.2
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-11-5		tianqing_wang	Create
 */
public class PersonalMemberAcctMessageController extends SimpleFormController {
	
	private Log log = LogFactory.getLog(PersonalMemberAcctMessageController.class);

	
	private PersonMemberService personMemberService;
	
	@SuppressWarnings("unchecked")
	@Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("PersonalMemberAcctMessageController.referenceData() is running...");
    	String loginName = request.getParameter("loginName");
    	Map paramMap = new HashMap(1);
    	paramMap.put("loginName", loginName);
		return paramMap;
	}

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
				BindException errors) throws Exception {    
    	log.debug("PersonalMemberAcctMessageController.onSubmit() is running...");
    	PersonSearchFormBean personSearchFormBean = (PersonSearchFormBean)command;
    	Map<String,Object> paraMap = new HashMap<String,Object>();
    	Page<PersonalAcctInfoSearchDto> page = PageUtils.getPage(request);
    	paraMap.put("loginName", personSearchFormBean.getLoginName());
		paraMap.put("memberCode", personSearchFormBean.getMemberCode());
		paraMap.put("userName", personSearchFormBean.getUserName());
		paraMap.put("type", personSearchFormBean.getType());
    	List<PersonalAcctInfoSearchDto> info  = personMemberService.queryPersonalMemberAcctMessage(paraMap,page);
    	/*List<PersonalAcctInfoSearchDto> newInfo = new ArrayList<PersonalAcctInfoSearchDto>();
    	for(PersonalAcctInfoSearchDto dto: info){
    		dto.setDebitBalance(getStrToMinus(dto.getDebitBalance()));
    		dto.setCreditBalance(getStrToMinus(dto.getCreditBalance()));
    		newInfo.add(dto);
    	}*/
    	page.setResult(info);
		return new ModelAndView(this.getSuccessView()).addObject("page", page);
	   }

	public void setPersonMemberService(PersonMemberService personMemberService) {
		this.personMemberService = personMemberService;
	}
}
