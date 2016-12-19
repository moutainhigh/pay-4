package com.pay.pe.accumulated.resources.service.impl;

import java.util.List;

import com.pay.pe.accumulated.resources.dao.AccumulatedResourcesDao;
import com.pay.pe.accumulated.resources.dto.AccumulatedResourcesDto;
import com.pay.pe.accumulated.resources.service.AccumulatedResService;

public class AccumulatedResServiceImpl implements AccumulatedResService {
	private AccumulatedResourcesDao resourcesDao;


	@Override
	public List<AccumulatedResourcesDto> queryAccumulatedResourcesList() {		
		return resourcesDao.queryAccumulatedResourcesList();
	}

	public void setResourcesDao(AccumulatedResourcesDao resourcesDao) {
		this.resourcesDao = resourcesDao;
	}

}
