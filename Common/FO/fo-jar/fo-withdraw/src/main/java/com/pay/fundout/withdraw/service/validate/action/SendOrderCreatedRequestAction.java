/**
 *  File: SendOrderCreatedRequestAction.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-12      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.validate.action;

import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;
import com.pay.inf.rule.AbstractAction;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.JSonUtil;

/**
 * 发送订单生成消息
 * @author zliner
 */
public class SendOrderCreatedRequestAction extends AbstractAction {
	//通知消息服务
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
	 * 规则验证中规则执行发送订单生成消息逻辑
	 * @param validateBean        待验证规则对象 
	 * @throws Exception          验证中抛出运行时异常
	 */
	protected void doExecute(Object validateBean) throws Exception {
		WithdrawRequestDTO withdrawRequest = (WithdrawRequestDTO)validateBean;
		String jsonStr = JSonUtil.toJSonString(withdrawRequest.getSeqId());
		notifyFacadeService.sendRequest(buildNotify2QueueRequest(jsonStr));
	}
	//构建对象产生
	private Notify2QueueRequest buildNotify2QueueRequest(String jsonStr) {
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(jsonStr);
		return request;
	}
}
