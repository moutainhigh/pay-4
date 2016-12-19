package com.pay.txncore.dao;

import java.util.HashMap;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.RefundOrderExtend;

public interface RefundOrderExtendDAO extends BaseDAO<RefundOrderExtend> {

	/**
	 * 
	 * @param refundOrderNo
	 * @return
	 */
	RefundOrderExtend findByRefundOrderNo(Long refundOrderNo);

	public boolean updateReturnCount(Long refundExtendNo,Long refundOrderNo);
}
