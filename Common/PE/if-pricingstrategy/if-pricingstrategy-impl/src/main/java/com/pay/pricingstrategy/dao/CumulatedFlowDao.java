package com.pay.pricingstrategy.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.pricingstrategy.dto.CumulatedFlowDto;

public interface CumulatedFlowDao extends BaseDAO<CumulatedFlowDto> {

	List<CumulatedFlowDto> queryCumulatedFlowList(Map<String, Object> paramMap);

}
