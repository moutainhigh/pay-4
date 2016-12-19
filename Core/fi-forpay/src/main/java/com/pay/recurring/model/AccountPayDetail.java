package com.pay.recurring.model;

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

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getPaymentOrderNo() {
		return paymentOrderNo;
	}

	public void setPaymentOrderNo(String paymentOrderNo) {
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
