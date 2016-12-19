package com.pay.txncore.service;

import com.pay.txncore.model.TradeAmountCount;

public interface TradeAmountCountService {
	
	Long save(TradeAmountCount tradeAmountCount);
	
	boolean update(TradeAmountCount tradeAmountCount);
	
	TradeAmountCount query(TradeAmountCount tradeAmountCount);
	
	void saveOrUpdate(TradeAmountCount tradeAmountCount);
}
