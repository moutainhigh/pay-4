package com.pay.ce.dao;

import com.pay.ce.model.CurrencyExchangeOrder;
import com.pay.inf.dao.BaseDAO;
import java.util.Map;
import java.util.List;

public interface CurrencyExchangeDAO extends BaseDAO<CurrencyExchangeOrder> {
	public void insertCurrencyExchange(CurrencyExchangeOrder currencyExchangeOrder);
	
	public List<CurrencyExchangeOrder> findByCriteria(CurrencyExchangeOrder currencyExchangeOrder);
	
//	public Integer findByCriteria_COUNT(CurrencyExchangeOrder currencyExchange);
	
}
