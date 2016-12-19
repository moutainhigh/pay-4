package com.pay.txncore.crosspay.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.SettlementBaseRateDAO;
import com.pay.txncore.model.SettlementBaseRate;
import com.pay.util.StringUtil;

public class SettlementBaseRateDAOImpl extends
		BaseDAOImpl<SettlementBaseRate> implements SettlementBaseRateDAO {

	@Override
	public SettlementBaseRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, String status, Date currentDate) {

		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("sourceCurrency", sourceCurrency);
		paraMap.put("targetCurrency", targetCurrency);
		
		if(currentDate!=null){
		    paraMap.put("currentDate", currentDate);
		}
		
		if(!StringUtil.isEmpty(status)){
			paraMap.put("status", status);
		}
		//paraMap.put("status", status);

		List<SettlementBaseRate> list = super.findByCriteria(
				"findCurrentCurrencyRate", paraMap);
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
