/**
 * 
 */
package com.pay.fo.order.service.batchpay2acct;

import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;

/**
 * @author NEW
 *
 */
public interface BatchPay2AcctOrderService {
	
	/**
	 * 创建批量付款到账户订单
	 * @param order 需要保存的订单信息
	 * @return
	 */
	void createOrder(BatchPaymentOrderDTO order);
	/**
	 * 更新批量付款到账户订单状态
	 * @param order 需要更新的订单信息
	 * @param oldStatus 当前订单状态
	 * @return
	 */
	boolean updateOrderStatus(BatchPaymentOrderDTO order,int oldStatus);
	
	/**
	 * 出款处理成功
	 * @param orderId
	 */
	void foProcessSuccess(long orderId);
	
	/**
	 * 出款处理成功
	 * @param orderId
	 */
	void foProcessSuccess(PayToAcctOrderDTO dto);
	
	/**
	 * 出款处理失败
	 * @param orderId
	 */
	void foProcessFail(long orderId);
	
	/**
	 * 处理完成的批量付款到账户的订单信息
	 */
	void processCompleteBatchPay2AcctOrder();
}
