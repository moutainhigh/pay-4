/**
 *  <p>File: OrderCondition.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.fundout.dto;

public class OrderCondition {

	/**
	 * 会员订单号
	 */
	private String memberCode;
	/**
	 * 交易类型:0-all
	 */
	private Integer dealType;
	/**
	 * 交易开始时间
	 */
	private String startTime;
	/**
	 * 交易结束时间
	 */
	private String endTime;
	/**
	 * 交易订单号
	 */
	private String dealId;
	/**
	 * 账户号
	 */
	private String payerAcctCode;
	
	/**
	 * 账户名称
	 */
	private String payerAcctName;
	
	private Integer orderStatus = 9;
	
	public String getPayerAcctCode() {
		return payerAcctCode;
	}
	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public Integer getDealType() {
		return dealType;
	}
	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getPayerAcctName() {
		return payerAcctName;
	}
	public void setPayerAcctName(String payerAcctName) {
		this.payerAcctName = payerAcctName;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
