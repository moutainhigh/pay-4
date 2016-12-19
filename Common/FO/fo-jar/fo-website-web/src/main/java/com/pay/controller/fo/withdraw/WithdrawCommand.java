/**
 * 
 */
package com.pay.controller.fo.withdraw;

import java.io.Serializable;
import java.util.Date;

import com.pay.fo.order.common.BankAcctType;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderType;

/**
 * @author NEW
 *
 */
public class WithdrawCommand implements Serializable {

	private static final long serialVersionUID = -6311070349902993498L;

	/**
	 * 订单号
	 */
	private Long orderId;
	/**
	 * 订单类型
	 */
	private final Integer orderType = OrderType.WITHDRAW.getValue();
	/**
	 * 订单子类型
	 */
	private final String orderSmallType = OrderSmallType.COMMON_WITHDRAW
			.getValue();
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
	private final Integer isPayerPayFee = 1;
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
	
	private String currencyCode;
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
	private final Integer payeeBankAcctType = BankAcctType.DebitCard.getValue();
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
	 * 交易别名
	 */
	private final String tradeAlias = OrderType.WITHDRAW.getDesc();
	/**
	 * 交易类型
	 */
	private Integer tradeType;
	/**
	 * 收款方手机号
	 */
	private String payeeMobile;

	/**
	 * 出款方式 1:手工
	 */
	private final Integer fundoutMode = 1;

	/**
	 * 实付金额(格式化)
	 */
	private String realpayAmountStr;

	/**
	 * 实际出款金额(格式化)
	 */
	private String realoutAmountStr;

	/**
	 * 申请金额
	 */
	private String requestAmount;

	/**
	 * 申请手续费
	 */
	private String requestFee;

	/**
	 * 重复录入的收款方银行账号
	 */
	private String payeeRepeatBankAcctCode;
	/**
	 * 单笔限额
	 */
	private Long singleLimitAmount;
	/**
	 * 格式化单笔限额字符串
	 */
	private String singleLimitAmountStr;
	/**
	 * 当前余额
	 */
	private Long currentBanlance;
	/**
	 * 格式化余额字符串
	 */
	private String currentBanlanceStr;
	/**
	 * 每天限额
	 */
	private Long dayLimitAmount;

	/**
	 * 每天限额(格式化字符串)
	 */
	private String dayLimitAmountStr;

	/**
	 * 每月限额
	 */
	private Long monthLimitAmount;
	/**
	 * 每月限额(格式化字符串)
	 */
	private String monthLimitAmountStr;
	/**
	 * 今天已付款总额
	 */
	private Long todayPaymentAmount;
	/**
	 * 当月已付款总额
	 */
	private Long currentMonthPaymentAmount;

	/**
	 * 当前允许付款金额
	 */
	private Long allowPaymentAmount;
	/**
	 * 格式化允许付款金额字符串
	 */
	private String allowPaymentAmountStr;
	/**
	 * 当前允许付款次数
	 */
	private Integer allowPaymentTimes;
	/**
	 * 当月允许交易的次数
	 */
	private Integer monthLimitTimes;
	/**
	 * 令牌标识
	 */
	private String token;

	/**
	 * 验证码
	 */
	private String validateCode;

	/**
	 * 优先级权值
	 */
	private final Integer priority = 5;

	/***
	 * 可提现余额
	 */
	private Long withdrawBanlance;
	
	/**
	 * 最大可提现余额
	 */
	private Long maximumCashBalance;
	
	/***
	 * 提现的账户名
	 */
	private String acctName;
	
	public Long getMaximumCashBalance() {
		return maximumCashBalance;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public void setMaximumCashBalance(Long maximumCashBalance) {
		this.maximumCashBalance = maximumCashBalance;
	}

	public Long getWithdrawBanlance() {
		return withdrawBanlance;
	}

	public void setWithdrawBanlance(Long withdrawBanlance) {
		this.withdrawBanlance = withdrawBanlance;
	}

	/**
	 * @return the orderId
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the orderStatus
	 */
	public Integer getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus
	 *            the orderStatus to set
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
	 * @param orderAmount
	 *            the orderAmount to set
	 */
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	/**
	 * @return the fee
	 */
	public Long getFee() {
		return fee;
	}

	/**
	 * @param fee
	 *            the fee to set
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
	 * @param realpayAmount
	 *            the realpayAmount to set
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
	 * @param realoutAmount
	 *            the realoutAmount to set
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
	 * @param payerName
	 *            the payerName to set
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
	 * @param payerLoginName
	 *            the payerLoginName to set
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
	 * @param payerMemberCode
	 *            the payerMemberCode to set
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
	 * @param payerMemberType
	 *            the payerMemberType to set
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
	 * @param payerAcctCode
	 *            the payerAcctCode to set
	 */
	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}

	/**
	 * @return the payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}

	/**
	 * @param payeeName
	 *            the payeeName to set
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
	 * @param payeeBankAcctCode
	 *            the payeeBankAcctCode to set
	 */
	public void setPayeeBankAcctCode(String payeeBankAcctCode) {
		this.payeeBankAcctCode = payeeBankAcctCode;
	}

	/**
	 * @return the payeeBankCode
	 */
	public String getPayeeBankCode() {
		return payeeBankCode;
	}

	/**
	 * @param payeeBankCode
	 *            the payeeBankCode to set
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
	 * @param payeeBankName
	 *            the payeeBankName to set
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
	 * @param payeeOpeningBankName
	 *            the payeeOpeningBankName to set
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
	 * @param payeeBankProvince
	 *            the payeeBankProvince to set
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
	 * @param payeeBankProvinceName
	 *            the payeeBankProvinceName to set
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
	 * @param payeeBankCity
	 *            the payeeBankCity to set
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
	 * @param payeeBankCityName
	 *            the payeeBankCityName to set
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
	 * @param paymentReason
	 *            the paymentReason to set
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
	 * @param failedReason
	 *            the failedReason to set
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
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the tradeType
	 */
	public Integer getTradeType() {
		return tradeType;
	}

	/**
	 * @param tradeType
	 *            the tradeType to set
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
	 * @param payeeMobile
	 *            the payeeMobile to set
	 */
	public void setPayeeMobile(String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}

	/**
	 * @return the realpayAmountStr
	 */
	public String getRealpayAmountStr() {
		return realpayAmountStr;
	}

	/**
	 * @param realpayAmountStr
	 *            the realpayAmountStr to set
	 */
	public void setRealpayAmountStr(String realpayAmountStr) {
		this.realpayAmountStr = realpayAmountStr;
	}

	/**
	 * @return the realoutAmountStr
	 */
	public String getRealoutAmountStr() {
		return realoutAmountStr;
	}

	/**
	 * @param realoutAmountStr
	 *            the realoutAmountStr to set
	 */
	public void setRealoutAmountStr(String realoutAmountStr) {
		this.realoutAmountStr = realoutAmountStr;
	}

	/**
	 * @return the requestAmount
	 */
	public String getRequestAmount() {
		return requestAmount;
	}

	/**
	 * @param requestAmount
	 *            the requestAmount to set
	 */
	public void setRequestAmount(String requestAmount) {
		this.requestAmount = requestAmount;
	}

	/**
	 * @return the requestFee
	 */
	public String getRequestFee() {
		return requestFee;
	}

	/**
	 * @param requestFee
	 *            the requestFee to set
	 */
	public void setRequestFee(String requestFee) {
		this.requestFee = requestFee;
	}

	/**
	 * @return the payeeRepeatBankAcctCode
	 */
	public String getPayeeRepeatBankAcctCode() {
		return payeeRepeatBankAcctCode;
	}

	/**
	 * @param payeeRepeatBankAcctCode
	 *            the payeeRepeatBankAcctCode to set
	 */
	public void setPayeeRepeatBankAcctCode(String payeeRepeatBankAcctCode) {
		this.payeeRepeatBankAcctCode = payeeRepeatBankAcctCode;
	}

	/**
	 * @return the singleLimitAmount
	 */
	public Long getSingleLimitAmount() {
		return singleLimitAmount;
	}

	/**
	 * @param singleLimitAmount
	 *            the singleLimitAmount to set
	 */
	public void setSingleLimitAmount(Long singleLimitAmount) {
		this.singleLimitAmount = singleLimitAmount;
	}

	/**
	 * @return the singleLimitAmountStr
	 */
	public String getSingleLimitAmountStr() {
		return singleLimitAmountStr;
	}

	/**
	 * @param singleLimitAmountStr
	 *            the singleLimitAmountStr to set
	 */
	public void setSingleLimitAmountStr(String singleLimitAmountStr) {
		this.singleLimitAmountStr = singleLimitAmountStr;
	}

	/**
	 * @return the currentBanlance
	 */
	public Long getCurrentBanlance() {
		return currentBanlance;
	}

	/**
	 * @param currentBanlance
	 *            the currentBanlance to set
	 */
	public void setCurrentBanlance(Long currentBanlance) {
		this.currentBanlance = currentBanlance;
	}

	/**
	 * @return the currentBanlanceStr
	 */
	public String getCurrentBanlanceStr() {
		return currentBanlanceStr;
	}

	/**
	 * @param currentBanlanceStr
	 *            the currentBanlanceStr to set
	 */
	public void setCurrentBanlanceStr(String currentBanlanceStr) {
		this.currentBanlanceStr = currentBanlanceStr;
	}

	/**
	 * @return the dayLimitAmount
	 */
	public Long getDayLimitAmount() {
		return dayLimitAmount;
	}

	/**
	 * @param dayLimitAmount
	 *            the dayLimitAmount to set
	 */
	public void setDayLimitAmount(Long dayLimitAmount) {
		this.dayLimitAmount = dayLimitAmount;
	}

	/**
	 * @return the dayLimitAmountStr
	 */
	public String getDayLimitAmountStr() {
		return dayLimitAmountStr;
	}

	/**
	 * @param dayLimitAmountStr
	 *            the dayLimitAmountStr to set
	 */
	public void setDayLimitAmountStr(String dayLimitAmountStr) {
		this.dayLimitAmountStr = dayLimitAmountStr;
	}

	/**
	 * @return the monthLimitAmount
	 */
	public Long getMonthLimitAmount() {
		return monthLimitAmount;
	}

	/**
	 * @param monthLimitAmount
	 *            the monthLimitAmount to set
	 */
	public void setMonthLimitAmount(Long monthLimitAmount) {
		this.monthLimitAmount = monthLimitAmount;
	}

	/**
	 * @return the monthLimitAmountStr
	 */
	public String getMonthLimitAmountStr() {
		return monthLimitAmountStr;
	}

	/**
	 * @param monthLimitAmountStr
	 *            the monthLimitAmountStr to set
	 */
	public void setMonthLimitAmountStr(String monthLimitAmountStr) {
		this.monthLimitAmountStr = monthLimitAmountStr;
	}

	/**
	 * @return the todayPaymentAmount
	 */
	public Long getTodayPaymentAmount() {
		return todayPaymentAmount;
	}

	/**
	 * @param todayPaymentAmount
	 *            the todayPaymentAmount to set
	 */
	public void setTodayPaymentAmount(Long todayPaymentAmount) {
		this.todayPaymentAmount = todayPaymentAmount;
	}

	public Integer getMonthLimitTimes() {
		return monthLimitTimes;
	}

	public void setMonthLimitTimes(Integer monthLimitTimes) {
		this.monthLimitTimes = monthLimitTimes;
	}

	/**
	 * @return the currentMonthPaymentAmount
	 */
	public Long getCurrentMonthPaymentAmount() {
		return currentMonthPaymentAmount;
	}

	/**
	 * @param currentMonthPaymentAmount
	 *            the currentMonthPaymentAmount to set
	 */
	public void setCurrentMonthPaymentAmount(Long currentMonthPaymentAmount) {
		this.currentMonthPaymentAmount = currentMonthPaymentAmount;
	}

	/**
	 * @return the allowPaymentAmount
	 */
	public Long getAllowPaymentAmount() {
		return allowPaymentAmount;
	}

	/**
	 * @param allowPaymentAmount
	 *            the allowPaymentAmount to set
	 */
	public void setAllowPaymentAmount(Long allowPaymentAmount) {
		this.allowPaymentAmount = allowPaymentAmount;
	}

	/**
	 * @return the allowPaymentAmountStr
	 */
	public String getAllowPaymentAmountStr() {
		return allowPaymentAmountStr;
	}

	/**
	 * @param allowPaymentAmountStr
	 *            the allowPaymentAmountStr to set
	 */
	public void setAllowPaymentAmountStr(String allowPaymentAmountStr) {
		this.allowPaymentAmountStr = allowPaymentAmountStr;
	}

	/**
	 * @return the allowPaymentTimes
	 */
	public Integer getAllowPaymentTimes() {
		return allowPaymentTimes;
	}

	/**
	 * @param allowPaymentTimes
	 *            the allowPaymentTimes to set
	 */
	public void setAllowPaymentTimes(Integer allowPaymentTimes) {
		this.allowPaymentTimes = allowPaymentTimes;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the orderType
	 */
	public Integer getOrderType() {
		return orderType;
	}

	/**
	 * @return the orderSmallType
	 */
	public String getOrderSmallType() {
		return orderSmallType;
	}

	/**
	 * @return the isPayerPayFee
	 */
	public Integer getIsPayerPayFee() {
		return isPayerPayFee;
	}

	/**
	 * @return the payerAcctType
	 */
	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	/**
	 * @return the payeeBankAcctType
	 */
	public Integer getPayeeBankAcctType() {
		return payeeBankAcctType;
	}

	/**
	 * @return the tradeAlias
	 */
	public String getTradeAlias() {
		return tradeAlias;
	}

	/**
	 * @return the fundoutMode
	 */
	public Integer getFundoutMode() {
		return fundoutMode;
	}

	/**
	 * @return the validateCode
	 */
	public String getValidateCode() {
		return validateCode;
	}

	/**
	 * @param validateCode
	 *            the validateCode to set
	 */
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

}
