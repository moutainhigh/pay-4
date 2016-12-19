package com.pay.pricingstrategy.service;

import java.util.Date;
/**
 * @计费参数
 * @since 1.0
 */
public class CalPricingStrategyParam {
    private Long transactionAmount;
    private Integer paymentServiceCode;
    private Integer serviceLevelCode;
    private Long memberCode;
    private Date mfDatetime;
    private Integer terminaltype;//终端类型
    private String reservedCode;//扩展代码
    //用来支持扩展的价格策略明细的类型
    public Long getMemberCode() {
        return memberCode;
    }
    public void setMemberCode(Long memberCode) {
        this.memberCode = memberCode;
    }
    public Integer getPaymentServiceCode() {
        return paymentServiceCode;
    }
    public void setPaymentServiceCode(Integer paymentServiceCode) {
        this.paymentServiceCode = paymentServiceCode;
    }
    public Integer getServiceLevelCode() {
        return serviceLevelCode;
    }
    public void setServiceLevelCode(Integer serviceLevelCode) {
        this.serviceLevelCode = serviceLevelCode;
    }
    public Long getTransactionAmount() {
        return transactionAmount;
    }
    public void setTransactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    public Date getMfDatetime() {
        return mfDatetime;
    }
    public void setMfDatetime(Date mfDatetime) {
        this.mfDatetime = mfDatetime;
    }
    public Integer getTerminaltype() {
        return terminaltype;
    }
    public void setTerminaltype(Integer terminaltype) {
        this.terminaltype = terminaltype;
    }
    public String getReservedCode() {
        return reservedCode;
    }
    public void setReservedCode(String reservedCode) {
        this.reservedCode = reservedCode;
    }
	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "    ";
	    
	    StringBuffer retValue = new StringBuffer();
	    
	    retValue.append("CalPricingStrategyParam ( ")
	        .append(super.toString()).append(TAB)
	        .append("transactionAmount = ").append(this.transactionAmount).append(TAB)
	        .append("paymentServiceCode = ").append(this.paymentServiceCode).append(TAB)
	        .append("serviceLevelCode = ").append(this.serviceLevelCode).append(TAB)
	        .append("memberCode = ").append(this.memberCode).append(TAB)
	        .append("mfDatetime = ").append(this.mfDatetime).append(TAB)
	        .append("terminaltype = ").append(this.terminaltype).append(TAB)
	        .append("reservedCode = ").append(this.reservedCode).append(TAB)
	        .append(" )");
	    
	    return retValue.toString();
	}
}
