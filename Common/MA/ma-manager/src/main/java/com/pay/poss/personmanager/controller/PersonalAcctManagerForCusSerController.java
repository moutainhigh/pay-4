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
 * @Description 会员账户管理-开放给客服用
 * @project 	poss-membermanager
 * @file 		PersonalAcctManagerForCurSerController.java 
 * @note		<br>
 * @develop		JDK1.6 + SpringSource 2.3.2
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-03-14		tianqing_wang			Create
 */
public class PersonalAcctManagerForCusSerController extends SimpleFormController {
	
	private Log log = LogFactory.getLog(PersonalAcctManagerForCusSerController.class);
	private PersonMemberService personMemberService;

	@SuppressWarnings("unchecked")
	@Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("PersonalAcctManagerForCusSerController.referenceData() is running...");
    	String memberCode = request.getParameter("memberCode");
    	Map paramMap = new HashMap();
    	paramMap.put("memberCode", memberCode);
		return paramMap;
	}

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
				BindException errors) throws Exception {    
    	log.debug("PersonalAcctManagerForCusSerController.onSubmit() is running...");
    	PersonSearchFormBean personSearchFormBean = (PersonSearchFormBean)command;
    	Map<String,String> paraMap = new HashMap<String,String>();
    	paraMap.put("loginName", personSearchFormBean.getLoginName());
		paraMap.put("memberCode", personSearchFormBean.getMemberCode());
		paraMap.put("userName", personSearchFormBean.getUserName());
		paraMap.put("acctCode", personSearchFormBean.getAcctCode());
		paraMap.put("type", personSearchFormBean.getType());
		paraMap.put("cusSerFlag", "customerSer");
		
    	Page<PersonalAcctInfoSearchDto> page = PageUtils.getPage(request);
    	List<PersonalAcctInfoSearchDto> info  = personMemberService.selectPersonalAcctInfoList(paraMap,page);
    	page.setResult(info);
		return new ModelAndView(this.getSuccessView())
		.addObject("page", page)
		.addObject("cusSerFlag","customerSer");
	   }

	public void setPersonMemberService(PersonMemberService personMemberService) {
		this.personMemberService = personMemberService;
	}
}
