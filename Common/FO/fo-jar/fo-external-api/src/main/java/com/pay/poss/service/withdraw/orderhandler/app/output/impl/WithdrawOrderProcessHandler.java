/**
 *  File: WithdrawOrderProcessHandler.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-13      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.withdraw.orderhandler.app.output.impl;

import java.util.HashMap;

import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.poss.service.withdraw.orderhandler.app.output.OrderProcessHandler;

/**
 * 提现订单后处理服务
 * @author zliner
 *
 */
public class WithdrawOrderProcessHandler implements OrderProcessHandler {
	//通知封装服务 
	private NotifyFacadeService notifyFacadeService;
	//队列名称
	private String queueName;
	//set注入
	public void setQueueName(final String param) {
		this.queueName = param;
	}
	//set注入
	public void setNotifyFacadeService(final NotifyFacadeService param) {
		this.notifyFacadeService = param;
	}
	/**
	 * 处理订单
	 * @param paramMap            订单后处理
	 */
	public void processHandler(HashMap<String,String> paramMap) {
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(paramMap);
		request.setIsOuter(1);
		notifyFacadeService.sendRequest(request);
	}

}
