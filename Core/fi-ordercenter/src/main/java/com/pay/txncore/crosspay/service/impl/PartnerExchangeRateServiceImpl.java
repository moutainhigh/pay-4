package com.pay.txncore.crosspay.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.fi.commons.PartnerExchangeRateStatusEnum;
import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.dao.PartnerExchangeRateDAO;
import com.pay.txncore.crosspay.model.PartnerExchangeRate;
import com.pay.txncore.crosspay.model.PartnerExchangeRateCriteria;
import com.pay.txncore.crosspay.service.CurrencyExchangeRateService;
import com.pay.txncore.crosspay.service.PartnerExchangeRateService;
import com.pay.txncore.model.CurrencyExchangeRate;

public class PartnerExchangeRateServiceImpl implements
		PartnerExchangeRateService {

	private Log logger = LogFactory.getLog(getClass());
	private CurrencyExchangeRateService currencyExchangeRateService;
	private PartnerExchangeRateDAO partnerExchangeRateDao;

	public void setPartnerExchangeRateDao(
			PartnerExchangeRateDAO partnerExchangeRateDao) {
		this.partnerExchangeRateDao = partnerExchangeRateDao;
	}

	public void setCurrencyExchangeRateService(
			CurrencyExchangeRateService currencyExchangeRateService) {
		this.currencyExchangeRateService = currencyExchangeRateService;
	}

	@Override
	public PartnerExchangeRate findById(Long id) {
		return partnerExchangeRateDao.findById(id);
	}

	@Override
	public long createPartnerExchangeRate(
			PartnerExchangeRate partnerExchangeRate) {
		return (Long) partnerExchangeRateDao.create(partnerExchangeRate);
	}

	@Override
	public boolean updatePartnerExchangeRate(
			PartnerExchangeRate partnerExchangeRate) {
		return partnerExchangeRateDao.update(partnerExchangeRate);
	}

	@Override
	public boolean deletePartnerExchangeRate(Long id) {
		return partnerExchangeRateDao.delete(id);
	}

	@Override
	public Page<PartnerExchangeRate> queryPartnerExchangeRateForPage(
			PartnerExchangeRateCriteria rateCriteria,
			Page<PartnerExchangeRate> origPage) {
		// 转换成查询page对象
		List<PartnerExchangeRate> resultList = partnerExchangeRateDao
				.findByCriteria(rateCriteria, origPage);
		// 转换成页面对象
		origPage.setResult(resultList);
		return origPage;
	}

	@Override
	public List<PartnerExchangeRate> queryPartnerExchangeByPartnerIdAndRateDate(
			Date rateDate, String currency) {
		PartnerExchangeRateCriteria query = new PartnerExchangeRateCriteria();
		query.createCriteria()
				.andStatusEqualTo(
						PartnerExchangeRateStatusEnum.NORMAL.getCode())
				.andCurrencyEqualTo(currency)
				.andFromDateLessThanOrEqualTo(rateDate)
				.andToDateGreaterThanOrEqualTo(rateDate);
		return partnerExchangeRateDao.findByCriteria(query);
	}

	@Override
	public PartnerExchangeRate findCurrentCurrencyRate(String partnerId,
			String sourceCurrency, String targetCurrency, Date currentDate) {

		if (logger.isInfoEnabled()) {
			logger.info("partnerId:" + partnerId);
			logger.info("sourceCurrency:" + sourceCurrency);
			logger.info("targetCurrency:" + targetCurrency);
			logger.info("currentDate:" + currentDate);
		}

		if (sourceCurrency.equals(targetCurrency)) {
			PartnerExchangeRate rate = new PartnerExchangeRate();
			rate.setExchangeRate("1");
			return rate;
		}

		PartnerExchangeRate rate = partnerExchangeRateDao
				.findCurrentCurrencyRate(partnerId, sourceCurrency,
						targetCurrency, currentDate);
		if (null == rate) {
			if (logger.isInfoEnabled()) {
				logger.info("未获取到商户订制汇率，从重获取基本汇率");
			}
			CurrencyExchangeRate currencyExchangeRate = currencyExchangeRateService
					.findCurrentCurrencyRate(sourceCurrency, targetCurrency,
							currentDate);

			if (null == currencyExchangeRate) {
				logger.error("未获取基本汇率 ...........#######################################");
			} else {
				rate = new PartnerExchangeRate();
				BeanUtils.copyProperties(currencyExchangeRate, rate);
			}
		}

		return rate;
	}

	@Override
	public void batchCreate(List<PartnerExchangeRate> rates) {
		partnerExchangeRateDao.batchCreate(rates);
	}

	@Override
	public List<PartnerExchangeRate> findByCriteria(PartnerExchangeRate rate) {
		// TODO Auto-generated method stub
		return partnerExchangeRateDao.findByCriteria(rate);
	}

}