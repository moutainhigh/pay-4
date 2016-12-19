/**
 *  File: MerchantNotifyImpl.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-12-21   Sany        Create
 *
 */
package com.pay.api.service.notify.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.api.dto.BatchPaymentItemResult;
import com.pay.api.dto.BatchPaymentResult;
import com.pay.api.service.BatchPaymentCallbackService;
import com.pay.api.service.notify.MerchantNotifyService;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.model.batchpayment.BatchPaymentOrder;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;

/**
 * 
 */
public abstract class MerchantNotifyServiceImpl implements MerchantNotifyService {
	
	private Log log = LogFactory.getLog(getClass());
	
	private BatchPaymentCallbackService batchPaymentCallbackService;
	
	protected BatchPaymentOrderService batchPaymentOrderService; 
	
	@Override
	public void notifyMerchant(BatchPaymentOrder order) {
		if (order == null) {
			log.error("出款API通知商户接口入参为空,忽略此通知!");
			return;
		}
		BatchPaymentResult result = new BatchPaymentResult();
		result.setBizNo(order.getBusinessBatchNo());
		result.setSuccessAmount(order.getSuccessAmount());
		result.setSuccessCount(order.getSuccessCount());
		result.setTotalFee(order.getFee());
		result.setRealoutAmount(order.getRealoutAmount());
		result.setRealpayAmount(order.getRealpayAmount());
		result.setPayerLoginName(order.getPayerLoginName());
		result.setPayerMemberType(order.getPayerMemberType());
		result.setPayerName(order.getPayerName());
		result.setPayerAcctcode(order.getPayerAcctCode());
		result.setPayerAcctType(order.getPayerAcctType());
		result.setMerchantCode(order.getPayerMemberCode());
		result.setTotalAmount(order.getOrderAmount());
		result.setTotalCount(order.getPaymentCount());
		
		if (OrderType.BATCHPAY2ACCT.getValue() == order.getOrderType()) {
			result.setItemList(getItemListForAcct(order.getOrderId()));
		}else {
			result.setItemList(getItemListForBank(order.getOrderId()));
		}
		batchPaymentCallbackService.notifyHandle(result);
	}
	
	/**
	 * 根据总订单id获取子订单
	 * @param parentOrderId
	 * @return
	 */
	protected abstract List<BatchPaymentItemResult> getItemListForAcct(Long parentOrderId);
	
	protected abstract List<BatchPaymentItemResult> getItemListForBank(Long parentOrderId);
	
	/**
	 * @param batchPaymentCallbackService the batchPaymentCallbackService to set
	 */
	public void setBatchPaymentCallbackService(
			BatchPaymentCallbackService batchPaymentCallbackService) {
		this.batchPaymentCallbackService = batchPaymentCallbackService;
	}
	
	/**
	 * @param batchPaymentOrderService the batchPaymentOrderService to set
	 */
	public void setBatchPaymentOrderService(
			BatchPaymentOrderService batchPaymentOrderService) {
		this.batchPaymentOrderService = batchPaymentOrderService;
	}
}
