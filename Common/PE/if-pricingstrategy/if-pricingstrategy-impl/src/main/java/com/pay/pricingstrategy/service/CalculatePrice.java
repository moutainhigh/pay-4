package com.pay.pricingstrategy.service;

import com.pay.pricingstrategy.service.impl.CalPriceFeeResponse;



/**
 * @author
 *
 */
public interface CalculatePrice {
    long calPrice(CalPriceInnerParam param);
    CalPriceFeeResponse calPriceDetail(CalPriceInnerParam param);
    int getCaculatemethod();
}