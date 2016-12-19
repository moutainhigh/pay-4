package com.pay.txncore.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.RefundOrderExtendDAO;
import com.pay.txncore.model.RefundOrderExtend;

public class RefundOrderExtendDAOImpl extends BaseDAOImpl<RefundOrderExtend> implements
RefundOrderExtendDAO {

@Override
public RefundOrderExtend findByRefundOrderNo(Long refundOrderNo) {
return (RefundOrderExtend) this.getSqlMapClientTemplate().queryForObject(
		"refundOrderExtend.findByRefundOrderNo", refundOrderNo);
}

@Override
public boolean updateReturnCount(Long refundExtendNo,Long refundOrderNo) {
	Map<String, String> queryParams = new HashMap<String, String>();
	queryParams.put("refundExtendNo", refundExtendNo.toString());
	queryParams.put("refundOrderNo", refundOrderNo.toString());
	return getSqlMapClientTemplate().update("refundOrderExtend.addRefundOrderExtendCount",
			queryParams)==1 ;
	}
}

