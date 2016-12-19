/**
 * 
 */
package com.pay.pricingstrategy.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.pricingstrategy.service.CalPriceInnerDealAmount;
import com.pay.pricingstrategy.service.CumulatedFlowService;



/**
 * @author 
 *
 */
public final class CalPriceInnerDealAmountImpl implements CalPriceInnerDealAmount {

	private CumulatedFlowService cumulatedFlowService;


	/**
     * 得到每个月累计成功交易饿.
     * @param mfdate MfDate
     * @return long
     */
    public long getSuccAccumulatedAmount(Date mfdate) {
        // TODO Auto-generated method stub
        return 1000;
    }

	@Override
	public BigDecimal getSuccAccumulatedAmount(int month, Long memberCode,
			Integer paymentServiceCode) {
		String acctCode=memberCode+"10";
		return cumulatedFlowService.queryAmountByMonth(paymentServiceCode, month, acctCode);
	}
	
    public void setCumulatedFlowService(CumulatedFlowService cumulatedFlowService) {
		this.cumulatedFlowService = cumulatedFlowService;
	}

}
