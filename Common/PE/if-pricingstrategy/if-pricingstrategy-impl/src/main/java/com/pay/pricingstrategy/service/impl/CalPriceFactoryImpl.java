/**
 * 
 */
package com.pay.pricingstrategy.service.impl;

import java.util.Iterator;
import java.util.List;

import com.pay.pricingstrategy.service.CalPriceFactory;
import com.pay.pricingstrategy.service.CalculatePrice;



/**
 * @author 
 *
 */
public class CalPriceFactoryImpl implements CalPriceFactory {
    /**
     * 所有CalculatePrice.
     */
    List calPriceList;
    /* (non-Javadoc)
     * 
     */
    public CalculatePrice getCalPrice(final int caculatemethod) {
        for (Iterator ite = calPriceList.iterator();ite.hasNext();) {
            CalculatePrice calP = (CalculatePrice) ite.next();
            if (calP.getCaculatemethod() == caculatemethod) {
                return calP;
            }
        }
        throw new RuntimeException(caculatemethod
            + " caculatemethod is not  definite");
    }
    public void setCalPriceList(List calPriceList) {
        this.calPriceList = calPriceList;
    }

}
