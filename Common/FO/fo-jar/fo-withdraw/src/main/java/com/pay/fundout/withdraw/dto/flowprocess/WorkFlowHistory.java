/**
 *  File: WorkFlowHistory.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-15      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.flowprocess;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author Jonathen Ni
 * @since 2010-09-15
 * 工作流历史信息类
 *
 */
public class WorkFlowHistory extends BaseObject {
	
	/**
	 * 节点名称
	 */
	private String nodeName;
	
	/**
	 * 处理结果
	 */
	private String handleStatus;
		
	/**
	 * 批注
	 */
	 private String taskMessage;
	 
	 /**
	  * 状态
	  */
	 private String state;
	 
	 /**
	  * 结果
	  */
	 private String outCome;
	 
	 /**
	  * 持续时间
	  */
	 private String duration;
	 
	 /**
	  * 结束时间
	  */
	 private Date endTime;
	 
	 /**
	  * 创建时间
	  */
	 private Date createTime;
	 
	 /**
	  * 任务ID
	  */
	 private String id;
	 
	 /**
	  * 任务处理人
	  */
	private String assignee;
	 
	 /**
	  * 流程实例ID
	  */
	private String executionId;
	 
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
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the outCome
	 */
	public String getOutCome() {
		return outCome;
	}

	/**
	 * @param outCome the outCome to set
	 */
	public void setOutCome(String outCome) {
		this.outCome = outCome;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * @return the executionId
	 */
	public String getExecutionId() {
		return executionId;
	}

	/**
	 * @param executionId the executionId to set
	 */
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
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
	 * @return the handleStatus
	 */
	public String getHandleStatus() {
		return handleStatus;
	}

	/**
	 * @param handleStatus the handleStatus to set
	 */
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
}
