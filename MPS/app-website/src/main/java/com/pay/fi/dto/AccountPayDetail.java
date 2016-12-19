package com.pay.fi.dto;

public class AccountPayDetail {
	private String createDate;
	private String tradeOrderNo;
	private String orderId;
	private String payeeName;
	private String status;
	private String amount;
	private String paymentOrderNo;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(final String createDate) {
		this.createDate = createDate;
	}

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(final String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(final String payeeName) {
		this.payeeName = payeeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(final String amount) {
		this.amount = amount;
	}
	
	public String getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(final String paymentOrderNo) {
		this.paymentOrderNo = paymentOrderNo;
	}

	@Override
	public String toString() {
		return "AccountPayDetail [amount=" + amount + ", createDate="
				+ createDate + ", orderId=" + orderId + ", payeeName="
				+ payeeName + ", paymentOrderNo=" + paymentOrderNo
				+ ", status=" + status + ", tradeOrderNo=" + tradeOrderNo + "]";
	}
	
}
