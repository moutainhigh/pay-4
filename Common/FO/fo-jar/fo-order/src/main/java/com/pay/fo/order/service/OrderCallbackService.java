/**
 * 
 */
package com.pay.fo.order.service;

import com.pay.fo.order.dto.Order;
import com.pay.inf.exception.AppException;
import com.pay.pe.service.accounting.AccountingService;

/**
 * @author NEW
 * 
 */
public interface OrderCallbackService {

	/**
	 * 处理订单 更新余额、更新订单状态
	 * 
	 * @param order
	 * @param accountingService
	 * @throws AppException
	 */
	void processRdTx(Order order, AccountingService accountingService)
			throws AppException;

	/**
	 * 更新订单状态
	 * 
	 * @param order
	 * @return
	 * @throws AppException
	 */
	boolean updateOrderStatus(Order order) throws AppException;

	/**
	 * 通知
	 * 
	 * @param order
	 */
	void notify(Order order);

}
