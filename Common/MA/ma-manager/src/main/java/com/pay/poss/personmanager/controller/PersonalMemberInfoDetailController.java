/**
 * 
 */
package com.pay.poss.personmanager.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.poss.personmanager.dto.PersonMemberInfoDto;
import com.pay.poss.personmanager.dto.PersonalLoginHistoryDto;
import com.pay.poss.personmanager.service.PersonMemberService;
import com.pay.util.DESUtil;

/**
 * @Description 会员详细信息
 * @project 	poss-membermanager
 * @file 		PersonalMemberInfoDetailController.java 
 * @note		<br>
 * @develop		JDK1.6 + SpringSource 2.3.2
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-11-4		tianqing_wang	Create
 */
public class PersonalMemberInfoDetailController extends SimpleFormController {
	
	private Log log = LogFactory.getLog(PersonalMemberInfoDetailController.class);
	
	private PersonMemberService personMemberService;
	
	@SuppressWarnings({ "unchecked", "null" })
	@Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("PersonalMemberInfoDetailController.referenceData() is running...");
    	String memberCode = request.getParameter("memberCode");
    	Map paramMap = new HashMap(5);
    	paramMap.put("memberCode", memberCode);
    	String cerCode = null;//身份证号码
    	String bankAcctNo = null;//银行账号
    	
    	PersonMemberInfoDto  memberInfo = personMemberService.selectPersonlMemberInfoDetail(memberCode);
    	PersonMemberInfoDto  bankAcct = personMemberService.selectMemberBankAcct(memberCode); 
    	PersonalLoginHistoryDto  loginHistory = personMemberService.selectMemberLoginIp(memberCode);
    	PersonMemberInfoDto  isPaperFile =  personMemberService.selectPersonlMemberIsPaperFile(memberCode);
    	//是否实名认证
    	Integer countCard = personMemberService.countBindMatrixCard(memberCode); 
    	//解密身份证和银行账号
    	if(null != memberInfo && null != memberInfo.getCerCode() 
    			&& !"".equals(memberInfo.getCerCode())){
    		cerCode = DESUtil.decrypt(memberInfo.getCerCode());
    		memberInfo.setCerCode(cerCode);
    	}
    	if(null != bankAcct && null != bankAcct.getBankAcct() 
    			&& !"".equals(bankAcct.getBankAcct())){
    		bankAcctNo = DESUtil.decrypt(bankAcct.getBankAcct());
    		bankAcct.setBankAcct(bankAcctNo);
    	}
    	if(countCard ==  0){
    		paramMap.put("isMatrixCard","否");
    	}else{
    		paramMap.put("isMatrixCard","是");
    	}
    	paramMap.put("memberInfo",memberInfo);
    	paramMap.put("bankAcct",bankAcct);
    	paramMap.put("loginHistory",loginHistory);
    	paramMap.put("isPaperFile",isPaperFile);
    	String cusSerFlag = request.getParameter("cusSerFlag");
    	if("customerSer".equals(cusSerFlag)){
    		paramMap.put("cusSerFlag","customerSer");
    	}
    	return paramMap;
	}

	public void setPersonMemberService(PersonMemberService personMemberService) {
		this.personMemberService = personMemberService;
	}
}
