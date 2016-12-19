package com.pay.txncore.crosspay.dao.impl;

import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.PartnerWithdrawOrderDAO;
import com.pay.txncore.crosspay.model.PartnerWithdrawOrder;

public class PartnerWithdrawOrderDAOImpl extends
		BaseDAOImpl<PartnerWithdrawOrder> implements PartnerWithdrawOrderDAO {

	@Override
	public boolean updatePartnerWithDraw(String sql,
			Map<String, Object> paramMap) {
		if (getSqlMapClientTemplate().update(sql, paramMap) == 1) {
			return true;
		} else {
			return false;
		}
	}
}
