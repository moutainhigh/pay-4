/**
 * 
 */
package com.pay.txncore.crosspay.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.SettlementRate;

/**
 * 清算基本汇率
 * @author peiyu.yang
 *
 */
public interface SettlementRateService {

	/**
	 * 
	 * @param rate
	 * @return
	 */
	boolean updateCurrencyRate(SettlementRate rate);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	int updateCurrencyRateStatus(List<SettlementRate> rateList);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	int updateCurrencyRate(List<SettlementRate> rateList);

	/**
	 * 
	 * @param rates
	 */
	void batchCreate(List<SettlementRate> rates);

	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<SettlementRate> findByCriteria(SettlementRate rate,Page page);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<SettlementRate> findByCriteria(SettlementRate rate);

	/**
	 * 获取结算汇率
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	SettlementRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, String status, String memberCode,Date currentDate);
	
	SettlementRate findCurrencyRate(Map<String,Object> paramMap);
	
	SettlementRate  getSettlementRate(SettlementRate rate);
}
