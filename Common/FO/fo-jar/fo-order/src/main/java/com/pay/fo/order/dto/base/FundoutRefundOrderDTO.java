package com.pay.fo.order.dto.base;

import java.util.Date;

import com.pay.fo.order.dto.Order;

public class FundoutRefundOrderDTO extends Order{
    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 原订单号
     */
    private Long srcOrderId;

    /**
     * 原订单类型
     */
    private Integer srcOrderType;

    /**
     * 退款理由
     */
    private String refundReason;

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
     * 审核员
     */
    private String auditor;
    
    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 银行订单号
     */
    private String bankOrderId;

    /**
     * 防重复字段
     */
    private String uniqueKey;

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
	 * @return the srcOrderId
	 */
	public Long getSrcOrderId() {
		return srcOrderId;
	}

	/**
	 * @param srcOrderId the srcOrderId to set
	 */
	public void setSrcOrderId(Long srcOrderId) {
		this.srcOrderId = srcOrderId;
	}

	/**
	 * @return the srcOrderType
	 */
	public Integer getSrcOrderType() {
		return srcOrderType;
	}

	/**
	 * @param srcOrderType the srcOrderType to set
	 */
	public void setSrcOrderType(Integer srcOrderType) {
		this.srcOrderType = srcOrderType;
	}

	/**
	 * @return the refundReason
	 */
	public String getRefundReason() {
		return refundReason;
	}

	/**
	 * @param refundReason the refundReason to set
	 */
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
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
	 * @return the auditor
	 */
	public String getAuditor() {
		return auditor;
	}

	/**
	 * @param auditor the auditor to set
	 */
	public void setAuditor(String auditor) {
		this.auditor = auditor;
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
	 * @return the uniqueKey
	 */
	public String getUniqueKey() {
		return uniqueKey;
	}

	/**
	 * @param uniqueKey the uniqueKey to set
	 */
	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	/**
	 * @return the auditRemark
	 */
	public String getAuditRemark() {
		return auditRemark;
	}

	/**
	 * @param auditRemark the auditRemark to set
	 */
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

    
    
   
}