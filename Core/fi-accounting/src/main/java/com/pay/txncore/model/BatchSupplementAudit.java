/**
 * 
 */
package com.pay.txncore.model;

import java.util.Date;

/**
 * @author hhj
 * 手工批量补单审核信息
 */
public class BatchSupplementAudit {

	/**
	 * ID
	 */
	private long batchsupplementAuditId;
	/**
	 * ID
	 */
	private long batchsupplementId;
	/**
	 * 批次号
	 */
	private long batchNo;
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
	 * 交易状态(0 失败 1成功)
	 */
	private String status;
	/**
	 * 补单状态(0未审核、1拒绝、2补单成功、3补单失败)
	 */
	private String auditstatus;
	/**
	 * 审核人
	 */
	private String auditName;
	/**
	 * 创建时间
	 */
	private String  createdate;
	/**
	 * 创建时间
	 */
	private String  auditdate;
	/**
	 * 备注
	 */
	private String  remark;
	
	//=====充值表====//
	/**
	 * 银行名称
	 */
	private String  depositbankName;
	/**
	 * 订单号
	 */
	private long depositorderNo;
	/**
	 * 银行订单号
	 */
	private long depositbankOrderNo;
	/**
	 * 金额
	 */
	private long  depositamount;
	/**
	 * 交易状态
	 */
	private String depositstatus;
	/**
	 * 创建时间
	 */
	private String  depositcreatedate;
	
	public long getBatchsupplementAuditId() {
		return batchsupplementAuditId;
	}
	public void setBatchsupplementAuditId(long batchsupplementAuditId) {
		this.batchsupplementAuditId = batchsupplementAuditId;
	}
	public long getBatchsupplementId() {
		return batchsupplementId;
	}
	public void setBatchsupplementId(long batchsupplementId) {
		this.batchsupplementId = batchsupplementId;
	}
	public long getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(long batchNo) {
		this.batchNo = batchNo;
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
	public String getAuditstatus() {
		return auditstatus;
	}
	public void setAuditstatus(String auditstatus) {
		this.auditstatus = auditstatus;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getAuditdate() {
		return auditdate;
	}
	public void setAuditdate(String auditdate) {
		this.auditdate = auditdate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDepositbankName() {
		return depositbankName;
	}
	public void setDepositbankName(String depositbankName) {
		this.depositbankName = depositbankName;
	}
	public long getDepositorderNo() {
		return depositorderNo;
	}
	public void setDepositorderNo(long depositorderNo) {
		this.depositorderNo = depositorderNo;
	}
	public long getDepositbankOrderNo() {
		return depositbankOrderNo;
	}
	public void setDepositbankOrderNo(long depositbankOrderNo) {
		this.depositbankOrderNo = depositbankOrderNo;
	}
	public long getDepositamount() {
		return depositamount;
	}
	public void setDepositamount(long depositamount) {
		this.depositamount = depositamount;
	}
	public String getDepositstatus() {
		return depositstatus;
	}
	public void setDepositstatus(String depositstatus) {
		this.depositstatus = depositstatus;
	}
	
	public String getDepositcreatedate() {
		return depositcreatedate;
	}
	public void setDepositcreatedate(String depositcreatedate) {
		this.depositcreatedate = depositcreatedate;
	}
	@Override
	public String toString() {
		return "BatchSupplementAudit [amount=" + amount + ", auditName=" + auditName + ", auditdate=" + auditdate + ", auditstatus=" + auditstatus + ", bankName=" + bankName
				+ ", bankOrderNo=" + bankOrderNo + ", batchNo=" + batchNo + ", batchsupplementAuditId=" + batchsupplementAuditId + ", batchsupplementId=" + batchsupplementId
				+ ", createdate=" + createdate + ", depositamount=" + depositamount + ", depositbankName=" + depositbankName + ", depositbankOrderNo=" + depositbankOrderNo
				+ ", depositcreatedate=" + depositcreatedate + ", depositorderNo=" + depositorderNo + ", depositstatus=" + depositstatus + ", orderNo=" + orderNo + ", remark="
				+ remark + ", status=" + status + ", toString()=" + super.toString() + "]";
	}
	
}
