package com.pay.txncore.crosspay.dao.impl;

import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.FrozenOrderDAO;
import com.pay.txncore.crosspay.model.FrozenOrder;

public class FrozenOrderDAOImpl extends BaseDAOImpl<FrozenOrder> implements
		FrozenOrderDAO {

	@Override
	public boolean updateFrozenOrder(String sql, Map<String, Object> conMap) {
		if (getSqlMapClientTemplate().update(sql, conMap) == 1) {
			return true;
		} else
			return false;
	}

	@Override
	public void createFrozenOrder(String sql, Map<String, Object> conMap) {
		getSqlMapClientTemplate().insert(sql, conMap);
	}
}
