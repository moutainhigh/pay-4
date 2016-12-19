package com.pay.txncore.crosspay.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.TokenPayInfoDAO;
import com.pay.txncore.model.TokenPayInfo;

public class TokenPayInfoDAOImpl extends BaseDAOImpl<TokenPayInfo> implements TokenPayInfoDAO {
	
	public List<TokenPayInfo> findByCriteria(Map<String, Object> paramMap, Page page) throws Exception {
		return super.findByCriteria(paramMap, page);
	}
	
}