/**
 *  File: IMessageHandle.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 21, 2011   ch-ma     Create
 *
 */
package com.pay.api.service;


/**
 * 报文处理服务
 */
public interface IMessageHandleService {

	/**
	 * @param requestInfo
	 *            xml or filePath
	 * @param request
	 * @return
	 */
	String process(final String merchantCode, final String bizNo,
			String requestInfo);

	/**
	 * 
	 * @param result
	 * @return
	 */
	//String generateResult(PaymentResult result);
}
