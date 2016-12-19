package com.pay.channel.redis.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Created by eva on 2016/8/15.
 */
public class ChannelObj {
    //1.交易,2.增加二级通道号,3.删除2级通道号
    private String dealType;
    //子类型
    private String subDealType;

    private BigDecimal channelConfigId;

    private BigDecimal partnerId;

    private BigDecimal paymentChannelItemId;

    private BigDecimal amount;//成交金额

    private String happenTimeStamp;

    private BigDecimal id;

    private BigDecimal channelSecondRelationId;

    private String operator;

    private BigDecimal memmberCurrentConnectId;

    private String failure;

    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }

    public BigDecimal getMemmberCurrentConnectId() {
        return memmberCurrentConnectId;
    }

    public void setMemmberCurrentConnectId(BigDecimal memmberCurrentConnectId) {
        this.memmberCurrentConnectId = memmberCurrentConnectId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public BigDecimal getChannelSecondRelationId() {
        return channelSecondRelationId;
    }

    public void setChannelSecondRelationId(BigDecimal channelSecondRelationId) {
        this.channelSecondRelationId = channelSecondRelationId;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getSubDealType() {
        return subDealType;
    }

    public void setSubDealType(String subDealType) {
        this.subDealType = subDealType;
    }

    public BigDecimal getChannelConfigId() {
        return channelConfigId;
    }

    public void setChannelConfigId(BigDecimal channelConfigId) {
        this.channelConfigId = channelConfigId;
    }

    public BigDecimal getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(BigDecimal partnerId) {
        this.partnerId = partnerId;
    }

    public BigDecimal getPaymentChannelItemId() {
        return paymentChannelItemId;
    }

    public void setPaymentChannelItemId(BigDecimal paymentChannelItemId) {
        this.paymentChannelItemId = paymentChannelItemId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getHappenTimeStamp() {
        return happenTimeStamp;
    }

    public void setHappenTimeStamp(String happenTimeStamp) {
        this.happenTimeStamp = happenTimeStamp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
