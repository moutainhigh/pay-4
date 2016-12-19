/**
 *  File: OrderHistoryDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-9      Sunsea.Li      Changes
 *  
 */
package com.pay.poss.service.ordercenter.dto.workorderhistory;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author Sunsea.Li
 *
 */
public class OrderHistoryDTO extends BaseObject {
	private static final long serialVersionUID = 5342208397490158518L;
	/**
	 * 系统交易号
	 */
	private Long orderKy;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 操作员号
	 */
	private String assignee;
	
	/**
	 * 操作名
	 */
	private String nodeName;
	
	/**
	 * 操作结果状态
	 */
	private String handleStatus;
	
	/**
	 * 操作备注
	 */
	private String taskMessage;

	public Long getOrderKy() {
		return orderKy;
	}

	public void setOrderKy(Long orderKy) {
		this.orderKy = orderKy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getTaskMessage() {
		return taskMessage;
	}

	public void setTaskMessage(String taskMessage) {
		this.taskMessage = taskMessage;
	}
}
