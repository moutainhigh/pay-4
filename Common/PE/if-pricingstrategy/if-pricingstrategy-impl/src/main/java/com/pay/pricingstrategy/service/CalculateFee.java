/**
 * 
 */
package com.pay.pricingstrategy.service;

import com.pay.pricingstrategy.service.impl.CalPriceFeeResponse;

/**
 * @author 
 *
 */
public interface CalculateFee {
    /**
     * 价格策略类型.
     * @return int
     */
    int getPricestrategyType();
    /**
     * 计算费用，单位是厘.
     * @param param CalPriceInnerParam
     * @return long
     */
    long calculateFee(CalFeeInnerParam param);
    /**
     * 计算费用，返回对象CalPriceFeeResponse.
     * @param param CalPriceInnerParam
     * @return CalPriceFeeResponse
     */
    
    CalPriceFeeResponse calculateFeeDetail(CalFeeInnerParam param);
}
