package com.pay.txncore.dto.refund;

public class RefundApplyDTO {
	
	private String reason;
	
    private Integer status;
    
    private String partnerId;
    
    private String refundType;
    
    private String updateDate;
    
    private Long refundOrderNo;
    
    private String creator;
    
    private Long refundApplyNo;
    
    private String orderAmount;
    
    private Long applyBatchNo;
    
    private String applyAmount;
    
    private String createDate;
    
    private String actualAmount;
    
    private String orderId;
    
    private String auditOperator;
    
    private String remark1;
    
    private String remark2;
    
    //退款状态码
    private String stateCode;
 
   //退款异常消息码
    private String message;
    
    
   
	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Long getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(Long refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Long getRefundApplyNo() {
		return refundApplyNo;
	}

	public void setRefundApplyNo(Long refundApplyNo) {
		this.refundApplyNo = refundApplyNo;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getApplyBatchNo() {
		return applyBatchNo;
	}

	public void setApplyBatchNo(Long applyBatchNo) {
		this.applyBatchNo = applyBatchNo;
	}

	public String getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAuditOperator() {
		return auditOperator;
	}

	public void setAuditOperator(String auditOperator) {
		this.auditOperator = auditOperator;
	}

	@Override
	public String toString() {
		return "RefundApplyDTO [actualAmount=" + actualAmount
				+ ", applyAmount=" + applyAmount + ", applyBatchNo="
				+ applyBatchNo + ", auditOperator=" + auditOperator
				+ ", createDate=" + createDate + ", creator=" + creator
				+ ", orderAmount=" + orderAmount + ", orderId=" + orderId
				+ ", partnerId=" + partnerId + ", reason=" + reason
				+ ", refundApplyNo=" + refundApplyNo + ", refundOrderNo="
				+ refundOrderNo + ", refundType=" + refundType + ", status="
				+ status + ", updateDate=" + updateDate + "]";
	}


}
