package com.pay.txncore.crosspay.dao;

import java.util.Date;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.TransactionRateAdjust;

public interface TransactionRateAdjustDAO extends BaseDAO<TransactionRateAdjust> {

	/**
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	TransactionRateAdjust findCurrencyRateAdjust(String sourceCurrency,
			String targetCurrency, String status,String memberCode, Date currentDate);
}