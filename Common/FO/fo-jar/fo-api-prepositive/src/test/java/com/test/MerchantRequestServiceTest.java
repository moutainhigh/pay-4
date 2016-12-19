/**
 *  File: MerchantConfigureTest.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 15, 2011   ch-ma     Create
 *
 */
package com.test;

import java.util.Date;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.pay.api.helper.AuditFlag;
import com.pay.api.helper.CurrencyCode;
import com.pay.api.helper.FeeType;
import com.pay.api.helper.PayType;
import com.pay.api.helper.PayeeType;
import com.pay.api.model.MerchantRequest;
import com.pay.api.service.MerchantRequestService;
import com.pay.api.util.ParameterXmlParserUtil;

/**
 * 
 */
public class MerchantRequestServiceTest extends AbstractTestNG {

	@Resource(name = "merchantRequestService")
	private MerchantRequestService merchantRequestService;

	@Test
	public void testFind() {

		MerchantRequest merchantRequest = new MerchantRequest();
		merchantRequest.setContent(generateXml("10000000001","bx1000001"));
		merchantRequest.setMerchantId("10000000001");
		merchantRequest.setOrderId("bc1000001");
		merchantRequest.setRequestDate(new Date());
		// merchantRequest.setRequestIp(command.getClientIp());
		// merchantRequest.setVersionNo(command.getVersion());
		merchantRequestService.saveMerchantRequestRnTx(merchantRequest);
	}

	public static String generateXml(String merchantCode, String bizNo) {

		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<PayPlatRequestParameter>");
		xml.append("<REQUEST_HEADER>");
		xml.append("<MERCHANT_CODE>" + merchantCode + "</MERCHANT_CODE>");
		xml.append("<BIZ_NO>" + bizNo + "</BIZ_NO>");
		xml.append("<CURRENCY_CODE>" + CurrencyCode.RMB.getValue()
				+ "</CURRENCY_CODE>");
		xml.append("<TOTAL_AMOUNT>" + 2000 + "</TOTAL_AMOUNT>");
		xml.append("<TOTAL_COUNT>" + 2 + "</TOTAL_COUNT>");
		xml.append("<AUDIT_FLAG>" + AuditFlag.NO.getValue() + "</AUDIT_FLAG>");
		xml.append("<FEE_TYPE>" + FeeType.PAYER.getValue() + "</FEE_TYPE>");
		xml.append("<PAY_TYPE>" + PayType.BANK.getValue() + "</PAY_TYPE>");
		xml.append("<REQUEST_TIME>" + "20111223111450" + "</REQUEST_TIME>");
		xml.append("<VERSION>" + "V1.0" + "</VERSION>");
		xml.append("<SIGNVALUE>" + "V1.0" + "</SIGNVALUE>");
		xml.append("</REQUEST_HEADER>");

		xml.append("<REQUEST_BODY>");

		for(int i=0;i<1000;i++){
			xml.append("<PAY_ITEM>");
			xml.append("<ORDER_ID>");
			xml.append(System.currentTimeMillis());
			xml.append("</ORDER_ID>");
			xml.append("<PAYEE_NAME>" + "马超湖" + "</PAYEE_NAME>");
			xml.append("<PAYEE_ACCOUNT>"+"356889110911712" + i+"</PAYEE_ACCOUNT>");
			xml.append("<AMOUNT>1000</AMOUNT>");
			xml.append("<PAYEE_MOBILE>15921167187</PAYEE_MOBILE>");
			xml.append("<NOTE>15921167187</NOTE>");
			xml.append("<REMARK>test</REMARK>");
			xml.append("<BANK_NAME>中国工商银行</BANK_NAME>");
			xml.append("<PROVINCE>上海市</PROVINCE>");
			xml.append("<CITY>上海市</CITY>");
			xml.append("<BRANCHE>中国工商银行上海市三林支行</BRANCHE>");
			xml.append("<PAYEE_TYPE>"+PayeeType.INDIVIDUAL.getValue()+"</PAYEE_TYPE>");
			xml.append("</PAY_ITEM>");
		}
		xml.append("</REQUEST_BODY>");
		xml.append("</PayPlatRequestParameter>");

		String result = ParameterXmlParserUtil.xmlFormat(xml.toString());
		return result;
	}
}
