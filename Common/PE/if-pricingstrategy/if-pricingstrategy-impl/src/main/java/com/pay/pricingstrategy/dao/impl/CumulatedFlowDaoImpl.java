package com.pay.pricingstrategy.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pricingstrategy.dao.CumulatedFlowDao;
import com.pay.pricingstrategy.dto.CumulatedFlowDto;

public class CumulatedFlowDaoImpl extends BaseDAOImpl<CumulatedFlowDto>
		implements CumulatedFlowDao {

	@Override
	public List<CumulatedFlowDto> queryCumulatedFlowList(
			Map<String, Object> paramMap) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("selectCumulatedFlow"), paramMap);
	}

}
