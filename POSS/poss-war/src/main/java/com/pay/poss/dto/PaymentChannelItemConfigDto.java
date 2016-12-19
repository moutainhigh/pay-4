package com.pay.poss.dto;

import java.util.Date;

public class PaymentChannelItemConfigDto {
	
	private String id;

	private String memberCode;

	private String paymentType;

	private String paymentChannelItemId;

	private String operator;

	private Date createDate;
	/**渠道优先级**/
	private String channelPriority;

	public String getChannelPriority() {
		return channelPriority;
	}

	public void setChannelPriority(String channelPriority) {
		this.channelPriority = channelPriority;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentChannelItemId() {
		return paymentChannelItemId;
	}

	public void setPaymentChannelItemId(String paymentChannelItemId) {
		this.paymentChannelItemId = paymentChannelItemId;
	}
}
