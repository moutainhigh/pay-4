/**
 * 
 */
package com.pay.api.validate;

import java.util.ArrayList;
import java.util.List;

import com.pay.api.dto.BatchPaymentItemRequest;
import com.pay.api.dto.BatchPaymentItemResult;
import com.pay.api.dto.BatchPaymentRequest;
import com.pay.api.dto.BatchPaymentResult;
import com.pay.api.helper.RequestStatus;
import com.pay.inf.rule.AbstractAction;

/**
 * @author ch-ma
 * 
 */
public class BatchPaymentInfoCheckFailAction extends AbstractAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractAction#doExecute(java.lang.Object)
	 */
	@Override
	protected void doExecute(Object validateBean) throws Exception {

		BatchPaymentRequest request = (BatchPaymentRequest) validateBean;
		BatchPaymentResult result = request.getResult();

		List<BatchPaymentItemResult> itemResultList = result.getItemList();

		if (null == itemResultList) {
			itemResultList = new ArrayList<BatchPaymentItemResult>();
			List<BatchPaymentItemRequest> itemList = request.getItemList();
			for (BatchPaymentItemRequest itemRequest : itemList) {
				BatchPaymentItemResult itemResult = itemRequest.getResult();
				itemResult.setOrderId(itemRequest.getOrderId());
				itemResult.setStatus(RequestStatus.FAIL.getValue());
				String errorCode = itemResult.getErrorCode();
				String errorMsg = itemResult.getErrorMsg();
				if (null == errorCode) {
					errorCode = result.getErrorCode();
					errorMsg = result.getErrorMsg();
				}
				itemResult.setErrorCode(errorCode);
				itemResult.setErrorMsg(errorMsg);
				itemResultList.add(itemResult);
			}
			result.setItemList(itemResultList);
		} else {
			List<BatchPaymentItemResult> resultList = new ArrayList<BatchPaymentItemResult>();
			resultList.addAll(itemResultList);
			for (BatchPaymentItemResult itemResult : resultList) {
				if (RequestStatus.SUCCESS.getValue() == itemResult.getStatus()) {
					itemResult.setErrorCode(null);
					itemResult.setErrorMsg(null);
					itemResult.setStatus(RequestStatus.FAIL.getValue());
				}
			}
			result.setItemList(resultList);
		}
		result.setSuccessAmount(0L);
		result.setSuccessCount(0);
		request.setResult(result);

	}

}
