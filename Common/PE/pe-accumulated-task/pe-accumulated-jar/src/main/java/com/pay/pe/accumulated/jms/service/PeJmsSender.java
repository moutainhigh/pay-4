package com.pay.pe.accumulated.jms.service;

import com.pay.pe.accumulated.jms.service.impl.NotifyTargetRequest;

public interface PeJmsSender {
	public void notifyRequest(NotifyTargetRequest targetRequest);
}
