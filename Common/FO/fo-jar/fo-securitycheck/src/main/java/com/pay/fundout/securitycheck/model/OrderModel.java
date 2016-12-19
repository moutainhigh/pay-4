package com.pay.fundout.securitycheck.model;

public class OrderModel {
	private int busiType;
	private long id;
	private long uerAmount;// 订单金额,用户录入金额
	private long payeeAmount;// 出款金额，收款方实收金额
	private long fee;// 费用
	private boolean payerPayFee = true;// true为付款方付手续费，false收款方付手续费
	private long payerAmount;// 实付金额，付款方实付金额
	private int status = 101;// 订单状态
	public int getBusiType() {
		return busiType;
	}
	public void setBusiType(int busiType) {
		this.busiType = busiType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUerAmount() {
		return uerAmount;
	}
	public void setUerAmount(long uerAmount) {
		this.uerAmount = uerAmount;
	}
	public long getPayeeAmount() {
		return payeeAmount;
	}
	public void setPayeeAmount(long payeeAmount) {
		this.payeeAmount = payeeAmount;
	}
	public long getFee() {
		return fee;
	}
	public void setFee(long fee) {
		this.fee = fee;
	}
	public boolean isPayerPayFee() {
		return payerPayFee;
	}
	public void setPayerPayFee(boolean payerPayFee) {
		this.payerPayFee = payerPayFee;
	}
	public long getPayerAmount() {
		return payerAmount;
	}
	public void setPayerAmount(long payerAmount) {
		this.payerAmount = payerAmount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
