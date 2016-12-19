/**
 * 
 */
package com.pay.api.validate.item;

import com.pay.api.dto.BatchPaymentItemRequest;
import com.pay.api.dto.BatchPaymentItemResult;
import com.pay.api.helper.ErrorCode;
import com.pay.inf.rule.MessageRule;

/**
 * @author ch-ma
 * 
 */
public class PaymentRealoutAmountCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		BatchPaymentItemRequest request = (BatchPaymentItemRequest) validateBean;
		BatchPaymentItemResult result = request.getResult();
		
		Long realOutAmount = result.getRealoutAmount();
		if(realOutAmount<=0){
			result.setErrorCode(ErrorCode.AMOUNT_LIMIT_INVALID);
			result.setErrorMsg(ErrorCode.AMOUNT_LIMIT_INVALID_DESC);
			request.setResult(result);
			return false;
		}else{
			return true;
		}

	}

}
