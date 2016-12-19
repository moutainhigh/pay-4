 /** @Description 
 * @project 	fo-withdraw
 * @file 		OrderPorcessorJmsQueueListener.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-12-10		Henry.Zeng			Create 
*/
package com.pay.fundout.mdp.withdraw.autofundout;

import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;

import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.fundout.autofundout.processor.service.AutoFundoutService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-12-10
 * @see 
 */
public class OrderProcessorJmsQueueListener implements MessageListener {
	
	private Map<String, AutoFundoutService> atuoFundoutMap ;

	
	public void setAtuoFundoutMap(Map<String, AutoFundoutService> atuoFundoutMap) {
		this.atuoFundoutMap = atuoFundoutMap;
	}

	@Override
	public void onMessage(Message message) {
		try {
			LogUtil.info(this.getClass(), "企业自动提现收到MQ消息", OPSTATUS.SUCCESS, "", "");
			if (message instanceof ActiveMQObjectMessage) {
				ActiveMQObjectMessage msg = (ActiveMQObjectMessage) message;
				Notify2QueueRequest request =  (Notify2QueueRequest) msg.getObject();
				Object obj = request.getTargetObject() ;
				if(	obj instanceof AutoFundoutResult){
					AutoFundoutResult result = (AutoFundoutResult)obj;
					Integer autoType = result.getAutoType();
					
					AutoFundoutService autoFundoutService = atuoFundoutMap.get(autoType.toString());
					
					//自动提现
					autoFundoutService.processAutoFundout(result);
					
				}
			}
		} catch (Exception ex) {
			LogUtil.error(this.getClass(), "企业自动提现收到MQ消息异常", OPSTATUS.EXCEPTION, "", "", ex.getMessage(), "001", ex.getMessage());
		}
	}

}
