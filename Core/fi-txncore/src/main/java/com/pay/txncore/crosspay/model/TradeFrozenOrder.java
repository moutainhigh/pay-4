package com.pay.txncore.crosspay.model;

import java.util.Date;

/**
 * 
 *
 */
public class TradeFrozenOrder {
	private String tradeOrderNo;
	private String partnerId;
	private Date orderTime;
	private String url;
	private String status;
	private String clearStatus;
	public String getTradeOrderNo() {
		return tradeOrderNo;
	}
	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getClearStatus() {
		return clearStatus;
	}
	public void setClearStatus(String clearStatus) {
		this.clearStatus = clearStatus;
	}
}
