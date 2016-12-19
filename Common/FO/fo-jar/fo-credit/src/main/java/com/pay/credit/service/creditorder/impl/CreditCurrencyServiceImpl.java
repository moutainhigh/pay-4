package com.pay.credit.service.creditorder.impl;

import java.sql.Timestamp;
import java.util.List;

import com.pay.credit.conditon.creditorder.PartnerCreditCurrency;
import com.pay.credit.dao.creditorder.CreditCurrencyDao;
import com.pay.credit.model.creditorder.CreditOrderDetailed;
import com.pay.credit.service.creditorder.CreditCurrencyService;
import com.pay.inf.dao.Page;

public class CreditCurrencyServiceImpl  implements CreditCurrencyService{
	
	
	private CreditCurrencyDao creditCurrencyDao;
	
	public void setCreditCurrencyDao(CreditCurrencyDao creditCurrencyDao) {
		this.creditCurrencyDao = creditCurrencyDao;
	}

	/***
	 * 查询授信币种
	 */
	public List<PartnerCreditCurrency> findAll(
			PartnerCreditCurrency creditCurrency,Page page) {
		return creditCurrencyDao.findAll(creditCurrency,page);
	}
	/***
	 * 增加授信币种
	 */
	public void addCreditCurrency(PartnerCreditCurrency creditCurrency) {
		 creditCurrencyDao.addCreditCurrency(creditCurrency);
	}

	@Override
	public String findCreditCurrency(String merchantCode) {
		return  creditCurrencyDao.findCreditCurrency(merchantCode);
	}

	@Override
	public void updateCurrencyConf(PartnerCreditCurrency creditCurrency) {
		creditCurrencyDao.updateCurrencyConf(creditCurrency);
	}

	@Override
	public void deleteCurrencyConf(String deleteId) {
		creditCurrencyDao.deleteCurrencyConf(deleteId);
	}
}
