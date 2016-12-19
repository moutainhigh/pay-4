/**
 * 
 */
package com.pay.txncore.dao.impl;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.TradeExtendsDAO;
import com.pay.txncore.model.TradeExtends;

/**
 * @author huhb
 * 
 */
public class TradeExtendsDAOImpl extends BaseDAOImpl<TradeExtends> implements
		TradeExtendsDAO {

	@Override
	public TradeExtends findByTradeOrderNo(Long tradeOrderNo) {
		return (TradeExtends) this.getSqlMapClientTemplate().queryForObject(
				"tradeExtends.findByTradeOrderNo", tradeOrderNo);
	}

}
