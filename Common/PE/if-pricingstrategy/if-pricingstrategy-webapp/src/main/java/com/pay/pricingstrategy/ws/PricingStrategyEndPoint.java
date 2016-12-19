package com.pay.pricingstrategy.ws;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jibx.runtime.JiBXException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ws.server.endpoint.AbstractDom4jPayloadEndpoint;

import com.pay.pricingstrategy.service.CalPricingStrategyParam;
import com.pay.pricingstrategy.service.PricingStrategyService;
import com.pay.pricingstrategy.util.JiBXUtil;
import com.pay.util.JSonUtil;

public class PricingStrategyEndPoint extends AbstractDom4jPayloadEndpoint {

	private static String format = "yyyy-MM-dd HH:mm:ss";

	private Log log = LogFactory.getLog(PricingStrategyEndPoint.class);

	private PricingStrategyService pricingStrategyService;

	/**
	 * @param pricingStrategyService
	 *            the pricingStrategyService to set
	 */
	public void setPricingStrategyService(
			PricingStrategyService pricingStrategyService) {
		this.pricingStrategyService = pricingStrategyService;
	}

	@Override
	protected Element invokeInternal(Element requestElement,
			Document responseDocument) throws Exception {
		if (log.isDebugEnabled())
			log.debug(requestElement.asXML());
		//PricingStrategyRequest request = JiBXUtil.xml2Object(PricingStrategyRequest.class, requestElement.asXML());

//		log.info("request = " + JSonUtil.toJSonString(request));
		//PricingStrategyResponse response = new PricingStrategyResponse();
		//ResponseHeader responseHeader = new ResponseHeader();
//		responseHeader.setAppId(request.getRequestHeader().getAppId());
//		responseHeader.setRequestId(request.getRequestHeader().getRequestId());
//		responseHeader.setService(request.getRequestHeader().getService());
//		responseHeader.setVersion(request.getRequestHeader().getVersion());
//		response.setResponseHeader(responseHeader);
//		ResponseBody responseBody = new ResponseBody();

		try {
//			responseBody = invokePricing(request);
		} catch (Throwable e) {
			e.printStackTrace();
//			responseBody.setResult("");
//			responseBody.setResultCode(false);
//			responseBody.setErrorCode(e.getMessage());
//			responseBody.setErrorMsg(getStackTrace(e));
		}
//		response.setResponseBody(responseBody);
//		log.info("response = " + JSonUtil.toJSonString(response));
		SAXReader reader = new SAXReader();
//		responseDocument = reader.read(new ByteArrayInputStream(JiBXUtil.object2Xml(response).getBytes("UTF-8")));
		if (log.isDebugEnabled())
			log.debug(responseDocument.getRootElement().asXML());
		return responseDocument.getRootElement();
	}

//	private ResponseBody invokePricing(PricingStrategyRequest request) throws JiBXException, ParseException {
//		CalPricingStrategyParam calParam = new CalPricingStrategyParam();
//		calParam.setMemberCode(request.getRequestBody().getMemberCode());
//		Date date = format(request.getRequestBody().getDate());
//
//		calParam.setMfDatetime(date);
//
//		calParam.setPaymentServiceCode(processLong(request.getRequestBody()
//				.getPaymentServiceCode()));
//		calParam.setReservedCode(request.getRequestBody().getReservedCode());
//		calParam.setServiceLevelCode(processInteger(request.getRequestBody()
//				.getServiceLevelCode()));
//		calParam.setTerminaltype((request.getRequestBody()
//				.getTerminaltype()));
//		calParam.setTransactionAmount(processLong(request.getRequestBody()
//				.getTransactionAmount()));
//
//		long fee = pricingStrategyService.calculatePrice(calParam);
//		ResponseBody responseBody = new ResponseBody();
//		responseBody.setResult(String.valueOf(fee));
//		responseBody.setResultCode(true);
//
//		return responseBody;
//	}

	private Date format(String str) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = df.parse(str);
		return date;
	}

	private Long processLong(long value) {
		if (value == 0)
			return null;
		return Long.valueOf(value);
	}

	private Integer processInteger(int value) {
		if (value == 0)
			return null;
		return Integer.valueOf(value);
	}

	private static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}
}
