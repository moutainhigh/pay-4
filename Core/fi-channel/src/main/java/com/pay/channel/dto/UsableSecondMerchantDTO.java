package com.pay.channel.dto;

import com.pay.channel.model.UsableSecondMerchant;

/**
 * Created by cuber.huang on 2016/8/12.
 */
public class UsableSecondMerchantDTO extends UsableSecondMerchant {
    private String paymentChannelName;//通道名称

    private String orgMerchantCode;//二级通道号名称

    private String orgCode;

    public String getPaymentChannelName() {
        return paymentChannelName;
    }

    public void setPaymentChannelName(String paymentChannelName) {
        this.paymentChannelName = paymentChannelName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgMerchantCode() {
        return orgMerchantCode;
    }

    public void setOrgMerchantCode(String orgMerchantCode) {
        this.orgMerchantCode = orgMerchantCode;
    }
}
