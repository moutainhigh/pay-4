package com.pay.recurring.model;

import java.util.Date;

public class RecurringQue {
	/**会员号**/
	private Long memberCode;
	/**商户订单号**/
	private String orderId;
	/**付款时间**/
	private Date paymentDate;
	/**币种**/
	private String currencyCode;
	/**金额**/
	private Long amount;
	/**失败笔数**/
	private Integer failedTimes;
	/***失败后重扣周期**/
	private Integer failedRetryDays;
	/**报文**/
	private String strContent;
	/**本期期号**/
	private Integer issueno;
	/**创建日期**/
	private Date createDate;
	/**更新时间**/
	private Date  updDate;
	/**客户接收异步消息接口**/
	private String custInterface;
	
	public String getCustInterface() {
		return custInterface;
	}

	public void setCustInterface(String custInterface) {
		this.custInterface = custInterface;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Integer getFailedTimes() {
		return failedTimes;
	}

	public void setFailedTimes(Integer failedTimes) {
		this.failedTimes = failedTimes;
	}

	public Integer getFailedRetryDays() {
		return failedRetryDays;
	}

	public void setFailedRetryDays(Integer failedRetryDays) {
		this.failedRetryDays = failedRetryDays;
	}

	public String getStrContent() {
		return strContent;
	}

	public void setStrContent(String strContent) {
		this.strContent = strContent;
	}

	public void setIssueno(Integer issueno) {
		this.issueno = issueno;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public Integer getIssueno() {
		return issueno;
	}
}
