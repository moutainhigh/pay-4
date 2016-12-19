package com.pay.pe.dao.currency;

import com.pay.inf.dao.BaseDAO;
import com.pay.pe.model.Currency;



public interface CurrencyDAO extends BaseDAO<Currency>{
	/**
	 * 根据币种缩写查找币种
	 * @param currencyCode
	 * @return
	 */
	public Currency getCurrencyByCode(String currencyCode);
	
	public boolean  deleteBySequenceId(Long sequenceId);
	
	public Currency getCurrencyByCF(String currencyCode,String flag);

}
