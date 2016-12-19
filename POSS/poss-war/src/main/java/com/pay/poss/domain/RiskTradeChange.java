package com.pay.poss.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by songyilin on 2016/10/12 0012.
 */
public class RiskTradeChange {

    private String partnerIdTwo;

    private Integer succeedCount;

    private Integer allCount;

    private String succeedRate;

    private BigDecimal succeedAmt;

    private String dataDate;

    public String getPartnerIdTwo() {
        return partnerIdTwo;
    }

    public void setPartnerIdTwo(String partnerIdTwo) {
        this.partnerIdTwo = partnerIdTwo;
    }

    public Integer getSucceedCount() {
        return succeedCount;
    }

    public void setSucceedCount(Integer succeedCount) {
        this.succeedCount = succeedCount;
    }


    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public String getSucceedRate() {
        return succeedRate;
    }

    public void setSucceedRate(String succeedRate) {
        this.succeedRate = succeedRate;
    }

    public BigDecimal getSucceedAmt() {
        return succeedAmt;
    }

    public void setSucceedAmt(BigDecimal succeedAmt) {
        this.succeedAmt = succeedAmt;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }


}
