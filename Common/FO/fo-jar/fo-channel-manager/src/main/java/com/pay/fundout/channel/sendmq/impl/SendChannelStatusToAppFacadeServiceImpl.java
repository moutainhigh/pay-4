package com.pay.fundout.channel.sendmq.impl;

import java.util.HashMap;

import com.pay.fundout.channel.sendmq.SendChannelStatusToAppFacadeService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;

/**
 * 
 * <p>出款渠道对应的目的行维护发送MQ通知APP服务</p>
 * @author Volcano.Wu
 * @since 2010-12-10
 * @see
 */
public class SendChannelStatusToAppFacadeServiceImpl implements SendChannelStatusToAppFacadeService{

	private NotifyFacadeService notifyFacadeService;
	//set注入
	public void setNotifyFacadeService(final NotifyFacadeService param) {
		this.notifyFacadeService = param;
	}
	/** JMS消息名 **/
	private String queueName;
	//set注入
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	@Override
	public void sendMq2App(HashMap<String, String> params) {
//		LogUtil.info(this.getClass(), "出款渠道对应的目的行维护发送MQ通知APP服务", OPSTATUS.SUCCESS, params.get("bankCode"), "");
//		Notify2QueueRequest queueRequest = new Notify2QueueRequest();
//		queueRequest.setQueueName(queueName);
//		queueRequest.setTargetObject(params);
//		queueRequest.setIsOuter(1);
//		notifyFacadeService.sendRequest(queueRequest);
	}
}
