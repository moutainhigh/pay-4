/**
 *  File: BatchPaymentCallbackService.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 23, 2011   ch-ma     Create
 *
 */
package com.pay.api.service;

import com.pay.api.dto.BatchPaymentResult;

/**
 * 
 */
public interface BatchPaymentCallbackService {

	/**
	 * 
	 * @param result
	 */
	void notifyHandle(BatchPaymentResult result);
}
