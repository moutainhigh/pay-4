/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.refund;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.gateway.dto.refund.RefundApiRequest;
import com.pay.gateway.dto.refund.RefundApiResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 验证网关商户账户属性
 */
public class MerchantAcctCheckRule extends MessageRule {

	private AccountQueryService accountQueryService;

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

		RefundApiRequest refundApiRequest = (RefundApiRequest) validateBean;
		RefundApiResponse refundApiResponse = refundApiRequest
				.getRefundApiResponse();

		String partnerId = refundApiRequest.getPartnerId();

		try {

			Integer acctType = null;
			AcctAttribDto acctAttribDto = null;

			acctAttribDto = accountQueryService
					.doQueryDefaultAcctAttribNsTx(Long.valueOf(partnerId));

			// 结算账户不存在
			if (acctAttribDto == null) {
				refundApiResponse.setResultCode(getMessageId());
				refundApiResponse.setResultMsg(getMessage());
				return false;
			}

			acctType = AcctTypeEnum
					.getGuaranteeAcctTypeByCurrency(acctAttribDto.getCurCode());
			acctAttribDto = accountQueryService.doQueryAcctAttribNsTx(
					Long.valueOf(partnerId), acctType);

			if (null != acctAttribDto && acctAttribDto.getFrozen() != 0
					&& acctAttribDto.getAllowIn() != 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			refundApiResponse.setResultCode(getMessageId());
			refundApiResponse.setResultMsg(getMessage());
			return false;
		}

	}

}
