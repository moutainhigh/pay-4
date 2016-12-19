package com.pay.txncore.crosspay.model;

import java.io.Serializable;
import java.util.Date;

public class OrderQueryResult  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 233174498898779911L;

	private String tradeOrderNo;
	
	private String orderId;
	
	private String tradeUrl;
	
	private String email;
	
	private String partnerId;
	
	private Date createDate;
	
	private String createDateString;
	
	private String orderAmount;
	
	private String extOrderInfo8;
	
	private String orderStatus;
	
	private String frozenStatus;
	
	private String refuseStatus;

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

	public String getTradeUrl() {
		return tradeUrl;
	}

	public void setTradeUrl(String tradeUrl) {
		this.tradeUrl = tradeUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getExtOrderInfo8() {
		return extOrderInfo8;
	}

	public void setExtOrderInfo8(String extOrderInfo8) {
		this.extOrderInfo8 = extOrderInfo8;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getFrozenStatus() {
		return frozenStatus;
	}

	public void setFrozenStatus(String frozenStatus) {
		this.frozenStatus = frozenStatus;
	}

	public String getRefuseStatus() {
		return refuseStatus;
	}

	public void setRefuseStatus(String refuseStatus) {
		this.refuseStatus = refuseStatus;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getCreateDateString() {
		return createDateString;
	}

	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}
	
	
	
	
}
