package com.pay.pe.dao.exchangerate;

import com.pay.pe.model.ExchangeRate;
import com.pay.util.MfDateTime;

public interface ExchangeRateDAO {
	/**
	 * 根据原币种、目标币种、时间查询出汇率
	 * 
	 * @param currencyCodeFrom
	 * @param currencyCodeTo
	 * @param date
	 * @return
	 */
	public ExchangeRate findExchangeRate(String currencyCodeFrom,
			String currencyCodeTo, MfDateTime date);
}
