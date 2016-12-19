/**
 *  File: BatchpaymentApiJmsQueueListener.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-12-26   Sany        Create
 *
 */
package com.pay.fundout.mdp.withdraw.api;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.api.service.notify.MerchantNotifyService;
import com.pay.fo.order.model.batchpayment.BatchPaymentOrder;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.util.JSonUtil;

/**
 * 出款API消息监听
 */
public class BatchpaymentApiJmsQueueListener implements MessageListener{
	
	private Log log = LogFactory.getLog(getClass());
	
	private MerchantNotifyService merchantNotifyService;
	
	public void onMessage(Message message){
		if (message instanceof ActiveMQObjectMessage) {
			ActiveMQObjectMessage msg = (ActiveMQObjectMessage) message;
			Notify2QueueRequest request;
			try {
				request = (Notify2QueueRequest) msg.getObject();
				log.info("出款API通知商户消息名：" + request.getQueueName());
				Object obj = JSonUtil.toObject((String)request.getTargetObject(),BatchPaymentOrder.class);
				BatchPaymentOrder order = (BatchPaymentOrder)obj;
				merchantNotifyService.notifyMerchant(order);
			} catch (Exception e) {
				log.error("出款API消息监听出错!",e);
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * @param merchantNotifyService the merchantNotifyService to set
	 */
	public void setMerchantNotifyService(
			MerchantNotifyService merchantNotifyService) {
		this.merchantNotifyService = merchantNotifyService;
	}
}
