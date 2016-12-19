package com.pay.pe.account.dto;

import java.math.BigDecimal;
import java.util.Date;

public class QueryEntryDTO {

	private static final long serialVersionUID = -5354049921871210101L;

	private String relatedOrderId;

	private String orderId;

	// 借贷标识
	private Integer crdr;
	// 创建日期
	private Date createdate;

	// 金额
	private BigDecimal value;

	// 过账日期
	private Date postDate;

	private String acctCode;

	private String crdrName;

	private String currencyCode;

	private String psCode;

	/**
	 * @return the relatedOrderId
	 */
	public String getRelatedOrderId() {
		return relatedOrderId;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param relatedOrderId
	 *            the relatedOrderId to set
	 */
	public void setRelatedOrderId(String relatedOrderId) {
		this.relatedOrderId = relatedOrderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the crdr
	 */
	public Integer getCrdr() {
		return crdr;
	}

	/**
	 * @return the createdate
	 */
	public Date getCreatedate() {
		return createdate;
	}

	/**
	 * @return the postDate
	 */
	public Date getPostDate() {
		return postDate;
	}

	/**
	 * @param crdr
	 *            the crdr to set
	 */
	public void setCrdr(Integer crdr) {
		this.crdr = crdr;
	}

	/**
	 * @param createdate
	 *            the createdate to set
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	/**
	 * @return the value
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	/**
	 * @param postDate
	 *            the postDate to set
	 */
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
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
	 * @return the crdrName
	 */
	public String getCrdrName() {
		return crdrName;
	}

	/**
	 * @param crdrName
	 *            the crdrName to set
	 */
	public void setCrdrName(String crdrName) {
		this.crdrName = crdrName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPsCode() {
		return psCode;
	}

	public void setPsCode(String psCode) {
		this.psCode = psCode;
	}

	@Override
	public String toString() {
		return "QueryEntryDTO [relatedOrderId=" + relatedOrderId + ", orderId="
				+ orderId + ", crdr=" + crdr + ", createdate=" + createdate
				+ ", value=" + value + ", postDate=" + postDate + ", acctCode="
				+ acctCode + ", crdrName=" + crdrName + ", currencyCode="
				+ currencyCode + ", psCode=" + psCode + "]";
	}
	
}
