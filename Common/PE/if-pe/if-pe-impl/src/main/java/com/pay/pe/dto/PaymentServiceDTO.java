package com.pay.pe.dto;

import java.io.Serializable;
import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.pe.dao.pagination.Pagination;

public class PaymentServiceDTO implements Serializable, Pagination {

	/**
	 * @id
	 */
	private static final long serialVersionUID = 1L;
	private Integer paymentservicecode;
	private String paymentservicename;
	private String description;
	private Long fixedpayer;
	private Long fixedpayee;
	private Integer payertype;
	private Integer payeetype;
	private Integer getmethod;
	private Integer paymethod;
	private Integer payeeAcctType;
	private Integer payerAcctType;
	private Page page;
	private Integer takeon;
	private Integer paymentServiceType;
	private String paymentServiceTypedesc;
	private List postingRuleCollectionDTO;
	private Integer reservedCodeType;

	/**
	 * 付费依据方
	 */
	private Integer dependOn;

	public Integer getReservedCodeType() {
		return reservedCodeType;
	}

	public void setReservedCodeType(Integer reservedCodeType) {
		this.reservedCodeType = reservedCodeType;
	}

	public Integer getPaymentServiceType() {
		return paymentServiceType;
	}

	public void setPaymentServiceType(Integer paymentServiceType) {
		this.paymentServiceType = paymentServiceType;
	}

	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getFixedpayee() {
		return fixedpayee;
	}

	public void setFixedpayee(Long fixedpayee) {
		this.fixedpayee = fixedpayee;
	}

	public Long getFixedpayer() {
		return fixedpayer;
	}

	public void setFixedpayer(Long fixedpayer) {
		this.fixedpayer = fixedpayer;
	}

	public Integer getGetmethod() {
		return getmethod;
	}

	public void setGetmethod(Integer getmethod) {
		this.getmethod = getmethod;
	}

	public Integer getPayeetype() {
		return payeetype;
	}

	public void setPayeetype(Integer payeetype) {
		this.payeetype = payeetype;
	}

	public Integer getPayertype() {
		return payertype;
	}

	public void setPayertype(Integer payertype) {
		this.payertype = payertype;
	}

	public Integer getPaymentservicecode() {
		return paymentservicecode;
	}

	public void setPaymentservicecode(Integer paymentservicecode) {
		this.paymentservicecode = paymentservicecode;
	}

	public String getPaymentservicename() {
		return paymentservicename;
	}

	public void setPaymentservicename(String paymentservicename) {
		this.paymentservicename = paymentservicename;
	}

	public Integer getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(Integer paymethod) {
		this.paymethod = paymethod;
	}

	/*
	 * (non-Javadoc)
	 */
	public void setPage(Page page) {
		this.page = page;

	}

	/*
	 * (non-Javadoc)
	 */
	public Page getPage() {
		// TODO Auto-generated method stub
		return this.page;
	}

	public Integer getTakeon() {
		return takeon;
	}

	public void setTakeon(Integer takeon) {
		this.takeon = takeon;
	}

	public Integer getDependOn() {
		return dependOn;
	}

	public void setDependOn(Integer dependOn) {
		this.dependOn = dependOn;
	}

	public List getPostingRuleCollectionDTO() {
		return postingRuleCollectionDTO;
	}

	public void setPostingRuleCollectionDTO(List postingRuleCollectionDTO) {
		this.postingRuleCollectionDTO = postingRuleCollectionDTO;
	}

	public String getPaymentServiceTypedesc() {

		return PaymentServiceType
				.getPAYMENTSERVICETYPEDesc(this.paymentServiceType);
	}

}
