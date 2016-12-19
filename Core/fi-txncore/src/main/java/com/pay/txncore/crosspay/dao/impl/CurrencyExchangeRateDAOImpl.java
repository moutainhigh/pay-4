package com.pay.txncore.crosspay.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.CurrencyExchangeRateDAO;
import com.pay.txncore.model.CurrencyExchangeRate;

public class CurrencyExchangeRateDAOImpl extends
		BaseDAOImpl<CurrencyExchangeRate> implements CurrencyExchangeRateDAO {

	@Override
	public CurrencyExchangeRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, Date currentDate) {

		Map paraMap = new HashMap();
		paraMap.put("sourceCurrency", sourceCurrency);
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("currentDate", currentDate);

		List<CurrencyExchangeRate> list = super.findByCriteria(
				"findCurrentCurrencyRate", paraMap);
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
