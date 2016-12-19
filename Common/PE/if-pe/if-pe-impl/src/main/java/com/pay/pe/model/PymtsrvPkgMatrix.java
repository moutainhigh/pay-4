package com.pay.pe.model;

import java.util.ArrayList;
import java.util.List;

import com.pay.inf.model.Model;



public class PymtsrvPkgMatrix implements Model {

	private Integer paymentServicePkgCode;
	private Integer dealCode;
	private Integer orderPayMethod;
	private Integer orderType;
	private String description;

	public Integer getDealCode() {
		return dealCode;
	}

	public void setDealCode(Integer dealCode) {
		this.dealCode = dealCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PymtsrvPkgMatrix() {
		super();
	}

	private static List<String> pk = new ArrayList<String>();
	static {
		pk.add("dealcode");
		pk.add("orderPayMethod");
		pk.add("orderType");
	}

	public Integer getOrderPayMethod() {
		return this.orderPayMethod;
	}

	public Integer getOrderType() {
		return this.orderType;
	}

	public Integer getPaymentServicePkgCode() {
		return this.paymentServicePkgCode;
	}

	public void setOrderPayMethod(Integer orderPayMethod) {
		this.orderPayMethod = orderPayMethod;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setPaymentServicePkgCode(Integer paymentServicePkgCode) {
		this.paymentServicePkgCode = paymentServicePkgCode;
	}

	public Object getPrimaryKey() {
		Object[] result = new Object[] { this.getDealCode(),
				this.getOrderPayMethod(), this.getOrderType() };
		return result;
	}

	public void setPrimaryKey(Object key) {
		if (null != key) {
			Object[] obj = (Object[]) key;
			this.setDealCode((Integer) obj[0]);
			this.setOrderPayMethod((Integer) obj[1]);
			this.setOrderType((Integer) obj[2]);
		}
	}

	public List getPrimaryKeyFields() {
		return pk;
	}

}
