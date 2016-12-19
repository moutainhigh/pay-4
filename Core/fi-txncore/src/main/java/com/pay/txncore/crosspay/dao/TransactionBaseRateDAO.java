package com.pay.txncore.crosspay.dao;


import java.util.Date;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.TransactionBaseRate;

public interface TransactionBaseRateDAO extends BaseDAO<TransactionBaseRate> {

	/**
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	TransactionBaseRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, String status, Date currentDate);
}