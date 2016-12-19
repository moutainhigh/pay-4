package com.pay.txncore.crosspay.service;

import java.util.Date;
import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.model.PartnerExchangeRate;
import com.pay.txncore.crosspay.model.PartnerExchangeRateCriteria;

public interface PartnerExchangeRateService {

	/**
	 * 
	 * @param rateCriteria
	 * @param page
	 * @return
	 */
	public Page<PartnerExchangeRate> queryPartnerExchangeRateForPage(
			PartnerExchangeRateCriteria rateCriteria,
			Page<PartnerExchangeRate> page);

	/**
	 * 
	 * @param rateDate
	 * @param currency
	 * @return
	 */
	public List<PartnerExchangeRate> queryPartnerExchangeByPartnerIdAndRateDate(
			Date rateDate, String currency);

	PartnerExchangeRate findById(Long id);

	long createPartnerExchangeRate(PartnerExchangeRate partnerExchangeRate);

	/**
	 * 
	 * @param rates
	 */
	void batchCreate(List<PartnerExchangeRate> rates);

	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<PartnerExchangeRate> findByCriteria(PartnerExchangeRate rate);

	boolean updatePartnerExchangeRate(PartnerExchangeRate partnerExchangeRate);

	boolean deletePartnerExchangeRate(Long id);

	/**
	 * 获取结算汇率
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	PartnerExchangeRate findCurrentCurrencyRate(String partnerId,
			String sourceCurrency, String targetCurrency, Date currentDate);

}