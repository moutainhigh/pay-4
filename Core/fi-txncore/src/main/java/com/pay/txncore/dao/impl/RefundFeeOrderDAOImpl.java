package com.pay.txncore.dao.impl;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.RefundFeeOrderDAO;
import com.pay.txncore.model.RefundFeeOrder;

public class RefundFeeOrderDAOImpl extends BaseDAOImpl<RefundFeeOrder> implements RefundFeeOrderDAO{
	
	@Override
	public RefundFeeOrder findByRefundOrderNo(Long refundOrderNo){
	return (RefundFeeOrder) this.getSqlMapClientTemplate().queryForObject(
			"refundFeeOrder.findByRefundOrderNo", refundOrderNo);
	}
}
