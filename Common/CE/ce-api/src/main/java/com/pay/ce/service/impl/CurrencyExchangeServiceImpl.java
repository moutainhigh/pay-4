package com.pay.ce.service.impl;

import java.util.List;

import com.pay.ce.dao.CurrencyExchangeDAO;
import com.pay.ce.model.CurrencyExchangeOrder;
import com.pay.ce.service.CurrencyExchangeService;

public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
	
	private CurrencyExchangeDAO  currencyExchangeDAO;

	public void setCurrencyExchangeDAO(CurrencyExchangeDAO currencyExchangeDAO) {
		this.currencyExchangeDAO = currencyExchangeDAO;
	}

	public void insertCurrencyExchange(
			CurrencyExchangeOrder currencyExchangeOrder) {
		// TODO Auto-generated method stub
		this.currencyExchangeDAO.insertCurrencyExchange(currencyExchangeOrder);
	}

	public List<CurrencyExchangeOrder> findByCriteria(
			CurrencyExchangeOrder currencyExchangeOrder) {
		// TODO Auto-generated method stub
		return this.currencyExchangeDAO.findByCriteria(currencyExchangeOrder);
	}
	
	

}
