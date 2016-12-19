package com.pay.api.model;

import java.util.Date;

public class MerchantConfigure{
    /**
     */
    private Integer id;

    /**
     */
    private String merchantCode;

    /**
     */
    private String authorizeAddress;

    /**
     */
    private String notifyUrl;

    /**
     */
    private String publicKey;

    /**
     */
    private String creator;

    /**
     */
    private Date createDate;

    /**
     */
    private Integer notifyFlag;

    /**
     */
    private Date updateDate;

    /**
     */
    private String updator;

    /**
     */
    private Integer status;

    /**
     *
     * @return the value of MERCHANT_CONFIGURE.ID
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return the value of MERCHANT_CONFIGURE.MERCHANT_CODE
     */
    public String getMerchantCode() {
        return merchantCode;
    }

    /**
     *
     */
    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    /**
     *
     * @return the value of MERCHANT_CONFIGURE.AUTHORIZE_ADDRESS
     */
    public String getAuthorizeAddress() {
        return authorizeAddress;
    }

    /**
     *
     */
    public void setAuthorizeAddress(String authorizeAddress) {
        this.authorizeAddress = authorizeAddress;
    }

    /**
     *
     * @return the value of MERCHANT_CONFIGURE.NOTIFY_URL
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     *
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     *
     * @return the value of MERCHANT_CONFIGURE.PUBLIC_KEY
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     *
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     *
     * @return the value of MERCHANT_CONFIGURE.CREATOR
     */
    public String getCreator() {
        return creator;
    }

    /**
     *
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     *
     * @return the value of MERCHANT_CONFIGURE.CREATE_DATE
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
     * @return the value of MERCHANT_CONFIGURE.NOTIFY_FLAG
     */
    public Integer getNotifyFlag() {
        return notifyFlag;
    }

    /**
     *
     */
    public void setNotifyFlag(Integer notifyFlag) {
        this.notifyFlag = notifyFlag;
    }

    /**
     *
     * @return the value of MERCHANT_CONFIGURE.UPDATE_DATE
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
     * @return the value of MERCHANT_CONFIGURE.UPDATOR
     */
    public String getUpdator() {
        return updator;
    }

    /**
     *
     */
    public void setUpdator(String updator) {
        this.updator = updator;
    }

    /**
     *
     * @return the value of MERCHANT_CONFIGURE.STATUS
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}