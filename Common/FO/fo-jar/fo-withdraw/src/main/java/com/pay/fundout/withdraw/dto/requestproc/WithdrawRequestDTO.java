/**
 *  File: WithDrawRequest.java
 *  Description:提现规则验证Bean
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-9-16   Sandy_Yang  create
 *  
 */
package com.pay.fundout.withdraw.dto.requestproc;

import java.io.Serializable;

import com.pay.poss.dto.withdraw.orderhandlermanage.BaseOrderDTO;

/**
 * 提现规则验证Bean
 * 
 * @author Sandy_Yang
 */
public class WithdrawRequestDTO extends BaseOrderDTO implements Serializable {
	// 序号
	private static final long serialVersionUID = -7599556984481254335L;
	/** 提现订单主键 **/
	private Long seqId;

	/** memberCode **/
	private String memberCode;

	/** 账户号 **/
	private String memberAcc;

	/** 帐户类型。默认人民币账户 **/
	private Long memberAccType;

	/** 银行账户类型。默认借记卡账户 **/
	private Long bankAcctType;

	/** 业务类型 0：提现,1：批量出款,2：信用卡还款 **/
	private Long busiType;

	/** 发起出款请求来源 **/
	private String fundorigin;

	/** 账户类型 **/
	private int acctType;

	/** 出款方式 **/
	private int withdrawType;

	/** 出款银行编号 **/
	private String withdrawBankCode;

	/** 出款银行 **/
	private String bankAcct;

	/** 用户申请的提现金额 **/
	private Long applyWithdrawAmount;

	/** 提现状态,1:处理中,2:处理成功3:处理失败 **/
	private Long status;

	/** 币种.默认CNY **/
	private String moneyType;

	/** 提现类别:0-普通个人提现，1-公司提现，2-认证提现，3-特殊提现（T+0提现、大额提现、委托提现、强制提现、批量付款到银行卡），4-bsp提现 **/
	private Long type;

	/** 优先级.值越大越优先.默认为5 **/
	private Short prioritys;

	/** 错误信息Id **/
	private String messageId;

	/** 会员类型 **/
	private int memberType;

	/** 收款人名称 **/
	private String accountName;

	/** 开户支行名称 **/
	private String bankBranch;

	/** 开户行所在省份 **/
	private Short bankProvince;

	/** 开户行所在城市 **/
	private Short bankCity;

	/** 交易备注 **/
	private String orderRemarks;

	/** 提现银行ID,关联到银行基础表 **/
	private String bankKy;
	
	/** 手续费 **/
	private Long fee;

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
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

	public Long getBankAcctType() {
		return bankAcctType;
	}

	public void setBankAcctType(Long bankAcctType) {
		this.bankAcctType = bankAcctType;
	}

	public Long getBusiType() {
		return busiType;
	}

	public void setBusiType(Long busiType) {
		this.busiType = busiType;
	}

	public String getFundorigin() {
		return fundorigin;
	}

	public void setFundorigin(String fundorigin) {
		this.fundorigin = fundorigin;
	}

	/* 吕宏修改:统一用父类订单状态 */
	public Long getStatus() {
		return new Long(getInnerStatus());
	}

	public void setStatus(Long status) {
		setInnerStatus(status.intValue());
	}

	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Short getPrioritys() {
		return prioritys;
	}

	public void setPrioritys(Short prioritys) {
		this.prioritys = prioritys;
	}

	public String getBankKy() {
		return bankKy;
	}

	public void setBankKy(String bankKy) {
		this.bankKy = bankKy;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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

	public String getOrderRemarks() {
		return orderRemarks;
	}

	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	public int getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(int withdrawType) {
		this.withdrawType = withdrawType;
	}

	public String getWithdrawBankCode() {
		return withdrawBankCode;
	}

	public void setWithdrawBankCode(String withdrawBankCode) {
		this.withdrawBankCode = withdrawBankCode;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Long getApplyWithdrawAmount() {
		return applyWithdrawAmount;
	}

	public void setApplyWithdrawAmount(Long applyWithdrawAmount) {
		this.applyWithdrawAmount = applyWithdrawAmount;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public int getAcctType() {
		return acctType;
	}

	public void setAcctType(int acctType) {
		this.acctType = acctType;
	}

	public Long getSeqId() {
		return seqId;
	}

	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}

	public int getMemberType() {
		return memberType;
	}

	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}

	public boolean isBusiness() {
		if (memberType == 1)
			return false;
		else
			return true;
	}
}
