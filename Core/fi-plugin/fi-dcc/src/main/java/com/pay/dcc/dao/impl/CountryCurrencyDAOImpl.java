package com.pay.dcc.dao.impl;

import java.util.List;

import com.pay.dcc.dao.CountryCurrencyDAO;
import com.pay.dcc.model.CountryCurrency;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class CountryCurrencyDAOImpl extends BaseDAOImpl<CountryCurrency> 
                           implements CountryCurrencyDAO {

	@Override
	public List<CountryCurrency> getCountryCurrencys(String countryCode) {
		return super.findByCriteria("findByCountryCode",countryCode);
	}

}
