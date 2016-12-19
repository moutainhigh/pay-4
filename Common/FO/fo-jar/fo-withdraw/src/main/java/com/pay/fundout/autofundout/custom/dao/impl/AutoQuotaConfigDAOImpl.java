package com.pay.fundout.autofundout.custom.dao.impl;

import com.pay.fundout.autofundout.custom.dao.AutoQuotaConfigDAO;
import com.pay.fundout.autofundout.custom.model.AutoFundoutConfig;
import com.pay.fundout.autofundout.custom.model.AutoQuotaConfig;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class AutoQuotaConfigDAOImpl extends BaseDAOImpl<AutoQuotaConfig> implements AutoQuotaConfigDAO {

	@Override
	public Long create(AutoQuotaConfig config) {
		return (Long) this.create("create", config);
	}

	public boolean update(AutoFundoutConfig config) {
		return getSqlMapClientTemplate().update("autoquotaconfig.update", config) == 0;
	}

	public long findId(long configId) {
		return (Long) getSqlMapClientTemplate().queryForObject("autoquotaconfig.findId", configId);
	}

	public AutoQuotaConfig findById(long configId) {
		return (AutoQuotaConfig) getSqlMapClientTemplate().queryForObject("autoquotaconfig.findById", configId);
	}

	@Override
	public int delete(Long configId) {
		return getSqlMapClientTemplate().delete("autoquotaconfig.delete", configId);
	}

}
