/**
 * 
 */
package com.pay.acc.translog.dao;

import java.sql.SQLException;
import java.util.List;

import com.pay.acc.translog.model.TransLog;
import com.pay.inf.dao.BaseDAO;

/**
 * @author Administrator
 *
 */
public interface TransLogDAO extends BaseDAO<TransLog> {
	/**
	 * 根据流水号查询支付日志
	 * 
	 * @param serialNo
	 *            流水号
	 * @return
	 */
	public TransLog queryTransLogWithSerialNo(Long serialNo);

	/**
	 * 根据交易流水号 查询
	 * 
	 * @author Sunny Ying
	 * @param serialNo
	 *            交易流水号
	 * @throw null
	 * @return List<TransLog>
	 */
	public List<TransLog> queryTransLogBySerialNo(Long serialNo)
			throws SQLException;

	/**
	 * 更新 关联id
	 * 
	 * @author Sunny Ying
	 * @param transLog
	 * @throw null
	 * @return int
	 */
	public int editLinkId(TransLog transLog);
}
