/**
 * 
 */
package com.pay.pricingstrategy.service;

import com.pay.pricingstrategy.dto.PricingStrategyDetailDTO;





/**
 * @author 
 *
 */
public class CalFeeInnerParam {
    private long transactionAmount;
    private PricingStrategyDetailDTO pricingstategydetaildto;
   
   
    public long getTransactionAmount() {
        return transactionAmount;
    }
    public void setTransactionAmount(long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    public PricingStrategyDetailDTO getPricingstategydetaildto() {
        return pricingstategydetaildto;
    }
    public void setPricingstategydetaildto(
                                           PricingStrategyDetailDTO pricingstategydetaildto) {
        this.pricingstategydetaildto = pricingstategydetaildto;
    }
   
}
