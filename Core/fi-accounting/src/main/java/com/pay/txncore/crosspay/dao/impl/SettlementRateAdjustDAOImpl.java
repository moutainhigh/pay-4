package com.pay.txncore.crosspay.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.SettlementRateAdjustDAO;
import com.pay.txncore.model.SettlementRateAdjust;
import com.pay.util.StringUtil;

public class SettlementRateAdjustDAOImpl extends
		BaseDAOImpl<SettlementRateAdjust> implements SettlementRateAdjustDAO {

	@Override
	public SettlementRateAdjust findCurrencyRateAdjust(String sourceCurrency,
			String targetCurrency, String status, String memberCode,Date currentDate) {

		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("sourceCurrency", sourceCurrency);
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("memberCode", memberCode);
		if(currentDate!=null){
		     paraMap.put("currentDate", currentDate);
		}
		
		if(!StringUtil.isEmpty(status)){
			paraMap.put("status", status);
		}
		//paraMap.put("status",status);
		
		List<SettlementRateAdjust> list = super.findByCriteria(
				"findCurrentCurrencyRate", paraMap);
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
