/**
 *  File: AccountingEntryDetailDto.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Jan 5, 2012   ch-ma     Create
 *
 */
package com.pay.pe.dto;

/**
 * 
 */
public class AccountingEntryDetailDto {

	/* 帐户代码 */
	private String acctcode;
	/* 借贷方向 */
	private Integer crdr;
	/* 金额 */
	private Long value;
	/* ma更新余额方向 1 为正 2为负 */
	private Integer maBlanceBy;
	/* 货币代码 */
	private String currencyCode;
	/*交易号*/
	private String dealId;
	
	public String getAcctcode() {
		return acctcode;
	}
	public void setAcctcode(String acctcode) {
		this.acctcode = acctcode;
	}
	public Integer getCrdr() {
		return crdr;
	}
	public void setCrdr(Integer crdr) {
		this.crdr = crdr;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	public Integer getMaBlanceBy() {
		return maBlanceBy;
	}
	public void setMaBlanceBy(Integer maBlanceBy) {
		this.maBlanceBy = maBlanceBy;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

}
