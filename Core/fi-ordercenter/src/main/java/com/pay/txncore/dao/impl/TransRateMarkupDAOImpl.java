package com.pay.txncore.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.TransRateMarkupDAO;
import com.pay.txncore.model.TransRateMarkup;

public class TransRateMarkupDAOImpl extends BaseDAOImpl<TransRateMarkup> 
        implements TransRateMarkupDAO {

	@Override
	public TransRateMarkup findTransRateMarkup(Map<String, Object> paramMap) {
		List<TransRateMarkup> list = super.findByCriteria("findCurrentCurrencyRate", paramMap);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
}
