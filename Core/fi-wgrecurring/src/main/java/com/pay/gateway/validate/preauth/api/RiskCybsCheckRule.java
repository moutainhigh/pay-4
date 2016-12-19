/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.preauth.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.MemberProductService;
import com.pay.gateway.dto.PreauthApiRequest;
import com.pay.gateway.dto.PreauthApiResponse;
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

		PreauthApiRequest preauthApiRequest = (PreauthApiRequest) validateBean;
		PreauthApiResponse preauthApiResponse = preauthApiRequest
				.getPreauthApiResponse();

		String memberCode = preauthApiRequest.getPartnerId();

		boolean isHaveProduct = memberProductService.isHaveProduct(
				Long.valueOf(memberCode), PRODUCT_CODE);

		if (!isHaveProduct) {
			return true;
		}

		Map<String, String> paraMap = new HashMap<String,String>();

		paraMap.put("billTo_city", preauthApiRequest.getBillCity());
		paraMap.put("billTo_country", preauthApiRequest.getBillCountryCode());
		//paraMap.put("billTo_customerID", preauthApiRequest.get);
		paraMap.put("billTo_email", preauthApiRequest.getBillEmail());
		paraMap.put("billTo_firstName", preauthApiRequest.getBillFirstName());
		paraMap.put("billTo_lastName", preauthApiRequest.getBillLastName());
		paraMap.put("billTo_ipAddress", preauthApiRequest.getCustomerIP());
		paraMap.put("billTo_phoneNumber",
				preauthApiRequest.getBillPhoneNumber());
		paraMap.put("billTo_postalCode", preauthApiRequest.getBillPostalCode());
		paraMap.put("billTo_state", preauthApiRequest.getBillState());
		paraMap.put("billTo_street1", preauthApiRequest.getBillStreet());
		paraMap.put("card_accountNumber",
				preauthApiRequest.getCardHolderNumber());
		paraMap.put("card_expirationMonth",
				preauthApiRequest.getCardExpirationMonth());
		paraMap.put("card_expirationYear",
				preauthApiRequest.getCardExpirationYear());
		/*paraMap.put("shipTo_country",
				preauthApiRequest.getShippingCountryCode());
		paraMap.put("shipTo_state", preauthApiRequest.getShippingState());
		paraMap.put("shipTo_city", preauthApiRequest.getShippingCity());
		paraMap.put("shipTo_street1", preauthApiRequest.getShippingStreet());
		paraMap.put("shipTo_postalCode",
				preauthApiRequest.getShippingPostalCode());
		paraMap.put("shipTo_phoneNumber",
				preauthApiRequest.getShippingPhoneNumber());
		paraMap.put("shipTo_firstName", preauthApiRequest.getShippingName());
		paraMap.put("shipTo_lastName", preauthApiRequest.getShippingName());*/
		paraMap.put("deviceFingerprintID",
				preauthApiRequest.getDeviceFingerprintId());
		paraMap.put("merchantReferenceCode", preauthApiRequest.getOrderId());
		paraMap.put("purchaseTotals_currency",
				preauthApiRequest.getCurrencyCode());
		paraMap.put("item_0_unitPrice", preauthApiRequest.getOrderAmount());
		// paraMap.put("merchant_defined_data3",
		// preauthApiRequest.getCurrencyCode());

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
					+ preauthApiRequest.getOrderId());
		}
		boolean resultFlg = "ACCEPT".equals(decision)
				|| "REVIEW".equals(decision);
		if (!resultFlg) {
			preauthApiResponse.setResultCode(getMessageId());
			preauthApiResponse.setResultMsg(getMessage());
			return false;
		} else {
			return true;
		}
	}

}
