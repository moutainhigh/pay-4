package com.pay.ma.chargeup.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.ma.chargeup.dao.BalanceEntryDAO;
import com.pay.ma.chargeup.model.BalanceEntry;

public class BalanceEntryDAOImpl extends BaseDAOImpl<BalanceEntry> implements
		BalanceEntryDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.ma.chargeup.dao.BalanceEntryDAO#
	 * queryBalanceEntryInfoWithSerialNo(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BalanceEntry> queryBalanceEntryInfoWithSerialNo(Map paramMap) {
		List<BalanceEntry> balanceEntries = this.getSqlMapClientTemplate()
				.queryForList(
						this.namespace.concat("queryBalanceEntryWithSerialNo"),
						paramMap);
		if (balanceEntries == null) {
			return new ArrayList<BalanceEntry>(0);
		}
		return balanceEntries;
	}

}
