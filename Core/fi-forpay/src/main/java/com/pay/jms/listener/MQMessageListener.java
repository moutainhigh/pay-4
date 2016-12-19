package com.pay.jms.listener;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.jms.notification.request.NotifyRequest;

/**
 * 监听MQ消息
 * @author peiyu.yang
 *
 */
public class MQMessageListener implements MessageListener {

	private final Log log = LogFactory.getLog(MQMessageListener.class);
	private Map<String,MQMessageReceiver> procContext = 
			              new HashMap<String, MQMessageReceiver>();

	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			ObjectMessage om = (ObjectMessage) message;

			try {
				if (om.getObject() instanceof NotifyRequest) {
					NotifyRequest request = (NotifyRequest) om.getObject();
					MQMessageReceiver receiver = procContext.get(request.getType()+"");
					boolean result= receiver.process(request);
					if(result){
						log.info("操作已完成！！！ request = "+ request);
					}else{
						log.info("操作失败！！！ request = "+ request);
					}
				} else {
					log.warn("the request[" + om.getObject()
							+ "] isn't a valid notifyRequest.");
				}
			} catch (JMSException e) {
				log.error(e);
			}
		}
	}

	public void setProcContext(Map<String, MQMessageReceiver> procContext) {
		this.procContext = procContext;
	}
	
}
