package com.pay.acc.acct.model;

import java.io.Serializable;
/**
 *  手工记账vo
 *  File: VouchAcct.java
 *  Description:
 *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016年8月30日   mmzhang     Create
 *
 */
public class VouchAcct implements Serializable{

/**
	 * Date      Author      Changes
	 * 2016年8月30日   mmzhang     Create
	 */
	private static final long serialVersionUID = 2201871861741583310L;

	private String accountCode ;
	
	/**
	 * 借贷标识， 1借2贷
	 */
	private Integer crdr;
	
	/**
	 * 金额
	 */
	private Long amount;

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public Integer getCrdr() {
		return crdr;
	}

	public void setCrdr(Integer crdr) {
		this.crdr = crdr;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
}
