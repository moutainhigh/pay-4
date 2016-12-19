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
 * 费率及上下线.
 */
public class CalChargeratioULLL implements CalculateFee {
    private final int pricestrategytype = PRICESTRATEGYTYPE.CHARGERATIOANDUPPERlIMITANDLOWERLIMIT.getValue();
    /* (non-Javadoc)
     * 
     */
    public int getPricestrategyType() {
        // TODO Auto-generated method stub
        return pricestrategytype;
    }

    /* (non-Javadoc)
     *
     * 费率及上下限.
     */
    public long calculateFee(final CalFeeInnerParam param) {

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
        assert param.getPricingstategydetaildto().getMaxCharge() != null;
        assert param.getPricingstategydetaildto().getMaxCharge() >= 0;
        assert param.getPricingstategydetaildto().getMinCharge() != null;
        assert param.getPricingstategydetaildto().getMinCharge() >= 0;
        CalPriceFeeResponse response = new CalPriceFeeResponse();
        response.setFixedFee(0L);
        response.setMixFee(param
                .getPricingstategydetaildto().getMinCharge());
        response.setMaxFee(param.getPricingstategydetaildto().getMaxCharge());
        response.setFeeRate(param.getPricingstategydetaildto()
                .getChargeRate());
        Long fee = param.getPricingstategydetaildto().getChargeRate()
                * param.getTransactionAmount();
        fee = PricingStrategyUtil.formatFee(fee);
        //
        fee = fee > param.getPricingstategydetaildto().getMaxCharge() ? param
                .getPricingstategydetaildto().getMaxCharge() : fee;
        response
                .setFee(fee > param.getPricingstategydetaildto().getMinCharge() ? fee
                        : param.getPricingstategydetaildto().getMinCharge());
        return response;
    }
}
