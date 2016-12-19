package com.pay.channel.model;

import java.math.BigDecimal;

public class SecondMerchantCount {
    private BigDecimal id;

    private BigDecimal paymentChannelItemId;

    private BigDecimal channelConfigId;

    private String cardOrg;

    private BigDecimal successTimes;

    private BigDecimal failureTimes;

    private BigDecimal rejectTimes;

    private BigDecimal amount;

    private String monthStamp;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getPaymentChannelItemId() {
        return paymentChannelItemId;
    }

    public void setPaymentChannelItemId(BigDecimal paymentChannelItemId) {
        this.paymentChannelItemId = paymentChannelItemId;
    }

    public BigDecimal getChannelConfigId() {
        return channelConfigId;
    }

    public void setChannelConfigId(BigDecimal channelConfigId) {
        this.channelConfigId = channelConfigId;
    }

    public String getCardOrg() {
        return cardOrg;
    }

    public void setCardOrg(String cardOrg) {
        this.cardOrg = cardOrg == null ? null : cardOrg.trim();
    }

    public BigDecimal getSuccessTimes() {
        return successTimes;
    }

    public void setSuccessTimes(BigDecimal successTimes) {
        this.successTimes = successTimes;
    }

    public BigDecimal getFailureTimes() {
        return failureTimes;
    }

    public void setFailureTimes(BigDecimal failureTimes) {
        this.failureTimes = failureTimes;
    }

    public BigDecimal getRejectTimes() {
        return rejectTimes;
    }

    public void setRejectTimes(BigDecimal rejectTimes) {
        this.rejectTimes = rejectTimes;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMonthStamp() {
        return monthStamp;
    }

    public void setMonthStamp(String monthStamp) {
        this.monthStamp = monthStamp;
    }
}