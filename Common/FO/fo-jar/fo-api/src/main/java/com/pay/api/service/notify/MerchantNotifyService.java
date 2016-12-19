/**
 *  File: MerchantNotify.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-12-21   Sany        Create
 *
 */
package com.pay.api.service.notify;

import com.pay.fo.order.model.batchpayment.BatchPaymentOrder;


/**
 * 付款API通知商户
 */
public interface MerchantNotifyService {
	
	/**
	 * 通知商户
	 */
	public void notifyMerchant(BatchPaymentOrder order);
	
}
