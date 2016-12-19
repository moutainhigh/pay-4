package com.pay.poss.domain;

import java.math.BigDecimal;

/**
 * Created by songyilin on 2016/10/14 0014.
 */
public class ClearingMigsRefundOrder {

    private String refundOrder;

    private String tradeOrderNo;

    private String paymentOrderNo;

    private String partnerId;

    private String orderId;

    private String transferRate;

    private String channelOrderNo;

    private BigDecimal preAmount;

    private BigDecimal refundAmount;

    private String currencyCode;

    private String refundType;

    private String reconciliationFlg;

    private String refundStatus;

    private String authorisation;

    private String channelReturnNo;

    private String createDate;

    private String completeDate;

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public BigDecimal getPreAmount() {
        return preAmount;
    }

    public void setPreAmount(BigDecimal preAmount) {
        this.preAmount = preAmount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundOrder() {
        return refundOrder;
    }

    public void setRefundOrder(String refundOrder) {
        this.refundOrder = refundOrder;
    }

    public String getTradeOrderNo() {
        return tradeOrderNo;
    }

    public void setTradeOrderNo(String tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    public String getPaymentOrderNo() {
        return paymentOrderNo;
    }

    public void setPaymentOrderNo(String paymentOrderNo) {
        this.paymentOrderNo = paymentOrderNo;
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

    public String getTransferRate() {
        return transferRate;
    }

    public void setTransferRate(String transferRate) {
        this.transferRate = transferRate;
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

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getReconciliationFlg() {
        return reconciliationFlg;
    }

    public void setReconciliationFlg(String reconciliationFlg) {
        this.reconciliationFlg = reconciliationFlg;
    }

    public String getAuthorisation() {
        return authorisation;
    }

    public void setAuthorisation(String authorisation) {
        this.authorisation = authorisation;
    }

    public String getChannelReturnNo() {
        return channelReturnNo;
    }

    public void setChannelReturnNo(String channelReturnNo) {
        this.channelReturnNo = channelReturnNo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }
}
