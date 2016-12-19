/**
 * 
 */
package com.pay.txncore.model;

import java.util.Date;

/**
 * @author huhb
 *
 */
public class SystemAlarm implements java.io.Serializable {

	
	private Long alarmId;
	
	private Long partnerId;
	
	private String alarmContent;
	
	private String errorDesc;
	
	private int status;
	
	private Date createTime;
	
	private Date updateTime;
	
	private String remark;
	
	private String remark1;

	public Long getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(Long alarmId) {
		this.alarmId = alarmId;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	@Override
	public String toString() {
		return "SystemAlarm [alarmContent=" + alarmContent + ", alarmId="
				+ alarmId + ", createTime=" + createTime + ", errorDesc="
				+ errorDesc + ", partnerId=" + partnerId + ", remark=" + remark
				+ ", remark1=" + remark1 + ", status=" + status
				+ ", updateTime=" + updateTime + "]";
	}
	
}
