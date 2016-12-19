/**
 * 
 */
package com.pay.fo.order.service.batchpayment;

import com.pay.inf.exception.AppException;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;

/**
 * @author NEW
 *
 */
public interface BatchPaymentOrderProcessService {
	/**
	 * 创建付款到银行订单
	 * @param order 需要保存的订单信息
	 * @return
	 * @throws AppException 
	 */
	Long createOrderRnTx(BatchPaymentOrderDTO order) throws AppException;
	/**
	 * 更新付款到银行订单状态
	 * @param order 需要更新的订单信息
	 * @param oldStatus 当前订单状态
	 * @return
	 * @throws AppException 
	 */
	boolean updateOrderStatusRdTx(BatchPaymentOrderDTO order,int oldStatus) throws AppException;
	
	/**
	 * 根据订单号获取订单信息
	 * @param orderId
	 * @return
	 */
	BatchPaymentOrderDTO getOrder(Long orderId);
	/**
	 * 根据会员号、订单类型、业务批次号获取订单信息
	 * @param orderId
	 * @return
	 */
	BatchPaymentOrderDTO getOrder(Long memberCode, Integer orderType,
			String businessBatchNo);
}
