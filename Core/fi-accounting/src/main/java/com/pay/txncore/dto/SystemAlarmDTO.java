package com.pay.txncore.dto;

import java.util.Date;

/**
 * 
 * @author Fred
 *
 */
public class SystemAlarmDTO {

	/**
	 * 报警日志主键ID
	 */
	private Long alarmId;

	/**
	 * 报警对象主体
	 */
	private String alarmContent;

	/**
	 * 错误日志内容
	 */
	private String errorDesc;

	/**
	 * 日志备注
	 */
	private String remark;

	/**
	 * 状态
	 */
	private int status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(Long alarmId) {
		this.alarmId = alarmId;
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

	@Override
	public String toString() {
		return "SystemAlarmDTO [alarmContent=" + alarmContent + ", alarmId="
				+ alarmId + ", createTime=" + createTime + ", errorDesc="
				+ errorDesc + ", remark=" + remark + ", status=" + status + "]";
	}

}
