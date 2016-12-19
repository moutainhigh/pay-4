package com.pay.pe.report.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.pe.report.dto.PerformanceReportDTO;
import com.pay.pe.report.dto.QueryBankKeyDTO;
import com.pay.pe.report.dto.QueryRequstParameter;
import com.pay.pe.report.dto.QueryResponseDTO;
import com.pay.pe.report.dto.SumaryRepDTO;

public interface QueryReportDao extends  BaseDAO<Object>{
	
	/**
	 * 查询所有符合条件的出款数据
	 * @param parameter
	 * @return
	 */
	List<QueryResponseDTO> queryReportForFundOut(QueryRequstParameter parameter);

	/**
	 * 查询所有符合条件的出款数据 并带分页信息
	 * @param parameter
	 * @param page
	 * @return
	 */
	Page<QueryResponseDTO> queryReportFundOut(QueryRequstParameter parameter,Page<QueryResponseDTO> page) ;
	
	
	/**
	 * 查询所有符合条件的出款退款数据
	 * @param parameter
	 * @return
	 */
	List<QueryResponseDTO> queryReportForReFundOut(QueryRequstParameter parameter);

	/**
	 * 查询所有符合条件的出款退款数据 并带分页信息
	 * @param parameter
	 * @param page
	 * @return
	 */
	Page<QueryResponseDTO> queryReportReFundOut(QueryRequstParameter parameter,Page<QueryResponseDTO> page) ;
	
	
	/**
	 * 查询所有符合条件的网关入款数据
	 * @param parameter
	 * @return
	 */
	List<QueryResponseDTO> queryReportForFundIn(QueryRequstParameter parameter);

	/**
	 * 查询所有符合条件的网关入款数据 并带分页信息
	 * @param parameter
	 * @param page
	 * @return
	 */
	Page<QueryResponseDTO> queryReportFundIn(QueryRequstParameter parameter,Page<QueryResponseDTO> page) ;
	
	
	/**
	 * 查询所有符合条件的网关入款数据
	 * @param parameter
	 * @return
	 */
	List<QueryResponseDTO> queryReportForReFundIn(QueryRequstParameter parameter);
	
	/**
	 * 查询销售激励报表的数据
	 */
	public List<PerformanceReportDTO> getPerformanceReport(String accTime);

	/**
	 * 查询所有符合条件的网关入款数据 并带分页信息
	 * @param parameter
	 * @param page
	 * @return
	 */
	Page<QueryResponseDTO> queryReportReFundIn(QueryRequstParameter parameter,Page<QueryResponseDTO> page) ;
	
	
	/**
	 * 出款出道列表
	 * @return
	 */
	List<QueryBankKeyDTO> getFundOutChannelBank();
	
	/**
	 * 返回入款出道列表
	 * @return
	 */
	List<QueryBankKeyDTO> getFundInChannelBank();
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public List<SumaryRepDTO> getSumaryPayInfo(Map<String, Object> map);
	
	public List<SumaryRepDTO> getSumaryDepositInfo(Map<String, Object> map);
	
	public List<SumaryRepDTO> getSumaryPayToAccInfo(Map<String, Object> map);
	
	public List<SumaryRepDTO> getSumaryPayToBankInfo(Map<String, Object> map);
	public List<SumaryRepDTO> getSumaryWithDrowInfo(Map<String, Object> map);
	public List<SumaryRepDTO> getSumaryrefundInfo(Map<String, Object> map);
	
	public List<SumaryRepDTO> getCreditQuickPayInfo(Map<String, Object> map);
	public List<SumaryRepDTO> getDebitQuickPayInfo(Map<String, Object> map);
}
