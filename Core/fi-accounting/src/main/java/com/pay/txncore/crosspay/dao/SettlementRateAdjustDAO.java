package com.pay.txncore.crosspay.dao;

import java.util.Date;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.SettlementRateAdjust;

public interface SettlementRateAdjustDAO extends BaseDAO<SettlementRateAdjust> {

	/**
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	SettlementRateAdjust findCurrencyRateAdjust(String sourceCurrency,
			String targetCurrency, String status,String memberCode,Date currentDate);
}