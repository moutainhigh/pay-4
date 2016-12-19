/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.api;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.MemberProductService;
import com.pay.gateway.dto.CrosspayApiRequest;
import com.pay.gateway.dto.CrosspayApiResponse;
import com.pay.inf.enums.DCCEnum;
import com.pay.inf.rule.MessageRule;
import com.pay.util.CurrencyCodeEnum;

/**
 * 商户开通的DCC产品判断
 */
public class DCCProductCheckRule extends MessageRule {

	private final Log logger = LogFactory.getLog(getClass());
	protected MemberProductService memberProductService;
	
	

	public void setMemberProductService(MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		CrosspayApiRequest crosspayApiRequest = (CrosspayApiRequest) validateBean;
		CrosspayApiResponse crosspayApiResponse = crosspayApiRequest
				.getCrosspayApiResponse();

		String memberCode = crosspayApiRequest.getPartnerId();

		boolean isHaveProduct = memberProductService.isHaveProduct(
				Long.valueOf(memberCode),DCCEnum.PARTNER_DCC_PRDCT.getCode());

		if (!isHaveProduct) {
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
