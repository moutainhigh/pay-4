package com.pay.txncore.dto;

public class TradeCountMap {
	
	/**
	 * 数量
	 */
	private int amount;
	
	/**
	 * 会员号
	 */
	private String partnerId;
	
	/**
	 * 状态
	 */
	private int status;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
