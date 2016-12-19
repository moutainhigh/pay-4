/**
 * 
 */
package com.pay.txncore.crosspay.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.txncore.crosspay.dao.CurrencyExchangeRateDAO;
import com.pay.txncore.crosspay.service.CurrencyExchangeRateService;
import com.pay.txncore.model.CurrencyExchangeRate;

/**
 * @author chaoyue
 *
 */
public class CurrencyExchangeRateServiceImpl implements
		CurrencyExchangeRateService {

	private CurrencyExchangeRateDAO currencyExchangeRateDAO;

	@Override
	public boolean updateCurrencyRate(CurrencyExchangeRate rate) {
		return currencyExchangeRateDAO.update(rate);
	}

	@Override
	public void batchCreate(List<CurrencyExchangeRate> rates) {	
		if(rates!=null&&rates.size()>0){
			for(CurrencyExchangeRate rate:rates){
				currencyExchangeRateDAO.update("batchValidStatus",rate);
			}
		}
		
		currencyExchangeRateDAO.batchCreate(rates);
	}

	public void setCurrencyExchangeRateDAO(
			CurrencyExchangeRateDAO currencyExchangeRateDAO) {
		this.currencyExchangeRateDAO = currencyExchangeRateDAO;
	}

	@Override
	public List<CurrencyExchangeRate> findByCriteria(CurrencyExchangeRate rate) {

		return currencyExchangeRateDAO.findByCriteria(rate);
	}

	@Override
	public CurrencyExchangeRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, Date currentDate) {

		return currencyExchangeRateDAO.findCurrentCurrencyRate(sourceCurrency,
				targetCurrency, currentDate);
	}

	@Override
	public int updateCurrencyRate(List<CurrencyExchangeRate> rateList) {
		return currencyExchangeRateDAO.updateBatch("batchValidStatus", rateList);
	}

}
