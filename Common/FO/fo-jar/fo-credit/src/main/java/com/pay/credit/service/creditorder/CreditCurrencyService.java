package com.pay.credit.service.creditorder;

import java.util.List;

import com.pay.credit.conditon.creditorder.PartnerCreditCurrency;
import com.pay.credit.model.creditorder.CreditOrderDetailed;
import com.pay.inf.dao.Page;

public interface CreditCurrencyService {
	/***
	 * 查询授信币种
	 * @param creditCurrency
	 * @return
	 */
	List<PartnerCreditCurrency> findAll(PartnerCreditCurrency creditCurrency,Page page);
	/***
	 * 增加授信币种
	 * @param creditCurrency
	 */
	void addCreditCurrency(PartnerCreditCurrency creditCurrency);
	/***
	 * 通过会员号查询授信币种
	 * @param merchantCode
	 * @return
	 */
	String findCreditCurrency(String merchantCode);
	/**
	 * 修改授信币种
	 * @param creditCurrency
	 */
	void updateCurrencyConf(PartnerCreditCurrency creditCurrency);
	
	/***
	 * 删除授信币种
	 * @param deleteId
	 */
	void deleteCurrencyConf(String deleteId);

}
