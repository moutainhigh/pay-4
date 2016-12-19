package com.pay.txncore.service;

import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;

public interface ChinaLocalPayService {
	
	/**
	 * 本地化支付 delin.dong
	 */
	PaymentResult chinaLocaleAcquire(PaymentInfo paymentInfo);

}
