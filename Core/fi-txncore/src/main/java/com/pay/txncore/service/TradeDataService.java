package com.pay.txncore.service;

import java.util.Map;

import com.pay.txncore.model.TradeData;

public interface TradeDataService {
   void save(TradeData tradeData);
   boolean  update(TradeData tradeData);
   
   TradeData queryTradeDate(Map<String,Object> params);
   
}
