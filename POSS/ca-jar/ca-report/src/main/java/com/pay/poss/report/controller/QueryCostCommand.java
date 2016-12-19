package com.pay.poss.report.controller;

import java.io.Serializable;

public class QueryCostCommand implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 开始时间
	 */
	private String startDate;
	
	/**
	 * 结束时间
	 */
	private String endDate;
	
	/**
	 * 业务类型
	 */
	private String businessType;
	
	/**
	 *  1 直连，2 手工
	 */
	private Integer type;
	
	/**
	 * 出款，入款渠道
	 */
	private String channel;
	
	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	/**
	 * 是否为分子公司
	 */
	private String innerMember;
	
	/**
	 * 会员类型
	 */
	private Integer memberType;

	/**
	 * 会员编号
	 */
	private String memberCode;
	
	/**
	 * 分子公司编号
	 */	
	private String imemberCode;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getInnerMember() {
		return innerMember;
	}

	public void setInnerMember(String innerMember) {
		this.innerMember = innerMember;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public void setImemberCode(String imemberCode) {
		this.imemberCode = imemberCode;
	}

	public String getImemberCode() {
		return imemberCode;
	}


}
