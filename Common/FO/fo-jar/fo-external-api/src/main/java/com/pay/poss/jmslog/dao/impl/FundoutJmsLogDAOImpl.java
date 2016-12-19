/**
 *  File: FundoutJmsLogDAOImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-16     darv      Changes
 *  
 *
 */
package com.pay.poss.jmslog.dao.impl;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.jmslog.dao.FundoutJmsLogDAO;
import com.pay.poss.jmslog.dto.FundoutJmsLogDTO;
import com.pay.poss.jmslog.model.FundoutJmsLog;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public class FundoutJmsLogDAOImpl extends BaseDAOImpl implements
		FundoutJmsLogDAO {

	@Override
	public void deleteJmsLog(Map params) {
		getSqlMapClientTemplate().delete(namespace.concat("deleteJmsLog"),
				params);
	}

	@Override
	public FundoutJmsLogDTO getJmsLogInfo(Map params) {
		return (FundoutJmsLogDTO) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getJmsLogInfo"), params);
	}

	@Override
	public Page<FundoutJmsLogDTO> getJmsLogList(Page<FundoutJmsLogDTO> page,
			Map params) {
		return findByQuery("getJmsLogList", page, params);
	}

	@Override
	public Long insertJmsLog(FundoutJmsLog jmsLog) {
		return (Long) create("insertJmsLog", jmsLog);
	}

	@Override
	public void updateJmsLogRetryTimes(Map params) {
		getSqlMapClientTemplate().update(
				namespace.concat("updateJmsLogRetryTimes"), params);
	}

	/**
	 * 在jms日志记录补发处理的时候，先判断工单是否已经生成，如果已经生成的话，则不重发消息，直接删除日志记录
	 * 
	 * @param orderId
	 * @return 存在记录数
	 */
	public int checkOrderIsExistForSendMessage(String orderId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("checkOrderIsExistForSendMessage"),
				Long.valueOf(orderId));
	}
}
