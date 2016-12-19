/**
 * 
 */
package com.pay.fo.order.service.afterprocess.impl;

import com.pay.fo.order.dto.Order;
import com.pay.fo.order.service.OrderCallbackService;
import com.pay.fo.order.service.afterprocess.OrderAfterProcessService;
import com.pay.inf.exception.AppException;
import com.pay.pe.service.accounting.AccountingService;

/**
 * @author NEW
 *
 */
public class OrderAfterProcessServiceImpl implements OrderAfterProcessService {

	@Override
	public void process(Order order, OrderCallbackService orderCallbackService,
			AccountingService accountingService) {
		try {
			orderCallbackService.processRdTx(order, accountingService);
			
			orderCallbackService.notify(order);
		} catch (AppException e) {
			throw new RuntimeException(e);
		}

	}

}
