/**
 *  File: WithdrawOrder.java
 *  Description:withdrawOrder model
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      Sandy_Yang      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.model.order;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * withdrawOrder model
 * @author Sandy_Yang
 * 
 */
public class WithdrawOrder extends BaseObject{

	private static final long serialVersionUID = -8521810636542788851L;

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
	
	/**收款方开户行银行名称 **/
	private String payeeBankName;

	public String getPayeeBankName() {
		return payeeBankName;
	}
	public void setPayeeBankName(final String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

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
	
	private Integer batchStatus;
	
	private int[] busiTypes;
	
	/**
	 * 实付金额
	 */
	private Long payerAmount;
	
	/**
	 * 订单金额
	 */
	private Long orderAmount;
	

	/**
	 * 会员名称
	 */
	private String memberName = "";
	/**
	 * 手机号码
	 */
	private String payeeMobile;
	
	private String riskLeveCode;//商户风控等级
	
	private String accountMode;//结算周期
	
	private Integer isLoaning;//是否垫资
	
	/** 业务批次号 **/
	private String batchNo;
	
	/** 前台复核提交时间**/
	private Date webAuditTime;
	
	/** 滞留原因 **/
	private String failReason;
	
	//-------------------add - batch_paymentorder:orderId
	private Long corderId ;
	
	private Long parentOrderId ;
	
	//------ PengJiangbo
	/**
	 * 付款方账号类型
	 */
	private Integer payerAcctType;
	/**
	 * 收款方账号类型
	 */
	private Integer payeeAcctType;
	/**
	 * 付款方币种代码
	 */
	private String payerCurrencyCode;
	/**
	 * 收款方币种代码
	 */
	private String payeeCurrencyCode;
	//------
	
	/**
	 * @return the failReason
	 */
	public String getFailReason() {
		return failReason;
	}
	/**
	 * @param failReason the failReason to set
	 */
	public void setFailReason(final String failReason) {
		this.failReason = failReason;
	}
	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}
	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(final String batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * @return the webAuditTime
	 */
	public Date getWebAuditTime() {
		return webAuditTime;
	}
	/**
	 * @param webAuditTime the webAuditTime to set
	 */
	public void setWebAuditTime(final Date webAuditTime) {
		this.webAuditTime = webAuditTime;
	}
	
	public Integer getIsLoaning() {
		return isLoaning;
	}

	public void setIsLoaning(final Integer isLoaning) {
		this.isLoaning = isLoaning;
	}

	/**
	 * @return the riskLeveCode
	 */
	public String getRiskLeveCode() {
		return riskLeveCode;
	}

	/**
	 * @param riskLeveCode the riskLeveCode to set
	 */
	public void setRiskLeveCode(final String riskLeveCode) {
		this.riskLeveCode = riskLeveCode;
	}

	/**
	 * @return the accountMode
	 */
	public String getAccountMode() {
		return accountMode;
	}

	/**
	 * @param accountMode the accountMode to set
	 */
	public void setAccountMode(final String accountMode) {
		this.accountMode = accountMode;
	}

	public String getPayeeMobile() {
		return payeeMobile;
	}

	public void setPayeeMobile(final String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(final String memberName) {
		this.memberName = memberName;
	}
	public Integer getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(final Integer batchStatus) {
		this.batchStatus = batchStatus;
	}

	private Long massOrderSeq;
	
	public Long getMassOrderSeq() {
		return massOrderSeq;
	}

	public void setMassOrderSeq(final Long massOrderSeq) {
		this.massOrderSeq = massOrderSeq;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(final Long fee) {
		this.fee = fee;
	}

	public String getTradeSeq() {
		return tradeSeq;
	}

	public void setTradeSeq(final String tradeSeq) {
		this.tradeSeq = tradeSeq;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(final Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(final Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(final Long memberCode) {
		this.memberCode = memberCode;
	}

	public Long getMemberType() {
		return memberType;
	}

	public void setMemberType(final Long memberType) {
		this.memberType = memberType;
	}

	public String getMemberAcc() {
		return memberAcc;
	}

	public void setMemberAcc(final String memberAcc) {
		this.memberAcc = memberAcc;
	}

	public Long getMemberAccType() {
		return memberAccType;
	}

	public void setMemberAccType(final Long memberAccType) {
		this.memberAccType = memberAccType;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(final Long amount) {
		this.amount = amount;
	}

	public Short getPrioritys() {
		return prioritys;
	}

	public void setPrioritys(final Short prioritys) {
		this.prioritys = prioritys;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(final String accountName) {
		this.accountName = accountName;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(final String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public Long getBankAcctType() {
		return bankAcctType;
	}

	public void setBankAcctType(final Long bankAcctType) {
		this.bankAcctType = bankAcctType;
	}

	public String getBankKy() {
		return bankKy;
	}

	public void setBankKy(final String bankKy) {
		this.bankKy = bankKy;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(final String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public Short getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(final Short bankProvince) {
		this.bankProvince = bankProvince;
	}

	public Short getBankCity() {
		return bankCity;
	}

	public void setBankCity(final Short bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankPurpose() {
		return bankPurpose;
	}

	public void setBankPurpose(final String bankPurpose) {
		this.bankPurpose = bankPurpose;
	}

	public String getBankRemarks() {
		return bankRemarks;
	}

	public void setBankRemarks(final String bankRemarks) {
		this.bankRemarks = bankRemarks;
	}

	public String getOrderRemarks() {
		return orderRemarks;
	}

	public void setOrderRemarks(final String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(final String moneyType) {
		this.moneyType = moneyType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Long getType() {
		return type;
	}

	public void setType(final Long type) {
		this.type = type;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(final Long status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(final Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getBusiType() {
		return busiType;
	}

	public void setBusiType(final Long busiType) {
		this.busiType = busiType;
	}

	public String getOrderSeqId() {
		return orderSeqId;
	}

	public void setOrderSeqId(final String orderSeqId) {
		this.orderSeqId = orderSeqId;
	}

	public String getFundorigin() {
		return fundorigin;
	}

	public void setFundorigin(final String fundorigin) {
		this.fundorigin = fundorigin;
	}

	public Short getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(final Short withdrawType) {
		this.withdrawType = withdrawType;
	}

	public String getWithdrawBankCode() {
		return withdrawBankCode;
	}

	public void setWithdrawBankCode(final String withdrawBankCode) {
		this.withdrawBankCode = withdrawBankCode;
	}

	public int[] getBusiTypes() {
		return busiTypes;
	}

	public void setBusiTypes(final int[] busiTypes) {
		this.busiTypes = busiTypes;
	}

	public Long getPayerAmount() {
		return payerAmount;
	}

	public void setPayerAmount(final Long payerAmount) {
		this.payerAmount = payerAmount;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(final Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Long getCorderId() {
		return corderId;
	}
	public void setCorderId(final Long corderId) {
		this.corderId = corderId;
	}
	public Long getParentOrderId() {
		return parentOrderId;
	}
	public void setParentOrderId(final Long parentOrderId) {
		this.parentOrderId = parentOrderId;
	}
	public Integer getPayerAcctType() {
		return payerAcctType;
	}
	public void setPayerAcctType(final Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}
	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}
	public void setPayeeAcctType(final Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}
	public String getPayerCurrencyCode() {
		return payerCurrencyCode;
	}
	public void setPayerCurrencyCode(final String payerCurrencyCode) {
		this.payerCurrencyCode = payerCurrencyCode;
	}
	public String getPayeeCurrencyCode() {
		return payeeCurrencyCode;
	}
	public void setPayeeCurrencyCode(final String payeeCurrencyCode) {
		this.payeeCurrencyCode = payeeCurrencyCode;
	}

	
}