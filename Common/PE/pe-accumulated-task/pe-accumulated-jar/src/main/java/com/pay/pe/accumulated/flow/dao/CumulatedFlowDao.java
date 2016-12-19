package com.pay.pe.accumulated.flow.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.pe.accumulated.flow.dto.CumulatedFlowDto;

public interface CumulatedFlowDao extends BaseDAO<CumulatedFlowDto> {
	
	List<CumulatedFlowDto> queryCumulatedFlowList(Map<String,Object> paramMap);

	
	/**
	* @Title: queryFlowByMonth
	* @Description:by month,paymentServiceCocde 
	* @param @param paramMap
	* @param @return    设定文件
	* @return List<CumulatedFlowDto>    返回类型
	* @throws
	*/ 
	List<CumulatedFlowDto> queryFlowByMonth(Map<String,Object> paramMap);
	
	/**
	* @Title: selectAccumulatedOrderId
	* @Description: 
	* @param @return    设定文件
	* @return Long    返回类型
	* @throws
	*/ 
	public Long selectAccumulatedOrderId();
}
