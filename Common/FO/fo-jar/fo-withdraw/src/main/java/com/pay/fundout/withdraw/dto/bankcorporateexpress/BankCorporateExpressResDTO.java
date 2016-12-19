package com.pay.fundout.withdraw.dto.bankcorporateexpress;

import com.pay.inf.model.BaseObject;

public class BankCorporateExpressResDTO extends BaseObject {

	private static final long serialVersionUID = 5550639134801726425L;

	private String workOrderky; // 工单
	private String sequenceId; // 交易流水号
	private String memberCode; // 会员号
	private String memberName; // 会员名称
	private String bankCode; // 银行代码
	private String bankName; // 银行名称
	private String bankBranch; // 开户行名称
	private String bankAcct; // 银行账户
	private Long amount; // 汇款金额
	private String accountName; // 收款人姓名
	private String orderRemarks; // 交易备注
	private Integer status; // 交易状态
	private String failReason; // 失败原因
	private String reAudit; // 复核结果

	public String getWorkOrderky() {
		return workOrderky;
	}

	public void setWorkOrderky(String workOrderky) {
		this.workOrderky = workOrderky;
	}

	public String getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getOrderRemarks() {
		return orderRemarks;
	}

	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getReAudit() {
		return reAudit;
	}

	public void setReAudit(String reAudit) {
		this.reAudit = reAudit;
	}

}
