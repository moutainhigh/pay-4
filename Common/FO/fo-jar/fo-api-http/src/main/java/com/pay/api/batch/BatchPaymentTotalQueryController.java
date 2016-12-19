/**
 *  File: BatchPaymentController.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 20, 2011   ch-ma     Create
 *
 */
package com.pay.api.batch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.api.service.IMessageHandleService;
import com.pay.util.Base64Util;

/**
 * 
 */
public class BatchPaymentTotalQueryController extends MultiActionController {

	private Log logger = LogFactory.getLog(BatchPaymentTotalQueryController.class);
	private IMessageHandleService messageHandleService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		String commandCode = request.getParameter("commandCode");
		String requestParameters = request.getParameter("requestParameters");

		if (logger.isInfoEnabled()) {
//			logger.info("request command data:" + commandCode);
			logger.info("request base64 data:" + requestParameters);
		}

//		MessageCommand command = transferMessageCommand(request, commandCode);

		// base64 transfer
		String xml = new String(Base64Util.decode(requestParameters.getBytes()));

		String resultXml = messageHandleService.process(null, null, xml);

		response.getWriter().print(new String(Base64Util.encode(resultXml.getBytes())));

		return null;
	}

	public void setMessageHandleService(IMessageHandleService messageHandleService) {
		this.messageHandleService = messageHandleService;
	}

//	private MessageCommand transferMessageCommand(HttpServletRequest request, String serviceCode) {
//
//		MessageCommand messageCommand = new MessageCommand();
//
//		if (null != serviceCode && serviceCode.trim().length() != 0) {
//			String s[] = serviceCode.split("\\|");
//			if (null != s && s.length == 6) {
//				messageCommand.setWebsvrCode(s[0]);
//				messageCommand.setProductCode(s[1]);
//				messageCommand.setMerchantCode(s[2]);
//				messageCommand.setBizNo(s[3]);
//				messageCommand.setVersion(s[4]);
//				messageCommand.setRequestIp(s[5]);
//			}
//		}
//		messageCommand.setClientIp(request.getRemoteAddr());
//		return messageCommand;
//	}

}
