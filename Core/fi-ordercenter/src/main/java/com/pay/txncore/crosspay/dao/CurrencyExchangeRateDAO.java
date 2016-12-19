package com.pay.txncore.crosspay.dao;

import java.util.Date;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.CurrencyExchangeRate;

public interface CurrencyExchangeRateDAO extends BaseDAO<CurrencyExchangeRate> {

	/**
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	CurrencyExchangeRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, Date currentDate);
}