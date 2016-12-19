package com.pay.acc.translog.dao;

import java.util.Map;

import com.pay.acc.translog.model.FlushesLog;
import com.pay.inf.dao.BaseDAO;

public interface FlushesLogDAO extends BaseDAO<FlushesLog> {

	
	/**更新冲正日志状态
	 * @param paramMap
	 */
	public boolean updateFlushesLog(Map<String,Object> paramMap);
	
	public int countFlushesStatus(Map<String,Object> paramMap);
	
	public boolean updateFlushesLogByStatus(Map<String,Object> paramMap);
	
    	
}
