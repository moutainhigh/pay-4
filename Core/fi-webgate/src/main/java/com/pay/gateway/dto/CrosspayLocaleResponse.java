package com.pay.gateway.dto;

public class CrosspayLocaleResponse {

	private String payAmount;
	
	private String responseCode;
	
	private String currencyCode;
	
	private String responseDesc;
	
	private String boletoUrl;

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getResponseDesc() {
		return responseDesc;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

	public String getBoletoUrl() {
		return boletoUrl;
	}

	public void setBoletoUrl(String boletoUrl) {
		this.boletoUrl = boletoUrl;
	}
}
