/**
 * 
 */
package com.pay.pricingstrategy.service.impl;

import org.springframework.util.Assert;

import com.pay.pricingstrategy.helper.PRICESTRATEGYTYPE;
import com.pay.pricingstrategy.service.CalFeeInnerParam;
import com.pay.pricingstrategy.service.CalculateFee;
import com.pay.pricingstrategy.util.PricingStrategyUtil;


/**
 * @author 
 * 费率和最低费用.
 */
public class CalChargeratioLL implements CalculateFee {
    private final int pricestrategytype = PRICESTRATEGYTYPE.CHARGERATIOANDLOWERLIMIT.getValue();
    /* (non-Javadoc)
     * 
     */
    public int getPricestrategyType() {
        return pricestrategytype;
    }

    /* (non-Javadoc)
     * 
     * 费率及下限.
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
        assert param.getPricingstategydetaildto().getChargeRate() != null;
        assert param.getPricingstategydetaildto().getChargeRate() >= 0;
        assert param.getPricingstategydetaildto().getMinCharge() != null;
        assert param.getPricingstategydetaildto().getMinCharge() >= 0;
        CalPriceFeeResponse response = new CalPriceFeeResponse();
        response.setFixedFee(0L);
        response.setMixFee(param.getPricingstategydetaildto().getMinCharge());
        response.setMaxFee(0L);
        response.setFeeRate(param.getPricingstategydetaildto()
                .getChargeRate());
        Long fee = param.getPricingstategydetaildto().getChargeRate()
                * param.getTransactionAmount();
        fee = PricingStrategyUtil.formatFee(fee);
        response.setFee(fee > param.getPricingstategydetaildto().getMinCharge() ? fee
                : param.getPricingstategydetaildto().getMinCharge());
        return response;
    }
}
