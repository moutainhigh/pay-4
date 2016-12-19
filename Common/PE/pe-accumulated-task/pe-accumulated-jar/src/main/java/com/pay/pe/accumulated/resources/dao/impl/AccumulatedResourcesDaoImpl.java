package com.pay.pe.accumulated.resources.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.accumulated.resources.dao.AccumulatedResourcesDao;
import com.pay.pe.accumulated.resources.dto.AccumulatedResourcesDto;

public class AccumulatedResourcesDaoImpl extends
		BaseDAOImpl<AccumulatedResourcesDto> implements AccumulatedResourcesDao {

	@Override
	public List<AccumulatedResourcesDto> queryAccumulatedResourcesList() {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("selectAccumulatedResources"), null);
	}

}
