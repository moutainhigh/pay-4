package com.pay.fundout.autofundout.custom.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.pay.fundout.autofundout.custom.dao.AutoTimeConfigDAO;
import com.pay.fundout.autofundout.custom.model.AutoTimeConfig;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class AutoTimeConfigDAOImpl extends BaseDAOImpl<AutoTimeConfig>
		implements AutoTimeConfigDAO {

	@Override
	public Long create(AutoTimeConfig config) {
		return (Long) this.create("create", config);
	}

	public boolean update(AutoTimeConfig config) {
		return getSqlMapClientTemplate()
				.update("autotimeconfig.update", config) == 0;
	}

	public long findId(long configId) {
		return (Long) getSqlMapClientTemplate().queryForObject(
				"autotimeconfig.findId", configId);
	}

	public List<AutoTimeConfig> findByConfigId(long configId) {
		return getSqlMapClientTemplate().queryForList(
				"autotimeconfig.findByConfigId", configId);
	}

	@Override
	public int delete(Long configId) {
		return getSqlMapClientTemplate().delete("autotimeconfig.delete",
				configId);
	}

	@Override
	public void create(List<AutoTimeConfig> configList) {
		try {
			this.getSqlMapClient().startBatch();
			for (AutoTimeConfig autoTimeConfig : configList) {
				this.create("create", autoTimeConfig);
			}
			getSqlMapClient().executeBatch();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
