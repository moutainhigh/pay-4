/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.token;

import com.pay.fi.commons.PayMccEnum;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.gateway.dto.TokenpayRequest;
import com.pay.gateway.dto.TokenpayResponse;
import com.pay.inf.rule.MessageRule;

/**
 * 接口MCC校验
 * 4000 实物交易类
 * 5000 航旅类交易 
 * 6000 虚拟类交易
 */
public class MccCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		TokenpayRequest crosspayApiRequest = (TokenpayRequest) validateBean;
		TokenpayResponse crosspayApiResponse = crosspayApiRequest.getTokenpayResponse();

//		String mcc = crosspayApiRequest.getMcc();
//		if (PayMccEnum.isExists(mcc)) {
//			return true;
//		} else {
//			crosspayApiResponse.setResultCode(getMessageId());
//			crosspayApiResponse.setResultMsg(getMessage());
//			return false;
//		}
		
		String mcc = crosspayApiRequest.getMcc();
		// token pay only support virtual trans for 0816 MTP
		if (PayMccEnum.VIR_TRANS.getCode().equals(mcc)) {
			return true;
		} else {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}
	}
}
