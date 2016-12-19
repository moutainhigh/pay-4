package com.pay.fundout.withdraw.dto;

import java.util.Date;

/**
 * @description 提现信息
 * @author Sandy_Yang
 * @date 2010-8-13
 */
public class WithdrawJmsDTO implements java.io.Serializable {

	private static final long serialVersionUID = -7054963155060623768L;

	/**
	 * 主键ID
	 */
	private Long withdrawId;

	/**
	 * memberCode
	 */
	private Long memberCode;

	/**
	 * 会员标识
	 */
	private Long operatorId;

	/**
	 * 提现类型,1:企业提现,2:个人提现
	 */
	private Integer type;

	/**
	 * 账户类型,10:人民币账户
	 */
	private Integer memberAccType;

	/**
	 * 提现金额
	 */
	private Long amount;

	/**
	 * 会员提现备注
	 */
	private String comments;

	/**
	 * 提现优先级,4:普通,数字越大优先级越高
	 */
	private int priority;

	/**
	 * 收款人姓名
	 */
	private String accountName;

	/**
	 * 提现银行卡卡号
	 */
	private String bankCardNum;

	/**
	 * 提现银行ID,关联到银行基础表
	 */
	private String bankId;

	/**
	 * 开户支行名称
	 */
	private String bankBranch;

	/**
	 * 开户行所在省份
	 */
	private Integer bankProvince;

	/**
	 * 开户行所在城市
	 */
	private Integer bankCity;

	/**
	 * 银行用途
	 */
	private String bankPurpose;

	/**
	 * 币种,eg:RMB,USD
	 */
	private String moneyType;

	/**
	 * 提现状态,1:处理中,2:处理成功3:处理失败
	 */
	private Integer status;

	/**
	 * 处理方式,1:手动,2:自动
	 */
	private Integer operateType;

	/**
	 * 失败原因
	 */
	private String errorMessage;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 更新时间
	 */
	private Date updateDate;

	public Long getWithdrawId() {
		return withdrawId;
	}

	public void setWithdrawId(Long withdrawId) {
		this.withdrawId = withdrawId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMemberAccType() {
		return memberAccType;
	}

	public void setMemberAccType(Integer memberAccType) {
		this.memberAccType = memberAccType;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public Integer getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(Integer bankProvince) {
		this.bankProvince = bankProvince;
	}

	public Integer getBankCity() {
		return bankCity;
	}

	public void setBankCity(Integer bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankPurpose() {
		return bankPurpose;
	}

	public void setBankPurpose(String bankPurpose) {
		this.bankPurpose = bankPurpose;
	}

	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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

	@Override
	public String toString() {
		return "WithdrawDTO [accountName=" + accountName + ", amount=" + amount
				+ ", bankBranch=" + bankBranch + ", bankCardNum=" + bankCardNum
				+ ", bankCity=" + bankCity + ", bankId=" + bankId
				+ ", bankProvince=" + bankProvince + ", bankPurpose="
				+ bankPurpose + ", comments=" + comments + ", createDate="
				+ createDate + ", errorMessage=" + errorMessage
				+ ", memberAccType=" + memberAccType + ", memberCode="
				+ memberCode + ", moneyType=" + moneyType + ", operateType="
				+ operateType + ", operatorId=" + operatorId + ", priority="
				+ priority + ", status=" + status + ", type=" + type
				+ ", updateDate=" + updateDate + ", withdrawId=" + withdrawId
				+ "]";
	}

}
