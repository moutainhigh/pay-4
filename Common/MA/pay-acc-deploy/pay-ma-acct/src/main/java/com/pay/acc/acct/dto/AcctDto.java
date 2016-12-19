/**
 * 
 */
package com.pay.acc.acct.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 * 
 */
public class AcctDto implements Serializable {

	/**
	 * 账户号
	 */
	private String acctCode;
	/**
	 * 会员号
	 */
	private Long memberCode;
	/**
	 * 余额
	 */
	private Long balance;
	/**
	 * 
	 */
	private Integer status;
	private Long frozeAmount = 0L;
	private Date createDate;
	private Date updateDate;
	private String currencyCode;
	/**
	 * 借方发生额
	 */
	private Long creditBalance;
	/**
	 * 
	 * 贷方发生额
	 */
	private Long debitBalance;

	/**
	 * 版本号
	 */
	private Long ver;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the acctCode
	 */
	public String getAcctCode() {
		return acctCode;
	}

	/**
	 * @param acctCode
	 *            the acctCode to set
	 */
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode
	 *            the memberCode to set
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the balance
	 */
	public Long getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(Long balance) {
		this.balance = balance;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the frozeAmount
	 */
	public Long getFrozeAmount() {
		return frozeAmount;
	}

	/**
	 * @param frozeAmount
	 *            the frozeAmount to set
	 */
	public void setFrozeAmount(Long frozeAmount) {
		this.frozeAmount = frozeAmount;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
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
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the creditBalance
	 */
	public Long getCreditBalance() {
		return creditBalance;
	}

	/**
	 * @param creditBalance
	 *            the creditBalance to set
	 */
	public void setCreditBalance(Long creditBalance) {
		this.creditBalance = creditBalance;
	}

	/**
	 * @return the debitBalance
	 */
	public Long getDebitBalance() {
		return debitBalance;
	}

	/**
	 * @param debitBalance
	 *            the debitBalance to set
	 */
	public void setDebitBalance(Long debitBalance) {
		this.debitBalance = debitBalance;
	}

	/**
	 * @return the ver
	 */
	public Long getVer() {
		return ver;
	}

	/**
	 * @param ver
	 *            the ver to set
	 */
	public void setVer(Long ver) {
		this.ver = ver;
	}

}
