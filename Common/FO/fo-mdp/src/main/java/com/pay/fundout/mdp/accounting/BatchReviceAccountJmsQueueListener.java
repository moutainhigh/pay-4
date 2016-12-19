/** @Description 
 * @project 	poss-withdraw
 * @file 		BatchReviceAccountFacadeServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-11		Henry.Zeng			Create 
 */
package com.pay.fundout.mdp.accounting;

import java.util.HashMap;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.service.fileservice.HandBatchFileService;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;

/**
 * <p>
 * 		监听MQ信息批量记账
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-10-11
 * @see
 */
public class BatchReviceAccountJmsQueueListener implements MessageListener {

	private transient Log log = LogFactory.getLog(getClass());
	
	private transient HandBatchFileService handBatchFileService;
	//注入操作记账的服务
	public void setHandBatchFileService(
			final HandBatchFileService param) {
		this.handBatchFileService = param;
	}
	
	@SuppressWarnings("unchecked")
	public void onMessage(Message message) {
		try {
			if(log.isDebugEnabled()){
				log.debug(this.getClass().getName()+"message:"+message);
			}
			log.debug(this.getClass().getName()+"message:"+message.getClass());
			if (message instanceof ActiveMQObjectMessage) {
				ActiveMQObjectMessage msg = (ActiveMQObjectMessage) message;
				if(log.isDebugEnabled()){
					log.debug(this.getClass().getName()+"msg:"+msg);
				}
				Notify2QueueRequest request =  (Notify2QueueRequest) msg.getObject();
				Object obj = request.getTargetObject() ;
				if(	obj instanceof HashMap<?, ?>){
					HashMap<String, String> params =  (HashMap<String, String>)obj ;
					if(log.isInfoEnabled()){
						log.info("revice accounting【 params】: "+params);
					}
					//批量记账 WORKORDERID=12345678 * ORDERID=12345678 * ISSUCC=1(1:成功,0：失败)
					if(!handBatchFileService.importConfirmRdTx(params)){
						//TODO 记账失败 需要记录日志表
						log.error("记账失败,需保存到报警表中..."+params);
					}
				}
			}
		} catch (Exception ex) {
			log.error("batchReviceAccountJmsQueueListener in "+ com.pay.util.DateUtil.formatDateTime("yyyy-MM-dd", new java.util.Date())+ex.getMessage(),ex);
			throw new RuntimeException(ex);
		}
	}

}
