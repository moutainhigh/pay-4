package com.pay.fo.bankcorp.model;

import java.util.Date;

public class ChannelCommunicationLog {
    /**
     */
    private Long sequenceId;

    /**
     */
    private Long channelOrderId;

    /**
     */
    private String channelCode;

    /**
     */
    private String fundoutBankCode;

    /**
     */
    private String bankTransCode;

    /**
     */
    private Date createDate;

    /**
     */
    private Integer type;

    /**
     */
    private String content;

    /**
     *
     * @return the value of CHANNEL_COMMUNICATION_LOG.SEQUENCE_ID
     */
    public Long getSequenceId() {
        return sequenceId;
    }

    /**
     *
     */
    public void setSequenceId(Long sequenceId) {
        this.sequenceId = sequenceId;
    }

    /**
     *
     * @return the value of CHANNEL_COMMUNICATION_LOG.CHANNEL_ORDER_ID
     */
    public Long getChannelOrderId() {
        return channelOrderId;
    }

    /**
     *
     */
    public void setChannelOrderId(Long channelOrderId) {
        this.channelOrderId = channelOrderId;
    }

    /**
     *
     * @return the value of CHANNEL_COMMUNICATION_LOG.CHANNEL_CODE
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
     * @return the value of CHANNEL_COMMUNICATION_LOG.FUNDOUT_BANK_CODE
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
     * @return the value of CHANNEL_COMMUNICATION_LOG.BANK_TRANS_CODE
     */
    public String getBankTransCode() {
        return bankTransCode;
    }

    /**
     *
     */
    public void setBankTransCode(String bankTransCode) {
        this.bankTransCode = bankTransCode;
    }

    /**
     *
     * @return the value of CHANNEL_COMMUNICATION_LOG.CREATE_DATE
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
     * @return the value of CHANNEL_COMMUNICATION_LOG.TYPE
     */
    public Integer getType() {
        return type;
    }

    /**
     *
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     *
     * @return the value of CHANNEL_COMMUNICATION_LOG.CONTENT
     */
    public String getContent() {
        return content;
    }

    /**
     *
     */
    public void setContent(String content) {
        this.content = content;
    }
}