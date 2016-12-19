/**
 *  File: MessageHandleServiceTest.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 23, 2011   ch-ma     Create
 *
 */
package com.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.pay.api.helper.AuditFlag;
import com.pay.api.helper.CurrencyCode;
import com.pay.api.helper.FeeType;
import com.pay.api.helper.PayType;
import com.pay.api.helper.PayeeType;
import com.pay.api.service.IMessageHandleService;
import com.pay.api.service.ISecurityProvider;
import com.pay.api.util.FreeMarkerUtil;
import com.pay.api.util.ParameterXmlParserUtil;

/**
 * 
 */
public class MessageHandleServiceTest extends AbstractTestNG {

	@Resource(name = "api-httpMessageHandle")
	private IMessageHandleService messageHandleService;
	@Resource(name = "api-paymentQueryMessageHandle")
	private IMessageHandleService queryMessageHandleService;

	@Resource(name = "api-securityProvider")
	private ISecurityProvider securityProvider;

	@Test
	public void testBatchPayment() {

		String merchantCode = "10000002328";
		String bizNo = "BZ" + System.currentTimeMillis();

		//String req = "<?xml version=\"1.0\" encoding=\"utf-8\"?><PayPlatRequestParameter><REQUEST_HEADER><MERCHANT_CODE>10000002328</MERCHANT_CODE><BIZ_NO>"+bizNo+"</BIZ_NO><CURRENCY_CODE>1</CURRENCY_CODE><TOTAL_AMOUNT>102</TOTAL_AMOUNT><TOTAL_COUNT>2</TOTAL_COUNT><AUDIT_FLAG>1</AUDIT_FLAG><FEE_TYPE>0</FEE_TYPE><PAY_TYPE>1</PAY_TYPE><REQUEST_TIME>20120222150327</REQUEST_TIME><VERSION>V1.0</VERSION><SIGNVALUE>0E522065CEACD7FF8D832A86ED93D531</SIGNVALUE></REQUEST_HEADER><REQUEST_BODY><PAY_ITEM><ORDER_ID>00000000000000000209</ORDER_ID><PAYEE_NAME>测试</PAYEE_NAME><PAYEE_ACCOUNT>12345678</PAYEE_ACCOUNT><AMOUNT>2</AMOUNT><PAYEE_MOBILE></PAYEE_MOBILE><NOTE>付款</NOTE><REMARK></REMARK><BANK_NAME>中国银行</BANK_NAME><PROVINCE>山西省</PROVINCE><CITY>长治市</CITY><BRANCHE>中国银行英雄路办事处</BRANCHE><PAYEE_TYPE>1</PAYEE_TYPE></PAY_ITEM><PAY_ITEM><ORDER_ID>00000000000000000218</ORDER_ID><PAYEE_NAME>测试</PAYEE_NAME><PAYEE_ACCOUNT>1234567890</PAYEE_ACCOUNT><AMOUNT>100</AMOUNT><PAYEE_MOBILE></PAYEE_MOBILE><NOTE>付款</NOTE><REMARK></REMARK><BANK_NAME>汉口银行</BANK_NAME><PROVINCE>湖北省</PROVINCE><CITY>鄂州市</CITY><BRANCHE>汉口银行鄂州分行营业部</BRANCHE><PAYEE_TYPE>1</PAYEE_TYPE></PAY_ITEM></REQUEST_BODY></PayPlatRequestParameter>";
		String req="<?xml version=\"1.0\" encoding=\"utf-8\"?><PayPlatRequestParameter><REQUEST_HEADER><MERCHANT_CODE>10000002328</MERCHANT_CODE><BIZ_NO>"+bizNo+"</BIZ_NO><CURRENCY_CODE>1</CURRENCY_CODE><TOTAL_AMOUNT>1</TOTAL_AMOUNT><TOTAL_COUNT>1</TOTAL_COUNT><AUDIT_FLAG>1</AUDIT_FLAG><FEE_TYPE>1</FEE_TYPE><PAY_TYPE>1</PAY_TYPE><REQUEST_TIME>20120222150259</REQUEST_TIME><VERSION>V1.0</VERSION><SIGNVALUE>8F894B631D2154C0F7777E1CC03B1457</SIGNVALUE></REQUEST_HEADER><REQUEST_BODY><PAY_ITEM><ORDER_ID>00000000000000000251</ORDER_ID><PAYEE_NAME>11</PAYEE_NAME><PAYEE_ACCOUNT>00000000</PAYEE_ACCOUNT><AMOUNT>1</AMOUNT><PAYEE_MOBILE></PAYEE_MOBILE><NOTE>付款</NOTE><REMARK></REMARK><BANK_NAME>中国工商银行</BANK_NAME><PROVINCE>陕西省</PROVINCE><CITY>安康市</CITY><BRANCHE>中国工商银行安康岚皋县支行</BRANCHE><PAYEE_TYPE>1</PAYEE_TYPE></PAY_ITEM></REQUEST_BODY></PayPlatRequestParameter>";
		String result = messageHandleService.process(merchantCode, bizNo,
				req);
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&------"+bizNo);
		System.out.print(result);
	}

	//@Test
	public void testBatchQuery() {

		String merchantCode = "10000000001";
		String bizNo = "BZ1324868464140";
		String queryType = "2";
		String orderId = "";
		String queryDate = "";
		
		StringBuilder sb = new StringBuilder();
		sb.append(merchantCode);
		sb.append(queryType);
		sb.append(orderId);
		sb.append(bizNo);
		sb.append(queryDate);

		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<PayPlatRequestParameter>");
		xml.append("<REQUEST_HEADER>");
		xml.append("<MERCHANT_CODE>" + merchantCode + "</MERCHANT_CODE>");
		xml.append("<QUERY_TYPE>" + queryType + "</QUERY_TYPE>");
		xml.append("<ORDER_ID>" + orderId + "</ORDER_ID>");
		xml.append("<BIZ_NO>" + bizNo + "</BIZ_NO>");
		xml.append("<QUERY_DATE></QUERY_DATE>");

		xml.append("<SIGNVALUE>" + securityProvider.generateSignature(sb.toString()) + "</SIGNVALUE>");
		xml.append("</REQUEST_HEADER>");
		xml.append("</PayPlatRequestParameter>");

		String result = ParameterXmlParserUtil.xmlFormat(xml.toString());
		
		String req = "<?xml version=\"1.0\" encoding=\"utf-8\"?><PayPlatRequestParameter><REQUEST_HEADER><MERCHANT_CODE>10000002328</MERCHANT_CODE><BIZ_NO>20120222150327724</BIZ_NO><CURRENCY_CODE>1</CURRENCY_CODE><TOTAL_AMOUNT>102</TOTAL_AMOUNT><TOTAL_COUNT>2</TOTAL_COUNT><AUDIT_FLAG>1</AUDIT_FLAG><FEE_TYPE>0</FEE_TYPE><PAY_TYPE>1</PAY_TYPE><REQUEST_TIME>20120222150327</REQUEST_TIME><VERSION>V1.0</VERSION><SIGNVALUE>0E522065CEACD7FF8D832A86ED93D531</SIGNVALUE></REQUEST_HEADER><REQUEST_BODY><PAY_ITEM><ORDER_ID>00000000000000000209</ORDER_ID><PAYEE_NAME>测试</PAYEE_NAME><PAYEE_ACCOUNT>12345678</PAYEE_ACCOUNT><AMOUNT>2</AMOUNT><PAYEE_MOBILE></PAYEE_MOBILE><NOTE>付款</NOTE><REMARK></REMARK><BANK_NAME>中国银行</BANK_NAME><PROVINCE>山西省</PROVINCE><CITY>长治市</CITY><BRANCHE>中国银行英雄路办事处</BRANCHE><PAYEE_TYPE>1</PAYEE_TYPE></PAY_ITEM><PAY_ITEM><ORDER_ID>00000000000000000218</ORDER_ID><PAYEE_NAME>测试</PAYEE_NAME><PAYEE_ACCOUNT>1234567890</PAYEE_ACCOUNT><AMOUNT>100</AMOUNT><PAYEE_MOBILE></PAYEE_MOBILE><NOTE>付款</NOTE><REMARK></REMARK><BANK_NAME>汉口银行</BANK_NAME><PROVINCE>湖北省</PROVINCE><CITY>鄂州市</CITY><BRANCHE>汉口银行鄂州分行营业部</BRANCHE><PAYEE_TYPE>1</PAYEE_TYPE></PAY_ITEM></REQUEST_BODY></PayPlatRequestParameter>";

		result = queryMessageHandleService.process("10000002328", "20120222150327724"+1,
				req);
		System.out.print(result);
	}

	public String generateXml(String merchantCode, String bizNo) {

		int j = 5;
		int amount = 1000 * j;
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<PayPlatRequestParameter>");
		xml.append("<REQUEST_HEADER>");
		xml.append("<MERCHANT_CODE>" + merchantCode + "</MERCHANT_CODE>");
		xml.append("<BIZ_NO>" + bizNo + "</BIZ_NO>");
		xml.append("<CURRENCY_CODE>" + CurrencyCode.RMB.getValue()
				+ "</CURRENCY_CODE>");
		xml.append("<TOTAL_AMOUNT>" + amount + "</TOTAL_AMOUNT>");
		xml.append("<TOTAL_COUNT>" + j + "</TOTAL_COUNT>");
		xml.append("<AUDIT_FLAG>" + AuditFlag.NO.getValue() + "</AUDIT_FLAG>");
		xml.append("<FEE_TYPE>" + FeeType.PAYER.getValue() + "</FEE_TYPE>");
		xml.append("<PAY_TYPE>" + PayType.BANK.getValue() + "</PAY_TYPE>");
		xml.append("<REQUEST_TIME>" + "20111223111450" + "</REQUEST_TIME>");
		xml.append("<VERSION>" + "V1.0" + "</VERSION>");
		xml.append("<SIGNVALUE>" + "${signValue}" + "</SIGNVALUE>");
		xml.append("</REQUEST_HEADER>");

		xml.append("<REQUEST_BODY>");

		for (int i = 0; i < j; i++) {
			xml.append("<PAY_ITEM>");
			xml.append("<ORDER_ID>");
			xml.append(System.currentTimeMillis());
			xml.append("</ORDER_ID>");
			xml.append("<PAYEE_NAME>" + "马超湖" + "</PAYEE_NAME>");
			xml.append("<PAYEE_ACCOUNT>" + "356889110911712" + i
					+ "</PAYEE_ACCOUNT>");
			xml.append("<AMOUNT>1000</AMOUNT>");
			xml.append("<PAYEE_MOBILE>15921167187</PAYEE_MOBILE>");
			xml.append("<NOTE>15921167187</NOTE>");
			xml.append("<REMARK>test</REMARK>");
			xml.append("<BANK_NAME>中国工商银行</BANK_NAME>");
			xml.append("<PROVINCE>上海市</PROVINCE>");
			xml.append("<CITY>上海市</CITY>");
			xml.append("<BRANCHE>中国工商银行上海市三林支行</BRANCHE>");
			xml.append("<PAYEE_TYPE>" + PayeeType.INDIVIDUAL.getValue()
					+ "</PAYEE_TYPE>");
			xml.append("</PAY_ITEM>");
		}
		xml.append("</REQUEST_BODY>");
		xml.append("</PayPlatRequestParameter>");

		String result = ParameterXmlParserUtil.xmlFormat(xml.toString());
		String srcData = ParameterXmlParserUtil.getNodeText(result,
				"REQUEST_BODY");

		String signvalue = securityProvider.generateSignature(srcData);

		Map map = new HashMap();
		map.put("signValue", signvalue);
		String resultXml = FreeMarkerUtil.parser(xml.toString(), map);

		result = ParameterXmlParserUtil.xmlFormat(resultXml.toString());
		return result;
	}
}
