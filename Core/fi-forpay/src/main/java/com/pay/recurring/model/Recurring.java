package com.pay.recurring.model;

import java.util.Date;

public class Recurring {
	/**商户会员号**/
	private Long memberCode;
	/**商户订单**/
	private String orderId;
	/**扣款频率(Y/M)**/
	private String frequency;
	/**扣款币种**/
	private String currencyCode;
	/**扣款金额**/
	private Long amount;
	/**剩余期数**/
	private Integer period;
	/**新更改订单号**/
	private String newOrderId;
	/**创建日期**/
	private Date createDate;
	/**最后扣款日期**/
	private Date lastestPaymentDate;
	/**下一扣款日期**/
	private Date nextPaymentDate;
	/**单次扣款允许的最大失败次数**/
	private Integer maxFailedTimes;
	/**失败重扣周期**/
	private Integer  failedRetryDays;
	/**支付日期***/
	private Integer paymentDay;
	/**生效/失效**/
	private String effFlag;
	/**更新日期**/
	private Date updDate;
	/**下单报文**/
	private String strContent;
	/**客户接收异步消息接口**/
	private String custInterface;
	
	private String lineComment;
	/**下期期号***/
	private String issueno;
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
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
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
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public String getNewOrderId() {
		return newOrderId;
	}
	public void setNewOrderId(String newOrderId) {
		this.newOrderId = newOrderId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastestPaymentDate() {
		return lastestPaymentDate;
	}
	public void setLastestPaymentDate(Date lastestPaymentDate) {
		this.lastestPaymentDate = lastestPaymentDate;
	}
	public Date getNextPaymentDate() {
		return nextPaymentDate;
	}
	public void setNextPaymentDate(Date nextPaymentDate) {
		this.nextPaymentDate = nextPaymentDate;
	}
	public Integer getMaxFailedTimes() {
		return maxFailedTimes;
	}
	public void setMaxFailedTimes(Integer maxFailedTimes) {
		this.maxFailedTimes = maxFailedTimes;
	}
	public Integer getPaymentDay() {
		return paymentDay;
	}
	public void setPaymentDay(Integer paymentDay) {
		this.paymentDay = paymentDay;
	}
	public String getEffFlag() {
		return effFlag;
	}
	public void setEffFlag(String effFlag) {
		this.effFlag = effFlag;
	}
	public Date getUpdDate() {
		return updDate;
	}
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
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
	public String getLineComment() {
		return lineComment;
	}
	public void setLineComment(String lineComment) {
		this.lineComment = lineComment;
	}
	public String getIssueno() {
		return issueno;
	}
	public void setIssueno(String issueno) {
		this.issueno = issueno;
	}
	public String getCustInterface() {
		return custInterface;
	}
	public void setCustInterface(String custInterface) {
		this.custInterface = custInterface;
	}
	
}
