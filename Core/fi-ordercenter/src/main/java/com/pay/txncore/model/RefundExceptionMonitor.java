package com.pay.txncore.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RefundExceptionMonitor implements Serializable{

	private static final long serialVersionUID = -8020968892462987533L;

	private BigDecimal partnerId;
	
	private String merchantName;
	
	private BigDecimal refundOrderNo;
	
	private String orderId;
	
	private BigDecimal tradeOrderNo;
	
	private String orgCode;
	
	private String channelName;
	
	private BigDecimal refundAmount;

	private String currencyCode;
	
	private BigDecimal status;
	
	private Date createDate;
	
	//added by PengJiangbo 增加渠道订单号查询
	private Long channelOrderNo ;
	
	/**
	 * @return the channelOrderNo
	 */
	public Long getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(Long channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public BigDecimal getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(BigDecimal partnerId) {
		this.partnerId = partnerId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public BigDecimal getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(BigDecimal refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(BigDecimal tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getStatus() {
		return status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "RefundExceptionMonitor [partnerId=" + partnerId
				+ ", merchantName=" + merchantName + ", refundOrderNo="
				+ refundOrderNo + ", orderId=" + orderId + ", tradeOrderNo="
				+ tradeOrderNo + ", orgCode=" + orgCode + ", channelName="
				+ channelName + ", refundAmount=" + refundAmount
				+ ", currencyCode=" + currencyCode + ", status=" + status
				+ ", createDate=" + createDate + "]";
	}
	
	
}
