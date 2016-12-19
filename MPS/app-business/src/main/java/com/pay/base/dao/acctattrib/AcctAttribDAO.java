package com.pay.base.dao.acctattrib;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.pay.base.model.AcctAttrib;
import com.pay.base.model.PseudoAcct;
import com.pay.inf.dao.BaseDAO;


public interface AcctAttribDAO extends BaseDAO<AcctAttrib>{

	public int updateAcctAttribPwd(Map<String, Object> map);
	
	
	public abstract Long createAcctAttrib(AcctAttrib acctAttrib);
	
	/**
	 * 多条数据批量提交插入
	 * @author Sunny Ying
	 * @param acctAttribList
	 * @throws DataAccessException
	 * @return void
	 */
	public abstract void createBatchAcctAttrib(final List<AcctAttrib> acctAttribList)throws DataAccessException;
		

	public AcctAttrib checkPaymentPwd(Map<String, String> map);
	
	
	/**
	 * 通过会员号查询账户的货币类型
	 * @param currency
	 * @return
	 */
	List<PseudoAcct> queryAcctCurrencyByMemberCode(Long memberCode) ;
}