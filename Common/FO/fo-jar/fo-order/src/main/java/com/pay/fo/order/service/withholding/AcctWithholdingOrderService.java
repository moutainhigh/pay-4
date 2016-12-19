/**
 * 
 */
package com.pay.fo.order.service.withholding;

import com.pay.fo.order.dto.base.PayToAcctOrderDTO;

/**
 * @author NEW
 *
 */
public interface AcctWithholdingOrderService {
	
	/**
	 * 创建付款到账户订单
	 * @param order 需要保存的订单信息
	 */
	void createOrder(PayToAcctOrderDTO order);
	
	/**
	 * 支付订单
	 * @param order
	 */
	void paymentOrder(PayToAcctOrderDTO order);
	/**
	 * 更新付款到账户订单状态
	 * @param order 需要更新的订单信息
	 * @param oldStatus 当前订单状态
	 * @return
	 */
	boolean updateOrderStatus(PayToAcctOrderDTO order,int oldStatus);

}
