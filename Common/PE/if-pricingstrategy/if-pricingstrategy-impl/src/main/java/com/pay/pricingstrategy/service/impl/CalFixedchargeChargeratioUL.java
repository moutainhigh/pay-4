package com.pay.pricingstrategy.service.impl;

import org.springframework.util.Assert;

import com.pay.pricingstrategy.helper.PRICESTRATEGYTYPE;
import com.pay.pricingstrategy.service.CalFeeInnerParam;
import com.pay.pricingstrategy.service.CalculateFee;
import com.pay.pricingstrategy.util.PricingStrategyUtil;


/**
 * 
 * @author 
 * 固定费用_费率及上限.
 * 费用 = Math.min(固定费用+费率*交易额,maxFee)
 */
public class CalFixedchargeChargeratioUL implements CalculateFee {
    private final int pricestrategytype = PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDUPPERlIMIT.getValue();
    public int getPricestrategyType() {
        return pricestrategytype;
    }
    /* (non-Javadoc)
     * 
     * 固定费用_费率及上限.
     */
    public long calculateFee(CalFeeInnerParam param) {
        return this.calculateFeeDetail(param).getFee();
    }
    /*
     * (non-Javadoc)
     * 
     */
    public CalPriceFeeResponse calculateFeeDetail(CalFeeInnerParam param) {
        assert param != null;
        assert param.getPricingstategydetaildto() != null;
        assert param.getTransactionAmount() >= 0;
        assert param.getPricingstategydetaildto().getChargeRate() != null;
        assert param.getPricingstategydetaildto().getChargeRate() >= 0;
        assert param.getPricingstategydetaildto().getFixedCharge() != null;
        assert param.getPricingstategydetaildto().getFixedCharge() > 0;
        assert param.getPricingstategydetaildto().getMaxCharge() != null;
        assert param.getPricingstategydetaildto().getMaxCharge() >= 0;
        CalPriceFeeResponse response = new CalPriceFeeResponse();
        response.setFixedFee(param.getPricingstategydetaildto()
                .getFixedCharge());
        response.setMixFee(0L);
        response.setMaxFee(param.getPricingstategydetaildto().getMaxCharge());
        response.setFeeRate(param.getPricingstategydetaildto()
                .getChargeRate());
        long fee = PricingStrategyUtil.formatFee(param.getPricingstategydetaildto()
                .getChargeRate()
                * param.getTransactionAmount())
                + param.getPricingstategydetaildto().getFixedCharge();
        response
                .setFee(fee > param.getPricingstategydetaildto().getMaxCharge() ?
                        param.getPricingstategydetaildto().getMaxCharge() : fee);
        return response;
    }
}
