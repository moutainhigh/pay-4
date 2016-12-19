package com.pay.pricingstrategy.service.impl;

import com.pay.pricingstrategy.helper.PRICESTRATEGYTYPE;
import com.pay.pricingstrategy.service.CalFeeInnerParam;
import com.pay.pricingstrategy.service.CalculateFee;
import com.pay.pricingstrategy.util.PricingStrategyUtil;



public class CalChargeratio implements CalculateFee{
    private final int pricestrategytype = PRICESTRATEGYTYPE.CHARGERATIO.getValue();
    public int getPricestrategyType() {
        return pricestrategytype;
    }
    /* 
     * 费率.
     */
    public long calculateFee(final CalFeeInnerParam param) {
        return this.calculateFeeDetail(param).getFee();
    }
    /*
     * (non-Javadoc) 
     */
    public CalPriceFeeResponse calculateFeeDetail(CalFeeInnerParam param) {
        assert param != null;
        assert param.getPricingstategydetaildto() != null;
        assert param.getPricingstategydetaildto().getChargeRate() != null;
        assert param.getPricingstategydetaildto().getChargeRate() >= 0;
        CalPriceFeeResponse response = new CalPriceFeeResponse();
        response.setFixedFee(0L);
        response.setMixFee(0L);
        response.setMaxFee(0L);
        response.setFeeRate(param.getPricingstategydetaildto()
        .getChargeRate());
        
        long fee = param.getPricingstategydetaildto()
        .getChargeRate()
        * param.getTransactionAmount();
        
        //对计费函数计算的金额进行万分处理.
        fee = PricingStrategyUtil.formatFee(fee);
        
        response.setFee(fee);
        return response;
    }
}
