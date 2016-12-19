/**
 * 
 */
package com.pay.txncore.crosspay.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.TransactionRate;

/**
 * 交易汇率
 * @author peiyu.yang
 *
 */
public interface TransactionRateService {

	/**
	 * 
	 * @param rate
	 * @return
	 */
	boolean updateCurrencyRate(TransactionRate rate);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	int updateCurrencyRateStatus(List<TransactionRate> rateList);
	
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	int updateCurrencyRate(List<TransactionRate> rateList);

	/**
	 * 
	 * @param rates
	 */
	void batchCreate(List<TransactionRate> rates);

	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<TransactionRate> findByCriteria(TransactionRate rate,Page page);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<TransactionRate> findByCriteria(TransactionRate rate);

	/**
	 * 获取结算汇率
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	TransactionRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, String status, String memberCode,Date currentDate);
	
	TransactionRate findCurrencyRate(Map<String,Object> paramMap);
	
	TransactionRate  getTransactionRate(TransactionRate rate);
}
