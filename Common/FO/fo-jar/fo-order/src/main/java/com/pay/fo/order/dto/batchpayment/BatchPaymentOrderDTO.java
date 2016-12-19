package com.pay.fo.order.dto.batchpayment;

import java.util.Date;

import com.pay.fo.order.dto.Order;

public class BatchPaymentOrderDTO extends Order{
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
     * 创建者
     */
    private String creator;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 更新日期
     */
    private Date updateDate;

    /**
     * 业务批次号
     */
    private String businessBatchNo;

    /**
     * 成功出款金额
     */
    private Long successAmount;

    /**
     * 实际支付手续费
     */
    private Long successFee;
    
    
    /**
     * 付款笔数
     */
    private Integer paymentCount;

    /**
     * 成功付款笔数
     */
    private Integer successCount;
    
    
    /**
	 * 收款方手续费
	 */
	private Long payeeFee;

	/**
	 * 付款方手续费
	 */
	private Long payerFee;
	
	/**
	 * 收款方账号类型
	 * @return
	 */
	private Integer payeeAcctType;
	
	/**
	 * 收款方币种代码
	 * @return
	 */
	private String payeeCurrencyCode;
	
	/**
	 * 付款方币种代码
	 */
	private String payerCurrencyCode;
    
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
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
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
	 * @return the businessBatchNo
	 */
	public String getBusinessBatchNo() {
		return businessBatchNo;
	}

	/**
	 * @param businessBatchNo the businessBatchNo to set
	 */
	public void setBusinessBatchNo(String businessBatchNo) {
		this.businessBatchNo = businessBatchNo;
	}

	/**
	 * @return the successAmount
	 */
	public Long getSuccessAmount() {
		return successAmount;
	}

	/**
	 * @param successAmount the successAmount to set
	 */
	public void setSuccessAmount(Long successAmount) {
		this.successAmount = successAmount;
	}

	/**
	 * @return the successFee
	 */
	public Long getSuccessFee() {
		return successFee;
	}

	/**
	 * @param successFee the successFee to set
	 */
	public void setSuccessFee(Long successFee) {
		this.successFee = successFee;
	}

	/**
	 * @return the paymentCount
	 */
	public Integer getPaymentCount() {
		return paymentCount;
	}

	/**
	 * @param paymentCount the paymentCount to set
	 */
	public void setPaymentCount(Integer paymentCount) {
		this.paymentCount = paymentCount;
	}

	/**
	 * @return the successCount
	 */
	public Integer getSuccessCount() {
		return successCount;
	}

	/**
	 * @param successCount the successCount to set
	 */
	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
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
		return "BatchPaymentOrderDTO [orderId=" + orderId + ", orderType="
				+ orderType + ", orderSmallType=" + orderSmallType
				+ ", orderStatus=" + orderStatus + ", orderAmount="
				+ orderAmount + ", isPayerPayFee=" + isPayerPayFee + ", fee="
				+ fee + ", realpayAmount=" + realpayAmount + ", realoutAmount="
				+ realoutAmount + ", payerName=" + payerName
				+ ", payerLoginName=" + payerLoginName + ", payerMemberCode="
				+ payerMemberCode + ", payerMemberType=" + payerMemberType
				+ ", payerAcctCode=" + payerAcctCode + ", payerAcctType="
				+ payerAcctType + ", creator=" + creator + ", createDate="
				+ createDate + ", updateDate=" + updateDate
				+ ", businessBatchNo=" + businessBatchNo + ", successAmount="
				+ successAmount + ", successFee=" + successFee
				+ ", paymentCount=" + paymentCount + ", successCount="
				+ successCount + ", payeeFee=" + payeeFee + ", payerFee="
				+ payerFee + ", payeeAcctType=" + payeeAcctType
				+ ", payeeCurrencyCode=" + payeeCurrencyCode
				+ ", payerCurrencyCode=" + payerCurrencyCode + "]";
	}

}