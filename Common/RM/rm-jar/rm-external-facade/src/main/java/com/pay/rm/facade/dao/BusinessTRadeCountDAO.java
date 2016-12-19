package com.pay.rm.facade.dao;

import java.util.List;
import java.util.Map;

import com.pay.rm.facade.model.BusinessTradeCount;

public interface BusinessTRadeCountDAO {
	List<BusinessTradeCount>  getBusinessTradeCount(BusinessTradeCount count);
	BusinessTradeCount  getBusinessTradeCount(Map<String,Object> params);
	BusinessTradeCount createBusinessTradeCount(BusinessTradeCount count);
	boolean updateBTC(Map<String,Object> params);
}
