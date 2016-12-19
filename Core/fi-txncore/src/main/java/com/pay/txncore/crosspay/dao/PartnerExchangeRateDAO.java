package com.pay.txncore.crosspay.dao;

import java.util.Date;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.crosspay.model.PartnerExchangeRate;

public interface PartnerExchangeRateDAO extends BaseDAO<PartnerExchangeRate> {

	/**
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	PartnerExchangeRate findCurrentCurrencyRate(String partnerId,
			String sourceCurrency, String targetCurrency, Date currentDate);
}