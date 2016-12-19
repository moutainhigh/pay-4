package com.pay.fundout.bankfile.generator.model;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author lIWEI
 * @Date 2011-6-13
 * @Description
 * @Copyright Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 */
public class FileDetailMode extends BaseObject implements Comparable<Object> {
	private static final long serialVersionUID = 1L;
	private String workorderKy;//工单流水号
	/*
	 * 充退相关
	 */
	private String rechargeBankSeq;// 充值银行流水 //交易流水号
	private String memberName;// 会员姓名 //收款人
	private String memberCode;// 会员号
	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	private String rechargeDate; // 充值时间
	private Date rechargeTime; // 充值时间
	private String rechargeBank;// 充值银行。见码表“银行编码”，当不是通过银行充值，此值为“系统银行”。 //银行名称
	private BigDecimal applyAmount;// 此订单充退实际金额，必须<=APPLY_MAX。 //汇款金额
	private String applyRemark;// 充退批注 //交易备注
	private BigDecimal rechargeAmount; // 充值金额
	private String refundOrderSeq; // 充退流水号
	private String refundDate; // 充退申请时间
	private String rechargeBankOrder; // 银行订单号
	private String unionBankCode; // 联行号
	private Date applyTimes;// 申请冲退时间
	private String detailKy;
	private String productOrderSeq;
	private String rechargeOrderSeq;
	private String showApplyAmount;
	private String showRechargeAmount;
	private String depositTypeName;
	private Long acctType;//账户类型
	
	private String currencyCode;//账户类型
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getAcctType() {
		return acctType;
	}

	public void setAcctType(Long acctType) {
		this.acctType = acctType;
	}

	public String getDepositTypeName() {
		return depositTypeName;
	}

	public void setDepositTypeName(String depositTypeName) {
		this.depositTypeName = depositTypeName;
	}

	public String getRechargeBankSeq() {
		return rechargeBankSeq;
	}

	public void setRechargeBankSeq(String rechargeBankSeq) {
		this.rechargeBankSeq = rechargeBankSeq;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getRechargeDate() {
		return rechargeDate;
	}

	public void setRechargeDate(String rechargeDate) {
		this.rechargeDate = rechargeDate;
	}

	public Date getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	/**
	 * @return the detailKy
	 */
	public String getDetailKy() {
		return detailKy;
	}

	/**
	 * @param detailKy the detailKy to set
	 */
	public void setDetailKy(String detailKy) {
		this.detailKy = detailKy;
	}

	/**
	 * @return the productOrderSeq
	 */
	public String getProductOrderSeq() {
		return productOrderSeq;
	}

	/**
	 * @param productOrderSeq the productOrderSeq to set
	 */
	public void setProductOrderSeq(String productOrderSeq) {
		this.productOrderSeq = productOrderSeq;
	}

	/**
	 * @return the rechargeOrderSeq
	 */
	public String getRechargeOrderSeq() {
		return rechargeOrderSeq;
	}

	/**
	 * @param rechargeOrderSeq the rechargeOrderSeq to set
	 */
	public void setRechargeOrderSeq(String rechargeOrderSeq) {
		this.rechargeOrderSeq = rechargeOrderSeq;
	}

	/*
	 * 出款相关
	 */
	private String batchNum; // 批次号
	private BigDecimal amount; // 提现金额
	private Short prioritys; // 优先级.值越大越优先.默认为5
	private String accountName; // 收款人名称
	private String bankAcct; // 银行账户
	private Long bankAcctType; // 银行账户类型。默认借记卡账户
	private String bankKy; // 提现银行ID,关联到银行基础表
	private String bankBranch; // 开户支行名称
	private Short bankProvince; // 开户行所在省份
	private Short bankCity; // 开户行所在城市
	private String orderRemarks; // 交易备注
	private String moneyType; // 币种.默认CNY
	private Integer status;
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间
	private Long busiType; // 业务类型 0：提现,1：批量出款,2：信用卡还款
	private String orderSeqId; // 进入出款系统的订单号
	private String orderSeq; // 出款订单号
	private String fundorigin;// 发起出款请求来源
	private Short withdrawType; // 出款方式
	private String withdrawBankCode; // 出款银行编号
	private Integer tradeType; // 交易类型
	private String tradeSeq; // 订单流水
	private String bankName;
	private String provinceName;
	private String cityName;
	private String bankNumber;
	/**
	 * @return the workorderKy
	 */
	public String getWorkorderKy() {
		return workorderKy;
	}

	/**
	 * @param workorderKy the workorderKy to set
	 */
	public void setWorkorderKy(String workorderKy) {
		this.workorderKy = workorderKy;
	}

	private String showAmount;

	/**
	 * 抽出子类共有熟悉包括付款方相关信息和用途
	 */
	private String payerAccNo;
	private String payerAccBranch;
	private String payerAccName;
	private String use;

	/**
	 * @return the payerAccNo
	 */
	public String getPayerAccNo() {
		return payerAccNo;
	}

	/**
	 * @param payerAccNo
	 *            the payerAccNo to set
	 */
	public void setPayerAccNo(String payerAccNo) {
		this.payerAccNo = payerAccNo;
	}

	/**
	 * @return the payerAccBranch
	 */
	public String getPayerAccBranch() {
		return payerAccBranch;
	}

	/**
	 * @param payerAccBranch
	 *            the payerAccBranch to set
	 */
	public void setPayerAccBranch(String payerAccBranch) {
		this.payerAccBranch = payerAccBranch;
	}

	/**
	 * @return the payerAccName
	 */
	public String getPayerAccName() {
		return payerAccName;
	}

	/**
	 * @param payerAccName
	 *            the payerAccName to set
	 */
	public void setPayerAccName(String payerAccName) {
		this.payerAccName = payerAccName;
	}

	/**
	 * @return the use
	 */
	public String getUse() {
		return use;
	}

	/**
	 * @param use
	 *            the use to set
	 */
	public void setUse(String use) {
		this.use = use;
	}

	@Override
	public int compareTo(Object o) {
		if (null != o) {
			return 0;
		}

		if (o instanceof FileDetailMode) {
			FileDetailMode temp = (FileDetailMode) o;
			return this.bankKy.compareTo(temp.getBankKy());
		} else {
			return 0;
		}
	}

	public String getRechargeBankOrder() {
		return rechargeBankOrder;
	}

	public void setRechargeBankOrder(String rechargeBankOrder) {
		this.rechargeBankOrder = rechargeBankOrder;
	}

	public String getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}

	public String getRefundOrderSeq() {
		return refundOrderSeq;
	}

	public void setRefundOrderSeq(String refundOrderSeq) {
		this.refundOrderSeq = refundOrderSeq;
	}

	public BigDecimal getRechargeAmount() {
		if (null == this.rechargeAmount) {
			return new BigDecimal(0.00);
		}
		return rechargeAmount;
	}

	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public String getRechargeBank() {
		return rechargeBank;
	}

	public void setRechargeBank(String rechargeBank) {
		this.rechargeBank = rechargeBank;
	}

	public BigDecimal getApplyAmount() {
		if (null == this.applyAmount) {
			return new BigDecimal(0.00);
		}
		return applyAmount;
	}

	public String getUnionBankCode() {
		return unionBankCode;
	}

	public void setUnionBankCode(String unionBankCode) {
		this.unionBankCode = unionBankCode;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getApplyRemark() {
		return applyRemark;
	}

	public void setApplyRemark(String applyRemark) {
		this.applyRemark = applyRemark;
	}

	public Date getApplyTimes() {
		return applyTimes;
	}

	public void setApplyTimes(Date applyTimes) {
		this.applyTimes = applyTimes;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}


	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
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


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeSeq() {
		return tradeSeq;
	}

	public void setTradeSeq(String tradeSeq) {
		this.tradeSeq = tradeSeq;
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

	public String getShowAmount() {
		return showAmount;
	}

	public void setShowAmount(String showAmount) {
		this.showAmount = showAmount;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}
	
	public String getShowApplyAmount() {
		if(null != applyAmount){
			this.showApplyAmount = applyAmount.toString();
		}else{
			this.showApplyAmount = "0.00";
		}
		return showApplyAmount;
	}

	public void setShowApplyAmount(String showApplyAmount) {
		this.showApplyAmount = showApplyAmount;
	}

	public String getShowRechargeAmount() {
		if(null != rechargeAmount){
			this.showRechargeAmount = rechargeAmount.toString();
		}else{
			this.showRechargeAmount = "0.00";
		}
		return showRechargeAmount;
	}

	public void setShowRechargeAmount(String showRechargeAmount) {
		this.showRechargeAmount = showRechargeAmount;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
}
