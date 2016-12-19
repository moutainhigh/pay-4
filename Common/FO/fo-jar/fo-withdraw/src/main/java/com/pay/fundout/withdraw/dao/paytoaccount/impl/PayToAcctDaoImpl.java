/**
 * 
 */
package com.pay.fundout.withdraw.dao.paytoaccount.impl;

import com.pay.fundout.withdraw.dao.paytoaccount.PayToAcctDao;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author NEW
 * 
 */
public class PayToAcctDaoImpl extends BaseDAOImpl implements PayToAcctDao {

	@Override
	public Long getDayTotalAmount(Long memberCode) {
		Long amount = (Long) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getDayTotalAmount"), memberCode);
		amount = amount == null ? 0L : amount;
		return amount;
	}

	@Override
	public Long getMonthTotalAmount(Long memberCode) {
		Long amount = (Long) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getMonthTotalAmount"), memberCode);
		amount = amount == null ? 0L : amount;
		return amount;
	}

	@Override
	public Integer getMonthTotalCount(Long memberCode) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getMonthTotalCount"), memberCode);
	}

	@Override
	public Integer getDayTotalCount(Long memberCode) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getDayTotalCount"), memberCode);
	}

}
