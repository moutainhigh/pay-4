package com.pay.poss.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by songyilin on 2016/10/12 0012.
 */
public class RiskMerchantTrade {

    private String partnerId;


    private BigDecimal orderSucceedAmount;

    private String orderSucceedCount;

    private BigDecimal refundAmount;

    private String refundCount;


    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public BigDecimal getOrderSucceedAmount() {
        return orderSucceedAmount;
    }

    public void setOrderSucceedAmount(BigDecimal orderSucceedAmount) {
        this.orderSucceedAmount = orderSucceedAmount;
    }

    public String getOrderSucceedCount() {
        return orderSucceedCount;
    }

    public void setOrderSucceedCount(String orderSucceedCount) {
        this.orderSucceedCount = orderSucceedCount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(String refundCount) {
        this.refundCount = refundCount;
    }
}
