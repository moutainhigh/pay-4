/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.base.fi.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.base.fi.dao.TradeOrderDAO;
import com.pay.base.fi.model.TradeOrder;
import com.pay.base.fi.model.TransactionSomeDaysSum;
import com.pay.base.fi.model.TransactionYesterdaySum;
import com.pay.base.fi.service.TradeOrderService;

/**
 * @author fjl
 * @date 2011-4-23
 */
public class TradeOrderServiceImpl implements TradeOrderService {

	private TradeOrderDAO tradeOrderDao ;
	
	private int rowNum = 10;
	
	
	@Override
	public List<TradeOrder> getTradeOrderbyPayer(final Long payer) {
		List<TradeOrder> ret = tradeOrderDao.findByPayer(payer,rowNum);
		if(ret == null){
			return new ArrayList<TradeOrder>(); 
		}
		return ret;
	}
	
	
	@Override
	public Integer queryCountByPayer(final Long payer){
		return tradeOrderDao.queryCountByPayer(payer);
	}

	/* (non-Javadoc)
	 * @see com.pay.base.fi.service.TradeOrderService#queryCountByConditions(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public TransactionYesterdaySum queryCountByConditions(final String startTime,
			final String endTime, final Long memberCode) {
		return this.tradeOrderDao.queryCountByConditions(startTime, endTime, memberCode) ;
	}
	
	/**
	 * @param tradeOrderDao the tradeOrderDao to set
	 */
	public void setTradeOrderDao(final TradeOrderDAO tradeOrderDao) {
		this.tradeOrderDao = tradeOrderDao;
	}

	/**
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(final int rowNum) {
		this.rowNum = rowNum;
	}


	@Override
	public List<TransactionSomeDaysSum> queryCountBySomeDaysTranSum(final String startTime,
			final String endTime, final Long memberCode) {
		return tradeOrderDao.queryCountBySomeDaysTranSum(startTime, endTime, memberCode);
	}

}
