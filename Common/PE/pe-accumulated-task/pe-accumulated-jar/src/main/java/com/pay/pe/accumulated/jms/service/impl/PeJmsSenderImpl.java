package com.pay.pe.accumulated.jms.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.jms.notification.request.EmailNotifyRequest;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.notification.request.NotifyRequest;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.notification.request.SMSNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.pe.accumulated.jms.service.PeJmsSender;
import com.pay.util.JSonUtil;
/**
 * 消息发送
 * @author 
 *
 */
public class PeJmsSenderImpl implements PeJmsSender {
	private Log logger = LogFactory.getLog(PeJmsSenderImpl.class);
	//消息发送服务 
	private JmsSender jmsSender;
	//set注入
	public void setJmsSender(final JmsSender param) {
		this.jmsSender = param;
	}
	
	/** 
	 * 发送邮件或短信通知
	 * @param targetRequest             发送消息内容
	 */
	@Override
	public void notifyRequest(NotifyTargetRequest targetRequest) {
		try {
			jmsSender.send(buildNotifyRequest(targetRequest));
		}catch(Exception e) {
			logger.error("targetObject's jasonStr: " + JSonUtil.toJSonString(targetRequest));
			logger.error("notify target queue is error",e);
		}
	}
	
	//构建不同的消息对象
	private NotifyRequest buildNotifyRequest(NotifyTargetRequest targetRequest) {
		if(RequestType.EMAIL.value() == targetRequest.getNotifyType()) {
			EmailNotifyRequest emailReq = new EmailNotifyRequest();
			BeanUtils.copyProperties(targetRequest, emailReq);
			return emailReq;
		}else if(RequestType.SMS.value() == targetRequest.getNotifyType()) {
			SMSNotifyRequest snsReq = new SMSNotifyRequest();
			BeanUtils.copyProperties(targetRequest, snsReq);
			return snsReq;
		}else if (RequestType.HTTP.value() == targetRequest.getNotifyType()) {
			HttpNotifyRequest httpReq = new HttpNotifyRequest();
			BeanUtils.copyProperties(targetRequest, httpReq);
			httpReq.setUrl(targetRequest.getUrlList().get(0));
			return httpReq;
		}else {
			return null;
		}
	}
}
