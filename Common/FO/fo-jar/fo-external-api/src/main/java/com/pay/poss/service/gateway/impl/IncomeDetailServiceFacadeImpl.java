package com.pay.poss.service.gateway.impl;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.dao.ExternalFacadeDao;
import com.pay.poss.dto.fi.IncomeDetailParaDTO;
import com.pay.poss.service.gateway.IncomeDetailServiceFacade;

/**
 * @author Sandy
 * @Date 2011-4-11
 * @Description 收款明细Service
 * @Copyright Copyright © 2004-2013 pay.com. All rights reserved.
 */
public class IncomeDetailServiceFacadeImpl implements IncomeDetailServiceFacade {

	private ExternalFacadeDao externalFacadeDao;

	@Override
	public Page<Map<String,Object>> queryIncomeDetailList(
			IncomeDetailParaDTO incomeDetailParaDTO, Integer pageNo,
			Integer pageSize) {
		return externalFacadeDao.queryIncomeDetailList(incomeDetailParaDTO, pageNo, pageSize);
	}

	@Override
	public Map<String, Object> querySingleIncomeDetail(Map<String, Object> map) {
		return externalFacadeDao.querySingleIncomeDetail(map);
	}
	
	/** set **/
	public void setExternalFacadeDao(ExternalFacadeDao externalFacadeDao) {
		this.externalFacadeDao = externalFacadeDao;
	}
}
