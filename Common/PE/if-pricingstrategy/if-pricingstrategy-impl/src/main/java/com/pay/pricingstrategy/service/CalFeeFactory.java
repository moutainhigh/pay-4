/**
 * 
 */
package com.pay.pricingstrategy.service;

/**
 * @author 
 *
 */
public interface CalFeeFactory {
    /**
     * 计算价格策略的factory，根据pricestrategyType找到具体的计算
     * 费用的CalculatePrice.
     * @param pricestrategyType int
     * @return CalculatePrice
     */
    CalculateFee getCalFee(int pricestrategyType);
}
