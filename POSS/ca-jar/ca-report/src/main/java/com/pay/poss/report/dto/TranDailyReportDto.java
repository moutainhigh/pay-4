package com.pay.poss.report.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TranDailyReportDto {

	private Long tranDailyReportNo;
	// 渠道订单号
	private Long channelOrderNo;
	// 网关订单号
	private Long tradeOrderNo;
	// 支付订单号
	private Long paymentOrderNo;
	private String orderId; // 商户订单号
	// 清算订单号
	private Long partnerSettlementOrderNo;
	// 交易类型
	private String tranType;
	// 渠道号
	private String orgCode;
	private String orgName; // 渠道名称
	// 交易日期
	private Date createDate;
	// 交易时间
	private Date createTime ;
	// 会员号
	private Long partnerId;
	//交易币种
	private String currencyCode;
	// 交易金额
	private BigDecimal tranAmount;
	private String tranCurrencyCode; // 交易币种
	// 渠道结算金额
	private BigDecimal payAmount;
	private String payRate; // 支付汇率
	private String payCurrencyCode; // 支付币种
	private String feeRate; // 收费费汇率
	// 清算币种
	private String settlementCurrencyCode;
	// 清算金额
	private BigDecimal settlementAmount;
	//清算汇率
	private String settlementRate;
	// 比例费收入
	private BigDecimal perFee;
	private String authorisation; // 授权码
	private String merchantNo; // 二级渠道号
	private String status; // 状态 0:处理中;1:成功;2:失败
	private BigDecimal bankPerFee; // 银行比例费
	private String bankPerCurrencyCode; // 银行比例费币种
	private BigDecimal bankFixedFee; // 银行固定费
	private String bankFixedCurrencyCode; // 银行固定费币种
	private BigDecimal bankAmount; // 银行存款入账
	private String bankCurrencyCode; // 银行入账币种
	private String payCnyRate; // 支付币种转cny汇率
	private BigDecimal bankPayAmountCny; // 银行支付金额cny
	private BigDecimal bankPerFeeCny; // 银行比例费用cny
	// 基本户清算
	private BigDecimal baseAmount;
	// 保证金清算
	private BigDecimal assureAmount;
	// 固定费收入
	private BigDecimal fixedFee;
	private String settlementCnyRate; // 清算币种转cny汇率
	private String settlementAmountCny; // 清算金额cny
	private BigDecimal baseAmountCny; // 基本户cny
	private BigDecimal bankFixedFeeCny; // 银行固定费用cny
	private BigDecimal assureAmountCny; // 保证金cny
	private BigDecimal fixedFeeCny; // 固定费cny
	// 汇差收入
	private BigDecimal rateIncome;
	private String rateRate; // 汇差收益率
	// 总收入
	private BigDecimal totalIncome;
	// 毛利
	private BigDecimal profit;
	private String profitRate; // 毛利率
	// 是否dcc DCC ALL
	private String payType;
	private String settlementDate; // 结算日期
	private String flag; // 0无效1有效
	// 是否清算 0未清算 1已清算
	private Integer settlementFlg;
	// 是否对账 0未对账 1已对账
	private Integer reconciliationFlg;
	// 是否清算 0未清算 1已清算
	private Integer assuresettlementFlg;
	// 备注
	private String remark;

	
	
	
	
	private String bankPayCurrencyCode; // 渠道支付币种
	private BigDecimal bankPayAmount; // 渠道支付金额
	// 笔数
	private Long tranCount;
	// 手续费支出
	private BigDecimal tranFee;
	// 银行存款入账
	private BigDecimal bankInAmt;
	
	//测试扩展字段
	private String todo ;

	/**
	 * @return the todo
	 */
	public String getTodo() {
		return todo;
	}

	/**
	 * @param todo the todo to set
	 */
	public void setTodo(String todo) {
		this.todo = todo;
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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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

	public BigDecimal getBankInAmt() {
		return bankInAmt;
	}

	public void setBankInAmt(BigDecimal bankInAmt) {
		this.bankInAmt = bankInAmt;
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

	public String getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(String feeRate) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public String getPayCnyRate() {
		return payCnyRate;
	}

	public void setPayCnyRate(String payCnyRate) {
		this.payCnyRate = payCnyRate;
	}

	public String getSettlementCnyRate() {
		return settlementCnyRate;
	}

	public void setSettlementCnyRate(String settlementCnyRate) {
		this.settlementCnyRate = settlementCnyRate;
	}

	public String getSettlementAmountCny() {
		return settlementAmountCny;
	}

	public void setSettlementAmountCny(String settlementAmountCny) {
		this.settlementAmountCny = settlementAmountCny;
	}

	public String getRateRate() {
		return rateRate;
	}

	public void setRateRate(String rateRate) {
		this.rateRate = rateRate;
	}

	public String getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(String profitRate) {
		this.profitRate = profitRate;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getSettlementRate() {
		return settlementRate;
	}

	public void setSettlementRate(String settlementRate) {
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
	
}
