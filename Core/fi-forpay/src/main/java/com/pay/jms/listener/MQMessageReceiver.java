package com.pay.jms.listener;

import com.pay.jms.notification.request.NotifyRequest;

public interface MQMessageReceiver {
     boolean process(NotifyRequest notifyRequest);
}
