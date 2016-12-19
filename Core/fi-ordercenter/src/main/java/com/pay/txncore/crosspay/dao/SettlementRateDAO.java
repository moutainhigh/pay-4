package com.pay.txncore.crosspay.dao;

import java.util.Date;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.SettlementRate;

public interface SettlementRateDAO extends BaseDAO<SettlementRate> {

	/**
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	SettlementRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, String status, String memberCode,Date currentDate);
	
	SettlementRate newFindCurrentCurrencyRate(Map<String,Object> paraMap);
}