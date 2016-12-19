/**
 * 
 */
package com.pay.fo.order.model.bsp;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author NEW
 *
 */
public class AuditQueryOrder extends BaseObject {
	/**
	 * 工单号
	 */
	private Long workOrderId;
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
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 更新日期
	 */
	private Date updateDate;
	/**
	 * 其他信息（创建者的外网用户名）
	 */
	private String externalInfo;
	/**
	 * 付款方名称
	 */
	private String payerName;
	/**
	 * 付款方会员号
	 */
	private Long payerMemberCode;
	/**
	 * 收款方名称
	 */
	private String payeeName;
	/**
	 * 收款方会员号
	 */
	private String payeeMemberCode;
	
	/**
	 * 资金调拨金额
	 */
	private Long amount;
	
	/**
	 * 资金调拨金额(格式化)
	 */
	private String amountStr;
	
	
	/**
	 * 起始日期（供查询用）
	 */
	private Date  beginDate;
	/**
	 * 截止日期（供查询用）
	 */
	private Date  endDate;
	
	private String remarks;
	
	public Long getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
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
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
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
	public String getExternalInfo() {
		return externalInfo;
	}
	public void setExternalInfo(String externalInfo) {
		this.externalInfo = externalInfo;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
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
	public String getPayeeMemberCode() {
		return payeeMemberCode;
	}
	public void setPayeeMemberCode(String payeeMemberCode) {
		this.payeeMemberCode = payeeMemberCode;
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
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getAmountStr() {
		return amountStr;
	}
	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
