/**
 *  File: NotifyFacadeServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-9      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.withdraw.inf.output.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.jms.notification.request.EmailNotifyRequest;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.notification.request.NotifyRequest;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.notification.request.SMSNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.pe.dto.BeanUtils;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.JSonUtil;

/**
 * 通知封装服务
 * @author zliner
 *
 */
public class NotifyFacadeServiceImpl implements NotifyFacadeService {
	//消息发送服务 
	private JmsSender jmsSender;
	//日志服务类
	private Log logger = LogFactory.getLog(NotifyFacadeServiceImpl.class);
	//set注入
	public void setJmsSender(final JmsSender param) {
		this.jmsSender = param;
	}
	 
	/**
	 * 发送消息处理  
	 * @param request             发送消息内容
	 */
	public void sendRequest(Notify2QueueRequest request) {
		try {
			jmsSender.send(request.getQueueName(), request);//此处发送包含uuid对象,以便方法后拦截
		}catch(Exception e) {
			logger.error("queueName: " + request.getQueueName());
			logger.error("targetObject's jasonStr: " + JSonUtil.toJSonString(request.getTargetObject()));
			logger.error("notify queue is error",e);
		}
	}
	 
	/** 
	 * 发送邮件或短信通知
	 * @param targetRequest             发送消息内容
	 */
	public void notifyRequest(NotifyTargetRequest targetRequest) {
		try {
			jmsSender.send(buildNotifyRequest(targetRequest));
		}catch(Exception e) {
			logger.error("targetObject's jasonStr: " + JSonUtil.toJSonString(targetRequest));
			logger.error("notify target queue is error",e);
		}
	}
	/** 
	 * 发送邮件或短信通知
	 * @param targetRequest             发送消息内容
	 */
	public void notifyRequest2(NotifyTargetRequest targetRequest) {
		try {
			jmsSender.send(buildNotifyRequest2(targetRequest));
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
	//构建不同的消息对象
	private NotifyRequest buildNotifyRequest2(NotifyTargetRequest targetRequest) {
		if(RequestType.EMAIL.value() == targetRequest.getNotifyType()) {
			EmailNotifyRequest emailReq = new EmailNotifyRequest();
			BeanUtils.copyProperties(emailReq,targetRequest);
			return emailReq;
		}else if(RequestType.SMS.value() == targetRequest.getNotifyType()) {
			SMSNotifyRequest snsReq = new SMSNotifyRequest();
			BeanUtils.copyProperties(snsReq,targetRequest);
			return snsReq;
		}else if (RequestType.HTTP.value() == targetRequest.getNotifyType()) {
			HttpNotifyRequest httpReq = new HttpNotifyRequest();
			BeanUtils.copyProperties( httpReq,targetRequest);
			httpReq.setUrl(targetRequest.getUrlList().get(0));
			return httpReq;
		}else {
			return null;
		}
	}
}
