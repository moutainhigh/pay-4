package com.pay.poss.report.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.QueryBankKeyDTO;
import com.pay.poss.report.dto.QueryRequstParameter;
import com.pay.poss.report.dto.QueryResponseDTO;

public interface QueryCostingDao extends BaseDAO<Object> {

	/**
	 * 查询所有符合条件的出款数据
	 * 
	 * @param parameter
	 * @return
	 */
	List<QueryResponseDTO> queryCostingForFundOut(QueryRequstParameter parameter);

	/**
	 * 查询所有符合条件的出款数据 并带分页信息
	 * 
	 * @param parameter
	 * @param page
	 * @return
	 */
	Page<QueryResponseDTO> queryCostingFundOut(QueryRequstParameter parameter,
			Page<QueryResponseDTO> page);

	/**
	 * 查询所有符合条件的出款退款数据
	 * 
	 * @param parameter
	 * @return
	 */
	List<QueryResponseDTO> queryCostingForReFundOut(
			QueryRequstParameter parameter);

	/**
	 * 查询所有符合条件的出款退款数据 并带分页信息
	 * 
	 * @param parameter
	 * @param page
	 * @return
	 */
	Page<QueryResponseDTO> queryCostingReFundOut(
			QueryRequstParameter parameter, Page<QueryResponseDTO> page);

	/**
	 * 查询所有符合条件的网关入款数据
	 * 
	 * @param parameter
	 * @return
	 */
	List<QueryResponseDTO> queryCostingForFundIn(QueryRequstParameter parameter);

	/**
	 * 查询所有符合条件的网关入款数据 并带分页信息
	 * 
	 * @param parameter
	 * @param page
	 * @return
	 */
	Page<QueryResponseDTO> queryCostingFundIn(QueryRequstParameter parameter,
			Page<QueryResponseDTO> page);

	/**
	 * 查询所有充值
	 * 
	 * @param parameter
	 * @return
	 */
	List<QueryResponseDTO> queryDepositCosting(QueryRequstParameter parameter);

	/**
	 * 查询所充值
	 * 
	 * @param parameter
	 * @param page
	 * @return
	 */
	Page<QueryResponseDTO> queryDepositCosting(QueryRequstParameter parameter,
			Page<QueryResponseDTO> page);

	/**
	 * 查询所有符合条件的网关入款数据
	 * 
	 * @param parameter
	 * @return
	 */
	List<QueryResponseDTO> queryCostingForReFundIn(
			QueryRequstParameter parameter);

	/**
	 * 查询所有符合条件的网关入款数据 并带分页信息
	 * 
	 * @param parameter
	 * @param page
	 * @return
	 */
	Page<QueryResponseDTO> queryCostingReFundIn(QueryRequstParameter parameter,
			Page<QueryResponseDTO> page);

	/**
	 * 出款出道列表
	 * 
	 * @return
	 */
	List<QueryBankKeyDTO> getFundOutChannelBank();

	/**
	 * 返回入款出道列表
	 * 
	 * @return
	 */
	List<QueryBankKeyDTO> getFundInChannelBank();
}
