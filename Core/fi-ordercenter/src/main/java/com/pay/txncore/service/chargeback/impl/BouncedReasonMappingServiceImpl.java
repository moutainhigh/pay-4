package com.pay.txncore.service.chargeback.impl;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.txncore.model.BouncedReasonMapping;
import com.pay.txncore.service.chargeback.BouncedReasonMappingService;

/**
 * 拒付原因映射
 * 2016年5月24日 15:23:18
 * delin.dong
 */
public class BouncedReasonMappingServiceImpl implements BouncedReasonMappingService {
	
	private BaseDAO bouncedReasonMappingDao;
	
	public void setBouncedReasonMappingDao(BaseDAO bouncedReasonMappingDao) {
		this.bouncedReasonMappingDao = bouncedReasonMappingDao;
	}
	
	@Override
	public List<BouncedReasonMapping> queryBouncedReasonMapping(
			BouncedReasonMapping bouncedReasonMapping,Page page) {
		List<BouncedReasonMapping> reasonMappings=bouncedReasonMappingDao.findByCriteria(bouncedReasonMapping,page);
		return reasonMappings;
	}

	@Override
	public void addBouncedReasonMapping(
			BouncedReasonMapping bouncedReasonMapping) {
		bouncedReasonMappingDao.create(bouncedReasonMapping);
	}
	
	


}
