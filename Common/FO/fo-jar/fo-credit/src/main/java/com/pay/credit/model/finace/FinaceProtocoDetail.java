package com.pay.credit.model.finace;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.inf.model.Model;

public class FinaceProtocoDetail implements Model{

	/**商户号**/
	private String merchantCode;
	/**商户名称**/
	private String merchantName;
	/**融资流水号**/
	private String creditOrderId;
	/**清算入账金额=放款金额+利息**/
	private BigDecimal gmtAmount;
	private double dGmtAmount;
	/**清算入账金额=放款金额+利息 总额**/
	private double totalGmtAmount;
	/**订单风险状态***/
	private  String riskStatus;
	/**网关订单号***/
	private Long tradeOrderNo;
	/**订单金额**/
	private BigDecimal orderAmount;
	private double dOrderAmount;
	/**利率**/
	private BigDecimal interst;
	/**利息**/
	private BigDecimal loanAmount;
	private double dloanAmount;
	/**清算日期**/
	private Date setTlement;
	/**放款日期***/
	private Date gmtLoan;
	/**融资状态***/
	private String status;
	/***币种**/
	private String currencyCode;
	/**交易币种**/
	private String tradeCurrencyCode;
	/**单笔订单金额***/
	private String singleOrderAmount;
	/***商户订单号***/
	private String orderId;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSingleOrderAmount() {
		return singleOrderAmount;
	}
	public void setSingleOrderAmount(String singleOrderAmount) {
		this.singleOrderAmount = singleOrderAmount;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getTradeCurrencyCode() {
		return tradeCurrencyCode;
	}
	public void setTradeCurrencyCode(String tradeCurrencyCode) {
		this.tradeCurrencyCode = tradeCurrencyCode;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getCreditOrderId() {
		return creditOrderId;
	}
	public void setCreditOrderId(String creditOrderId) {
		this.creditOrderId = creditOrderId;
	}
	public BigDecimal getGmtAmount() {
		return gmtAmount;
	}
	public void setGmtAmount(BigDecimal gmtAmount) {
		this.gmtAmount = gmtAmount;
	}
	public String getRiskStatus() {
		return riskStatus;
	}
	public void setRiskStatus(String riskStatus) {
		this.riskStatus = riskStatus;
	}
	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}
	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public BigDecimal getInterst() {
		return interst;
	}
	public void setInterst(BigDecimal interst) {
		this.interst = interst;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Date getSetTlement() {
		return setTlement;
	}
	public void setSetTlement(Date setTlement) {
		this.setTlement = setTlement;
	}
	public Date getGmtLoan() {
		return gmtLoan;
	}
	public void setGmtLoan(Date gmtLoan) {
		this.gmtLoan = gmtLoan;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getTotalGmtAmount() {
		return totalGmtAmount;
	}
	public void setTotalGmtAmount(double totalGmtAmount) {
		this.totalGmtAmount = totalGmtAmount;
	}
	public double getdGmtAmount() {
		return dGmtAmount;
	}
	public void setdGmtAmount(double dGmtAmount) {
		this.dGmtAmount = dGmtAmount;
	}
	public double getdOrderAmount() {
		return dOrderAmount;
	}
	public void setdOrderAmount(double dOrderAmount) {
		this.dOrderAmount = dOrderAmount;
	}
	public double getDloanAmount() {
		return dloanAmount;
	}
	public void setDloanAmount(double dloanAmount) {
		this.dloanAmount = dloanAmount;
	}
}
