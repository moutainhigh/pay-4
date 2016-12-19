package com.pay.poss.report.service.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dao.QueryCostingDao;
import com.pay.poss.report.dto.QueryBankKeyDTO;
import com.pay.poss.report.dto.QueryRequstParameter;
import com.pay.poss.report.dto.QueryResponseDTO;
import com.pay.poss.report.service.QueryCostServicre;

public class QueryCostServiceImpl implements QueryCostServicre {
	
	private QueryCostingDao queryCostingDao;

	public void setQueryCostingDao(QueryCostingDao queryCostingDao) {
		this.queryCostingDao = queryCostingDao;
	}
	
	/**
	 * 查询所有符合条件的数据
	 * @param parameter
	 * @return
	 */
	@Override
	public List<QueryResponseDTO> getAllQueryReport(
			QueryRequstParameter parameter) {
		if(parameter == null || parameter.getBusinessType() == null){
			return null;
		}
		if("1".equalsIgnoreCase(parameter.getBusinessType())){
			return queryCostingDao.queryCostingForFundOut(parameter);
		}else if("2".equalsIgnoreCase(parameter.getBusinessType())){
			return queryCostingDao.queryCostingForReFundOut(parameter);
		}else if("3".equalsIgnoreCase(parameter.getBusinessType())){
			return queryCostingDao.queryCostingForFundIn(parameter);
		}else if("4".equalsIgnoreCase(parameter.getBusinessType())){
			return queryCostingDao.queryCostingForReFundIn(parameter);
		}else if("5".equalsIgnoreCase(parameter.getBusinessType())){
			return queryCostingDao.queryDepositCosting(parameter);
		}
		return null;
	}
	
	/**
	 * 查询所有符合条件数据 并带分页信息
	 * @param parameter
	 * @param page
	 * @return
	 */
	@Override
	public Page<QueryResponseDTO> getAllQueryReport(
			QueryRequstParameter parameter, Page<QueryResponseDTO> page) {
		if(parameter == null || parameter.getBusinessType() == null){
			return null;
		}
		if("1".equalsIgnoreCase(parameter.getBusinessType())){
			return queryCostingDao.queryCostingFundOut(parameter, page);
		}else if("2".equalsIgnoreCase(parameter.getBusinessType())){//
			return queryCostingDao.queryCostingReFundOut(parameter, page);
		}else if("3".equalsIgnoreCase(parameter.getBusinessType())){
			return queryCostingDao.queryCostingFundIn(parameter, page);
		}else if("4".equalsIgnoreCase(parameter.getBusinessType())){
			return queryCostingDao.queryCostingReFundIn(parameter, page);
		}else if("5".equalsIgnoreCase(parameter.getBusinessType())){
			return queryCostingDao.queryDepositCosting(parameter, page);
		}
		return null;
	}

	@Override
	public List<QueryBankKeyDTO> getFundOutChennalBank() {
		return queryCostingDao.getFundOutChannelBank();
	}

	@Override
	public List<QueryBankKeyDTO> getFundInChennalBank() {
		return queryCostingDao.getFundInChannelBank();
	}


}
