package com.pay.poss.report.dao.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.report.dao.QueryCostingDao;
import com.pay.poss.report.dto.QueryBankKeyDTO;
import com.pay.poss.report.dto.QueryRequstParameter;
import com.pay.poss.report.dto.QueryResponseDTO;

@SuppressWarnings("unchecked")
public class QueryCostingDaoImpl extends BaseDAOImpl<Object> implements
		QueryCostingDao {

	// 网关交易
	@Override
	public List<QueryResponseDTO> queryCostingForFundIn(
			QueryRequstParameter parameter) {
		return (List<QueryResponseDTO>) this.getSqlMapClientTemplate()
				.queryForList(this.namespace.concat("getfundInCosting"),
						parameter);
	}

	@Override
	public Page<QueryResponseDTO> queryCostingFundIn(
			QueryRequstParameter parameter, Page<QueryResponseDTO> page) {
		return queryTempatePage(parameter,
				this.namespace.concat("getfundInCosting"), page);
	}

	// 充值交易
	@Override
	public List<QueryResponseDTO> queryDepositCosting(
			QueryRequstParameter parameter) {
		return (List<QueryResponseDTO>) this.getSqlMapClientTemplate()
				.queryForList(this.namespace.concat("getdepositCosting"),
						parameter);
	}

	@Override
	public Page<QueryResponseDTO> queryDepositCosting(
			QueryRequstParameter parameter, Page<QueryResponseDTO> page) {
		return queryTempatePage(parameter,
				this.namespace.concat("getdepositCosting"), page);
	}

	// 出款交易
	@Override
	public List<QueryResponseDTO> queryCostingForFundOut(
			QueryRequstParameter parameter) {
		return (List<QueryResponseDTO>) this.getSqlMapClientTemplate()
				.queryForList(this.namespace.concat("getfundOutCosting"),
						parameter);
	}

	@Override
	public Page<QueryResponseDTO> queryCostingFundOut(
			QueryRequstParameter parameter, Page<QueryResponseDTO> page) {
		return queryTempatePage(parameter,
				this.namespace.concat("getfundOutCosting"), page);
	}

	// 网关退款
	@Override
	public List<QueryResponseDTO> queryCostingForReFundIn(
			QueryRequstParameter parameter) {
		return (List<QueryResponseDTO>) this.getSqlMapClientTemplate()
				.queryForList(this.namespace.concat("getRefundInCosting"),
						parameter);
	}

	@Override
	public Page<QueryResponseDTO> queryCostingReFundIn(
			QueryRequstParameter parameter, Page<QueryResponseDTO> page) {
		return queryTempatePage(parameter,
				this.namespace.concat("getRefundInCosting"), page);
	}

	// 出款退款
	@Override
	public List<QueryResponseDTO> queryCostingForReFundOut(
			QueryRequstParameter parameter) {
		return (List<QueryResponseDTO>) this.getSqlMapClientTemplate()
				.queryForList(this.namespace.concat("getRefundOutCosting"),
						parameter);
	}

	@Override
	public Page<QueryResponseDTO> queryCostingReFundOut(
			QueryRequstParameter parameter, Page<QueryResponseDTO> page) {
		return queryTempatePage(parameter,
				this.namespace.concat("getRefundOutCosting"), page);
	}

	@Override
	public List<QueryBankKeyDTO> getFundOutChannelBank() {
		return (List<QueryBankKeyDTO>) this.getSqlMapClientTemplate()
				.queryForList(this.namespace.concat("getfundOutChannel"));
	}

	@Override
	public List<QueryBankKeyDTO> getFundInChannelBank() {
		return (List<QueryBankKeyDTO>) this.getSqlMapClientTemplate()
				.queryForList(this.namespace.concat("getfundInChannel"));
	}

	/**
	 * 
	 * @param parameter
	 * @param namespace
	 * @param page
	 * @return
	 */
	private Page<QueryResponseDTO> queryTempatePage(
			QueryRequstParameter parameter, String namespace,
			Page<QueryResponseDTO> page) {
		try {
			if (page.getFirst() == 0) {
				int totalSize = (Integer) super.getSqlMapClientTemplate()
						.queryForObject(namespace + "_COUNT", parameter);
				page.setTotalCount(totalSize);
			}
			page.setResult((List<QueryResponseDTO>) getSqlMapClientTemplate()
					.queryForList(namespace, parameter, page.getFirst(),
							page.getPageSize()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

}
