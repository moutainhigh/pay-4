package com.pay.poss.domain;

import java.math.BigDecimal;

/**
 * Created by songyilin on 2016/10/14 0014.
 */
public class RiskBankOrder {

    private String returnNo;

    private String partnerId;

    private String orderId;

    private String tradeOrderNo;

    private BigDecimal orderAmount;

    private String currencyCode;

    private String settlementRate;

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTradeOrderNo() {
        return tradeOrderNo;
    }

    public void setTradeOrderNo(String tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getSettlementRate() {
        return settlementRate;
    }

    public void setSettlementRate(String settlementRate) {
        this.settlementRate = settlementRate;
    }
}
