package com.pay.txncore.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.TransRateMarkup;


/**
 * 交易汇率markup
 * @author peiyu.yang
 *
 */
public interface TransRateMarkupService {
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	boolean updateTransRateMarkup(TransRateMarkup markup);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	int updateTransRateMarkupStatus(List<TransRateMarkup> markupList);
	
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	int updateTransRateMarkup(List<TransRateMarkup> markupList);

	/**
	 * 
	 * @param rates
	 */
	void batchCreate(List<TransRateMarkup> markups);

	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<TransRateMarkup> findByCriteria(TransRateMarkup rate,Page page);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<TransRateMarkup> findByCriteria(TransRateMarkup rate);

	/**
	 * 获取结算汇率
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @return
	 */
	TransRateMarkup findTransRateMarkup(String sourceCurrency,
			String targetCurrency, String status, String memberCode,Date currentDate);
	
	TransRateMarkup findTransRateMarkup(Map<String,Object> paramMap);
	
	TransRateMarkup getTransRateMarkup(Map<String,Object> paramMap);
	
	TransRateMarkup  getTransRateMarkup(TransRateMarkup markup);
	
}
