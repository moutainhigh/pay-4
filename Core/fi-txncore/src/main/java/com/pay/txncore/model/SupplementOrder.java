package com.pay.txncore.model;

import java.util.Date;

/**
 * @Title: SupplementOrder.java
 * @Package com.pay.fi.model
 * @Description: 手工补单Model
 * @author Gavin_Song(foxdog888@gmail.com)
 * @date 2011-5-30 上午11:59:40
 * @version V1.0
 */
public class SupplementOrder {
	/**
	 * 主键
	 */
	private long supplementNo;
	
	/**
	 * 充值协议流水
	 */
	private long depositProtocolNo;
	
	/**
	 * 充值定单流水
	 */
	private long depositOrderNo;

	/**
	 * 支付定单流水
	 */
	private long paymentOrderNo;
	
	/**
	 * 银行定单号
	 */
	private String returnNo;
	
	/**
	 * 操作员号
	 */
	private String operator;
	
	/**
	 * 审核员号
	 */
	private String  auditors;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 更新时间
	 */
	private Date updateDate;
	
	/**
	 * 操作员备注
	 */
	private String operatorRemark;
	
	/**
	 * 审核员备注
	 */
	private String auditorsRemark;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 补单批次号
	 */
	private String batchNo;
	
	/**
	 * 补单类型(手工单笔，手工批量，自动申请，自动审核)
	 */
	private String supplementType;
	
	/**
	 * 外部跟踪号，用于和上步操作关联
	 */
	private String followNo;
	
	private String billType;


	public long getSupplementNo() {
		return supplementNo;
	}

	public void setSupplementNo(long supplementNo) {
		this.supplementNo = supplementNo;
	}

	public long getDepositProtocolNo() {
		return depositProtocolNo;
	}

	public void setDepositProtocolNo(long depositProtocolNo) {
		this.depositProtocolNo = depositProtocolNo;
	}

	public long getDepositOrderNo() {
		return depositOrderNo;
	}

	public void setDepositOrderNo(long depositOrderNo) {
		this.depositOrderNo = depositOrderNo;
	}

	public long getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(long paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public String getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}

	

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getAuditors() {
		return auditors;
	}

	public void setAuditors(String auditors) {
		this.auditors = auditors;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getOperatorRemark() {
		return operatorRemark;
	}

	public void setOperatorRemark(String operatorRemark) {
		this.operatorRemark = operatorRemark;
	}

	public String getAuditorsRemark() {
		return auditorsRemark;
	}

	public void setAuditorsRemark(String auditorsRemark) {
		this.auditorsRemark = auditorsRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSupplementType() {
		return supplementType;
	}

	public void setSupplementType(String supplementType) {
		this.supplementType = supplementType;
	}

	public String getFollowNo() {
		return followNo;
	}

	public void setFollowNo(String followNo) {
		this.followNo = followNo;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	@Override
	public String toString() {
		return "SupplementOrder [supplementNo=" + supplementNo
				+ ", depositProtocolNo=" + depositProtocolNo
				+ ", depositOrderNo=" + depositOrderNo + ", paymentOrderNo="
				+ paymentOrderNo + ", returnNo=" + returnNo + ", operator="
				+ operator + ", auditors=" + auditors + ", status=" + status
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", operatorRemark=" + operatorRemark + ", auditorsRemark="
				+ auditorsRemark + ", remark=" + remark + ", batchNo="
				+ batchNo + ", supplementType=" + supplementType
				+ ", followNo=" + followNo + ", billType=" + billType + "]";
	}
}
