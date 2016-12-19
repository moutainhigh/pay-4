/**
 *  File: Notify2QueueRequest.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-9      zliner      Changes
 *  
 *
 */
package com.pay.poss.dto.withdraw.notify;

import java.io.Serializable;
import java.util.UUID;

/**
 * 发送到队列消息
 * @author zliner
 *
 */
public class Notify2QueueRequest implements Serializable {
	
	public Notify2QueueRequest(){
		this.uniqueKey = UUID.randomUUID().toString();
	}
	//序号
	private static final long serialVersionUID = 3533968821490091888L;
	//队列名称 
	private String queueName;
	//发送队列的消息对象
	private Serializable targetObject;
	
	/** 消息唯一标识 **/
	private String uniqueKey;
	
	/**此消息是否发送至外系统,1:表示发送到外系统,FO内部请忽略**/
	private int isOuter;
	
	public int getIsOuter() {
		return isOuter;
	}
	public void setIsOuter(int isOuter) {
		this.isOuter = isOuter;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public Serializable getTargetObject() {
		return targetObject;
	}
	public void setTargetObject(Serializable targetObject) {
		this.targetObject = targetObject;
	}
	public String getUniqueKey() {
		return uniqueKey;
	}
}
