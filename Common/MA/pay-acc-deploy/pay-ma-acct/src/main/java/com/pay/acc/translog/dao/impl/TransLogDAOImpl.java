/**
 * 
 */
package com.pay.acc.translog.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.pay.acc.translog.dao.TransLogDAO;
import com.pay.acc.translog.model.TransLog;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author Administrator
 * 
 */
public class TransLogDAOImpl extends BaseDAOImpl<TransLog> implements
		TransLogDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.translog.dao.TransLogDAO#queryTransLogWithSerialNo(java.lang
	 * .Long)
	 */
	@Override
	public TransLog queryTransLogWithSerialNo(Long serialNo) {

		return (TransLog) this.getSqlMapClientTemplate().queryForObject(
				this.namespace.concat("queryTransLogWithSerialNo"), serialNo);
	}

	public List<TransLog> queryTransLogBySerialNo(Long serialNo)
			throws SQLException {
		return (List<TransLog>) this.getSqlMapClientTemplate().queryForList(
				namespace.concat("getTransLogBySerialNo"), serialNo);
	}

	public int editLinkId(TransLog transLog) {
		return this.getSqlMapClientTemplate().update(
				namespace.concat("updateLinkId"), transLog);
	}
}
