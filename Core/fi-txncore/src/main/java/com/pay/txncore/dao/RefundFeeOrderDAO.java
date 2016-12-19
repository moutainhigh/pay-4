package com.pay.txncore.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.RefundFeeOrder;

public interface RefundFeeOrderDAO extends BaseDAO<RefundFeeOrder> {

	/**
	 * 
	 * @param refundOrderNo
	 * @return
	 */
	RefundFeeOrder findByRefundOrderNo(Long refundOrderNo);
}
