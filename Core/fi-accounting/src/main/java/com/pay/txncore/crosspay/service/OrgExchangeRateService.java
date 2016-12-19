/**
 * 
 */
package com.pay.txncore.crosspay.service;

import java.util.Date;
import java.util.List;

import com.pay.txncore.model.OrgCurrencyExchangeRate;

/**
 * @author chaoyue
 *
 */
public interface OrgExchangeRateService {

	/**
	 * 
	 * @param rate
	 * @return
	 */
	boolean updateCurrencyRate(OrgCurrencyExchangeRate rate);

	/**
	 * 
	 * @param rates
	 */
	void batchCreate(List<OrgCurrencyExchangeRate> rates);

	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<OrgCurrencyExchangeRate> findByCriteria(OrgCurrencyExchangeRate rate);

	/**
	 * 获取结算汇率
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	OrgCurrencyExchangeRate findCurrentCurrencyRate(String orgCode,
			String sourceCurrency, String targetCurrency, Date currentDate);
}
