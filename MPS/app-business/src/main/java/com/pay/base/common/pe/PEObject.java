package com.pay.base.common.pe;

public class PEObject {
	
	/**
	 * 会员号
	 */
	String memberCode;
	/**
	 * 订单号	
	 */
	String orderId;
	
	/**
	 * 10-人民币账户
	 */
	Integer accountType;
	
	/**
	 * 1代表个人  付款方机构类型代码，MEMBER(1),  BANK(3), KQ(4)(如果付款方是会员账户时候，设置为1，如果付款方式为科目的时候，设置为3)
	 */
	Integer payerOrgType;
	
	/**
	 *  最后一级科目编号
	 */
	String payerOrgCode;
	
	/**
	 * 交易代码
	 */
	Integer dealCode;
	
	/**
	 * 订单代码
	 */
	Integer orderCode;
	
	/**
	 * 支付方式
	 */
	Integer payMethod;
	
	@SuppressWarnings("unused")
	private PEObject(){}
	
	public PEObject(String memberCode,String orderId,Integer accountType,Integer payerOrgType,String payerOrgCode,Integer dealCode,Integer orderCode,Integer payMethod){
		this.memberCode = memberCode;
		this.orderId = orderId;
		this.accountType = accountType;
		this.payerOrgCode = payerOrgCode;
		this.payerOrgType = payerOrgType;
		this.dealCode = dealCode;
		this.orderCode = orderCode;
		this.payMethod = payMethod;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {	
		this.orderId = orderId;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Integer getPayerOrgType() {
		return payerOrgType;
	}

	public void setPayerOrgType(Integer payerOrgType) {
		this.payerOrgType = payerOrgType;
	}

	public String getPayerOrgCode() {
		return payerOrgCode;
	}

	public void setPayerOrgCode(String payerOrgCode) {
		this.payerOrgCode = payerOrgCode;
	}

	public Integer getDealCode() {
		return dealCode;
	}

	public void setDealCode(Integer dealCode) {
		this.dealCode = dealCode;
	}

	public Integer getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}
	
	
}
