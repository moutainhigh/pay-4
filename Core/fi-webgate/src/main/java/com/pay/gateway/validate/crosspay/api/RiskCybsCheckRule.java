/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.MemberProductService;
import com.pay.gateway.dto.CrosspayApiRequest;
import com.pay.gateway.dto.CrosspayApiResponse;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.rule.MessageRule;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;

/**
 * cybs判断
 */
public class RiskCybsCheckRule extends MessageRule {

	private final Log logger = LogFactory.getLog(getClass());
	private HessianInvokeService invokeService;
	static String PRODUCT_CODE = "CYBS_CODE";
	protected MemberProductService memberProductService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	public void setMemberProductService(
			MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		CrosspayApiRequest crosspayApiRequest = (CrosspayApiRequest) validateBean;
		CrosspayApiResponse crosspayApiResponse = crosspayApiRequest
				.getCrosspayApiResponse();

		String memberCode = crosspayApiRequest.getPartnerId();

		boolean isHaveProduct = memberProductService.isHaveProduct(
				Long.valueOf(memberCode), PRODUCT_CODE);

		if (!isHaveProduct) {
			return true;
		}

		Map<String, String> paraMap = new HashMap();

		paraMap.put("billTo_city", crosspayApiRequest.getBillCity());
		paraMap.put("billTo_country", crosspayApiRequest.getBillCountryCode());
		paraMap.put("billTo_customerID", crosspayApiRequest.getRegisterUserId());
		paraMap.put("billTo_email", crosspayApiRequest.getBillEmail());
		paraMap.put("billTo_firstName", crosspayApiRequest.getBillName());
		paraMap.put("billTo_lastName", crosspayApiRequest.getBillName());
		paraMap.put("billTo_ipAddress", crosspayApiRequest.getCustomerIP());
		paraMap.put("billTo_phoneNumber",
				crosspayApiRequest.getBillPhoneNumber());
		paraMap.put("billTo_postalCode", crosspayApiRequest.getBillPostalCode());
		paraMap.put("billTo_state", crosspayApiRequest.getBillState());
		paraMap.put("billTo_street1", crosspayApiRequest.getBillStreet());
		paraMap.put("card_accountNumber",
				crosspayApiRequest.getCardHolderNumber());
		paraMap.put("card_expirationMonth",
				crosspayApiRequest.getCardExpirationMonth());
		paraMap.put("card_expirationYear",
				crosspayApiRequest.getCardExpirationYear());
		paraMap.put("shipTo_country",
				crosspayApiRequest.getShippingCountryCode());
		paraMap.put("shipTo_state", crosspayApiRequest.getShippingState());
		paraMap.put("shipTo_city", crosspayApiRequest.getShippingCity());
		paraMap.put("shipTo_street1", crosspayApiRequest.getShippingStreet());
		paraMap.put("shipTo_postalCode",
				crosspayApiRequest.getShippingPostalCode());
		paraMap.put("shipTo_phoneNumber",
				crosspayApiRequest.getShippingPhoneNumber());
		paraMap.put("shipTo_firstName", crosspayApiRequest.getShippingName());
		paraMap.put("shipTo_lastName", crosspayApiRequest.getShippingName());
		paraMap.put("deviceFingerprintID",
				crosspayApiRequest.getDeviceFingerprintId());
		paraMap.put("merchantReferenceCode", crosspayApiRequest.getOrderId());
		paraMap.put("purchaseTotals_currency",
				crosspayApiRequest.getCurrencyCode());

		paraMap.put("item_0_unitPrice",crosspayApiRequest.getOrderAmount());
		paraMap.put("merchant_defined_data1",crosspayApiRequest.getPartnerId());
		paraMap.put("merchant_defined_data2",crosspayApiRequest.getGoodsDesc());
		paraMap.put("merchant_defined_data3",
		 "MCC");
		paraMap.put("merchant_defined_data4",crosspayApiRequest.getSiteId());

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke("7010203", sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(), SystemCodeEnum.PRE.getCode(),
				SystemCodeEnum.PRE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String decision = resultMap.get("decision");

		if (logger.isInfoEnabled()) {
			logger.info("cybs result:" + decision + ",orderId:"
					+ crosspayApiRequest.getOrderId());
		}
		boolean resultFlg = "ACCEPT".equals(decision)
				|| "REVIEW".equals(decision);
		if (!resultFlg) {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		} else {
			return true;
		}
	}
}
