/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.rule;


import com.pay.fi.commons.TradeMccEnum;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 账单邮箱校验。长度不能超过128个字符
 */
public class BillEmailCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		CrosspayRequest crosspayApiRequest = (CrosspayRequest) validateBean;
		CrosspayResponse crosspayApiResponse = crosspayApiRequest.getCrosspayResponse();

		String billEmail = crosspayApiRequest.getBillEmail();
		
		String mcc = crosspayApiRequest.getMcc();
		
		
		if(TradeMccEnum.OBJ_TRADE.getCode().equals(mcc)){//电商类交易
			if (StringUtil.isEmpty(billEmail)||billEmail.trim().length()>128) {
				crosspayApiResponse.setResultCode(getMessageId());
				crosspayApiResponse.setResultMsg(getMessage());
				return false;
			}
		}else{
			if (!StringUtil.isEmpty(billEmail)&&billEmail.trim().length()>128) {
				crosspayApiResponse.setResultCode(getMessageId());
				crosspayApiResponse.setResultMsg(getMessage());
				return false;
			}
			
		}
		
		return true;
	}
}
