package com.pay.base.dao.queryhistory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.pay.app.facade.dto.MaSumDto;
import com.pay.base.model.QueryCorpBalance;

/**
 * 
 * @author jerry_jin
 *
 */
public interface QueryBalanceDAO {
	
	public BigDecimal queryBalance(Map<String,Object> paramMap);

	public List<QueryCorpBalance> queryBalanceList(Map<String, Object> paramMap);
	
	public MaSumDto queryHistoryBusinessSum(Map<String, Object> paramMap);
	
	public BigDecimal queryIncomeBalance(String acctCode,String type);
	
	public BigDecimal queryExpensesBalance(String acctCode,String type);
	
	public Integer queryIncomeCount(String acctCode,String type);
	
	public Integer queryExpensesCount(String acctCode,String type);

	/**
	 * 余额币种的查询
	 * @param paramMap
	 * @return
	 */
	public String queryBalanceCurCode(Map<String, Object> paramMap);
	
}
