package com.pay.pe.model;

import java.sql.Timestamp;

import com.pay.inf.model.Model;

public class PostingRule implements Model {
	private Integer postingRuleCode;

	private Long acctAliasAcctTypeCode;
	private Integer orgType;
	private Long acctCode;
	private Integer crDr;
	private Timestamp invalidDate;
	private Integer party;
	private PaymentService paymentService;
	private Integer paymentServiceCode;
	private Integer status;
	private Timestamp validDate;
	private Long acctAliasIndMbr;

	private Long acctAliasBizMbr;
	private Integer asynchronousPosting;
	private Integer mbrAcctSpecific;

	public PostingRule() {
		super();
		this.paymentService = new PaymentService();
	}

	public Long getAcctAliasBizMbr() {
		return this.acctAliasBizMbr;
	}

	public Long getAcctAliasIndMbr() {
		return this.acctAliasIndMbr;
	}

	public Integer getAsynchronousPosting() {
		return this.asynchronousPosting;
	}

	public Integer getCrDr() {
		return this.crDr;
	}

	public Integer getParty() {
		return this.party;
	}

	public PaymentService getPaymentService() {
		return (PaymentService) this.paymentService;
	}

	// protected ValueHolderInterface getPaymentServiceHolder() {
	// return this.paymentService;
	// }

	public Integer getStatus() {
		return this.status;
	}

	public void setAcctAliasBizMbr(Long acctAliasBizMbr) {
		this.acctAliasBizMbr = acctAliasBizMbr;
	}

	public void setAcctAliasIndMbr(Long acctAliasIndMbr) {
		this.acctAliasIndMbr = acctAliasIndMbr;
	}

	public Long getAcctAliasAcctTypeCode() {
		return acctAliasAcctTypeCode;
	}

	public void setAcctAliasAcctTypeCode(Long acctAliasAcctTypeCode) {
		this.acctAliasAcctTypeCode = acctAliasAcctTypeCode;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public void setAsynchronousPosting(Integer asynchronousPosting) {
		this.asynchronousPosting = asynchronousPosting;
	}

	public void setCrDr(Integer crdr) {
		this.crDr = crdr;
	}

	public void setParty(Integer party) {
		this.party = party;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
		// this.paymentService.setValue(paymentService);
	}

	// protected void setPaymentServiceHolder(ValueHolderInterface
	// paymentService) {
	// this.paymentService = paymentService;
	// }

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMbrAcctSpecific() {
		return mbrAcctSpecific;
	}

	public void setMbrAcctSpecific(Integer mbrAcctSpecific) {
		this.mbrAcctSpecific = mbrAcctSpecific;
	}

	public String getFkId() {
		if (null != this.getPaymentService())
			return this.getPaymentService().getPaymentServiceCode().toString();
		else
			return null;
	}

	// public String toString() {
	// StringBuffer sb = new StringBuffer();
	// sb.append("acctAliasAcctType->" + acctAliasAcctType + ";");
	// sb.append("acctAliasOrgType->" + acctAliasOrgType + ";");
	// sb.append("acctcode->" + acctcode + ";");
	// sb.append("crdr->" + crdr + ";");
	// sb.append("invaliddate->" + invaliddate + ";");
	// sb.append("party->" + party + ";");
	// sb.append("paymentService->" + getFkId() + ";");
	// sb.append("postingrulecode->" + postingrulecode + ";");
	// sb.append("status->" + status + ";");
	// sb.append("validdate->" + validdate + ";");
	// sb.append("acctAliasIndMbr->" + acctAliasIndMbr + ";");
	// sb.append("asynchronousPosting->" + asynchronousPosting + ";");
	// sb.append("mbrAcctSpecific->" + mbrAcctSpecific + ";");
	// return sb.toString();
	// }
	public Integer getPaymentServicePK() {
		if (null != this.getPaymentService())
			return this.getPaymentService().getPaymentServiceCode();
		return null;
	}

	public Long getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(Long acctCode) {
		this.acctCode = acctCode;
	}

	public Timestamp getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Timestamp invalidDate) {
		this.invalidDate = invalidDate;
	}

	public Integer getPostingRuleCode() {
		return postingRuleCode;
	}

	public void setPostingRuleCode(Integer postingRuleCode) {
		this.postingRuleCode = postingRuleCode;
	}

	public Timestamp getValidDate() {
		return validDate;
	}

	public void setValidDate(Timestamp validDate) {
		this.validDate = validDate;
	}

	public Integer getPaymentServiceCode() {
		return paymentServiceCode;
	}

	public void setPaymentServiceCode(Integer paymentServiceCode) {
		this.paymentServiceCode = paymentServiceCode;
	}

}
