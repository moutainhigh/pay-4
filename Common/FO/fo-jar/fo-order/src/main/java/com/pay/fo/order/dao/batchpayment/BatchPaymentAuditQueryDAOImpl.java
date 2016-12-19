/**
 * 
 */
package com.pay.fo.order.dao.batchpayment;

import com.pay.fo.order.model.batchpayment.BatchPaymentReqBaseQueryInfo;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author NEW
 * 
 */
public class BatchPaymentAuditQueryDAOImpl extends
		BaseDAOImpl<BatchPaymentReqBaseQueryInfo> implements
		BatchPaymentAuditQueryDAO {

	@Override
	public Page<BatchPaymentReqBaseQueryInfo> queryBatchPaymentAuditList(
			Page<BatchPaymentReqBaseQueryInfo> page,
			BatchPaymentReqBaseQueryInfo info) {
		return super.findByQuery("queryBatchPaymentAuditList", page, info);
	}

	@Override
	public BatchPaymentReqBaseQueryInfo getAuditQueryInfo(Long requestSeq,
			Long payerMemberCode) {
		BatchPaymentReqBaseQueryInfo info = new BatchPaymentReqBaseQueryInfo();
		info.setPayerMemberCode(payerMemberCode);
		info.setRequestSeq(requestSeq);
		return super.findObjectByCriteria("queryBatchPaymentAuditInfo", info);
	}

}
