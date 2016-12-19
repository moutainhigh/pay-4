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

import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.personmanager.dto.PersonalAcctBalanceDto;
import com.pay.poss.personmanager.dto.PersonalAcctTradeTotalDto;
import com.pay.poss.personmanager.formbean.PersonSearchFormBean;
import com.pay.poss.personmanager.service.PersonMemberService;

/**
 * @Description 会员账户余额查询
 * @project 	poss-membermanager
 * @file 		PersonalAcctBalanceController.java 
 * @note		<br>
 * @develop		JDK1.6 + SpringSource 2.3.2
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-21		tianqing_wang			Create
 */
public class PersonalAcctBalanceController extends SimpleFormController {
	
	private Log log = LogFactory.getLog(PersonalAcctBalanceController.class);
	private PersonMemberService personMemberService;

	@SuppressWarnings("unchecked")
	@Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("======PersonalAcctBalanceController.referenceData() is running...");
    	String acctCode = request.getParameter("acctCode");
    	String loginName = request.getParameter("loginName");
    	Map paramMap = new HashMap();
    	paramMap.put("acctCode", acctCode);
    	paramMap.put("loginName", loginName);
    	paramMap.put("dealTypeList",PayForEnum.SEARCH_TYPES);
		return paramMap;
	}

	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
				BindException errors) throws Exception {    
    	log.debug("PersonalAcctBalanceController.onSubmit() is running...");
    	PersonSearchFormBean personSearchFormBean = (PersonSearchFormBean)command;
    	Map<String,Object> paraMap = new HashMap<String,Object>();
    	Map<String,String> returnMap = new HashMap<String,String>();
    	String dealType=personSearchFormBean.getDealType();
    	
    	paraMap.put("dealType", dealType);
    	
    	paraMap.put("acctCode", personSearchFormBean.getAcctCode());
    	
		paraMap.put("startDate", personSearchFormBean.getStartDate());
		paraMap.put("endDate", personSearchFormBean.getEndDate());
		paraMap.put("loginName", personSearchFormBean.getLoginName());
		paraMap.put("type", personSearchFormBean.getType());
		
		paraMap.put("balanceStrat", personSearchFormBean.getBalanceStrat());
		paraMap.put("balanceEnd", personSearchFormBean.getBalanceEnd());
		
    	Page<PersonalAcctBalanceDto> page = PageUtils.getPage(request);
    	List<PersonalAcctBalanceDto> infoOld  = personMemberService.queryPersonalAcctBalanceList(paraMap,page);
    	
    	if(infoOld != null){
    		for (PersonalAcctBalanceDto dto : infoOld){
        		Map balanceMap = new HashMap(2);
        		balanceMap.put("dealid", dto.getOrderNumber());
        		balanceMap.put("acctCode", dto.getAcctCode());
        		if(dto.getDealType().equals(String.valueOf(PayForEnum.FEE_AMOUNT.getCode()))){
        			balanceMap.put("status", 1);
				}else{
					balanceMap.put("status", 0);
				}
        		balanceMap.put("dealCode", dto.getDealCode());
        		String strBalance = personMemberService.queryBalance(balanceMap);
        		dto.setStrBalance(strBalance);
        		dto.setDealType(PayForEnum.get(Integer.parseInt(dto.getDealType())).getMessage());
        	}
    	}
    	List<PersonalAcctTradeTotalDto> total = personMemberService.queryPeraonalAcctTradeTotal(paraMap);
    	page.setResult(infoOld);
    	for(PersonalAcctTradeTotalDto dto:total){
    		returnMap.put("totalPay", dto.getTotalPay());
    		returnMap.put("totalRevenue", dto.getTotalRevenue());
    		//returnMap.put("totalBalance", dto.getTotalBalance());
    	}
    	paraMap.put("dealType", dealType);
		return new ModelAndView(this.getSuccessView()).addObject("page", page)
				.addObject("returnMap",returnMap)
				.addObject("paraMap",paraMap);
	   }

	public void setPersonMemberService(PersonMemberService personMemberService) {
		this.personMemberService = personMemberService;
	}
	
}
