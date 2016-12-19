package com.pay.txncore.dto.refund;

public class BatchReqDetailDTO {
	
	private String itemNo = "0";
	private String orderNo ="0";
	private String requestOrderAmount="0";
	private Long orderAmount = 0L;
	private String requestRefundAmount="0";
	private Long refundAmount=0L;
	private String remark;
	private String errorMsg;
	private boolean isValid;
    private String partnerId;
    
    private String refundType;
    private String refundDest;
    private Long refundOrderNo;
    private String resultCode;
    private Long refundDetailNo;
    
    
	public Long getRefundDetailNo() {
		return refundDetailNo;
	}

	public void setRefundDetailNo(Long refundDetailNo) {
		this.refundDetailNo = refundDetailNo;
	}

	public Long getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(Long refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRequestOrderAmount() {
		return requestOrderAmount;
	}

	public void setRequestOrderAmount(String requestOrderAmount) {
		this.requestOrderAmount = requestOrderAmount;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getRequestRefundAmount() {
		return requestRefundAmount;
	}

	public void setRequestRefundAmount(String requestRefundAmount) {
		this.requestRefundAmount = requestRefundAmount;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
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

	public String getRefundDest() {
		return refundDest;
	}

	public void setRefundDest(String refundDest) {
		this.refundDest = refundDest;
	}

	@Override
	public String toString() {
		return "BatchReqDetailDTO [errorMsg=" + errorMsg + ", isValid="
				+ isValid + ", itemNo=" + itemNo + ", orderAmount="
				+ orderAmount + ", orderNo=" + orderNo + ", partnerId="
				+ partnerId + ", refundAmount=" + refundAmount
				+ ", refundDest=" + refundDest + ", refundType=" + refundType
				+ ", remark=" + remark + ", requestOrderAmount="
				+ requestOrderAmount + ", requestRefundAmount="
				+ requestRefundAmount + "]";
	}
}
