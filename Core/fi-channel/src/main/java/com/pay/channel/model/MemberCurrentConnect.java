package com.pay.channel.model;

import java.math.BigDecimal;
import java.util.Date;

public class MemberCurrentConnect {
    private BigDecimal id;

    private BigDecimal partnerId;

    private String cardOrg;

    private BigDecimal paymentChannelItemId;

    private BigDecimal channelConfigId;

    private BigDecimal countTimes;

    private BigDecimal countAmount;

    private Long rejectTimes;

    private Date connectTime;

    private String hasWarning;

    private String manual;

    private BigDecimal channelSecondRelationId;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(BigDecimal partnerId) {
        this.partnerId = partnerId;
    }

    public String getCardOrg() {
        return cardOrg;
    }

    public void setCardOrg(String cardOrg) {
        this.cardOrg = cardOrg == null ? null : cardOrg.trim();
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

    public BigDecimal getCountTimes() {
        return countTimes;
    }

    public void setCountTimes(BigDecimal countTimes) {
        this.countTimes = countTimes;
    }

    public BigDecimal getCountAmount() {
        return countAmount;
    }

    public void setCountAmount(BigDecimal countAmount) {
        this.countAmount = countAmount;
    }

    public Long getRejectTimes() {
        return rejectTimes;
    }

    public void setRejectTimes(Long rejectTimes) {
        this.rejectTimes = rejectTimes;
    }

    public Date getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(Date connectTime) {
        this.connectTime = connectTime;
    }

    public String getHasWarning() {
        return hasWarning;
    }

    public void setHasWarning(String hasWarning) {
        this.hasWarning = hasWarning;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual == null ? null : manual.trim();
    }

    public BigDecimal getChannelSecondRelationId() {
        return channelSecondRelationId;
    }

    public void setChannelSecondRelationId(BigDecimal channelSecondRelationId) {
        this.channelSecondRelationId = channelSecondRelationId;
    }
}