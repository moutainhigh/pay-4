package com.pay.channel.dto;

import com.pay.channel.model.SecondMerchantCount;

/**
 * Created by cuber.huang on 2016/8/12.
 */
public class SecondMerchantCountDTO extends SecondMerchantCount {
    private String paymentChannelName;//通道名称

    private String orgMerchantCode;//二级通道号名称

    private String orgCode;

    private String startMonth;

    private String endMonth;

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

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }
}
