/**
 * 
 */
package com.pay.txncore.model;

import java.util.Date;

/**
 * @author hhj
 * 手工批量补单
 */
public class BatchSupplement {
	/**
	 * ID
	 */
	private long batchRepairId;
	/**
	 * 批次号
	 */
	private long batchNo;
	/**
	 * 操作员
	 */
	private String operator;
	/**
	 * 总金额
	 */
	private long countAmount;
	/**
	 * 总比数
	 */
	private long countNum;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * 文件大小
	 */
	private String fileSize;
	/**
	 * 导入时间
	 */
	private Date dateTime;
	/**
	 * 入库时间
	 */
	private Date systemdate;
	public long getBatchRepairId() {
		return batchRepairId;
	}
	public void setBatchRepairId(long batchRepairId) {
		this.batchRepairId = batchRepairId;
	}
	public long getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(long batchNo) {
		this.batchNo = batchNo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public long getCountAmount() {
		return countAmount;
	}
	public void setCountAmount(long countAmount) {
		this.countAmount = countAmount;
	}
	public long getCountNum() {
		return countNum;
	}
	public void setCountNum(long countNum) {
		this.countNum = countNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public Date getSystemdate() {
		return systemdate;
	}
	public void setSystemdate(Date systemdate) {
		this.systemdate = systemdate;
	}
	@Override
	public String toString() {
		return "BatchSupplement [batchNo=" + batchNo + ", batchRepairId=" + batchRepairId + ", countAmount=" + countAmount + ", countNum=" + countNum + ", dateTime=" + dateTime
				+ ", fileName=" + fileName + ", fileSize=" + fileSize + ", operator=" + operator + ", status=" + status + ", systemdate=" + systemdate + ", toString()="
				+ super.toString() + "]";
	}
}
