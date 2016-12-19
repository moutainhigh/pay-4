/**
 *  File: JmsLogAop.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-11-16   Sandy_Yang  create
 *  
 *
 */
package com.pay.poss.jmslog.service;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.jmslog.model.FundoutJmsLog;

/**
 * 消息发送/接收日志处理
 * 
 * @author Sandy_Yang
 */
public class JmsLogAopService {

	protected Log log = LogFactory.getLog(getClass());
	
	private FundoutJmsLogService jmsLogService;
	
	/**
	 * FO发送JMS消息前记录日志
	 * @param joinPoint
	 */
	@SuppressWarnings("unchecked")
	public void preSendLog(JoinPoint joinPoint) {
		try {
			if (joinPoint == null || joinPoint.getArgs().length==0) {
				return;
			}
			Notify2QueueRequest request = (Notify2QueueRequest) joinPoint.getArgs()[0];
			if (request.getIsOuter() == 1) {//外部消息忽略
				return;
			}
			log.info("创建FO内部消息发送日志...");
			Object transObj = request.getTargetObject();
			String proxyTarget = joinPoint.getTarget().getClass().getName();
			String objHash = request.getUniqueKey();
			String queueName = request.getQueueName();
			StringBuilder busiInfo = new StringBuilder();

			if (transObj instanceof String) {
				busiInfo.append(transObj);
			} else if (transObj instanceof Map) {
				Map map = (Map) transObj;
				for (Object key : map.keySet()) {
					busiInfo.append(key + "=" + map.get(key) + "&");
				}
			} else {
				Class clazz = transObj.getClass();
				Field fields[] = clazz.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					busiInfo.append(field.getName() + "=" + field.get(transObj) + "&");
				}
			}
			log.info("JMS传输信息:"+busiInfo);
			
			FundoutJmsLog jmsLog = new FundoutJmsLog();
			jmsLog.setBusiInfo(busiInfo.toString());
			jmsLog.setObjHash(objHash);//uuid
			jmsLog.setProxyTarget(proxyTarget);
			jmsLog.setQueueName(queueName);
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(request);
			jmsLog.setTransObject(bos.toByteArray());
			
			jmsLogService.insertJmsLog(jmsLog);
			
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("创建FO内部消息发送日志失败.....", e);
			}
		}
	}
	
	
	/**
	 * FO接受JMS消息后删除对应日志
	 * @param joinPoint
	 */
	@SuppressWarnings("unchecked")
	public void afterReturnReceiveLog(JoinPoint joinPoint) {
		try{
			if (joinPoint == null || joinPoint.getArgs().length == 0) {
				return;
			}
			log.info("AOP删除JMS发送日志....");
			ActiveMQObjectMessage msg = (ActiveMQObjectMessage) joinPoint.getArgs()[0];
			Notify2QueueRequest request = (Notify2QueueRequest) msg.getObject();
			String hash = request.getUniqueKey();
			StringBuilder innerHash = new StringBuilder();
			Object transObj = request.getTargetObject();
			if (transObj instanceof String) {
				innerHash.append(transObj);
			} else if (transObj instanceof Map) {
				Map map = (Map) transObj;
				for (Object key : map.keySet()) {
					innerHash.append(key + "=" + map.get(key) + "&");
				}
			} else {
				Class clazz = transObj.getClass();
				Field fields[] = clazz.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					innerHash.append(field.getName() + "=" + field.get(transObj) + "&");
				}
			}
			Map<String,String> map = new HashMap<String,String>();
			map.put("objHash", hash);
			jmsLogService.deleteJmsLog(map);
			log.info("AOP删除JMS信息:" + innerHash.toString());
		}catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("JMS删除日志失败...",e);
			}
		}
	}
	public void setJmsLogService(final FundoutJmsLogService jmsLogService) {
		this.jmsLogService = jmsLogService;
	}
}
