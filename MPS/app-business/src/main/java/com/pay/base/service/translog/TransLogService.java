/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-business 
 *@CreateDate 上午09:21:12 2010-12-7
 */
package com.pay.base.service.translog;

import java.sql.SQLException;
import java.util.List;

import com.pay.base.model.TransLog;


/**
 * @author Sunny Ying
 * @description TODO
 * @version 上午09:21:12 & 2010-12-7
 */

public interface TransLogService {
	/**
	 * 
	 * @author Sunny Ying
	 * @param transLog
	 * @throw null
	 * @return Long
	 */
	public Long createTransLog(TransLog transLog);
	
	/**
	 * 根据交易流水号 查询
	 * @author Sunny Ying
	 * @param serialNo 交易流水号
	 * @throw NumberFormatException, SQLException
	 * @return List<TransLog>
	 */
	public List<TransLog> queryTransLogBySerialNo(String serialNo)throws NumberFormatException, SQLException;
	
	/**
	 * 更新 TransLog 的关联id
	 * @author Sunny Ying
	 * @param transLog
	 * @param baseId
	 * @throw null
	 * @return int
	 */
	public int editTransLogForLinkId(List<TransLog> transLogList,Long baseId);
}
