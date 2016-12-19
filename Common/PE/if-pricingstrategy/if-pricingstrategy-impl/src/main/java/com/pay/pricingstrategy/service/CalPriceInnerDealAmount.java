package com.pay.pricingstrategy.service;

import java.math.BigDecimal;
import java.util.Date;



public interface CalPriceInnerDealAmount {
    /**
     * 得到每个月累计成功交易饿.
     * @param mfdate MfDate
     * @return long
     */
    long getSuccAccumulatedAmount(Date mfdate);
    
    
    /**
    * @Title: getSuccAccumulatedAmount
    * @Description: 根据业务，会员，月，得到累计的金额
    * @param @param month
    * @param @param memberCode
    * @param @param paymentServiceCode
    * @param @return    设定文件
    * @return BigDecimal    返回类型
    * @throws
    */ 
    BigDecimal getSuccAccumulatedAmount(int month,Long memberCode,Integer paymentServiceCode);
}
