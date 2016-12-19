/**
 * 
 */
package com.pay.fo.order.service.batchpay2bank;

import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.service.fundoutprocess.FundoutProcessService;

/**
 * @author NEW
 *
 */
public interface BatchPay2BankOrderService extends FundoutProcessService{
	
	/**
	 * 创建批量付款到银行订单
	 * @param order 需要保存的订单信息
	 * @return
	 */
	void createOrder(BatchPaymentOrderDTO order);
	/**
	 * 更新批量付款到银行订单状态
	 * @param order 需要更新的订单信息
	 * @param oldStatus 当前订单状态
	 * @return
	 */
	boolean updateOrderStatus(BatchPaymentOrderDTO order,int oldStatus);
	
	
}
