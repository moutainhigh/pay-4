package com.pay.channel.model;

import java.math.BigDecimal;
import java.util.Date;

public class MemberSecondLimit {
    private BigDecimal id;

    private BigDecimal partnerId;

    private String cardOrg;

    private BigDecimal limitTimes;

    private BigDecimal limitAmount;

    private BigDecimal limitDay;

    private String switchFlag;

    private Date createDate;

    private String operator;

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

    public BigDecimal getLimitTimes() {
        return limitTimes;
    }

    public void setLimitTimes(BigDecimal limitTimes) {
        this.limitTimes = limitTimes;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public BigDecimal getLimitDay() {
        return limitDay;
    }

    public void setLimitDay(BigDecimal limitDay) {
        this.limitDay = limitDay;
    }

    public String getSwitchFlag() {
        return switchFlag;
    }

    public void setSwitchFlag(String switchFlag) {
        this.switchFlag = switchFlag == null ? null : switchFlag.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }
}