package com.pay.txncore.dao;

import com.pay.inf.dao.BaseDAO;

import com.pay.txncore.model.RefundOrderStatusChangeLog;


public interface RefundOrderStatusChangeLogDAO extends BaseDAO<RefundOrderStatusChangeLog>{

	/**
	 * 这个函数没有用 
	 * @param 
	 * @return
	 */
	RefundOrderStatusChangeLog findByRefundOrderNo(Long refundOrderNo);
}
