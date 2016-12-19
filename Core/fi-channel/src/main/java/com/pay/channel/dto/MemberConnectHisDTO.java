package com.pay.channel.dto;

import com.pay.channel.model.MemberConnectHis;

import java.util.Date;

/**
 * Created by cuber.huang on 2016/8/12.
 */
public class MemberConnectHisDTO extends MemberConnectHis {
    private String paymentChannelName;//通道名称

    private String orgMerchantCode;//二级通道号名称

    private String orgCode;

    private Date startDate;

    private Date endDate;

    private int days;

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getPaymentChannelName() {
        return paymentChannelName;
    }

    public void setPaymentChannelName(String paymentChannelName) {
        this.paymentChannelName = paymentChannelName;
    }

    public String getOrgMerchantCode() {
        return orgMerchantCode;
    }

    public void setOrgMerchantCode(String orgMerchantCode) {
        this.orgMerchantCode = orgMerchantCode;
    }
}
