/**
 *  File: FundoutJmsLogServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-16     darv      Changes
 *  
 *
 */
package com.pay.poss.jmslog.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.jmslog.dao.FundoutJmsLogDAO;
import com.pay.poss.jmslog.dto.FundoutJmsLogDTO;
import com.pay.poss.jmslog.model.FundoutJmsLog;
import com.pay.poss.jmslog.service.FundoutJmsLogService;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public class FundoutJmsLogServiceImpl implements FundoutJmsLogService {
	private FundoutJmsLogDAO fundoutJmsLogDAO;
	private NotifyFacadeService notifyFacadeService;
	private String queueName; //queue名称
	
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	public void setFundoutJmsLogDAO(FundoutJmsLogDAO fundoutJmsLogDAO) {
		this.fundoutJmsLogDAO = fundoutJmsLogDAO;
	}

	@Override
	public void deleteJmsLog(Map params) {
		fundoutJmsLogDAO.deleteJmsLog(params);
	}

	@Override
	public FundoutJmsLogDTO getJmsLogInfo(Map params) {
		return fundoutJmsLogDAO.getJmsLogInfo(params);
	}

	@Override
	public Page<FundoutJmsLogDTO> getJmsLogList(Page<FundoutJmsLogDTO> page, Map params) {
		return fundoutJmsLogDAO.getJmsLogList(page, params);
	}

	@Override
	public Long insertJmsLog(FundoutJmsLog jmsLog) {
		return fundoutJmsLogDAO.insertJmsLog(jmsLog);
	}

	@Override
	public void updateJmsLogRetryTimes(Map params) {
		fundoutJmsLogDAO.updateJmsLogRetryTimes(params);
	}

	/**
	 * jms日志记录补发消息功能和异常数据处理功能针对工单的补单处理会重复。
	 * 在jms日志记录补发处理的时候，先判断工单是否已经生成，如果已经生成的话，则不重发消息，直接删除日志记录
	 * author: Volcano.Wu
	 * @param params
	 */
	public void redoSendMessage(Map<String,Object> params){
		FundoutJmsLogDTO jmsLog = fundoutJmsLogDAO.getJmsLogInfo(params);
		
		if(queueName.equals(jmsLog.getQueueName())){
			String orderId = jmsLog.getBusiInfo();
			if(fundoutJmsLogDAO.checkOrderIsExistForSendMessage(orderId) > 0){
				fundoutJmsLogDAO.deleteJmsLog(params);
			}
		}
		sendMessage(jmsLog);
		params.put("updateTime", new Date());
		fundoutJmsLogDAO.updateJmsLogRetryTimes(params);
			
	}
	
	/**
	 * 发送消息
	 * @param jmsLog
	 */
	private void sendMessage(FundoutJmsLogDTO jmsLog){
		
		try{
			ByteArrayInputStream bis = new ByteArrayInputStream(jmsLog.getTransObject());
			ObjectInputStream ois = new ObjectInputStream(bis);
			Notify2QueueRequest req = (Notify2QueueRequest)ois.readObject();
			bis.close();
			ois.close();
			notifyFacadeService.sendRequest(req);
		}catch(Exception e){
			LogUtil.error(FundoutJmsLogServiceImpl.class, "jms日志记录补发消息", OPSTATUS.EXCEPTION, String.valueOf(jmsLog.getSequenceId()), "", e.getMessage(), "", "");
		}
	}
}
