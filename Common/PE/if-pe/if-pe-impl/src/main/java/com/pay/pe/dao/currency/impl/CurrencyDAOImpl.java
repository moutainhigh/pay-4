package com.pay.pe.dao.currency.impl;

import javax.sql.DataSource;

import org.springframework.util.Assert;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.dao.currency.CurrencyDAO;
import com.pay.pe.model.Currency;

public class CurrencyDAOImpl extends BaseDAOImpl<Currency> implements CurrencyDAO {
	
	private DataSource noProxyDataSource;


	public Currency getCurrencyByCode(String currencyCode) {
		
		Assert.notNull(currencyCode);
		Currency currency = new Currency();
		currency.setCurrencyCode(currencyCode);
		return (Currency) super.findObjectBySelective(currency);
	}


	public void setNoProxyDataSource(DataSource noProxyDataSource) {
		this.noProxyDataSource = noProxyDataSource;
	}


	@Override
	public boolean deleteBySequenceId(Long sequenceId) {
		return super.delete("deleteByPrimaryKey", sequenceId);
	}


	@Override
	public Currency getCurrencyByCF(String currencyCode, String flag) {
	
		Assert.notNull(currencyCode);
		Currency currency = new Currency();
		currency.setCurrencyCode(currencyCode);
		currency.setFlag(flag);
		return (Currency) super.findObjectBySelective(currency);
	}
	
	
	

}
