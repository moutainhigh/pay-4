/**
 * 
 */
package com.pay.txncore.service;

import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;

/**
 * @author yanshichuan
 *
 */
public interface BindCardService {
	
	public PaymentResult bind(PaymentInfo paymentInfo);
	
	public PaymentResult unBind(PaymentInfo paymentInfo);
	
	public PaymentResult unBindByToken(PaymentInfo paymentInfo);
	
	public PaymentResult addErrorCardBindOrder(PaymentInfo paymentInfo, String type);
	
	public PaymentResult updateErrorCardBindOrder(Long id);
}
