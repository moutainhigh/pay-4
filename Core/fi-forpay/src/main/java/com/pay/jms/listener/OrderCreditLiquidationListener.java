package com.pay.jms.listener;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.forpay.client.SettleClientService;
import com.pay.jms.notification.request.NotifyRequest;

public class OrderCreditLiquidationListener implements MessageListener {
	private SettleClientService settleClientService;
	private final Log log = LogFactory.getLog(OrderCreditLiquidationListener.class);
	@Override
	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			ObjectMessage om = (ObjectMessage) message;
			
			NotifyRequest request;
			try {
				request = (NotifyRequest) om.getObject();
				log.info("开始订单授信清算处理 request ="+ request);
				Map<String, String> requestData = request.getData();
				
				settleClientService.settlementRnTx(requestData);		
				log.info("完成订单授信清算处理  request ="+ request);
			} catch (JMSException e) {
				log.info("订单授信清算处理失败 ");
				log.error(e);
			}
			
			
			
			
			
		}
	}


	public void setSettleClientService(SettleClientService settleClientService) {
		this.settleClientService = settleClientService;
	}

}
