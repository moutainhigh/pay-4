package com.pay.txncore.crosspay.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.OrgCurrencyExchangeRateDAO;
import com.pay.txncore.model.OrgCurrencyExchangeRate;

public class OrgCurrencyExchangeRateDAOImpl extends
		BaseDAOImpl<OrgCurrencyExchangeRate> implements
		OrgCurrencyExchangeRateDAO {

	@Override
	public OrgCurrencyExchangeRate findCurrentCurrencyRate(String orgCode,
			String sourceCurrency, String targetCurrency, Date currentDate) {

		Map paraMap = new HashMap();
		paraMap.put("orgCode", orgCode);
		paraMap.put("sourceCurrency", sourceCurrency);
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("currentDate", currentDate);

		List<OrgCurrencyExchangeRate> list = super.findByCriteria(
				"findCurrentCurrencyRate", paraMap);
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
