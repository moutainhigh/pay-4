 /** @Description 
 * @project 	poss-reconcile
 * @file 		RefundIbatisDao.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-09-01		Sunsea_Li		Create 
*/
package com.pay.poss.refund.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.poss.base.exception.PlatformDaoException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.refund.dao.RefundDao;

public class RefundIbatisDao extends SqlMapClientDaoSupport implements
		RefundDao {

	/* (non-Javadoc)
	 * @see com.pay.poss.refund.dao.RefundDao#insertBatch(java.lang.String, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<Long> insertBatch(final String stmtId, final List<T> paramList)
			throws PlatformDaoException {
		try {
			return (List<Long>) getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				public Object doInSqlMapClient(final SqlMapExecutor executor) throws SQLException {
					executor.startBatch();
					List<Long> keys = new ArrayList<Long>();
					for (final T param : paramList) {
						Object id = executor.insert(stmtId, param);
						if (id == null) {
							keys.add(0L) ;
						} else {
							keys.add((Long) id);
						}
					}
					executor.executeBatch();
					return keys;
				}
			});
		} catch (Exception e) {
			logger.error("批量插入错误 [语句编号=" + stmtId + "]", e);
			throw new PlatformDaoException("批量插入错误 [语句编号=" + stmtId + "]", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
	}

}
