/**
 * 
 */
package com.pay.fundout.bsp.service.impl;

import com.pay.fundout.bsp.dao.AuditQueryOrderDAO;
import com.pay.fundout.bsp.model.AuditQueryOrder;
import com.pay.fundout.bsp.service.AuditQueryOrderService;
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
