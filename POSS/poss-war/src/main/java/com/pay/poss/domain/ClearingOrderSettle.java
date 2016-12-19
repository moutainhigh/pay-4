package com.pay.poss.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by songyilin on 2016/10/11 0011.
 */
public class ClearingOrderSettle {

    private String partnerId; //商户号

    private String orderId; //商户订单号

    private String tradeOrderNo;  //网关订单号

    private String paymentOrderNo;  //支付订单号

    private String channelOrderNo;   //渠道订单号

    private String orgCode; //渠道号

    private BigDecimal amount; //交易金额

    private String currencyCode; //交易币种

    private String transferRate; //交易汇率

    private String channelStatus;  //渠道结果

    private String gatewayStatus; //网关结果

    private String tradeDate; //交易时间

    private String area;        //地区

    private BigDecimal payAmount;  //支付金额

    private String transferCurrencyCode; //支付币种

    private String settlementCurrencyCode; //清算币种

    private BigDecimal settlementRate; //清算汇率

    private BigDecimal feeRate; //手续费汇率

    private String merchantNo;  //渠道二级号

    private String authorisation; //授权码

    private String settlementDate; //清算日期

    private String settlementCreatedate; //清算创建时间

    private String settlementFlg; //清算标志

    private String cardType; //卡种

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getPaymentOrderNo() {
        return paymentOrderNo;
    }

    public void setPaymentOrderNo(String paymentOrderNo) {
        this.paymentOrderNo = paymentOrderNo;
    }

    public String getChannelOrderNo() {
        return channelOrderNo;
    }

    public void setChannelOrderNo(String channelOrderNo) {
        this.channelOrderNo = channelOrderNo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTransferRate() {
        return transferRate;
    }

    public void setTransferRate(String transferRate) {
        this.transferRate = transferRate;
    }

    public String getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(String channelStatus) {
        this.channelStatus = channelStatus;
    }

    public String getGatewayStatus() {
        return gatewayStatus;
    }

    public void setGatewayStatus(String gatewayStatus) {
        this.gatewayStatus = gatewayStatus;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
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

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getAuthorisation() {
        return authorisation;
    }

    public void setAuthorisation(String authorisation) {
        this.authorisation = authorisation;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getSettlementCreatedate() {
        return settlementCreatedate;
    }

    public void setSettlementCreatedate(String settlementCreatedate) {
        this.settlementCreatedate = settlementCreatedate;
    }

    public String getSettlementFlg() {
        return settlementFlg;
    }

    public void setSettlementFlg(String settlementFlg) {
        this.settlementFlg = settlementFlg;
    }
}
