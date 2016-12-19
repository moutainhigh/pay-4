package com.pay.dcc.dao;

import java.util.List;

import com.pay.dcc.model.CountryCurrency;

public interface CountryCurrencyDAO {
	List<CountryCurrency> getCountryCurrencys(String countryCode);
}
