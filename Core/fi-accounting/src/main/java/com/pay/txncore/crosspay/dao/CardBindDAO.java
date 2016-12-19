package com.pay.txncore.crosspay.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.txncore.model.CardBindOrder;

public interface CardBindDAO extends BaseDAO<CardBindOrder> {
	
	public List<CardBindOrder> findByCriteria(Map<String, Object> paramMap, Page page) throws Exception;
}