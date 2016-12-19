/**
 *  File: BatchPaymentItemCheckRule.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 24, 2011   ch-ma     Create
 *
 */
package com.pay.api.validate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pay.api.dto.BatchPaymentItemRequest;
import com.pay.api.dto.BatchPaymentItemResult;
import com.pay.api.dto.BatchPaymentRequest;
import com.pay.api.dto.BatchPaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.RequestStatus;
import com.pay.inf.rule.MessageRule;
import com.pay.service.ValidateService;

/**
 * 
 */
public class BatchPaymentItemCheckRule extends MessageRule {

	private ValidateService itemRequestValidateService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		BatchPaymentRequest request = (BatchPaymentRequest) validateBean;
		BatchPaymentResult result = request.getResult();
		List<BatchPaymentItemResult> itemResultList = new ArrayList<BatchPaymentItemResult>();
		boolean flag = true;
		Long successAmount = 0L;
		Integer successCount = 0;
		Long totalFee = 0L;

		List<BatchPaymentItemRequest> itemList = request.getItemList();
		for (BatchPaymentItemRequest itemRequest : itemList) {
			String errorCode = itemRequestValidateService.validate(itemRequest);
			BatchPaymentItemResult itemResult = itemRequest.getResult();
			BeanUtils.copyProperties(itemRequest, itemResult);
			if (null != errorCode) {
				itemResult.setStatus(RequestStatus.FAIL.getValue());
				flag = false;
			}else{
				itemResult.setStatus(RequestStatus.SUCCESS.getValue());
				itemResult.setErrorCode(ErrorCode.SUCCESS);
				itemResult.setErrorMsg(ErrorCode.SUCCESS_DESC);
				successAmount += itemRequest.getAmount();
				successCount ++;
				totalFee += itemResult.getFee();
			}
			itemResultList.add(itemResult);
		}
		result.setItemList(itemResultList);
		if(!flag){
			result.setErrorCode(ErrorCode.FAIL);
			result.setErrorMsg(ErrorCode.FAIL_DESC);
		}else{
			result.setSuccessAmount(successAmount);
			result.setSuccessCount(successCount);
			result.setTotalFee(totalFee);
			result.setErrorCode(ErrorCode.SUCCESS);
			result.setErrorMsg(ErrorCode.SUCCESS_DESC);
		}
		
		request.setResult(result);
		return flag;
	}

	public void setItemRequestValidateService(
			ValidateService itemRequestValidateService) {
		this.itemRequestValidateService = itemRequestValidateService;
	}

}
