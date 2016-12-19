package com.pay.txncore.crosspay.dao.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.TransactionBaseRateDAO;
import com.pay.txncore.model.TransactionBaseRate;
import com.pay.util.StringUtil;

public class TransactionBaseRateDAOImpl extends
		BaseDAOImpl<TransactionBaseRate> implements TransactionBaseRateDAO {

	@Override
	public TransactionBaseRate findCurrentCurrencyRate(String sourceCurrency,
			String targetCurrency, String status, Date currentDate) {

		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("sourceCurrency", sourceCurrency);
		paraMap.put("targetCurrency", targetCurrency);
		if(currentDate!=null){
					Calendar c = Calendar.getInstance();
					c.setTime(currentDate);
		            c.add(Calendar.DAY_OF_MONTH,-30);
				    Date effectDate = c.getTime();
				    paraMap.put("effectDate", effectDate);
				    Calendar c1 = Calendar.getInstance();
					c1.setTime(currentDate);
		            c1.add(Calendar.DAY_OF_MONTH,1);
				    Date expireDate = c1.getTime();
				    paraMap.put("expireDate",expireDate);
		}
		
		if(!StringUtil.isEmpty(status)){
			paraMap.put("status", status);
		}
		
		List<TransactionBaseRate> list = super.findByCriteria(
				"findCurrentCurrencyRate", paraMap);
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
