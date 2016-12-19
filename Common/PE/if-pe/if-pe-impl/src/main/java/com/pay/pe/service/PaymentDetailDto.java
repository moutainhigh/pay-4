package com.pay.pe.service;

import java.io.Serializable;
import java.util.Date;

public class PaymentDetailDto implements Serializable{
	/* 帐户代码 */
	private String acctcode;
	/* 借贷方向 */
	private Integer crdr;
	/* 金额 */
	private Long value;
	/* ma更新余额方向 1 为正 2为负 */
	private Integer maBlanceBy;
	/* 支付服务code*/
	private Integer paymentServiceId;
	/* 货币代码 */
	private String currencyCode;

	private Long exchangeRate;

	private Date createdate;

	private Integer entrycode;

	private String dealId;

	private Integer status;

	private String text;

	private Long vouchercode;

	private Date transactiondate;
	
	private Integer dealType;
	
	private  String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDealType() {
    	return dealType;
    }

	public void setDealType(Integer dealType) {
    	this.dealType = dealType;
    }

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

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public Integer getPaymentServiceId() {
		return paymentServiceId;
	}

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

	public Date getTransactiondate() {
		return transactiondate;
	}

	public void setTransactiondate(Date transactiondate) {
		this.transactiondate = transactiondate;
	}

	public Integer getMaBlanceBy() {
		return maBlanceBy;
	}

	public void setMaBlanceBy(Integer maBlanceBy) {
		this.maBlanceBy = maBlanceBy;
	}
	
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("acctcode="+acctcode+"\n");
		buffer.append("crdr="+crdr+"\n");
		buffer.append("value="+value+"\n");
		buffer.append("maBlanceBy="+maBlanceBy+"\n");
		return buffer.toString();
	}

}
