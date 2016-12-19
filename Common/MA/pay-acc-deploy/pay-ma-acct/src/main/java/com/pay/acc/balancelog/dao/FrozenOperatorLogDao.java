/**
 * 
 */
package com.pay.acc.balancelog.dao;

import java.sql.SQLException;
import java.util.List;

import com.pay.acc.balancelog.model.FrozenOperatorLog;
import com.pay.inf.dao.BaseDAO;

/**
 * 冻结金额与解冻数据库操作 
 * @author ddr
 * @version 1  2012-6-6
 *
 *
 */
public interface FrozenOperatorLogDao extends BaseDAO<FrozenOperatorLog> {
	
	
	
	
	/**
	 * 批量新增冻结或解冻日志 
	 * @param frozenOperatorLogs
	 * @return
	 * @throws SQLException
	 */
	public List<Long> batchAddFrozenLog(List<FrozenOperatorLog> frozenOperatorLogs) throws SQLException;
	
	
}
