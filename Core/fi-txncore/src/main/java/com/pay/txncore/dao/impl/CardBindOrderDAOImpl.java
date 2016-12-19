package com.pay.txncore.dao.impl;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.CardBindOrderDAO;
import com.pay.txncore.model.CardBindOrder;

public class CardBindOrderDAOImpl extends BaseDAOImpl<CardBindOrder> 
                                 implements CardBindOrderDAO {

	@Override
	public Long getPayIdbyToken(String token) {
		return (Long) getSqlMapClientTemplate().queryForObject("tokenPay.getPayIDByToken", token);
	}
}
