 /** @Description 
 * @project 	poss-withdraw
 * @file 		BatchSendAccountFacadeServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-11		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.service.accountmdp.impl;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.service.accountmdp.BatchSendAccountFacadeService;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;

/**
 * <p>发送JMS消息 批量记账</p>
 * @author Henry.Zeng
 * @since 2010-10-11
 * @see 
 */
public class BatchSendAccountFacadeServiceImpl implements
		BatchSendAccountFacadeService {
	private transient Log log = LogFactory.getLog(getClass());
	
	private NotifyFacadeService notifyFacadeService;
	//set注入
	public void setNotifyFacadeService(final NotifyFacadeService param) {
		this.notifyFacadeService = param;
	}
	/** JMS消息名 **/
	private String queueName;
	
	public void setQueueName(final String param) {
		this.queueName = param;
	}

	@Override
	public void sendMq2BatchAccount(HashMap<String, String> params) {
		log.info("【sendMq2BatchAccount.params:】"+params);
		notifyFacadeService.sendRequest(buildNotify2QueueRequest(params));
	}
	
	//构建对象产生
	Notify2QueueRequest buildNotify2QueueRequest(HashMap<String, String> params) {
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(params);
		return request;
	}
}
