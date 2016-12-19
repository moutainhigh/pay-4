package com.pay.poss.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by songyilin on 2016/10/11 0011.
 */
public class ClearingRefundOrder {


    private String refundOrder; //退款交易号

    private String tradeOrderNo;    //网关订单号

    private String paymentOrderNo;  //支付订单号

    private String partnerId; //商户会员号

    private String orderId;  //商户订单号

    private BigDecimal refundAmount;    //退款金额

    private String currencyCode; //交易币种

    private String refundType; //退款类型

    private String refundStatus;    //状态

    private String reconciliationFlg; //是否对账

    private String createDate; //创建时间

    private String orgCode; //渠道号

    private BigDecimal payRate; //支付汇率

    private String transferCurrencyCode; //支付币种

    private String settlementCurrencyCode; //清算币种

    private BigDecimal settlementRate; //清算汇率

    private String merchantNo;  //渠道二级号

    private BigDecimal amount; //交易金额

    private BigDecimal payAmount; //支付金额

    private String area;    //地区


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
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

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getReconciliationFlg() {
        return reconciliationFlg;
    }

    public void setReconciliationFlg(String reconciliationFlg) {
        this.reconciliationFlg = reconciliationFlg;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public BigDecimal getPayRate() {
        return payRate;
    }

    public void setPayRate(BigDecimal payRate) {
        this.payRate = payRate;
    }

    public String getTransferCurrencyCode() {
        return transferCurrencyCode;
    }

    public void setTransferCurrencyCode(String transferCurrencyCode) {
        this.transferCurrencyCode = transferCurrencyCode;
    }

    public String getSettlementCurrencyCode() {
        return settlementCurrencyCode;
    }

    public void setSettlementCurrencyCode(String settlementCurrencyCode) {
        this.settlementCurrencyCode = settlementCurrencyCode;
    }

    public BigDecimal getSettlementRate() {
        return settlementRate;
    }

    public void setSettlementRate(BigDecimal settlementRate) {
        this.settlementRate = settlementRate;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
}
