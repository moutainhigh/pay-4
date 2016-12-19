/**
 * 
 */
package com.pay.fo.order.service.batchpayment.impl;

import com.pay.fo.order.dao.batchpayment.BatchPaymentAuditQueryDAO;
import com.pay.fo.order.model.batchpayment.BatchPaymentReqBaseQueryInfo;
import com.pay.fo.order.service.batchpayment.BatchPaymentAuditQueryService;
import com.pay.inf.dao.Page;

/**
 * @author NEW
 *
 */
public class BatchPaymentAuditQueryServiceImpl implements
		BatchPaymentAuditQueryService {
	
	private BatchPaymentAuditQueryDAO batchPaymentAuditQueryDAO;

	@Override
	public Page<BatchPaymentReqBaseQueryInfo> queryBatchPaymentAuditList(
			Page<BatchPaymentReqBaseQueryInfo> page,
			BatchPaymentReqBaseQueryInfo auditQueryInfo) {
		return batchPaymentAuditQueryDAO.queryBatchPaymentAuditList(page, auditQueryInfo);
	}

	@Override
	public BatchPaymentReqBaseQueryInfo getAuditQueryInfo(Long requestSeq,
			Long payerMemberCode) {
		return batchPaymentAuditQueryDAO.getAuditQueryInfo(requestSeq, payerMemberCode);
	}

	/**
	 * @param batchPaymentAuditQueryDAO the batchPaymentAuditQueryDAO to set
	 */
	public void setBatchPaymentAuditQueryDAO(
			BatchPaymentAuditQueryDAO batchPaymentAuditQueryDAO) {
		this.batchPaymentAuditQueryDAO = batchPaymentAuditQueryDAO;
	}
	

}
