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
public class PayToAcctOrderDTO extends Order {
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
	 * 外部订单号
	 */
	private String foreignOrderId;

	/**
	 * 总订单号
	 */
	private Long parentOrderId;

	/**
	 * 付款方手机号
	 */
	private Long payerMobile;

	/**
	 * 实际付款金额
	 */
	private Long realpayAmount;
	/**
	 * 实际出款金额
	 */
	private Long realoutAmount;

	/**
	 * 收款方手续费
	 */
	private Long payeeFee;

	/**
	 * 付款方手续费
	 */
	private Long payerFee;

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
	 * @return the orderType
	 */
	public Integer getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType
	 *            the orderType to set
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
	 * @param orderSmallType
	 *            the orderSmallType to set
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
	 * @return the payerAcctType
	 */
	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	/**
	 * @param payerAcctType
	 *            the payerAcctType to set
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
	 * @return the payeeAcctType
	 */
	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	/**
	 * @param payeeAcctType
	 *            the payeeAcctType to set
	 */
	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
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
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
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
	 * @param tradeAlias
	 *            the tradeAlias to set
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
	 * @param tradeType
	 *            the tradeType to set
	 */
	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	/**
	 * @return the foreignOrderId
	 */
	public String getForeignOrderId() {
		return foreignOrderId;
	}

	/**
	 * @param foreignOrderId
	 *            the foreignOrderId to set
	 */
	public void setForeignOrderId(String foreignOrderId) {
		this.foreignOrderId = foreignOrderId;
	}

	/**
	 * @return the parentOrderId
	 */
	public Long getParentOrderId() {
		return parentOrderId;
	}

	/**
	 * @param parentOrderId
	 *            the parentOrderId to set
	 */
	public void setParentOrderId(Long parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

	public Long getPayerMobile() {
		return payerMobile;
	}

	public void setPayerMobile(Long payerMobile) {
		this.payerMobile = payerMobile;
	}

}
