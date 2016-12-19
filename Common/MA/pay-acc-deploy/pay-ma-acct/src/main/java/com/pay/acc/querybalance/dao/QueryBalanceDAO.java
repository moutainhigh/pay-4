package com.pay.acc.querybalance.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.pay.acc.querybalance.dto.MaSumDto;
import com.pay.acc.querybalance.model.QueryBalance;


/**
 * 
 * @author jerry_jin
 *
 */
public interface QueryBalanceDAO {
	
	public BigDecimal queryBalance(Map<String,Object> paramMap);

	public List<QueryBalance> queryBalanceList(Map<String, Object> paramMap);
	
	public MaSumDto queryHistoryBusinessSum(Map<String, Object> paramMap);
	
	public BigDecimal queryIncomeBalance(String acctCode,String type);
	
	public BigDecimal queryExpensesBalance(String acctCode,String type);
	
	public Integer queryIncomeCount(String acctCode,String type);
	
	public Integer queryExpensesCount(String acctCode,String type);
	
}
