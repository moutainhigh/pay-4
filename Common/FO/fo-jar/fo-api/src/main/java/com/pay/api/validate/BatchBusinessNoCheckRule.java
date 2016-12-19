/**
 *  File: Batch2bankBusinessNoCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.api.validate;

import com.pay.api.dto.BatchPaymentRequest;
import com.pay.api.dto.BatchPaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.PayType;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.service.batchpayment.BatchPaymentReqBaseInfoService;
import com.pay.inf.rule.MessageRule;

/**
 * 验证批付到银行批次号
 */
public class BatchBusinessNoCheckRule extends MessageRule {

	/**
	 * 批量付款请求基本信息服务类
	 */
	private BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		BatchPaymentRequest request = (BatchPaymentRequest) validateBean;
		BatchPaymentResult result = request.getResult();
		String businessNo = request.getBizNo();
		Long memberCode = request.getMerchantCode();

		Integer payType = request.getPayType();
		Integer type = OrderType.BATCHPAY2BANK.getValue();
		if (PayType.ACCT.getValue() == payType) {
			type = OrderType.BATCHPAY2ACCT.getValue();
		}

		BatchPaymentReqBaseInfoDTO reqInfo = batchPaymentReqBaseInfoService
				.getBatchPaymentReqBaseInfo(memberCode, type, businessNo);
		if (null != reqInfo) {
			result.setErrorCode(ErrorCode.BUSINESSNO_INVALID);
			result.setErrorMsg(ErrorCode.BUSINESSNO_INVALID_DESC);
			request.setResult(result);
			return false;
		} else {
			return true;
		}
	}

	public void setBatchPaymentReqBaseInfoService(
			BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService) {
		this.batchPaymentReqBaseInfoService = batchPaymentReqBaseInfoService;
	}

}
