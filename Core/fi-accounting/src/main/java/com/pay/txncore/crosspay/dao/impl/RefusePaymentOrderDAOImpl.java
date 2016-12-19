package com.pay.txncore.crosspay.dao.impl;

import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.RefusePaymentOrderDAO;
import com.pay.txncore.crosspay.model.RefusePaymentOrder;

public class RefusePaymentOrderDAOImpl extends BaseDAOImpl<RefusePaymentOrder>
		implements RefusePaymentOrderDAO {

	@Override
	public boolean updateRefusePayment(String sql, Map<String, Object> conMap) {
		if (getSqlMapClientTemplate().update(sql, conMap) == 1) {
			return true;
		} else
			return false;
	}

	@Override
	public void createRefusePayment(String sql, Map<String, Object> conMap) {
		getSqlMapClientTemplate().insert(sql, conMap);
	}
}
