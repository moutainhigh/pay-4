/**
 * 
 */
package com.pay.txncore.crosspay.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.txncore.crosspay.dao.OrgCurrencyExchangeRateDAO;
import com.pay.txncore.crosspay.service.CurrencyExchangeRateService;
import com.pay.txncore.crosspay.service.OrgExchangeRateService;
import com.pay.txncore.model.CurrencyExchangeRate;
import com.pay.txncore.model.OrgCurrencyExchangeRate;

/**
 * @author chaoyue
 *
 */
public class OrgExchangeRateServiceImpl implements OrgExchangeRateService {

	private Log logger = LogFactory.getLog(getClass());
	private OrgCurrencyExchangeRateDAO orgCurrencyExchangeRateDAO;
	private CurrencyExchangeRateService currencyExchangeRateService;

	public void setCurrencyExchangeRateService(
			CurrencyExchangeRateService currencyExchangeRateService) {
		this.currencyExchangeRateService = currencyExchangeRateService;
	}

	public void setOrgCurrencyExchangeRateDAO(
			OrgCurrencyExchangeRateDAO orgCurrencyExchangeRateDAO) {
		this.orgCurrencyExchangeRateDAO = orgCurrencyExchangeRateDAO;
	}

	public boolean updateCurrencyRate(OrgCurrencyExchangeRate rate) {
		return orgCurrencyExchangeRateDAO.update(rate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.txncore.crosspay.service.OrgExchangeRateService#batchCreate(java
	 * .util.List)
	 */
	@Override
	public void batchCreate(List<OrgCurrencyExchangeRate> rates) {
		
		if(rates!=null&&rates.size()>0){
			for(OrgCurrencyExchangeRate rate:rates){
				orgCurrencyExchangeRateDAO.update("batchValidOrgRateStatus",rate);
			}
		}
		orgCurrencyExchangeRateDAO.batchCreate(rates);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.txncore.crosspay.service.OrgExchangeRateService#findByCriteria
	 * (com.pay.txncore.model.CurrencyExchangeRate)
	 */
	@Override
	public List<OrgCurrencyExchangeRate> findByCriteria(
			OrgCurrencyExchangeRate rate) {
		// TODO Auto-generated method stub
		return orgCurrencyExchangeRateDAO.findByCriteria(rate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.txncore.crosspay.service.OrgExchangeRateService#
	 * findCurrentCurrencyRate(java.lang.String, java.lang.String,
	 * java.util.Date)
	 */
	@Override
	public OrgCurrencyExchangeRate findCurrentCurrencyRate(String orgCode,
			String sourceCurrency, String targetCurrency, Date currentDate) {

		if (logger.isInfoEnabled()) {
			logger.info("orgCode:" + orgCode);
			logger.info("sourceCurrency:" + sourceCurrency);
			logger.info("targetCurrency:" + targetCurrency);
			logger.info("currentDate:" + currentDate);
		}

		if (sourceCurrency.equals(targetCurrency)) {
			OrgCurrencyExchangeRate rate = new OrgCurrencyExchangeRate();
			rate.setExchangeRate("1");
			return rate;
		}

		OrgCurrencyExchangeRate rate = orgCurrencyExchangeRateDAO
				.findCurrentCurrencyRate(orgCode, sourceCurrency,
						targetCurrency, currentDate);
		return rate;
	}
}
