package com.pay.pe.service.payment.impl;

import com.pay.pe.service.payment.AbstractPaymentService;
import com.pay.pe.service.payment.common.PaymentRequest;
import com.pay.pricingstrategy.service.CalPricingStrategyParam;
import com.pay.pricingstrategy.service.impl.CalPriceFeeResponse;

/**
 * 费用支付服务
 *
 */
public class BillingPaymentService extends AbstractPaymentService {
    /**
     * 通过价格策略算费.
     */
    @Override
    protected CalPriceFeeResponse calculateAmount(
            final PaymentRequest request,
            final CalPricingStrategyParam para) {
    	return getPricingService().calculatePriceToResponse(para);
    }
//    protected Money calculateAmount(
//            final PaymentRequest request,
//            final CalPricingStrategyParam para) {
//        Long price = getPricingService().calculatePrice(para);
//        return Money.rmb(price.longValue());
//    }
    
    
    
    
}
