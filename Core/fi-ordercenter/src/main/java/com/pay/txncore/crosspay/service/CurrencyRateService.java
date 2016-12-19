package com.pay.txncore.crosspay.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.txncore.model.SettlementBaseRate;
import com.pay.txncore.model.SettlementRate;
import com.pay.txncore.model.SettlementRateAdjust;
import com.pay.txncore.model.TransactionBaseRate;
import com.pay.txncore.model.TransactionRate;
import com.pay.txncore.model.TransactionRateAdjust;

public interface CurrencyRateService {
	void createTransactionBaseRate(List<TransactionBaseRate> baseRates);
	void createTransactionRate(List<TransactionRate> baseRates);
	void createTransactionRateAdjust(List<TransactionRateAdjust> baseRateAdjusts);
	
	void createSettlementBaseRate(List<SettlementBaseRate> baseRates);
	void createSettlemenRate(List<SettlementRate> baseRates);
	void createSettlemenRateAdjust(List<SettlementRateAdjust> baseRateAdjusts);
	
	TransactionRate getTransactionRate(String sourceCurrency,
			String targetCurrency, String status, String memberCode,Date currentDate);
	
	SettlementRate getSettlementRate(String sourceCurrency,
			String targetCurrency, String status, String memberCode,Date currentDate);
	
    /**
     * 
     * @param param
     * @return
     */
	TransactionRate getNewTransactionRate(Map<String,Object> param);
	TransactionRate getBoncedTransactionRate(Map<String,Object> params,String currencyCode,String targetCurrencyCode,Date currentDate);
    
	/***
	 * 
	 * @param param
	 * @return
	 */
	SettlementRate getNewSettlementRate(Map<String,Object> param);
	
	SettlementBaseRate findSettlementBaseRate(String sourceCurrency,
			String targetCurrency, String status, Date currentDate);
	
	TransactionBaseRate findTransactionBaseRate(String sourceCurrency,
			String targetCurrency, String status, Date currentDate);
}
