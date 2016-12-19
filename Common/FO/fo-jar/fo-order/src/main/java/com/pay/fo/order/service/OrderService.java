/**
 * 
 */
package com.pay.fo.order.service;

import com.pay.fo.order.dto.Order;

/**
 * @author NEW
 *
 */
public interface OrderService {
	/**
	 * 创建订单
	 * @param order
	 * @return
	 */
	Long createOrder(Order order);
	
	/**
	 * 更新订单信息
	 * @param upOrder 用作更新的对像
	 * @return
	 */
	boolean updateOrder(Order upOrder);
	
	/**
	 * 更新订单状态
	 * @param orderId 订单号
	 * @param oldStatus 原订单状态
	 * @return
	 */
	boolean updateOrderStatus(Order upOrder,int oldStatus);
	
	
	/**
	 * 获取订单信息
	 * @param orderId
	 * @return
	 */
	Order getOrder(Long orderId);
	
}
