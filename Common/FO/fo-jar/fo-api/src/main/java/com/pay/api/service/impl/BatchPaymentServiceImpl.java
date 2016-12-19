/**
 *  File: BatchPaymentServiceImpl.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 22, 2011   ch-ma     Create
 *
 */
package com.pay.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pay.api.dto.BatchPaymentItemRequest;
import com.pay.api.dto.BatchPaymentItemResult;
import com.pay.api.dto.BatchPaymentRequest;
import com.pay.api.dto.BatchPaymentResult;
import com.pay.api.helper.AuditFlag;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.FeeType;
import com.pay.api.helper.PayType;
import com.pay.api.helper.PayeeType;
import com.pay.api.helper.RequestStatus;
import com.pay.api.service.BatchPaymentService;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToAcctReqDetailDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fo.order.dto.batchpayment.RequestDetail;
import com.pay.fo.order.service.batchpay2acct.BatchPay2AcctOrderService;
import com.pay.fo.order.service.batchpay2acct.BatchPay2AcctRequestService;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankOrderService;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankRequestService;
import com.pay.fo.order.service.batchpayment.BatchPaymentReqBaseInfoService;
import com.pay.service.ValidateService;
import com.pay.util.StringUtil;

/**
 * 
 */
public class BatchPaymentServiceImpl implements BatchPaymentService {

	private BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService;
	private BatchPay2BankRequestService batchPay2BankRequestService;
	private BatchPay2AcctRequestService batchPay2AcctRequestService;
	private BatchPay2BankOrderService batchPay2BankOrderService;
	private BatchPay2AcctOrderService batchPay2AcctOrderService;
	private ValidateService validateService;

	@Override
	public BatchPaymentResult payment(BatchPaymentRequest request)
			throws Exception {

		// validate
		String errorCode = validateService.validate(request);

		if (null != errorCode && !errorCode.equals(ErrorCode.SUCCESS)) {
			return request.getResult();
		}

		Integer payType = request.getPayType();
		Integer auditFlag = request.getAuditFlag();
		BatchPaymentResult result = request.getResult();

		// build request info
		BatchPaymentReqBaseInfoDTO reqInfo = buildRequestInfo(request, result);
		
		List<BatchPaymentItemRequest> itemList = request.getItemList();
		List<RequestDetail> details = new ArrayList<RequestDetail>();
		if (payType == PayType.BANK.getValue()) {
			buildBankReqDetail(reqInfo, details, itemList);
			// 保存请求信息
			batchPay2BankRequestService.saveRequestInfoRnTx(reqInfo);
		} else {
			buildAcctReqDetail(reqInfo, details, itemList);
			batchPay2AcctRequestService.saveRequestInfoRnTx(reqInfo);
		}

		// update busiNo
		batchPaymentReqBaseInfoService.update(reqInfo);

		if (auditFlag == AuditFlag.NO.getValue()) {
			// if audit pass ,to do
			BatchPaymentOrderDTO order = buildBatchPaymentOrder(reqInfo);

			if (payType == PayType.BANK.getValue()) {
				batchPay2BankOrderService.createOrder(order);
			} else {
				batchPay2AcctOrderService.createOrder(order);
			}
		}

		return result;
	}

	public void setBatchPay2BankRequestService(
			BatchPay2BankRequestService batchPay2BankRequestService) {
		this.batchPay2BankRequestService = batchPay2BankRequestService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public void setBatchPay2BankOrderService(
			BatchPay2BankOrderService batchPay2BankOrderService) {
		this.batchPay2BankOrderService = batchPay2BankOrderService;
	}

	public void setBatchPay2AcctOrderService(
			BatchPay2AcctOrderService batchPay2AcctOrderService) {
		this.batchPay2AcctOrderService = batchPay2AcctOrderService;
	}

	public void setBatchPay2AcctRequestService(
			BatchPay2AcctRequestService batchPay2AcctRequestService) {
		this.batchPay2AcctRequestService = batchPay2AcctRequestService;
	}

	public void setBatchPaymentReqBaseInfoService(
			BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService) {
		this.batchPaymentReqBaseInfoService = batchPaymentReqBaseInfoService;
	}

	private void buildBankReqDetail(BatchPaymentReqBaseInfoDTO reqInfo,
			List<RequestDetail> details, List<BatchPaymentItemRequest> itemList) {
		for (BatchPaymentItemRequest item : itemList) {
			BatchPaymentToBankReqDetailDTO detail = new BatchPaymentToBankReqDetailDTO();
			BatchPaymentItemResult itemResult = item.getResult();
			detail.setBankNumber(itemResult.getBankNumber());
			detail.setCreateDate(new Date());
			detail.setErrorMsg(itemResult.getErrorMsg());
			detail.setFee(itemResult.getFee());
			detail.setForeignOrderId(item.getOrderId());
			detail.setIsPayerPayFee(item.getFeeType());
			detail.setOrderStatus(OrderStatus.INIT.getValue());
			detail.setOrderStatusDesc(OrderStatus.INIT.getDesc());
			detail.setPayeeBankAcctCode(item.getPayeeAccount());
			detail.setPayeeBankCity(item.getCityCode());
			detail.setPayeeBankCityName(item.getCity());
			detail.setPayeeBankCode(item.getOrgCode());
			detail.setPayeeBankName(item.getBankName());
			detail.setPayeeBankProvince(item.getProvinceCode());
			detail.setPayeeBankProvinceName(item.getProvince());
			detail.setPayeeName(item.getPayeeName());
			detail.setPayeeOpeningBankName(item.getBranche());
			detail.setPaymentAmount(item.getAmount());
			detail.setRealpayAmount(itemResult.getRealpayAmount());
			detail.setRealoutAmount(itemResult.getRealoutAmount());
			detail.setRemark(item.getNote());
			detail.setRequestAmount(new BigDecimal(item.getAmount()).divide(new BigDecimal("1000")).toString());
			if (item.getPayeeType() == PayeeType.INDIVIDUAL.getValue()) {
				detail.setTradeType("C");
			} else {
				detail.setTradeType("B");
			}
			detail.setUpdateDate(new Date());
			if (StringUtil.isEmpty(itemResult.getErrorCode()) || itemResult.getErrorCode().equals(ErrorCode.SUCCESS)) {
				detail.setValidStatus(RequestStatus.SUCCESS.getValue());
			} else {
				detail.setValidStatus(RequestStatus.FAIL.getValue());
			}
			details.add(detail);
		}
		reqInfo.setRequestDetails(details);
	}

	private void buildAcctReqDetail(BatchPaymentReqBaseInfoDTO reqInfo,
			List<RequestDetail> details, List<BatchPaymentItemRequest> itemList) {
		for (BatchPaymentItemRequest item : itemList) {
			BatchPaymentToAcctReqDetailDTO detail = new BatchPaymentToAcctReqDetailDTO();
			BatchPaymentItemResult itemResult = item.getResult();
			detail.setCreateDate(new Date());
			detail.setErrorMsg(itemResult.getErrorMsg());
			// detail.setFee(itemResult.getFee());
			detail.setForeignOrderId(item.getOrderId());
			// detail.setIsPayerPayFee(item.getFeeType());
			detail.setOrderStatus(OrderStatus.INIT.getValue());
			detail.setOrderStatusDesc(OrderStatus.INIT.getDesc());
			detail.setPayeeName(item.getPayeeName());
			detail.setPaymentAmount(item.getAmount());
			detail.setRemark(item.getNote());
			detail.setRequestAmount(new BigDecimal(item.getAmount()).divide(new BigDecimal("1000")).toString());
			detail.setUpdateDate(new Date());
			if (StringUtil.isEmpty(itemResult.getErrorCode()) || itemResult.getErrorCode().equals(ErrorCode.SUCCESS)) {
				detail.setValidateStatus(RequestStatus.SUCCESS.getValue());
			} else {
				detail.setValidateStatus(RequestStatus.FAIL.getValue());
			}

			detail.setPayeeLoginName(itemResult.getPayeeLoginName());
			details.add(detail);
		}
		reqInfo.setRequestDetails(details);
	}

	private BatchPaymentReqBaseInfoDTO buildRequestInfo(
			BatchPaymentRequest request, BatchPaymentResult result) {

		BatchPaymentReqBaseInfoDTO reqInfo = new BatchPaymentReqBaseInfoDTO();
		Integer payType = request.getPayType();
		Integer feeType = request.getFeeType();
		Integer auditFlag = request.getAuditFlag();
		Long successAmount = result.getSuccessAmount();
		Integer successCount = result.getSuccessCount();
		Long totalFee = result.getTotalFee();

		reqInfo.setAuditRemark("");
		reqInfo.setBusinessBatchNo(request.getBizNo());
		reqInfo.setCreateDate(new Date());
		reqInfo.setUpdateDate(new Date());
		reqInfo.setCreator("api");
		reqInfo.setErrorMsg(result.getErrorMsg());
		reqInfo.setFee(result.getTotalFee());
		reqInfo.setRequestSrc("api");
		reqInfo.setIsPayerPayFee(feeType);
		reqInfo.setPayerAcctCode(result.getPayerAcctcode());
		reqInfo.setPayerAcctType(result.getPayerAcctType());
		reqInfo.setPayerLoginName(result.getPayerLoginName());
		reqInfo.setPayerMemberCode(request.getMerchantCode());
		reqInfo.setPayerMemberType(result.getPayerMemberType());
		reqInfo.setPayerName(result.getPayerName());

		if (auditFlag == AuditFlag.NO.getValue()) {
			reqInfo.setStatus(2);
			reqInfo.setAuditor("system");
		} else {
			reqInfo.setStatus(1);
		}

		if (payType == PayType.ACCT.getValue()) {
			reqInfo.setRequestType(OrderType.BATCHPAY2ACCT.getValue());
		} else {
			reqInfo.setRequestType(OrderType.BATCHPAY2BANK.getValue());
		}

		if (feeType == FeeType.PAYER.getValue()) {
			reqInfo.setRealpayAmount(totalFee + successAmount);
			reqInfo.setRealoutAmount(successAmount);
		} else {
			reqInfo.setRealpayAmount(successAmount);
			reqInfo.setRealoutAmount(successAmount - totalFee);
		}

		reqInfo.setRequestAmount(request.getTotalAmount());
		reqInfo.setRequestCount(request.getTotalCount());
		reqInfo.setValidAmount(successAmount);
		reqInfo.setValidCount(successCount);
		return reqInfo;
	}

	private BatchPaymentOrderDTO buildBatchPaymentOrder(
			BatchPaymentReqBaseInfoDTO reqInfo) {
		BatchPaymentOrderDTO order = new BatchPaymentOrderDTO();
		order.setBusinessBatchNo(reqInfo.getBusinessBatchNo());
		order.setCreateDate(reqInfo.getUpdateDate());
		order.setCreator(reqInfo.getAuditor());
		order.setOrderType(reqInfo.getRequestType());
		order.setOrderStatus(OrderStatus.INIT.getValue());

		if (reqInfo.getRequestType() == 4) {
			order
					.setOrderSmallType(OrderSmallType.API_BATCHPAY2BANK
							.getValue());
		} else {
			order
					.setOrderSmallType(OrderSmallType.API_BATCHPAY2ACCT
							.getValue());
		}

		order.setPayerAcctCode(reqInfo.getPayerAcctCode());
		order.setPayerAcctType(reqInfo.getPayerAcctType());
		order.setPayerLoginName(reqInfo.getPayerLoginName());
		order.setPayerMemberCode(reqInfo.getPayerMemberCode());
		order.setPayerMemberType(reqInfo.getPayerMemberType());
		order.setPayerName(reqInfo.getPayerName());

		order.setOrderAmount(reqInfo.getValidAmount());
		order.setIsPayerPayFee(reqInfo.getIsPayerPayFee());
		order.setFee(reqInfo.getFee());
		order.setRealoutAmount(reqInfo.getRealoutAmount());
		order.setRealpayAmount(reqInfo.getRealpayAmount());
		order.setPaymentCount(reqInfo.getValidCount());

		return order;
	}
}
