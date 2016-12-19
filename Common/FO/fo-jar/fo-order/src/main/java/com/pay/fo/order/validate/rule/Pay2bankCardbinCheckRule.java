/**
 *  File: Pay2bankCardbinCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-13   terry     Create
 *
 */
package com.pay.fo.order.validate.rule;

import java.util.HashMap;
import java.util.Map;

import com.pay.cardbin.service.CardBinInfoFactoryService;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fo.order.validate.PaymentRequest;
import com.pay.fundout.util.FreeMarkParseUtil;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 
 */
public class Pay2bankCardbinCheckRule extends MessageRule {

	/**
	 * 添加卡Bin验证服务
	 */
	private CardBinInfoFactoryService cardBinService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		boolean result=true;
		StringBuffer errorMsg = new StringBuffer();
		PaymentRequest detailRequest = (PaymentRequest) validateBean;
		String type = detailRequest.getTradeType();
		String bankCode = detailRequest.getPaymentResponse().getPayeeBankCode();
		String bankAcct = detailRequest.getPayeeBankAcctCode();
		String payeeName = detailRequest.getPayeeName();
		if(StringUtil.isEmpty(payeeName)){
			errorMsg = errorMsg.append(detailRequest.getRow() + "行,收款人名称为空;");
			result = false;
		}
		if(StringUtil.isEmpty(bankAcct)){
			errorMsg = errorMsg.append(detailRequest.getRow() + "行,银行账号为空;");
			result = false;
		}
		if (BatchPaymentToBankReqDetailDTO.PESONER.equalsIgnoreCase(type)
				&& cardBinService.isCreditCard(bankCode, bankAcct)) {
			Map paraMap = new HashMap();
			paraMap.put("row", detailRequest.getRow());
			errorMsg = errorMsg.append(FreeMarkParseUtil.parseTemplate(getMessageId(), paraMap));
			result = false;
		} 
		if(!StringUtil.isEmpty(errorMsg.toString())){
			detailRequest.getPaymentResponse().setErrorMsg(errorMsg.toString());
		}
		return result;
	}

	public void setCardBinService(CardBinInfoFactoryService cardBinService) {
		this.cardBinService = cardBinService;
	}

}
