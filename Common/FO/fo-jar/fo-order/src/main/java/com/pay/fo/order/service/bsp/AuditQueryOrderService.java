/**
 * 
 */
package com.pay.fo.order.service.bsp;

import com.pay.fo.order.model.bsp.AuditQueryOrder;
import com.pay.inf.dao.Page;

/**
 * @author NEW
 *
 */
public interface AuditQueryOrderService {
	/**
	 * 查询资金调拨审核列表
	 * @param page
	 * @param auditQueryOrder
	 */
	public Page<AuditQueryOrder> queryFundAdjustmentAuditList(Page<AuditQueryOrder> page ,AuditQueryOrder auditQueryOrder);
	/**
	 * 获取审核订单详情
	 * @param orderId
	 * @param createMemberCode
	 * @return
	 */
	public AuditQueryOrder getAuditQueryOrder(Long orderId,Long createMemberCode);

}
