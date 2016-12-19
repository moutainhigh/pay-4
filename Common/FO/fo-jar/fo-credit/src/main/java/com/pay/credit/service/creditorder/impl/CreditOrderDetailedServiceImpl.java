package com.pay.credit.service.creditorder.impl;

import java.util.List;
import java.util.Map;

import com.pay.credit.dao.creditorder.ICreditOrderDetailedDao;
import com.pay.credit.model.creditorder.CreditOrderDetailed;
import com.pay.credit.service.creditorder.ICreditOrderDetailedService;
import com.pay.inf.dao.Page;

public class CreditOrderDetailedServiceImpl implements ICreditOrderDetailedService {
	
	private ICreditOrderDetailedDao creditOrderDetailedDao;
	
	public void setCreditOrderDetailedDao(
			ICreditOrderDetailedDao creditOrderDetailedDao) {
		this.creditOrderDetailedDao = creditOrderDetailedDao;
	}



	public List<CreditOrderDetailed> findCreditOrderDetailed(
			CreditOrderDetailed creditOrderDetailed,Page page) {
		return creditOrderDetailedDao.findCreditOrderDetailed(creditOrderDetailed,page);
	}



	@Override
	public List<CreditOrderDetailed> findCreditOrderDetailedAll(
			String creditOrderId) {
		return creditOrderDetailedDao.findCreditOrderDetailedAll(creditOrderId);
	}



	@Override
	public void updateCreditDetail(List<Map<String, String>> resultScore) {
		for (Map<String, String> map : resultScore) {
			creditOrderDetailedDao.updateCreditDetail(map);
		}
	}
}
