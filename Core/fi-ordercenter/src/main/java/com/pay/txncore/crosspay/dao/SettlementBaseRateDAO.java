package com.pay.txncore.crosspay.dao;

import java.util.Date;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.SettlementBaseRate;

public interface SettlementBaseRateDAO extends BaseDAO<SettlementBaseRate> {

	/**
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	SettlementBaseRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, String status, Date currentDate);
}