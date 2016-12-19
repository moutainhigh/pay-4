/**
 *  File: HttpMessageHandel.java
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.api.dto.BatchPaymentItemRequest;
import com.pay.api.dto.BatchPaymentItemResult;
import com.pay.api.dto.BatchPaymentRequest;
import com.pay.api.dto.BatchPaymentResult;
import com.pay.api.dto.http.PaymentItemRequest;
import com.pay.api.dto.http.PaymentItemResult;
import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.PayType;
import com.pay.api.helper.SignType;
import com.pay.api.model.MerchantConfigure;
import com.pay.api.model.MerchantConfigureCriteria;
import com.pay.api.model.MerchantRequest;
import com.pay.api.service.BaseMessageHandle;
import com.pay.api.service.BatchPaymentService;
import com.pay.api.util.FreeMarkerUtil;
import com.pay.api.util.ParameterXmlParserUtil;
import com.pay.inf.dao.BaseDAO;
import com.pay.service.ValidateService;
import com.pay.util.MD5Util;

/**
 * 
 */
public class PaymentMessageHandle extends BaseMessageHandle {

	private Log logger = LogFactory.getLog(PaymentMessageHandle.class);
	private ValidateService validateService;
	private BatchPaymentService batchPaymentService;
	private BaseDAO<MerchantConfigure> merchantConfigureDao;

	@Override
	protected PaymentRequest transferRequest(String requestXml) {

		PaymentRequest request = ParameterXmlParserUtil.parser(
				PaymentRequest.class, requestTransferMap, requestXml,
				"REQUEST_HEADER");
		List<PaymentItemRequest> itemList = ParameterXmlParserUtil.parserList(
				PaymentItemRequest.class, requestTransferMap, requestXml,
				"REQUEST_BODY");
		request.setItemList(itemList);

		PaymentResult result = request.getResult();
		result.setBizNo(request.getBizNo());
		result.setMerchantCode(request.getMerchantCode());

		String totalAmount = request.getTotalAmount();
		String totalCount = request.getTotalCount();

		try {
			result.setTotalAmount(new BigDecimal(totalAmount).longValue());
			result.setTotalCount(new BigDecimal(totalCount).intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return request;
	}

	@Override
	protected String checkRequest(String requestXml, PaymentRequest request)
			throws Exception {

		// save request
		MerchantRequest requestLog = buildMerchantRequest(request
				.getMerchantCode(), request.getBizNo(), requestXml);
		try{
			merchantRequestService.saveMerchantRequestRnTx(requestLog);
		}catch(Exception e){
			logger.error("save error:", e);
			PaymentResult result = new PaymentResult();
			result.setErrorCode(ErrorCode.BUSINESSNO_INVALID);
			result.setErrorMsg(ErrorCode.BUSINESSNO_INVALID_DESC);
			request.setResult(result);
			return ErrorCode.BUSINESSNO_INVALID;
		}

		request.setXml(requestXml);

		// verify
		return validateService.validate(request);
	}

	@Override
	protected String generateResultXml(PaymentRequest request,
			PaymentResult result) {
		Map parameterMap = new HashMap();
		
		String errorCode = result.getErrorCode();
		
		parameterMap.put("SUCCESS_AMOUNT", String.valueOf(result
				.getSuccessAmount() / 10));
		parameterMap.put("SUCCESS_COUNT", String.valueOf(result
				.getSuccessCount()));
		

		parameterMap.put("BIZ_NO", request.getBizNo());
		parameterMap.put("TOTAL_AMOUNT", request.getTotalAmount());
		parameterMap.put("TOTAL_COUNT", request.getTotalCount());
		
		parameterMap.put("ERROR_CODE", result.getErrorCode());
		parameterMap.put("ERROR_MSG", result.getErrorMsg());
		// 

		List<PaymentItemResult> itemList = result.getItemList();
		
		parameterMap.put("itemList", itemList);

		String resultXml = FreeMarkerUtil.parser(responseXmlTemplate.trim(),
				parameterMap);

		String srcData = ParameterXmlParserUtil.getNodeText(resultXml,
				"RESPONSE_BODY");
		
		
		MerchantConfigureCriteria criteria = new MerchantConfigureCriteria();
		criteria.createCriteria().andMerchantCodeEqualTo(request.getMerchantCode());
		MerchantConfigure merchantConfigure = (MerchantConfigure) merchantConfigureDao
				.findObjectByCriteria(criteria);
		String signType = request.getSignType();
		if(null != merchantConfigure && SignType.MD5.getValue().equals(signType)){
			String signvalue = MD5Util.md5Hex(srcData + merchantConfigure.getPublicKey());
			parameterMap.put("SIGNVALUE", signvalue);
		} else if (SignType.RSA.getValue().equals(signType)) {
			String signvalue = securityProvider.generateSignature(srcData);
			parameterMap.put("SIGNVALUE", signvalue);
		}

		resultXml = FreeMarkerUtil.parser(responseXmlTemplate.trim(),
				parameterMap);

		return resultXml;
	}

	@Override
	protected PaymentResult handle(PaymentRequest request) throws Exception {

		BatchPaymentRequest batchPaymentRequest = buildBatchPaymentRequest(request);
		BatchPaymentResult batchPaymentResult = batchPaymentService
				.payment(batchPaymentRequest);

		// transfer result
		PaymentResult result = transferResult(request, batchPaymentResult);

		return result;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public void setBatchPaymentService(BatchPaymentService batchPaymentService) {
		this.batchPaymentService = batchPaymentService;
	}
	
	public void setMerchantConfigureDao(
			BaseDAO<MerchantConfigure> merchantConfigureDao) {
		this.merchantConfigureDao = merchantConfigureDao;
	}

	private PaymentResult transferResult(PaymentRequest request,
			BatchPaymentResult batchPaymentResult) {

		PaymentResult result = new PaymentResult();
		String bizNo = request.getBizNo();
		Long totalAmount = batchPaymentResult.getTotalAmount();
		Integer totalCount = batchPaymentResult.getTotalCount();
		Long successAmount = batchPaymentResult.getSuccessAmount();
		Long totalFee = batchPaymentResult.getTotalFee();
		Integer successCount = batchPaymentResult.getSuccessCount();
		String errorCode = batchPaymentResult.getErrorCode();

		List<BatchPaymentItemResult> batchPaymentItemList = batchPaymentResult
				.getItemList();

		result.setBizNo(bizNo);
		result.setSuccessAmount(successAmount);
		result.setSuccessCount(successCount);
		
		if (null != totalFee) {
			totalFee = new BigDecimal(totalFee).divide(new BigDecimal("10"))
					.longValue();
		}
		result.setTotalAmount(totalAmount);
		result.setTotalCount(totalCount);
		result.setTotalFee(totalFee);
		
		result.setErrorCode(errorCode);
		result.setErrorMsg(batchPaymentResult.getErrorMsg());
		List<PaymentItemResult> itemList = null;
		if (null != batchPaymentItemList) {
			itemList = new ArrayList<PaymentItemResult>();
			for (BatchPaymentItemResult item : batchPaymentItemList) {
				PaymentItemResult itemResult = new PaymentItemResult();
				itemResult.setErrorCode(item.getErrorCode());
				itemResult.setErrorMsg(item.getErrorMsg());
				itemResult.setpaySeqNo(item.getpaySeqNo());
				itemResult.setOrderId(item.getOrderId());
				itemResult.setStatus(item.getStatus());
				itemResult.setSuccessTime(item.getSuccessTime());
				itemList.add(itemResult);
			}
		}
		result.setItemList(itemList);
		return result;
	}

	private BatchPaymentRequest buildBatchPaymentRequest(PaymentRequest request) {

		BatchPaymentRequest batchPaymentRequest = new BatchPaymentRequest();
		List<BatchPaymentItemRequest> batchItemList = new ArrayList<BatchPaymentItemRequest>();
		batchPaymentRequest.setAuditFlag(Integer
				.valueOf(request.getAuditFlag()));
		batchPaymentRequest.setBizNo(request.getBizNo());
		batchPaymentRequest.setCurrencyCode(Integer.valueOf(request
				.getCurrencyCode()));
		batchPaymentRequest.setFeeType(Integer.valueOf(request.getFeeType()));
		batchPaymentRequest.setMerchantCode(Long.valueOf(request
				.getMerchantCode()));
		batchPaymentRequest.setPayType(Integer.valueOf(request.getPayType()));
		batchPaymentRequest.setTotalAmount(Long.valueOf(request
				.getTotalAmount()) * 10);
		batchPaymentRequest.setTotalCount(Integer.valueOf(request
				.getTotalCount()));
		List<PaymentItemRequest> itemList = request.getItemList();
		for (PaymentItemRequest item : itemList) {
			BatchPaymentItemRequest batchItem = new BatchPaymentItemRequest();
			batchItem.setAmount(new BigDecimal(item.getAmount()).multiply(
					new BigDecimal("10")).longValue());
			batchItem.setBankName(item.getBankName());
			batchItem.setBranche(item.getBranche());
			batchItem.setCity(item.getCity());
			batchItem.setNote(item.getNote());
			batchItem.setOrderId(item.getOrderId());
			batchItem.setPayeeAccount(item.getPayeeAccount());
			batchItem.setPayeeMobile(item.getPayeeMobile());
			batchItem.setPayeeName(item.getPayeeName());
			if (Integer.valueOf(request.getPayType()) == PayType.BANK
					.getValue()) {
				batchItem.setPayeeType(Integer.valueOf(item.getPayeeType()));
			}

			batchItem.setProvince(item.getProvince());
			batchItem.setRemark(item.getRemark());
			batchItem.setNote(item.getNote());
			batchItem.setOrgCode(item.getResult().getOrgCode());
			batchItem.setProvinceCode(item.getResult().getProvinceCode());
			batchItem.setCityCode(item.getResult().getCityCode());
			batchItem.setFeeType(Integer.valueOf(request.getFeeType()));
			batchItem.setMerchantCode(Long.valueOf(request.getMerchantCode()));
			batchItem.setPayType(Integer.valueOf(request.getPayType()));
			batchItemList.add(batchItem);
		}

		batchPaymentRequest.setItemList(batchItemList);

		return batchPaymentRequest;
	}

	private MerchantRequest buildMerchantRequest(String merchantCode,
			String bizNo, String requestXml) {
		MerchantRequest merchantRequest = new MerchantRequest();
		merchantRequest.setContent(requestXml);
		merchantRequest.setMerchantId(merchantCode);
		merchantRequest.setOrderId(bizNo);
		merchantRequest.setRequestDate(new Date());
		// merchantRequest.setRequestIp(command.getClientIp());
		// merchantRequest.setVersionNo(command.getVersion());
		return merchantRequest;
	}

}
