/**
 * 
 */
package com.pay.credit.model.creditorder;

import java.math.BigDecimal;


/**
 * @author PengJiangbo
 *
 */
public class ExtractCreditOrderDetailCommon {
	/** 融资订单流水号 */
	private String creditOrderId ;
	/** 商户号 */
	private String merchantCode ;
	/** 商户名称 */
	private String merchantName ;
	/** 状态 */
	private String status ;
	/** 放款金额 */
	private BigDecimal gmtAmount ;
	/** 利息 */
	private BigDecimal loanAmount ; 
	/** 利率 */
	private BigDecimal interest ;
	
	public String getCreditOrderId() {
		return creditOrderId;
	}
	public void setCreditOrderId(final String creditOrderId) {
		this.creditOrderId = creditOrderId;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(final String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(final String merchantName) {
		this.merchantName = merchantName;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}
	
	public BigDecimal getGmtAmount() {
		return gmtAmount;
	}
	public void setGmtAmount(final BigDecimal gmtAmount) {
		this.gmtAmount = gmtAmount;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(final BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(final BigDecimal interest) {
		this.interest = interest;
	}
	@Override
	public String toString() {
		return "ExtractCreditOrderDetailCommon [creditOrderId=" + creditOrderId
				+ ", merchantCode=" + merchantCode + ", merchantName="
				+ merchantName + ", status=" + status + ", gmtAmount="
				+ gmtAmount + ", loanAmount=" + loanAmount + ", interest="
				+ interest + "]";
	}
	
}
