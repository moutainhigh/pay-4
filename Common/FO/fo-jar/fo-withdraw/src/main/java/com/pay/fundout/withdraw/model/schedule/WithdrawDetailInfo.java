package com.pay.fundout.withdraw.model.schedule;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * 产生批次所用DTO
 * 
 * @Description
 * @project poss-refund
 * @file RefundBatchDTO.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-9-17 Volcano.Wu Create
 */
public class WithdrawDetailInfo extends BaseObject {

	private static final long serialVersionUID = 1850455424980407536L;

	private Long rechargeOrderSeq;// 充值流水 //交易号
	private Long rechargeBankSeq;// 充值银行流水 //交易流水号
	private String rechargeBank;// 充值银行。见码表“银行编码”，当不是通过银行充值，此值为“系统银行”。 //银行名称
	private BigDecimal applyAmount;// 此订单充退实际金额，必须<=APPLY_MAX。 //汇款金额
	private String memberName;// 会员姓名 //收款人
	private String applyRemark;// 充退批注 //交易备注
	
	//批次号
	private String batchNum;	

	// 银行备注
	// 银行用途
	// 失败原因

	private String orderSeqToStr;
	
	private String bankSeqToStr;
	
	public String getOrderSeqToStr() {
		if(null != this.rechargeOrderSeq){
			return this.rechargeOrderSeq.toString();
		}else{
			return "";
		}
	}

	public void setOrderSeqToStr(String orderSeqToStr) {
		this.orderSeqToStr = orderSeqToStr;
	}

	public String getBankSeqToStr() {
		if(null != this.getRechargeBankSeq()){
			return this.rechargeBankSeq.toString();
		}else{
			return "";
		}
	}

	public void setBankSeqToStr(String bankSeqToStr) {
		this.bankSeqToStr = bankSeqToStr;
	}

	/**
	 * @return the batchNum
	 */
	public String getBatchNum() {
		return batchNum;
	}

	/**
	 * @param batchNum the batchNum to set
	 */
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

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
	
	private String bankName;
	
	private String provinceName;
	
	private String cityName;
	
	private BigDecimal showAmount;
	
	public Long getRechargeOrderSeq() {
		return rechargeOrderSeq;
	}

	public void setRechargeOrderSeq(Long rechargeOrderSeq) {
		this.rechargeOrderSeq = rechargeOrderSeq;
	}

	public Long getRechargeBankSeq() {
		return rechargeBankSeq;
	}

	public void setRechargeBankSeq(Long rechargeBankSeq) {
		this.rechargeBankSeq = rechargeBankSeq;
	}

	public String getRechargeBank() {
		return rechargeBank;
	}

	public void setRechargeBank(String rechargeBank) {
		this.rechargeBank = rechargeBank;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	/**
	 * @return the sequenceId
	 */
	public Long getSequenceId() {
		return sequenceId;
	}

	/**
	 * @param sequenceId the sequenceId to set
	 */
	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode the memberCode to set
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the memberType
	 */
	public Long getMemberType() {
		return memberType;
	}

	/**
	 * @param memberType the memberType to set
	 */
	public void setMemberType(Long memberType) {
		this.memberType = memberType;
	}

	/**
	 * @return the memberAcc
	 */
	public String getMemberAcc() {
		return memberAcc;
	}

	/**
	 * @param memberAcc the memberAcc to set
	 */
	public void setMemberAcc(String memberAcc) {
		this.memberAcc = memberAcc;
	}

	/**
	 * @return the memberAccType
	 */
	public Long getMemberAccType() {
		return memberAccType;
	}

	/**
	 * @param memberAccType the memberAccType to set
	 */
	public void setMemberAccType(Long memberAccType) {
		this.memberAccType = memberAccType;
	}

	/**
	 * @return the amount
	 */
	public Long getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 * @return the prioritys
	 */
	public Short getPrioritys() {
		return prioritys;
	}

	/**
	 * @param prioritys the prioritys to set
	 */
	public void setPrioritys(Short prioritys) {
		this.prioritys = prioritys;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the bankAcct
	 */
	public String getBankAcct() {
		return bankAcct;
	}

	/**
	 * @param bankAcct the bankAcct to set
	 */
	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	/**
	 * @return the bankAcctType
	 */
	public Long getBankAcctType() {
		return bankAcctType;
	}

	/**
	 * @param bankAcctType the bankAcctType to set
	 */
	public void setBankAcctType(Long bankAcctType) {
		this.bankAcctType = bankAcctType;
	}

	/**
	 * @return the bankKy
	 */
	public String getBankKy() {
		return bankKy;
	}

	/**
	 * @param bankKy the bankKy to set
	 */
	public void setBankKy(String bankKy) {
		this.bankKy = bankKy;
	}

	/**
	 * @return the bankBranch
	 */
	public String getBankBranch() {
		return bankBranch;
	}

	/**
	 * @param bankBranch the bankBranch to set
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * @return the bankProvince
	 */
	public Short getBankProvince() {
		return bankProvince;
	}

	/**
	 * @param bankProvince the bankProvince to set
	 */
	public void setBankProvince(Short bankProvince) {
		this.bankProvince = bankProvince;
	}

	/**
	 * @return the bankCity
	 */
	public Short getBankCity() {
		return bankCity;
	}

	/**
	 * @param bankCity the bankCity to set
	 */
	public void setBankCity(Short bankCity) {
		this.bankCity = bankCity;
	}

	/**
	 * @return the bankPurpose
	 */
	public String getBankPurpose() {
		return bankPurpose;
	}

	/**
	 * @param bankPurpose the bankPurpose to set
	 */
	public void setBankPurpose(String bankPurpose) {
		this.bankPurpose = bankPurpose;
	}

	/**
	 * @return the bankRemarks
	 */
	public String getBankRemarks() {
		return bankRemarks;
	}

	/**
	 * @param bankRemarks the bankRemarks to set
	 */
	public void setBankRemarks(String bankRemarks) {
		this.bankRemarks = bankRemarks;
	}

	/**
	 * @return the orderRemarks
	 */
	public String getOrderRemarks() {
		return orderRemarks;
	}

	/**
	 * @param orderRemarks the orderRemarks to set
	 */
	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	/**
	 * @return the moneyType
	 */
	public String getMoneyType() {
		return moneyType;
	}

	/**
	 * @param moneyType the moneyType to set
	 */
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the type
	 */
	public Long getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Long type) {
		this.type = type;
	}

	/**
	 * @return the status
	 */
	public Long getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the busiType
	 */
	public Long getBusiType() {
		return busiType;
	}

	/**
	 * @param busiType the busiType to set
	 */
	public void setBusiType(Long busiType) {
		this.busiType = busiType;
	}

	/**
	 * @return the orderSeqId
	 */
	public String getOrderSeqId() {
		return orderSeqId;
	}

	/**
	 * @param orderSeqId the orderSeqId to set
	 */
	public void setOrderSeqId(String orderSeqId) {
		this.orderSeqId = orderSeqId;
	}

	/**
	 * @return the fundorigin
	 */
	public String getFundorigin() {
		return fundorigin;
	}

	/**
	 * @param fundorigin the fundorigin to set
	 */
	public void setFundorigin(String fundorigin) {
		this.fundorigin = fundorigin;
	}

	/**
	 * @return the withdrawType
	 */
	public Short getWithdrawType() {
		return withdrawType;
	}

	/**
	 * @param withdrawType the withdrawType to set
	 */
	public void setWithdrawType(Short withdrawType) {
		this.withdrawType = withdrawType;
	}

	/**
	 * @return the withdrawBankCode
	 */
	public String getWithdrawBankCode() {
		return withdrawBankCode;
	}

	/**
	 * @param withdrawBankCode the withdrawBankCode to set
	 */
	public void setWithdrawBankCode(String withdrawBankCode) {
		this.withdrawBankCode = withdrawBankCode;
	}

	/**
	 * @return the tradeType
	 */
	public Integer getTradeType() {
		return tradeType;
	}

	/**
	 * @param tradeType the tradeType to set
	 */
	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	/**
	 * @return the tradeSeq
	 */
	public String getTradeSeq() {
		return tradeSeq;
	}

	/**
	 * @param tradeSeq the tradeSeq to set
	 */
	public void setTradeSeq(String tradeSeq) {
		this.tradeSeq = tradeSeq;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getApplyRemark() {
		return applyRemark;
	}

	public void setApplyRemark(String applyRemark) {
		this.applyRemark = applyRemark;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public BigDecimal getShowAmount() {
		return showAmount;
	}

	public void setShowAmount(BigDecimal showAmount) {
		this.showAmount = showAmount;
	}
}
