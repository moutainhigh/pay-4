/**
 * 
 */
package com.pay.txncore.crosspay.service;

import java.util.Date;
import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.TransactionBaseRate;

/**
 * 清算基本汇率
 * @author peiyu.yang
 *
 */
public interface TransactionBaseRateService {

	/**
	 * 
	 * @param rate
	 * @return
	 */
	boolean updateCurrencyRate(TransactionBaseRate rate);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	int updateCurrencyRate(List<TransactionBaseRate> rateList);

	/**
	 * 
	 * @param rates
	 */
	void batchCreate(List<TransactionBaseRate> rates);

	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<TransactionBaseRate> findByCriteria(TransactionBaseRate rate,Page page);

	/**
	 * 获取结算汇率
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	TransactionBaseRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, String status, Date currentDate);
}
