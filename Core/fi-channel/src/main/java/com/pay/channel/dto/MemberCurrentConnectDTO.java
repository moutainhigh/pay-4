package com.pay.channel.dto;

import com.pay.channel.model.MemberCurrentConnect;

/**
 * Created by cuber.huang on 2016/8/12.
 */
public class MemberCurrentConnectDTO extends MemberCurrentConnect {

    private String paymentChannelName;//通道名称

    private String orgMerchantCode;//二级通道号名称

    private String orgCode;

    private int days;

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
