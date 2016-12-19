package com.pay.ce.dao.impl;

import java.util.List;

import com.pay.ce.dao.CurrencyExchangeDAO;
import com.pay.ce.model.CurrencyExchangeOrder;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class CurrencyExchangeDAOImpl extends BaseDAOImpl<CurrencyExchangeOrder> implements CurrencyExchangeDAO {

	public void insertCurrencyExchange(
			CurrencyExchangeOrder currencyExchangeOrder) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert(namespace.concat("insertCurrencyExchange"),
				currencyExchangeOrder);
	}

	public List<CurrencyExchangeOrder> findByCriteria(
			CurrencyExchangeOrder currencyExchangeOrder) {
		// TODO Auto-generated method stub
		return null;
	}

/*	public List<CurrencyExchangeOrder> findByCriteria(
			CurrencyExchangeOrder currencyExchangeOrder) {
		// TODO Auto-generated method stub
		return null;
	}*/

	
	
	
	
	
//	public Integer findByCriteria_COUNT(CurrencyExchangeOrder currencyExchange) {
//		// TODO Auto-generated method stub
//		
//	}
//		 
	
	
	
}
