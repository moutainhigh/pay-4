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
 * 固定费率_费率及下限. 
 * 费用 = Math.max(固定费用+费率*交易额,minFee)
 */
public class CalFixedchargeChargeratioLL implements CalculateFee {
    private final int pricestrategytype = PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDLOWERLIMIT.getValue();
    /* (non-Javadoc)
     * 
     */
    public int getPricestrategyType() {
        // TODO Auto-generated method stub
        return pricestrategytype;
    }

    /* (non-Javadoc)
     * 
     * 固定费率_费率及下限.
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
        assert param.getPricingstategydetaildto().getMinCharge() != null;
        assert param.getPricingstategydetaildto().getMinCharge() >= 0;
        CalPriceFeeResponse response = new CalPriceFeeResponse();
        response.setFixedFee(param.getPricingstategydetaildto()
                .getFixedCharge());
        response.setMixFee(param.getPricingstategydetaildto().getMinCharge());
        response.setMaxFee(0L);
        response.setFeeRate(param.getPricingstategydetaildto()
                .getChargeRate());
        long fee = PricingStrategyUtil.formatFee(param.getPricingstategydetaildto()
                .getChargeRate()
                * param.getTransactionAmount())
                + param.getPricingstategydetaildto().getFixedCharge();
        response
                .setFee(fee > param.getPricingstategydetaildto().getMinCharge() ? fee
                        : param.getPricingstategydetaildto().getMinCharge());
        return response;
    }
}
