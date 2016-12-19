/**
 * 
 */
package com.pay.acc.deal.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.deal.dao.BalanceEntryDAO;
import com.pay.acc.deal.model.BalanceEntry;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author Administrator
 * 
 */
public class BalanceEntryDAOImpl extends BaseDAOImpl<BalanceEntry> implements
		BalanceEntryDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.deal.dao.BalanceEntryDAO#queryBalanceEntryForTransAmountNsTx
	 * (java.lang.String, java.lang.Long, java.lang.Integer)
	 */
	public Long queryBalanceEntryForTransAmount(String sqlId,
			Map<String, Object> paraMap) {
		Long transAmounts = (Long) getSqlMapClientTemplate().queryForObject(
				this.namespace.concat(sqlId), paraMap);
		return transAmounts;
	}

	@Override
	public Long sumTwoWeekAddValue(String acctCode, Long date) {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("acctCode", acctCode);
		paramMap.put("date", date);
		Long addAmount = (Long) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("sumTwoWeekAddValue"), paramMap);
		if (null == addAmount) {
			addAmount = 0L;
		}
		return addAmount;
	}

	@Override
	public Long sumTwoWeekPayerMinusValue(String acctCode, Long date) {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("acctCode", acctCode);
		paramMap.put("date", date);
		Long minusAmount = (Long) this.getSqlMapClientTemplate()
				.queryForObject(namespace.concat("sumTwoWeekPayerMinusValue"),
						paramMap);
		if (null == minusAmount) {
			minusAmount = 0L;
		}
		return minusAmount;

	}

	@Override
	public Long sumTwoWeekPayeeMinusValue(String acctCode, Long date) {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("acctCode", acctCode);
		paramMap.put("date", date);
		Long minusAmount = (Long) this.getSqlMapClientTemplate()
				.queryForObject(namespace.concat("sumTwoWeekPayeeMinusValue"),
						paramMap);
		if (null == minusAmount) {
			minusAmount = 0L;
		}
		return minusAmount;
	}

	@Override
	public List<BalanceEntry> queryCrdrSumByAcctCode(String acctCode,
			String startAt, String endAt) {
		Map<String, Object> paramMap = new HashMap<String, Object>(3);
		paramMap.put("acctCode", acctCode);
		paramMap.put("startAt", startAt);
		paramMap.put("endAt", endAt);
		List<BalanceEntry> beList = this.getSqlMapClientTemplate()
				.queryForList(namespace.concat("queryCrdrSumByAcctCode"),
						paramMap);
		return beList;
	}

	@Override
	public Long queryWithdrawalBanalce(String acctCode, Long date) {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("acctCode", acctCode);
		paramMap.put("date", date);
		Long withdrawalBanalce = (Long) this.getSqlMapClientTemplate()
				.queryForObject(namespace.concat("selectWithdrawalBanalce"),
						paramMap);
		if (null == withdrawalBanalce) {
			withdrawalBanalce = 0L;
		}
		return withdrawalBanalce;
	}

	@Override
	public Long sumTwoWeekMinusValue(String acctCode, Long date) {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("acctCode", acctCode);
		paramMap.put("date", date);
		Long minusAmount = (Long) this.getSqlMapClientTemplate()
				.queryForObject(namespace.concat("sumTwoWeekMinusValue"),
						paramMap);
		if (null == minusAmount) {
			minusAmount = 0L;
		}
		return minusAmount;
	}

	@Override
	public List<BalanceEntry> queryBalanceEntryBySerialNo(String orderId,
			Integer dealCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("serialNo", orderId);
		paramMap.put("dealCode", dealCode);
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("queryBalanceEntryBySerialNo"), paramMap);
	}

	@Override
	public Long sumTwoWeekWithdrawRefund(String acctCode, Long date) {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("acctCode", acctCode);
		paramMap.put("date", date);
		Object withdrawRefund = this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("sumTwoWeekWithdrawRefund"), paramMap);
		if (withdrawRefund == null) {
			return new Long(0);
		}
		return (Long) withdrawRefund;
	}

	@Override
	public Long queryBalanceByAcctCode(String acctCode, String endAt) {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("acctCode", acctCode);
		paramMap.put("endAt", endAt);
		Object obj = this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("queryBalanceByAcctCode"), paramMap);
		if (obj != null)
			return (Long) obj;
		return 0L;

	}

	@Override
	public BalanceEntry selectBalanceByAcctCodeAndDate(String acctCode,
			Date date) {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("acctCode", acctCode);
		paramMap.put("date", date);
		List<BalanceEntry> list = this.getSqlMapClientTemplate().queryForList(
				namespace.concat("selectBalanceByAcctCodeAndDate"), paramMap);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
