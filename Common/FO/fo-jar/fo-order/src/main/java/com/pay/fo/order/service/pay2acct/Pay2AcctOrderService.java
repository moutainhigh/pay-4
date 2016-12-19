/**
 * 
 */
package com.pay.fo.order.service.pay2acct;

import com.pay.fo.order.dto.audit.WorkOrderDto;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;


/**
 * @author NEW
 *
 */
public interface Pay2AcctOrderService {
	
	/**
	 * 验证订单信息
	 * @param order
	 * @return
	 */
	String validate(PayToAcctOrderDTO order);
	
	/**
	 * 创建付款到账户订单
	 * @param order 需要保存的订单信息
	 */
	void createOrder(PayToAcctOrderDTO order);
	/**
	 * 更新付款到账户订单状态
	 * @param order 需要更新的订单信息
	 * @param oldStatus 当前订单状态
	 * @return
	 */
	boolean updateOrderStatus(PayToAcctOrderDTO order,int oldStatus);
	
	/**
	 * 创建付款到账户订单（需要复核功能），暂不扣款
	 * @param order 需要保存的订单信息
	 * @param workOrder 需要保存的工单信息
	 * @return
	 */
	void createOrderRdTx(PayToAcctOrderDTO order,WorkOrderDto workOrder);
	
	/**
	 * 复核通过时,支付付款到账户订单
	 * @param order 需要更新的订单信息
	 * @param workOrder 需要更新的工单信息
	 * @return
	 */
	void auditPass(WorkOrderDto workOrder);
	
	/**
	 * 复核拒绝时,取消付款到账户订单
	 * @param order 需要更新的订单信息
	 * @param workOrder 需要更新的工单信息
	 * @return
	 */
	void auditReject(WorkOrderDto workOrder);
}
