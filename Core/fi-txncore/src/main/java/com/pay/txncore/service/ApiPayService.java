package com.pay.txncore.service;

import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;

public interface ApiPayService {
	/**
	 * 跨境API支付
	 * 
	 * @param paymentInfo
	 * @return
	 */
	PaymentResult crossApiPay(PaymentInfo paymentInfo);
	
	/**
	 * 跨境API 3D支付
	 * 
	 * @param paymentInfo
	 * @return
	 */
	PaymentResult cross3DApiPay(PaymentInfo paymentInfo);
	
	/**
	 * 跨境API威富通【微信｜支付宝】支付
	 * 
	 * @param paymentInfo
	 * @return
	 */
	PaymentResult crossWftApiPay(PaymentInfo paymentInfo);
}
