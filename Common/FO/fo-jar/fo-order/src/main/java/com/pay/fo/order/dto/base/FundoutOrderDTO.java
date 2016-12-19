/**
 * 
 */
package com.pay.fo.order.dto.base;

import java.util.Date;

import com.pay.fo.order.dto.Order;

/**
 * @author NEW
 *
 */
public class FundoutOrderDTO extends Order {
	
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
	
	/**
	 * 退票订单号
	 */
	private Long refundOrderId;
	
	/**
	 * 出款渠道编号
	 */
	private String channelCode;
	
	private String bankNumber;
	
	private Integer workOrderStatus;//工单状态
	//余额
	private Long balance ;
	
	//-------------- PengJiangbo added
	//private Integer payerAcctType;
	private Integer payeeAcctType;
	
	private String payeeCurrencyCode;
	
	private String payerCurrencyCode;
	
	private String fundoutRate; //提现清算汇率
	
	public String getFundoutRate() {
		return fundoutRate;
	}
	public void setFundoutRate(String fundoutRate) {
		this.fundoutRate = fundoutRate;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public Integer getWorkOrderStatus() {
		return workOrderStatus;
	}
	public void setWorkOrderStatus(Integer workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	/**
	 * @return the orderId
	 */
	public Long getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the orderType
	 */
	public Integer getOrderType() {
		return orderType;
	}
	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	/**
	 * @return the orderSmallType
	 */
	public String getOrderSmallType() {
		return orderSmallType;
	}
	/**
	 * @param orderSmallType the orderSmallType to set
	 */
	public void setOrderSmallType(String orderSmallType) {
		this.orderSmallType = orderSmallType;
	}
	/**
	 * @return the orderStatus
	 */
	public Integer getOrderStatus() {
		return orderStatus;
	}
	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * @return the orderAmount
	 */
	public Long getOrderAmount() {
		return orderAmount;
	}
	/**
	 * @param orderAmount the orderAmount to set
	 */
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	/**
	 * @return the isPayerPayFee
	 */
	public Integer getIsPayerPayFee() {
		return isPayerPayFee;
	}
	/**
	 * @param isPayerPayFee the isPayerPayFee to set
	 */
	public void setIsPayerPayFee(Integer isPayerPayFee) {
		this.isPayerPayFee = isPayerPayFee;
	}
	/**
	 * @return the fee
	 */
	public Long getFee() {
		return fee;
	}
	/**
	 * @param fee the fee to set
	 */
	public void setFee(Long fee) {
		this.fee = fee;
	}
	/**
	 * @return the realpayAmount
	 */
	public Long getRealpayAmount() {
		return realpayAmount;
	}
	/**
	 * @param realpayAmount the realpayAmount to set
	 */
	public void setRealpayAmount(Long realpayAmount) {
		this.realpayAmount = realpayAmount;
	}
	/**
	 * @return the realoutAmount
	 */
	public Long getRealoutAmount() {
		return realoutAmount;
	}
	/**
	 * @param realoutAmount the realoutAmount to set
	 */
	public void setRealoutAmount(Long realoutAmount) {
		this.realoutAmount = realoutAmount;
	}
	/**
	 * @return the payerName
	 */
	public String getPayerName() {
		return payerName;
	}
	/**
	 * @param payerName the payerName to set
	 */
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	/**
	 * @return the payerLoginName
	 */
	public String getPayerLoginName() {
		return payerLoginName;
	}
	/**
	 * @param payerLoginName the payerLoginName to set
	 */
	public void setPayerLoginName(String payerLoginName) {
		this.payerLoginName = payerLoginName;
	}
	/**
	 * @return the payerMemberCode
	 */
	public Long getPayerMemberCode() {
		return payerMemberCode;
	}
	/**
	 * @param payerMemberCode the payerMemberCode to set
	 */
	public void setPayerMemberCode(Long payerMemberCode) {
		this.payerMemberCode = payerMemberCode;
	}
	/**
	 * @return the payerMemberType
	 */
	public Integer getPayerMemberType() {
		return payerMemberType;
	}
	/**
	 * @param payerMemberType the payerMemberType to set
	 */
	public void setPayerMemberType(Integer payerMemberType) {
		this.payerMemberType = payerMemberType;
	}
	/**
	 * @return the payerAcctCode
	 */
	public String getPayerAcctCode() {
		return payerAcctCode;
	}
	/**
	 * @param payerAcctCode the payerAcctCode to set
	 */
	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}
	/**
	 * @return the payerAcctType
	 */
	public Integer getPayerAcctType() {
		return payerAcctType;
	}
	/**
	 * @param payerAcctType the payerAcctType to set
	 */
	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}
	/**
	 * @return the payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}
	/**
	 * @param payeeName the payeeName to set
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	/**
	 * @return the payeeBankAcctCode
	 */
	public String getPayeeBankAcctCode() {
		return payeeBankAcctCode;
	}
	/**
	 * @param payeeBankAcctCode the payeeBankAcctCode to set
	 */
	public void setPayeeBankAcctCode(String payeeBankAcctCode) {
		this.payeeBankAcctCode = payeeBankAcctCode;
	}
	/**
	 * @return the payeeBankAcctType
	 */
	public Integer getPayeeBankAcctType() {
		return payeeBankAcctType;
	}
	/**
	 * @param payeeBankAcctType the payeeBankAcctType to set
	 */
	public void setPayeeBankAcctType(Integer payeeBankAcctType) {
		this.payeeBankAcctType = payeeBankAcctType;
	}
	/**
	 * @return the payeeBankCode
	 */
	public String getPayeeBankCode() {
		return payeeBankCode;
	}
	/**
	 * @param payeeBankCode the payeeBankCode to set
	 */
	public void setPayeeBankCode(String payeeBankCode) {
		this.payeeBankCode = payeeBankCode;
	}
	/**
	 * @return the payeeBankName
	 */
	public String getPayeeBankName() {
		return payeeBankName;
	}
	/**
	 * @param payeeBankName the payeeBankName to set
	 */
	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}
	/**
	 * @return the payeeOpeningBankName
	 */
	public String getPayeeOpeningBankName() {
		return payeeOpeningBankName;
	}
	/**
	 * @param payeeOpeningBankName the payeeOpeningBankName to set
	 */
	public void setPayeeOpeningBankName(String payeeOpeningBankName) {
		this.payeeOpeningBankName = payeeOpeningBankName;
	}
	/**
	 * @return the payeeBankProvince
	 */
	public String getPayeeBankProvince() {
		return payeeBankProvince;
	}
	/**
	 * @param payeeBankProvince the payeeBankProvince to set
	 */
	public void setPayeeBankProvince(String payeeBankProvince) {
		this.payeeBankProvince = payeeBankProvince;
	}
	/**
	 * @return the payeeBankProvinceName
	 */
	public String getPayeeBankProvinceName() {
		return payeeBankProvinceName;
	}
	/**
	 * @param payeeBankProvinceName the payeeBankProvinceName to set
	 */
	public void setPayeeBankProvinceName(String payeeBankProvinceName) {
		this.payeeBankProvinceName = payeeBankProvinceName;
	}
	/**
	 * @return the payeeBankCity
	 */
	public String getPayeeBankCity() {
		return payeeBankCity;
	}
	/**
	 * @param payeeBankCity the payeeBankCity to set
	 */
	public void setPayeeBankCity(String payeeBankCity) {
		this.payeeBankCity = payeeBankCity;
	}
	/**
	 * @return the payeeBankCityName
	 */
	public String getPayeeBankCityName() {
		return payeeBankCityName;
	}
	/**
	 * @param payeeBankCityName the payeeBankCityName to set
	 */
	public void setPayeeBankCityName(String payeeBankCityName) {
		this.payeeBankCityName = payeeBankCityName;
	}
	/**
	 * @return the paymentReason
	 */
	public String getPaymentReason() {
		return paymentReason;
	}
	/**
	 * @param paymentReason the paymentReason to set
	 */
	public void setPaymentReason(String paymentReason) {
		this.paymentReason = paymentReason;
	}
	/**
	 * @return the failedReason
	 */
	public String getFailedReason() {
		return failedReason;
	}
	/**
	 * @param failedReason the failedReason to set
	 */
	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the tradeAlias
	 */
	public String getTradeAlias() {
		return tradeAlias;
	}
	/**
	 * @param tradeAlias the tradeAlias to set
	 */
	public void setTradeAlias(String tradeAlias) {
		this.tradeAlias = tradeAlias;
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
	 * @return the payeeMobile
	 */
	public String getPayeeMobile() {
		return payeeMobile;
	}
	/**
	 * @param payeeMobile the payeeMobile to set
	 */
	public void setPayeeMobile(String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}
	/**
	 * @return the bankOrderId
	 */
	public String getBankOrderId() {
		return bankOrderId;
	}
	/**
	 * @param bankOrderId the bankOrderId to set
	 */
	public void setBankOrderId(String bankOrderId) {
		this.bankOrderId = bankOrderId;
	}
	/**
	 * @return the foreignOrderId
	 */
	public String getForeignOrderId() {
		return foreignOrderId;
	}
	/**
	 * @param foreignOrderId the foreignOrderId to set
	 */
	public void setForeignOrderId(String foreignOrderId) {
		this.foreignOrderId = foreignOrderId;
	}
	/**
	 * @return the fundoutBankCode
	 */
	public String getFundoutBankCode() {
		return fundoutBankCode;
	}
	/**
	 * @param fundoutBankCode the fundoutBankCode to set
	 */
	public void setFundoutBankCode(String fundoutBankCode) {
		this.fundoutBankCode = fundoutBankCode;
	}
	/**
	 * @return the fundoutBankName
	 */
	public String getFundoutBankName() {
		return fundoutBankName;
	}
	/**
	 * @param fundoutBankName the fundoutBankName to set
	 */
	public void setFundoutBankName(String fundoutBankName) {
		this.fundoutBankName = fundoutBankName;
	}
	/**
	 * @return the fundoutMode
	 */
	public Integer getFundoutMode() {
		return fundoutMode;
	}
	/**
	 * @param fundoutMode the fundoutMode to set
	 */
	public void setFundoutMode(Integer fundoutMode) {
		this.fundoutMode = fundoutMode;
	}
	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	/**
	 * @return the parentOrderId
	 */
	public Long getParentOrderId() {
		return parentOrderId;
	}
	/**
	 * @param parentOrderId the parentOrderId to set
	 */
	public void setParentOrderId(Long parentOrderId) {
		this.parentOrderId = parentOrderId;
	}
	/**
	 * @return the refundOrderId
	 */
	public Long getRefundOrderId() {
		return refundOrderId;
	}
	/**
	 * @param refundOrderId the refundOrderId to set
	 */
	public void setRefundOrderId(Long refundOrderId) {
		this.refundOrderId = refundOrderId;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	
	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}
	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}
	public String getPayeeCurrencyCode() {
		return payeeCurrencyCode;
	}
	public void setPayeeCurrencyCode(String payeeCurrencyCode) {
		this.payeeCurrencyCode = payeeCurrencyCode;
	}
	public String getPayerCurrencyCode() {
		return payerCurrencyCode;
	}
	public void setPayerCurrencyCode(String payerCurrencyCode) {
		this.payerCurrencyCode = payerCurrencyCode;
	}
	@Override
	public String toString() {
		return "FundoutOrderDTO [orderId=" + orderId + ", orderType="
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
				+ ", refundOrderId=" + refundOrderId + ", channelCode="
				+ channelCode + ", bankNumber=" + bankNumber
				+ ", workOrderStatus=" + workOrderStatus + ", payeeAcctType="
				+ payeeAcctType + ", payeeCurrencyCode=" + payeeCurrencyCode
				+ ", payerCurrencyCode=" + payerCurrencyCode + "]";
	}
	
}
