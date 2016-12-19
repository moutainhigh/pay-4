/**
 *  File: WithdrawWFParameter.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.flowprocess;

import com.pay.inf.model.BaseObject;

/**
 * <p>提现工作流参数类</p>
 * @author Administrator
 *
 */
public class WithdrawWFParameter extends BaseObject {
	
	/**
	  * 用户登录名
	  */
	 public static final String WF_PARAMETER_KEY_USERID = "userId";
	 
	 /**
	  * 当前节点名
	  */
	 public static final String WF_PARAMETER_KEY_NODENAME = "nodeName";
	 
	 /**
	  * 流程名称
	  */
	 public static final String WF_PARAMETER_KEY_PROCESSKEY = "processKey";
	 
	 /**
	  * 指定授权人
	  */
	 public static final String WF_PARAMETER_KEY_ASSIGNER = "assigner";
	 
	 /**
	  * 批注
	  */
	 public static final String WF_PARAMETER_KEY_TASKMESSAGE = "taskMessage";
	 
	 /**
	  * 工作流程实例ID
	  */
	 public static final String WF_PARAMETER_KEY_PROCESSINSTANCEID = "processInstanceId";
	 
	 /**
	  * 上一个节点操作 人
	  */
	 public static final String WF_PARAMETER_KEY_PREVIOUSUSER = "previousUser";
	 
	 /**
	  * 工单流水号
	  */
	 public static final String WF_PARAMETER_KEY_WORKORDERKY = "workOrderKy";
	
	 
	 /**
	  * 用户登录名
	  */
	 private String userId;
	 
	 /**
	  * 节点名称
	  */
	 private String nodeName;
	 
	 /**
	  * 流程名称
	  */
	 private String processKey;
	 
	 /**
	  * 指定授权人
	  */
	 private String assigner;
	 
	 /**
	  * 批注
	  */
	 private String taskMessage;
	 
	 /**
	  * 工作流程实例ID
	  */
	 private String processInstanceId;
	 
	 /**
	  * 上一个节点操作 人
	  */
	 private String previousUser;
	 
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}
	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	/**
	 * @return the processKey
	 */
	public String getProcessKey() {
		return processKey;
	}
	/**
	 * @param processKey the processKey to set
	 */
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	/**
	 * @return the assigner
	 */
	public String getAssigner() {
		return assigner;
	}
	/**
	 * @param assigner the assigner to set
	 */
	public void setAssigner(String assigner) {
		this.assigner = assigner;
	}
	/**
	 * @return the taskMessage
	 */
	public String getTaskMessage() {
		return taskMessage;
	}
	/**
	 * @param taskMessage the taskMessage to set
	 */
	public void setTaskMessage(String taskMessage) {
		this.taskMessage = taskMessage;
	}
	/**
	 * @return the processInstanceId
	 */
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	/**
	 * @param processInstanceId the processInstanceId to set
	 */
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	/**
	 * @return the previousUser
	 */
	public String getPreviousUser() {
		return previousUser;
	}
	/**
	 * @param previousUser the previousUser to set
	 */
	public void setPreviousUser(String previousUser) {
		this.previousUser = previousUser;
	}
}
