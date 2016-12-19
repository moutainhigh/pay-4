/**
 * 
 */
package com.pay.fo.order.service.bsp.impl;

import com.pay.fo.order.dao.bsp.AuditQueryOrderDAO;
import com.pay.fo.order.model.bsp.AuditQueryOrder;
import com.pay.fo.order.service.bsp.AuditQueryOrderService;
import com.pay.inf.dao.Page;

/**
 * @author NEW
 *
 */
public class AuditQueryOrderServiceImpl implements AuditQueryOrderService {

	
	private AuditQueryOrderDAO auditQueryOrderDAO;
	
	@Override
	public Page<AuditQueryOrder> queryFundAdjustmentAuditList(
			Page<AuditQueryOrder> page, AuditQueryOrder auditQueryOrder) {
		return auditQueryOrderDAO.queryFundAdjustmentAuditList(page, auditQueryOrder);
	}

	public void setAuditQueryOrderDAO(AuditQueryOrderDAO auditQueryOrderDAO) {
		this.auditQueryOrderDAO = auditQueryOrderDAO;
	}

	@Override
	public AuditQueryOrder getAuditQueryOrder(Long orderId,
			Long createMemberCode) {
		return auditQueryOrderDAO.getAuditQueryOrder(orderId, createMemberCode);
	}


}
