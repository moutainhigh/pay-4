package com.pay.txncore.service.impl;

import java.util.List;
import java.util.Map;

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

	@Override
	public TradeData queryTradeDate(Map<String, Object> params) {
		List<TradeData> list = tradeDataDAO.findByCriteria(params);
		if(list!=null&&!list.isEmpty())
		      return list.get(0);
		return null;
	}
}
