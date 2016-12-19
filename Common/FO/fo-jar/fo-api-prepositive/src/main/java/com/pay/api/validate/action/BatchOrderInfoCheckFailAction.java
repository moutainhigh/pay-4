/**
 * 
 */
package com.pay.api.validate.action;

import java.util.ArrayList;
import java.util.List;

import com.pay.api.dto.http.PaymentItemRequest;
import com.pay.api.dto.http.PaymentItemResult;
import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.helper.RequestStatus;
import com.pay.inf.rule.AbstractAction;

/**
 * @author ch-ma
 * 
 */
public class BatchOrderInfoCheckFailAction extends AbstractAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractAction#doExecute(java.lang.Object)
	 */
	@Override
	protected void doExecute(Object validateBean) throws Exception {

		PaymentRequest request = (PaymentRequest) validateBean;
		PaymentResult result = request.getResult();
		List<PaymentItemResult> itemResultList = result.getItemList();

		if (null == itemResultList) {
			itemResultList = new ArrayList<PaymentItemResult>();
			List<PaymentItemRequest> itemList = request.getItemList();
			for (PaymentItemRequest itemRequest : itemList) {
				PaymentItemResult itemResult = itemRequest.getResult();
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
			List<PaymentItemResult> resultList = new ArrayList<PaymentItemResult>();
			resultList.addAll(itemResultList);
			for (PaymentItemResult itemResult : resultList) {
				if (RequestStatus.SUCCESS.getValue() == itemResult.getStatus()) {
					itemResult.setErrorCode(null);
					itemResult.setErrorMsg(null);
					itemResult.setStatus(RequestStatus.FAIL.getValue());
				}
			}
			result.setItemList(resultList);
		}

	}

}
