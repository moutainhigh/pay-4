/**
 *  File: BaseMessageHandle.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 21, 2011   ch-ma     Create
 *
 */
package com.pay.api.service;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.model.MerchantResponse;

/**
 * 
 */
public abstract class BaseMessageHandle implements IMessageHandleService {

	private Log logger = LogFactory.getLog(BaseMessageHandle.class);
	protected MerchantRequestService merchantRequestService;
	protected MerchantResponseService merchantResponseService;
	protected Map<String, String> requestTransferMap;
	protected ISecurityProvider securityProvider;
	protected String responseXmlTemplate;

	protected abstract String checkRequest(String requestXml, PaymentRequest request) throws Exception;

	protected abstract PaymentResult handle(PaymentRequest request) throws Exception;

	protected abstract String generateResultXml(PaymentRequest request, PaymentResult result);
	
	protected abstract PaymentRequest transferRequest(String requestXml);

	@Override
	public String process(final String merchantCode, final String bizNo, String requestInfo) {

		String resultXml = null;

		// parse
		PaymentRequest request = transferRequest(requestInfo);
		try {
			// check
			String errorCode = checkRequest(requestInfo, request);
			if (null != errorCode) {
				resultXml = generateResultXml(request, request.getResult());
				MerchantResponse merchantResponse = buildMerchantResponse(request.getMerchantCode(), request.getBizNo(), resultXml);
				merchantResponseService.saveMerchantResponseRnTx(merchantResponse);
				return resultXml;
			}

			// do payment
			PaymentResult result = handle(request);
			resultXml = generateResultXml(request, result);

			// save response
			MerchantResponse merchantResponse = buildMerchantResponse(merchantCode, bizNo, resultXml);
			merchantResponseService.saveMerchantResponseRnTx(merchantResponse);
		} catch (Exception e) {
			logger.error("payment error:", e);
			PaymentResult result = new PaymentResult();
			result.setMerchantCode(merchantCode);
			result.setBizNo(bizNo);
			resultXml = generateResultXml(request, result);
		}

		return resultXml;
	}

	public void setMerchantRequestService(MerchantRequestService merchantRequestService) {
		this.merchantRequestService = merchantRequestService;
	}

	public void setMerchantResponseService(MerchantResponseService merchantResponseService) {
		this.merchantResponseService = merchantResponseService;
	}

	public void setRequestTransferMap(Map<String, String> requestTransferMap) {
		this.requestTransferMap = requestTransferMap;
	}

	public void setSecurityProvider(ISecurityProvider securityProvider) {
		this.securityProvider = securityProvider;
	}

	public void setResponseXmlTemplate(String responseXmlTemplate) {
		this.responseXmlTemplate = responseXmlTemplate;
	}

	protected MerchantResponse buildMerchantResponse(String merchantCode, String bizNo, String responseXml) {
		MerchantResponse merchantResponse = new MerchantResponse();
		merchantResponse.setContent(responseXml);
		merchantResponse.setMerchantId(merchantCode);
		merchantResponse.setOrderId(bizNo);
		merchantResponse.setCreateDate(new Date());
		return merchantResponse;
	}

}
