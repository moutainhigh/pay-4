/**
 * 
 */
package com.pay.pricingstrategy.service.impl;

import org.springframework.util.Assert;

import com.pay.pricingstrategy.helper.PRICESTRATEGYTYPE;
import com.pay.pricingstrategy.service.CalFeeInnerParam;
import com.pay.pricingstrategy.service.CalculateFee;



/**
 * @author 
 * 固定费用. 
 * 费用 = 固定费用
 */
public class CalFixedcharge implements CalculateFee {
    private final int pricestrategytype = PRICESTRATEGYTYPE.FIXEDCHARGE.getValue();
    /* (non-Javadoc)
     * 
     */
    
    public int getPricestrategyType() {
        return pricestrategytype;
    }

    /* (non-Javadoc)
     * 
     * 固定费用.
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
        assert param.getPricingstategydetaildto().getFixedCharge() != null;
        assert param.getPricingstategydetaildto().getFixedCharge() >= 0;
        CalPriceFeeResponse response = new CalPriceFeeResponse();
        response.setFixedFee(param.getPricingstategydetaildto().getFixedCharge());
        response.setMixFee(0L);
        response.setMaxFee(0L);
        response
                .setFee(param.getPricingstategydetaildto().getFixedCharge());
        return response;
    }
}
