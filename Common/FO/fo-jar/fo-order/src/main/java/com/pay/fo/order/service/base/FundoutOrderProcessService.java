/**
 * 
 */
package com.pay.fo.order.service.base;

import com.pay.inf.exception.AppException;
import com.pay.fo.order.dto.base.FundoutOrderDTO;

/**
 * @author NEW
 *
 */
public interface FundoutOrderProcessService {
	
	/**
	 * 创建出款订单
	 * @param order 需要保存的订单信息
	 * @return
	 * @throws AppException 
	 */
	Long createOrderRnTx(FundoutOrderDTO order) throws AppException;
	/**
	 * 更新出款订单状态
	 * @param order 需要更新的订单信息
	 * @param oldStatus 当前订单状态
	 * @return
	 * @throws AppException 
	 */
	boolean updateOrderStatusRdTx(FundoutOrderDTO order,int oldStatus) throws AppException;
	
	/**
	 * 根据订单号获取订单信息
	 * @param orderId
	 * @return
	 */
	FundoutOrderDTO getOrder(Long orderId);

}
