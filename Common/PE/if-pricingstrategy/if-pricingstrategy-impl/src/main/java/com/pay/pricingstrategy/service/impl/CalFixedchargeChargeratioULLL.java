/**
 * 
 */
package com.pay.pricingstrategy.service.impl;

import com.pay.pricingstrategy.helper.PRICESTRATEGYTYPE;
import com.pay.pricingstrategy.service.CalFeeInnerParam;
import com.pay.pricingstrategy.service.CalculateFee;
import com.pay.pricingstrategy.util.PricingStrategyUtil;



/**
 * @author 
 * 费用 = Math.max(Math.min(固定费用+费率*交易额,maxFee),minFee)
 */
public class CalFixedchargeChargeratioULLL implements CalculateFee {
    private final int pricestrategytype = PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDUPPERlIMITANDLOWERLIMIT.getValue();
    /* (non-Javadoc)
     * 
     */
    public int getPricestrategyType() {
        // TODO Auto-generated method stub
        return pricestrategytype;
    }

    /* (non-Javadoc)
     *
     * 固定费率_费率及上下限.
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
//        assert param.getPricingstategydetaildto().getFixedCharge() > 0;
        assert param.getPricingstategydetaildto().getFixedCharge() >= 0;
        assert param.getPricingstategydetaildto().getMaxCharge() != null;
        assert param.getPricingstategydetaildto().getMaxCharge() >= 0;
        assert param.getPricingstategydetaildto().getMinCharge() != null;
//        assert param.getPricingstategydetaildto().getMinCharge() > 0;
        assert param.getPricingstategydetaildto().getMinCharge() >= 0;

        CalPriceFeeResponse response = new CalPriceFeeResponse();
        response.setFixedFee(param.getPricingstategydetaildto()
                .getFixedCharge());
        response.setMixFee(param.getPricingstategydetaildto().getMinCharge());
        response.setMaxFee(param.getPricingstategydetaildto().getMaxCharge());
        response.setFeeRate(param.getPricingstategydetaildto()
                .getChargeRate());
        long fee = PricingStrategyUtil.formatFee(param.getPricingstategydetaildto()
                .getChargeRate()
                * param.getTransactionAmount())
                + param.getPricingstategydetaildto().getFixedCharge();
        fee = fee > param.getPricingstategydetaildto().getMaxCharge() ? param
                .getPricingstategydetaildto().getMaxCharge() : fee;
        response
                .setFee(fee > param.getPricingstategydetaildto().getMinCharge() ? fee
                        : param.getPricingstategydetaildto().getMinCharge());
        return response;
    }
}
