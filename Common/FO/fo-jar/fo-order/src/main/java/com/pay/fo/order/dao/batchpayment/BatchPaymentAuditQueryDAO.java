/**
 * 
 */
package com.pay.fo.order.dao.batchpayment;

import com.pay.fo.order.model.batchpayment.BatchPaymentReqBaseQueryInfo;
import com.pay.inf.dao.Page;

/**
 * @author NEW
 *
 */
public interface BatchPaymentAuditQueryDAO {
	/**
	 * 查询批量付款复核列表
	 * @param page
	 * @param auditQueryInfo
	 */
	public Page<BatchPaymentReqBaseQueryInfo> queryBatchPaymentAuditList(Page<BatchPaymentReqBaseQueryInfo> page ,BatchPaymentReqBaseQueryInfo auditQueryInfo);
	
	/**
	 * 获取复核请求详情
	 * @param requestSeq
	 * @param payerMemberCode
	 * @return
	 */
	public BatchPaymentReqBaseQueryInfo getAuditQueryInfo(Long requestSeq,Long payerMemberCode);
}
