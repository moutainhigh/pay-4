package com.pay.acct.crossBorderPay.model;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the KF_FEE_CONFIG database table.
 * 
 */
public class KfFeeConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	private long feeConfigNo;

	private Long capValue;

	private Date createDate;

	private Long fixedFee;

	private Long minimumValue;

	private String operator;

	private Long partnerId;

	private Long percentageFee;

	private Long smallBaselin;

	private Long smallServiceFee;

	private Date updateDate;

	public KfFeeConfig() {
		//super();
	}
	public KfFeeConfig(Long capValue, Long fixedFee, Long minimumValue,
			String operator, Long partnerId, Long percentageFee,
			Long smallBaselin, Long smallServiceFee) {
		super();
		this.capValue = capValue;
		this.fixedFee = fixedFee;
		this.minimumValue = minimumValue;
		this.operator = operator;
		this.partnerId = partnerId;
		this.percentageFee = percentageFee;
		this.smallBaselin = smallBaselin;
		this.smallServiceFee = smallServiceFee;
	}

	public long getFeeConfigNo() {
		return this.feeConfigNo;
	}

	public void setFeeConfigNo(long feeConfigNo) {
		this.feeConfigNo = feeConfigNo;
	}

	public Long getCapValue() {
		return this.capValue;
	}

	public void setCapValue(Long capValue) {
		this.capValue = capValue;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getFixedFee() {
		return this.fixedFee;
	}

	public void setFixedFee(Long fixedFee) {
		this.fixedFee = fixedFee;
	}

	public Long getMinimumValue() {
		return this.minimumValue;
	}

	public void setMinimumValue(Long minimumValue) {
		this.minimumValue = minimumValue;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Long getPercentageFee() {
		return this.percentageFee;
	}

	public void setPercentageFee(Long percentageFee) {
		this.percentageFee = percentageFee;
	}

	public Long getSmallBaselin() {
		return this.smallBaselin;
	}

	public void setSmallBaselin(Long smallBaselin) {
		this.smallBaselin = smallBaselin;
	}

	public Long getSmallServiceFee() {
		return this.smallServiceFee;
	}

	public void setSmallServiceFee(Long smallServiceFee) {
		this.smallServiceFee = smallServiceFee;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}