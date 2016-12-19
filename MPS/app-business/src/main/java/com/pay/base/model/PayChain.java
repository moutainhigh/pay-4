package com.pay.base.model;

import java.math.BigDecimal;
import java.util.Date;

public class PayChain implements java.io.Serializable{
	
	private static final long serialVersionUID = -752558383116388713L;
	private Long payChainId;
	private String payChainNumber;
	private String receiptDesc;//描述
	private Integer status;
	private Long memberCode;
	private String payChainUrl;
	private Date createDate;
	private Date updateDate;
	private Integer grandTotalStatus;
	private Long payTotalCount;
	private BigDecimal payTotalAmount;
	private Date overdueDate;
	private Integer effectiveDate;
	private String payChainName; //支付链名称
	
	public Long getPayChainId() {
		return payChainId;
	}
	public void setPayChainId(Long payChainId) {
		this.payChainId = payChainId;
	}
	public String getPayChainNumber() {
		return payChainNumber;
	}
	public void setPayChainNumber(String payChainNumber) {
		this.payChainNumber = payChainNumber;
	}
	public String getReceiptDesc() {
		return receiptDesc;
	}
	public void setReceiptDesc(String receiptDesc) {
		this.receiptDesc = receiptDesc;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getPayChainUrl() {
		return payChainUrl;
	}
	public void setPayChainUrl(String payChainUrl) {
		this.payChainUrl = payChainUrl;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getGrandTotalStatus() {
		return grandTotalStatus;
	}
	public void setGrandTotalStatus(Integer grandTotalStatus) {
		this.grandTotalStatus = grandTotalStatus;
	}
	
	public Long getPayTotalCount() {
		return payTotalCount;
	}
	public void setPayTotalCount(Long payTotalCount) {
		this.payTotalCount = payTotalCount;
	}
	public BigDecimal getPayTotalAmount() {
		return payTotalAmount;
	}
	public void setPayTotalAmount(BigDecimal payTotalAmount) {
		this.payTotalAmount = payTotalAmount;
	}
	public Date getOverdueDate() {
		return overdueDate;
	}
	public void setOverdueDate(Date overdueDate) {
		this.overdueDate = overdueDate;
	}
	public Integer getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Integer effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getPayChainName() {
		return payChainName;
	}
	public void setPayChainName(String payChainName) {
		this.payChainName = payChainName;
	}	
}
