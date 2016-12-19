package com.pay.pe.account.service.impl;

import com.pay.inf.dao.Page;
import com.pay.pe.account.dao.AccumulatedResourcesDao;
import com.pay.pe.account.dto.AccumulatedResourcesDTO;
import com.pay.pe.account.service.AccumulatedResourcesService;

public class AccumulatedResourcesServiceImpl implements AccumulatedResourcesService {
	
	private AccumulatedResourcesDao accumulatedResourcesDao;

	public AccumulatedResourcesDao getAccumulatedResourcesDao() {
		return accumulatedResourcesDao;
	}

	public void setAccumulatedResourcesDao(
			AccumulatedResourcesDao accumulatedResourcesDao) {
		this.accumulatedResourcesDao = accumulatedResourcesDao;
	}

	@Override
	public Page<AccumulatedResourcesDTO> searchPage(AccumulatedResourcesDTO dto,Page<AccumulatedResourcesDTO> pageParam) {
		return 	getAccumulatedResourcesDao().search(dto, pageParam);
	}

	@Override
	public AccumulatedResourcesDTO findById(Long id) {
		return getAccumulatedResourcesDao().findById(id);
	}

	@Override
	public Long addAccumulatedResRdTx(AccumulatedResourcesDTO dto) {
		
		int repeatCount = accumulatedResourcesDao.repeatCount(dto);
		if(repeatCount != 0)
			return -1L;
		return (Long) accumulatedResourcesDao.create(dto);
	}

	@Override
	public boolean modifyAccumulatedResRdTx(AccumulatedResourcesDTO dto) {
		int repeatCount = accumulatedResourcesDao.repeatCount(dto);
		if(repeatCount != 0) //
			return false;
		return accumulatedResourcesDao.update(dto);
	}

	@Override
	public boolean removeAccuResRdTx(Long id) {
		return accumulatedResourcesDao.delete(id);
	}

	
	

	
	
}
