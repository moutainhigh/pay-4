package com.pay.fundout.withdraw.model.result;

import com.pay.inf.model.BaseObject;
/**
 * <p>提现对账结果DTO</p>
 * @author Henry.Zeng
 * @since 2010-9-7
 * @see
 */
public class WithdrawReconcileResult extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long leftStatus;
	private String leftFailureReason;
	private Long rightOrderSeq;
	private Long status;
	private Long fileKy;
	private java.util.Date leftTime;
	private String leftBankSeq;
	private String batchNum;
	private Long leftAmount;
	private String rightMemberName;
	private String leftRemark;
	private String bankCode;
	private Long rightAmount;
	private String rightBankAcct;
	private Long busiFlag;
	private Long resultKy;
	private String leftBankAcct;
	private java.util.Date rightTime;
	private String leftBankBranch;
	private String leftBackSeq;
	private String rightBankBranch;
	private java.util.Date cutTime;
	private String leftMemberName;

	public Long getLeftStatus() {
		return leftStatus;
	}

	public void setLeftStatus(Long leftStatus) {
		this.leftStatus = leftStatus;
	}

	public String getLeftFailureReason() {
		return leftFailureReason;
	}

	public void setLeftFailureReason(String leftFailureReason) {
		this.leftFailureReason = leftFailureReason;
	}

	public Long getRightOrderSeq() {
		return rightOrderSeq;
	}

	public void setRightOrderSeq(Long rightOrderSeq) {
		this.rightOrderSeq = rightOrderSeq;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getFileKy() {
		return fileKy;
	}

	public void setFileKy(Long fileKy) {
		this.fileKy = fileKy;
	}

	public java.util.Date getLeftTime() {
		return leftTime;
	}

	public void setLeftTime(java.util.Date leftTime) {
		this.leftTime = leftTime;
	}

	public String getLeftBankSeq() {
		return leftBankSeq;
	}

	public void setLeftBankSeq(String leftBankSeq) {
		this.leftBankSeq = leftBankSeq;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public Long getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(Long leftAmount) {
		this.leftAmount = leftAmount;
	}

	public String getRightMemberName() {
		return rightMemberName;
	}

	public void setRightMemberName(String rightMemberName) {
		this.rightMemberName = rightMemberName;
	}

	public String getLeftRemark() {
		return leftRemark;
	}

	public void setLeftRemark(String leftRemark) {
		this.leftRemark = leftRemark;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Long getRightAmount() {
		return rightAmount;
	}

	public void setRightAmount(Long rightAmount) {
		this.rightAmount = rightAmount;
	}

	public String getRightBankAcct() {
		return rightBankAcct;
	}

	public void setRightBankAcct(String rightBankAcct) {
		this.rightBankAcct = rightBankAcct;
	}

	public Long getBusiFlag() {
		return busiFlag;
	}

	public void setBusiFlag(Long busiFlag) {
		this.busiFlag = busiFlag;
	}

	public Long getResultKy() {
		return resultKy;
	}

	public void setResultKy(Long resultKy) {
		this.resultKy = resultKy;
	}

	public String getLeftBankAcct() {
		return leftBankAcct;
	}

	public void setLeftBankAcct(String leftBankAcct) {
		this.leftBankAcct = leftBankAcct;
	}

	public java.util.Date getRightTime() {
		return rightTime;
	}

	public void setRightTime(java.util.Date rightTime) {
		this.rightTime = rightTime;
	}

	public String getLeftBankBranch() {
		return leftBankBranch;
	}

	public void setLeftBankBranch(String leftBankBranch) {
		this.leftBankBranch = leftBankBranch;
	}

	public String getLeftBackSeq() {
		return leftBackSeq;
	}

	public void setLeftBackSeq(String leftBackSeq) {
		this.leftBackSeq = leftBackSeq;
	}

	public String getRightBankBranch() {
		return rightBankBranch;
	}

	public void setRightBankBranch(String rightBankBranch) {
		this.rightBankBranch = rightBankBranch;
	}

	public java.util.Date getCutTime() {
		return cutTime;
	}

	public void setCutTime(java.util.Date cutTime) {
		this.cutTime = cutTime;
	}

	public String getLeftMemberName() {
		return leftMemberName;
	}

	public void setLeftMemberName(String leftMemberName) {
		this.leftMemberName = leftMemberName;
	}
}