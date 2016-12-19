package com.pay.txncore.crosspay.dao.impl;

import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.PartnerClearCycleDAO;

public class PartnerClearCycleDAOImpl extends BaseDAOImpl implements
		PartnerClearCycleDAO {

	@Override
	public boolean updatePartnerWithDraw(Map<String, Object> paramMap) {
		return getSqlMapClientTemplate().update(
				namespace.concat("updateWithdrawableAmount"), paramMap) == 1;
	}
}
