/**
 *  File: MerchantResponseService.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 15, 2011   ch-ma     Create
 *
 */
package com.pay.api.service;

import com.pay.api.model.MerchantResponse;

/**
 * 
 */
public interface MerchantResponseService {

	/**
	 * 
	 * @param merchantResponse
	 * @return
	 */
	Long saveMerchantResponseRnTx(MerchantResponse merchantResponse);
}
