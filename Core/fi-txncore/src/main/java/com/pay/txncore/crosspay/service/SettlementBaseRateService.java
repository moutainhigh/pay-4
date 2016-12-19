/**
 * 
 */
package com.pay.txncore.crosspay.service;

import java.util.Date;
import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.SettlementBaseRate;

/**
 * 清算基本汇率
 * @author peiyu.yang
 *
 */
public interface SettlementBaseRateService {

	/**
	 * 
	 * @param rate
	 * @return
	 */
	boolean updateCurrencyRate(SettlementBaseRate rate);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	int updateCurrencyRate(List<SettlementBaseRate> rateList);

	/**
	 * 
	 * @param rates
	 */
	void batchCreate(List<SettlementBaseRate> rates);

	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<SettlementBaseRate> findByCriteria(SettlementBaseRate rate,Page page);

	/**
	 * 获取结算汇率
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	SettlementBaseRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, String status, Date currentDate);
}
