package com.pay.txncore.model;

import java.util.Date;

public class CreditFilterOrder {

	private String ip;
	private String email;
	private String cardholderCardno;
	private String shippingState;
	private String orderId;
	private String cpType;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCardholderCardno() {
		return cardholderCardno;
	}
	public void setCardholderCardno(String cardholderCardno) {
		this.cardholderCardno = cardholderCardno;
	}
	public String getShippingState() {
		return shippingState;
	}
	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCpType() {
		return cpType;
	}
	public void setCpType(String cpType) {
		this.cpType = cpType;
	}
	
	
}