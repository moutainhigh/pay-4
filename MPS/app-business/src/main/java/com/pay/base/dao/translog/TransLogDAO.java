/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-business 
 *@CreateDate 下午05:57:17 2010-12-6
 */
package com.pay.base.dao.translog;


import java.sql.SQLException;
import java.util.List;

import com.pay.base.model.TransLog;
import com.pay.inf.dao.BaseDAO;


/**
 * @author Sunny Ying
 * @description TODO
 * @version 下午05:57:17 & 2010-12-6
 */

public interface TransLogDAO extends BaseDAO<TransLog>{

	/**
	 * 
	 * @author Sunny Ying
	 * @param transLog
	 * @throw null
	 * @return Long
	 */
	public Long createTransLong(TransLog transLog);
	
	/**
	 * 根据交易流水号 查询
	 * @author Sunny Ying
	 * @param serialNo 交易流水号
	 * @throw null
	 * @return List<TransLog>
	 */
	public List<TransLog> queryTransLogBySerialNo(Long serialNo)throws SQLException;
	
	/**
	 * 更新 关联id
	 * @author Sunny Ying
	 * @param transLog
	 * @throw null
	 * @return int
	 */
	public int editLinkId(TransLog transLog);
}
