package com.pay.poss.param;

/**
 * Created by songyilin on 2016/10/11 0011.
 */
public class SettlementTradeParam extends BaseTradeParam {

    private String type;

    private Integer settlementStatus;

    private Integer refundStatus;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(Integer settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }
}
