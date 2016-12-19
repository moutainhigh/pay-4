package com.pay.poss.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ClearingBalanceEntry {
    private Long bentryId;

    private BigDecimal voucherCode;

    private String acctCode;

    private Short crdr;

    private BigDecimal value;

    private String text;

    private String dealid;

    private Long paymentServiceCode;

    private String creationDate;

    private Short status;

    private Long entryCode;

    private String postDate;

    private Short entryType;

    private String currencyCode;

    private Long exchangeRate;

    private String transactionDate;

    private String updateDate;

    private BigDecimal balance;

    private String payDate;

    private Short balanceDirect;

    private Long dealCode;

    private String value1;

    private String value2;

    private String area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getBentryId() {
        return bentryId;
    }

    public void setBentryId(Long bentryId) {
        this.bentryId = bentryId;
    }

    public BigDecimal getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(BigDecimal voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    public Short getCrdr() {
        return crdr;
    }

    public void setCrdr(Short crdr) {
        this.crdr = crdr;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDealid() {
        return dealid;
    }

    public void setDealid(String dealid) {
        this.dealid = dealid;
    }

    public Long getPaymentServiceCode() {
        return paymentServiceCode;
    }

    public void setPaymentServiceCode(Long paymentServiceCode) {
        this.paymentServiceCode = paymentServiceCode;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getEntryCode() {
        return entryCode;
    }

    public void setEntryCode(Long entryCode) {
        this.entryCode = entryCode;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public Short getEntryType() {
        return entryType;
    }

    public void setEntryType(Short entryType) {
        this.entryType = entryType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Long exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Short getBalanceDirect() {
        return balanceDirect;
    }

    public void setBalanceDirect(Short balanceDirect) {
        this.balanceDirect = balanceDirect;
    }

    public Long getDealCode() {
        return dealCode;
    }

    public void setDealCode(Long dealCode) {
        this.dealCode = dealCode;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }
}