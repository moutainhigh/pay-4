package com.pay.pe.service.exchangerate.impl;

import com.pay.pe.dao.exchangerate.ExchangeRateDAO;
import com.pay.pe.dto.ExchangeRateDTO;
import com.pay.pe.model.ExchangeRate;
import com.pay.pe.service.exchangerate.ExchangeRateService;
import com.pay.util.BeanConvertUtil;
import com.pay.util.MfDateTime;

public class ExchangeRateServiceImpl implements ExchangeRateService {
	private ExchangeRateDAO exchangeRateDAO;

	public void setExchangeRateDAO(ExchangeRateDAO exchangeRateDAO) {
		this.exchangeRateDAO = exchangeRateDAO;
	}

	public ExchangeRateDTO findExchangeRate(String currencyCodeFrom,
			String currencyCodeTo, MfDateTime date) {
		ExchangeRate exchangeRate = (ExchangeRate) this.exchangeRateDAO
				.findExchangeRate(currencyCodeFrom, currencyCodeTo, date);

		if (exchangeRate == null) {
			return null;
		}
		return BeanConvertUtil.convert(ExchangeRateDTO.class, exchangeRate);
	}

}
