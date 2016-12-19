/**
 * 
 */
package com.pay.acc.deal.dto;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class BalanceDealSimpleDto implements Serializable {
	/**
	 * 流水号
	 */
	private String serialNo;
	
	/**
	 * 付款方账号
	 */
	private String payAcctCode;
	
	/**
	 * 收款方账户
	 */
	private String revAcctCode;
	
	private String payOrgCode;
	
	private String revOrgCode;
	
	/**
	 * 传入的金额
	 */
	private Long amount;
	
	/**
	 * 记账状态
	 */
	private Integer chargeupStatus;

	/**
	 * @return the amount
	 */
	public Long getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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
	 * @return the payOrgCode
	 */
	public String getPayOrgCode() {
		return payOrgCode;
	}

	/**
	 * @param payOrgCode the payOrgCode to set
	 */
	public void setPayOrgCode(String payOrgCode) {
		this.payOrgCode = payOrgCode;
	}

	/**
	 * @return the revOrgCode
	 */
	public String getRevOrgCode() {
		return revOrgCode;
	}

	/**
	 * @param revOrgCode the revOrgCode to set
	 */
	public void setRevOrgCode(String revOrgCode) {
		this.revOrgCode = revOrgCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BalanceDealSimpleDto [amount=" + amount + ", payAcctCode=" + payAcctCode + ", payOrgCode=" + payOrgCode + ", revAcctCode="
				+ revAcctCode + ", revOrgCode=" + revOrgCode + ", serialNo=" + serialNo + "]";
	}

	public Integer getChargeupStatus() {
		return chargeupStatus;
	}

	public void setChargeupStatus(Integer chargeupStatus) {
		this.chargeupStatus = chargeupStatus;
	}

	
	

}
