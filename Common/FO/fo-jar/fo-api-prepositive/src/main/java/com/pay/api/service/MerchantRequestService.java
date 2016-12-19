/**
 *  File: MerchantRequestService.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 14, 2011   ch-ma     Create
 *
 */
package com.pay.api.service;

import com.pay.api.model.MerchantRequest;

/**
 * 
 */
public interface MerchantRequestService {

	/**
	 * 
	 * @param merchantRequest
	 * @return
	 */
	Long saveMerchantRequestRnTx(MerchantRequest merchantRequest);

	/**
	 * 
	 * @param merchantCode
	 * @param orderId
	 * @return
	 */
	MerchantRequest findByMerchantCodeAndOrderId(Long merchantCode,
			final String orderId);
}
