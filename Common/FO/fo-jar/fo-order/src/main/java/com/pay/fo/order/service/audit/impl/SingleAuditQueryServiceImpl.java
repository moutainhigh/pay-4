/**
 * 
 */
package com.pay.fo.order.service.audit.impl;

import com.pay.fo.order.dao.audit.SingleAuditQueryDAO;
import com.pay.fo.order.model.audit.SingleAuditQueryInfo;
import com.pay.fo.order.service.audit.SingleAuditQueryService;
import com.pay.inf.dao.Page;

/**
 * @author NEW
 *
 */
public class SingleAuditQueryServiceImpl implements SingleAuditQueryService {

	private SingleAuditQueryDAO singleAuditQueryDAO;
	
	
	
	public void setSingleAuditQueryDAO(SingleAuditQueryDAO singleAuditQueryDAO) {
		this.singleAuditQueryDAO = singleAuditQueryDAO;
	}



	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.audit.SingleAuditQueryService#queryAuditList(com.pay.inf.dao.Page, com.pay.fo.order.model.audit.SingleAuditQueryInfo)
	 */
	@Override
	public Page<SingleAuditQueryInfo> queryAuditList(
			Page<SingleAuditQueryInfo> page, SingleAuditQueryInfo info) {
		return singleAuditQueryDAO.queryAuditList(page, info);
	}

}
