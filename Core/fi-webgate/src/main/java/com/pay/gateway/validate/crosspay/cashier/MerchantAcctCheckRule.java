/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.cashier;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.gateway.dto.CrosspayGatewayRequest;
import com.pay.gateway.dto.CrosspayGatewayResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 验证网关商户账户属性
 */
public class MerchantAcctCheckRule extends MessageRule {

	private AccountQueryService accountQueryService;
	private String messageEn;

	public String getMessageEn() {
		return messageEn;
	}

	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
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

		String partnerId = onlineRequestDTO.getPartnerId();
		String language = onlineRequestDTO.getLanguage();

		try {

			AcctAttribDto acctAttribDto = accountQueryService
					.doQueryDefaultAcctAttribNsTx(Long.valueOf(partnerId));

			if (null != acctAttribDto && acctAttribDto.getFrozen() != 0
					&& acctAttribDto.getAllowIn() != 0) {
				return true;
			} else {
				if("cn".equals(language))
					onlineResponseDTO.setResultMsg(getMessage());
				else
					onlineResponseDTO.setResultMsg(getMessageEn());
				onlineResponseDTO.setResultCode(getMessageId());
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			if("cn".equals(language))
				onlineResponseDTO.setResultMsg(getMessage());
			else
				onlineResponseDTO.setResultMsg(getMessageEn());
			onlineResponseDTO.setResultCode(getMessageId());
			return false;
		}

	}

}
