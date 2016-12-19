package com.pay.pe.report.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.pe.report.dto.PerformanceReportDTO;
import com.pay.pe.report.dto.QueryBankKeyDTO;
import com.pay.pe.report.dto.QueryRequstParameter;
import com.pay.pe.report.dto.QueryResponseDTO;
import com.pay.pe.report.dto.SumaryResuleDTO;

public interface QueryReportServicre {

	/**
	 * 查询所有符合条件的数据
	 * @param parameter
	 * @return
	 */
	List<QueryResponseDTO> getAllQueryReport(QueryRequstParameter parameter);

	/**
	 * 查询所有符合条件数据 并带分页信息
	 * @param parameter
	 * @param page
	 * @return
	 */
	Page<QueryResponseDTO> getAllQueryReport(QueryRequstParameter parameter,Page<QueryResponseDTO> page) ;
	
	/**
	 * 获得出款渠道列表
	 * @return
	 */
	List<QueryBankKeyDTO> getFundOutChennalBank();
	
	/**
	 * 查询销售激励报表
	 */
	public List<PerformanceReportDTO> getPerformanceReport(String accTime);
	
	/**
	 * 获得入款渠道列表
	 * @return
	 */
	List<QueryBankKeyDTO> getFundInChennalBank();
	
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<SumaryResuleDTO> getSumaryReportInfo(Map<String, Object> map);
}
