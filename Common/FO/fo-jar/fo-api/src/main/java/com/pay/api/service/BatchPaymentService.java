/**
 *  File: BatchPaymentService.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 22, 2011   ch-ma     Create
 *
 */
package com.pay.api.service;

import com.pay.api.dto.BatchPaymentRequest;
import com.pay.api.dto.BatchPaymentResult;

/**
 * 
 */
public interface BatchPaymentService {

	/**
	 * 
	 * @param request
	 * @return
	 */
	BatchPaymentResult payment(BatchPaymentRequest request) throws Exception;
}
