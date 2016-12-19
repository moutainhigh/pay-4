/**
 *  File: NotifyFacadeService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-9      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.withdraw.inf.output;

import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;

/**
 * 通知封装服务 
 * 所有发送消息及邮件或短信均做异常捕获处理
 * @author zliner
 *
 */
public interface NotifyFacadeService {
	/**
	 * 发送消息处理  
	 * @param request             发送消息内容
	 */
	void sendRequest(Notify2QueueRequest request);
	 
	/** 
	 * 发送邮件或短信通知
	 * @param request             发送消息内容
	 */
	void notifyRequest(NotifyTargetRequest request);
	/** 
	 * 发送邮件或短信通知
	 * @param request             发送消息内容
	 */
	void notifyRequest2(NotifyTargetRequest request);
	
}
