package com.pay.pe.dto;

import java.io.Serializable;
import java.util.Date;

import com.pay.util.DateUtil;

/**
 * @Company: g
 * @modify
 * @version 1.0
 */
public class AccountEntryDTO implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4291157019984952148L;

	/* 账户代码 */
	private String acctcode;
	/* 借贷标志 */
	private Integer crdr;
	/* 金额 */
	private Long value;
	/* 加减标记 */
	private Integer maBlanceBy;
	/* 交易号 */
	private String dealId;
	/* 分录号 */
	private Integer entrycode;
	/* 创建时间 */
	private Date createdate;
	/* 支付服务号 */
	private Integer paymentServiceId;
	/* 状态:1-已记账，0-未记账 */
	private Integer status;
	/* 摘要 */
	private String text;
	/* 凭证号 */
	private Long vouchercode;
	/* 记账币种 */
	private String currencyCode;
	/* 记账汇率 */
	private Long exchangeRate;
	/* 交易时间 */
	private Date transactiondate;
	// 交易类型
	private Integer dealType;

	public Integer getDealType() {
		return dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	/**
	 * 实际交易时间，如果为空则默认为dealbegindate
	 */
	public Date getTransactiondate() {
		return transactiondate;
	}

	public void setTransactiondate(Date transactiondate) {
		this.transactiondate = transactiondate;
	}

	public String getAcctcode() {
		return acctcode;
	}

	public void setAcctcode(String acctcode) {
		this.acctcode = acctcode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Long exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Integer getCrdr() {
		return crdr;
	}

	public void setCrdr(Integer crdr) {
		this.crdr = crdr;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Integer getEntrycode() {
		return entrycode;
	}

	public void setEntrycode(Integer entrycode) {
		this.entrycode = entrycode;
	}

	/**
	 * @return Returns the dealId.
	 */
	public String getDealId() {
		return dealId;
	}

	/**
	 * @param dealId
	 *            The dealId to set.
	 */
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	/**
	 * @return Returns the paymentServiceId.
	 */
	public Integer getPaymentServiceId() {
		return paymentServiceId;
	}

	/**
	 * @param paymentServiceId
	 *            The paymentServiceId to set.
	 */
	public void setPaymentServiceId(Integer paymentServiceId) {
		this.paymentServiceId = paymentServiceId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public Long getVouchercode() {
		return vouchercode;
	}

	public void setVouchercode(Long vouchercode) {
		this.vouchercode = vouchercode;
	}

	public static void main(String[] args) {
		Date d = new Date();
		System.out.println(DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", d));
	}

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
		final String TAB = "    ";

		StringBuffer retValue = new StringBuffer();

		retValue.append("AccountEntryDTO ( ").append(super.toString())
				.append(TAB).append("acctcode = ").append(this.acctcode)
				.append(TAB).append("crdr = ").append(this.crdr).append(TAB)
				.append("createdate = ").append(this.createdate).append(TAB)
				.append("entrycode = ").append(this.entrycode).append(TAB)
				.append("dealId = ").append(this.dealId).append(TAB)
				.append("paymentServiceId = ").append(this.paymentServiceId)
				.append(TAB).append("status = ").append(this.status)
				.append(TAB).append("text = ").append(this.text).append(TAB)
				.append("value = ").append(this.value).append(TAB)
				.append("vouchercode = ").append(this.vouchercode).append(TAB)
				.append("currencyCode = ").append(this.currencyCode)
				.append(TAB).append("exchangeRate = ")
				.append(this.exchangeRate).append(TAB)
				.append("transactiondate = ").append(this.transactiondate)
				.append(TAB).append(" )");

		return retValue.toString();
	}

	public Integer getMaBlanceBy() {
		return maBlanceBy;
	}

	public void setMaBlanceBy(Integer maBlanceBy) {
		this.maBlanceBy = maBlanceBy;
	}

}
