/**
 * 
 */
package com.pay.fo.order.service.base;

import com.pay.inf.exception.AppException;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;

/**
 * @author NEW
 *
 */
public interface PayToAcctOrderProcessService {
	
	/**
	 * 创建付款到银行订单
	 * @param order 需要保存的订单信息
	 * @return
	 * @throws AppException 
	 */
	Long createOrderRnTx(PayToAcctOrderDTO order) throws AppException;
	/**
	 * 更新付款到银行订单状态
	 * @param order 需要更新的订单信息
	 * @param oldStatus 当前订单状态
	 * @return
	 * @throws AppException 
	 */
	boolean updateOrderStatusRdTx(PayToAcctOrderDTO order,int oldStatus) throws AppException;
	
	/**
	 * 根据订单号获取订单信息
	 * @param orderId
	 * @return
	 */
	PayToAcctOrderDTO getOrder(Long orderId);

}
