/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.locale;

import java.util.List;

import com.pay.gateway.dto.CrosspayGatewayRequest;
import com.pay.gateway.dto.CrosspayGatewayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListDto;
import com.pay.rm.blackwhitelist.service.BlackWhiteListService;

/**
 * 黑名单判断
 */
public class RiskBlackListCheckRule extends MessageRule {

	private BlackWhiteListService blackWhiteListService;
	private static final int BLACK_LIST_TYPE = 2;
	private String messageEn;
	
	public String getMessageEn() {
		return messageEn;
	}

	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}

	public void setBlackWhiteListService(
			BlackWhiteListService blackWhiteListService) {
		this.blackWhiteListService = blackWhiteListService;
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

		List<BlackWhiteListDto> blackWhiteListDtos = null;
		String ip = onlineRequestDTO.getCustomerIP();
		String language = onlineRequestDTO.getLanguage();

		// 校验IP
		blackWhiteListDtos = blackWhiteListService.queryBlackWhiteList(3,
				BLACK_LIST_TYPE);
		if (null != blackWhiteListDtos && !blackWhiteListDtos.isEmpty()) {
			for (BlackWhiteListDto dto : blackWhiteListDtos) {
				// 全匹配
				if (dto.getPartType() == 1) {
					if (dto.getContent().equals(ip)) {
						
						if("cn".equals(language))
							onlineResponseDTO.setResultMsg("IP黑名单");
						else
							onlineResponseDTO.setResultMsg("IP Black List");
						
						onlineResponseDTO.setResultCode(getMessageId());
						
						return false;
					}
				} else if (dto.getPartType() == 2) {
					if (ip.startsWith(dto.getContent())) {
						
						if("cn".equals(language))
							onlineResponseDTO.setResultMsg("IP黑名单");
						else
							onlineResponseDTO.setResultMsg("IP Black List");
						
						onlineResponseDTO.setResultCode(getMessageId());
						return false;
					}
				}
			}
		}
		return true;
	}

}
