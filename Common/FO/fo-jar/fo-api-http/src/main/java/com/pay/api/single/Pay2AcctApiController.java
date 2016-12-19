/**
 *  File: Pay2AcctApiController.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 15, 2011   ch-ma     Create
 *
 */
package com.pay.api.single;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pay.util.Base64Util;

/**
 * 
 */
public class Pay2AcctApiController extends AbstractController {

	private Log logger = LogFactory.getLog(Pay2AcctApiController.class);
	//private PaymentApiService paymentService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String requestParameters = request.getParameter("requestParameters");

		if (logger.isInfoEnabled()) {
			logger.info("submit with payment acct info:" + requestParameters);
		}

		String xml = new String(Base64Util.decode(requestParameters.getBytes()));

		if (logger.isInfoEnabled()) {
			logger.info("submit with payment acct xml:" + xml);
		}
		//String result = paymentService.process(xml);

		if (logger.isInfoEnabled()) {
//			logger.info("with payment result xml:" + result);
		}

//		response.getWriter().print(new String(Base64Util.encode(result.getBytes())));

		return null;
	}

//	public void setPaymentService(PaymentApiService paymentService) {
//		this.paymentService = paymentService;
//	}

}
