package com.pay.txncore.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by cuber.huang on 2016/7/27.
 */
public class AfterChannel2CashierDTO {
    /**渠道订单ID*/
    private String channelOrderNo;
    /**商户原始送过来的订单金额*/
    private String orderAmountOrigin;
    /**商户原始送过来的交易比重*/
    private String currencyCodeOrigin;
    /**商户返回的URL*/
    private String returnUrl;
    /**商户的订单ID*/
    private String orderId;
    /**送给渠道的金额*/
    private String orderAmount;
    /**送给渠道的交易币种*/
    private String currencyCode;

    private String tradeOrderNo;

    private String charset;

    private String signType;

    private String settlementCurrencyCode;

    private String acquiringTime;

    private String remark;

    private String dealId;

    private String partnerId;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSettlementCurrencyCode() {
        return settlementCurrencyCode;
    }

    public void setSettlementCurrencyCode(String settlementCurrencyCode) {
        this.settlementCurrencyCode = settlementCurrencyCode;
    }

    public String getAcquiringTime() {
        return acquiringTime;
    }

    public void setAcquiringTime(String acquiringTime) {
        this.acquiringTime = acquiringTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTradeOrderNo() {
        return tradeOrderNo;
    }

    public void setTradeOrderNo(String tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    public String getChannelOrderNo() {
        return channelOrderNo;
    }

    public void setChannelOrderNo(String channelOrderNo) {
        this.channelOrderNo = channelOrderNo;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getOrderAmountOrigin() {
        return orderAmountOrigin;
    }

    public void setOrderAmountOrigin(String orderAmountOrigin) {
        this.orderAmountOrigin = orderAmountOrigin;
    }

    public String getCurrencyCodeOrigin() {
        return currencyCodeOrigin;
    }

    public void setCurrencyCodeOrigin(String currencyCodeOrigin) {
        this.currencyCodeOrigin = currencyCodeOrigin;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
