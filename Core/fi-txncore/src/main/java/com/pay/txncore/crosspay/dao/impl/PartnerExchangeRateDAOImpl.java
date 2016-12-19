package com.pay.txncore.crosspay.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.PartnerExchangeRateDAO;
import com.pay.txncore.crosspay.model.PartnerExchangeRate;

public class PartnerExchangeRateDAOImpl extends
		BaseDAOImpl<PartnerExchangeRate> implements PartnerExchangeRateDAO {

	@Override
	public PartnerExchangeRate findCurrentCurrencyRate(String partnerId,
			String sourceCurrency, String targetCurrency, Date currentDate) {
		Map paraMap = new HashMap();
		paraMap.put("partnerId", partnerId);
		paraMap.put("sourceCurrency", sourceCurrency);
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("currentDate", currentDate);

		List<PartnerExchangeRate> list = super.findByCriteria(
				"findCurrentCurrencyRate", paraMap);
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
