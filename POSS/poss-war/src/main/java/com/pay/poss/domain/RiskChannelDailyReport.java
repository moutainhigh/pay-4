package com.pay.poss.domain;

import java.math.BigDecimal;

/**
 * Created by songyilin on 2016/10/12 0012.
 */
public class RiskChannelDailyReport {

    private String merchantNoTwo;

    private String partnerIdTwo;

    private String tradeDate;



    private BigDecimal vcardRmbAmount;

    private Integer vCardCount;

    private Integer mcardCount;

    private BigDecimal mcardRmbAmount;


    public String getMerchantNoTwo() {
        return merchantNoTwo;
    }

    public void setMerchantNoTwo(String merchantNoTwo) {
        this.merchantNoTwo = merchantNoTwo;
    }

    public String getPartnerIdTwo() {
        return partnerIdTwo;
    }

    public void setPartnerIdTwo(String partnerIdTwo) {
        this.partnerIdTwo = partnerIdTwo;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public BigDecimal getVcardRmbAmount() {
        return vcardRmbAmount;
    }

    public void setVcardRmbAmount(BigDecimal vcardRmbAmount) {
        this.vcardRmbAmount = vcardRmbAmount;
    }

    public Integer getvCardCount() {
        return vCardCount;
    }

    public void setvCardCount(Integer vCardCount) {
        this.vCardCount = vCardCount;
    }

    public Integer getMcardCount() {
        return mcardCount;
    }

    public void setMcardCount(Integer mcardCount) {
        this.mcardCount = mcardCount;
    }

    public BigDecimal getMcardRmbAmount() {
        return mcardRmbAmount;
    }

    public void setMcardRmbAmount(BigDecimal mcardRmbAmount) {
        this.mcardRmbAmount = mcardRmbAmount;
    }
}
