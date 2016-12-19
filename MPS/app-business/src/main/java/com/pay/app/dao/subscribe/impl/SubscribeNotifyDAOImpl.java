package com.pay.app.dao.subscribe.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.app.dao.subscribe.SubscribeNotifyDAO;
import com.pay.app.model.SubscribeNotify;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author jim_chen
 * @version 2010-10-8
 */
@SuppressWarnings("unchecked")
public class SubscribeNotifyDAOImpl extends BaseDAOImpl implements
		SubscribeNotifyDAO {

	@Override
	public List<SubscribeNotify> querySubscribeNotifyList(
			Map<String, Object> paramMap) {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("queryAccSubscribeNotifyByMap"), paramMap);
	}

	@Override
	public int countSubscribeNotifyByMap(Map<String, Object> paramMap) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("countAccSubscribeNotifyByMap"), paramMap);
	}

	@Override
	public boolean batchInsert(final List<SubscribeNotify> subList) {
		try {
			getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					for (SubscribeNotify tmp : subList) {
						executor.insert(namespace.concat("create"), tmp);
					}
					executor.executeBatch();
					return null;
				}
			});
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean batchUpdateRdTx(final List<SubscribeNotify> subList) {
		try {
			getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					for (SubscribeNotify tmp : subList) {
						executor.update(namespace.concat("update"), tmp);
					}
					executor.executeBatch();
					return null;
				}
			});
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
