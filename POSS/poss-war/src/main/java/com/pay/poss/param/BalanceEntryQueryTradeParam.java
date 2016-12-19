package com.pay.poss.param;

/**
 * Created by songyilin on 2016/10/11 0011.
 */
public class BalanceEntryQueryTradeParam extends BaseTradeParam {


    private Short crdr;

    private Long dealCode;



    public Short getCrdr() {
        return crdr;
    }

    public void setCrdr(Short crdr) {
        this.crdr = crdr;
    }

    public Long getDealCode() {
        return dealCode;
    }

    public void setDealCode(Long dealCode) {
        this.dealCode = dealCode;
    }
}
