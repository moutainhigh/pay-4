/**
 * 
 */
package com.pay.pricingstrategy.service.impl;

import com.pay.pricingstrategy.helper.CACULATEMETHOD;
import com.pay.pricingstrategy.service.CalPriceInnerParam;
import com.pay.pricingstrategy.service.CalculatePrice;



/**
 * caculatemethod是每月固定的交易费用计算.
 * @author 
 *
 */
public class CalculatePriceFixedImpl implements CalculatePrice {
    //每月固定
    private final int caculatemethod = CACULATEMETHOD.FIXED.getValue();
    /* (non-Javadoc)
     * 
     */
    public long calPrice(CalPriceInnerParam param) {
        //每月固定，单笔交易不计费.
        return this.calPriceDetail(param).getFee();
    }

    /* (non-Javadoc)
     * 
     */
    public int getCaculatemethod() {
        // TODO Auto-generated method stub
        return caculatemethod;
    }

    public CalPriceFeeResponse calPriceDetail(CalPriceInnerParam param) {
        //每月固定，单笔交易不计费.
        return new CalPriceFeeResponse();
    }

}
