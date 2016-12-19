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
public class OrderInfoDTO extends Order {
	
	 /**
     * 订单号
     */
    private Long orderId;

    /**
     * 订单类型
     */
    private Integer orderType;

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
     * 	付款方登录名称
     */
    private String payerLoginName;

    /**
     * 付款方会员号
     */
    private Long payerMemberCode;

    /**
     * 收款方名称
     */
    private String payeeName;
    
    /**
     * 	收款方登录名称
     */
    private String payeeLoginName;

    /**
     * 收款方会员号
     */
    private Long payeeMemberCode;

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
     * 优先级权值
     */
    private Integer priority;

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
	 * @return the payeeLoginName
	 */
	public String getPayeeLoginName() {
		return payeeLoginName;
	}

	/**
	 * @param payeeLoginName the payeeLoginName to set
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
	 * @param payeeMemberCode the payeeMemberCode to set
	 */
	public void setPayeeMemberCode(Long payeeMemberCode) {
		this.payeeMemberCode = payeeMemberCode;
	}
    

}
