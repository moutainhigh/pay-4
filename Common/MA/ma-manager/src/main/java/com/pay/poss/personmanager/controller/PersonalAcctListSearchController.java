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
import com.pay.poss.base.common.PossIpUtil;
import com.pay.poss.base.common.SessionUserUtils;
import com.pay.poss.personmanager.dto.PersonMemberInfoDto;
import com.pay.poss.personmanager.formbean.PersonSearchFormBean;
import com.pay.poss.personmanager.service.PersonMemberService;
import com.pay.poss.security.model.SessionUserHolder;

/**
 * @Description 会员基本信息查询
 * @note		<br>
 * @develop		JDK1.6 + SpringSource 2.3.2
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-11		jose_liu			Create
 */
public class PersonalAcctListSearchController extends SimpleFormController {
	
	private Log log = LogFactory.getLog(PersonalAcctListSearchController.class);
	public static final  String OPERATE_FROZEN = "frozen";
	public static final  String OPERATE_UNFROZEN = "unFrozen";
	
	private PersonMemberService personMemberService;
	
	@SuppressWarnings("unchecked")
	@Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("PersonalAcctListSearchController.referenceData() is running...");
    	SessionUserHolder user = SessionUserUtils.getUserInfo(request);
    	String memberCode = request.getParameter("memberCode");
    	String operate  = request.getParameter("operate");

    	String ip = PossIpUtil.getIp(request);
    	Map paramMap = new HashMap();
    	paramMap.put("memberCode", memberCode);
    	paramMap.put("operate", operate);
    	if(null!=user){
    		if(OPERATE_FROZEN.equals(operate)  ||OPERATE_UNFROZEN.equals(operate)){
    			paramMap.put("loginIp", ip);
    			paramMap.put("loginName", user.getUsername());
        		//personMemberService.updatePersonalMemberOperate(paramMap);
    			personMemberService.updateMemberStatusTrans(paramMap);
        	}
    	}
		return paramMap;
	}

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
				BindException errors) throws Exception {    
    	log.debug("PersonalAcctListSearchController.onSubmit() is running...");
    	PersonSearchFormBean personSearchFormBean = (PersonSearchFormBean)command;
    	Page<PersonMemberInfoDto> page = PageUtils.getPage(request);
    	List<PersonMemberInfoDto> info  = personMemberService.selectPersonMemberInfoList(personSearchFormBean,page);
    	page.setResult(info);
		return new ModelAndView(this.getSuccessView()).addObject("page", page);
	   }

	public void setPersonMemberService(PersonMemberService personMemberService) {
		this.personMemberService = personMemberService;
	}
}
