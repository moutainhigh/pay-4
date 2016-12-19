package com.pay.pe.service.payment.impl;

import com.pay.pricingstrategy.service.CalPricingStrategyParam;
import com.pay.pricingstrategy.service.impl.CalPriceFeeResponse;
import com.pay.pe.service.payment.AbstractPaymentService;
import com.pay.pe.service.payment.common.PaymentRequest;

/**
 * 
 */
public class DealPaymentService extends AbstractPaymentService {

	@Override
	protected CalPriceFeeResponse calculateAmount(final PaymentRequest request, final CalPricingStrategyParam para) {
		CalPriceFeeResponse calPriceFeeeFesponse = new CalPriceFeeResponse();
		calPriceFeeeFesponse.setFee(request.getAmount().getAmount());
		return calPriceFeeeFesponse;
	}

	// protected Money calculateAmount(
	// final PaymentRequest request,
	// final CalPricingStrategyParam para) {
	// //Long price = getPricingService().calculatePrice(para);
	// //return Money.rmb(price.longValue());
	// return request.getAmount();
	// }
}
