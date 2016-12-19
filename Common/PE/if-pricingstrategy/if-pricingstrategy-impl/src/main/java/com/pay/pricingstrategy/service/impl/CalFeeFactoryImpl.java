/**
 * 
 */
package com.pay.pricingstrategy.service.impl;

import java.util.Iterator;
import java.util.List;

import com.pay.pricingstrategy.service.CalFeeFactory;
import com.pay.pricingstrategy.service.CalculateFee;



/**
 * @author 
 *
 */
public class CalFeeFactoryImpl implements CalFeeFactory {
    /**
     * 所有不同价格策略类型的计算费用的了类.
     */
    List calFeeList;
    /* (non-Javadoc)
     * 
     */
    public CalculateFee getCalFee(int pricestrategyType) {
        for (Iterator ite = calFeeList.iterator();ite.hasNext();) {
            CalculateFee calPrice = (CalculateFee)ite.next();
            if (pricestrategyType == calPrice.getPricestrategyType()) {
                return calPrice;
            }
        }
        throw new RuntimeException(pricestrategyType + " pricestrategytype is not  definite");
    }
    public void setCalFeeList(List calFeeList) {
        this.calFeeList = calFeeList;
    }
  

}
