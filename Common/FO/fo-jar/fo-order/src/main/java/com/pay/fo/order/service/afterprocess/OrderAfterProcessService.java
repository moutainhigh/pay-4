/**
 * 
 */
package com.pay.fo.order.service.afterprocess;

import com.pay.fo.order.dto.Order;
import com.pay.fo.order.service.OrderCallbackService;
import com.pay.pe.service.accounting.AccountingService;

/**
 * @author NEW
 *
 */
public interface OrderAfterProcessService {
	
	/**
	 * 处理订单（更新订单状态、更新余额、发送通知）
	 * @param order
	 * @param orderCallbackService
	 * @param accountingService
	 */
	void process(Order order,OrderCallbackService orderCallbackService, AccountingService accountingService);

}
