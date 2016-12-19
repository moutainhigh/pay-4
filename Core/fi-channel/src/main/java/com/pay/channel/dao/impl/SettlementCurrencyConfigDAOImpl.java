package com.pay.channel.dao.impl;

import java.util.List;

import com.pay.channel.dao.SettlementCurrencyConfigDAO;
import com.pay.channel.model.SettlementCurrencyConfig;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class SettlementCurrencyConfigDAOImpl extends BaseDAOImpl<SettlementCurrencyConfig> implements
		SettlementCurrencyConfigDAO {

	@Override
	public List<SettlementCurrencyConfig> queryListScc(
			SettlementCurrencyConfig scc, Page page) {
		return super.findByCriteria(scc, page);
	}

	@Override
	public List<SettlementCurrencyConfig> queryListScc(
			SettlementCurrencyConfig scc) {
		return super.findByCriteria(scc);
	}

	@Override
	public void addSettlementCurrencyConfig(SettlementCurrencyConfig scc) {
		super.create(scc);
	}

	@Override
	public void updateSettlementCurrencyConfig(SettlementCurrencyConfig scc) {
		super.update(scc);
	}

	@Override
	public void deleteSettlementCurrencyConfig(SettlementCurrencyConfig scc) {
		super.delete(scc);
	}

	@Override
	public List<SettlementCurrencyConfig> queryIsOnly(
			SettlementCurrencyConfig scc) {
		return super.findByCriteria("findIsOnly", scc);
	}

}
