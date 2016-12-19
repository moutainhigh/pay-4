package com.pay.txncore.model;

import java.util.Date;

/**
 * Created by cuber on 2016/8/31.
 */
public class PreController implements java.io.Serializable {

    private long id;
    private String orderId;
    private long memberCode;
    private long tradeOrderNo;
    private long paymentOrderNo;
    private long channelOrderNo;
    private String channelResponseNo;
    private Date createDate;
    private String orgCode;
    private String exchangeRate;
    private long preAuthorAmount;
    private String preCurrencyCode;
    private long actAuthorAmount;
    private String actCurrencyCode;
    private long captureAmountTotal;
    private long actCapAmountTotal;
    private String authorStatus;
    private String captureStatus;
    private String authorVoidStatus;
    private long paymentChannelItemId;
    private long channelConfigId;
    private Date lastestUpdateDate;
    private String lastestUpdateType;
    private long lastestUpdateNo;

    private String cardOrg;
    private String mcc;
    private String terminalCode;
    private long memberCurrentId;

    public String getCardOrg() {
        return cardOrg;
    }

    public void setCardOrg(String cardOrg) {
        this.cardOrg = cardOrg;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public long getMemberCurrentId() {
        return memberCurrentId;
    }

    public void setMemberCurrentId(long memberCurrentId) {
        this.memberCurrentId = memberCurrentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(long memberCode) {
        this.memberCode = memberCode;
    }

    public long getTradeOrderNo() {
        return tradeOrderNo;
    }

    public void setTradeOrderNo(long tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    public long getPaymentOrderNo() {
        return paymentOrderNo;
    }

    public void setPaymentOrderNo(long paymentOrderNo) {
        this.paymentOrderNo = paymentOrderNo;
    }

    public long getChannelOrderNo() {
        return channelOrderNo;
    }

    public void setChannelOrderNo(long channelOrderNo) {
        this.channelOrderNo = channelOrderNo;
    }

    public String getChannelResponseNo() {
        return channelResponseNo;
    }

    public void setChannelResponseNo(String channelResponseNo) {
        this.channelResponseNo = channelResponseNo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public long getPreAuthorAmount() {
        return preAuthorAmount;
    }

    public void setPreAuthorAmount(long preAuthorAmount) {
        this.preAuthorAmount = preAuthorAmount;
    }

    public String getPreCurrencyCode() {
        return preCurrencyCode;
    }

    public void setPreCurrencyCode(String preCurrencyCode) {
        this.preCurrencyCode = preCurrencyCode;
    }

    public long getActAuthorAmount() {
        return actAuthorAmount;
    }

    public void setActAuthorAmount(long actAuthorAmount) {
        this.actAuthorAmount = actAuthorAmount;
    }

    public String getActCurrencyCode() {
        return actCurrencyCode;
    }

    public void setActCurrencyCode(String actCurrencyCode) {
        this.actCurrencyCode = actCurrencyCode;
    }

    public long getCaptureAmountTotal() {
        return captureAmountTotal;
    }

    public void setCaptureAmountTotal(long captureAmountTotal) {
        this.captureAmountTotal = captureAmountTotal;
    }

    public long getActCapAmountTotal() {
        return actCapAmountTotal;
    }

    public void setActCapAmountTotal(long actCapAmountTotal) {
        this.actCapAmountTotal = actCapAmountTotal;
    }

    public String getAuthorStatus() {
        return authorStatus;
    }

    public void setAuthorStatus(String authorStatus) {
        this.authorStatus = authorStatus;
    }

    public String getCaptureStatus() {
        return captureStatus;
    }

    public void setCaptureStatus(String captureStatus) {
        this.captureStatus = captureStatus;
    }

    public String getAuthorVoidStatus() {
        return authorVoidStatus;
    }

    public void setAuthorVoidStatus(String authorVoidStatus) {
        this.authorVoidStatus = authorVoidStatus;
    }

    public long getPaymentChannelItemId() {
        return paymentChannelItemId;
    }

    public void setPaymentChannelItemId(long paymentChannelItemId) {
        this.paymentChannelItemId = paymentChannelItemId;
    }

    public long getChannelConfigId() {
        return channelConfigId;
    }

    public void setChannelConfigId(long channelConfigId) {
        this.channelConfigId = channelConfigId;
    }

    public Date getLastestUpdateDate() {
        return lastestUpdateDate;
    }

    public void setLastestUpdateDate(Date lastestUpdateDate) {
        this.lastestUpdateDate = lastestUpdateDate;
    }

    public String getLastestUpdateType() {
        return lastestUpdateType;
    }

    public void setLastestUpdateType(String lastestUpdateType) {
        this.lastestUpdateType = lastestUpdateType;
    }

    public long getLastestUpdateNo() {
        return lastestUpdateNo;
    }

    public void setLastestUpdateNo(long lastestUpdateNo) {
        this.lastestUpdateNo = lastestUpdateNo;
    }
}
