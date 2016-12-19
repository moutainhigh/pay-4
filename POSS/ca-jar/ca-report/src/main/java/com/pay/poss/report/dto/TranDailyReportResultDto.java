package com.pay.poss.report.dto;

/**
 * 
 * @author JiangboPeng
 * 
 * 该对象用于交易日报表查询结果映射使用
 * 由于系统sql对金额类直接进行格式化成9,999.00的String格式，
 * 故该查询结果对象统一对金额定义成String类型
 */
import java.math.BigDecimal;

public class TranDailyReportResultDto {

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
//	private Date createDate;
	private String createDate;
	// 交易时间
//	private Date createTime ;
	private String createTime ;
	// 会员号
	private Long partnerId;
	//交易币种
	private String currencyCode;
	// 交易金额
	//private BigDecimal tranAmount;
	private String tranAmount ;
	private String tranCurrencyCode; // 交易币种
	// 渠道结算金额
//	private BigDecimal payAmount;
	private String payAmount ;
	private String payRate; // 支付汇率
	private String payCurrencyCode; // 支付币种
	private String feeRate; // 收费费汇率
	// 清算币种
	private String settlementCurrencyCode;
	// 清算金额
//	private BigDecimal settlementAmount;
	private String settlementAmount ;
	//清算汇率
	private String settlementRate;
	// 比例费收入
//	private BigDecimal perFee;
	private String perFee;
	private String authorisation; // 授权码
	private String merchantNo; // 二级渠道号
	private String status; // 状态 0:处理中;1:成功;2:失败
//	private BigDecimal bankPerFee; // 银行比例费
	private String bankPerFee; // 银行比例费
	private String bankPerCurrencyCode; // 银行比例费币种
//	private BigDecimal bankFixedFee; // 银行固定费
	private String bankFixedFee; // 银行固定费
	private String bankFixedCurrencyCode; // 银行固定费币种
//	private BigDecimal bankAmount; // 银行存款入账
	private String bankAmount; // 银行存款入账
	private String bankCurrencyCode; // 银行入账币种
	private String payCnyRate; // 支付币种转cny汇率
//	private BigDecimal bankPayAmountCny; // 银行支付金额cny
	private String bankPayAmountCny; // 银行支付金额cny
//	private BigDecimal bankPerFeeCny; // 银行比例费用cny
	private String bankPerFeeCny; // 银行比例费用cny
	// 基本户清算
//	private BigDecimal baseAmount;
	private String baseAmount;
	// 保证金清算
//	private BigDecimal assureAmount;
	private String assureAmount;
	// 固定费收入
//	private BigDecimal fixedFee;
	private String fixedFee;
	private String settlementCnyRate; // 清算币种转cny汇率
//	private BigDecimal settlementAmountCny; // 清算金额cny
	private String settlementAmountCny; // 清算金额cny
//	private BigDecimal baseAmountCny; // 基本户cny
	private String baseAmountCny; // 基本户cny
	private BigDecimal bankFixedFeeCny; // 银行固定费用cny
//	private BigDecimal assureAmountCny; // 保证金cny
	private String assureAmountCny; // 保证金cny
//	private BigDecimal fixedFeeCny; // 固定费cny
	private String fixedFeeCny; // 固定费cny
	// 汇差收入
//	private BigDecimal rateIncome;
	private String rateIncome;
	private String rateRate; // 汇差收益率
	// 总收入
//	private BigDecimal totalIncome;
	private String totalIncome;
	// 毛利
//	private BigDecimal profit;
	private String profit;
	private String profitRate; // 毛利率
	// 是否dcc DCC ALL
	private String payType;
	private String settlementDate; // 结算日期
	private String flag; // 0无效1有效
	// 是否清算 0未清算 1已清算
//	private Integer settlementFlg;
	private String settlementFlg;
	// 是否对账 0未对账 1已对账
//	private Integer reconciliationFlg;
	private String reconciliationFlg;
	// 是否清算 0未清算 1已清算
//	private Integer assuresettlementFlg;
	private String assuresettlementFlg;
	// 备注
	private String remark;
	//比例费CNY
	private String perFeeCny ;

	/**
	 * @return the perFeeCny
	 */
	public String getPerFeeCny() {
		return perFeeCny;
	}

	/**
	 * @param perFeeCny the perFeeCny to set
	 */
	public void setPerFeeCny(String perFeeCny) {
		this.perFeeCny = perFeeCny;
	}

	private String bankPayCurrencyCode; // 渠道支付币种
	private BigDecimal bankPayAmount; // 渠道支付金额
	// 笔数
	private Long tranCount;
	// 手续费支出
//	private BigDecimal tranFee;
	private String tranFee;
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

	

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	
	/**
	 * @return the tranFee
	 */
	public String getTranFee() {
		return tranFee;
	}

	/**
	 * @param tranFee the tranFee to set
	 */
	public void setTranFee(String tranFee) {
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

	/**
	 * @return the settlementFlg
	 */
	public String getSettlementFlg() {
		return settlementFlg;
	}

	/**
	 * @param settlementFlg the settlementFlg to set
	 */
	public void setSettlementFlg(String settlementFlg) {
		this.settlementFlg = settlementFlg;
	}

	/**
	 * @return the reconciliationFlg
	 */
	public String getReconciliationFlg() {
		return reconciliationFlg;
	}

	/**
	 * @param reconciliationFlg the reconciliationFlg to set
	 */
	public void setReconciliationFlg(String reconciliationFlg) {
		this.reconciliationFlg = reconciliationFlg;
	}

	/**
	 * @return the assuresettlementFlg
	 */
	public String getAssuresettlementFlg() {
		return assuresettlementFlg;
	}

	/**
	 * @param assuresettlementFlg the assuresettlementFlg to set
	 */
	public void setAssuresettlementFlg(String assuresettlementFlg) {
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


	public BigDecimal getBankFixedFeeCny() {
		return bankFixedFeeCny;
	}

	public void setBankFixedFeeCny(BigDecimal bankFixedFeeCny) {
		this.bankFixedFeeCny = bankFixedFeeCny;
	}

	/**
	 * @return the tranAmount
	 */
	public String getTranAmount() {
		return tranAmount;
	}

	/**
	 * @param tranAmount the tranAmount to set
	 */
	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}

	/**
	 * @return the payAmount
	 */
	public String getPayAmount() {
		return payAmount;
	}

	/**
	 * @param payAmount the payAmount to set
	 */
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	/**
	 * @return the settlementAmount
	 */
	public String getSettlementAmount() {
		return settlementAmount;
	}

	/**
	 * @param settlementAmount the settlementAmount to set
	 */
	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	/**
	 * @return the perFee
	 */
	public String getPerFee() {
		return perFee;
	}

	/**
	 * @param perFee the perFee to set
	 */
	public void setPerFee(String perFee) {
		this.perFee = perFee;
	}

	/**
	 * @return the bankPerFee
	 */
	public String getBankPerFee() {
		return bankPerFee;
	}

	/**
	 * @param bankPerFee the bankPerFee to set
	 */
	public void setBankPerFee(String bankPerFee) {
		this.bankPerFee = bankPerFee;
	}

	/**
	 * @return the bankFixedFee
	 */
	public String getBankFixedFee() {
		return bankFixedFee;
	}

	/**
	 * @param bankFixedFee the bankFixedFee to set
	 */
	public void setBankFixedFee(String bankFixedFee) {
		this.bankFixedFee = bankFixedFee;
	}

	/**
	 * @return the bankAmount
	 */
	public String getBankAmount() {
		return bankAmount;
	}

	/**
	 * @param bankAmount the bankAmount to set
	 */
	public void setBankAmount(String bankAmount) {
		this.bankAmount = bankAmount;
	}

	/**
	 * @return the bankPayAmountCny
	 */
	public String getBankPayAmountCny() {
		return bankPayAmountCny;
	}

	/**
	 * @param bankPayAmountCny the bankPayAmountCny to set
	 */
	public void setBankPayAmountCny(String bankPayAmountCny) {
		this.bankPayAmountCny = bankPayAmountCny;
	}

	/**
	 * @return the bankPerFeeCny
	 */
	public String getBankPerFeeCny() {
		return bankPerFeeCny;
	}

	/**
	 * @param bankPerFeeCny the bankPerFeeCny to set
	 */
	public void setBankPerFeeCny(String bankPerFeeCny) {
		this.bankPerFeeCny = bankPerFeeCny;
	}

	/**
	 * @return the baseAmount
	 */
	public String getBaseAmount() {
		return baseAmount;
	}

	/**
	 * @param baseAmount the baseAmount to set
	 */
	public void setBaseAmount(String baseAmount) {
		this.baseAmount = baseAmount;
	}

	/**
	 * @return the assureAmount
	 */
	public String getAssureAmount() {
		return assureAmount;
	}

	/**
	 * @param assureAmount the assureAmount to set
	 */
	public void setAssureAmount(String assureAmount) {
		this.assureAmount = assureAmount;
	}

	/**
	 * @return the fixedFee
	 */
	public String getFixedFee() {
		return fixedFee;
	}

	/**
	 * @param fixedFee the fixedFee to set
	 */
	public void setFixedFee(String fixedFee) {
		this.fixedFee = fixedFee;
	}

	/**
	 * @return the settlementAmountCny
	 */
	public String getSettlementAmountCny() {
		return settlementAmountCny;
	}

	/**
	 * @param settlementAmountCny the settlementAmountCny to set
	 */
	public void setSettlementAmountCny(String settlementAmountCny) {
		this.settlementAmountCny = settlementAmountCny;
	}

	/**
	 * @return the baseAmountCny
	 */
	public String getBaseAmountCny() {
		return baseAmountCny;
	}

	/**
	 * @param baseAmountCny the baseAmountCny to set
	 */
	public void setBaseAmountCny(String baseAmountCny) {
		this.baseAmountCny = baseAmountCny;
	}

	/**
	 * @return the assureAmountCny
	 */
	public String getAssureAmountCny() {
		return assureAmountCny;
	}

	/**
	 * @param assureAmountCny the assureAmountCny to set
	 */
	public void setAssureAmountCny(String assureAmountCny) {
		this.assureAmountCny = assureAmountCny;
	}

	/**
	 * @return the fixedFeeCny
	 */
	public String getFixedFeeCny() {
		return fixedFeeCny;
	}

	/**
	 * @param fixedFeeCny the fixedFeeCny to set
	 */
	public void setFixedFeeCny(String fixedFeeCny) {
		this.fixedFeeCny = fixedFeeCny;
	}

	/**
	 * @return the rateIncome
	 */
	public String getRateIncome() {
		return rateIncome;
	}

	/**
	 * @param rateIncome the rateIncome to set
	 */
	public void setRateIncome(String rateIncome) {
		this.rateIncome = rateIncome;
	}

	/**
	 * @return the totalIncome
	 */
	public String getTotalIncome() {
		return totalIncome;
	}

	/**
	 * @param totalIncome the totalIncome to set
	 */
	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}

	/**
	 * @return the profit
	 */
	public String getProfit() {
		return profit;
	}

	/**
	 * @param profit the profit to set
	 */
	public void setProfit(String profit) {
		this.profit = profit;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	
	
}
