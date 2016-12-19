/**
 * 
 */
package com.pay.acc.acct.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.acc.acct.dao.AcctDAO;
import com.pay.acc.acct.dto.MemberAcctDto;
import com.pay.acc.acct.model.Acct;
import com.pay.acc.acct.model.AcctWithdrawFee;
import com.pay.acc.acct.model.PseudoAcct;
import com.pay.acc.acct.model.VouchAcct;
import com.pay.acc.balancelog.dto.FrozenAmountDto;
import com.pay.acc.balancelog.dto.UnFrozenAmountDto;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author Administrator
 * 
 */
public class AcctDAOImpl extends BaseDAOImpl<Acct> implements AcctDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.acc.acct.dao.AcctDAO#queryAcctWithAcctCode(java.lang.Long)
	 */
	@Override
	public Acct queryAcctWithAcctCode(final String acctCode) {
		return (Acct) this.getSqlMapClientTemplate().queryForObject(
				this.namespace.concat("queryAccAcctWithAcctCode"), acctCode);

	}

	/**
	 * 更新余额，不做负数限制
	 * 
	 * @param acctCode
	 * @param amount
	 * @param debitAmount
	 * @param creditAmount
	 * @return
	 */
	@Override
	public boolean updateBalance(final String acctCode, final Long amount,
			final Long debitAmount, final Long creditAmount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("creditAmount", creditAmount);
		map.put("debitAmount", debitAmount);
		map.put("acctCode", acctCode);
		return super.updateByMap("updateBalance", map);
	}

	/**
	 * 更新余额，做负数限制
	 * 
	 * @param acctCode
	 * @param amount
	 * @param debitAmount
	 * @param creditAmount
	 * @return
	 */
	@Override
	public boolean updateBalanceCheckNegative(final String acctCode,
			final Long amount, final Long debitAmount, final Long creditAmount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("creditAmount", creditAmount);
		map.put("debitAmount", debitAmount);
		map.put("acctCode", acctCode);
		return super.updateByMap("updateBalanceCheckNegative", map);
	}

	@Override
	public boolean updateAcctCreditBalanceWithAcctCode(final Long amount,
			final Long creditAmount, final String acctCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("creditAmount", creditAmount);
		map.put("acctCode", acctCode);
		return this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateCreditBalanceWithAcctCode"), map) == 1;

	}

	@Override
	public boolean updateAcctDebitBalanceWithAcctCode(final Long amount,
			final Long debitAmount, final String acctCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("debitAmount", debitAmount);
		map.put("acctCode", acctCode);
		return this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateDebitBalanceWithAcctCode"), map) == 1;

	}

	@Override
	public boolean updateCreditReduceBalanceWithAcctCode(final Long amount,
			final Long creditAmount, final String acctCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("creditAmount", creditAmount);
		map.put("acctCode", acctCode);
		return this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateCreditReduceBalanceWithAcctCode"),
				map) == 1;
	}

	@Override
	public boolean updateDebitAddBalanceWithAcctCode(final Long amount,
			final Long debitAmount, final String acctCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("debitAmount", debitAmount);
		map.put("acctCode", acctCode);
		return this
				.getSqlMapClientTemplate()
				.update(this.namespace
						.concat("updateDebitAddBalanceWithAcctCode"),
						map) == 1;
	}

	public Acct queryAcctByMemberCodeAndAcctTypeId(final Long memberCode,
			final Integer acctTypeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("acctTypeId", acctTypeId);
		return (Acct) this.getSqlMapClientTemplate().queryForObject(
				this.namespace.concat("queryAcctByMemberCodeAndAcctTypeId"),
				map);
	}
	
	
	@Override
	public List<Acct> queryAcctsByMemberCode(final Long memberCode,
			final Integer acctTypeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("acctTypeId", acctTypeId);
		return this.getSqlMapClientTemplate().queryForList(
				this.namespace.concat("queryAcctByMemberCode"),map);
	}
	

	@Override
	public boolean updateAcctStatusWithAcctCode(final String acctCode, final Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acctCode", acctCode);
		map.put("status", status);
		return this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateAcctStatusWithAcctCode"), map) == 1;

	}

	@Override
	public boolean updateCreditNegativeBalanceWithAcctCode(final Long amount,
			final Long creditAmount, final String acctCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("creditAmount", creditAmount);
		map.put("acctCode", acctCode);
		return this
				.getSqlMapClientTemplate()
				.update(this.namespace
						.concat("updateCreditNegativeBalanceWithAcctCode"),
						map) == 1;
	}

	@Override
	public boolean updateDebitNegativeBalanceWithAcctCode(final Long amount,
			final Long debitAmount, final String acctCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("debitAmount", debitAmount);
		map.put("acctCode", acctCode);
		return this
				.getSqlMapClientTemplate()
				.update(this.namespace
						.concat("updateDebitNegativeBalanceWithAcctCode"),
						map) == 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.acct.dao.AcctDAO#updateAcctCreditBalanceWithVer(java.lang
	 * .Long, java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@Override
	public Integer updateAcctCreditBalanceWithVer(final Long amount,
			final Long creditAmount, final String acctCode, final Long ver) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("creditAmount", creditAmount);
		map.put("acctCode", acctCode);
		map.put("oldVer", ver);
		return this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateCreditBalanceWithVer"), map);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.acct.dao.AcctDAO#updateAcctDebitBalanceWithVer(java.lang.
	 * Long, java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@Override
	public Integer updateAcctDebitBalanceWithVer(final Long amount, final Long debitAmount,
			final String acctCode, final Long ver) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("debitAmount", debitAmount);
		map.put("acctCode", acctCode);
		map.put("oldVer", ver);
		return this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateDebitBalanceWithVer"), map);
	}

	@Override
	public List<Acct> queryAcctByMemberCode(final Long memberCode) {
		return this.getSqlMapClientTemplate().queryForList(
				this.namespace.concat("queryAccByMemberCode"), memberCode);

	}

	@Override
	public boolean updateFrozenAmountWithAcctCode(final String acctCode,
			final Long frozenAmount) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("frozenAmount", frozenAmount);
		map.put("acctCode", acctCode);
		return this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateFrozenAmountWithAcctCode"), map) == 1;

	}

	@Override
	public boolean updateUnFrozenAmountWithAcctCode(final String acctCode,
			final Long frozenAmount, final String orderId, final Long dealCode) {
		Map<String, Object> map = new HashMap<String, Object>(4);
		map.put("frozenAmount", frozenAmount);
		map.put("acctCode", acctCode);
		map.put("orderId", orderId);
		map.put("dealCode", dealCode);
		return this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateFrozenAmountWithAcctCode"), map) == 1;
	}

	@Override
	public int batchAddFrozen(final List<FrozenAmountDto> frozenAmountDtos)
			throws SQLException {
		SqlMapClient client = this.getSqlMapClient();
		String updateId = namespace.concat("unFrozenAmount");
		String queryAcct = namespace.concat("queryAccAcctWithAcctCode");
		client.startBatch();
		int v = 0;
		for (FrozenAmountDto dto : frozenAmountDtos) {
			client.update(updateId, dto);
			Acct acct = (Acct) client.queryForObject(queryAcct,
					dto.getAcctCode());
			v++;
		}
		client.executeBatch();
		return v;
	}

	@Override
	public int batchUnFrozen(final List<UnFrozenAmountDto> unFrozenAmountDtos)
			throws SQLException {
		SqlMapClient client = this.getSqlMapClient();
		String updateId = namespace.concat("unFrozenAmount");
		String queryAcct = namespace.concat("queryAccAcctWithAcctCode");
		String insertFrozenLog = namespace.concat("queryAccAcctWithAcctCode");
		client.startBatch();
		int v = 0;
		for (UnFrozenAmountDto dto : unFrozenAmountDtos) {
			client.update(updateId, dto);
			Acct acct = (Acct) client.queryForObject(queryAcct,
					dto.getAcctCode());
		}
		client.executeBatch();
		return v;
	}

	@Override
	public boolean addFrozenAmount(final FrozenAmountDto frozenAmountDto) {

		int count = getSqlMapClientTemplate().update(
				namespace.concat("addFrozenAmount"), frozenAmountDto);
		return count == 1;
	}

	@Override
	public boolean unFrozenAmount(final UnFrozenAmountDto unFrozenAmountDto) {
		int count = getSqlMapClientTemplate().update(
				namespace.concat("unFrozenAmount"), unFrozenAmountDto);
		return count == 1;
	}

	@Override
	public BigDecimal getHasFrozenAmountOfPoss(final long memberCode) {
		Long v = (Long) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getHasFrozenAmountOfPoss"), memberCode);
		return BigDecimal.valueOf(v);
	}

	@Override
	public List<MemberAcctDto> queryMemberAcctDto(final MemberAcctDto memberAcctDto) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("queryMemberAcctDto"), memberAcctDto);
	}

	@Override
	public boolean updateAcctWithdrawFee(final List<AcctWithdrawFee> acctWithdrawFees) {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {

			@Override
			public Object doInSqlMapClient(final SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				int batch = 0;
				for(AcctWithdrawFee acctWithdrawFee : acctWithdrawFees){
					executor.update(namespace.concat("updateAcctWithdrawFee"), acctWithdrawFee);
					batch++;
					if(batch == 100)
					{
						executor.executeBatch(); 
						batch=0;
					}
					executor.executeBatch();
				}
				return "";
			}
		}) ;
		return true;
	}

	@Override
	public List<Acct> queryAcctWithFeeByMemberCode(final Long memberCode) {
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("queryAcctWithFeeByMemberCode"), memberCode) ;
	}

	@Override
	public Acct queryAcctWithFeeByMemberCodeAndCurrencyCode(final Long memberCode,
			final String currencyCode) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("memberCode", memberCode) ;
		hMap.put("currencyCode", currencyCode) ;
		return (Acct) this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("queryAcctWithFeeByMemberCodeAndCurrencyCode"), hMap) ;
	}

	/* (non-Javadoc)
	 * @see com.pay.acc.acct.dao.AcctDAO#queryAcctByMemberCodeAndCurrency(java.lang.Long, java.lang.String)
	 */
	@Override
	public List<PseudoAcct> queryAcctByMemberCodeAndCurrency(final Long memberCode,
			final String currency) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("memberCode", memberCode) ;
		hMap.put("currency", currency) ;
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("queryAcctByMemberCodeAndCurrency"), hMap) ;
	}
	
	@Override
	public BigDecimal queryFrozenAmountByMemberCodeAndCurrency(final Long memberCode,
			final String currency,final String acctType,final String dealCode) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("memberCode", memberCode) ;
		hMap.put("currency", currency) ;
		hMap.put("acctType", acctType) ;
		hMap.put("dealCode", dealCode) ;
		return (BigDecimal)this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("queryFrozenAmountByMemberCodeAndCurrency"), hMap) ;
	}
	@Override
	public BigDecimal queryUnFrozenAmountByMemberCodeAndCurrency(final Long memberCode,
			final String currency,final String acctType,final String dealCode) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("memberCode", memberCode) ;
		hMap.put("currency", currency) ;
		hMap.put("acctType", acctType) ;
		hMap.put("dealCode", dealCode) ;
		return (BigDecimal)this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("queryUnFrozenAmountByMemberCodeAndCurrency"), hMap) ;
	}
	@Override
	public Boolean updateFrozenAmountByMemberCodeAndCurrency(Long memberCode, String currency,
			String acctType,BigDecimal frozenAmount) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("memberCode", memberCode);
		hMap.put("currency", currency);
		hMap.put("acctType", acctType);
		hMap.put("frozenAmount", frozenAmount);
		int count = getSqlMapClientTemplate().update(
				namespace.concat("updateFrozenAmountByMemberCodeAndCurrency"), hMap);
		return count == 1;
	}
	
	@Override
	public List<Acct> queryAcctCodeForAcctType(final String acctType) {
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("queryAcctCodeForAcctType"), acctType) ;
	}
	@Override
	public List<Acct> queryFrozenAmountByacctCode(List<Acct> accts,final String dealCode) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("accts", accts);
		hMap.put("dealCode", dealCode) ;
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("queryFrozenAmountByacctCode"), hMap) ;
	}
	@Override
	public List<VouchAcct> queryBasicRepairAmount() {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("queryBasicRepairAmount"), hMap) ;
	}
	@Override
	public List<VouchAcct> queryGuaranteeRepairAmount() {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("queryGuaranteeRepairAmount"), hMap) ;
	}
	@Override
	public List<Acct> queryUnFrozenAmountByacctCode(List<Acct> accts,final String dealCode) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("accts", accts);
		hMap.put("dealCode", dealCode) ;
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("queryUnFrozenAmountByacctCode"), hMap) ;
	}
	@Override
	public Boolean updateFrozenAmount(
			String acctCode,BigDecimal frozenAmount) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("acctCode", acctCode);
		hMap.put("frozenAmount", frozenAmount);
		int count = getSqlMapClientTemplate().update(
				namespace.concat("updateFrozenAmount"), hMap);
		return count == 1;
	}
	
	@Override
	public Integer updateFrozenAmountBatch(final List<Acct> paramList) {
		try {
			return (Integer) getSqlMapClientTemplate().execute(
					new SqlMapClientCallback() {
						public Object doInSqlMapClient(SqlMapExecutor executor)
								throws SQLException {
							executor.startBatch();
							Integer count = 0;
							for (Acct param : paramList) {
								Map<String, Object> hMap = new HashMap<String, Object>() ;
								hMap.put("acctCode", param.getAcctCode());
								hMap.put("frozenAmount", param.getFrozeAmount());
								count = executor.update(
										namespace.concat("updateFrozenAmount"), hMap);
							}
							executor.executeBatch();
							return count;
						}
					});
		} catch (Exception e) {
			logger.error("批量更新错误 [语句编号=updateFrozenAmount", e);
		}
		return 0;
	}
	/***
	 * 查询拒付罚款可配置的币种  delin
	 * @param memberCode
	 * @return
	 */
	@Override
	public List<Map> queryAcctAttribCurCode(Long memberCode) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("memberCode", memberCode) ;
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("queryAcctAttribCurCode"), hMap) ;
	}

	@Override
	public Map<String,Object> queryAcctBycurCodeAndmenberCode(Map<String, Object> params) {
		return (Map<String,Object>) this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("queryAcctBycurCodeAndmenberCode"), params) ;
	}

}


