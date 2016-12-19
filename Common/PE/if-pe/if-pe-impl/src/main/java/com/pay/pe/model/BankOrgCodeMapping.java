/**
 * 
 */
package com.pay.pe.model;

import java.util.Date;

/**
 * @author chaoyue
 *
 */
public class BankOrgCodeMapping {

	private Long id;
	private Integer orderCode;
	private Integer dealCode;
	private Integer dependOn;
	private String bankCode;
	private String currencyCode;
	private String businessType;
	private Date createDate;
	private String operator;
	private String orgCode;
	private String description;
	private String psCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getDealCode() {
		return dealCode;
	}

	public void setDealCode(Integer dealCode) {
		this.dealCode = dealCode;
	}

	public Integer getDependOn() {
		return dependOn;
	}

	public void setDependOn(Integer dependOn) {
		this.dependOn = dependOn;
	}

	public String getPsCode() {
		return psCode;
	}

	public void setPsCode(String psCode) {
		this.psCode = psCode;
	}

}
