/**
 * 
 */
package com.pay.txncore.service;

import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;

/**
 * 创建token及预授权绑卡服务
 * @author Jiangbo.Peng
 *
 */
public interface CreateTokenPreauthService {

	/**
	 * 创建token绑卡
	 * @param paymentInfo
	 * @return
	 */
	public PaymentResult CreateTokenPreauthBind(PaymentInfo paymentInfo);
	
//	public PaymentResult unBind(PaymentInfo paymentInfo);
//	
//	public PaymentResult unBindByToken(PaymentInfo paymentInfo);
	
	public PaymentResult addErrorCardBindOrder(PaymentInfo paymentInfo, String type);
	
	public PaymentResult updateErrorCardBindOrder(Long id);
	
}
