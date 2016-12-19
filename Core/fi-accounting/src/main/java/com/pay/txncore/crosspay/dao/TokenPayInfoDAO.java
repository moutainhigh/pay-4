package com.pay.txncore.crosspay.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.txncore.model.TokenPayInfo;

public interface TokenPayInfoDAO extends BaseDAO<TokenPayInfo> {
	
	public List<TokenPayInfo> findByCriteria(Map<String, Object> paramMap, Page page) throws Exception;
}