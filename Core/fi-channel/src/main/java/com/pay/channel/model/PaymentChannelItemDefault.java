package com.pay.channel.model;

public class PaymentChannelItemDefault implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private java.util.Date createDate;
	private Integer paymentType;
	private Integer memberType;
	private Long paymentChannelItemId;
	private String operator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Long getPaymentChannelItemId() {
		return paymentChannelItemId;
	}

	public void setPaymentChannelItemId(Long paymentChannelItemId) {
		this.paymentChannelItemId = paymentChannelItemId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}