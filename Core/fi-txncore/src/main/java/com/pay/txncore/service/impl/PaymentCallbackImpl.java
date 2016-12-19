package com.pay.txncore.service.impl;

import java.util.Map;

import com.pay.txncore.dto.ChannelPaymentResult;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.service.PaymentCallback;


/**
 * 支付回调
 * @author peiyu.yang
 *
 */
public class PaymentCallbackImpl implements PaymentCallback {

	@Override
	public PaymentResult paymentCallback(
			ChannelPaymentResult channelPaymentResult) {
		
		return null;
	}

	@Override
	public PaymentResult payment3DCallback(Map requestParam) {
		
		return null;
	}

}
