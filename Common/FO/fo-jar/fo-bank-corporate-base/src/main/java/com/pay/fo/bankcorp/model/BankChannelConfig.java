package com.pay.fo.bankcorp.model;

public class BankChannelConfig {
    /**
     */
    private String channelCode;

    /**
     */
    private String bankName;

    /**
     */
    private String bankAcc;

    /**
     */
    private String bankAccName;

    /**
     */
    private Long minRemindedBalance;

    /**
     */
    private Integer isSupportMultiple;

    /**
     */
    private Long upperLimit;

    /**
     */
    private Long lowerLimit;

    /**
     */
    private Integer maxSupportItems;

    /**
     */
    private String serverAddress;

    /**
     */
    private Integer serverPort;

    /**
     */
    private String macKey;

    /**
     *
     * @return the value of BANK_CHANNEL_CONFIG.CHANNEL_CODE
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
     * @return the value of BANK_CHANNEL_CONFIG.BANK_NAME
     */
    public String getBankName() {
        return bankName;
    }

    /**
     *
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_CONFIG.BANK_ACC
     */
    public String getBankAcc() {
        return bankAcc;
    }

    /**
     *
     */
    public void setBankAcc(String bankAcc) {
        this.bankAcc = bankAcc;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_CONFIG.BANK_ACC_NAME
     */
    public String getBankAccName() {
        return bankAccName;
    }

    /**
     *
     */
    public void setBankAccName(String bankAccName) {
        this.bankAccName = bankAccName;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_CONFIG.MIN_REMINDED_BALANCE
     */
    public Long getMinRemindedBalance() {
        return minRemindedBalance;
    }

    /**
     *
     */
    public void setMinRemindedBalance(Long minRemindedBalance) {
        this.minRemindedBalance = minRemindedBalance;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_CONFIG.IS_SUPPORT_MULTIPLE
     */
    public Integer getIsSupportMultiple() {
        return isSupportMultiple;
    }

    /**
     *
     */
    public void setIsSupportMultiple(Integer isSupportMultiple) {
        this.isSupportMultiple = isSupportMultiple;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_CONFIG.UPPER_LIMIT
     */
    public Long getUpperLimit() {
        return upperLimit;
    }

    /**
     *
     */
    public void setUpperLimit(Long upperLimit) {
        this.upperLimit = upperLimit;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_CONFIG.LOWER_LIMIT
     */
    public Long getLowerLimit() {
        return lowerLimit;
    }

    /**
     *
     */
    public void setLowerLimit(Long lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_CONFIG.MAX_SUPPORT_ITEMS
     */
    public Integer getMaxSupportItems() {
        return maxSupportItems;
    }

    /**
     *
     */
    public void setMaxSupportItems(Integer maxSupportItems) {
        this.maxSupportItems = maxSupportItems;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_CONFIG.SERVER_ADDRESS
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     *
     */
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_CONFIG.SERVER_PORT
     */
    public Integer getServerPort() {
        return serverPort;
    }

    /**
     *
     */
    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    /**
     *
     * @return the value of BANK_CHANNEL_CONFIG.MAC_KEY
     */
    public String getMacKey() {
        return macKey;
    }

    /**
     *
     */
    public void setMacKey(String macKey) {
        this.macKey = macKey;
    }
}