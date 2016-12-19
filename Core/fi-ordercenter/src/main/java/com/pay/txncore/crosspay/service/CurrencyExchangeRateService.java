/**
 * 
 */
package com.pay.txncore.crosspay.service;

import java.util.Date;
import java.util.List;

import com.pay.txncore.model.CurrencyExchangeRate;

/**
 * @author chaoyue
 *
 */
public interface CurrencyExchangeRateService {

	/**
	 * 
	 * @param rate
	 * @return
	 */
	boolean updateCurrencyRate(CurrencyExchangeRate rate);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	int updateCurrencyRate(List<CurrencyExchangeRate> rateList);

	/**
	 * 
	 * @param rates
	 */
	void batchCreate(List<CurrencyExchangeRate> rates);

	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<CurrencyExchangeRate> findByCriteria(CurrencyExchangeRate rate);

	/**
	 * 获取结算汇率
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	CurrencyExchangeRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, Date currentDate);
}
