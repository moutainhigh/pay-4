/**
 * 
 */
package com.pay.fundout.bsp.service;

import com.pay.fundout.withdraw.dto.workorder.WorkOrderDto;
import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;

/**
 * @author NEW
 *
 */
public interface FundAdjustmentService {
	
	/**
	 * 创建资金调拨订单
	 * @param order
	 * @return
	 */
	void createOrder(Pay2AcctOrder order,WorkOrderDto workorder);
	
	/**
	 * 审核资金调拨订单
	 * @param order
	 * @param requestFrom
	 */
	void auditOrder(Pay2AcctOrder order,String requestFrom,WorkOrderDto workorder);

}
