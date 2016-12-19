package com.pay.poss.conreg.dto;

import java.util.Date;

public class TConregOrder {
    private Long conregOrderNo;
    private String status;
    private String partnerId;
    private Long conregAmt;
    private String realName;
    private String idNumber;
    private Date createDate;
    private Date updateDate;

    public Long getConregOrderNo() {
        return conregOrderNo;
    }

    public void setConregOrderNo(Long conregOrderNo) {
        this.conregOrderNo = conregOrderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public Long getConregAmt() {
        return conregAmt;
    }

    public void setConregAmt(Long conregAmt) {
        this.conregAmt = conregAmt;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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
}