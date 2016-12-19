/**
 * 
 */
package com.pay.txncore.crosspay.service;

import java.util.Date;
import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.TransactionRateAdjust;

/**
 * 清算汇率微调
 * @author peiyu.yang
 *
 */
public interface TransactionRateAdjustService {

	/**
	 * 
	 * @param rate
	 * @return
	 */
	boolean updateCurrencyRateAdjust(TransactionRateAdjust rateAdjust);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	int updateCurrencyRateAdjust(List<TransactionRateAdjust> rateAdjustList);

	/**
	 * 
	 * @param rates
	 */
	void batchCreate(List<TransactionRateAdjust> rateAdjusts);

	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<TransactionRateAdjust> findByCriteria(TransactionRateAdjust rateAdjust,Page page);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<TransactionRateAdjust> findByCriteria(TransactionRateAdjust rateAdjust);

	/**
	 * 获取结算汇率微调
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	TransactionRateAdjust findCurrencyRateAdjust(String sourceCurrency,
			String targetCurrency, String status, String memberCode,Date currentDate);
}
