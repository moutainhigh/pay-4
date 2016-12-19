package com.pay.credit.dao.creditorder;

import java.util.List;

import com.pay.credit.conditon.creditorder.PartnerCreditCurrency;
import com.pay.inf.dao.Page;

public interface CreditCurrencyDao {

	List<PartnerCreditCurrency> findAll(PartnerCreditCurrency creditCurrency,Page page);

	void addCreditCurrency(PartnerCreditCurrency creditCurrency);

	String findCreditCurrency(String merchantCode);
	/***
	 * 修改    授信币种配置
	 * @param creditCurrency
	 */
	void updateCurrencyConf(PartnerCreditCurrency creditCurrency);
	/***
	 * 删除授信币配置
	 * @param deleteId
	 */
	void deleteCurrencyConf(String deleteId);

}
