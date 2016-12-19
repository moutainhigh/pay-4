package com.pay.txncore.crosspay.dao;


import java.util.Date;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.TransactionRate;


/**
 * 商户交易汇率
 * @author peiyu.yang
 *
 */
public interface TransactionRateDAO extends BaseDAO<TransactionRate> {

	/**
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	TransactionRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, String status,String memberCode ,Date currentDate);
	/**
	 * 
	 * @param paramMap
	 * @return
	 */
	TransactionRate newFindCurrentCurrencyRate(Map<String,Object> paramMap);
}