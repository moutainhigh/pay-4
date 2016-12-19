/**
 * 
 */
package com.pay.acc.service.account.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * @author wolf_huang 
 * 
 * @date 2010-9-21
 */
public class AcctDetailBalanceDto implements Serializable {

	/**
	 *账户号 
	 */
	@NotNull
	private String  acctCode;
	
	/**
	 * 变化金额 
	 */
	@NotNull
	private Long amount;
	
	/**
	 *余额方向  （1为加，2为减）
	 */
	@NotNull
	private Integer amountBy;
	
	/**
	 * 借贷方向   （1为借，2为贷）
	 */
	@NotNull
	private Integer debitBy;
	
	/**
	 * 支付流水号
	 */
	@NotNull
	private Long serialNo;
	
	/**
	 * 支付类型
	 */
	@NotNull
	private Integer payType;
	
	/**
	 * 支付时间
	 */
	@NotNull
	private Date payDate;

	/**
	 * @return the acctCode
	 */
	public String getAcctCode() {
		return acctCode;
	}

	/**
	 * @param acctCode the acctCode to set
	 */
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

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
	 * @return the amountBy
	 */
	public Integer getAmountBy() {
		return amountBy;
	}

	/**
	 * @param amountBy the amountBy to set
	 */
	public void setAmountBy(Integer amountBy) {
		this.amountBy = amountBy;
	}

	/**
	 * @return the debitBy
	 */
	public Integer getDebitBy() {
		return debitBy;
	}

	/**
	 * @param debitBy the debitBy to set
	 */
	public void setDebitBy(Integer debitBy) {
		this.debitBy = debitBy;
	}

	/**
	 * @return the serialNo
	 */
	public Long getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the payType
	 */
	public Integer getPayType() {
		return payType;
	}

	/**
	 * @param payType the payType to set
	 */
	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	/**
	 * @return the payDate
	 */
	public Date getPayDate() {
		return payDate;
	}

	/**
	 * @param payDate the payDate to set
	 */
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AcctDetailBalanceDto [acctCode=" + acctCode + ", amount=" + amount + ", amountBy=" + amountBy + ", debitBy=" + debitBy
				+ ", payDate=" + payDate + ", payType=" + payType + ", serialNo=" + serialNo + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acctCode == null) ? 0 : acctCode.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((amountBy == null) ? 0 : amountBy.hashCode());
		result = prime * result + ((debitBy == null) ? 0 : debitBy.hashCode());
		result = prime * result + ((payDate == null) ? 0 : payDate.hashCode());
		result = prime * result + ((payType == null) ? 0 : payType.hashCode());
		result = prime * result + ((serialNo == null) ? 0 : serialNo.hashCode());
		return result;
	}

	
	
	
}
