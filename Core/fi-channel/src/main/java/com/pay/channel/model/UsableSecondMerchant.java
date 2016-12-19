package com.pay.channel.model;

import java.math.BigDecimal;
import java.util.Date;

public class UsableSecondMerchant {
    private BigDecimal id;

    private BigDecimal paymentChannelItemId;

    private BigDecimal channelConfigId;

    private String cardOrg;

    private String useReference;

    private Date joinFreeTime;

    private Date deleteDate;

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

    public String getUseReference() {
        return useReference;
    }

    public void setUseReference(String useReference) {
        this.useReference = useReference;
    }

    public Date getJoinFreeTime() {
        return joinFreeTime;
    }

    public void setJoinFreeTime(Date joinFreeTime) {
        this.joinFreeTime = joinFreeTime;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }
}