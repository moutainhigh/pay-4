/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.base.fi.dao;

import java.util.List;

import com.pay.base.fi.model.TradeOrder;
import com.pay.base.fi.model.TransactionSomeDaysSum;
import com.pay.base.fi.model.TransactionYesterdaySum;
import com.pay.inf.dao.BaseDAO;

/**
 * @author fjl
 * @date 2011-4-23
 */
public interface TradeOrderDAO extends BaseDAO<TradeOrder> {

	/**
	 * 根据付款方帐号查询最近rowNum 笔记录
	 * @param payer
	 * @param rowNum
	 * @return
	 */
	public List<TradeOrder> findByPayer(Long payer, int rowNum);
	
	
	public Integer queryCountByPayer(Long payer);
	
	/**
	 * 网关订单昨日交易查询
	 * @param startTime
	 * @param endTime
	 * @param memberCode
	 * @return
	 */
	TransactionYesterdaySum queryCountByConditions(String startTime, String endTime, Long memberCode) ;
	/**
	 * 近30日订单成交量统计
	 * @param startTime
	 * @param endTime
	 * @param memberCode
	 * @return
	 */
	List<TransactionSomeDaysSum> queryCountBySomeDaysTranSum(String startTime, String endTime, Long memberCode) ;
	
}
