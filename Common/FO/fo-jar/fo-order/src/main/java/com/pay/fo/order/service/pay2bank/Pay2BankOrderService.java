/**
 * 
 */
package com.pay.fo.order.service.pay2bank;

import com.pay.fo.order.dto.audit.WorkOrderDto;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.fundoutprocess.FundoutProcessService;

/**
 * @author NEW
 *
 */
public interface Pay2BankOrderService extends FundoutProcessService {
	
	/**
	 * 验证订单信息
	 * @param order
	 * @return
	 */
	String validate(FundoutOrderDTO order);
	
	/**
	 * 创建付款到银行订单，并将申请金额扣到中间账户
	 * @param order 需要保存的订单信息
	 * @return
	 */
	void createOrder(FundoutOrderDTO order);
	
	/**
	 * 创建付款到银行订单（需要复核功能），暂不扣款
	 * @param order 需要保存的订单信息
	 * @param workOrder 需要保存的工单信息
	 * @return
	 */
	void createOrderRdTx(FundoutOrderDTO order,WorkOrderDto workOrder);
	
	/**
	 * 复核通过时,支付付款到银行订单
	 * @param order 需要更新的订单信息
	 * @param workOrder 需要更新的工单信息
	 * @return
	 */
	void auditPass(WorkOrderDto workOrder);
	
	
	/**
	 * 复核拒绝时,取消付款到银行订单
	 * @param order 需要更新的订单信息
	 * @param workOrder 需要更新的工单信息
	 * @return
	 */
	void auditReject(WorkOrderDto workOrder);
	
	/**
	 * 更新付款到银行订单状态
	 * @param order 需要更新的订单信息
	 * @param oldStatus 当前订单状态
	 * @return
	 */
	boolean updateOrderStatus(FundoutOrderDTO order,int oldStatus);
	
	

}
