 /** @Description 
 * @project 	poss-refund
 * @file 		BaseServiceImpl.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date			Author			Changes
 * 2010-9-6		sunsea.li		Create 
*/
package com.pay.poss.refund.service.impl;

import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;

public class BaseServiceImpl {
	
	//构建消息对象
	protected Notify2QueueRequest buildNotify2QueueRequest(String jsonStr,String queueName) {
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(jsonStr);
		return request;
	}
}
