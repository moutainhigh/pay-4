/**
 * 
 */
package com.pay.base.model;

import java.util.Date;

/**
 * 伪账户类
 * @author PengJiangbo
 *
 */
public class PseudoAcct {

	private String currency ;
	private String acctName ;
	private Date createDate ;

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
