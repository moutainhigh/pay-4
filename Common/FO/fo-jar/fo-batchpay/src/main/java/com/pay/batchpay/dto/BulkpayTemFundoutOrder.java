/**
 * 
 */
package com.pay.batchpay.dto;

import java.util.Date;

/**
 * 提现，批量付款记录明细
 * @author PengJiangbo
 *
 */
public class BulkpayTemFundoutOrder {

	/**
	 * 订单号
	 */
	private Long orderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 订单子类型
	 */
	private String orderSmallType;
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	
	/**
	 * 订单金额
	 */
	private Long orderAmount;
	/**
	 * 是否是付款方付手续费
	 */
	private Integer isPayerPayFee;
	/**
	 * 手续费
	 */
	private Long fee;
	/**
	 * 实际付款金额
	 */
	private Long realpayAmount;
	/**
	 * 实际出款金额
	 */
	private Long realoutAmount;
	/**
	 * 付款方名称
	 */
	private String payerName;
	/**
	 * 付款方登录标识
	 */
	
	
	
	private String payerLoginName;
	/**
	 * 付款方会员号
	 */
	private Long payerMemberCode;
	/**
	 * 付款方会员类型
	 */
	private Integer payerMemberType;
	/**
	 * 付款方账号
	 */
	private String payerAcctCode;
	/**
	 * 付款方账号类型
	 */
	private Integer payerAcctType;
	/**
	 * 收款方名称
	 */
	private String payeeName;
	/**
	 * 收款方银行账号
	 */
	private String payeeBankAcctCode;
	/**
	 * 收款方银行账号类型
	 */
	private Integer payeeBankAcctType;
	/**
	 * 收款银行代码
	 */
	private String payeeBankCode;
	/**
	 * 收款银行名称
	 */
	private String payeeBankName;
	/**
	 * 收款方开户行名称
	 */
	
	
	private String payeeOpeningBankName;
	/**
	 * 收款行所在省份代码
	 */
	private String payeeBankProvince;
	/**
	 * 收款行所在省份名称
	 */
	private String payeeBankProvinceName;
	/**
	 * 收款行所在城市代码
	 */
	private String payeeBankCity;
	/**
	 * 收款行所在城市名称
	 */
	private String payeeBankCityName;
	/**
	 * 付款理由
	 */
	private String paymentReason;
	/**
	 * 失败的原因
	 */
	private String failedReason;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 更新日期
	 */
	private Date updateDate;
	/**
	 * 交易别名
	 */
	private String tradeAlias;
	/**
	 * 交易类型
	 */
	
	
	private Integer tradeType;
	/**
	 * 收款方手机号
	 */
	private String payeeMobile;
	/**
	 * 银行订单号
	 */
	private String bankOrderId;
	/**
	 * 外部订单号
	 */
	private String foreignOrderId;
	/**
	 * 出款行代码
	 */
	private String fundoutBankCode;
	/**
	 * 出款行名称
	 */
	private String fundoutBankName;
	/**
	 * 出款方式
	 */
	private Integer fundoutMode;
	/**
	 * 优先级权值
	 */
	private Integer priority;
	/**
	 * 总订单号
	 */
	private Long parentOrderId;
	
	private String bankNumber;
	
	
	
	private String riskLevelCode ;
	
	private String accountMode ;
	
	private int isLoaning ;
	
	private String channelCode ;
	
	//批量付款批次号，关联T_BULKPayment_order
	private String bulkpayNo ;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getOrderSmallType() {
		return orderSmallType;
	}

	public void setOrderSmallType(String orderSmallType) {
		this.orderSmallType = orderSmallType;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getIsPayerPayFee() {
		return isPayerPayFee;
	}

	public void setIsPayerPayFee(Integer isPayerPayFee) {
		this.isPayerPayFee = isPayerPayFee;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public Long getRealpayAmount() {
		return realpayAmount;
	}

	public void setRealpayAmount(Long realpayAmount) {
		this.realpayAmount = realpayAmount;
	}

	public Long getRealoutAmount() {
		return realoutAmount;
	}

	public void setRealoutAmount(Long realoutAmount) {
		this.realoutAmount = realoutAmount;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerLoginName() {
		return payerLoginName;
	}

	public void setPayerLoginName(String payerLoginName) {
		this.payerLoginName = payerLoginName;
	}

	public Long getPayerMemberCode() {
		return payerMemberCode;
	}

	public void setPayerMemberCode(Long payerMemberCode) {
		this.payerMemberCode = payerMemberCode;
	}

	public Integer getPayerMemberType() {
		return payerMemberType;
	}

	public void setPayerMemberType(Integer payerMemberType) {
		this.payerMemberType = payerMemberType;
	}

	public String getPayerAcctCode() {
		return payerAcctCode;
	}

	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}

	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeBankAcctCode() {
		return payeeBankAcctCode;
	}

	public void setPayeeBankAcctCode(String payeeBankAcctCode) {
		this.payeeBankAcctCode = payeeBankAcctCode;
	}

	public Integer getPayeeBankAcctType() {
		return payeeBankAcctType;
	}

	public void setPayeeBankAcctType(Integer payeeBankAcctType) {
		this.payeeBankAcctType = payeeBankAcctType;
	}

	public String getPayeeBankCode() {
		return payeeBankCode;
	}

	public void setPayeeBankCode(String payeeBankCode) {
		this.payeeBankCode = payeeBankCode;
	}

	public String getPayeeBankName() {
		return payeeBankName;
	}

	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	public String getPayeeOpeningBankName() {
		return payeeOpeningBankName;
	}

	public void setPayeeOpeningBankName(String payeeOpeningBankName) {
		this.payeeOpeningBankName = payeeOpeningBankName;
	}

	public String getPayeeBankProvince() {
		return payeeBankProvince;
	}

	public void setPayeeBankProvince(String payeeBankProvince) {
		this.payeeBankProvince = payeeBankProvince;
	}

	public String getPayeeBankProvinceName() {
		return payeeBankProvinceName;
	}

	public void setPayeeBankProvinceName(String payeeBankProvinceName) {
		this.payeeBankProvinceName = payeeBankProvinceName;
	}

	public String getPayeeBankCity() {
		return payeeBankCity;
	}

	public void setPayeeBankCity(String payeeBankCity) {
		this.payeeBankCity = payeeBankCity;
	}

	public String getPayeeBankCityName() {
		return payeeBankCityName;
	}

	public void setPayeeBankCityName(String payeeBankCityName) {
		this.payeeBankCityName = payeeBankCityName;
	}

	public String getPaymentReason() {
		return paymentReason;
	}

	public void setPaymentReason(String paymentReason) {
		this.paymentReason = paymentReason;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
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

	public String getTradeAlias() {
		return tradeAlias;
	}

	public void setTradeAlias(String tradeAlias) {
		this.tradeAlias = tradeAlias;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public String getPayeeMobile() {
		return payeeMobile;
	}

	public void setPayeeMobile(String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}

	public String getBankOrderId() {
		return bankOrderId;
	}

	public void setBankOrderId(String bankOrderId) {
		this.bankOrderId = bankOrderId;
	}

	public String getForeignOrderId() {
		return foreignOrderId;
	}

	public void setForeignOrderId(String foreignOrderId) {
		this.foreignOrderId = foreignOrderId;
	}

	public String getFundoutBankCode() {
		return fundoutBankCode;
	}

	public void setFundoutBankCode(String fundoutBankCode) {
		this.fundoutBankCode = fundoutBankCode;
	}

	public String getFundoutBankName() {
		return fundoutBankName;
	}

	public void setFundoutBankName(String fundoutBankName) {
		this.fundoutBankName = fundoutBankName;
	}

	public Integer getFundoutMode() {
		return fundoutMode;
	}

	public void setFundoutMode(Integer fundoutMode) {
		this.fundoutMode = fundoutMode;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Long getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(Long parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getRiskLevelCode() {
		return riskLevelCode;
	}

	public void setRiskLevelCode(String riskLevelCode) {
		this.riskLevelCode = riskLevelCode;
	}

	public String getAccountMode() {
		return accountMode;
	}

	public void setAccountMode(String accountMode) {
		this.accountMode = accountMode;
	}

	public int getIsLoaning() {
		return isLoaning;
	}

	public void setIsLoaning(int isLoaning) {
		this.isLoaning = isLoaning;
	}

	public String getBulkpayNo() {
		return bulkpayNo;
	}

	public void setBulkpayNo(String bulkpayNo) {
		this.bulkpayNo = bulkpayNo;
	}

	@Override
	public String toString() {
		return "BulkpayTemFundoutOrder [orderId=" + orderId + ", orderType="
				+ orderType + ", orderSmallType=" + orderSmallType
				+ ", orderStatus=" + orderStatus + ", orderAmount="
				+ orderAmount + ", isPayerPayFee=" + isPayerPayFee + ", fee="
				+ fee + ", realpayAmount=" + realpayAmount + ", realoutAmount="
				+ realoutAmount + ", payerName=" + payerName
				+ ", payerLoginName=" + payerLoginName + ", payerMemberCode="
				+ payerMemberCode + ", payerMemberType=" + payerMemberType
				+ ", payerAcctCode=" + payerAcctCode + ", payerAcctType="
				+ payerAcctType + ", payeeName=" + payeeName
				+ ", payeeBankAcctCode=" + payeeBankAcctCode
				+ ", payeeBankAcctType=" + payeeBankAcctType
				+ ", payeeBankCode=" + payeeBankCode + ", payeeBankName="
				+ payeeBankName + ", payeeOpeningBankName="
				+ payeeOpeningBankName + ", payeeBankProvince="
				+ payeeBankProvince + ", payeeBankProvinceName="
				+ payeeBankProvinceName + ", payeeBankCity=" + payeeBankCity
				+ ", payeeBankCityName=" + payeeBankCityName
				+ ", paymentReason=" + paymentReason + ", failedReason="
				+ failedReason + ", createDate=" + createDate + ", updateDate="
				+ updateDate + ", tradeAlias=" + tradeAlias + ", tradeType="
				+ tradeType + ", payeeMobile=" + payeeMobile + ", bankOrderId="
				+ bankOrderId + ", foreignOrderId=" + foreignOrderId
				+ ", fundoutBankCode=" + fundoutBankCode + ", fundoutBankName="
				+ fundoutBankName + ", fundoutMode=" + fundoutMode
				+ ", priority=" + priority + ", parentOrderId=" + parentOrderId
				+ ", bankNumber=" + bankNumber + ", riskLevelCode="
				+ riskLevelCode + ", accountMode=" + accountMode
				+ ", isLoaning=" + isLoaning + ", channelCode=" + channelCode
				+ ", bulkpayNo=" + bulkpayNo + "]";
	}

}
