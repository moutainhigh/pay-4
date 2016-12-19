/**
 * 
 */
package com.pay.acc.balancelog.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class BalanceLog implements Serializable{
	private Long id;
	private String acctCode;
	private Date payDate;
	private Long balance;
	private Long serialNo;
	private Integer balanceDirect;
	private Integer balanceChangeType;
	private Long amount;
	private Date createDate;
	private Date updateDate;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
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
	/**
	 * @return the balance
	 */
	public Long getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(Long balance) {
		this.balance = balance;
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
	 * @return the balanceDirect
	 */
	public Integer getBalanceDirect() {
		return balanceDirect;
	}
	/**
	 * @param balanceDirect the balanceDirect to set
	 */
	public void setBalanceDirect(Integer balanceDirect) {
		this.balanceDirect = balanceDirect;
	}
	/**
	 * @return the balanceChangeType
	 */
	public Integer getBalanceChangeType() {
		return balanceChangeType;
	}
	/**
	 * @param balanceChangeType the balanceChangeType to set
	 */
	public void setBalanceChangeType(Integer balanceChangeType) {
		this.balanceChangeType = balanceChangeType;
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
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BalanceLog [acctCode=" + acctCode + ", amount=" + amount + ", balance=" + balance + ", balanceChangeType="
				+ balanceChangeType + ", balanceDirect=" + balanceDirect + ", createDate=" + createDate + ", id=" + id + ", payDate="
				+ payDate + ", serialNo=" + serialNo + ", updateDate=" + updateDate + "]";
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
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((balanceChangeType == null) ? 0 : balanceChangeType.hashCode());
		result = prime * result + ((balanceDirect == null) ? 0 : balanceDirect.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((payDate == null) ? 0 : payDate.hashCode());
		result = prime * result + ((serialNo == null) ? 0 : serialNo.hashCode());
		result = prime * result + ((updateDate == null) ? 0 : updateDate.hashCode());
		return result;
	}
	
	
	
	
	
	

}
