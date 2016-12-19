package com.pay.fo.bankcorp.model;

import java.util.Date;

public class BankChannelOrder {
    /**
     */
    private Long orderId;

    /**
     */
    private Integer orderStatus;

    /**
     */
    private Date createDate;

    /**
     */
    private Date updateDate;

    /**
     */
    private Long tradeOrderId;

    /**
     */
    private Date tradeDate;

    /**
     */
    private Integer tradeOrderType;

    /**
     */
    private String tradeOrderSmallType;

    /**
     */
    private String payeeBankAcc;

    /**
     */
    private String payeeName;

    /**
     */
    private Long amount;

    /**
     */
    private String channelCode;

    /**
     */
    private String fundoutBankCode;

    /**
     */
    private String customSequenceId;

    /**
     */
    private String bankSequenceId;

    /**
     */
    private String failedReason;

    /**
     */
    private String payerBankAcc;

    /**
     */
    private String payerBankAccName;

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.ORDER_ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     *
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.ORDER_STATUS
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     *
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.CREATE_DATE
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     *
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.UPDATE_DATE
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     *
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.TRADE_ORDER_ID
     */
    public Long getTradeOrderId() {
        return tradeOrderId;
    }

    /**
     *
     */
    public void setTradeOrderId(Long tradeOrderId) {
        this.tradeOrderId = tradeOrderId;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.TRADE_DATE
     */
    public Date getTradeDate() {
        return tradeDate;
    }

    /**
     *
     */
    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.TRADE_ORDER_TYPE
     */
    public Integer getTradeOrderType() {
        return tradeOrderType;
    }

    /**
     *
     */
    public void setTradeOrderType(Integer tradeOrderType) {
        this.tradeOrderType = tradeOrderType;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.TRADE_ORDER_SMALL_TYPE
     */
    public String getTradeOrderSmallType() {
        return tradeOrderSmallType;
    }

    /**
     *
     */
    public void setTradeOrderSmallType(String tradeOrderSmallType) {
        this.tradeOrderSmallType = tradeOrderSmallType;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.PAYEE_BANK_ACC
     */
    public String getPayeeBankAcc() {
        return payeeBankAcc;
    }

    /**
     *
     */
    public void setPayeeBankAcc(String payeeBankAcc) {
        this.payeeBankAcc = payeeBankAcc;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.PAYEE_NAME
     */
    public String getPayeeName() {
        return payeeName;
    }

    /**
     *
     */
    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.AMOUNT
     */
    public Long getAmount() {
        return amount;
    }

    /**
     *
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.CHANNEL_CODE
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     *
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.FUNDOUT_BANK_CODE
     */
    public String getFundoutBankCode() {
        return fundoutBankCode;
    }

    /**
     *
     */
    public void setFundoutBankCode(String fundoutBankCode) {
        this.fundoutBankCode = fundoutBankCode;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.CUSTOM_SEQUENCE_ID
     */
    public String getCustomSequenceId() {
        return customSequenceId;
    }

    /**
     *
     */
    public void setCustomSequenceId(String customSequenceId) {
        this.customSequenceId = customSequenceId;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.BANK_SEQUENCE_ID
     */
    public String getBankSequenceId() {
        return bankSequenceId;
    }

    /**
     *
     */
    public void setBankSequenceId(String bankSequenceId) {
        this.bankSequenceId = bankSequenceId;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.FAILED_REASON
     */
    public String getFailedReason() {
        return failedReason;
    }

    /**
     *
     */
    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.PAYER_BANK_ACC
     */
    public String getPayerBankAcc() {
        return payerBankAcc;
    }

    /**
     *
     */
    public void setPayerBankAcc(String payerBankAcc) {
        this.payerBankAcc = payerBankAcc;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_ORDER.PAYER_BANK_ACC_NAME
     */
    public String getPayerBankAccName() {
        return payerBankAccName;
    }

    /**
     *
     */
    public void setPayerBankAccName(String payerBankAccName) {
        this.payerBankAccName = payerBankAccName;
    }
}