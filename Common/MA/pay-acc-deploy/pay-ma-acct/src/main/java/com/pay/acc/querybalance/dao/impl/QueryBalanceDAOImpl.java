package com.pay.acc.querybalance.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.querybalance.dao.QueryBalanceDAO;
import com.pay.acc.querybalance.dto.MaSumDto;
import com.pay.acc.querybalance.model.QueryBalance;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class QueryBalanceDAOImpl extends BaseDAOImpl<QueryBalance> implements QueryBalanceDAO {



	@Override
	public List<QueryBalance> queryBalanceList(Map<String,Object> paramMap) {
		return getSqlMapClientTemplate().queryForList(getNamespace().concat("queryBalanceList"), paramMap);
	}

	@Override
	public MaSumDto queryHistoryBusinessSum(Map<String,Object> paramMap) {
		return (MaSumDto) getSqlMapClientTemplate().queryForObject(getNamespace().concat("queryHistoryBusinessSum"), paramMap);
	}

	@Override
	public BigDecimal queryBalance(Map<String,Object> paramMap) {
		Object obj=getSqlMapClientTemplate().queryForObject(getNamespace().concat("queryBalance"), paramMap);
		return obj==null?new BigDecimal(0.00):(BigDecimal)obj;
	}

	
	@Override
	public BigDecimal queryIncomeBalance(String acctCode,String type) {
		Map<String,Object> paramMap=new HashMap<String,Object>(2);
		paramMap.put("acctCode", acctCode);
		paramMap.put("type", type);
		return (BigDecimal)getSqlMapClientTemplate().queryForObject(getNamespace().concat("queryIncomeSum"), paramMap);
	}
	
	@Override
	public BigDecimal queryExpensesBalance(String acctCode,String type) {
		Map<String,Object> paramMap=new HashMap<String,Object>(2);
		paramMap.put("acctCode", acctCode);
		paramMap.put("type", type);
		return (BigDecimal)getSqlMapClientTemplate().queryForObject(getNamespace().concat("queryExpensesSum"), paramMap);
	}
	
	@Override
	public Integer queryIncomeCount(String acctCode,String type){
		Map<String,Object> paramMap=new HashMap<String,Object>(2);
		paramMap.put("acctCode", acctCode);
		paramMap.put("type", type);
		return (Integer)getSqlMapClientTemplate().queryForObject(getNamespace().concat("queryIncomeCount"), paramMap);
	}
	
	@Override
	public Integer queryExpensesCount(String acctCode,String type){
		Map<String,Object> paramMap=new HashMap<String,Object>(2);
		paramMap.put("acctCode", acctCode);
		paramMap.put("type", type);
		return (Integer)getSqlMapClientTemplate().queryForObject(getNamespace().concat("queryExpensesCount"), paramMap);
	}
}
