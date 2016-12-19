package com.pay.pe.report.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.report.dao.QueryReportDao;
import com.pay.pe.report.dto.PerformanceReportDTO;
import com.pay.pe.report.dto.QueryBankKeyDTO;
import com.pay.pe.report.dto.QueryRequstParameter;
import com.pay.pe.report.dto.QueryResponseDTO;
import com.pay.pe.report.dto.SumaryRepDTO;

@SuppressWarnings("unchecked")
public class QueryReportDaoImpl extends BaseDAOImpl<Object> implements QueryReportDao{

	@Override
	public List<QueryResponseDTO> queryReportForFundIn(
			QueryRequstParameter parameter) {
		return (List<QueryResponseDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getfundInReport"), parameter);
	}

	@Override
	public List<QueryResponseDTO> queryReportForFundOut(
			QueryRequstParameter parameter) {
		return (List<QueryResponseDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getfundOutReport"), parameter);
	}

	@Override
	public List<QueryResponseDTO> queryReportForReFundIn(
			QueryRequstParameter parameter) {
		return (List<QueryResponseDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getRefundInReport"), parameter);
	}

	@Override
	public List<QueryResponseDTO> queryReportForReFundOut(
			QueryRequstParameter parameter) {
		
		return (List<QueryResponseDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getRefundOutReport"), parameter);
	}

	
	@Override
	public Page<QueryResponseDTO> queryReportFundIn(
			QueryRequstParameter parameter, Page<QueryResponseDTO> page) {
		return queryTempatePage(parameter, this.namespace.concat("getfundInReport"), page);
	}

	@Override
	public Page<QueryResponseDTO> queryReportFundOut(
			QueryRequstParameter parameter, Page<QueryResponseDTO> page) {
		return queryTempatePage(parameter, this.namespace.concat("getfundOutReport"), page);
	}

	@Override
	public Page<QueryResponseDTO> queryReportReFundIn(
			QueryRequstParameter parameter, Page<QueryResponseDTO> page) {
		return queryTempatePage(parameter, this.namespace.concat("getRefundInReport"), page);
	}

	@Override
	public Page<QueryResponseDTO> queryReportReFundOut(
			QueryRequstParameter parameter, Page<QueryResponseDTO> page) {
		return queryTempatePage(parameter, this.namespace.concat("getRefundOutReport"), page);
	}

	@Override
	public List<QueryBankKeyDTO> getFundOutChannelBank() {
		return (List<QueryBankKeyDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getfundOutChannel"));
	}
	
	@Override
	public List<QueryBankKeyDTO> getFundInChannelBank() {
		return (List<QueryBankKeyDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getfundInChannel"));
	}
	public List<PerformanceReportDTO> getPerformanceReport(String accTime) {
		return (List<PerformanceReportDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getPerformanceReport"),accTime);
	}
	/**
	 * 
	 * @param parameter
	 * @param namespace
	 * @param page
	 * @return
	 */
	private Page<QueryResponseDTO> queryTempatePage(
			QueryRequstParameter parameter, String namespace, Page<QueryResponseDTO> page) {
		try {
			if (page.getFirst() == 0) {
				int totalSize = (Integer) super.getSqlMapClientTemplate().queryForObject(namespace+"_COUNT", parameter);
				page.setTotalCount(totalSize);
			}
			page.setResult((List<QueryResponseDTO>)getSqlMapClientTemplate().queryForList(namespace, parameter, page.getFirst(), page.getPageSize()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public List<SumaryRepDTO> getSumaryDepositInfo(Map<String, Object> map) {
		return (List<SumaryRepDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getSumaryDepositReport"), map);
	}

	@Override
	public List<SumaryRepDTO> getSumaryPayInfo(Map<String, Object> map) {
		return (List<SumaryRepDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getSumaryPayReport"), map);
	}

	@Override
	public List<SumaryRepDTO> getSumaryPayToAccInfo(Map<String, Object> map) {
		return (List<SumaryRepDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getSumaryPayToAccReport"), map);
	}

	@Override
	public List<SumaryRepDTO> getSumaryPayToBankInfo(Map<String, Object> map) {
		return (List<SumaryRepDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getSumaryPayToBankReport"), map);
	}

	@Override
	public List<SumaryRepDTO> getSumaryWithDrowInfo(Map<String, Object> map) {
		return (List<SumaryRepDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getSumaryWithDrowReport"), map);
	}

	@Override
	public List<SumaryRepDTO> getSumaryrefundInfo(Map<String, Object> map) {
		return (List<SumaryRepDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getSumaryRefundReport"), map);
	}

	@Override
	public List<SumaryRepDTO> getCreditQuickPayInfo(Map<String, Object> map) {
		return (List<SumaryRepDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getCreditQuickPayReport"), map);
	}

	@Override
	public List<SumaryRepDTO> getDebitQuickPayInfo(Map<String, Object> map) {
		return (List<SumaryRepDTO>) this.getSqlMapClientTemplate().queryForList(this.namespace.concat("getDebitQuickPayReport"), map);
	}
}

