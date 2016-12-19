/**
 * 
 */
package com.pay.fo.order.model.audit;

import java.util.Date;


/**
 * @author NEW
 *
 */
public class SingleAuditQueryInfo {
	
	/**
	 * 工单号
	 */
	private Long workOrderId;
	
	/**
	 * 工单状态
	 */
	private Integer workOrderStatus;
	/**
	 * 创建会员号
	 */
	private Long createMemberCode;
	/**
	 * 创建操作员
	 */
	private String createOperator;
	/**
	 * 审核会员号
	 */
	private Long auditMemberCode;
	/**
	 * 审核操作员
	 */
	private String auditOperator;
	/**
	 * 审核备注
	 */
	private String auditRemark;
	 /**
     * 订单号
     */
    private Long orderId;

    /**
     * 订单类型
     */
    private Integer orderType;
    
    /**
     * 订单类型
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
	 * 令牌标识
	 */
	private String token;
	
	
	/**
	 * 起始日期（供查询用）
	 */
	private Date  beginDate;
	/**
	 * 截止日期（供查询用）
	 */
	private Date  endDate;
	public Long getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}
	public Integer getWorkOrderStatus() {
		return workOrderStatus;
	}
	public void setWorkOrderStatus(Integer workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}
	public Long getCreateMemberCode() {
		return createMemberCode;
	}
	public void setCreateMemberCode(Long createMemberCode) {
		this.createMemberCode = createMemberCode;
	}
	public String getCreateOperator() {
		return createOperator;
	}
	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}
	public Long getAuditMemberCode() {
		return auditMemberCode;
	}
	public void setAuditMemberCode(Long auditMemberCode) {
		this.auditMemberCode = auditMemberCode;
	}
	public String getAuditOperator() {
		return auditOperator;
	}
	public void setAuditOperator(String auditOperator) {
		this.auditOperator = auditOperator;
	}
	public String getAuditRemark() {
		return auditRemark;
	}
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
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
	public String getOrderSmaillType() {
		return orderSmallType;
	}
	public void setOrderSmaillType(String orderSmaillType) {
		this.orderSmallType = orderSmaillType;
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
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getPayeeLoginName() {
		return payeeLoginName;
	}
	public void setPayeeLoginName(String payeeLoginName) {
		this.payeeLoginName = payeeLoginName;
	}
	public Long getPayeeMemberCode() {
		return payeeMemberCode;
	}
	public void setPayeeMemberCode(Long payeeMemberCode) {
		this.payeeMemberCode = payeeMemberCode;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	
	

}
