/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.cashier;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.MemberProductService;
import com.pay.gateway.dto.CrosspayGatewayRequest;
import com.pay.gateway.dto.CrosspayGatewayResponse;
import com.pay.inf.enums.DCCEnum;
import com.pay.inf.rule.MessageRule;
import com.pay.util.CurrencyCodeEnum;

/**
 * 商户开通的DCC产品判断
 */
public class DCCProductCheckRule extends MessageRule {

	private final Log logger = LogFactory.getLog(getClass());
	protected MemberProductService memberProductService;
	private String messageEn;
    
	public String getMessageEn() {
		return messageEn;
	}
	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}
	
	

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

		CrosspayGatewayRequest onlineRequestDTO = (CrosspayGatewayRequest) validateBean;
		CrosspayGatewayResponse onlineResponseDTO = onlineRequestDTO
				.getGatewayResponseDTO();

		String memberCode = onlineRequestDTO.getPartnerId();

		boolean isHaveProduct = memberProductService.isHaveProduct(
				Long.valueOf(memberCode),DCCEnum.PARTNER_DCC_PRDCT.getCode());

		if (!isHaveProduct) {
			return true;
		}
		
		String currencyCode = onlineRequestDTO.getCurrencyCode();
		String language = onlineRequestDTO.getLanguage();
		
		logger.info("currencyCode: "+currencyCode);

		if (CurrencyCodeEnum.CNY.getCode()
				                      .equals(currencyCode)) {
			return true;
		} else {
			if("cn".equals(language))
				onlineResponseDTO.setResultMsg(getMessage());
			else
				onlineResponseDTO.setResultMsg(getMessageEn());
			onlineResponseDTO.setResultCode(getMessageId());
			
			return false;
		}
		
	}
	
	

}
