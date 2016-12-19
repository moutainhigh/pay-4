package com.pay.pricingstrategy.service;


/**
 * @author 
 * calcultePrice的构造器，找到具体的calcultePrice.
 */
public interface CalPriceFactory {
    /**
     * 根据caculatemethod找到CalculatePrice.
     * @param caculatemethod
     * @return CalculatePrice
     */
    CalculatePrice getCalPrice(int caculatemethod);
}
