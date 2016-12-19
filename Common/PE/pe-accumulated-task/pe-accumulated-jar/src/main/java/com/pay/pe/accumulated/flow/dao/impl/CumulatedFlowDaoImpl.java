package com.pay.pe.accumulated.flow.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.accumulated.flow.dao.CumulatedFlowDao;
import com.pay.pe.accumulated.flow.dto.CumulatedFlowDto;

public class CumulatedFlowDaoImpl extends BaseDAOImpl<CumulatedFlowDto>
		implements CumulatedFlowDao {

	@Override
	public List<CumulatedFlowDto> queryCumulatedFlowList(
			Map<String, Object> paramMap) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("selectCumulatedFlowByDeal"), paramMap);
	}

	@Override
	public List<CumulatedFlowDto> queryFlowByMonth(Map<String, Object> paramMap) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("selectCumulatedFlow"), paramMap);
	}

	@Override
	public Long selectAccumulatedOrderId() {
		return (Long) getSqlMapClientTemplate().queryForObject(
				namespace.concat("selectAccumulatedOrderId"));
	}

}
