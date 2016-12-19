/**
 *  File: BatchPaymentOrderCommService.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-12   terry     Create
 *
 */
package com.pay.fo.order.service.batchpayment.impl;

import java.util.Date;

import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToAcctReqDetailDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;
import com.pay.fundout.service.ma.AccountInfo;
import com.pay.fundout.service.ma.AccountQueryFacadeService;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.service.ma.MemberQueryFacadeService;

/**
 * 
 */
public abstract class BatchPaymentOrderCommService implements
		BatchPaymentOrderService {

	/**
	 * 会员信息查询服务类
	 */
	private MemberQueryFacadeService memberQueryFacadeService;

	/**
	 * 账户信息查询服务类
	 */
	private AccountQueryFacadeService accountQueryFacadeService;

	public void setMemberQueryFacadeService(
			MemberQueryFacadeService memberQueryFacadeService) {
		this.memberQueryFacadeService = memberQueryFacadeService;
	}

	public void setAccountQueryFacadeService(
			AccountQueryFacadeService accountQueryFacadeService) {
		this.accountQueryFacadeService = accountQueryFacadeService;
	}

	protected FundoutOrderDTO buildDetailOrder(
			BatchPaymentOrderDTO parentOrder,
			BatchPaymentToBankReqDetailDTO reqDetail) {

		FundoutOrderDTO detail = new FundoutOrderDTO();

		detail.setCreateDate(reqDetail.getUpdateDate());
		detail.setForeignOrderId(reqDetail.getForeignOrderId());
		detail.setOrderAmount(reqDetail.getPaymentAmount());
		detail.setOrderSmallType(parentOrder.getOrderSmallType());
		detail.setOrderStatus(OrderStatus.PROCESSING.getValue());
		detail.setOrderType(OrderType.BATCHPAY2BANK.getValue());
		detail.setParentOrderId(parentOrder.getOrderId());

		detail.setIsPayerPayFee(reqDetail.getIsPayerPayFee());
		detail.setFee(reqDetail.getFee());
		detail.setFundoutMode(1);
		detail.setCreateDate(new Date());
		detail.setUpdateDate(new Date());
		detail.setPayeeBankAcctCode(reqDetail.getPayeeBankAcctCode());
		detail.setPayeeBankAcctType(1);
		detail.setPayeeBankCity(reqDetail.getPayeeBankCity());
		detail.setPayeeBankCityName(reqDetail.getPayeeBankCityName());
		detail.setPayeeBankCode(reqDetail.getPayeeBankCode());
		detail.setPayeeBankName(reqDetail.getPayeeBankName());
		detail.setPayeeBankProvince(reqDetail.getPayeeBankProvince());
		detail.setPayeeBankProvinceName(reqDetail.getPayeeBankProvinceName());
		detail.setPayeeName(reqDetail.getPayeeName());
		detail.setPayeeOpeningBankName(reqDetail.getPayeeOpeningBankName());

		detail.setPayerAcctCode(parentOrder.getPayerAcctCode());
		detail.setPayerAcctType(parentOrder.getPayerAcctType());
		detail.setPayerLoginName(parentOrder.getPayerLoginName());
		detail.setPayerMemberCode(parentOrder.getPayerMemberCode());
		detail.setPayerMemberType(parentOrder.getPayerMemberType());
		detail.setPayerName(parentOrder.getPayerName());
//		detail.setFundoutBankCode(reqDetail.get);
		detail.setPaymentReason(reqDetail.getRemark());
		detail.setPriority(5);
		detail.setRealoutAmount(reqDetail.getRealoutAmount());
		detail.setRealpayAmount(reqDetail.getRealpayAmount());
		if(parentOrder.getOrderSmallType().equals(OrderSmallType.AUTO_BATCHPAY2BANK.getValue())){
			detail.setTradeAlias(OrderSmallType.AUTO_BATCHPAY2BANK.getDesc());
		}else{
			detail.setTradeAlias(OrderSmallType.COMMON_BATCHPAY2BANK.getDesc());
		}
		int tradeType = 0;
		if ("b".equalsIgnoreCase(reqDetail.getTradeType())) {
			tradeType = 1;
		}
		detail.setTradeType(tradeType);
		detail.setBankNumber(reqDetail.getBankNumber());
		return detail;
	}

	// construct pay2acct item
	protected PayToAcctOrderDTO buildDetailOrder(
			BatchPaymentOrderDTO parentOrder,
			BatchPaymentToAcctReqDetailDTO reqDetail) {
		PayToAcctOrderDTO detail = new PayToAcctOrderDTO();
		detail.setCreateDate(reqDetail.getUpdateDate());
		detail.setForeignOrderId(reqDetail.getForeignOrderId());
		detail.setOrderAmount(reqDetail.getPaymentAmount());
		detail.setOrderSmallType(OrderSmallType.COMMON_BATCHPAY2ACCT.getValue());
		detail.setOrderStatus(OrderStatus.INIT.getValue());
		detail.setOrderType(OrderType.BATCHPAY2ACCT.getValue());
		detail.setParentOrderId(parentOrder.getOrderId());
		MemberInfo payeeMember = memberQueryFacadeService.getMemberInfo(reqDetail.getPayeeLoginName());
		AccountInfo account = accountQueryFacadeService.getAccountInfo(payeeMember.getMemberCode(), parentOrder.getPayerAcctType());
		detail.setPayeeAcctCode(account.getAcctCode());
		detail.setPayeeAcctType(parentOrder.getPayerAcctType());
		detail.setPayeeLoginName(reqDetail.getPayeeLoginName());
		detail.setPayeeMemberCode(payeeMember.getMemberCode());
		detail.setPayeeMemberType(payeeMember.getMemberType());
		detail.setPayeeName(payeeMember.getMemberName());
		detail.setPayerAcctCode(parentOrder.getPayerAcctCode());
		detail.setPayerAcctType(parentOrder.getPayerAcctType());
		detail.setPayerLoginName(parentOrder.getPayerLoginName());
		detail.setPayerMemberCode(parentOrder.getPayerMemberCode());
		detail.setPayerMemberType(parentOrder.getPayerMemberType());
		detail.setPayerName(parentOrder.getPayerName());
		detail.setPayerFee(reqDetail.getPayerFee());
		detail.setPaymentReason(reqDetail.getRemark());
		detail.setTradeAlias(OrderSmallType.COMMON_BATCHPAY2ACCT.getDesc());
		int tradeType = 0;
		if (payeeMember.getMemberType() == MemberTypeEnum.MERCHANT.getCode()) {
			tradeType = 1;
		}
		detail.setTradeType(tradeType);

		return detail;
	}
}
