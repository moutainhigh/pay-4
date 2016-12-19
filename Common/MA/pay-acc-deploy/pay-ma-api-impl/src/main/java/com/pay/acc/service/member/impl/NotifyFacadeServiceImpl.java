package com.pay.acc.service.member.impl;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.NotifyFacadeService;
import com.pay.jms.sender.JmsSender;
import com.pay.util.JSonUtil;

/**
 * 
 * @author meng.li
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
	 * @param request 发送消息内容
	 */
	public void sendRequest(String queueName, HashMap<String, Object> request) {
		try {
			jmsSender.send(queueName, request);//此处发送包含uuid对象,以便方法后拦截
		}catch(Exception e) {
			logger.error("queueName: " + queueName);
			logger.error("targetObject's jasonStr: " + JSonUtil.toJSonString(request));
			logger.error("notify queue is error",e);
		}
	}
	 
}
