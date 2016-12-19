/**
 * 
 */
package com.pay.fo.order.service.withdraw;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.fundoutprocess.FundoutProcessService;

/**
 * @author NEW
 *
 */
public interface WithdrawOrderService extends FundoutProcessService{
	/**
	 * 创建付款到银行订单
	 * @param order 需要保存的订单信息
	 * @return
	 */
	void createOrder(FundoutOrderDTO order);
	/**
	 * 更新付款到银行订单状态
	 * @param order 需要更新的订单信息
	 * @param oldStatus 当前订单状态
	 * @return
	 */
	boolean updateOrderStatus(FundoutOrderDTO order,int oldStatus);
	
}
