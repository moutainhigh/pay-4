package com.pay.txncore.dto.refund;

public class RefundOrderQueryDTO {
	private String refundObjType;
    private String requestSerialId;
    private Long tradeOrderNo;
    private String status;
    private Long depositBackNo;
    private String pageUrl;
    private Long refundValue;
    private String partnerId;
    private Long paymentOrderNo;
    private java.util.Date partnerRefundTime;
    private String refundType;
    private java.util.Date updateDate;
    private Long refundOrderNo;
    private Long payeeFee;
    private Long payerFee;
    private String bgUrl;
    private Long refundAmount;
    private String partnerRefundOrderId;
    private String errorCode;
    private java.util.Date createDate;
    private String remark;
    private String strCreateDate;
    private String strUpdateDate;
    private String strRefundAmount;
    private String strActualRefund;
    private String strPayeeFee;
    
    
	public String getStrPayeeFee() {
		return strPayeeFee;
	}
	public void setStrPayeeFee(String strPayeeFee) {
		this.strPayeeFee = strPayeeFee;
	}
	public String getStrRefundAmount() {
		return strRefundAmount;
	}
	public void setStrRefundAmount(String strRefundAmount) {
		this.strRefundAmount = strRefundAmount;
	}
	public String getStrActualRefund() {
		return strActualRefund;
	}
	public void setStrActualRefund(String strActualRefund) {
		this.strActualRefund = strActualRefund;
	}
	public String getStrCreateDate() {
		return strCreateDate;
	}
	public void setStrCreateDate(String strCreateDate) {
		this.strCreateDate = strCreateDate;
	}
	public String getStrUpdateDate() {
		return strUpdateDate;
	}
	public void setStrUpdateDate(String strUpdateDate) {
		this.strUpdateDate = strUpdateDate;
	}
	public String getRefundObjType() {
		return refundObjType;
	}
	public void setRefundObjType(String refundObjType) {
		this.refundObjType = refundObjType;
	}
	public String getRequestSerialId() {
		return requestSerialId;
	}
	public void setRequestSerialId(String requestSerialId) {
		this.requestSerialId = requestSerialId;
	}
	public Long getTradeOrderNo() {
		return tradeOrderNo;
	}
	public void setTradeOrderNo(Long tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getDepositBackNo() {
		return depositBackNo;
	}
	public void setDepositBackNo(Long depositBackNo) {
		this.depositBackNo = depositBackNo;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public Long getRefundValue() {
		return refundValue;
	}
	public void setRefundValue(Long refundValue) {
		this.refundValue = refundValue;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public Long getPaymentOrderNo() {
		return paymentOrderNo;
	}
	public void setPaymentOrderNo(Long paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}
	public java.util.Date getPartnerRefundTime() {
		return partnerRefundTime;
	}
	public void setPartnerRefundTime(java.util.Date partnerRefundTime) {
		this.partnerRefundTime = partnerRefundTime;
	}
	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	public java.util.Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}
	public Long getRefundOrderNo() {
		return refundOrderNo;
	}
	public void setRefundOrderNo(Long refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getBgUrl() {
		return bgUrl;
	}
	public void setBgUrl(String bgUrl) {
		this.bgUrl = bgUrl;
	}
	public Long getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getPartnerRefundOrderId() {
		return partnerRefundOrderId;
	}
	public void setPartnerRefundOrderId(String partnerRefundOrderId) {
		this.partnerRefundOrderId = partnerRefundOrderId;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	@Override
	public String toString() {
		return "RefundOrderQueryDTO [bgUrl=" + bgUrl + ", createDate="
				+ createDate + ", depositBackNo=" + depositBackNo
				+ ", errorCode=" + errorCode + ", pageUrl=" + pageUrl
				+ ", partnerId=" + partnerId + ", partnerRefundOrderId="
				+ partnerRefundOrderId + ", partnerRefundTime="
				+ partnerRefundTime + ", payeeFee=" + payeeFee + ", payerFee="
				+ payerFee + ", paymentOrderNo=" + paymentOrderNo
				+ ", refundAmount=" + refundAmount + ", refundObjType="
				+ refundObjType + ", refundOrderNo=" + refundOrderNo
				+ ", refundType=" + refundType + ", refundValue=" + refundValue
				+ ", remark=" + remark + ", requestSerialId=" + requestSerialId
				+ ", status=" + status + ", strActualRefund=" + strActualRefund
				+ ", strCreateDate=" + strCreateDate + ", strPayeeFee="
				+ strPayeeFee + ", strRefundAmount=" + strRefundAmount
				+ ", strUpdateDate=" + strUpdateDate + ", tradeOrderNo="
				+ tradeOrderNo + ", updateDate=" + updateDate + "]";
	}
}
