package com.pay.txncore.model;

import java.math.BigDecimal;
import java.util.Date;

public class TranDailyReportVo {
	private String orderId; // 商户订单号
	private String orgName; // 渠道名称
	private String tranCurrencyCode; // 交易币种
	private String payRate; // 支付汇率
	private String payCurrencyCode; // 支付币种
	private BigDecimal feeRate; // 收费费汇率
	private String authorisation; // 授权码
	private String merchantNo; // 二级渠道号
	private Integer status; // 状态 0:处理中;1:成功;2:失败

	private String bankPayCurrencyCode; // 渠道支付币种
	private BigDecimal bankPayAmount; // 渠道支付金额

	private BigDecimal bankPerFee; // 银行比例费
	private String bankPerCurrencyCode; // 银行比例费币种
	private BigDecimal bankFixedFee; // 银行固定费
	private String bankFixedCurrencyCode; // 银行固定费币种
	private BigDecimal bankAmount; // 银行存款入账
	private String bankCurrencyCode; // 银行入账币种
	private BigDecimal payCnyRate; // 支付币种转cny汇率
	private BigDecimal bankPayAmountCny; // 银行支付金额cny
	private BigDecimal bankPerFeeCny; // 银行比例费用cny
	private BigDecimal bankFixedFeeCny; // 银行固定费用cny
	private BigDecimal settlementCnyRate; // 清算币种转cny汇率
	private BigDecimal settlementAmountCny; // 清算金额cny
	private BigDecimal baseAmountCny; // 基本户cny
	private BigDecimal assureAmountCny; // 保证金cny
	private BigDecimal fixedFeeCny; // 固定费cny
	private BigDecimal rateRate; // 汇差收益率
	private BigDecimal profitRate; // 毛利率
	private Date settlementDate; // 结算日期
	private String flag; // 0无效1有效
	
	private String currencyCode ;
	
	private Long tranDailyReportNo;
	// 渠道订单号
	private Long channelOrderNo;
	// 支付订单号
	private Long paymentOrderNo;
	// 清算订单号
	private Long partnerSettlementOrderNo;
	// 网关订单号
	private Long tradeOrderNo;
	// 笔数
	private Long tranCount;
	// 交易类型
	private String tranType;
	// 渠道号
	private String orgCode;
	// 交易时间， 取渠道订单交易时间
	private Date createDate;
	// 会员号
	private Long partnerId;
	// 渠道结算金额
	private BigDecimal payAmount;
	// 交易金额
	private BigDecimal tranAmount;
	// 手续费支出
	private BigDecimal tranFee;
	// 清算币种
	private String settlementCurrencyCode;
	// 清算金额
	private BigDecimal settlementAmount;
	// 基本户清算
	private BigDecimal baseAmount;
	// 保证金清算
	private BigDecimal assureAmount;
	// 比例费收入
	private BigDecimal perFee;
	// 固定费收入
	private BigDecimal fixedFee;
	// 汇差收入
	private BigDecimal rateIncome;
	// 总收入
	private BigDecimal totalIncome;
	// 毛利
	private BigDecimal profit;
	//结算汇率
	private BigDecimal settlementRate;
	// 是否dcc DCC ALL
	private String payType;
	// 是否清算 0未清算 1已清算
	private Integer settlementFlg;
	// 是否清算 0未清算 1已清算
	private Integer assuresettlementFlg;
	// 是否对账 0未对账 1已对账
	private Integer reconciliationFlg;
	// 备注
	private String remark;
	//渠道支付币种
	private String channelPayCurrencyCode ;
	//比例费CNY
	private BigDecimal perFeeCny ;
	

	
	/**
	 * @return the perFeeCny
	 */
	public BigDecimal getPerFeeCny() {
		return perFeeCny;
	}

	/**
	 * @param perFeeCny the perFeeCny to set
	 */
	public void setPerFeeCny(BigDecimal perFeeCny) {
		this.perFeeCny = perFeeCny;
	}

	/**
	 * @return the channelPayCurrencyCode
	 */
	public String getChannelPayCurrencyCode() {
		return channelPayCurrencyCode;
	}

	/**
	 * @param channelPayCurrencyCode the channelPayCurrencyCode to set
	 */
	public void setChannelPayCurrencyCode(String channelPayCurrencyCode) {
		this.channelPayCurrencyCode = channelPayCurrencyCode;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}
	
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getTranFee() {
		return tranFee;
	}

	public void setTranFee(BigDecimal tranFee) {
		this.tranFee = tranFee;
	}

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public BigDecimal getBaseAmount() {
		return baseAmount;
	}

	public void setBaseAmount(BigDecimal baseAmount) {
		this.baseAmount = baseAmount;
	}

	public BigDecimal getAssureAmount() {
		return assureAmount;
	}

	public void setAssureAmount(BigDecimal assureAmount) {
		this.assureAmount = assureAmount;
	}

	public BigDecimal getPerFee() {
		return perFee;
	}

	public void setPerFee(BigDecimal perFee) {
		this.perFee = perFee;
	}

	public BigDecimal getFixedFee() {
		return fixedFee;
	}

	public void setFixedFee(BigDecimal fixedFee) {
		this.fixedFee = fixedFee;
	}

	public BigDecimal getRateIncome() {
		return rateIncome;
	}

	public void setRateIncome(BigDecimal rateIncome) {
		this.rateIncome = rateIncome;
	}

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public Long getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(Long channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Integer getSettlementFlg() {
		return settlementFlg;
	}

	public void setSettlementFlg(Integer settlementFlg) {
		this.settlementFlg = settlementFlg;
	}

	public Integer getReconciliationFlg() {
		return reconciliationFlg;
	}

	public void setReconciliationFlg(Integer reconciliationFlg) {
		this.reconciliationFlg = reconciliationFlg;
	}

	public Integer getAssuresettlementFlg() {
		return assuresettlementFlg;
	}

	public void setAssuresettlementFlg(Integer assuresettlementFlg) {
		this.assuresettlementFlg = assuresettlementFlg;
	}

	public Long getTranDailyReportNo() {
		return tranDailyReportNo;
	}

	public void setTranDailyReportNo(Long tranDailyReportNo) {
		this.tranDailyReportNo = tranDailyReportNo;
	}

	public Long getPartnerSettlementOrderNo() {
		return partnerSettlementOrderNo;
	}

	public void setPartnerSettlementOrderNo(Long partnerSettlementOrderNo) {
		this.partnerSettlementOrderNo = partnerSettlementOrderNo;
	}

	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public Long getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(Long paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	public Long getTranCount() {
		return tranCount;
	}

	public void setTranCount(Long tranCount) {
		this.tranCount = tranCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(BigDecimal tranAmount) {
		this.tranAmount = tranAmount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getTranCurrencyCode() {
		return tranCurrencyCode;
	}

	public void setTranCurrencyCode(String tranCurrencyCode) {
		this.tranCurrencyCode = tranCurrencyCode;
	}

	public String getPayRate() {
		return payRate;
	}

	public void setPayRate(String payRate) {
		this.payRate = payRate;
	}

	public String getPayCurrencyCode() {
		return payCurrencyCode;
	}

	public void setPayCurrencyCode(String payCurrencyCode) {
		this.payCurrencyCode = payCurrencyCode;
	}


	/**
	 * @return the feeRate
	 */
	public BigDecimal getFeeRate() {
		return feeRate;
	}

	/**
	 * @param feeRate the feeRate to set
	 */
	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}

	public String getAuthorisation() {
		return authorisation;
	}

	public void setAuthorisation(String authorisation) {
		this.authorisation = authorisation;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBankPerCurrencyCode() {
		return bankPerCurrencyCode;
	}

	public void setBankPerCurrencyCode(String bankPerCurrencyCode) {
		this.bankPerCurrencyCode = bankPerCurrencyCode;
	}

	public String getBankFixedCurrencyCode() {
		return bankFixedCurrencyCode;
	}

	public void setBankFixedCurrencyCode(String bankFixedCurrencyCode) {
		this.bankFixedCurrencyCode = bankFixedCurrencyCode;
	}

	public String getBankCurrencyCode() {
		return bankCurrencyCode;
	}

	public void setBankCurrencyCode(String bankCurrencyCode) {
		this.bankCurrencyCode = bankCurrencyCode;
	}

	
	/**
	 * @return the payCnyRate
	 */
	public BigDecimal getPayCnyRate() {
		return payCnyRate;
	}

	/**
	 * @param payCnyRate the payCnyRate to set
	 */
	public void setPayCnyRate(BigDecimal payCnyRate) {
		this.payCnyRate = payCnyRate;
	}

	
	/**
	 * @return the settlementCnyRate
	 */
	public BigDecimal getSettlementCnyRate() {
		return settlementCnyRate;
	}

	/**
	 * @param settlementCnyRate the settlementCnyRate to set
	 */
	public void setSettlementCnyRate(BigDecimal settlementCnyRate) {
		this.settlementCnyRate = settlementCnyRate;
	}

	/**
	 * @return the settlementAmountCny
	 */
	public BigDecimal getSettlementAmountCny() {
		return settlementAmountCny;
	}

	/**
	 * @param settlementAmountCny the settlementAmountCny to set
	 */
	public void setSettlementAmountCny(BigDecimal settlementAmountCny) {
		this.settlementAmountCny = settlementAmountCny;
	}

	/**
	 * @return the rateRate
	 */
	public BigDecimal getRateRate() {
		return rateRate;
	}

	/**
	 * @param rateRate the rateRate to set
	 */
	public void setRateRate(BigDecimal rateRate) {
		this.rateRate = rateRate;
	}


	/**
	 * @return the profitRate
	 */
	public BigDecimal getProfitRate() {
		return profitRate;
	}

	/**
	 * @param profitRate the profitRate to set
	 */
	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	/**
	 * @return the settlementDate
	 */
	public Date getSettlementDate() {
		return settlementDate;
	}

	/**
	 * @param settlementDate the settlementDate to set
	 */
	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the settlementRate
	 */
	public BigDecimal getSettlementRate() {
		return settlementRate;
	}

	/**
	 * @param settlementRate the settlementRate to set
	 */
	public void setSettlementRate(BigDecimal settlementRate) {
		this.settlementRate = settlementRate;
	}

	public String getBankPayCurrencyCode() {
		return bankPayCurrencyCode;
	}

	public void setBankPayCurrencyCode(String bankPayCurrencyCode) {
		this.bankPayCurrencyCode = bankPayCurrencyCode;
	}

	public BigDecimal getBankPayAmount() {
		return bankPayAmount;
	}

	public void setBankPayAmount(BigDecimal bankPayAmount) {
		this.bankPayAmount = bankPayAmount;
	}

	public BigDecimal getBankPerFee() {
		return bankPerFee;
	}

	public void setBankPerFee(BigDecimal bankPerFee) {
		this.bankPerFee = bankPerFee;
	}

	public BigDecimal getBankFixedFee() {
		return bankFixedFee;
	}

	public void setBankFixedFee(BigDecimal bankFixedFee) {
		this.bankFixedFee = bankFixedFee;
	}

	public BigDecimal getBankAmount() {
		return bankAmount;
	}

	public void setBankAmount(BigDecimal bankAmount) {
		this.bankAmount = bankAmount;
	}

	public BigDecimal getBankPayAmountCny() {
		return bankPayAmountCny;
	}

	public void setBankPayAmountCny(BigDecimal bankPayAmountCny) {
		this.bankPayAmountCny = bankPayAmountCny;
	}

	public BigDecimal getBankPerFeeCny() {
		return bankPerFeeCny;
	}

	public void setBankPerFeeCny(BigDecimal bankPerFeeCny) {
		this.bankPerFeeCny = bankPerFeeCny;
	}

	public BigDecimal getBankFixedFeeCny() {
		return bankFixedFeeCny;
	}

	public void setBankFixedFeeCny(BigDecimal bankFixedFeeCny) {
		this.bankFixedFeeCny = bankFixedFeeCny;
	}

	public BigDecimal getBaseAmountCny() {
		return baseAmountCny;
	}

	public void setBaseAmountCny(BigDecimal baseAmountCny) {
		this.baseAmountCny = baseAmountCny;
	}

	public BigDecimal getAssureAmountCny() {
		return assureAmountCny;
	}

	public void setAssureAmountCny(BigDecimal assureAmountCny) {
		this.assureAmountCny = assureAmountCny;
	}

	public BigDecimal getFixedFeeCny() {
		return fixedFeeCny;
	}

	public void setFixedFeeCny(BigDecimal fixedFeeCny) {
		this.fixedFeeCny = fixedFeeCny;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TranDailyReportVo [orderId=" + orderId + ", orgName=" + orgName
				+ ", tranCurrencyCode=" + tranCurrencyCode + ", payRate="
				+ payRate + ", payCurrencyCode=" + payCurrencyCode
				+ ", feeRate=" + feeRate + ", authorisation=" + authorisation
				+ ", merchantNo=" + merchantNo + ", status=" + status
				+ ", bankPayCurrencyCode=" + bankPayCurrencyCode
				+ ", bankPayAmount=" + bankPayAmount + ", bankPerFee="
				+ bankPerFee + ", bankPerCurrencyCode=" + bankPerCurrencyCode
				+ ", bankFixedFee=" + bankFixedFee + ", bankFixedCurrencyCode="
				+ bankFixedCurrencyCode + ", bankAmount=" + bankAmount
				+ ", bankCurrencyCode=" + bankCurrencyCode + ", payCnyRate="
				+ payCnyRate + ", bankPayAmountCny=" + bankPayAmountCny
				+ ", bankPerFeeCny=" + bankPerFeeCny + ", bankFixedFeeCny="
				+ bankFixedFeeCny + ", settlementCnyRate=" + settlementCnyRate
				+ ", settlementAmountCny=" + settlementAmountCny
				+ ", baseAmountCny=" + baseAmountCny + ", assureAmountCny="
				+ assureAmountCny + ", fixedFeeCny=" + fixedFeeCny
				+ ", rateRate=" + rateRate + ", profitRate=" + profitRate
				+ ", settlementDate=" + settlementDate + ", flag=" + flag
				+ ", currencyCode=" + currencyCode + ", tranDailyReportNo="
				+ tranDailyReportNo + ", channelOrderNo=" + channelOrderNo
				+ ", paymentOrderNo=" + paymentOrderNo
				+ ", partnerSettlementOrderNo=" + partnerSettlementOrderNo
				+ ", tradeOrderNo=" + tradeOrderNo + ", tranCount=" + tranCount
				+ ", tranType=" + tranType + ", orgCode=" + orgCode
				+ ", createDate=" + createDate + ", partnerId=" + partnerId
				+ ", payAmount=" + payAmount + ", tranAmount=" + tranAmount
				+ ", tranFee=" + tranFee + ", settlementCurrencyCode="
				+ settlementCurrencyCode + ", settlementAmount="
				+ settlementAmount + ", baseAmount=" + baseAmount
				+ ", assureAmount=" + assureAmount + ", perFee=" + perFee
				+ ", fixedFee=" + fixedFee + ", rateIncome=" + rateIncome
				+ ", totalIncome=" + totalIncome + ", profit=" + profit
				+ ", settlementRate=" + settlementRate + ", payType=" + payType
				+ ", settlementFlg=" + settlementFlg + ", assuresettlementFlg="
				+ assuresettlementFlg + ", reconciliationFlg="
				+ reconciliationFlg + ", remark=" + remark
				+ ", channelPayCurrencyCode=" + channelPayCurrencyCode + "]";
	}
	
	
}
