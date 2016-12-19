package com.pay.txncore.dao.impl;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.RefundOrderStatusChangeLogDAO;
import com.pay.txncore.model.RefundOrderStatusChangeLog;



public class RefundOrderStatusChangeLogDAOImpl extends
		BaseDAOImpl<RefundOrderStatusChangeLog> implements
		RefundOrderStatusChangeLogDAO {

	@Override
	public RefundOrderStatusChangeLog findByRefundOrderNo(Long refundOrderNo) {
		return (RefundOrderStatusChangeLog) this.getSqlMapClientTemplate()
				.queryForObject(
						"RefundOrderStatusChangeLog.findByRefundOrderNo",
						refundOrderNo);
	}

}
