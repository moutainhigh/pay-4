package com.pay.txncore.dao.impl;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.TradeDataDAO;
import com.pay.txncore.model.TradeData;

public class TradeDataDAOImpl extends BaseDAOImpl<TradeData> 
                             implements TradeDataDAO {
	@Override
	public void createTradeData(TradeData tradeData) {
		super.create(tradeData);
	}
}
