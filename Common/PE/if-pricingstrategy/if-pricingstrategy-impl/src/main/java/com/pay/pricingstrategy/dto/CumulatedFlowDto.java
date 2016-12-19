package com.pay.pricingstrategy.dto;

import java.math.BigDecimal;

public class CumulatedFlowDto {
	private String 		acctCode;
	private Long 		id;
	private	Integer 	orderType;
	private Integer		dealCode;
	private	Integer 	month;
	private Integer 	type;
	private BigDecimal 	totalAmount;
	private Integer 	paymentServiceCode;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getDealCode() {
		return dealCode;
	}
	public void setDealCode(Integer dealCode) {
		this.dealCode = dealCode;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Integer getPaymentServiceCode() {
		return paymentServiceCode;
	}
	public void setPaymentServiceCode(Integer paymentServiceCode) {
		this.paymentServiceCode = paymentServiceCode;
	}

}
