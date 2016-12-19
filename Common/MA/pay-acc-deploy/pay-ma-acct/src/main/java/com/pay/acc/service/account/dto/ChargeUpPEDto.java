/**
 * 
 */
package com.pay.acc.service.account.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 * 
 */
public class ChargeUpPEDto implements Serializable {

	private Integer dealCode;
	
	private Integer orderCode;
	
	private Integer payMethod;
	
	/**
	 *订单金额 
	 */
	private Long orderAmount;
	
	/**
	 * 支付发起人就是付款方
	 * 
	 */
	private String submitAcctCode;
	/**
	 * 付款方会员号
	 */
	private Long payMemberCode;
	/**
	 * 付款方账户
	 */
	@NotNull
	private String payAcctCode;
	
	private Integer payAcctType;
	
	private Integer payOrgType;
	
	private Long payOrgCode;

	/**
	 * 收款方会员号
	 */
	private Long revMemberCode;

	/**
	 * 收款方账户
	 */
	@NotNull
	private String revAcctCode;
	
	private Integer revAcctType;

	private Integer revOrgType;
	
	private Long revOrgCode;
	
	/**
	 * 付款方费用
	 */
	private Long payFee;
	/**
	 * 收款方费用
	 * 
	 */
	private Long revFee;

	/**
	 * @return the dealCode
	 */
	public Integer getDealCode() {
		return dealCode;
	}

	/**
	 * @param dealCode the dealCode to set
	 */
	public void setDealCode(Integer dealCode) {
		this.dealCode = dealCode;
	}

	/**
	 * @return the orderCode
	 */
	public Integer getOrderCode() {
		return orderCode;
	}

	/**
	 * @param orderCode the orderCode to set
	 */
	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}

	/**
	 * @return the payMethod
	 */
	public Integer getPayMethod() {
		return payMethod;
	}

	/**
	 * @param payMethod the payMethod to set
	 */
	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

	/**
	 * @return the orderAmount
	 */
	public Long getOrderAmount() {
		return orderAmount;
	}

	/**
	 * @param orderAmount the orderAmount to set
	 */
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	/**
	 * @return the submitAcctCode
	 */
	public String getSubmitAcctCode() {
		return submitAcctCode;
	}

	/**
	 * @param submitAcctCode the submitAcctCode to set
	 */
	public void setSubmitAcctCode(String submitAcctCode) {
		this.submitAcctCode = submitAcctCode;
	}

	/**
	 * @return the payMemberCode
	 */
	public Long getPayMemberCode() {
		return payMemberCode;
	}

	/**
	 * @param payMemberCode the payMemberCode to set
	 */
	public void setPayMemberCode(Long payMemberCode) {
		this.payMemberCode = payMemberCode;
	}

	/**
	 * @return the payAcctCode
	 */
	public String getPayAcctCode() {
		return payAcctCode;
	}

	/**
	 * @param payAcctCode the payAcctCode to set
	 */
	public void setPayAcctCode(String payAcctCode) {
		this.payAcctCode = payAcctCode;
	}

	/**
	 * @return the payAcctType
	 */
	public Integer getPayAcctType() {
		return payAcctType;
	}
	

	/**
	 * @return the payFee
	 */
	public Long getPayFee() {
		return payFee;
	}

	/**
	 * @param payFee the payFee to set
	 */
	public void setPayFee(Long payFee) {
		this.payFee = payFee;
	}

	/**
	 * @return the revFee
	 */
	public Long getRevFee() {
		return revFee;
	}

	/**
	 * @param revFee the revFee to set
	 */
	public void setRevFee(Long revFee) {
		this.revFee = revFee;
	}

	/**
	 * @param payAcctType the payAcctType to set
	 */
	public void setPayAcctType(Integer payAcctType) {
		this.payAcctType = payAcctType;
	}

	/**
	 * @return the payOrgType
	 */
	public Integer getPayOrgType() {
		return payOrgType;
	}

	/**
	 * @param payOrgType the payOrgType to set
	 */
	public void setPayOrgType(Integer payOrgType) {
		this.payOrgType = payOrgType;
	}

	/**
	 * @return the payOrgCode
	 */
	public Long getPayOrgCode() {
		return payOrgCode;
	}

	/**
	 * @param payOrgCode the payOrgCode to set
	 */
	public void setPayOrgCode(Long payOrgCode) {
		this.payOrgCode = payOrgCode;
	}

	/**
	 * @return the revMemberCode
	 */
	public Long getRevMemberCode() {
		return revMemberCode;
	}

	/**
	 * @param revMemberCode the revMemberCode to set
	 */
	public void setRevMemberCode(Long revMemberCode) {
		this.revMemberCode = revMemberCode;
	}

	/**
	 * @return the revAcctCode
	 */
	public String getRevAcctCode() {
		return revAcctCode;
	}

	/**
	 * @param revAcctCode the revAcctCode to set
	 */
	public void setRevAcctCode(String revAcctCode) {
		this.revAcctCode = revAcctCode;
	}

	/**
	 * @return the revAcctType
	 */
	public Integer getRevAcctType() {
		return revAcctType;
	}

	/**
	 * @param revAcctType the revAcctType to set
	 */
	public void setRevAcctType(Integer revAcctType) {
		this.revAcctType = revAcctType;
	}

	/**
	 * @return the revOrgType
	 */
	public Integer getRevOrgType() {
		return revOrgType;
	}

	/**
	 * @param revOrgType the revOrgType to set
	 */
	public void setRevOrgType(Integer revOrgType) {
		this.revOrgType = revOrgType;
	}

	/**
	 * @return the revOrgCode
	 */
	public Long getRevOrgCode() {
		return revOrgCode;
	}

	/**
	 * @param revOrgCode the revOrgCode to set
	 */
	public void setRevOrgCode(Long revOrgCode) {
		this.revOrgCode = revOrgCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ChargeUpPEDto [dealCode=" + dealCode + ", orderAmount=" + orderAmount + ", orderCode=" + orderCode + ", payAcctCode="
				+ payAcctCode + ", payAcctType=" + payAcctType + ", payMemberCode=" + payMemberCode + ", payMethod=" + payMethod
				+ ", payOrgCode=" + payOrgCode + ", payOrgType=" + payOrgType + ", revAcctCode=" + revAcctCode + ", revAcctType="
				+ revAcctType + ", revMemberCode=" + revMemberCode + ", revOrgCode=" + revOrgCode + ", revOrgType=" + revOrgType
				+ ", submitAcctCode=" + submitAcctCode + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dealCode == null) ? 0 : dealCode.hashCode());
		result = prime * result + ((orderAmount == null) ? 0 : orderAmount.hashCode());
		result = prime * result + ((orderCode == null) ? 0 : orderCode.hashCode());
		result = prime * result + ((payAcctCode == null) ? 0 : payAcctCode.hashCode());
		result = prime * result + ((payAcctType == null) ? 0 : payAcctType.hashCode());
		result = prime * result + ((payMemberCode == null) ? 0 : payMemberCode.hashCode());
		result = prime * result + ((payMethod == null) ? 0 : payMethod.hashCode());
		result = prime * result + ((payOrgCode == null) ? 0 : payOrgCode.hashCode());
		result = prime * result + ((payOrgType == null) ? 0 : payOrgType.hashCode());
		result = prime * result + ((revAcctCode == null) ? 0 : revAcctCode.hashCode());
		result = prime * result + ((revAcctType == null) ? 0 : revAcctType.hashCode());
		result = prime * result + ((revMemberCode == null) ? 0 : revMemberCode.hashCode());
		result = prime * result + ((revOrgCode == null) ? 0 : revOrgCode.hashCode());
		result = prime * result + ((revOrgType == null) ? 0 : revOrgType.hashCode());
		result = prime * result + ((submitAcctCode == null) ? 0 : submitAcctCode.hashCode());
		return result;
	}

	

	
	

}
