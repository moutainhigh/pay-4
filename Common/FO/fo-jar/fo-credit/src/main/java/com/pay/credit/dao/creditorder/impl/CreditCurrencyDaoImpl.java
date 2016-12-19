package com.pay.credit.dao.creditorder.impl;

import java.util.List;

import com.pay.credit.conditon.creditorder.PartnerCreditCurrency;
import com.pay.credit.dao.creditorder.CreditCurrencyDao;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;


public class CreditCurrencyDaoImpl extends BaseDAOImpl<PartnerCreditCurrency> implements CreditCurrencyDao{
	@Override
	public List<PartnerCreditCurrency> findAll(PartnerCreditCurrency creditCurrency,Page page) {
		return super.findByCriteria("findByCriteria",creditCurrency,page);
	}

	@Override
	public void addCreditCurrency(PartnerCreditCurrency creditCurrency) {
		this.getSqlMapClientTemplate().insert(getNamespace().concat("addCreditCurrency"),creditCurrency);
	}

	public String findCreditCurrency(String merchantCode) {
		List<PartnerCreditCurrency> queryForList = this.getSqlMapClientTemplate().queryForList(getNamespace().concat("findCreditCurrency"), merchantCode);
			if(queryForList!=null && !queryForList.isEmpty()){
				return queryForList.get(0).getCurrency();
			}
		return null;
	}

	@Override
	public void updateCurrencyConf(PartnerCreditCurrency creditCurrency) {
		this.getSqlMapClientTemplate().update(getNamespace().concat("updateCurrencyConf"), creditCurrency);
	}

	@Override
	public void deleteCurrencyConf(String deleteId) {
		this.getSqlMapClientTemplate().delete(getNamespace().concat("deleteCurrencyConf"), deleteId);
	}
}
