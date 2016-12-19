/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauth.api;

import java.util.List;


import com.pay.gateway.dto.PreauthApiRequest;
import com.pay.gateway.dto.PreauthApiResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListDto;
import com.pay.rm.blackwhitelist.service.BlackWhiteListService;
import com.pay.util.IPUtil;
import com.pay.util.StringUtil;

/**
 * 黑名单判断
 */
public class RiskBlackListCheckRule extends MessageRule {

	private BlackWhiteListService blackWhiteListService;
	private static final int BLACK_LIST_TYPE = 2;

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

		PreauthApiRequest preauthApiRequest = (PreauthApiRequest) validateBean;
		PreauthApiResponse preauthApiResponse = preauthApiRequest
				.getPreauthApiResponse();

		List<BlackWhiteListDto> blackWhiteListDtos = null;
		String ip = preauthApiRequest.getCustomerIP();

		if (StringUtil.isEmpty(ip) || !IPUtil.validateIp(ip)) {
			preauthApiResponse.setResultCode(getMessageId());
			preauthApiResponse.setResultMsg("IP校验不通过");
			return false;
		}

		// 校验IP
		blackWhiteListDtos = blackWhiteListService.queryBlackWhiteList(3,
				BLACK_LIST_TYPE);
		if (null != blackWhiteListDtos && !blackWhiteListDtos.isEmpty()) {
			for (BlackWhiteListDto dto : blackWhiteListDtos) {
				// 全匹配
				if (dto.getPartType() == 1) {
					if (dto.getContent().equals(ip)) {
						preauthApiResponse.setResultCode(getMessageId());
						preauthApiResponse.setResultMsg("IP黑名单");
						return false;
					}
				} else if (dto.getPartType() == 2) {
					if (ip.startsWith(dto.getContent())) {
						preauthApiResponse.setResultCode(getMessageId());
						preauthApiResponse.setResultMsg("IP黑名单");
						return false;
					}
				}
			}
		}

		// 校验邮箱
		String billEmail = preauthApiRequest.getBillEmail();
		//String shippingMail = preauthApiRequest.getShippingMail();
		String cardHolderEmail = preauthApiRequest.getCardHolderEmail();
		blackWhiteListDtos = blackWhiteListService.queryBlackWhiteList(2,
				BLACK_LIST_TYPE);
		if (null != blackWhiteListDtos && !blackWhiteListDtos.isEmpty()) {
			for (BlackWhiteListDto dto : blackWhiteListDtos) {
				// 账单邮箱，全匹配
				if (dto.getPartType() == 1) {
					if (dto.getContent().equals(billEmail)) {
						preauthApiResponse.setResultCode(getMessageId());
						preauthApiResponse.setResultMsg("账单邮箱黑名单");
						return false;
					}

					/*if (dto.getContent().equals(shippingMail)) {
						preauthApiResponse.setResultCode(getMessageId());
						preauthApiResponse.setResultMsg("收件邮箱黑名单");
						return false;
					}*/
					if (dto.getContent().equals(cardHolderEmail)) {
						preauthApiResponse.setResultCode(getMessageId());
						preauthApiResponse.setResultMsg("支付邮箱黑名单");
						return false;
					}

				} else if (dto.getPartType() == 2) {
					if (billEmail.startsWith(dto.getContent())) {
						preauthApiResponse.setResultCode(getMessageId());
						preauthApiResponse.setResultMsg("账单邮箱黑名单");
						return false;
					}
					/*if (shippingMail.startsWith(dto.getContent())) {
						preauthApiResponse.setResultCode(getMessageId());
						preauthApiResponse.setResultMsg("收件邮箱黑名单");
						return false;
					}*/
					if (cardHolderEmail.startsWith(dto.getContent())) {
						preauthApiResponse.setResultCode(getMessageId());
						preauthApiResponse.setResultMsg("支付邮箱黑名单");
						return false;
					}
				}

			}
		}

		String billCountryCode = preauthApiRequest.getBillCountryCode();
		/*String shippingCountryCode = preauthApiRequest
				.getShippingCountryCode();*/
		blackWhiteListDtos = blackWhiteListService.queryBlackWhiteList(4,
				BLACK_LIST_TYPE);
		if (null != blackWhiteListDtos && !blackWhiteListDtos.isEmpty()) {
			for (BlackWhiteListDto dto : blackWhiteListDtos) {
				// 账单邮箱，全匹配
				if (dto.getPartType() == 1) {
					if (dto.getContent().equals(billCountryCode)) {
						preauthApiResponse.setResultCode(getMessageId());
						preauthApiResponse.setResultMsg("账单国家黑名单");
						return false;
					}

					/*if (dto.getContent().equals(shippingCountryCode)) {
						preauthApiResponse.setResultCode(getMessageId());
						preauthApiResponse.setResultMsg("收件国家黑名单");
						return false;
					}*/

				}

			}
		}

		String cardHolderNumber = preauthApiRequest.getCardHolderNumber();
		// 判断卡号
		blackWhiteListDtos = blackWhiteListService.queryBlackWhiteList(1,
				BLACK_LIST_TYPE);
		if (null != blackWhiteListDtos && !blackWhiteListDtos.isEmpty()) {
			for (BlackWhiteListDto dto : blackWhiteListDtos) {
				// 账单邮箱，全匹配
				if (dto.getPartType() == 1) {
					if (dto.getContent().equals(cardHolderNumber)) {
						preauthApiResponse.setResultCode(getMessageId());
						preauthApiResponse.setResultMsg("银行卡号黑名单");
						return false;
					}

				} else if (dto.getPartType() == 2) {
					if (cardHolderNumber.startsWith(dto.getContent())) {
						preauthApiResponse.setResultCode(getMessageId());
						preauthApiResponse.setResultMsg("银行卡号黑名单");
						return false;
					}

				}

			}
		}
		return true;
	}

}
