package com.pay.rm.blacklist.dto;

import java.util.Date;

public class BlacklistLogDTO {

	private Long blacklistLogId; // id
	private Date logTime; // 时间
	private String logOperator; // 操作人
	private String logContent; // 内容
	private Long hmdid;

	public Long getHmdid() {
		return hmdid;
	}

	public void setHmdid(Long hmdid) {
		this.hmdid = hmdid;
	}

	public Long getBlacklistLogId() {
		return blacklistLogId;
	}

	public void setBlacklistLogId(Long blacklistLogId) {
		this.blacklistLogId = blacklistLogId;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public String getLogOperator() {
		return logOperator;
	}

	public void setLogOperator(String logOperator) {
		this.logOperator = logOperator;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

}
