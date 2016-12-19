package com.pay.recurring.model;

import java.util.Date;

public class RecurringResponse {
	/**会员号**/
	private String memberCode;
	/**商户订单号**/
	private String orderId;
	/**付款时间**/
	private String paymentDate;
	/**币种**/
	private String currencyCode;
	/**金额**/
	private String amount;
	/**本期期号**/
	private String issueno;
	
	private String resultCode;
	
	private String resultDesc;
	/**客户接收异步消息接口**/
	private String custInterface;
	
	public String getCustInterface() {
		return custInterface;
	}
	public void setCustInterface(String custInterface) {
		this.custInterface = custInterface;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getIssueno() {
		return issueno;
	}
	public void setIssueno(String issueno) {
		this.issueno = issueno;
	}

}
