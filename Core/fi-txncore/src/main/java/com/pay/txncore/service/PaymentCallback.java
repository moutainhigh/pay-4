package com.pay.txncore.service;

import java.util.Map;

import com.pay.txncore.dto.ChannelPaymentResult;
import com.pay.txncore.dto.PaymentResult;

public interface PaymentCallback {
	
	/**
	 * 支付回调
	 * @return
	 */
	PaymentResult paymentCallback(ChannelPaymentResult channelPaymentResult);
	
	
	/**
	 * 3D支付回调
	 */
	
	public PaymentResult payment3DCallback(	Map requestParam);

}
