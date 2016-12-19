package com.pay.pe.accumulated.resources.dto;

public class AccumulatedResourcesDto {

	
	private Long 	id;					//主键
	private Integer orderType;			//订单类型
	private Integer	dealCode;			//支付方式
	private Integer dealType;			//订单类型
	private Integer paymentServiceCode;	//支付服务代码
	private Integer takeOn;				//作用方　１付款方，２收款方
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getDealType() {
		return dealType;
	}
	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}
	public Integer getPaymentServiceCode() {
		return paymentServiceCode;
	}
	public void setPaymentServiceCode(Integer paymentServiceCode) {
		this.paymentServiceCode = paymentServiceCode;
	}
	public Integer getTakeOn() {
		return takeOn;
	}
	public void setTakeOn(Integer takeOn) {
		this.takeOn = takeOn;
	}
	
}
