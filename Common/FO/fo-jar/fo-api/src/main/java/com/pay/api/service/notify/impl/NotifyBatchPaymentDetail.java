/**
 *  File: Pay2AcctDetailList.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-12-26   Sany        Create
 *
 */
package com.pay.api.service.notify.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.api.dto.BatchPaymentItemResult;
import com.pay.fo.order.model.base.FundoutOrder;
import com.pay.fo.order.model.base.PayToAcctOrder;

/**
 * 
 */
public class NotifyBatchPaymentDetail extends MerchantNotifyServiceImpl {

	@Override
	protected List<BatchPaymentItemResult> getItemListForAcct(Long parentOrderId) {
		List<PayToAcctOrder> list = batchPaymentOrderService.getAcctDetailOrderByParentId(parentOrderId);
		List<BatchPaymentItemResult> itemResult = new ArrayList<BatchPaymentItemResult>();
		if (list != null) {
			for (PayToAcctOrder pay2Acct : list) {
				BatchPaymentItemResult item = new BatchPaymentItemResult();
				item.setAmount(pay2Acct.getOrderAmount());
				item.setCreateDate(pay2Acct.getCreateDate());
				item.setErrorMsg(pay2Acct.getFailedReason());
				item.setFee(0L);//付款到账户无手续费
				item.setForeignOrderId(pay2Acct.getForeignOrderId());
				item.setpaySeqNo(pay2Acct.getOrderId());
				item.setIsPayerPayFee(1);//付款到账户无手续费
				item.setOrderId(pay2Acct.getForeignOrderId());
				item.setPayeeAcctcode(pay2Acct.getPayeeAcctCode());
				item.setPayeeAcctType(pay2Acct.getPayeeAcctType());
				item.setPayeeLoginName(pay2Acct.getPayeeLoginName());
				item.setPayeeMemberCode(pay2Acct.getPayeeMemberCode());
				item.setPayeeMemberType(pay2Acct.getPayeeMemberType());
				item.setPayeeName(pay2Acct.getPayeeName());
				item.setRemark(pay2Acct.getPaymentReason());
				if (pay2Acct.getOrderStatus() == 111) {
					item.setStatus(1);
				}else {
					item.setStatus(0);
				}
				item.setSuccessTime(pay2Acct.getUpdateDate().toString());
				itemResult.add(item);
			}
		}
		return itemResult;
	}
	
	
	@Override
	protected List<BatchPaymentItemResult> getItemListForBank(Long parentOrderId) {
		List<FundoutOrder> list = batchPaymentOrderService.getBankDetailOrderByParentId(parentOrderId);
		List<BatchPaymentItemResult> itemResult = new ArrayList<BatchPaymentItemResult>();
		if (list != null) {
			for (FundoutOrder order : list) {
				BatchPaymentItemResult item = new BatchPaymentItemResult();
				item.setAmount(order.getOrderAmount());
				item.setCreateDate(order.getCreateDate());
				item.setErrorMsg(order.getFailedReason());
				item.setFee(order.getFee());
				item.setForeignOrderId(order.getForeignOrderId());
				item.setpaySeqNo(order.getOrderId());
				item.setIsPayerPayFee(order.getIsPayerPayFee());
				item.setOrderId(order.getForeignOrderId());
				item.setPayeeBankCity(order.getPayeeBankCity());
				item.setPayeeBankCityName(order.getPayeeBankCityName());
				if (order.getBankOrderId() != null) {
					item.setPayeeBankCode(order.getBankOrderId());
				}
				item.setPayeeBankName(order.getPayeeBankName());
				item.setPayeeBankProvince(order.getPayeeBankProvince());
				item.setPayeeBankProvinceName(order.getPayeeBankProvinceName());
				item.setPayeeOpeningBankName(order.getPayeeOpeningBankName());
				item.setPayeeName(order.getPayeeName());
				item.setRemark(order.getPaymentReason());
				if (order.getOrderStatus() == 111) {
					item.setStatus(1);
				}else {
					item.setStatus(0);
				}
				item.setSuccessTime(order.getUpdateDate().toString());//date
				itemResult.add(item);
			}
		}
		return itemResult;
	}

}
