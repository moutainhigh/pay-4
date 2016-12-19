/**
 *  File: PaymentItemCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 24, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate.rule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pay.api.dto.http.PaymentItemRequest;
import com.pay.api.dto.http.PaymentItemResult;
import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.RequestStatus;
import com.pay.inf.rule.MessageRule;
import com.pay.service.ValidateService;

/**
 * 
 */
public class PaymentItemCheckRule extends MessageRule {

	private ValidateService itemRequestValidateService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentRequest request = (PaymentRequest) validateBean;
		PaymentResult result = request.getResult();
		List<PaymentItemResult> itemResultList = null;

		boolean flag = true;
		List<PaymentItemRequest> itemList = request.getItemList();
		if (null != itemList && !itemList.isEmpty()) {
			itemResultList = new ArrayList<PaymentItemResult>();
			for (PaymentItemRequest itemRequest : itemList) {
				itemRequest.setPayType(Integer.valueOf(request.getPayType()));
				String errorCode = itemRequestValidateService
						.validate(itemRequest);
				PaymentItemResult itemResult = itemRequest.getResult();
				BeanUtils.copyProperties(itemRequest, itemResult);
				
				if (null != errorCode) {
					flag = false;
					itemResult.setStatus(RequestStatus.FAIL.getValue());
				}else{
					itemResult.setStatus(RequestStatus.SUCCESS.getValue());
				}
				itemResultList.add(itemResult);
			}
			result.setItemList(itemResultList);
			if (!flag) {
				result.setErrorCode(ErrorCode.FAIL);
				result.setErrorMsg(ErrorCode.FAIL_DESC);
			}
		} else {
			result.setErrorCode(ErrorCode.REQUESTITEM_INVALID);
			result.setErrorMsg(ErrorCode.REQUESTITEM_INVALID_DESC);
		}
		request.setResult(result);
		return flag;
	}

	public void setItemRequestValidateService(
			ValidateService itemRequestValidateService) {
		this.itemRequestValidateService = itemRequestValidateService;
	}

}
