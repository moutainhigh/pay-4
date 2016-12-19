/**
 * 
 */
package com.pay.txncore.crosspay.service;

import java.util.Date;
import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.SettlementRateAdjust;

/**
 * 清算汇率微调
 * @author peiyu.yang
 *
 */
public interface SettlementRateAdjustService {

	/**
	 * 
	 * @param rate
	 * @return
	 */
	boolean updateCurrencyRateAdjust(SettlementRateAdjust rateAdjust);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	int updateCurrencyRateAdjust(List<SettlementRateAdjust> rateAdjustList);

	/**
	 * 
	 * @param rates
	 */
	void batchCreate(List<SettlementRateAdjust> rateAdjusts);

	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<SettlementRateAdjust> findByCriteria(SettlementRateAdjust rateAdjust,Page page);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<SettlementRateAdjust> findByCriteria(SettlementRateAdjust rateAdjust);

	/**
	 * 获取结算汇率微调
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	SettlementRateAdjust findCurrencyRateAdjust(String sourceCurrency,
			String targetCurrency, String status, String memberCode,Date currentDate);
}
