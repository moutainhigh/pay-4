/**
 * 
 */
package com.pay.credit.model.creditcurrency;

import java.util.Date;

/**
 * 商户授信币种
 * @author PengJiangbo
 *
 */
public class PartnerCreditCurrencyCode {
	/**
	 * ID	NUMBER
		CREDIT_CURRENCY	VARCHAR2(20 BYTE)
		PARTNERID	VARCHAR2(20 BYTE)
		CREATEDATE	DATE
		UPDATEDATE	DATE
		STATUS	VARCHAR2(20 BYTE)
		OPERATOR	VARCHAR2(20 BYTE)
	 */
	/** 主键 */
	private Integer id ;
	/** 授信币种 */
	private String creditCurrency ;
	/** 会员号 */
	private Long partnerId ;
	/** 创建时间 */
	private Date createDate ;
	/** 更新时间 */
	private Date updateDate ;
	/** 状态 */
	private String status ;
	/** 操作人 */
	private String operator ;
	
	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getCreditCurrency() {
		return creditCurrency;
	}

	public void setCreditCurrency(final String creditCurrency) {
		this.creditCurrency = creditCurrency;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(final Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(final String operator) {
		this.operator = operator;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(final Long partnerId) {
		this.partnerId = partnerId;
	}

	@Override
	public String toString() {
		return "PartnerCreditCurrencyCode [id=" + id + ", creditCurrency="
				+ creditCurrency + ", partnerId=" + partnerId + ", createDate="
				+ createDate + ", updateDate=" + updateDate + ", status="
				+ status + ", operator=" + operator + "]";
	}

}
