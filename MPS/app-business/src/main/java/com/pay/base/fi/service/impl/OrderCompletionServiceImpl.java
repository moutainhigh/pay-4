/**
 * 
 */
package com.pay.base.fi.service.impl;

import java.util.Map;

import com.pay.base.fi.dao.OrderCompletionDao;
import com.pay.base.fi.model.CustomizationParam;
import com.pay.base.fi.service.OrderCompletionService;

/**
 * @author PengJiangbo
 *
 */
public class OrderCompletionServiceImpl implements OrderCompletionService {

	private OrderCompletionDao orderCompletionDao ;
	
	@Override
	public long insertCustomizationParam(CustomizationParam customizationParam) {
		return this.orderCompletionDao.insertCustomizationParam(customizationParam) ;
	}

	@Override
	public CustomizationParam findCustomizationParam() {
		
		return this.orderCompletionDao.findCustomizationParam();
	}

	public void setOrderCompletionDao(OrderCompletionDao orderCompletionDao) {
		this.orderCompletionDao = orderCompletionDao;
	}

	public int updateCustomizationParam(Map<String, Object> map) {
		return this.orderCompletionDao.updateCustomizationParam(map) ;
	}

}
