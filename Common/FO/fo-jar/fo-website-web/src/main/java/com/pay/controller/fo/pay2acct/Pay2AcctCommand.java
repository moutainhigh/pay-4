/**
 * 
 */
package com.pay.controller.fo.pay2acct;

import java.io.Serializable;
import java.util.Date;

import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderType;

/**
 * @author NEW
 * 
 */
public class Pay2AcctCommand implements Serializable {

	private static final long serialVersionUID = 6268744894211938486L;

	/**
	 * 订单号
	 */
	private Long orderId;

	/**
	 * 订单类型
	 */
	private final Integer orderType = OrderType.PAY2ACCT.getValue();

	/**
	 * 订单子类型
	 */
	private final String orderSmallType = OrderSmallType.COMMON_PAY2ACCT
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
	 * 收款方登录标识
	 */
	private String payeeLoginName;

	/**
	 * 收款方会员号
	 */
	private Long payeeMemberCode;

	/**
	 * 收款方会员类型
	 * 
	 */
	private Integer payeeMemberType;

	/**
	 * 收款方账号
	 * 
	 */
	private String payeeAcctCode;

	/**
	 * 收款方账号类型
	 */
	private Integer payeeAcctType;

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
	private final String tradeAlias = OrderType.PAY2ACCT.getDesc();

	/**
	 * 交易类型
	 */
	private Integer tradeType;

	/**
	 * 申请金额
	 */
	private String requestAmount;
	/**
	 * 订单金额(格式化)
	 */
	private String orderAmountStr;

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
	 * 令牌标识
	 */
	private String token;

	/**
	 * 查询起始时间（格式化）
	 */
	private String beginDate;
	/**
	 * 查询截止时间（格式化）
	 */
	private String endDate;
	/**
	 * 工单操作状态
	 */
	private int workOrderStatus;
	/**
	 * 审核备注
	 */
	private String auditRemark;
	/**
	 * 是否开通了复核产品
	 */
	private int isHaveProduct;

	/**
	 * 收款方手续费
	 */
	private Long payeeFee;

	/**
	 * 付款方手续费
	 */
	private Long payerFee;

	/**
	 * 申请手续费
	 */
	private String requestFee;

	/**
	 * 是否是付款方付手续费
	 */
	private Integer isPayerPayFee;

	/**
	 * 实际付款金额
	 */
	private Long realpayAmount;
	/**
	 * 实际出款金额
	 */
	private Long realoutAmount;

	/**
	 * 实付金额(格式化)
	 */
	private String realpayAmountStr;

	/**
	 * 实际出款金额(格式化)
	 */
	private String realoutAmountStr;

	public String getRealpayAmountStr() {
		return realpayAmountStr;
	}

	public void setRealpayAmountStr(String realpayAmountStr) {
		this.realpayAmountStr = realpayAmountStr;
	}

	public String getRealoutAmountStr() {
		return realoutAmountStr;
	}

	public void setRealoutAmountStr(String realoutAmountStr) {
		this.realoutAmountStr = realoutAmountStr;
	}

	public String getRequestFee() {
		return requestFee;
	}

	public void setRequestFee(String requestFee) {
		this.requestFee = requestFee;
	}

	public Integer getIsPayerPayFee() {
		return isPayerPayFee;
	}

	public void setIsPayerPayFee(Integer isPayerPayFee) {
		this.isPayerPayFee = isPayerPayFee;
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

	public Long getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(Long payeeFee) {
		this.payeeFee = payeeFee;
	}

	public Long getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Long payerFee) {
		this.payerFee = payerFee;
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
	 * @return the payeeLoginName
	 */
	public String getPayeeLoginName() {
		return payeeLoginName;
	}

	/**
	 * @param payeeLoginName
	 *            the payeeLoginName to set
	 */
	public void setPayeeLoginName(String payeeLoginName) {
		this.payeeLoginName = payeeLoginName;
	}

	/**
	 * @return the payeeMemberCode
	 */
	public Long getPayeeMemberCode() {
		return payeeMemberCode;
	}

	/**
	 * @param payeeMemberCode
	 *            the payeeMemberCode to set
	 */
	public void setPayeeMemberCode(Long payeeMemberCode) {
		this.payeeMemberCode = payeeMemberCode;
	}

	/**
	 * @return the payeeMemberType
	 */
	public Integer getPayeeMemberType() {
		return payeeMemberType;
	}

	/**
	 * @param payeeMemberType
	 *            the payeeMemberType to set
	 */
	public void setPayeeMemberType(Integer payeeMemberType) {
		this.payeeMemberType = payeeMemberType;
	}

	/**
	 * @return the payeeAcctCode
	 */
	public String getPayeeAcctCode() {
		return payeeAcctCode;
	}

	/**
	 * @param payeeAcctCode
	 *            the payeeAcctCode to set
	 */
	public void setPayeeAcctCode(String payeeAcctCode) {
		this.payeeAcctCode = payeeAcctCode;
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
	 * @return the orderAmountStr
	 */
	public String getOrderAmountStr() {
		return orderAmountStr;
	}

	/**
	 * @param orderAmountStr
	 *            the orderAmountStr to set
	 */
	public void setOrderAmountStr(String orderAmountStr) {
		this.orderAmountStr = orderAmountStr;
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
	 * @return the payerAcctType
	 */
	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	/**
	 * @return the payeeAcctType
	 */
	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	/**
	 * @return the tradeAlias
	 */
	public String getTradeAlias() {
		return tradeAlias;
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

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(int workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public int getIsHaveProduct() {
		return isHaveProduct;
	}

	public void setIsHaveProduct(int isHaveProduct) {
		this.isHaveProduct = isHaveProduct;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

}
