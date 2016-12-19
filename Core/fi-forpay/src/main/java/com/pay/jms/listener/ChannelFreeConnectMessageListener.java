package com.pay.jms.listener;

import com.pay.forpay.client.ChannelClientService;
import com.pay.jms.notification.request.NotifyRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.Map;

/**
 * Created by eva on 2016/8/18.
 */
public class ChannelFreeConnectMessageListener implements MessageListener {
    private final Log log = LogFactory.getLog(OrderCreditLiquidationListener.class);
    private ChannelClientService channelClientService;

    public void setChannelClientService(ChannelClientService channelClientService) {
        this.channelClientService = channelClientService;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            ObjectMessage om = (ObjectMessage) message;

            NotifyRequest request;
            try {
                request = (NotifyRequest) om.getObject();
                log.info("二级商户号自由切换,数据 request ="+ request);
                Map<String, String> requestData = request.getData();

                channelClientService.sendChannel(requestData);
                log.info("二级商户号自由切换,数据  request ="+ requestData);
            } catch (JMSException e) {
                log.info("二级商户号自由切换 ");
                log.error(e);
            }
        }
    }
}
