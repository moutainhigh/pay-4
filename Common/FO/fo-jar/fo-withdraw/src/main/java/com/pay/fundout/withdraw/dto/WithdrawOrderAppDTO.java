/**
 *  File: WithdrawOrderAppDTO.java
 *  Description:WithdrawOrder DTO for App
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-9-14   Sandy_Yang  create
 *
 */
package com.pay.fundout.withdraw.dto;

import java.util.Date;

import com.pay.poss.dto.withdraw.orderhandlermanage.BaseOrderDTO;

/**
 * WithdrawOrder DTO for App
 * @author Sandy_Yang
 */
public class WithdrawOrderAppDTO extends BaseOrderDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2311104122458764371L;

	/** 主键 **/
	private Long sequenceId;

	/** 会员号 **/
	private Long memberCode;

	/** 申请人会员类型。默认个人会员 **/
	private Long memberType;

	/** 账户号 **/
	private String memberAcc;

	/** 帐户类型。默认人民币账户 **/
	private Long memberAccType;

	/** 提现金额 **/
	private Long amount;

	/** 优先级.值越大越优先.默认为5 **/
	private Short prioritys;

	/** 收款人名称**/
	private String accountName;

	/** 银行账户 **/
	private String bankAcct;

	/** 银行账户类型。默认借记卡账户 **/
	private Long bankAcctType;

	/** 提现银行ID,关联到银行基础表 **/
	private String bankKy;

	/** 开户支行名称 **/
	private String bankBranch;

	/** 开户行所在省份 **/
	private Short bankProvince;

	/** 开户行所在城市 **/
	private Short bankCity;

	/** 银行用途(在银行方查询中使用) **/
	private String bankPurpose;

	/** 银行备注 **/
	private String bankRemarks;

	/** 交易备注 **/
	private String orderRemarks;

	/** 币种.默认CNY **/
	private String moneyType;

	/** 错误信息 **/
	private String errorMessage;

	/** 提现类别:普通个人提现0、公司提现1、认证提现2、特殊提现3（ T+0提现、大额提现、委托提现、强制提现、批量付款到银行卡） **/
	private Long type;

	/** 提现状态,1:处理中,2:处理成功3:处理失败 **/
	private Long status;

	/** 创建时间 **/
	private Date createTime;

	/** 更新时间 **/
	private Date updateTime;

	/** 业务类型 0：提现,1：批量出款,2：信用卡还款 **/
	private Long busiType;

	/** 进入出款系统的订单号 **/
	private String orderSeqId;

	/** 发起出款请求来源 **/
	private String fundorigin;

	/** 出款方式 **/
	private Short withdrawType;

	/** 出款银行编号 **/
	private String withdrawBankCode;

	/** 交易类型 **/
	private Integer tradeType;
	
	/** 订单流水 **/
	private String tradeSeq;
	
	private Long fee;
	/**
	 * 批量订单号
	 */
	private Long massOrderSeq;
	
	/**
	 * 实付金额
	 */
	private Long payerAmount;
	
	/**
	 * 订单金额
	 */
	private Long orderAmount;
	/**
	 * 收款方手机号码
	 */
	private String payeeMobile;
	
	/**
	 * 退款原因
	 */
	private String refundReason;
	
	public String getPayeeMobile() {
		return payeeMobile;
	}

	public void setPayeeMobile(String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}

	public Long getMassOrderSeq() {
		return massOrderSeq;
	}

	public void setMassOrderSeq(Long massOrderSeq) {
		this.massOrderSeq = massOrderSeq;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public String getTradeSeq() {
		return tradeSeq;
	}

	public void setTradeSeq(String tradeSeq) {
		this.tradeSeq = tradeSeq;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Long getMemberType() {
		return memberType;
	}

	public void setMemberType(Long memberType) {
		this.memberType = memberType;
	}

	public String getMemberAcc() {
		return memberAcc;
	}

	public void setMemberAcc(String memberAcc) {
		this.memberAcc = memberAcc;
	}

	public Long getMemberAccType() {
		return memberAccType;
	}

	public void setMemberAccType(Long memberAccType) {
		this.memberAccType = memberAccType;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Short getPrioritys() {
		return prioritys;
	}

	public void setPrioritys(Short prioritys) {
		this.prioritys = prioritys;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public Long getBankAcctType() {
		return bankAcctType;
	}

	public void setBankAcctType(Long bankAcctType) {
		this.bankAcctType = bankAcctType;
	}

	public String getBankKy() {
		return bankKy;
	}

	public void setBankKy(String bankKy) {
		this.bankKy = bankKy;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public Short getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(Short bankProvince) {
		this.bankProvince = bankProvince;
	}

	public Short getBankCity() {
		return bankCity;
	}

	public void setBankCity(Short bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankPurpose() {
		return bankPurpose;
	}

	public void setBankPurpose(String bankPurpose) {
		this.bankPurpose = bankPurpose;
	}

	public String getBankRemarks() {
		return bankRemarks;
	}

	public void setBankRemarks(String bankRemarks) {
		this.bankRemarks = bankRemarks;
	}

	public String getOrderRemarks() {
		return orderRemarks;
	}

	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}


	
    /*吕宏修改:统一用父类订单状态*/
	public Long getStatus() {
		return new Long(getInnerStatus());
	}

	public void setStatus(Long status) {
		setInnerStatus(status.intValue());
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

	public Long getBusiType() {
		return busiType;
	}

	public void setBusiType(Long busiType) {
		this.busiType = busiType;
	}

	public String getOrderSeqId() {
		return orderSeqId;
	}

	public void setOrderSeqId(String orderSeqId) {
		this.orderSeqId = orderSeqId;
	}

	public String getFundorigin() {
		return fundorigin;
	}

	public void setFundorigin(String fundorigin) {
		this.fundorigin = fundorigin;
	}

	public Short getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(Short withdrawType) {
		this.withdrawType = withdrawType;
	}

	public String getWithdrawBankCode() {
		return withdrawBankCode;
	}

	public void setWithdrawBankCode(String withdrawBankCode) {
		this.withdrawBankCode = withdrawBankCode;
	}

	public Long getPayerAmount() {
		return payerAmount;
	}

	public void setPayerAmount(Long payerAmount) {
		this.payerAmount = payerAmount;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	
	
}
