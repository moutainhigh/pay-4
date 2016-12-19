package com.pay.fundout.autofundout.custom.dao.impl;

import java.util.List;

import com.pay.fundout.autofundout.custom.dao.AutoFundoutConfigDAO;
import com.pay.fundout.autofundout.custom.model.AutoFundoutConfig;
import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.inf.dao.impl.BaseDAOImpl;

@SuppressWarnings("unchecked")
public class AutoFundoutConfigDAOImpl extends BaseDAOImpl<AutoFundoutConfig>
		implements AutoFundoutConfigDAO {

	@Override
	public Long create(AutoFundoutConfig config) {
		return (Long) this.create("create", config);
	}

	@Override
	public boolean disable(Long id) {
		return getSqlMapClientTemplate()
				.update("autofundoutconfig.disable", id) == 0;
	}

	@Override
	public boolean update(AutoFundoutConfig config) {
		return getSqlMapClientTemplate().update("autofundoutconfig.update",
				config) == 0;
	}

	@Override
	public AutoFundoutConfig findById(Long memberCode) {
		return (AutoFundoutConfig) getSqlMapClientTemplate().queryForObject(
				"autofundoutconfig.findByMemberCode", memberCode);
	}

	@Override
	public Integer findType(long memberCode) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"autofundoutconfig.findType", memberCode);
	}

	@Override
	public List<AutoFundoutResult> queryAutoQuotaFundoutResult() {
		return getSqlMapClientTemplate().queryForList(
				"autofundoutresult.queryAutoQuotaFundoutResult");
	}

	@Override
	public List<AutoFundoutResult> queryAutoTimeFundoutResult() {
		return getSqlMapClientTemplate().queryForList(
				"autofundoutresult.queryAutoTimeFundoutResult");
	}

	@Override
	public List<AutoFundoutResult> queryAutoMoreTimeFundoutResult() {
		return getSqlMapClientTemplate().queryForList(
				"autofundoutresult.queryAutoMoreTimeFundoutResult");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.autofundout.custom.dao.AutoFundoutConfigDAO#
	 * findByMemberCodeAndBankCard(java.lang.Long, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer findByMemberCodeAndBankCard(
			AutoFundoutConfig autoFundoutConfig) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"autofundoutconfig.findByMemberCodeAndBankCard",
				autoFundoutConfig);
	}

}
