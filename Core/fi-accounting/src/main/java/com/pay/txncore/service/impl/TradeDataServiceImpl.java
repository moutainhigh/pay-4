package com.pay.txncore.service.impl;

import com.pay.txncore.dao.TradeDataDAO;
import com.pay.txncore.model.TradeData;
import com.pay.txncore.service.TradeDataService;

public class TradeDataServiceImpl implements TradeDataService {
	private TradeDataDAO tradeDataDAO;

	@Override
	public void save(TradeData tradeData) {
	     tradeDataDAO.create(tradeData);
	}

	public void setTradeDataDAO(TradeDataDAO tradeDataDAO) {
		this.tradeDataDAO = tradeDataDAO;
	}

	@Override
	public boolean update(TradeData tradeData) {
		return tradeDataDAO.update(tradeData);
	}
}
