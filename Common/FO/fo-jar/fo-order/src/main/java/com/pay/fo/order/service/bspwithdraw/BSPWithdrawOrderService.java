/**
 * 
 */
package com.pay.fo.order.service.bspwithdraw;

import com.pay.fo.order.dto.audit.WorkOrderDto;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.fundoutprocess.FundoutProcessService;

/**
 * @author NEW
 *
 */
public interface BSPWithdrawOrderService extends FundoutProcessService{
	/**
	 * 创建出金订单
	 * @param order 需要保存的订单信息
	 * @return
	 */
	void createOrder(FundoutOrderDTO order,WorkOrderDto workorder);
	
	/**
	 * 更新出金订单状态
	 * @param order 需要更新的订单信息
	 * @param oldStatus 当前订单状态
	 * @return
	 */
	boolean updateOrderStatus(FundoutOrderDTO order,int oldStatus);
	
	/**
	 * 复核通过
	 * @param workorder
	 */
	void auditPass(WorkOrderDto workorder);
	
	/**
	 * 复核拒绝
	 * @param order
	 * @param workorder
	 */
	void auditReject(WorkOrderDto workorder);
	
}
