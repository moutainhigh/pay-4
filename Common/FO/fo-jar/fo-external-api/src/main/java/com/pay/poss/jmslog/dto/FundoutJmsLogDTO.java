/**
 *  File: FundoutJmsLogDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-16     darv      Changes
 *  
 *
 */
package com.pay.poss.jmslog.dto;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author darv
 * 
 */
public class FundoutJmsLogDTO extends BaseObject {
	private Long sequenceId;
	private byte[] transObject;
	private Integer retryTimes;
	private String busiInfo;
	private String objHash;
	private String queueName;
	private String proxyTarget;
	private Date createTime;
	private Date updateTime;

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public byte[] getTransObject() {
		return transObject;
	}

	public void setTransObject(byte[] transObject) {
		this.transObject = transObject;
	}

	public Integer getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(Integer retryTimes) {
		this.retryTimes = retryTimes;
	}

	public String getBusiInfo() {
		return busiInfo;
	}

	public void setBusiInfo(String busiInfo) {
		this.busiInfo = busiInfo;
	}

	public String getObjHash() {
		return objHash;
	}

	public void setObjHash(String objHash) {
		this.objHash = objHash;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getProxyTarget() {
		return proxyTarget;
	}

	public void setProxyTarget(String proxyTarget) {
		this.proxyTarget = proxyTarget;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
