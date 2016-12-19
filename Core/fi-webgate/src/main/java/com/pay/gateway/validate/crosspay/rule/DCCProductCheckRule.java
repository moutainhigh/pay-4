/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.rule;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.model.MemberProduct;
import com.pay.acc.member.service.MemberBaseProductService;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.inf.enums.DCCEnum;
import com.pay.inf.rule.MessageRule;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.StringUtil;

/**
 * 商户开通的DCC产品判断
 */
public class DCCProductCheckRule extends MessageRule {

	private final Log logger = LogFactory.getLog(getClass());
	private MemberBaseProductService memberProductService;
	
	
	public void setMemberProductService(
			MemberBaseProductService memberProductService) {
		this.memberProductService = memberProductService;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		CrosspayRequest crosspayApiRequest = (CrosspayRequest) validateBean;
		CrosspayResponse crosspayApiResponse = crosspayApiRequest.getCrosspayResponse();

		String memberCode = crosspayApiRequest.getPartnerId();
		String payType = crosspayApiRequest.getPayType();
		
		if(StringUtil.isEmpty(payType)||"EDC".equals(payType)){
			return true;
		}		
		StringBuilder sb = new StringBuilder();
		sb.append("'").append(DCCEnum.PARTNER_STANDARD.getCode()).append("','")
		  .append(DCCEnum.PARTNER_DCC_PRDCT.getCode()).append("'");
		
		List<MemberProduct> list = memberProductService.queryMemberProductsByMemberCode
				                           (Long.valueOf(memberCode), sb.toString());
		if (list==null||list.isEmpty()) {
			return true;
		}
		
		String currencyCode = crosspayApiRequest.getCurrencyCode();
		logger.info("currencyCode: "+currencyCode);

		if (CurrencyCodeEnum.CNY.getCode().equals(currencyCode)) {
			return true;
		} else {
			crosspayApiResponse.setResultMsg(getMessage());
			crosspayApiResponse.setResultCode(getMessageId());
			return false;
		}
	}
}
