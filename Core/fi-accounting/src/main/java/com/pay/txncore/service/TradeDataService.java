package com.pay.txncore.service;

import com.pay.txncore.model.TradeData;

public interface TradeDataService {
   void save(TradeData tradeData);
   boolean  update(TradeData tradeData);
}
