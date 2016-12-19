/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.base.fi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.base.fi.dao.TradeOrderDAO;
import com.pay.base.fi.model.TradeOrder;
import com.pay.base.fi.model.TransactionSomeDaysSum;
import com.pay.base.fi.model.TransactionYesterdaySum;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author fjl
 * @date 2011-4-23
 */
public class TradeOrderDAOImpl extends BaseDAOImpl<TradeOrder> implements TradeOrderDAO {

	@Override
	public List<TradeOrder> findByPayer(final Long payer , final int rowNum){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("payer", payer);
		param.put("rownum", rowNum);
		return getSqlMapClientTemplate().queryForList("findByPayer", param );
	}

	
	@Override
	public Integer queryCountByPayer(final Long payer){
		return (Integer)getSqlMapClientTemplate().queryForObject("findByPayerCount", payer);  
	}


	/* (non-Javadoc)
	 * @see com.pay.base.fi.dao.TradeOrderDAO#queryCountByConditions(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public TransactionYesterdaySum queryCountByConditions(final String startTime,
			final String endTime, final Long memberCode) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("startTime", startTime) ;
		hMap.put("endTime", endTime) ;
		hMap.put("memberCode", memberCode) ;
		return (TransactionYesterdaySum) this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("queryCountByConditions"), hMap) ;
	}


	@Override
	public List<TransactionSomeDaysSum> queryCountBySomeDaysTranSum(final String startTime,
			final String endTime, final Long memberCode) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("startTime", startTime) ;
		hMap.put("endTime", endTime) ;
		hMap.put("memberCode", memberCode) ;
		return getSqlMapClientTemplate().queryForList(this.getNamespace().concat("queryCountBySomeDays"), hMap) ;
	
	}
}
