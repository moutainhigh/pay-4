/**
 * 
 */
package com.pay.pe.dto;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * g
 *
 */
public class AccountDairyDTO implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -7582629171721351480L;
    private Long acctCode;
    
    
    private Timestamp acctperioddate;
    private Timestamp closingtime;
    private Long openingBal;
    private Long balance;
    private Long closingBal;
    private Integer closed;
    private Long openingCrBal;
    private Long openingDrBal;
    private Long debitBalance;
    private Long creditBalance;
    private Long closingCrBal;
    private Long closingDrBal;
	
  
	public Timestamp getAcctperioddate() {
        return acctperioddate;
    }
    public void setAcctperioddate(Timestamp acctperioddate) {
        this.acctperioddate = acctperioddate;
    }
    public Long getBalance() {
        return balance;
    }
    public void setBalance(Long balance) {
        this.balance = balance;
    }
    public Integer getClosed() {
        return closed;
    }
    public void setClosed(Integer closed) {
        this.closed = closed;
    }
    public Long getClosingBal() {
        return closingBal;
    }
    public void setClosingBal(Long closingBal) {
        this.closingBal = closingBal;
    }
    public Long getClosingCrBal() {
        return closingCrBal;
    }
    public void setClosingCrBal(Long closingCrBal) {
        this.closingCrBal = closingCrBal;
    }
    public Long getClosingDrBal() {
        return closingDrBal;
    }
    public void setClosingDrBal(Long closingDrBal) {
        this.closingDrBal = closingDrBal;
    }
    public Timestamp getClosingtime() {
        return closingtime;
    }
    public void setClosingtime(Timestamp closingtime) {
        this.closingtime = closingtime;
    }
    public Long getCreditBalance() {
        return creditBalance;
    }
    public void setCreditBalance(Long creditBalance) {
        this.creditBalance = creditBalance;
    }
    public Long getDebitBalance() {
        return debitBalance;
    }
    public void setDebitBalance(Long debitBalance) {
        this.debitBalance = debitBalance;
    }
    public Long getOpeningBal() {
        return openingBal;
    }
    public void setOpeningBal(Long openingBal) {
        this.openingBal = openingBal;
    }
    public Long getOpeningCrBal() {
        return openingCrBal;
    }
    public void setOpeningCrBal(Long openingCrBal) {
        this.openingCrBal = openingCrBal;
    }
    public Long getOpeningDrBal() {
        return openingDrBal;
    }
    public void setOpeningDrBal(Long openingDrBal) {
        this.openingDrBal = openingDrBal;
    }
    
    
    
    
    
	public Long getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(Long acctCode) {
		this.acctCode = acctCode;
	}
	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "    ";
	    
	    StringBuffer retValue = new StringBuffer();
	    
	    retValue.append("AccountDairyDTO ( ")
	        .append(super.toString()).append(TAB)
	        .append("accountDto = ").append(this.acctCode).append(TAB)
	        .append("acctperioddate = ").append(this.acctperioddate).append(TAB)
	        .append("closingtime = ").append(this.closingtime).append(TAB)
	        .append("openingBal = ").append(this.openingBal).append(TAB)
	        .append("balance = ").append(this.balance).append(TAB)
	        .append("closingBal = ").append(this.closingBal).append(TAB)
	        .append("closed = ").append(this.closed).append(TAB)
	        .append("openingCrBal = ").append(this.openingCrBal).append(TAB)
	        .append("openingDrBal = ").append(this.openingDrBal).append(TAB)
	        .append("debitBalance = ").append(this.debitBalance).append(TAB)
	        .append("creditBalance = ").append(this.creditBalance).append(TAB)
	        .append("closingCrBal = ").append(this.closingCrBal).append(TAB)
	        .append("closingDrBal = ").append(this.closingDrBal).append(TAB)
	        .append(" )");
	    
	    return retValue.toString();
	}
	
}
