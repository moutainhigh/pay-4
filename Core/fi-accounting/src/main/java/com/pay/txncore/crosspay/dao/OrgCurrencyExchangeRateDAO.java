package com.pay.txncore.crosspay.dao;

import java.util.Date;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.OrgCurrencyExchangeRate;

public interface OrgCurrencyExchangeRateDAO extends
		BaseDAO<OrgCurrencyExchangeRate> {

	/**
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	OrgCurrencyExchangeRate findCurrentCurrencyRate(String orgCode,
			String sourceCurrency, String targetCurrency, Date currentDate);
}