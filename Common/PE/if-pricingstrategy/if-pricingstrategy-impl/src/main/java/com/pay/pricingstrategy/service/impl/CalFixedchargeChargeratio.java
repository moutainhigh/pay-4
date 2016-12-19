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
 * 固定费用_费率. 费用 = 固定费用+ 费率*交易额
 */
public class CalFixedchargeChargeratio implements CalculateFee {
    private final int pricestrategytype = PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIO.getValue();
    /* (non-Javadoc)
     * 
     */
    public int getPricestrategyType() {
        // TODO Auto-generated method stub
        return pricestrategytype;
    }

    /* (non-Javadoc)
     * 
     * 固定费用_费率.
     */
    public long calculateFee(final CalFeeInnerParam param) {
       return this.calculateFeeDetail(param).getFee();
    }
    public CalPriceFeeResponse calculateFeeDetail(CalFeeInnerParam param) {
        assert param != null;
        assert param.getPricingstategydetaildto() != null;
        assert param.getTransactionAmount() >= 0;
        assert param.getPricingstategydetaildto().getChargeRate() != null;
        assert param.getPricingstategydetaildto().getChargeRate() >= 0;
        assert param.getPricingstategydetaildto().getFixedCharge() != null;
        assert param.getPricingstategydetaildto().getFixedCharge() > 0;

        CalPriceFeeResponse response = new CalPriceFeeResponse();
        response.setFixedFee(param.getPricingstategydetaildto()
                .getFixedCharge());
        response.setMixFee(0L);
        response.setMaxFee(0L);
        response.setFeeRate(param.getPricingstategydetaildto()
                .getChargeRate());
        response.setFee(PricingStrategyUtil.formatFee(param.getPricingstategydetaildto()
                .getChargeRate()
                * param.getTransactionAmount())
                + param.getPricingstategydetaildto().getFixedCharge());
        return response;
    }
}
