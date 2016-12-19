/**
 *  File: Pay2bankSingleRiskLimitCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-13   terry     Create
 *
 */
package com.pay.api.validate.item;

import com.pay.api.dto.BatchPaymentItemRequest;
import com.pay.api.dto.BatchPaymentItemResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.PayType;
import com.pay.inf.rule.MessageRule;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;

/**
 * 
 */
public class BatchPaymentRiskCheckRule extends MessageRule {

	private FoRcLimitFacade foRcLimitFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		BatchPaymentItemRequest request = (BatchPaymentItemRequest) validateBean;
		BatchPaymentItemResult result = request.getResult();
		Long merchantCode = request.getMerchantCode();
		Integer payType = request.getPayType();
		Long requestAmount = request.getAmount();

		Integer buziType = RCLIMITCODE.FO_PAY_ENTERPRISE_BANK2P.getKey();
		if (payType == PayType.ACCT.getValue()) {
			buziType = RCLIMITCODE.FO_PAY_ENTERPRISE_ACC2P.getKey();
		}

		RCLimitResultDTO rule = foRcLimitFacade.getBusinessRcLimit(buziType,
				null, merchantCode);

		// 增加判断金额必须大于0才能支付
		if (null != rule && rule.getSingleLimit() < requestAmount) {
			result.setErrorCode(ErrorCode.EXCEED_LIMIT);
			result.setErrorMsg(ErrorCode.EXCEED_LIMIT_DESC);
			request.setResult(result);
			return false;
		}
		return true;
	}

	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}

}
