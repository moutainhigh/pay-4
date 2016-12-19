/**
 * 
 */
package com.pay.acc.acct.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 * 
 */
public class Acct implements Serializable {

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
	/**
	 * 账户余额
	 */
	private Long totalBalance ;
	
	/**
	 * 账户提现手续费币种 
	 */
	private String acctWithDrawCurrencyCode ;
	/**
	 * 账户提现手续费金额
	 */
	private Long acctWithDrawFee ;
	
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(final String currencyCode) {
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
	public void setAcctCode(final String acctCode) {
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
	public void setMemberCode(final Long memberCode) {
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
	public void setBalance(final Long balance) {
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
	public void setStatus(final Integer status) {
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
	public void setFrozeAmount(final Long frozeAmount) {
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
	public void setCreateDate(final Date createDate) {
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
	public void setUpdateDate(final Date updateDate) {
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
	public void setCreditBalance(final Long creditBalance) {
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
	public void setDebitBalance(final Long debitBalance) {
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
	public void setVer(final Long ver) {
		this.ver = ver;
	}

	public String getAcctWithDrawCurrencyCode() {
		return acctWithDrawCurrencyCode;
	}

	public void setAcctWithDrawCurrencyCode(final String acctWithDrawCurrencyCode) {
		this.acctWithDrawCurrencyCode = acctWithDrawCurrencyCode;
	}

	public Long getAcctWithDrawFee() {
		return acctWithDrawFee;
	}

	public void setAcctWithDrawFee(final Long acctWithDrawFee) {
		this.acctWithDrawFee = acctWithDrawFee;
	}

	public Long getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(final Long totalBalance) {
		this.totalBalance = totalBalance;
	}

	@Override
	public String toString() {
		return "Acct [acctCode=" + acctCode + ", memberCode=" + memberCode
				+ ", balance=" + balance + ", status=" + status
				+ ", frozeAmount=" + frozeAmount + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", currencyCode="
				+ currencyCode + ", creditBalance=" + creditBalance
				+ ", debitBalance=" + debitBalance + ", ver=" + ver
				+ ", acctWithDrawCurrencyCode=" + acctWithDrawCurrencyCode
				+ ", acctWithDrawFee=" + acctWithDrawFee + "]";
	}
}
