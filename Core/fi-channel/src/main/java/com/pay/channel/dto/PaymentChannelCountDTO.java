package com.pay.channel.dto;

import com.pay.channel.model.PaymentChannelCount;

/**
 * Created by cuber.huang on 2016/8/12.
 */
public class PaymentChannelCountDTO  extends PaymentChannelCount {

    private String paymentChannelName;

    private String orgCode;

    private String startMonth;

    private String endMonth;

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
}
