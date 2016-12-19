package com.pay.txncore.service;


import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;

public interface CardBindService {
	
	public PaymentResult bind(PaymentInfo paymentInfo);
	
	public PaymentResult unBind(PaymentInfo paymentInfo);
	
	public PaymentResult unBindByToken(PaymentInfo paymentInfo);
	
	public PaymentResult addErrorCardBindOrder(PaymentInfo paymentInfo, String type);
	
	public PaymentResult updateErrorCardBindOrder(Long id);
	
	/**API-token支付-支付成功后绑卡
	 * @param paymentInfo 支付信息对象
	 * @return PaymentResult 支付结果对象
	 * add by zhaoyang 20160919
	 */
	public PaymentResult payAndBind(PaymentInfo paymentInfo);
	
	/**CASHIER-Token支付-支付成功后绑卡
	 * @param paymentInfo
	 * @return
	 */
	public PaymentResult cashierPayAndBind(PaymentInfo paymentInfo);
    
}
