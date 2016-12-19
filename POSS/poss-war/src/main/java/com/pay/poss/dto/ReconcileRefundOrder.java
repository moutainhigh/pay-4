package com.pay.poss.dto;

/**
*
* 这个代码和Fi-Accounting 中的RefundOrder对应。  
* （1）增加这个类的原因是， 解析map 太麻烦了。
* （2）换一个类的名字的原因是：　Fi-Accounting中的这个  ClearingRefundOrder 并不合理
*
**/


import java.util.Date;

/**
* REFUND_ORDER
*/

public class ReconcileRefundOrder implements java.io.Serializable {

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
	private Date complateDate;
	private Date updateDate;
	private Long refundOrderNo;
	private Long payeeFee;
	private Long payerFee;
	private String bgUrl;
	private Long refundAmount;
	private String partnerRefundOrderId;
	private String errorCode;
	private java.util.Date createDate;
	private String remark;
	private String serialNo;
	private String orderId;
	private String currencyCode;
	private String refundSource;
	private String rate;
	private Integer reconciliationFlg;
	// 操作人
	private String operator;
	// 批次
	private String bacthNo;

	private Date bacthCreateDate;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getBacthNo() {
		return bacthNo;
	}

	public void setBacthNo(String bacthNo) {
		this.bacthNo = bacthNo;
	}

	public Date getBacthCreateDate() {
		return bacthCreateDate;
	}

	public void setBacthCreateDate(Date bacthCreateDate) {
		this.bacthCreateDate = bacthCreateDate;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	private String errorMsg;
	
	public Integer getReconciliationFlg() {
		return reconciliationFlg;
	}

	public void setReconciliationFlg(Integer reconciliationFlg) {
		this.reconciliationFlg = reconciliationFlg;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRefundSource() {
		return refundSource;
	}

	public void setRefundSource(String refundSource) {
		this.refundSource = refundSource;
	}

	// 退款转换后金额
	private Long transferAmount;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Date getComplateDate() {
		return complateDate;
	}

	public void setComplateDate(Date complateDate) {
		this.complateDate = complateDate;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Long getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(Long transferAmount) {
		this.transferAmount = transferAmount;
	}

}