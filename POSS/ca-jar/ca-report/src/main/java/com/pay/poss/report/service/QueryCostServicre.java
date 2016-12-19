package com.pay.poss.report.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.QueryBankKeyDTO;
import com.pay.poss.report.dto.QueryRequstParameter;
import com.pay.poss.report.dto.QueryResponseDTO;

public interface QueryCostServicre {

	/**
	 * 查询所有符合条件的数据
	 * 
	 * @param parameter
	 * @return
	 */
	List<QueryResponseDTO> getAllQueryReport(QueryRequstParameter parameter);

	/**
	 * 查询所有符合条件数据 并带分页信息
	 * 
	 * @param parameter
	 * @param page
	 * @return
	 */
	Page<QueryResponseDTO> getAllQueryReport(QueryRequstParameter parameter,
			Page<QueryResponseDTO> page);

	/**
	 * 获得出款渠道列表
	 * 
	 * @return
	 */
	List<QueryBankKeyDTO> getFundOutChennalBank();

	/**
	 * 获得入款渠道列表
	 * 
	 * @return
	 */
	List<QueryBankKeyDTO> getFundInChennalBank();
}
