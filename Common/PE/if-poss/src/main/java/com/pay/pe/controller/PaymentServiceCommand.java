package com.pay.pe.controller;


public final class PaymentServiceCommand {
    
	private static final long serialVersionUID = 3907928555149985176L;
	private String description;
    private Long fixedpayee;
    private Long fixedpayer;
    private String getmethod;
    private String payeeAcctType;
    private String payeetype;
    private String payerAcctType;
    private String payertype;
    private Integer paymentservicecode;
    private String paymentservicename;
    private String oldpaymentservicename;
    private String paymentServiceType;//支付方式
    private String oldpaymentServiceType;
    private String paymethod;
    private String takeon;
    private Integer fixpayer;//1,固定；0，非固定(指定付款方)
    private Integer fixpayee;//1,固定；0，非固定(指定收款方)
    private Integer reservedCodeType;
    
    public Integer getReservedCodeType() {
		return reservedCodeType;
	}
	public void setReservedCodeType(Integer reservedCodeType) {
		this.reservedCodeType = reservedCodeType;
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
    public String getGetmethod() {
        return getmethod;
    }
    public void setGetmethod(String getmethod) {
        this.getmethod = getmethod;
    }
    public String getOldpaymentservicename() {
        return oldpaymentservicename;
    }
    public void setOldpaymentservicename(String oldpaymentservicename) {
        this.oldpaymentservicename = oldpaymentservicename;
    }
    public String getPayeeAcctType() {
        return payeeAcctType;
    }
    public void setPayeeAcctType(String payeeAcctType) {
        this.payeeAcctType = payeeAcctType;
    }
    public String getPayeetype() {
        return payeetype;
    }
    public void setPayeetype(String payeetype) {
        this.payeetype = payeetype;
    }
    public String getPayerAcctType() {
        return payerAcctType;
    }
    public void setPayerAcctType(String payerAcctType) {
        this.payerAcctType = payerAcctType;
    }
    public String getPayertype() {
        return payertype;
    }
    public void setPayertype(String payertype) {
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
    public String getPaymentServiceType() {
        return paymentServiceType;
    }
    public void setPaymentServiceType(String paymentServiceType) {
        this.paymentServiceType = paymentServiceType;
    }
    public String getPaymethod() {
        return paymethod;
    }
    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }
    public String getTakeon() {
        return takeon;
    }
    public void setTakeon(String takeon) {
        this.takeon = takeon;
    }
   
    public Integer getFixpayee() {
        return fixpayee;
    }
    public void setFixpayee(Integer fixpayee) {
        this.fixpayee = fixpayee;
    }
    public Integer getFixpayer() {
        return fixpayer;
    }
    public void setFixpayer(Integer fixpayer) {
        this.fixpayer = fixpayer;
    }
    public String getOldpaymentServiceType() {
        return oldpaymentServiceType;
    }
    public void setOldpaymentServiceType(String oldpaymentServiceType) {
        this.oldpaymentServiceType = oldpaymentServiceType;
    }

}
