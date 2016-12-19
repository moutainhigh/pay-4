/**
 *  File: FundoutJmsLogDAO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-16     darv      Changes
 *  
 *
 */
package com.pay.poss.jmslog.dao;

import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.jmslog.dto.FundoutJmsLogDTO;
import com.pay.poss.jmslog.model.FundoutJmsLog;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public interface FundoutJmsLogDAO extends BaseDAO {
	/**
	 * 插入日志
	 * 
	 * @param jmsLog
	 * @return
	 */
	public Long insertJmsLog(FundoutJmsLog jmsLog);

	/**
	 * 查询日志列表
	 * 
	 * @param page
	 * @param params
	 * @return
	 */
	public Page<FundoutJmsLogDTO> getJmsLogList(Page<FundoutJmsLogDTO> page,
			Map params);

	/**
	 * 查询单个日志
	 * 
	 * @param params
	 * @return
	 */
	public FundoutJmsLogDTO getJmsLogInfo(Map params);

	/**
	 * 删除日志
	 * 
	 * @param params
	 */
	public void deleteJmsLog(Map params);

	/**
	 * 更新补单次数
	 * 
	 * @param params
	 */
	public void updateJmsLogRetryTimes(Map params);

	/**
	 * 在jms日志记录补发处理的时候，先判断工单是否已经生成，如果已经生成的话，则不重发消息，直接删除日志记录
	 * 
	 * @param orderId
	 * @return 存在记录数
	 */
	public int checkOrderIsExistForSendMessage(String orderId);
}
