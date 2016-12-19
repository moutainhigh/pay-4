/**
 * 
 */
package com.pay.txncore.model;

import java.util.Date;

/**
 * @author gavin_song
 * 手工批量补单比对信息
 */
public class BatchSupplementCompare {

	/**
	 * ID
	 */
	private long batchsupplementInfoId;
	/**
	 * ID
	 */
	private long batchsupplementId;
	/**
	 * 银行名称
	 */
	private String  bankName;
	/**
	 * 订单号
	 */
	private long orderNo;
	/**
	 * 银行订单号
	 */
	private String bankOrderNo;
	/**
	 * 金额
	 */
	private long  amount;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 文件导入时间
	 */
	private Date createDate;
	/**
	 * 创建时间
	 */
	private Date systemDate;
	/**
	 * 备注
	 */
	private String  remark;
	
	/**
	 * 批次号
	 */
	private String batchNo;
	
	/**
	 *卡标识
	 */
	private String billType;
	
	public long getBatchsupplementInfoId() {
		return batchsupplementInfoId;
	}
	public void setBatchsupplementInfoId(long batchsupplementInfoId) {
		this.batchsupplementInfoId = batchsupplementInfoId;
	}
	public long getBatchsupplementId() {
		return batchsupplementId;
	}
	public void setBatchsupplementId(long batchsupplementId) {
		this.batchsupplementId = batchsupplementId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}
	public String getBankOrderNo() {
		return bankOrderNo;
	}
	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	
	@Override
	public String toString() {
		return "BatchSupplementCompare [batchsupplementInfoId="
				+ batchsupplementInfoId + ", batchsupplementId="
				+ batchsupplementId + ", bankName=" + bankName + ", orderNo="
				+ orderNo + ", bankOrderNo=" + bankOrderNo + ", amount="
				+ amount + ", status=" + status + ", createDate=" + createDate
				+ ", systemDate=" + systemDate + ", remark=" + remark
				+ ", batchNo=" + batchNo + ", billType=" + billType + "]";
	}
}
