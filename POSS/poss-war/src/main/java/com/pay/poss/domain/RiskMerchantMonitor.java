package com.pay.poss.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by songyilin on 2016/10/12 0012.
 */
public class RiskMerchantMonitor {

    private String partnerIdTwo;

    private String gatewayDate;

    private Integer allCount;

    private Integer succeedCount;

    private BigDecimal succeedRate;

    private Integer riskRefuseCount;

    private BigDecimal riskRefuseRate;

    private Integer notGrantCount;

    private BigDecimal notGrantRate;

    private Integer cheatCount;

    private BigDecimal cheatRate;

    public String getPartnerIdTwo() {
        return partnerIdTwo;
    }

    public void setPartnerIdTwo(String partnerIdTwo) {
        this.partnerIdTwo = partnerIdTwo;
    }

    public String getGatewayDate() {
        return gatewayDate;
    }

    public void setGatewayDate(String gatewayDate) {
        this.gatewayDate = gatewayDate;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public Integer getSucceedCount() {
        return succeedCount;
    }

    public void setSucceedCount(Integer succeedCount) {
        this.succeedCount = succeedCount;
    }

    public BigDecimal getSucceedRate() {
        return succeedRate;
    }

    public void setSucceedRate(BigDecimal succeedRate) {
        this.succeedRate = succeedRate;
    }

    public Integer getRiskRefuseCount() {
        return riskRefuseCount;
    }

    public void setRiskRefuseCount(Integer riskRefuseCount) {
        this.riskRefuseCount = riskRefuseCount;
    }

    public BigDecimal getRiskRefuseRate() {
        return riskRefuseRate;
    }

    public void setRiskRefuseRate(BigDecimal riskRefuseRate) {
        this.riskRefuseRate = riskRefuseRate;
    }

    public Integer getNotGrantCount() {
        return notGrantCount;
    }

    public void setNotGrantCount(Integer notGrantCount) {
        this.notGrantCount = notGrantCount;
    }

    public BigDecimal getNotGrantRate() {
        return notGrantRate;
    }

    public void setNotGrantRate(BigDecimal notGrantRate) {
        this.notGrantRate = notGrantRate;
    }

    public Integer getCheatCount() {
        return cheatCount;
    }

    public void setCheatCount(Integer cheatCount) {
        this.cheatCount = cheatCount;
    }

    public BigDecimal getCheatRate() {
        return cheatRate;
    }

    public void setCheatRate(BigDecimal cheatRate) {
        this.cheatRate = cheatRate;
    }
}
