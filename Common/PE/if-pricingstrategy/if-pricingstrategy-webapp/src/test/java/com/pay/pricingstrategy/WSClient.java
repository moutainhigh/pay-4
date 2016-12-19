package com.pay.pricingstrategy;

import javax.xml.transform.stream.StreamResult;

import org.springframework.ws.client.core.WebServiceTemplate;

public class WSClient {

	public static void main(String[] args) throws Exception{
		long memberCode = 10015225196L;
		long paymentServiceCode = 230L;
		String reservedCode = null;
		int serviceLevelCode = 0;
		int terminaltype = 1;
		long transactionAmount = 1500L*1000;
//		PricingStrategyRequest request = new PricingStrategyRequest();
//		RequestBody rb = new RequestBody();
//		rb.setMemberCode(memberCode);
//		rb.setDate("2009-01-01 00:00:00");
//		rb.setPaymentServiceCode(paymentServiceCode);
//		rb.setReservedCode(reservedCode);
//		rb.setServiceLevelCode(serviceLevelCode);
//		rb.setTerminaltype(terminaltype);
//		rb.setTransactionAmount(transactionAmount);
//		request.setRequestBody(rb);
//		RequestHeader header = new RequestHeader();
//		header.setAppId("test");
//		header.setRequestId("requestId");
//		header.setService("inf.pricingstrategy");
//		header.setVersion("1.0");
//		request.setRequestHeader(header);
		
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setDefaultUri("http://localhost:8080/webapp/services/");
		
//		StreamSource source = new StreamSource(new StringReader(JiBXUtil.object2Xml(request)));
        StreamResult result = new StreamResult(System.out);
//        webServiceTemplate.sendSourceAndReceiveToResult(source, result);

		
	}
}
