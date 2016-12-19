package com.pay.txncore.model;

import java.util.Date;

/**
 * Created by cuber.huang on 2016/7/16.
 */
public class PartnerChannelCountry {
    private Long id;
    private Long memCode;
    private String orgCode;
    private String countryCode;
    private int priority;
    private Date createDate;
    private Date updateDate;
    private String operator;

    public Long getMemCode() {
        return memCode;
    }

    public void setMemCode(Long memCode) {
        this.memCode = memCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
