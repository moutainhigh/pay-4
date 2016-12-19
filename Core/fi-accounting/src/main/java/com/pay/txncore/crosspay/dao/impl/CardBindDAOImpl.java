package com.pay.txncore.crosspay.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.CardBindDAO;
import com.pay.txncore.model.CardBindOrder;

public class CardBindDAOImpl extends BaseDAOImpl<CardBindOrder> implements CardBindDAO {
	
	public List<CardBindOrder> findByCriteria(Map<String, Object> paramMap, Page page) throws Exception {
		return super.findByCriteria(paramMap, page);
	}
}