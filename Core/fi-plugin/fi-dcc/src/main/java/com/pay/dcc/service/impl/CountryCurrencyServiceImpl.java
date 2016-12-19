package com.pay.dcc.service.impl;

import java.util.List;

import com.pay.dcc.dao.CountryCurrencyDAO;
import com.pay.dcc.model.CountryCurrency;
import com.pay.dcc.service.CountryCurrencyService;

public class CountryCurrencyServiceImpl implements CountryCurrencyService {
	
	private CountryCurrencyDAO countryCurrencyDAO;
	
	public void setCountryCurrencyDAO(CountryCurrencyDAO countryCurrencyDAO) {
		this.countryCurrencyDAO = countryCurrencyDAO;
	}
	
	@Override
	public List<CountryCurrency> getCountryCurrencys(String countryCode) {
		return countryCurrencyDAO.getCountryCurrencys(countryCode);
	}

}
